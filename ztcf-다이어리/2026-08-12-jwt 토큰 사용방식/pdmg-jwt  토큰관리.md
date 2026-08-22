현재 구현을 기준으로 보면, `pdmg-jwt`의 RS256 토큰 발급과 `pdmg-service`의 토큰 검증은 아직 연결되지 않았습니다.

## 1. `pdmg-jwt`의 Private Key 관리

현재 Private Key는 파일이나 DB에 저장하지 않습니다. 애플리케이션이 시작될 때 메모리에서 새 RSA 키쌍을 생성합니다.

[JwtKeyConfiguration.java](</C:/Programming(23-08-15)/nsight-tcf-framework-codex/pdmg-jwt/src/main/java/nhnis/mg/jw/a/config/JwtKeyConfiguration.java:19>)

```java
@Bean
public RSAKey jwtSigningKey() throws Exception {
    RSAKey key = new RSAKeyGenerator(2048)
            .keyID("nsight-jwt-rs256")
            .generate();

    return key;
}
```

생성된 키쌍에서 Private Key를 Spring Bean으로 등록합니다.

```java
@Bean
public RSAPrivateKey jwtPrivateKey(RSAKey jwtSigningKey)
        throws JOSEException {
    return jwtSigningKey.toRSAPrivateKey();
}
```

그리고 토큰을 발급할 때 이 Private Key로 RS256 서명합니다.

[JwtTokenIssuer.java](</C:/Programming(23-08-15)/nsight-tcf-framework-codex/pdmg-jwt/src/main/java/nhnis/mg/jw/a/support/JwtTokenIssuer.java:48>)

```java
SignedJWT signedJwt = new SignedJWT(
    new JWSHeader.Builder(JWSAlgorithm.RS256)
        .keyID("nsight-jwt-rs256")
        .type(JOSEObjectType.JWT)
        .build(),
    claims
);

signedJwt.sign(new RSASSASigner(privateKey));
```

현재 관리 구조는 다음과 같습니다.

```text
pdmg-jwt 시작
  → RSA 2048 Key Pair 신규 생성
  → Private Key는 JVM 메모리에만 보관
  → 토큰 발급 시 Private Key로 RS256 서명
  → 서버 종료 시 키 소멸
```

### 현재 구조의 문제

`pdmg-jwt`가 재기동되면 새로운 키쌍이 생성됩니다. 따라서 재기동 전에 발급된 토큰은 더 이상 검증할 수 없습니다.

또한 서버를 여러 대 운영하면 각각 다른 Private Key를 생성하므로 인스턴스 간 JWT 호환이 되지 않습니다.

운영에서는 다음처럼 관리해야 합니다.

```text
KeyStore / Vault / KMS / Kubernetes Secret
               ↓
        pdmg-jwt 시작 시 로딩
               ↓
      동일한 Private Key로 서명
```

Private Key는 `pdmg-service`로 전달하면 안 됩니다. 오직 `pdmg-jwt`만 보유해야 합니다.

---

## 2. Public Key는 어디에 있는가

Public Key 역시 같은 RSA 키쌍에서 만들어집니다.

[JwtKeyConfiguration.java](</C:/Programming(23-08-15)/nsight-tcf-framework-codex/pdmg-jwt/src/main/java/nhnis/mg/jw/a/config/JwtKeyConfiguration.java:33>)

```java
@Bean
public RSAPublicKey jwtPublicKey(RSAKey jwtSigningKey)
        throws JOSEException {
    return jwtSigningKey.toRSAPublicKey();
}
```

Public Key는 JWKS 형식으로 만들어집니다.

```java
@Bean
public JWKSet jwtJwkSet(RSAKey jwtSigningKey) {
    return new JWKSet(jwtSigningKey.toPublicJWK());
}
```

그리고 다음 URL로 공개됩니다.

[JwkSetController.java](</C:/Programming(23-08-15)/nsight-tcf-framework-codex/pdmg-jwt/src/main/java/nhnis/mg/jw/a/entry/web/JwkSetController.java:17>)

```http
GET http://127.0.0.1:8110/.well-known/jwks.json
```

응답 개념은 다음과 같습니다.

```json
{
  "keys": [
    {
      "kty": "RSA",
      "kid": "nsight-jwt-rs256",
      "use": "sig",
      "n": "...",
      "e": "AQAB"
    }
  ]
}
```

여기에는 Public Key 정보만 포함되고 Private Key는 포함되지 않습니다.

---

## 3. `pdmg-service`에 Public Key가 있는가

현재 `pdmg-service`와 `pdmg-fw`에는 RS256 Public Key가 없습니다.

다음 구성도 실제 검증 코드에서 사용되지 않습니다.

```yaml
nsight:
  security:
    jwt:
      issuer-uri: http://127.0.0.1:8110
      jwk-set-uri: http://127.0.0.1:8110/.well-known/jwks.json
```

현재 `pdmg-service`의 검증은 `pdmg-fw`의 [JwtProvider.java](</C:/Programming(23-08-15)/nsight-tcf-framework-codex/pdmg-fw/src/main/java/nhnis/fw/commons/jwt/JwtProvider.java:40>)가 담당합니다.

이 클래스는 Public Key가 아니라 `jwt.secret` 공유 비밀키를 사용합니다.

```java
@Value("${jwt.secret}")
private String secret;

@PostConstruct
public void init() {
    key = Keys.hmacShaKeyFor(
        secret.getBytes(StandardCharsets.UTF_8)
    );
}
```

즉 현재 구조는 다음과 같습니다.

```text
pdmg-jwt
  Private Key로 RS256 서명
            ↓
       JWT 전달
            ↓
pdmg-service / pdmg-fw
  jwt.secret HMAC 키로 검증 시도
            ↓
          실패
```

RS256 토큰은 RSA Public Key로 검증해야 하는데, 현재 `JwtProvider`는 HMAC 공유키로 검증하고 있습니다.

---

## 4. 현재 `pdmg-service`의 토큰 검증 흐름

요청이 들어오면 `pdmg-fw`의 `DefaultFilter`가 토큰을 검사합니다.

[DefaultFilter.java](</C:/Programming(23-08-15)/nsight-tcf-framework-codex/pdmg-fw/src/main/java/nhnis/fw/commons/filter/DefaultFilter.java:138>)

```text
HTTP 요청
  → DefaultFilter
  → Authorization 헤더 확인
  → Bearer Token 추출
  → JwtProvider.validate(token)
  → JwtProvider.isAccessToken(token)
  → JwtProvider.getSsoId(token)
  → Controller/TCF 처리
```

검증 코드:

```java
String authorization =
        request.getHeader(HttpHeaders.AUTHORIZATION);

if (authorization == null
        || !authorization.startsWith("Bearer ")) {
    response.sendError(401);
    return;
}

String token = authorization.substring(7);

if (!jwtProvider.validate(token)) {
    response.sendError(401);
    return;
}

if (!jwtProvider.isAccessToken(token)) {
    response.sendError(401);
    return;
}

String ssoId = jwtProvider.getSsoId(token);
request.setAttribute("ssoId", ssoId);
```

단, `local` 프로필에서는 JWT 검증 자체를 건너뜁니다.

```java
if (!"local".equalsIgnoreCase(active)) {
    // JWT 검증
}
```

따라서 현재 로컬 환경에서는 RS256/HMAC 불일치가 드러나지 않을 수 있습니다. `dev`나 `prod`에서는 `pdmg-jwt`가 발급한 RS256 토큰이 기존 `JwtProvider` 검증을 통과하지 못합니다.

## 5. 정상적인 권장 구조

```text
pdmg-jwt
  ├─ Private Key: KeyStore/Vault/KMS에서 로딩
  ├─ Private Key로 RS256 서명
  └─ Public Key를 JWKS URL로 공개
                 │
                 ▼
pdmg-service / pdmg-fw
  ├─ JWKS URL에서 Public Key 조회
  ├─ JWT 헤더의 kid로 검증 키 선택
  ├─ RS256 서명 검증
  ├─ iss, aud, exp, nbf 검증
  └─ type=ACCESS 검증
```

`pdmg-service`가 Public Key 파일을 직접 가지고 있을 필요는 없습니다. `JwtDecoder`가 JWKS URL을 통해 Public Key를 조회하고 캐시하게 하는 방식이 적절합니다.

예를 들면:

```java
@Bean
JwtDecoder jwtDecoder() {
    NimbusJwtDecoder decoder =
        NimbusJwtDecoder.withJwkSetUri(
            "http://pdmg-jwt:8110/.well-known/jwks.json"
        ).build();

    return decoder;
}
```

그리고 기존 `JwtProvider`를 RS256 기반으로 변경해야 합니다.

핵심 결론은 다음과 같습니다.

- Private Key: 현재 `pdmg-jwt` 시작 시 메모리에서 임시 생성
- Public Key: `pdmg-jwt`의 JWKS URL에서 제공
- `pdmg-service`의 Public Key: 현재 없음
- 현재 검증 방식: `pdmg-fw`가 `jwt.secret`으로 HMAC 검증
- 결과: `pdmg-jwt`가 생성한 RS256 토큰과 호환되지 않음
- 필요한 변경: `pdmg-fw`의 검증기를 JWKS 기반 RS256 검증기로 교체해야 함
