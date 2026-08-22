# NSIGHT 표준전문 Header 아키텍처 분석 및 적용 가이드

## 1. 문서 목적

본 문서는 업로드된 **「은행/상호 계정계 표준전문 헤더 정의서」** 화면을 기준으로,
계정계 표준전문의 구조와 필드 역할을 분석하고 이를 NSIGHT/PDMG의
`hdr_nhnis + dto`, `ServiceContext`, TCF, Logging, Security 구조와 어떻게 연결할지 정리한 문서이다.

본 문서의 핵심 목적은 계정계의 고정길이 전문을 그대로 복제하는 것이 아니라,
**계정계 Header의 의미와 책임을 추출하여 NSIGHT HTTP/JSON 표준전문에 필요한 핵심 공통정보만 재구성하는 것**이다.

---

## 2. 분석 대상

분석 대상 화면은 다음과 같다.

- 은행/상호 계정계 표준전문 헤더 정의서
- 시스템/공통 Header 영역
- Transaction Header 영역
- Service Header 영역
- 필드별 논리명/물리명/길이/시작위치/필수여부/생성주체/비고

화면 하단에서 Service Header Size 및 전체 Header 길이 `800`이 확인되므로,
해당 구조는 **고정길이 기반 표준전문 Header** 성격이 강한 것으로 판단한다.

---

# 3. 전문 전체 구조

```text
┌──────────────────────────────────────────────────────────────┐
│                표준전문 Standard Message                    │
├──────────────────────────────────────────────────────────────┤
│ ① 시스템/공통 Header                                        │
│                                                              │
│   표준전문종류구분코드                                       │
│   표준전문업무구분코드                                       │
│   표준전문응답구분코드                                       │
│   프로토콜채널코드                                           │
│   금융메시지ID                                               │
│   단말IP주소                                                 │
│   거래일시                                                   │
│   표준전문실행모드                                           │
│   발생점번호                                                 │
│   조작자사번                                                 │
│   거래사무소코드                                             │
│   중앙회/조합 구분                                           │
│   ...                                                        │
├──────────────────────────────────────────────────────────────┤
│ ② Transaction Header                                        │
│                                                              │
│   1차/2차 직원업무코드                                       │
│   1차/2차 직원업무구분                                       │
│   MCA 관련 정보                                              │
│   PINPAD/매체 관련 정보                                      │
│   DB PIN                                                     │
│   PGW 정보                                                   │
│   거래 경로/채널/시스템 정보                                 │
│   교차거래 관련 정보                                         │
│   BPR 이미지ID                                               │
│   자동화기기 마감여부                                        │
│   ...                                                        │
├──────────────────────────────────────────────────────────────┤
│ ③ Service Header                                            │
│                                                              │
│   Service Header Size                                        │
│   Reserved                                                   │
├──────────────────────────────────────────────────────────────┤
│ ④ 업무 Body                                                  │
│                                                              │
│   업무별 Request / Response Data                             │
└──────────────────────────────────────────────────────────────┘
```

---

# 4. Header 정의서 컬럼 해석

| 컬럼 | 의미 | 아키텍처적 의미 |
|---|---|---|
| 구분 | Header 영역 | Common / Transaction / Service 구분 |
| 변경구분 | 변경 여부 | 전문 버전 및 변경관리 |
| 항목명 | 업무 표시명 | 사용자 관점 필드명 |
| 논리명 | 표준 논리명 | 데이터 표준 및 의미 |
| 물리명 | 실제 필드명 | 프로그램/전문 Mapping Key |
| 항목유형 | 문자/숫자 | Serialization 데이터 타입 |
| 길이 | 필드 길이 | 고정길이 전문 규격 |
| 시작위치 | Offset | 전문 Parsing 기준 |
| 필수데이터구분 | 요청/응답 필수 여부 | Message Validation |
| 생성주체 | channel/MCA/CORE 등 | 데이터 생성·소유 책임 |
| 비고 | 값/코드/조건 | 값 범위 및 업무 Rule |

특히 **생성주체**는 단순 설명 컬럼이 아니라
전문 아키텍처에서 매우 중요한 **Data Ownership / Trust Boundary** 정보이다.

---

# 5. 기능 관점 Header 분류

기존 정의서는 필드 배치 순서 중심이므로 NSIGHT에서는 다음처럼 기능별로 재분류하여 관리하는 것이 적절하다.

| 분류 | 대표 정보 | 사용 목적 |
|---|---|---|
| 거래 식별 | 금융메시지ID, 원거래ID | 거래 유일 식별 |
| 추적 | Global ID, Trace ID | End-to-End Trace |
| 라우팅 | 시스템ID, 채널, 업무코드 | 목적 시스템 결정 |
| 사용자 | 조작자사번 | 사용자 식별 |
| 조직 | 점번호, 사무소코드 | 영업점/조직 식별 |
| 단말 | 단말IP, 단말번호 | 접속 단말 식별 |
| 채널 | 채널코드, MCA 관련 정보 | 채널 경로 식별 |
| 거래통제 | 실행모드, 거래구분 | 실행 정책 |
| 복구/재처리 | 원거래ID, 재처리 관련정보 | 복구 및 재실행 |
| 보안 | 사용자/단말/채널 정보 | 인증·권한·감사 |
| 운영 | 시스템/노드/경로 | 장애 및 운영 추적 |
| 업무 | Transaction Header 정보 | 업무 공통 처리 |

---

# 6. NSIGHT/PDMG 표준전문과의 연결

현재 NSIGHT/PDMG 표준전문 구조는 다음과 같이 연결하는 것이 적절하다.

```text
Request
 │
 ├─ hdr_nhnis        ← 공통 Header
 │
 └─ dto              ← 업무 데이터
      │
      ▼
DefaultFilter
      │
      ├─ Header Parsing
      ├─ GUID
      ├─ ServiceContext
      └─ MDC
      ▼
Interceptor
      │
      ├─ JWT
      ├─ User
      ├─ IP
      ├─ ServiceId
      └─ Logging
      ▼
TCF
      │
      ├─ STF
      ├─ Timeout
      ├─ Transaction
      └─ Dispatcher
      ▼
Handler
      ↓
Facade
      ↓
Service
```

핵심 원칙은 다음과 같다.

> **공통 Header는 Framework가 관리하고, 업무 데이터만 업무 DTO로 분리한다.**

---

# 7. 계정계 Header를 그대로 복제하면 안 되는 이유

계정계 전문은 고정길이 기반이며 다양한 채널·MCA·단말·자동화기기·BPR 등의 정보를 포함한다.

이를 NSIGHT HTTP/JSON 환경에 그대로 복제하면 다음 문제가 발생할 수 있다.

- 불필요한 Legacy 필드 증가
- Header 크기 과대
- Client가 알 필요 없는 내부정보 노출
- 생성주체 불명확
- 보안/감사 필드 위·변조 가능성 증가
- 서비스별 불필요한 공통 의존성 증가
- 전문 버전 관리 복잡도 증가

따라서 다음 분류 절차가 필요하다.

```text
계정계 표준전문 800 Byte
        │
        ▼
┌─────────────────────────┐
│ Header Classification   │
├─────────────────────────┤
│ 반드시 유지             │
│ 조건부 유지             │
│ 업무 Body로 이동        │
│ Context 내부정보        │
│ Legacy 전용 → 제거      │
└────────────┬────────────┘
             ▼
       NSIGHT Header
```

---

# 8. NSIGHT 권장 핵심 Header

1차 기준으로 다음 정도를 NSIGHT 핵심 Header 후보로 두는 것이 적절하다.

| NSIGHT Header | 역할 | 계정계 전문과 관계 |
|---|---|---|
| `globalId` | E2E 거래 추적 | 금융메시지ID 계열 |
| `serviceId` | 거래 식별/Dispatcher | 업무/서비스 식별 |
| `systemId` | 호출 시스템 식별 | 시스템 관련 Header |
| `channelId` | 호출 채널 식별 | 채널코드 |
| `screenId` | 화면 식별 | 화면/프로그램 정보 |
| `userId` | 사용자 식별 | 조작자사번 |
| `branchCode` | 영업점/조직 식별 | 점번호/사무소 |
| `clientIp` | 단말 IP | 단말IP |
| `requestDateTime` | 요청 거래시간 | 거래일시 |
| `originalGlobalId` | 원거래 추적 | 원거래 ID |
| `transactionType` | 조회/등록/수정/삭제 등 | 거래유형 |
| `language` | 언어 | 필요 시 |
| `resultCode` | 결과코드 | 응답 전문 |
| `resultMessage` | 결과메시지 | 응답 전문 |

---

# 9. ServiceContext 설계

HTTP/JSON에서는 고정 Offset 자체보다 실행 Context가 더 중요하다.

```text
                 HTTP / JSON
                     │
                     ▼
              Standard Message
                     │
        ┌────────────┴────────────┐
        ▼                         ▼
     Header                      DTO
        │                         │
        ▼                         ▼
 ServiceContext              Business DTO
        │
 ┌──────┼─────────┐
 ▼      ▼         ▼
TCF   Logging   Security
 │      │         │
 ▼      ▼         ▼
TX    MDC/GUID   JWT
 │
 ▼
Dispatcher
```

권장 개념은 다음과 같다.

| 객체 | 역할 |
|---|---|
| Standard Message | 외부 시스템 간 전달 Envelope |
| Header | 외부 공통 거래정보 |
| ServiceContext | HTTP Request 범위의 실행 공통정보 |
| TransactionContext | TCF 거래 실행구간의 거래정보 |
| Business DTO | 실제 업무 입력·출력 데이터 |

---

# 10. Header 생성주체 재정의

계정계에서는 `channel / MCA / CORE` 등으로 생성주체가 나뉘어 있으므로
NSIGHT에서도 필드별 생성책임과 신뢰경계를 명확히 해야 한다.

```text
Browser
  │
  │ screenId
  ▼
UI
  │
  │ serviceId
  ▼
Gateway / Filter
  │
  │ globalId / clientIp / requestDateTime
  ▼
JWT / Security
  │
  │ userId / branchCode
  ▼
TCF
  │
  │ transactionType / transaction context
  ▼
Business
```

권장 생성 책임은 다음과 같다.

| Header | 권장 생성/검증 주체 |
|---|---|
| `globalId` | Gateway / Framework |
| `serviceId` | UI 전달 + Framework 검증 |
| `systemId` | Gateway / Framework |
| `channelId` | Gateway |
| `screenId` | UI |
| `userId` | JWT / Security Context |
| `branchCode` | JWT / User Context |
| `clientIp` | Gateway / Filter |
| `requestDateTime` | Framework |
| `transactionType` | ServiceId / TCF |
| `resultCode` | Framework / Exception Handler |
| `resultMessage` | Framework / Exception Handler |

---

# 11. 신뢰 경계 원칙

다음 정보는 Client 전달값을 그대로 신뢰해서는 안 된다.

- `userId`
- `branchCode`
- `clientIp`
- 권한정보
- 채널 내부 식별값
- 시스템 내부 실행상태
- 결과코드

권장 원칙은 다음과 같다.

```text
Client Supplied
     ↓
Framework Validation
     ↓
JWT / Security Context
     ↓
Trusted ServiceContext
```

즉 업무 코드에서는 원시 Header보다 **검증된 ServiceContext**를 사용해야 한다.

---

# 12. 전체 목표 구조

```text
                  NSIGHT STANDARD MESSAGE
                            │
             ┌──────────────┴──────────────┐
             │                             │
             ▼                             ▼
        hdr_nhnis                         dto
      Common Header                 Business Data
             │                             │
             ▼                             │
       DefaultFilter                       │
             │                             │
       Header Parsing                      │
             │                             │
             ▼                             │
       ServiceContext                      │
             │                             │
    ┌────────┼────────┬────────┐           │
    ▼        ▼        ▼        ▼           │
   JWT      MDC      TCF    ImageLog       │
    │        │        │                    │
    │        │        ▼                    │
    │        │    ServiceId                │
    │        │        │                    │
    │        │        ▼                    │
    │        │   Dispatcher                │
    │        │        │                    │
    └────────┴────────┼────────────────────┘
                     ▼
                  Handler
                     ↓
                  Facade
                     ↓
                  Service
                     ↓
                    DAO
```

---

# 13. Architecture Rule

| Rule ID | 규칙 | 판정 |
|---|---|---|
| `MSG-001` | 공통 Header와 업무 DTO를 분리한다 | 필수 |
| `MSG-002` | 업무 Service가 전체 전문 객체에 직접 의존하지 않는다 | 필수 |
| `MSG-003` | 공통 Header는 Framework가 Parsing/검증한다 | 필수 |
| `MSG-004` | 검증된 Header는 ServiceContext로 제공한다 | 필수 |
| `MSG-005` | 사용자/조직정보는 JWT 또는 Trusted Context 기준으로 보정한다 | 필수 |
| `MSG-006` | Client IP는 Gateway/Server에서 확정한다 | 필수 |
| `MSG-007` | ServiceId는 Dispatcher의 논리적 거래주소로 사용한다 | 필수 |
| `MSG-008` | GUID/Global ID는 End-to-End 추적의 기준키로 사용한다 | 필수 |
| `MSG-009` | 계정계 Legacy 필드를 무조건 NSIGHT Header에 복제하지 않는다 | 금지 |
| `MSG-010` | MCA/PINPAD/BPR 등 채널특화 필드는 필요 시 별도 Context/Body로 분리한다 | 필수 |
| `MSG-011` | Error Response는 업무에서 직접 만들지 않고 Framework가 표준화한다 | 필수 |
| `MSG-012` | Header 필드별 생성주체와 신뢰수준을 정의한다 | 필수 |
| `MSG-013` | 요청/응답 필수여부를 Schema/Validation Rule로 관리한다 | 필수 |
| `MSG-014` | Header 변경은 버전관리 및 호환성 검토를 수행한다 | 필수 |

---

# 14. 계정계 → NSIGHT Header 매핑 관리표

향후 최종 표준전문 정의서는 다음 컬럼 구조로 관리하는 것이 좋다.

| No | 계정계 항목명 | 물리명 | 타입 | 길이 | 시작위치 | 요청필수 | 응답필수 | 생성주체 | 의미 | NSIGHT 처리 | NSIGHT Header | ServiceContext | 생성/검증 컴포넌트 | 상태 |
|---:|---|---|---|---:|---:|---|---|---|---|---|---|---|---|---|
| 1 | 금융메시지ID | 확인필요 | 문자 | 확인필요 | 확인필요 | O | O | channel/core | 거래추적 | 유지/변환 | `globalId` | `globalId` | FW | 설계대상 |
| 2 | 단말IP주소 | 확인필요 | 문자 | 확인필요 | 확인필요 | O | - | channel | 단말식별 | 유지/보정 | `clientIp` | `clientIp` | Gateway/FW | 설계대상 |
| 3 | 조작자사번 | 확인필요 | 문자 | 확인필요 | 확인필요 | O | - | channel | 사용자 | JWT 기반 보정 | `userId` | `userId` | Security | 설계대상 |
| 4 | 거래사무소코드 | 확인필요 | 문자 | 확인필요 | 확인필요 | O | - | channel | 조직/점포 | JWT/User Context 기반 | `branchCode` | `branchCode` | Security/FW | 설계대상 |
| 5 | 프로토콜채널코드 | 확인필요 | 문자 | 확인필요 | 확인필요 | O | - | channel | 채널식별 | 유지/정규화 | `channelId` | `channelId` | Gateway | 설계대상 |

※ 이미지에서 식별이 불명확한 물리명/길이/위치는 임의 추정하지 않고 `확인필요`로 유지한다.

---

# 15. 요청 전문 예시

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "globalId": "20260818-ABCDEF-000001",
      "serviceId": "mgcoa9001S0",
      "systemId": "NSIGHT",
      "channelId": "WEB",
      "screenId": "CO-A-9001",
      "userId": "USER001",
      "branchCode": "1234",
      "clientIp": "10.10.10.10",
      "requestDateTime": "2026-08-18T22:25:00+09:00",
      "transactionType": "S"
    }
  },
  "dto": {
    "customerId": "..."
  }
}
```

주의:
- 예시 값은 구조 설명을 위한 샘플이다.
- 실제 필드명/길이/필수여부는 최종 표준전문 정의서에서 확정해야 한다.

---

# 16. 정상 응답 예시

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "globalId": "20260818-ABCDEF-000001",
      "serviceId": "mgcoa9001S0",
      "resultCode": "0000",
      "resultMessage": "SUCCESS"
    }
  },
  "dto": {
    "data": {}
  }
}
```

---

# 17. 오류 응답 예시

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "globalId": "20260818-ABCDEF-000001",
      "serviceId": "mgcoa9001S0"
    }
  },
  "result": {
    "code": "MP0404",
    "message": "요청한 데이터를 찾을 수 없습니다."
  }
}
```

업무 Service는 오류 JSON을 직접 조립하기보다 예외를 발생시키고,
Framework Exception Handler가 표준 오류 전문으로 변환하는 구조가 적절하다.

---

# 18. 향후 최종화 작업

최종 **NSIGHT 표준전문 Header 정의서**를 완성하려면 다음 작업이 필요하다.

1. 계정계 800-byte Header 전 필드 목록화
2. 항목명/논리명/물리명/길이/Offset 정확 추출
3. 요청/응답 필수여부 확인
4. 생성주체 확인
5. 기능별 분류
6. NSIGHT 유지/변환/제거/Body 이동 판정
7. NSIGHT Header명 확정
8. ServiceContext 필드 확정
9. 생성·검증 컴포넌트 확정
10. JSON Schema 작성
11. Validation Rule 작성
12. Header Version 정책 작성
13. Logging/Masking 정책 작성
14. ServiceId/Dispatcher 연결
15. Runtime Evidence 항목 정의

---

# 19. 최종 결론

계정계 표준전문 Header는 단순 데이터 포맷이 아니라
**거래 식별, 라우팅, 사용자/조직/단말 식별, 통제, 추적, 복구, 감사까지 포함하는 거래 실행 메타데이터 체계**이다.

NSIGHT에서는 이를 그대로 복제하지 않고 다음 구조로 재해석해야 한다.

```text
계정계 고정길이 Header
        ↓
의미/책임/생성주체 분석
        ↓
NSIGHT 핵심 Header 선별
        ↓
ServiceContext
        ↓
TCF / Security / Logging / Dispatcher
        ↓
Business DTO
```

최종 목표는 **“고정길이 800-byte Header의 복제”가 아니라
“NSIGHT에 필요한 최소·신뢰·추적 가능한 공통 거래 Context의 표준화”**이다.
