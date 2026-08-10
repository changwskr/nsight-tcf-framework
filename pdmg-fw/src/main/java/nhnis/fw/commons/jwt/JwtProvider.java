/***************************************************************************
 * Copyright 2026 by Nonghyup. All rights reserved. Nonghyup 의 사전 승인 없이
 * 본 내용의 전부 또는 일부에 대한 복사, 배포, 사용을 금합니다. Nonghyup의 사전 승인
 * 없이 소스코드를 변경하여 사용하는 경우 소스코드에 대한 품질과 성능을 보장하지 않습니다.
 *
 * If you modify this source without Nonghyup’s approval. Nonghyup does
 * not guarantee the quality and performance of source.
 ***************************************************************************/
package nhnis.fw.commons.jwt;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Access Token 서명·만료 검증.
 *
 * <p>{@code jwt.jwk-set-uri} 가 있으면 RS256(JWKS)을 우선 사용하고,
 * 없으면 {@code jwt.secret} HS256으로 검증한다.
 */
@Component
public class JwtProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    private static final long JWKS_CACHE_TTL_MS = 60_000L;

    @Value("${jwt.secret:}")
    private String secret;

    @Value("${jwt.jwk-set-uri:}")
    private String jwkSetUri;

    private SecretKey hmacKey;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private volatile JWKSet cachedJwkSet;
    private volatile long cachedJwkSetAt;

    @PostConstruct
    public void init() {
        if (StringUtils.hasText(secret)) {
            byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
            if (bytes.length >= 32) {
                hmacKey = Keys.hmacShaKeyFor(bytes);
            } else {
                log.warn("jwt.secret 길이가 32바이트 미만이라 HS256 검증을 사용할 수 없습니다.");
            }
        }
        if (StringUtils.hasText(jwkSetUri)) {
            log.info("JwtProvider JWKS 검증 활성화: {}", jwkSetUri);
        }
    }

    public boolean validate(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT validate fail: {}", e.toString());
            return false;
        }
    }

    public String getSsoId(String token) {
        try {
            JWTClaimsSet claims = parseClaims(token);
            String userId = claims.getStringClaim("userId");
            if (StringUtils.hasText(userId)) {
                return userId;
            }
            return claims.getSubject();
        } catch (Exception e) {
            throw new IllegalArgumentException("JWT subject 조회 실패", e);
        }
    }

    public boolean isAccessToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet claims = parseClaims(token);
            String type = claims.getStringClaim("type");
            if ("ACCESS".equals(type)) {
                return true;
            }
            // pdmg-jwt 구버전 Access Token(type claim 없음) + RS256
            return !StringUtils.hasText(type)
                    && JWSAlgorithm.RS256.equals(jwt.getHeader().getAlgorithm());
        } catch (Exception e) {
            return false;
        }
    }

    private JWTClaimsSet parseClaims(String token) throws Exception {
        SignedJWT jwt = SignedJWT.parse(token);
        JWSAlgorithm alg = jwt.getHeader().getAlgorithm();

        if (JWSAlgorithm.RS256.equals(alg) || JWSAlgorithm.RS384.equals(alg)
                || JWSAlgorithm.RS512.equals(alg)) {
            if (!StringUtils.hasText(jwkSetUri)) {
                throw new IllegalStateException("RS256 토큰인데 jwt.jwk-set-uri 가 없습니다.");
            }
            JWKSet jwkSet = loadJwkSet();
            String kid = jwt.getHeader().getKeyID();
            JWK jwk = StringUtils.hasText(kid) ? jwkSet.getKeyByKeyId(kid) : null;
            if (jwk == null && !jwkSet.getKeys().isEmpty()) {
                jwk = jwkSet.getKeys().get(0);
            }
            if (!(jwk instanceof RSAKey rsaKey)) {
                throw new IllegalStateException("JWKS에서 RSA 공개키를 찾지 못했습니다. kid=" + kid);
            }
            RSAPublicKey publicKey = rsaKey.toRSAPublicKey();
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            if (!jwt.verify(verifier)) {
                throw new IllegalArgumentException("JWT 서명 검증 실패");
            }
        } else {
            if (hmacKey == null) {
                throw new IllegalStateException("HS256 검증용 jwt.secret 이 없습니다.");
            }
            JWSVerifier verifier = new MACVerifier(hmacKey.getEncoded());
            if (!jwt.verify(verifier)) {
                throw new IllegalArgumentException("JWT 서명 검증 실패");
            }
        }

        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        Date exp = claims.getExpirationTime();
        if (exp != null && exp.before(new Date())) {
            throw new IllegalArgumentException("JWT 만료");
        }
        return claims;
    }

    private JWKSet loadJwkSet() throws Exception {
        long now = System.currentTimeMillis();
        JWKSet current = cachedJwkSet;
        if (current != null && (now - cachedJwkSetAt) < JWKS_CACHE_TTL_MS) {
            return current;
        }
        synchronized (this) {
            if (cachedJwkSet != null && (System.currentTimeMillis() - cachedJwkSetAt) < JWKS_CACHE_TTL_MS) {
                return cachedJwkSet;
            }
            HttpRequest request = HttpRequest.newBuilder(URI.create(jwkSetUri))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("JWKS 조회 실패 HTTP " + response.statusCode());
            }
            cachedJwkSet = JWKSet.parse(unwrapJwksJson(response.body()));
            cachedJwkSetAt = System.currentTimeMillis();
            return cachedJwkSet;
        }
    }

    /**
     * 표준 JWKS {@code {"keys":[...]}} 또는 업무 Advice가 감싼
     * {@code {"dto":{"keys":[...]}}} 모두 허용한다.
     */
    private String unwrapJwksJson(String body) throws Exception {
        JsonNode root = objectMapper.readTree(body);
        if (root != null && root.has("dto") && root.get("dto").has("keys")) {
            return root.get("dto").toString();
        }
        return body;
    }
}
