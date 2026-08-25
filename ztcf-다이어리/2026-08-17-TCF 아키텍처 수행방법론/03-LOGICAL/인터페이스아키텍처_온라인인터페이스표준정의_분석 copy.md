# 인터페이스 아키텍처 — 온라인 인터페이스 표준 정의 분석

## 1. 핵심 결론

장표는 정보계 온라인 인터페이스를 **호출 주체와 대상 영역에 따라 서로 다른 표준 중계 시스템으로 라우팅**한다.

| 연계 범위 | 표준 중계 | 핵심 원칙 |
|---|---|---|
| 대면 채널 → 정보계 | 영업점 MCA | 대면 채널 전문 표준 적용 |
| 비대면 채널 → 정보계 | MCI | 비대면 채널 전문 표준 적용 |
| 정보계 ↔ 대내 시스템 | API G/W(Cruz APIM) | 양방향 전사 표준 API 진입점 |
| 정보계 ↔ 대외 기관 | API G/W | 양방향 대외 전문 연계 표준 |
| 타 법인(은행) → 정보계 | GSE | 법인 간 전사 표준 경로 |
| 정보계 단말 → 정보계 | 중계 없음 | 대면 단말의 직접 거래 처리 |
| 패키지 UI → 정보계 솔루션 | 중계 없음 | 패키지 UI의 직접 거래 처리 |

```text
대면 채널 ── 영업점 MCA ───────────────> 정보계
비대면 채널 ── MCI ────────────────────> 정보계
정보계 단말 ── 직접 ───────────────────> 정보계
패키지 UI ── 직접 ─────────────────────> 정보계 솔루션

정보계 ───── API G/W(Cruz APIM) ───────> 대내 시스템
대내 시스템 ─ API G/W(Cruz APIM) ──────> 정보계
정보계 ───── API G/W ──────────────────> 대외 기관
대외 기관 ─── API G/W ─────────────────> 정보계
타 법인(은행) ─ GSE ───────────────────> 정보계
```

핵심은 모든 경로를 단일 제품으로 강제하는 것이 아니라 **채널·대내·대외·법인 간 도메인의 기존 표준 진입점을 유지하면서 정보계 내부 계약과 추적 기준을 일관되게 적용**하는 것이다.

## 2. 장표 원문 전사

| No. | 구분 | 소스 | 인터페이스 시스템 | 타깃 | 설명 |
|---:|---|---|---|---|---|
| 1 | 온라인 | 통합업무시스템 | 영업점 MCA | 정보계 | 대면 채널 전사 표준 인터페이스 |
| 2 | 온라인 | 비대면 채널 | MCI | 정보계 | 비대면 채널 전사 표준 인터페이스 |
| 3 | 온라인 | 정보계 단말 | 없음 | 정보계 | 정보계 대면 채널 인터페이스, 인터페이스 시스템 경유 없이 거래 처리 |
| 4 | 온라인 | 패키지 UI | 없음 | 정보계 솔루션 | 정보계 패키지 솔루션 UI 인터페이스, 인터페이스 시스템 경유 없이 거래 처리 |
| 5 | 온라인 | 정보계 | API G/W | 대내 시스템 | 정보계와 대내 시스템 간 전사 표준 인터페이스는 API G/W(Cruz APIM)를 경유 |
| 6 | 온라인 | 대내 시스템 | API G/W | 정보계 | 대내 시스템과 정보계 간 전사 표준 인터페이스는 API G/W(Cruz APIM)를 경유 |
| 7 | 온라인 | 정보계 | API G/W | 대외 기관 | 대외 기관 전문 연계 전사 표준 인터페이스 |
| 8 | 온라인 | 대외 기관 | API G/W | 정보계 | 대외 기관 전문 연계 전사 표준 인터페이스 |
| 9 | 온라인 | 타 법인 | GSE | 정보계 | 타 법인(은행)과 정보계 간 전사 표준 인터페이스는 GSE를 경유 |

> MCA, MCI, GSE의 약어 확장과 제품 내부 상세는 장표만으로 확정하지 않는다. 본 문서는 장표가 제시한 논리적 인터페이스 역할과 경로를 기준으로 분석한다.

## 3. 경로 분류 모델

### 3.1 채널 인바운드

```text
통합업무시스템 → 영업점 MCA → 정보계
비대면 채널     → MCI       → 정보계
정보계 단말     → Direct    → 정보계
패키지 UI       → Direct    → 정보계 솔루션
```

대면·비대면 채널은 각각 기존 채널 통합 시스템을 경유한다. 정보계 전용 단말과 패키지 UI는 인터페이스 시스템을 경유하지 않으므로 대상 애플리케이션이 인증·인가·입력검증·Rate Limit·감사·오류 처리를 직접 제공해야 한다.

### 3.2 대내 시스템 양방향

```text
정보계 ── API G/W ──> 대내 시스템
정보계 <── API G/W ── 대내 시스템
```

호출 방향과 무관하게 API Gateway를 공통 진입점으로 사용한다. 소비자는 타깃 서버의 물리 URL이 아니라 Gateway에 등록된 논리 API를 호출해야 한다.

### 3.3 대외 기관 양방향

```text
정보계 ── API G/W ──> 대외 기관
정보계 <── API G/W ── 대외 기관
```

대외 연계는 내부 API보다 네트워크 경계, 기관별 전문, 인증서, 암호화, 타임아웃, 재전송 및 대사 요건이 강하다. API Gateway 경유만으로 모든 대외 요건이 충족되는 것은 아니며 DMZ, 대외계, EAI 등 실제 내부 경로를 별도 설계해야 한다.

### 3.4 타 법인 인바운드

```text
타 법인(은행) → GSE → 정보계
```

장표는 타 법인 연계를 GSE로 분리한다. 타 법인 코드, 호출 권한, 전문 버전과 장애 책임 경계를 정보계 API와 별도 계약으로 관리해야 한다.

## 4. 전체 온라인 논리 아키텍처

```text
┌──────────────────────────── 채널 영역 ────────────────────────────┐
│ 통합업무시스템 ──> 영업점 MCA ─┐                                 │
│ 비대면 채널 ─────> MCI ────────┼──────┐                          │
│ 정보계 단말 ─────> Direct ─────┘      │                          │
└────────────────────────────────────────│──────────────────────────┘
                                         v
                              ┌─────────────────────┐
                              │       정보계        │
                              │ Online AP / Service │
                              └─────────────────────┘
                               ^       ^        │
                               │       │        v
┌──────── 패키지 영역 ──────┐  │       │  ┌──────────────┐
│ 패키지 UI ──> 정보계 솔루션├──┘       └──│ API G/W      │<──> 대내 시스템
└────────────────────────────┘             │ Cruz APIM    │<──> 대외 기관
                                           └──────────────┘
                                                   ^
타 법인(은행) ───────────────> GSE ────────────────┘ 또는 정보계 진입
```

GSE와 API Gateway의 실제 연결 순서는 장표에 직접 나타나지 않으므로 구축 설계서에서 확정해야 한다. 텍스트 구성도는 논리 경계를 표현한 것이다.

## 5. 영업점 MCA 경유 대면 채널

### 역할

- 통합업무시스템의 대면 채널 전문을 정보계로 전달한다.
- 채널별 인증·세션·전문 헤더·라우팅을 전사 기준으로 통제한다.
- 정보계 서비스의 물리 주소 변경을 채널로부터 숨긴다.

### 표준 요구사항

- 대면 채널 전문의 Header, 서비스 ID, GUID, 사용자·점포 정보를 표준화한다.
- MCA와 정보계 간 Timeout, 최대 전문 크기, 문자셋, 오류 코드 매핑을 정의한다.
- 중복 전송 시 업무 결과가 중복 생성되지 않도록 멱등성 기준을 둔다.
- 점포·사용자 권한은 정보계에서 최종 재검증한다.
- MCA 장애 시 우회 경로를 임의로 만들지 않고 승인된 복구 절차를 사용한다.

## 6. MCI 경유 비대면 채널

### 역할

- 인터넷·모바일 등 비대면 채널의 전사 표준 인터페이스를 제공한다.
- 채널 인증, 서비스 라우팅, 트래픽 제어와 전문 변환의 공통 지점을 형성한다.

### 표준 요구사항

- 모바일·인터넷 채널의 인증 토큰과 고객 식별정보 전달 규칙을 표준화한다.
- 비대면 트래픽 급증에 대비하여 Rate Limit, Queue, Circuit Breaker를 적용한다.
- 재시도 가능한 조회와 재시도 위험이 있는 거래 명령을 구분한다.
- 채널 장애·네트워크 단절 후 재호출에 대비한 Idempotency Key를 적용한다.
- 개인정보와 인증정보를 로그에서 마스킹한다.

## 7. 정보계 단말 직접 인터페이스

장표는 정보계 단말이 별도의 인터페이스 시스템 없이 정보계와 직접 거래한다고 정의한다.

```text
정보계 단말 → L4/WEB 또는 승인된 엔드포인트 → 정보계 AP
```

### 직접 연결의 의미

- 중계 제품이 없다는 뜻이지 계약·통제가 없다는 뜻이 아니다.
- 정보계 자체가 인증·인가, 전문 검증, 오류 표준, 거래 추적과 부하 제어를 담당한다.
- 단말이 AP/DB 서버의 물리 주소나 DB에 직접 접속해서는 안 된다.

### 보완 통제

- 단말 인증서 또는 관리형 Device Identity
- 사용자 SSO/MFA와 업무 권한 확인
- L4/WEB 단일 진입점과 Source Network 제한
- TLS, Session Timeout, 화면·API 버전 호환성
- GUID 및 사용자·단말·화면 ID 감사 로그
- 단말 버전 강제 업데이트와 취약 버전 차단

## 8. 패키지 UI 직접 인터페이스

패키지 UI는 정보계 솔루션과 직접 통신한다.

```text
패키지 UI → 패키지 WEB/WAS Endpoint → 정보계 솔루션
```

### 설계 원칙

- 패키지 UI를 DB에 직접 연결하지 않고 제품의 공식 서비스 계층을 사용한다.
- UI와 서버 버전 호환성, Session, 인증·인가, 라이선스 및 브라우저/클라이언트 요구사항을 관리한다.
- 패키지 고유 프로토콜을 다른 정보계 시스템의 표준으로 확산하지 않는다.
- 외부 시스템과 연계할 때는 패키지 내부 API를 API Gateway 뒤에 Adapter로 노출한다.
- 제품 업그레이드 시 직접 인터페이스의 영향 범위와 회귀시험을 수행한다.

## 9. API Gateway(Cruz APIM) 표준

API Gateway는 정보계와 대내·대외 시스템 간 양방향 전사 표준 진입점이다.

```text
Consumer
  → DNS/VIP
    → API Gateway
      ├─ TLS/mTLS 종료
      ├─ 인증·인가
      ├─ Consumer/App 식별
      ├─ Rate Limit·Quota
      ├─ GUID·Access Log
      ├─ Routing·Version
      ├─ Schema/Size 기본 검증
      └─ Target 또는 내부 EAI 연계
```

### Gateway에 둘 항목

- 공통 인증·인가와 인증서 검증
- Route, API Version, Consumer별 정책
- Rate Limit, Quota, IP/Network 정책
- 기본 Schema·Payload 크기 검증
- GUID 발급·전달, 접근 로그와 지표
- 표준 오류 변환과 보안 Header

### Gateway에 과도하게 두지 않을 항목

- 복잡한 업무 의사결정
- 장기 상태·업무 트랜잭션
- 대규모 데이터 가공
- 제품 고유 DB 직접 접근
- 소비자별 임시 변환 로직의 무제한 누적

업무 로직은 정보계 AP 또는 책임 있는 EAI/Adapter 계층에 둔다.

## 10. GSE 경유 타 법인 연계

장표는 타 법인, 특히 은행과 정보계 간 전사 표준 경로로 GSE를 제시한다.

### 계약 항목

- 송신·수신 법인 코드와 시스템 코드
- 서비스·전문 ID, 방향, 버전, 개시일
- 인증서, 네트워크 Zone, 암호화와 키 소유자
- Timeout, Retry, 거래 중복 방지와 취소·보상
- 운영 시간, SLA, 장애 연락망과 책임 경계
- GUID 또는 법인 간 Correlation ID 매핑
- 오류 코드와 대사·재처리 절차

GSE의 정확한 기능과 API Gateway와의 내부 연결 관계는 제품·네트워크 설계서로 보완해야 한다.

## 11. 공통 온라인 전문 표준

이전 정보계 표준에서 정의한 JSON/HTTP 원칙을 모든 적용 가능 경로에 사용하되, MCA·MCI·GSE의 기존 전사 전문이 다른 형식이면 경계 Adapter에서 변환한다.

### 권장 Header

```json
{
  "header": {
    "guid": "<GUID>",
    "correlationId": "<end-to-end-id>",
    "serviceId": "<service-id>",
    "interfaceId": "<interface-id>",
    "sourceSystem": "<source-code>",
    "targetSystem": "<target-code>",
    "channelType": "BRANCH|NON_FACE|TERMINAL|PACKAGE|INTERNAL|EXTERNAL|AFFILIATE",
    "requestTimestamp": "<ISO-8601>",
    "schemaVersion": "1.0"
  },
  "body": {}
}
```

### 표준 계약 항목

- 문자셋, 날짜·시간·Timezone, 숫자·금액·Boolean 규칙
- 필수·선택·Null·빈 문자열 규칙
- 최대 전문 크기와 압축 기준
- 요청·응답·단방향 여부
- HTTP Method·Status 또는 전사 응답 코드
- 업무 오류와 시스템 오류 분리
- 인증·인가·개인정보·마스킹
- Timeout·Retry·멱등성·보상
- API/전문 Schema Version과 호환성

## 12. GUID와 종단 추적

```text
Source
  → MCA/MCI/Direct/API G/W/GSE
    → 정보계 WEB/WAS
      → 하위 API/DB/Event
        → Response

동일 GUID 또는 매핑된 Correlation ID를 전 구간 보존
```

- 원천 GUID가 있으면 보존하고 없으면 최초 정보계 진입점에서 생성한다.
- 각 중계 시스템의 고유 거래 ID와 GUID의 매핑을 저장한다.
- GUID를 인증 수단이나 업무 중복키로 혼용하지 않는다.
- 로그에는 GUID, Interface ID, 서비스 ID, 소스·타깃, 결과, 지연을 남긴다.
- 개인정보가 포함된 전문 전체 로깅은 금지하거나 필드 단위 마스킹한다.

## 13. 호출 복원력 표준

| 항목 | 적용 원칙 |
|---|---|
| Timeout | 홉별 Timeout 합이 사용자 응답 SLA를 넘지 않게 예산화 |
| Retry | 멱등 요청만, 제한 횟수·Backoff·Jitter 적용 |
| Circuit Breaker | Target 장애 시 빠른 실패와 자원 보호 |
| Bulkhead | 채널·타깃별 Thread/Connection Pool 분리 |
| Rate Limit | 채널·소비자·서비스별 과부하 방지 |
| Idempotency | 거래·명령의 중복 처리 방지 |
| Compensation | 분산 업무의 부분 성공을 취소·보상 |
| Queue/Fallback | 정합성을 훼손하지 않는 업무에 한해 적용 |

### Timeout 예산 예시

```text
사용자 SLA 3초
├─ 채널/MCA/MCI/API G/W: 0.5초
├─ 정보계 AP:             1.5초
├─ 하위 시스템/DB:        0.7초
└─ 네트워크·안전 여유:    0.3초
```

숫자는 예시이며 실제 업무 SLA와 성능시험으로 결정한다.

## 14. 보안 경계

| 경로 | 주요 보안 통제 |
|---|---|
| 통합업무→MCA→정보계 | 사용자·점포 권한, 전용망, 전문 무결성 |
| 비대면→MCI→정보계 | 고객 인증, Token, Rate Limit, WAF/봇 방어 |
| 정보계 단말→정보계 | Device 인증, SSO/MFA, Network 제한, TLS |
| 패키지 UI→솔루션 | 패키지 인증, Session·권한, 직접 DB 차단 |
| 정보계↔대내 | OAuth2/JWT 또는 mTLS, API ACL, 최소 Source |
| 정보계↔대외 | 기관 인증서, mTLS, DMZ/대외계, 전문 암호화 |
| 타 법인→GSE→정보계 | 법인 식별, 인증서·Network, 전문·권한 검증 |

공통적으로 운영·개발·DR의 URL, 인증서, Client ID, Secret과 계정을 분리한다.

## 15. 오류·응답 표준

```json
{
  "header": {
    "guid": "<GUID>",
    "interfaceId": "<interface-id>",
    "responseTimestamp": "<ISO-8601>"
  },
  "result": {
    "success": false,
    "code": "<standard-error-code>",
    "category": "VALIDATION|AUTH|BUSINESS|SYSTEM|TIMEOUT|DEPENDENCY",
    "message": "<safe-client-message>",
    "retryable": false
  }
}
```

- 채널·Gateway·GSE의 오류 코드를 정보계 표준 오류로 매핑한다.
- 사용자 메시지와 운영 상세 원인을 분리한다.
- Stack Trace, SQL, Token, 개인정보를 응답에 포함하지 않는다.
- Timeout인지 처리 결과 미확정인지 구분하여 중복 재호출을 방지한다.
- 대외·타 법인 오류는 상대 기관 코드와 내부 코드를 함께 추적한다.

## 16. 모니터링과 SLA

### 공통 지표

- 호출 수, TPS, 성공률, 오류 코드 분포
- P50/P95/P99 응답시간과 Timeout
- 채널/MCA/MCI/API G/W/GSE/정보계 홉별 지연
- Circuit Open, Retry, Rate Limit 차단 수
- Thread·Connection Pool, Queue, CPU·메모리
- 인증 실패, 비정상 원천, TLS 인증서 만료

### 경로별 추적

| 경로 | 추가 추적 키 |
|---|---|
| MCA | 점포·단말·사용자·전사 거래 ID |
| MCI | 채널·Device·세션·고객 인증 거래 ID |
| Direct 단말 | 단말 ID·화면 ID·사용자 ID |
| 패키지 UI | 제품 버전·Client 버전·Session ID |
| API G/W | Consumer/App ID·API·Route·Policy 결과 |
| GSE | 법인·기관·전문 ID·상대 거래 ID |

GUID를 기준으로 각 시스템의 로컬 거래 ID를 연결해 종단 분석이 가능해야 한다.

## 17. 인터페이스 등록부

각 온라인 인터페이스는 다음 속성을 중앙 관리한다.

```yaml
interface_id: <unique-id>
name: <interface-name>
source_system: <source>
interface_system: MCA|MCI|DIRECT|API_GW|GSE
target_system: <target>
direction: inbound|outbound
channel_type: <type>
protocol: HTTP_JSON|ENTERPRISE_MESSAGE|PACKAGE_NATIVE
endpoint_or_service_id: <logical-id>
owner: <organization>
schema_version: <version>
authentication: <method>
timeout_ms: <value>
retry_policy: <policy-id>
idempotency: <rule>
sla: <policy-id>
monitoring: <dashboard-alert-id>
status: planned|active|deprecated|retired
```

물리 IP나 URL 변경은 등록부의 논리 서비스 관계를 바꾸지 않도록 Gateway·DNS·서비스 디스커버리로 추상화한다.

## 18. 테스트 기준

### 기능·계약

- 정상·필수값 누락·경계값·잘못된 형식
- Schema 하위 호환성과 알 수 없는 필드 처리
- 오류 코드·HTTP Status·전사 응답 코드 매핑
- 문자셋·한글·금액·날짜·Timezone

### 복원력

- Target 지연·Timeout·연결 거부·부분 장애
- Retry 중복 처리와 Idempotency
- Circuit Breaker Open/Half-Open/Recovery
- MCA/MCI/API G/W/GSE 한 노드 장애와 Failover
- Connection Pool 고갈·Rate Limit·트래픽 급증

### 보안

- 무인증·만료 Token·잘못된 인증서·권한 부족
- 전문 변조·Replay·과대 Payload·Injection
- 직접 URL 우회와 비허용 Source 접근
- 민감정보 로그·응답 노출

### 운영·DR

- GUID 종단 검색과 홉별 지연 확인
- 운영·개발 Endpoint와 Credential 분리
- DR 전환 후 DNS/VIP/Gateway Route와 인증서
- 롤백·버전 병행·소비자 전환

## 19. 주요 위험과 대응

| 위험 | 영향 | 대응 |
|---|---|---|
| 중계 시스템별 전문 표준 불일치 | 채널별 Adapter 난립 | 정보계 공통 계약과 경계 변환 원칙 |
| Direct 경로를 무통제로 해석 | 인증·추적·부하 통제 누락 | 정보계/패키지가 Gateway 공통 기능 직접 구현 |
| 정보계 단말의 DB 직접 연결 | 보안·결합도·변경 영향 확대 | WEB/WAS 서비스 계층만 허용 |
| API G/W 우회 호출 | 정책·감사·버전 관리 단절 | Network ACL·DNS·서비스 등록 통제 |
| Gateway 업무 로직 비대화 | 병목·제품 종속·변경 복잡성 | 공통 정책만 Gateway, 업무는 AP/EAI |
| 대내·대외 동일 정책 | 대외 인증·SLA·대사 누락 | 기관별 계약·인증서·오류·재처리 분리 |
| GSE 역할 불명확 | 중복 중계·책임 공백 | 제품/네트워크 상세 설계와 RACI 확정 |
| Retry 오용 | 중복 거래·장애 증폭 | 멱등성·Backoff·최대 횟수·보상 |
| GUID 단절 | 장애 원인·거래 경로 추적 실패 | Header 강제 검증과 ID 매핑 저장 |
| 패키지 UI 제품 종속 확산 | 업그레이드·교체 비용 증가 | 패키지 경계 Adapter와 표준 API 노출 |

## 20. 검증 체크리스트

- [ ] 장표의 온라인 경로 9개가 인터페이스 등록부에 모두 존재하는가?
- [ ] 대면 채널은 MCA, 비대면 채널은 MCI를 경유하는가?
- [ ] 정보계 단말 직접 경로가 DB가 아닌 서비스 계층으로 연결되는가?
- [ ] 패키지 UI가 공식 WEB/WAS Endpoint를 사용하고 직접 DB 접속을 하지 않는가?
- [ ] 정보계와 대내 시스템의 양방향 호출이 API G/W를 경유하는가?
- [ ] 정보계와 대외 기관의 양방향 전문이 API G/W 정책을 적용받는가?
- [ ] 타 법인 연계가 GSE를 경유하고 법인별 계약·인증을 갖는가?
- [ ] MCA·MCI·API G/W·GSE와 정보계 간 전문 매핑이 문서화되어 있는가?
- [ ] JSON/HTTP 적용 구간에 공통 Header·오류·Schema 표준이 있는가?
- [ ] GUID가 전 구간에서 유지되고 로컬 거래 ID와 매핑되는가?
- [ ] 인증·인가·TLS/mTLS·민감정보 마스킹이 경로별로 적용되는가?
- [ ] Timeout 예산, Retry, Circuit Breaker, Bulkhead와 멱등성이 정의되어 있는가?
- [ ] Gateway를 우회하는 물리 URL 접근이 Network에서 차단되는가?
- [ ] 직접 경로가 중계 경로와 동등한 감사·Rate Limit·모니터링을 제공하는가?
- [ ] 홉별 성능·오류와 종단 SLA를 모니터링하는가?
- [ ] 운영·개발·DR의 Endpoint, 인증서와 Secret이 분리되어 있는가?
- [ ] 장애·Failover·버전 병행·DR 전환 시험이 완료되었는가?

## 21. 최종 평가

온라인 인터페이스 표준은 대면 MCA, 비대면 MCI, 대내·대외 API Gateway, 법인 간 GSE라는 전사 표준 경로를 구분하면서 정보계 단말과 패키지 UI에는 직접 연결을 허용하는 구조다. 이 설계의 성공 조건은 경로만 맞추는 것이 아니라 **공통 전문 계약, GUID 종단 추적, 직접 연결의 동등 보안 통제, Gateway 우회 차단, 멱등성과 복원력, 경로별 SLA 및 중앙 등록부**를 함께 적용하는 것이다. 특히 “인터페이스 시스템 없음”을 통제 없음으로 오해하지 않고 정보계 또는 패키지 서비스 계층이 해당 책임을 명시적으로 수행해야 한다.

