# 메커니즘·아키텍처 표준 — 도메인 정의 및 URL 서비스 호출 구조 분석

## 1. 핵심 결론

이 장표는 업무 영역마다 개발·운영 도메인을 분리하고, URL 경로에 애플리케이션 코드와 서비스 코드를 결합해 온라인 서비스를 식별하는 표준을 정의한다.

```text
http://{업무도메인}.{환경}.nacf/{애플리케이션코드}/{서비스코드}
```

대표 예시는 다음과 같다.

```text
개발: http://mp.test.nacf/cm/{서비스코드}
운영: http://mp.prod.nacf/cm/{서비스코드}
```

핵심 구성 요소:

| 요소 | 예시 | 의미 |
|---|---|---|
| 업무 도메인 | `mp`, `ms`, `cr`, `pt`, `sb`, `oa` | 업무 영역 또는 솔루션 구분 |
| 환경 | `test`, `prod` | 개발·운영 환경 분리 |
| 조직 도메인 | `nacf` | 농협중앙회 내부 도메인 정책 |
| 애플리케이션 코드 | `cm`, `ic`, `pc` 등 | 업무 애플리케이션 식별 |
| 서비스 코드 | `{서비스코드}` | Controller 서비스 메서드 또는 거래 식별 |

이 구조는 DNS 이름으로 업무·환경을 구분하고 URL 경로로 애플리케이션과 서비스를 라우팅하는 **2단계 서비스 식별 체계**다.

---

## 2. 원본 장표 FACT 전사

### 2.1 업무 영역별 도메인

| 순번 | 업무 영역 | 개발 환경 | 운영 환경 | 원본 URL 예시 |
|---:|---|---|---|---|
| 1 | 미니 싱글뷰 | `ms.test.nacf` | `ms.prod.nacf` | `http://ms.prod.nacf/ms/{서비스코드}` |
| 2 | 마케팅 플랫폼 | `mp.test.nacf` | `mp.prod.nacf` | 루트 및 애플리케이션별 서비스 URL |
| 3 | 신용 실적(BI포털) | `cr.test.nacf` | `cr.prod.nacf` | `http://cr.prod.nacf/`, `http://cr.prod.nacf/cr/{서비스코드}` |
| 4 | BI 포털(업무 솔루션) | `pt.test.nacf` | `pt.prod.nacf` | `http://pt.prod.nacf/{솔루션 경로}` |
| 5 | Self-BI(업무 솔루션) | `sb.test.nacf` | `sb.prod.nacf` | 장표 비고: `http://ms.prod.nacf/{솔루션 경로}` |
| 6 | OLAP(업무 솔루션) | `oa.test.nacf` | `oa.prod.nacf` | 장표 비고: `http://ms.prod.nacf/ms/{솔루션 경로}` |

### 2.2 마케팅 플랫폼 URL 목록

원본 장표에 표시된 운영 URL은 다음과 같다.

```text
http://mp.prod.nacf/
http://mp.prod.nacf/ic/{서비스코드}
http://mp.prod.nacf/pc/{서비스코드}
http://mp.prod.nacf/bc/{서비스코드}
http://mp.prod.nacf/sa/{서비스코드}
http://mp.prod.nacf/pd/{서비스코드}
http://mp.prod.nacf/cm/{서비스코드}
http://mp.prod.nacf/eb/{서비스코드}
http://mp.prod.nacf/ss/{서비스코드}
http://mp.prod.nacf/cs/{서비스코드}
http://mp.prod.nacf/ct/{서비스코드}
http://mp.prod.nacf/mg/{서비스코드}
```

### 2.3 도메인 정책 근거

장표 하단에는 다음 내용이 기재되어 있다.

- 도메인 정책: 농협중앙회 `nacf`
- 도메인 정책 참고자료: `dns_gslb_신청_매뉴얼_v1.5_240312.hwp`

---

## 3. 도메인 명명 구조

### 3.1 표준 형식

```text
{business}.{environment}.nacf
```

```text
┌──────────┬─────────────┬──────────┐
│ business │ environment │ org/tld  │
├──────────┼─────────────┼──────────┤
│ mp       │ prod        │ nacf     │
└──────────┴─────────────┴──────────┘
```

### 3.2 업무 코드

| 코드 | 업무 영역 |
|---|---|
| `ms` | 미니 싱글뷰 |
| `mp` | 마케팅 플랫폼 |
| `cr` | 신용 실적 |
| `pt` | BI 포털 |
| `sb` | Self-BI |
| `oa` | OLAP |

### 3.3 환경 코드

| 코드 | 환경 | 용도 |
|---|---|---|
| `test` | 개발·시험 | 개발, 통합시험, 기능 검증 |
| `prod` | 운영 | 실사용자 서비스 |

장표에는 개발과 운영만 정의되어 있다. 로컬, 단위시험, 스테이징, DR 환경의 별도 코드가 필요한지는 추가 확인이 필요하다.

---

## 4. URL 서비스 식별 구조

### 4.1 프레임워크 업무 서비스

```text
http://{domain}/{applicationCode}/{serviceCode}
```

예:

```text
http://mp.prod.nacf/cm/mpcm0001
```

해석:

```text
mp.prod.nacf  → 마케팅플랫폼 운영 도메인
cm            → 캠페인 애플리케이션
mpcm0001      → 개별 서비스 ID
```

### 4.2 솔루션 서비스

BI 포털, Self-BI, OLAP은 `{서비스코드}`보다 `{솔루션 경로}`를 사용한다.

```text
http://pt.prod.nacf/{솔루션 경로}
http://sb.prod.nacf/{솔루션 경로}  # 도메인 표 기준 권장 해석
http://oa.prod.nacf/{솔루션 경로}  # 도메인 표 기준 권장 해석
```

솔루션이 자체 URL 체계를 갖기 때문에 온라인 프레임워크의 서비스 ID 라우팅과 구분한 것으로 해석된다.

### 4.3 루트 URL

`http://mp.prod.nacf/`와 `http://cr.prod.nacf/`가 별도로 제시된다. 루트는 다음 용도 중 하나일 수 있다.

- 포털 또는 기본 화면
- 헬스체크·상태 페이지
- 기본 컨텍스트 리다이렉트
- 애플리케이션 목록 또는 안내 페이지

운영 보안을 위해 루트에서 서버 정보나 디렉터리 목록을 노출해서는 안 된다.

---

## 5. 서비스 호출 및 라우팅 메커니즘

```text
Client / 정보단말 / 타 시스템
        │
        │ 1. URL 요청
        ▼
DNS / GSLB
        │  업무·환경 도메인 해석
        ▼
L4 / Load Balancer / Reverse Proxy
        │  Host 기반 가상호스트 선택
        ▼
WEB / API Gateway
        │  첫 번째 Path Segment로 애플리케이션 선택
        ▼
NH Cloud Framework Dispatcher
        │  두 번째 Path Segment로 서비스 ID 식별
        ▼
Controller.method
        ▼
Service → DAO → Database
        │
        ▼
표준 응답
```

### 5.1 1단계 — DNS/GSLB

`mp.prod.nacf` 같은 FQDN을 운영 VIP 또는 서비스 엔드포인트로 변환한다. GSLB를 적용하면 센터·사이트 단위 장애 전환과 상태 기반 라우팅이 가능하다.

### 5.2 2단계 — Host 기반 라우팅

WEB, Reverse Proxy 또는 API Gateway가 HTTP `Host` 헤더를 기준으로 마케팅플랫폼, 미니 싱글뷰, BI포털 등의 가상호스트를 선택한다.

### 5.3 3단계 — 애플리케이션 코드 라우팅

첫 번째 경로 세그먼트인 `/cm`, `/pc`, `/mg` 등으로 애플리케이션을 결정한다.

### 5.4 4단계 — 서비스 코드 라우팅

`/{서비스코드}`를 Controller 메서드 또는 프레임워크 서비스 ID에 매핑한다.

```text
URL /cm/mpcm0001
  → application=CM
  → serviceId=MPCM0001
  → CampaignController.mpcm0001(...)
```

서비스 ID–Controller 매핑은 기동 시 레지스트리에 등록하고 중복 ID를 차단해야 한다.

---

## 6. 마케팅플랫폼 애플리케이션 경로 분석

장표는 마케팅플랫폼 아래 여러 애플리케이션 코드를 제공한다.

| 경로 | 추정 업무 | 비고 |
|---|---|---|
| `/ic` | 통합고객 | 업무 코드 해석은 별도 정의서 확인 필요 |
| `/pc` | 개인고객 | 〃 |
| `/bc` | 기업고객 | 〃 |
| `/sa` | 상담판매 | 〃 |
| `/pd` | 통합상품 | 〃 |
| `/cm` | 캠페인 | 앞선 패키지 표준의 `CM`과 일치 |
| `/eb` | EBM | 〃 |
| `/ss` | 영업지원 | 〃 |
| `/cs` | CS | 〃 |
| `/ct` | 콘텐츠 | 〃 |
| `/mg` | 메시지 | 앞선 호출 구조의 `MG`와 일치 |

경로 코드는 Java 패키지, 서비스 ID, 로그 필드와 동일한 업무 분류 체계를 공유하는 것이 바람직하다.

```text
URL:     /cm/mpcm0001
Package: nhnis.mp.cm...
Service: mpcm0001
Log:     application=CM, serviceId=MPCM0001
```

---

## 7. 개발·운영 환경 분리

### 7.1 분리 원칙

- 개발 클라이언트는 `.test.nacf`만 호출한다.
- 운영 클라이언트는 `.prod.nacf`만 호출한다.
- DNS, 인증서, VIP, 방화벽과 백엔드 풀을 환경별로 분리한다.
- 운영 데이터와 비밀정보를 개발 환경에 복제하지 않는다.
- 환경별 설정은 코드가 아니라 외부 설정·Secret으로 관리한다.

### 7.2 환경 오호출 방지

- 운영 애플리케이션에서 `.test.nacf` outbound를 차단한다.
- 개발 계정과 운영 계정을 분리한다.
- CI/CD 단계에서 환경별 endpoint allowlist를 검증한다.
- 응답 헤더와 로그에 실행 환경을 기록한다.
- 화면에 비운영 환경 배너를 표시한다.

---

## 8. URL 표준 권고

### 8.1 프로토콜

장표 예시는 `http://`이지만 운영 환경은 원칙적으로 `https://`를 사용해야 한다.

```text
https://mp.prod.nacf/cm/{서비스코드}
```

- TLS 1.2 이상
- 내부 PKI 또는 승인 인증서
- HTTP→HTTPS 강제 리다이렉트 또는 HTTP 포트 차단
- 서비스 간 TLS 적용 범위 명시

### 8.2 문자와 형식

- 도메인과 경로 코드는 소문자를 사용한다.
- 경로 끝 `/` 적용 여부를 통일한다.
- 공백, 한글, 파일 확장자를 서비스 URL에 사용하지 않는다.
- 서비스 코드는 불변 식별자로 관리한다.
- 의미 변경 시 기존 서비스 코드 재사용보다 신규 코드를 발급한다.

### 8.3 HTTP 메서드

장표는 URL만 정의하므로 HTTP 메서드 표준을 보완해야 한다.

| 작업 | 권장 메서드 |
|---|---|
| 조회 | `GET` 또는 표준 거래 `POST` |
| 생성 | `POST` |
| 전체 변경 | `PUT` |
| 부분 변경 | `PATCH` |
| 삭제 | `DELETE` |

기존 프레임워크가 모든 거래를 `POST`로 처리한다면 서비스 메타데이터에 조회/변경 속성과 멱등성을 별도로 선언한다.

### 8.4 쿼리와 경로 변수

- 리소스 식별자는 path parameter를 사용한다.
- 검색·필터·페이징은 query parameter를 사용한다.
- 개인정보와 인증정보를 URL query에 넣지 않는다.
- 대형 또는 민감 입력은 request body를 사용한다.

---

## 9. 서비스 코드 관리

### 9.1 권장 등록 항목

| 항목 | 설명 |
|---|---|
| 서비스 ID | 전역 또는 애플리케이션 내 고유 식별자 |
| 애플리케이션 그룹 | `MP`, `BL` 등 |
| 애플리케이션 코드 | `CM`, `MG`, `CR` 등 |
| Controller 메서드 | 실제 호출 대상 |
| 입력·출력 계약 | DTO/JSON Schema |
| HTTP 메서드 | `GET`, `POST` 등 |
| 인증·권한 | 요구 정책 |
| 타임아웃 | 최대 처리시간 |
| 트랜잭션 속성 | 조회/변경, read-only 여부 |
| 소유팀 | 운영·장애 대응 주체 |
| 상태 | 개발, 운영, 폐기 예정, 폐기 |

### 9.2 중복 및 충돌 방지

- 애플리케이션 내 서비스 ID 중복을 빌드 시 차단한다.
- 동일 URL에 복수 Controller가 매핑되지 않게 한다.
- 대소문자 차이만 있는 경로를 금지한다.
- 신규 URL과 기존 Rewrite 규칙의 충돌을 배포 전 검사한다.

---

## 10. 보안 구조

```text
Client
  → TLS
  → WAF/API Gateway
     ├─ 인증
     ├─ 인가
     ├─ Rate Limit
     ├─ 입력 크기 제한
     └─ 감사 로그
  → Framework System Preprocessing
     ├─ 서비스 ID 검증
     ├─ 사용자·채널 확인
     └─ 객체 수준 권한 검증
```

필수 기준:

- 알려지지 않은 Host 헤더 차단
- 허용된 애플리케이션·서비스 코드만 라우팅
- URL path traversal과 이중 인코딩 차단
- 서비스별 인증·권한과 호출자 allowlist
- Rate Limit 및 동시 처리량 제한
- 오류 응답에서 내부 IP, 클래스, SQL 정보 제거
- 개인정보가 포함된 path/query 로그 마스킹

---

## 11. 장애 전환과 GSLB

장표가 DNS/GSLB 신청 매뉴얼을 참조하므로 도메인 운영에는 다음 정책이 필요하다.

### 정상 경로

```text
mp.prod.nacf
  → GSLB
    → 주센터 VIP
      → WEB/WAS Pool
```

### 장애 경로

```text
주센터 Health Check 실패
  → GSLB 정책 전환
    → DR센터 VIP
      → DR WEB/WAS Pool
```

운영 고려사항:

- DNS TTL과 전환 목표시간
- GSLB 헬스체크 URL의 업무 의존성
- 주·DR 인증서 및 DNS 이름 일치
- 세션과 캐시 데이터 복구 정책
- DB 전환 완료 전 애플리케이션 유입 차단
- 복귀(failback) 절차와 데이터 정합성 검증

단순 프로세스 생존 확인보다 DB·필수 의존성까지 확인하는 readiness endpoint를 사용하되, 과도한 의존성 때문에 전체 서비스가 불필요하게 제외되지 않도록 설계한다.

---

## 12. 관측성 및 로그

모든 요청에 다음 정보를 연결한다.

```text
traceId
host/domain
environment
applicationGroup
applicationCode
serviceId
httpMethod
statusCode/resultCode
elapsedMs
caller/channel
backendInstance
```

핵심 메트릭:

- 도메인·애플리케이션·서비스 ID별 TPS
- HTTP 상태 및 업무 결과 코드별 오류율
- p50/p95/p99 응답시간
- GSLB/LB 백엔드 상태와 전환 이력
- 잘못된 Host·경로·서비스 ID 요청 건수
- 인증 실패, Rate Limit, 타임아웃 건수

---

## 13. 캐시와 URL 변경

- DNS 변경 전 TTL을 계획적으로 낮춘다.
- 서비스 URL 변경 시 구 URL의 유지 기간을 정의한다.
- 영구 이전은 `301/308`, 임시 이전은 `302/307`을 목적에 맞게 사용한다.
- 거래성 `POST` 요청을 무분별하게 리다이렉트하지 않는다.
- 클라이언트에 도메인을 하드코딩하지 않고 환경 설정 또는 서비스 디스커버리를 사용한다.
- API Gateway Rewrite를 사용하면 외부 URL과 내부 라우팅 경로를 문서화한다.

---

## 14. 테스트 전략

| 테스트 | 검증 내용 |
|---|---|
| DNS 테스트 | test/prod 도메인이 올바른 VIP로 해석되는지 |
| Host 라우팅 테스트 | 각 업무 도메인이 올바른 WEB/WAS 풀로 가는지 |
| Path 라우팅 테스트 | `/cm/{서비스코드}`가 정확한 Controller로 가는지 |
| 중복 매핑 테스트 | 서비스 ID와 URL 충돌 여부 |
| 계약 테스트 | 입력·출력 DTO, HTTP 상태, 업무 오류코드 |
| 환경 격리 테스트 | 운영→개발, 개발→운영 오호출 차단 |
| 보안 테스트 | Host Header Injection, Path Traversal, 인증 우회 |
| 성능 테스트 | 도메인·서비스별 처리량과 지연시간 |
| 장애 전환 테스트 | GSLB 주→DR 전환과 복귀 |
| 인증서 테스트 | SAN, 만료일, 체인, TLS 버전 |

---

## 15. 표기 불일치 및 위험 분석

### 15.1 Self-BI URL 불일치

도메인 열에는 다음이 정의되어 있다.

```text
개발: sb.test.nacf
운영: sb.prod.nacf
```

그러나 비고 URL은 `http://ms.prod.nacf/{솔루션 경로}`로 보인다. 가능한 원인은 다음과 같다.

1. Self-BI가 실제로 미니 싱글뷰 도메인 아래 프록시된다.
2. 장표 복사 과정에서 URL이 수정되지 않았다.
3. `sb.prod.nacf`는 별칭이고 실제 대표 URL은 `ms.prod.nacf`다.

### 15.2 OLAP URL 불일치

도메인 열은 `oa.test.nacf`, `oa.prod.nacf`인데 비고는 `http://ms.prod.nacf/ms/{솔루션 경로}`로 보인다. 이 역시 운영 라우팅 정책 또는 문서 오류인지 확인해야 한다.

### 15.3 HTTP 사용

모든 예시가 `http://`다. 내부망이라도 인증정보와 개인정보가 오갈 수 있으므로 실제 운영 프로토콜이 HTTPS인지 확인하고, HTTP가 현행이라면 TLS 전환 계획이 필요하다.

### 15.4 환경 명칭

`test`가 개발만 의미하는지 통합·인수시험까지 포함하는지 불명확하다. 다수 시험 환경이 필요하면 `dev`, `sit`, `uat` 등의 추가 정책을 정의한다.

---

## 16. 운영·개발 체크리스트

### 도메인 신청

- [ ] 업무 영역 코드가 승인되었는가?
- [ ] 개발·운영·DR 도메인이 구분되어 있는가?
- [ ] GSLB/VIP와 헬스체크가 정의되었는가?
- [ ] DNS TTL과 장애 전환 정책이 정의되었는가?
- [ ] TLS 인증서 발급 범위에 도메인이 포함되는가?

### 서비스 등록

- [ ] 애플리케이션 코드와 서비스 ID가 고유한가?
- [ ] Controller 메서드와 1:1로 매핑되는가?
- [ ] 입력·출력 계약과 오류코드가 등록되었는가?
- [ ] 인증·권한·타임아웃·Rate Limit이 정해졌는가?
- [ ] 소유팀과 장애 연락처가 지정되었는가?

### 배포

- [ ] URL Rewrite와 라우팅 충돌 검사를 통과했는가?
- [ ] 개발 endpoint가 운영 설정에 남아 있지 않은가?
- [ ] 헬스체크와 readiness가 정상인가?
- [ ] 로그·메트릭·추적이 수집되는가?
- [ ] 구 버전 URL의 호환·폐기 계획이 있는가?

---

## 17. FACT·ANALYSIS·확인 필요

### FACT — 그림에서 직접 확인되는 내용

- 미니 싱글뷰, 마케팅플랫폼, 신용실적, BI포털, Self-BI, OLAP의 개발·운영 도메인이 정의되어 있다.
- 개발 환경은 `test`, 운영 환경은 `prod`를 사용한다.
- 공통 조직 도메인은 `nacf`다.
- 온라인 프레임워크 URL은 애플리케이션 코드와 서비스 코드를 경로에 사용한다.
- 마케팅플랫폼은 `/ic`, `/pc`, `/bc`, `/sa`, `/pd`, `/cm`, `/eb`, `/ss`, `/cs`, `/ct`, `/mg` 경로를 제시한다.
- 솔루션 영역은 `{솔루션 경로}`를 사용한다.
- DNS/GSLB 신청 매뉴얼이 정책 근거로 제시된다.

### ANALYSIS — 구조로부터 도출한 해석

- 도메인이 업무 영역과 환경을, URL 경로가 애플리케이션과 서비스를 식별한다.
- DNS/GSLB→WEB/API Gateway→Framework Dispatcher의 다단계 라우팅 구조로 해석할 수 있다.
- 서비스 ID는 Controller 메서드, 패키지 코드, 로그 추적 키와 연결하는 것이 적합하다.
- 솔루션 URL은 프레임워크 서비스 ID보다 솔루션 자체 라우팅 체계를 따른다.

### 확인 필요

- Self-BI와 OLAP 비고 URL의 `ms.prod.nacf` 표기가 의도된 프록시 구성인지 문서 오류인지
- 운영 환경의 실제 프로토콜이 HTTP인지 HTTPS인지
- 마케팅플랫폼 각 경로 코드의 공식 한글 업무명
- 서비스 코드 형식, 길이, 대소문자, 전역 고유성 규칙
- 루트 URL의 역할과 외부 공개 여부
- DR 도메인 또는 GSLB 장애 전환 시 동일 FQDN 유지 여부
- `test` 외 개발·SIT·UAT 환경 구분 방식
- URL 버전(`/v1`) 적용 여부와 폐기 정책

---

## 18. 최종 평가

이 도메인·URL 표준은 **업무 영역과 환경을 DNS에, 애플리케이션과 거래를 URL 경로에 배치하는 계층형 서비스 주소 체계**다. 주소만 보아도 어느 업무, 어느 환경, 어느 애플리케이션, 어느 서비스인지 식별할 수 있어 운영 라우팅과 로그 분석, 장애 격리에 유리하다.

표준의 완성도를 높이려면 HTTPS, 서비스 ID 레지스트리, 환경 격리, API 버전, GSLB 장애 전환, URL 불일치 정정이 함께 관리되어야 한다. 특히 Self-BI와 OLAP의 도메인 열과 비고 URL 차이는 실제 구축 전에 반드시 확정해야 한다.
