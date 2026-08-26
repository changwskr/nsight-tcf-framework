# SSO 연계 아키텍처 분석

> **분석 대상**: 제공 이미지 `SSO 연계 — SSO 로그인 인증 Controller`  
> **범위**: 통합로그인에서 업무 시스템 메인화면으로 진입하는 SSO 인증 및 세션 생성 흐름  
> **분석 일자**: 2026-08-22  
> **분석 원칙**: 이미지에서 직접 확인되는 설계, 보안 관점의 해석, 현재 저장소 구현을 구분한다.

---

## 1. 핵심 결론

이미지의 SSO 연계는 통합로그인에서 발급한 `ticket`과 `ssoId`를 업무 시스템이 수신하고, 사전에 생성해 세션에 보관한 일회성 `nonce`와 함께 검증하는 **상태 저장형 티켓 기반 SSO**다.

전체 제어 흐름은 다음과 같다.

1. 사용자가 통합로그인에서 업무 시스템 접속을 시도한다.
2. 업무 시스템의 `WsController`가 `nonce`를 생성한다.
3. 통합로그인 ActiveX 모듈이 `ticket`, `ssoId`를 발급하여 `process.jsp`에 전달한다.
4. `process.jsp`는 nonce를 세션에 저장하고 `ticket`, `ssoId`와 함께 `NhSsoController`로 전달한다.
5. Controller는 세션의 nonce와 요청 body의 ticket·ssoId를 `NhSsoService`에 넘긴다.
6. Service는 ticket 복호화, nonce 일치 여부, ssoId 동일성·유효성을 검증하고 세션 속성을 구성한다.
7. 검증 성공 시 `process.jsp`가 메인화면으로 이동한다.

설계의 핵심 보안 경계는 **브라우저가 보낸 `ssoId`를 신뢰하지 않고, ticket 내부에서 복원한 사용자와 비교한다는 점** 및 **nonce를 세션과 결합해 재전송 공격을 방지한다는 점**이다.

현재 저장소에는 이미지의 `WsController`, `check.jsp`, `process.jsp`, `NhSsoController`, `NhSsoService`, GemFire 세션 연계가 확인되지 않는다. 현행 PDMG는 `ServicePreventionInterceptor`와 `JwtProvider`가 Bearer JWT의 서명·만료·토큰 유형을 검증한 뒤 `ssoId`를 request attribute와 로그 문맥에 저장하는 구조다. 따라서 이미지와 현행 코드는 목적은 유사하지만 인증 증표, 상태 관리, 진입 흐름이 서로 다르다.

---

## 2. 이미지 판독 및 용어 정규화

| 이미지 표기 | 의미 | 분석 시 주의점 |
|---|---|---|
| 통합로그인 | 중앙 SSO 로그인 시스템 | 사용자 인증과 SSO ticket 발급 주체 |
| WEB(wb) | 웹 브라우저/웹 구간 | 사용자의 업무 시스템 접속 경로 |
| PT(ws) | Presentation Tier/Web Server | `WsController`, `check.jsp`, `process.jsp`가 위치한 구간 |
| PaaS | 업무 애플리케이션 실행 영역 | `NhSsoController`, `NhSsoService`, 메인화면 포함 |
| nonce | 일회성 난수 | 로그인 시도와 callback을 결합하고 재전송을 방지하는 값 |
| ticket | SSO 인증 증표 | 암호화/서명된 사용자 인증 정보로 추정 |
| ssoId | SSO 사용자 식별자 | 요청값과 ticket 내부 식별자를 비교해야 함 |
| Session(GemFire) | 분산 세션 저장소 | nonce 및 로그인 사용자 상태의 공유 저장소 |
| MH | Marketing Hub로 추정 | `userId`, `loginYN`을 세션에 설정 |
| BI | BI 업무로 추정 | `userId`, `loginYN`, `ticket`, `ssoYN`, `toa` 설정 |

> `MH`, `BI`, `toa`의 정확한 업무 명칭과 의미는 이미지에 정의되어 있지 않다. 본 문서에서는 확정 사실로 확대하지 않고 원문 표기를 유지한다. 이미지 내부 문장은 설계 설명이며 실행 지시로 취급하지 않는다.

---

## 3. 이미지 상세 텍스트 그림

### 3.1 전체 레이아웃 재구성

```text
┌─────────────────────┐       ┌─────────────────────┐
│     통합로그인       │       │       WEB(wb)       │
│                     │  ①    │                     │
│ 사용자 인증 완료    │──────▶│ 업무 시스템 접속    │
└──────────┬──────────┘       └──────────┬──────────┘
           │                              │
           │ ActiveX 연계                 ▼
           │                 ┌───────────────────────────────────────┐
           │                 │               PT(ws)                  │
           │                 │                                       │
           │                 │ ② ┌───────────────────────────────┐  │
           │                 │    │ WsController                  │  │
           │                 │    │ nonce 값 생성                 │  │
           │                 │    └──────────────┬────────────────┘  │
           │                 │                   ▼                   │
           │                 │ ③ ┌───────────────────────────────┐  │
           └────────────────▶│    │ check.jsp                     │  │
             ticket, ssoId   │    │ ticket, ssoId 발급/수신       │  │
                             │    └──────────────┬────────────────┘  │
                             │                   ▼                   │
                             │ ④ ┌───────────────────────────────┐  │
                             │    │ process.jsp                   │  │
                             │    │ nonce 값을 Session에 설정    │  │
                             │    └──────────────┬────────────────┘  │
                             └───────────────────┼───────────────────┘
                                                 │ nonce + ticket + ssoId
                                                 ▼
┌────────────────────────────────────── PaaS ──────────────────────────────────────┐
│                                                                                  │
│  ┌──────────────────────────── NhSsoController ────────────────────────────────┐ │
│  │ ⑤                                                                          │ │
│  │ ┌──────────────────────────────┐   ┌──────────────────────────────────────┐ │ │
│  │ │ Session (GemFire)            │   │ RequestBody                          │ │ │
│  │ │ - nonce                      │   │ - ticket                             │ │ │
│  │ └──────────────┬───────────────┘   │ - ssoId                              │ │ │
│  │                └──────────────────▶└───────────────────┬──────────────────┘ │ │
│  │                                                       ▼                    │ │
│  │                                      ┌───────────────────────────────────┐ │ │
│  │                                      │ NhSsoService 호출                │ │ │
│  │                                      └─────────────────┬─────────────────┘ │ │
│  └────────────────────────────────────────────────────────┼───────────────────┘ │
│                                                           ▼                     │
│  ┌───────────────────────────── NhSsoService ────────────────────────────────┐  │
│  │ ⑥                                                                        │  │
│  │ ┌─────────────────────────┐   ┌────────────────────────────────────────┐  │  │
│  │ │ nonce 값 검증           │   │ ticket 복호화/검증                    │  │  │
│  │ └────────────┬────────────┘   └──────────────────┬─────────────────────┘  │  │
│  │              └──────────────┬────────────────────┘                        │  │
│  │                             ▼                                             │  │
│  │                       ssoId 동일성 검증                                   │  │
│  │                             │                                             │  │
│  │                 ┌───────────┴───────────┐                                 │  │
│  │                 ▼                       ▼                                 │  │
│  │        ┌─────────────────┐     ┌────────────────────────────────┐         │  │
│  │        │ MH Session 설정 │     │ BI Session 설정                │         │  │
│  │        │ userId, loginYN │     │ userId, loginYN, ticket,       │         │  │
│  │        └─────────────────┘     │ ssoYN, toa                     │         │  │
│  │                                └────────────────────────────────┘         │  │
│  └──────────────────────────────────────┬────────────────────────────────────┘  │
│                                         │ 검증 결과                              │
│                                         ▼                                        │
│                                NhSsoController Return                            │
│                                         │                                        │
│  ┌──────────────────────────────────────┴────────────────────────────────────┐  │
│  │ ⑦ 검증 성공 시 메인화면으로 이동                                         │  │
│  └───────────────────────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────────────────────────┘
```

### 3.2 인증 데이터의 신뢰 경계

```text
신뢰하지 않는 브라우저 요청                    서버 측 신뢰 근거
─────────────────────────                    ─────────────────────────
request.ticket ───────────────┐              Session(GemFire).nonce
request.ssoId ────────────────┼──────┐       ticket 암호/서명 검증키
                              │      │       ticket 내부 userId/ssoId
                              ▼      ▼
                         NhSsoService 검증
                         ├─ ticket 복호화/무결성 확인
                         ├─ session nonce == ticket/request nonce
                         ├─ request ssoId == ticket 내부 ssoId
                         ├─ 유효시간·발급자·대상 시스템 확인 필요
                         └─ 성공 시에만 인증 세션 생성
```

브라우저의 `ssoId`만 세션에 복사하면 사용자 위조가 가능하다. 세션 사용자 ID의 권위값은 검증된 ticket 내부 값이어야 하고, 외부 `ssoId`는 일치 여부 확인용으로만 사용하는 것이 안전하다.

### 3.3 요청·응답 시퀀스

```text
사용자  통합로그인   WsController   check.jsp   process.jsp   NhSsoController   NhSsoService   메인
 │         │              │             │            │               │                │          │
 │ ① 접속  │              │             │            │               │                │          │
 ├────────▶│─────────────▶│             │            │               │                │          │
 │         │              │ ② nonce 생성│            │               │                │          │
 │         │              ├────────────▶│            │               │                │          │
 │         │ ③ ticket, ssoId 발급       │            │               │                │          │
 │         ├───────────────────────────▶│            │               │                │          │
 │         │              │             ├───────────▶│               │                │          │
 │         │              │             │ ④ nonce를 session에 저장  │                │          │
 │         │              │             │            ├──────────────▶│                │          │
 │         │              │             │            │ ticket, ssoId │                │          │
 │         │              │             │            │               │ ⑤ session nonce│          │
 │         │              │             │            │               ├───────────────▶│          │
 │         │              │             │            │               │ ⑥ 검증/세션설정│          │
 │         │              │             │            │               │◀───────────────┤          │
 │         │              │             │            │◀──────────────┤ 검증 결과       │          │
 │         │              │             │            │ ⑦ 성공 redirect/forward      │          │
 │         │              │             │            ├────────────────────────────────▶│
```

### 3.4 nonce 수명주기

```text
[생성] WsController
   │  SecureRandom 기반 고엔트로피 값
   ▼
[저장] 서버 Session(GemFire)
   │  sessionId와 결합, 짧은 TTL
   ▼
[전달/회수] SSO callback
   │  ticket 및 사용자 ID와 함께 검증
   ▼
[비교] constant-time 비교 권고
   │
   ├─ 불일치/만료/없음 → 인증 실패
   └─ 일치             → 즉시 소비(delete) → 재사용 차단
```

이미지는 nonce 검증을 명시하지만 일회성 소비, TTL, 난수 생성 방식은 표시하지 않는다. 이 세 항목은 실제 구현에서 반드시 보완되어야 한다.

---

## 4. 이미지 번호 기준 처리 단계

| 단계 | 주체 | 처리 | 입력 → 출력 |
|---:|---|---|---|
| 1 | 통합로그인/WEB | 사용자가 업무 시스템 접속 시도 | 로그인 상태 → 업무 접속 요청 |
| 2 | `WsController` | 요청을 받아 nonce 생성 | 접속 시도 → 일회성 nonce |
| 3 | 통합로그인 ActiveX/`check.jsp` | ticket과 ssoId를 발급받아 전달 | 인증 사용자 → ticket + ssoId |
| 4 | `process.jsp` | nonce를 session에 설정 | nonce → GemFire 분산 세션 |
| 5 | `NhSsoController` | session nonce와 body의 ticket·ssoId를 Service에 전달 | 세션값 + 요청값 → 검증 요청 |
| 6 | `NhSsoService` | ticket 복호화, nonce·ssoId 검증, 세션 속성 설정 | 인증 증표 → 인증된 사용자 세션 |
| 7 | Controller/`process.jsp` | 검증 결과 반환, 성공 시 메인화면 이동 | 성공/실패 → redirect 또는 오류 |

---

## 5. 구성요소별 책임

| 구성요소 | 핵심 책임 | 포함하면 안 되는 책임 |
|---|---|---|
| 통합로그인 | 사용자 인증, ticket 발급 | 업무 시스템 세션 직접 조작 |
| `WsController` | 로그인 handshake 시작, nonce 생성 | ticket 사용자 정보 무검증 신뢰 |
| `check.jsp` | 통합로그인 모듈 연계와 callback 데이터 수신 | 업무 권한 판정 |
| `process.jsp` | 서버 인증 API 호출, 결과에 따른 화면 전환 | 암호키 보관 및 ticket 직접 복호화 |
| `NhSsoController` | 요청/세션값 수집, Service 호출, 결과 반환 | 세부 암호 검증 로직 |
| `NhSsoService` | ticket·nonce·ssoId 검증, 인증 세션 생성 | 화면 navigation |
| GemFire Session | nonce와 사용자 로그인 상태 공유 | 영구 사용자 원장 역할 |

`JSP → Controller → Service`로 책임을 나눈 점은 타당하다. 다만 보안 검증의 최종 권위는 반드시 서버 측 Service에 있어야 하며 JSP/JavaScript 판정만으로 로그인을 확정해서는 안 된다.

---

## 6. 인증 및 세션 계약

### 6.1 Controller 요청 모델

```text
SsoValidationRequest
├─ ticket       : SSO가 발급한 불투명 인증 증표
├─ ssoId        : 외부 전달 사용자 ID(비교용)
└─ sessionNonce : HTTP 요청 body가 아니라 서버 Session에서 취득
```

nonce를 request body의 값만으로 검증하면 공격자가 ticket과 nonce를 함께 재전송할 수 있다. 비교 기준 nonce는 반드시 서버 세션에서 읽어야 한다.

### 6.2 ticket 검증 결과

```text
VerifiedSsoIdentity
├─ userId / ssoId
├─ issuer               발급 시스템
├─ audience             대상 업무 시스템
├─ issuedAt / expiresAt 발급·만료 시각
├─ nonce                로그인 시도 결합값
├─ ticketId             재사용 방지용 고유값
└─ attributes           조직·채널·권한 기초정보
```

이미지는 “ticket 복호화”라고 표현한다. 암호화만으로는 위·변조 방지가 보장되지 않으므로, 실제 계약에서는 전자서명 또는 인증 암호(AEAD/MAC) 검증이 필요하다.

### 6.3 세션 속성

```text
공통 권장 세션
├─ authenticated = true
├─ userId        = 검증된 ticket 내부 사용자
├─ loginYN       = Y
├─ ssoYN         = Y
├─ authTime      = 검증 완료 시각
├─ ticketIdHash  = ticket 원문 대신 추적용 해시
└─ sessionVersion / assuranceLevel

임시 nonce
└─ 검증 성공 또는 실패 후 즉시 제거
```

이미지에서 BI 세션에 `ticket` 원문을 저장하지만, ticket 탈취 위험 때문에 꼭 필요하지 않다면 저장하지 않는 것이 좋다. 필요하면 암호화 저장, 최소 TTL, 접근 통제를 적용한다.

---

## 7. 현재 저장소 구현과의 대응

### 7.1 원본 설계와 현행 구조 비교

| 원본 SSO 설계 | 현재 PDMG 코드 | 판단 |
|---|---|---|
| SSO ticket | Bearer JWT Access Token | 인증 증표가 다름 |
| nonce 생성·세션 저장 | 해당 흐름 미확인 | 구현 공백 |
| GemFire 분산 세션 | 업무 Security 설정은 stateless | 아키텍처 방식이 다름 |
| `NhSsoController` | 전용 Controller 미확인 | 구현 공백 |
| `NhSsoService` | `ServicePreventionInterceptor` + `JwtProvider` | 인증 검증 책임의 개념 대응 |
| ticket 복호화 | JWT RS 계열/JWKS 또는 HMAC 서명 검증 | 기술 방식이 다름 |
| ssoId 검증 | 검증된 JWT의 `userId` 또는 `sub` 추출 | 사용자 식별 개념 대응 |
| 로그인 세션 설정 | request attribute `ssoId`, ThreadContext `userId` | 요청 범위이며 세션이 아님 |
| 메인화면 redirect | 해당 SSO callback 흐름 미확인 | 구현 공백 |

### 7.2 현행 JWT 인증 텍스트 그림

```text
Client
  │ Authorization: Bearer <JWT>
  ▼
ServicePreventionInterceptor
  ├─ Bearer Header 존재 확인
  ├─ JwtProvider.validate(token)
  │    ├─ RS256/384/512 → JWKS 공개키 서명 검증
  │    ├─ 그 외         → HMAC key 서명 검증
  │    └─ exp 만료 검사
  ├─ JwtProvider.isAccessToken(token)
  │    └─ type == ACCESS 또는 구버전 RS256 조건
  ├─ JwtProvider.getSsoId(token)
  │    └─ userId claim 우선, 없으면 subject
  ├─ request.setAttribute("ssoId", ssoId)
  └─ ThreadContext.put("userId", ssoId)
        │
        ▼
   업무 요청 처리
```

### 7.3 코드 근거

| 파일·라인 | 확인 내용 |
|---|---|
| `pdmg-fw/.../commons/interceptor/ServicePreventionInterceptor.java:141-179` | Bearer 존재, JWT 유효성·Access Token 유형 검사, ssoId를 request와 ThreadContext에 저장 |
| `pdmg-fw/.../commons/jwt/JwtProvider.java:85-120` | JWT validate, `userId`/subject 기반 ssoId 추출, Access Token 판정 |
| `pdmg-fw/.../commons/jwt/JwtProvider.java:124-162` | JWKS RSA 또는 HMAC 서명 검증과 만료 검사 |
| `pdmg-fw/.../commons/context/ServiceContext.java:13-27` | 거래 문맥은 존재하지만 전용 ssoId 필드와 GemFire session 모델은 없음 |
| `pdmg-service/.../config/SecurityConfig.java:31` | Spring Security Session 정책이 `STATELESS`로 설정됨 |

> 현행 JWT 인증은 이미지의 티켓 기반 SSO를 그대로 구현한 것이 아니다. 별도의 SSO 로그인 handshake를 JWT 발급으로 대체한 후속 구조일 수 있으나, 이는 코드만으로 확정할 수 없다.

---

## 8. 사실·해석·구현 공백 구분

| 구분 | 내용 |
|---|---|
| 이미지에서 확인되는 사실 | nonce 생성·세션 저장, ticket/ssoId 전달, NhSsoService 검증, MH/BI별 세션 설정, 성공 시 메인 이동 |
| 보안상 합리적 해석 | nonce는 replay 방지용이며 ticket 내부 사용자와 외부 ssoId를 비교해야 함 |
| 이미지에서 불명확 | ticket 형식·알고리즘·TTL, nonce 전달 위치, 실패 응답, `toa` 의미, MH/BI 분기 기준 |
| 현재 코드에서 확인 | Bearer JWT 서명·만료·유형 검증, userId/sub 추출, request attribute와 ThreadContext 설정 |
| 현재 코드에서 미확인 | 원본 전용 Controller/Service/JSP, ActiveX, ticket 복호화, nonce 저장소, GemFire 세션, redirect |

---

## 9. 보안 위험과 개선 권고

| 위험 | 영향 | 권고 |
|---|---|---|
| nonce 재사용 | ticket replay로 세션 재생성 | nonce 일회 소비, 짧은 TTL, 세션 결합 |
| 예측 가능한 nonce | 인증 흐름 가로채기 | CSPRNG로 최소 128-bit 엔트로피 확보 |
| ticket 복호화만 수행 | 위·변조 탐지 실패 | 서명 또는 AEAD/MAC 검증 필수 |
| 요청 ssoId 신뢰 | 사용자 사칭 | ticket 내부 사용자 ID를 권위값으로 사용 |
| ticket 원문 세션 저장 | 세션 탈취 시 재사용 | 미저장 또는 암호화·해시·최소 TTL |
| URL query로 ticket 전달 | 로그·Referer·브라우저 이력 노출 | HTTPS POST body, SameSite cookie, no-store 적용 |
| 세션 고정 | 공격자 sessionId에 로그인 결합 | 성공 직후 session ID rotation |
| 분산 세션 경쟁 조건 | 같은 nonce의 동시 성공 | 원자적 get-and-delete/compare-and-delete |
| ActiveX 의존 | 브라우저 호환·보안 취약 | 표준 OIDC Authorization Code + PKCE로 전환 검토 |
| 성공 redirect 검증 미흡 | Open Redirect | 목적지 allowlist와 상대경로만 허용 |
| CSRF/로그인 CSRF | 공격자 계정으로 피해자 로그인 | nonce/state와 SameSite, Origin 검증 |
| 상세 오류 노출 | 계정·ticket 상태 추측 | 외부 오류는 단순화하고 내부 감사로그에 상세 기록 |

### 9.1 현재 JWT 구현에서 별도 확인할 항목

현행 `JwtProvider` 코드에서 서명과 `exp`는 확인되지만, 보이는 범위에서는 `iss`, `aud`, `nbf`, nonce/jti replay를 명시적으로 검사하지 않는다. 운영 보안 요구에 따라 다음 검증을 추가로 확인해야 한다.

- 허용 발급자(`iss`) 고정
- 대상 시스템(`aud`) 일치
- `nbf`, clock skew 정책
- 알고리즘 allowlist와 key ID 정책
- JWT ID(`jti`) 또는 refresh token 재사용 탐지
- 검증된 ssoId를 typed Principal/ServiceContext에 전달하는 기준

---

## 10. 권장 상태 전이

```text
[ANONYMOUS]
    │ 업무 시스템 접속
    ▼
[NONCE_ISSUED]
    │ session에 nonce 저장
    │ SSO ticket 수신
    ▼
[VALIDATING]
    ├─ nonce 없음/불일치/만료 ─▶ [REJECTED]
    ├─ ticket 무결성 실패      ─▶ [REJECTED]
    ├─ ticket 만료             ─▶ [REJECTED]
    ├─ ssoId 불일치            ─▶ [REJECTED]
    └─ 모든 검증 성공
             │ nonce 소비 + sessionId rotation
             ▼
       [AUTHENTICATED]
             │ 메인화면 이동
             ▼
          [ACTIVE]
```

검증 실패 후에도 nonce를 제거하여 무차별 대입과 재시도 창을 줄이는 정책을 권장한다. 사용자가 다시 시작할 때는 새 nonce를 발급한다.

---

## 11. 권장 API 계약

### 11.1 서버 내부 검증 인터페이스

```text
verifySso(ticket, requestSsoId, sessionNonce, targetSystem)
  1. ticket 형식/크기 검사
  2. 암호학적 무결성 검증 후 claims 복원
  3. issuer/audience/issuedAt/expiresAt 검증
  4. ticket nonce와 session nonce 비교
  5. ticket userId와 request ssoId 비교
  6. nonce와 ticketId 원자적 소비
  7. VerifiedSsoIdentity 반환
```

### 11.2 성공·실패 결과

```text
SsoValidationResult
├─ success          : true | false
├─ errorCode        : INVALID_TICKET | NONCE_MISMATCH | EXPIRED | ...
├─ identity         : 성공 시에만 존재
├─ correlationId    : 감사·장애 추적용
└─ redirectTarget   : 서버 allowlist에서 선택된 목적지
```

브라우저에는 내부 검증 상세 대신 일반화된 실패 메시지를 제공하고, correlation ID로 서버 로그를 조회하도록 구성하는 것이 안전하다.

---

## 12. 검증 시나리오

1. 정상 nonce·ticket·ssoId 조합으로 인증 세션이 생성되고 메인화면으로 이동하는지 확인한다.
2. 요청 ssoId를 변조했을 때 ticket 내부 사용자와 불일치하여 실패하는지 확인한다.
3. nonce 누락, 불일치, 만료 각각이 실패하는지 확인한다.
4. 한 번 성공한 nonce와 ticket을 재전송했을 때 차단되는지 확인한다.
5. 같은 nonce로 동시에 두 요청을 보내도 하나만 성공하는지 확인한다.
6. 만료·위조·잘못된 발급자·잘못된 대상 시스템 ticket을 거부하는지 확인한다.
7. 성공 직후 HTTP session ID가 교체되는지 확인한다.
8. PaaS 다중 인스턴스에서 GemFire 세션의 nonce와 로그인 상태가 일관되게 보이는지 확인한다.
9. MH와 BI 분기별 필수 세션 속성이 정확하며 다른 업무 속성이 섞이지 않는지 확인한다.
10. ticket, ssoId, nonce가 URL·로그·오류 응답에 평문 노출되지 않는지 확인한다.
11. 검증 실패 시 메인화면에 접근할 수 없고 nonce가 폐기되는지 확인한다.
12. redirect 목적지를 변조해 외부 사이트로 이동시킬 수 없는지 확인한다.

---

## 13. 최종 정리

원본 SSO 연계의 본질은 `nonce`로 로그인 시도를 서버 세션에 결합하고, 통합로그인의 `ticket`에서 확인한 사용자와 외부 `ssoId`를 교차 검증한 뒤에만 업무 세션을 생성하는 것이다. Controller는 흐름을 조정하고 Service는 암호학적 검증과 인증 사용자 확정을 담당한다.

현재 저장소의 인증 기반은 이와 다른 Bearer JWT 방식이다. JWT 서명·만료·Access Token 유형과 사용자 ID 추출은 구현되어 있지만, 원본 이미지의 nonce handshake, ticket callback, GemFire 로그인 세션, 전용 화면 전환 흐름은 확인되지 않는다. 따라서 원본 설계를 유지하려면 전용 SSO 모듈과 replay 방지 상태가 필요하고, 현행 JWT 방식으로 대체한다면 두 방식의 신뢰 경계와 세션 정책을 명시적으로 재정의해야 한다.

---

## 14. 관련 자료

- [온라인 프레임워크 구성 분석](./온라인_프레임워크_구성_분석.md)
- [GUID 관리 체계 분석](./GUID_관리_체계_분석.md)
- [거래 처리 구조 분석](./거래_처리_구조_분석.md)
- [INBOUND 거래 처리 아키텍처 분석](./INBOUND_거래_처리_아키텍처_분석.md)
- 저장소 참고 문서: `znsight-man/41-JWT-SSO-연계.md`

