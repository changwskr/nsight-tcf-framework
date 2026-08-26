# 인터페이스 아키텍처 — 파일 인터페이스 표준 정의 분석

## 1. 핵심 결론

장표는 정보계의 모든 표준 파일 연계를 **FOS**로 일원화한다. 내부 채널·패키지·대내 시스템 연계는 FOS를 사용하고, 대외 기관 연계는 `FOS(대외MCA)`를 통해 처리한다.

| 연계 범위 | 표준 중계 | 방향 |
|---|---|---|
| 정보계 단말 → 정보계 | FOS | 인바운드 |
| 패키지 UI → 정보계 솔루션 | FOS | 인바운드 |
| 정보계 ↔ 대내 시스템 | FOS | 양방향 |
| 정보계 ↔ 대외 기관 | FOS(대외MCA) | 양방향 |

```text
정보계 단말 ───────> FOS ─────────────> 정보계
패키지 UI ─────────> FOS ─────────────> 정보계 솔루션

정보계 ─────────────> FOS ─────────────> 대내 시스템
정보계 <───────────── FOS <───────────── 대내 시스템

정보계 ─────────────> FOS(대외MCA) ────> 대외 기관
정보계 <───────────── FOS(대외MCA) <──── 대외 기관
```

FOS 경유만으로 파일 인터페이스가 완성되는 것은 아니다. 파일명, Layout, 문자셋, 압축·암호화, 완료 신호, 무결성, 중복 방지, 재전송, 보존, SLA와 책임 주체를 **인터페이스 계약**으로 함께 관리해야 한다.

## 2. 장표 원문 전사

| No. | 구분 | 소스 | 인터페이스 시스템 | 타깃 | 설명 |
|---:|---|---|---|---|---|
| 1 | 파일 | 정보계 단말 | FOS | 정보계 | 정보계 시스템 파일 전송 표준 인터페이스 |
| 2 | 파일 | 패키지 UI | FOS | 정보계 솔루션 | 정보계 시스템 파일 전송 표준 인터페이스 |
| 3 | 파일 | 정보계 | FOS | 대내 시스템 | 정보계 시스템 파일 전송 표준 인터페이스 |
| 4 | 파일 | 대내 시스템 | FOS | 정보계 | 정보계 시스템 파일 전송 표준 인터페이스 |
| 5 | 파일 | 정보계 | FOS(대외MCA) | 대외 기관 | 정보계 시스템 파일 전송 표준 인터페이스 |
| 6 | 파일 | 대외 기관 | FOS(대외MCA) | 정보계 | 정보계 시스템 파일 전송 표준 인터페이스 |

> FOS와 대외MCA의 제품 내부 구조와 약어 확장은 장표만으로 확정하지 않는다. 본 분석은 장표가 정의한 논리적 파일 전송 경로와 책임 경계를 기준으로 한다.

## 3. 파일 인터페이스 전체 구조

```text
┌──────────────────────────── 내부 연계 ────────────────────────────┐
│                                                                  │
│ 정보계 단말 ─┐                                                   │
│ 패키지 UI ───┼──> [FOS] ──> [정보계 / 정보계 솔루션]             │
│              │                                                   │
│ 정보계 <─────┼──> [FOS] <──> [대내 시스템]                       │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘

┌──────────────────────────── 대외 연계 ────────────────────────────┐
│                                                                  │
│ 정보계 <────────> [FOS] <─ 내부 연계 ─> [대외MCA] <────> 대외기관│
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

장표의 `FOS(대외MCA)` 표기는 대외 파일 연계에서 FOS가 대외MCA와 결합된 표준 경로를 사용한다는 의미로 해석한다. FOS와 대외MCA 중 어느 컴포넌트가 스케줄링, 암호화, 실제 전송, 대사와 재처리를 담당하는지는 상세 설계서에서 RACI로 확정해야 한다.

## 4. 경로별 상세 분석

### 4.1 정보계 단말 → FOS → 정보계

정보계 전용 단말이 생성·선택한 파일을 정보계 시스템으로 전달하는 경로다.

- 단말이 서버 공유 디렉터리나 SFTP 계정에 직접 접근하지 않고 FOS 표준 요청을 사용한다.
- 사용자, 단말, 화면, 업무 권한과 업로드 파일 유형을 검증한다.
- 브라우저/클라이언트 업로드는 파일 크기, 확장자, MIME, 악성코드를 검사한다.
- 업로드 완료 전 파일이 업무 처리에 노출되지 않도록 임시 영역을 사용한다.
- File ID, 사용자 ID, 원본 파일명, Hash와 처리 결과를 감사한다.

### 4.2 패키지 UI → FOS → 정보계 솔루션

패키지 UI가 정보계 솔루션에 파일을 전달하는 경로다.

- 패키지의 고유 업로드 기능을 FOS 계약과 연결하되, 제품 내부 경로에 타 시스템이 직접 의존하지 않게 한다.
- UI와 서버의 버전별 파일 Layout 호환성을 관리한다.
- 패키지 Upgrade 시 FOS Adapter와 파일 처리 회귀시험을 수행한다.
- 제품 임시 파일과 최종 업무 파일의 저장 위치·보존 책임을 구분한다.

### 4.3 정보계 → FOS → 대내 시스템

정보계가 대내 시스템으로 파일을 송신한다.

- 정보계는 완성된 파일과 Manifest를 FOS에 등록한다.
- FOS는 전송 대상, 스케줄, 재시도, 상태와 수신 확인을 관리한다.
- 타깃은 최종 완료 신호 후에만 파일을 소비한다.
- 재전송 시 동일 파일을 중복 반영하지 않도록 File ID와 업무키를 사용한다.

### 4.4 대내 시스템 → FOS → 정보계

대내 시스템에서 정보계로 파일을 수신한다.

- 송신 시스템이 파일 계약과 완료 신호를 준수하는지 검증한다.
- 정보계 수신 영역을 `ready`, `processing`, `done`, `error`로 분리한다.
- 수신 파일의 건수·금액·Hash를 송신 Manifest와 대사한다.
- 처리 실패는 원본을 보존하고 원인 수정 후 승인된 절차로 재처리한다.

### 4.5 정보계 → FOS(대외MCA) → 대외 기관

정보계가 대외 기관으로 파일을 송신하는 경로다.

- 기관별 암호화, 전자서명, 압축, 파일명, 전송 시간과 확인 전문을 적용한다.
- 내부 원본과 대외 전송본을 분리하고 개인정보 최소화·마스킹을 수행한다.
- 대외MCA 구간의 실제 전송 프로토콜과 인증서는 기관 계약에 따라 관리한다.
- 송신 성공은 중계 전달이 아니라 상대 기관의 수신·검증 확인 기준으로 판정한다.

### 4.6 대외 기관 → FOS(대외MCA) → 정보계

대외 기관의 파일을 정보계로 수신한다.

- 송신 기관·인증서·원천 주소를 검증한다.
- 복호화·전자서명 검증·악성코드 검사 후 내부 FOS로 전달한다.
- 기관의 파일명과 Layout을 정보계 표준 인터페이스 계약에 매핑한다.
- 검증 실패 파일은 Quarantine에 격리하고 업무 경로에 노출하지 않는다.
- 수신 확인·오류 회신과 재전송 책임을 기관별로 정의한다.

## 5. 표준 파일 계약

모든 파일 인터페이스는 다음 정보를 중앙 등록부에 보유해야 한다.

| 분류 | 계약 항목 | 예시/설명 |
|---|---|---|
| 식별 | Interface ID | 파일 인터페이스 고유 ID |
| 식별 | File ID / Transfer ID | 개별 파일·전송 추적 ID |
| 관계 | 송신·수신 시스템 | 시스템 코드, 환경, 담당자 |
| 경로 | FOS Route | 송신 Queue/Folder, Target Route |
| 파일명 | Naming Pattern | 업무코드·기준일·순번·버전·확장자 |
| 형식 | Layout | CSV, Fixed Length, JSON, XML, Binary 등 |
| 인코딩 | Charset / Newline | UTF-8, EUC-KR, LF, CRLF 등 |
| 구조 | Header/Body/Trailer | 건수·합계·Control 정보 |
| 크기 | Max File/Record Size | 최대 파일·레코드 크기 |
| 압축 | Compression | 알고리즘, 확장자, 수행 순서 |
| 보안 | Encryption/Signature | 알고리즘, 키·인증서 소유자 |
| 무결성 | Hash/Checksum | SHA-256 등 승인 알고리즘 |
| 완료 | Completion Signal | Atomic Rename, Manifest, Control File |
| 일정 | Schedule/SLA | 송수신 시각, 최대 완료 시간 |
| 장애 | Retry/Recovery | 횟수, Backoff, 재전송·재처리 주체 |
| 보존 | Retention/Deletion | 구간별 보존 기간과 삭제 방식 |

### 계약 예시

```yaml
interface_id: FILE-INFO-001
source_system: <source-code>
target_system: <target-code>
fos_route: <logical-route-id>
direction: inbound
file_name_pattern: '<business>_<yyyymmdd>_<seq>.dat'
format: fixed_length
charset: utf-8
newline: lf
schema_version: '1.0'
completion_signal: manifest
integrity:
  algorithm: sha-256
  record_count: true
  amount_total: true
security:
  encryption: <approved-method>
  signature: <approved-method>
sla:
  expected_at: '<schedule>'
  deadline: '<deadline>'
retention:
  source_days: <n>
  relay_days: <n>
  target_days: <n>
```

## 6. 파일명 표준

### 권장 구성

```text
<interface-id>_<business-date>_<sequence>_<schema-version>.<extension>
```

예시 형식:

```text
FILEINFO001_20260823_0001_v1.dat
FILEINFO001_20260823_0001_v1.dat.manifest
```

### 원칙

- 파일명만으로 시스템·인터페이스·기준일·순번·버전을 식별할 수 있게 한다.
- 공백, 제어문자, 경로 구분자와 OS 종속 특수문자를 금지한다.
- 개인정보·계좌번호·고객명 등 민감정보를 파일명에 포함하지 않는다.
- 중복 이름 덮어쓰기를 금지하고 동일 File ID 재전송 정책을 명시한다.
- 작성 중 파일은 `.part`, `.tmp` 등 임시명으로 두고 완료 후 원자적으로 변경한다.
- 대소문자 정책과 최대 길이를 명시한다.

## 7. 파일 Layout 표준

### 7.1 Fixed Length

- Byte/문자 길이 기준을 명확히 하고 멀티바이트 문자셋을 고려한다.
- Padding 문자, 숫자 정렬, 부호·소수점, 날짜 형식을 정의한다.
- Header/Trailer의 레코드 수와 합계를 본문과 대사한다.

### 7.2 Delimited(CSV 등)

- 구분자, Quote, Escape, Header 유무와 개행 규칙을 정의한다.
- 필드 내 구분자·개행·Quote 처리와 Null/빈 문자열을 구분한다.
- Spreadsheet Formula Injection을 방지한다.

### 7.3 JSON/XML

- JSON Schema 또는 XSD와 버전·호환성 정책을 관리한다.
- 대용량 파일은 Streaming Parser를 사용하고 전체 메모리 적재를 피한다.
- XML 외부 Entity와 과도한 중첩 등 Parser 공격을 방어한다.

### 7.4 Binary

- 제품·버전·Endian·압축·Checksum을 명시한다.
- 전용 Reader/Writer와 장기 호환성·변환 계획을 관리한다.

## 8. 안전한 파일 완료 처리

작성 중인 파일을 수신자가 읽는 문제를 방지해야 한다.

### 방식 A — 임시명 후 Atomic Rename

```text
<file>.part
  → 전체 쓰기·fsync
  → Hash 계산
  → 동일 파일시스템 내 Rename
  → <file>.dat
```

### 방식 B — Manifest/Control File

```text
1. Data File 전송
2. Data File Hash·건수 검증
3. Manifest/Control File을 마지막에 전송
4. 수신자는 Manifest가 있을 때만 처리
```

### Manifest 예시

```json
{
  "interfaceId": "FILE-INFO-001",
  "fileId": "<unique-file-id>",
  "fileName": "<data-file-name>",
  "schemaVersion": "1.0",
  "sizeBytes": 0,
  "recordCount": 0,
  "checksum": {
    "algorithm": "SHA-256",
    "value": "<hash>"
  },
  "createdAt": "<ISO-8601>"
}
```

## 9. 처리 상태 모델

```text
CREATED
  → REGISTERED
    → TRANSFERRING
      → DELIVERED
        → VALIDATING
          → READY
            → PROCESSING
              → CONSUMED
                → ARCHIVED

실패 분기
  TRANSFERRING/VALIDATING/PROCESSING
    → RETRY_WAIT → RETRYING
    → FAILED
    → QUARANTINED
```

### 상태별 소유자

| 상태 구간 | 대표 책임 |
|---|---|
| CREATED | 송신 업무 시스템 |
| REGISTERED~DELIVERED | FOS/대외MCA 전송 계층 |
| VALIDATING~READY | 수신 Adapter/FOS |
| PROCESSING~CONSUMED | 수신 업무 시스템 |
| ARCHIVED/DELETED | 계약상 보존 책임자 |
| FAILED/QUARANTINED | 장애 원인에 따른 송신·중계·수신 공동 처리 |

## 10. 무결성 및 대사

파일 전송 성공과 업무 반영 성공을 구분한다.

```text
전송 대사
  파일명 + 크기 + SHA-256 + Transfer ID

구조 대사
  Header/Trailer + Record Count + Schema Validation

업무 대사
  처리 건수 + 성공/오류 건수 + 금액/수량 합계 + 업무키
```

### 대사 원칙

- 송신, FOS, 대외MCA, 수신 각 구간의 File/Transfer ID를 연결한다.
- Hash는 파일이 완성된 후 계산하고 송·수신 값을 비교한다.
- 암호화 전 원문 Hash와 암호화 파일 Hash의 사용 목적을 구분한다.
- 건수·합계가 불일치하면 부분 반영하지 않고 계약된 복구 절차를 따른다.
- 대외 기관의 수신 확인과 정보계 업무 반영 확인을 별도 상태로 관리한다.

## 11. 멱등성·중복 방지

파일 재전송과 업무 재처리는 서로 다른 작업이다.

| 상황 | 처리 원칙 |
|---|---|
| 동일 File ID·동일 Hash 재수신 | 이미 완료되었으면 재반영하지 않고 성공 이력 반환 |
| 동일 File ID·다른 Hash | 변조 또는 버전 충돌로 격리·승인 필요 |
| 다른 File ID·같은 업무키 | 업무 중복 규칙으로 판정 |
| 전송 실패 | FOS/MFT 구간 재전송, 업무 반영 없음 확인 |
| 처리 실패 | 원본 보존 후 수신 업무의 재처리 절차 수행 |
| 결과 불명 | 처리 이력 조회 후 재전송/재처리 결정 |

수신 시스템은 File ID와 Schema Version, Hash, 처리 상태, 완료 시각을 저장해야 한다.

## 12. 보안 표준

### 인증·인가

- FOS 송수신 시스템, Route와 서비스 계정을 상호 인증한다.
- 대외MCA는 기관별 인증서·전용망·원천 주소를 검증한다.
- 사용자 업로드는 사용자·단말·업무 권한을 확인한다.
- 송신·조회·재전송·삭제·복호화 권한을 분리한다.

### 전송·저장 보호

- 전송 구간 암호화와 파일 자체 암호화를 데이터 등급에 따라 적용한다.
- 암호화·전자서명 키는 Vault/HSM 등 승인된 키 관리 시스템에서 관리한다.
- Stage·Retry·Quarantine·Archive 영역도 암호화·접근통제 대상이다.
- 민감 파일은 보존 종료 후 복구 불가능한 방식으로 삭제한다.

### 콘텐츠 보안

- 확장자와 MIME/Signature를 함께 검증한다.
- 악성코드 검사와 압축 폭탄·Zip Slip·경로 조작을 방어한다.
- 최대 파일·압축 해제 크기와 파일 개수를 제한한다.
- 파일명에서 `../`, 절대경로, 제어문자와 예약 문자를 제거한다.
- Parser는 크기·필드·중첩 한도를 적용한다.

## 13. 재시도·재처리 및 장애 대응

### 전송 오류

```text
연결/전송 실패
  → 제한 횟수 자동 재시도(Backoff + Jitter)
  → 최대 횟수 초과
  → FAILED + 운영 알림
  → 원인 해결
  → 동일 Transfer/File ID 정책에 따른 재전송
```

### 검증 오류

- Hash, 크기, 전자서명, Schema 또는 건수 불일치는 Quarantine으로 이동한다.
- 자동 수정하거나 부분 처리하지 않는다.
- 송신 원본과 Manifest를 비교해 재생성 또는 재전송한다.

### 업무 처리 오류

- 원본을 보존하고 성공·실패 레코드 분리 여부를 계약에 따른다.
- 부분 Commit이 허용되면 성공 목록과 Reject 파일을 생성하고 대사한다.
- 전량 원자 처리가 필요하면 전체 Rollback 후 파일 단위 재처리한다.
- 재처리 승인자, 사유, 실행자, 횟수와 결과를 감사한다.

### FOS/대외MCA 장애

- 중계 Queue·Stage의 내구성과 디스크 고갈을 감시한다.
- Active–Standby/Cluster 전환 시 Transfer 상태와 Lock을 복구한다.
- 이중 전송과 Split-Brain을 방지한다.
- DR 전환 후 Route, 인증서, DNS/VIP, 보존 파일과 재시작 지점을 검증한다.

## 14. 저장 경로와 파일시스템

앞서 정의한 AP·DB 파일시스템 표준의 `/userdir*` Data Area와 연계할 수 있다.

```text
/userdir[_hostname]/<interface-id>
├─ outbound/
│  ├─ creating/
│  ├─ ready/
│  ├─ sent/
│  └─ error/
├─ inbound/
│  ├─ receiving/
│  ├─ ready/
│  ├─ processing/
│  ├─ done/
│  └─ error/
├─ quarantine/
└─ archive/
```

- Creating/Receiving과 Ready는 같은 파일시스템에 두어 Atomic Rename을 보장한다.
- FOS Agent와 업무 계정의 UID/GID·그룹·umask를 표준화한다.
- 공유 스토리지에서는 HA 노드 간 소유권과 Lock 정책을 일치시킨다.
- 용량·inode·증가율·보존 만료와 열린 삭제 파일을 감시한다.
- Archive는 백업이 아니며 RPO/RTO에 맞는 별도 보호를 적용한다.

## 15. 스케줄·SLA

| 항목 | 정의 내용 |
|---|---|
| 생성 예정 시각 | 송신 파일이 준비되어야 하는 시각 |
| 전송 개시·완료 | FOS 등록과 Target 전달 목표 |
| 수신 Deadline | 지연 판단 기준 |
| 처리 완료 | 업무 반영 완료 목표 |
| 최대 파일 크기 | 정상 SLA를 만족하는 상한 |
| 재시도 | 횟수·간격·최대 지연 |
| 지연 통보 | Warn/Critical 시각과 연락 대상 |
| Cut-off | 당일 처리·익일 이월 판단 기준 |

파일 미도착, 지연 도착, 빈 파일, 중복 파일, 초과 크기와 휴일 일정도 SLA 규칙에 포함한다.

## 16. 모니터링과 거래 추적

### 추적 키

- Interface ID
- File ID / Transfer ID
- FOS Route ID
- 대외MCA 거래 ID
- 원본·최종 파일명
- Schema Version
- Batch/Job ID
- GUID/Correlation ID(온라인 거래에서 파생된 경우)

### 핵심 지표

- 예정 대비 도착·완료 지연
- 전송 성공률, 재시도 및 실패율
- 전송량, 처리량, 파일 크기와 Queue Depth
- Hash·서명·Schema·건수 불일치
- Ready/Processing/Error/Quarantine 체류 파일 수와 시간
- 중복 차단 및 재처리 건수
- FOS/대외MCA Agent·Node 상태와 디스크 사용률
- 인증서·암호화 키 만료

### 종단 추적

```text
송신 File ID
  ↔ FOS Transfer ID
    ↔ 대외MCA 거래 ID(대외 시)
      ↔ 수신 File ID
        ↔ 업무 Batch/Job ID
          ↔ 반영 결과·대사 ID
```

## 17. 운영·개발·DR 분리

- 환경별 FOS Route, Agent, Endpoint, Service Account와 인증서를 분리한다.
- 개발 파일이 운영 Route로 전송되지 않도록 시스템 코드·Network·Credential을 교차 검증한다.
- 테스트 데이터는 개인정보를 비식별화하고 대외 기관 운영 경로 사용을 금지한다.
- DR 환경에 Route·인증서·키·계약·디렉터리 구조를 사전 동기화한다.
- DR 전환 시 미완료 Transfer의 승계·취소·재전송 정책을 시험한다.
- 운영 복귀 후 양 센터의 중복 파일과 처리 상태를 대사한다.

## 18. 인터페이스 등록부

```yaml
interface_id: FILE-<domain>-<number>
type: file
source_system: <system-code>
target_system: <system-code>
interface_system: FOS|FOS_EXTERNAL_MCA
direction: inbound|outbound
owner: <organization>
file_contract: <contract-id/version>
fos_route: <route-id>
external_institution: <institution-code-if-any>
security_policy: <policy-id>
schedule_sla: <sla-id>
retry_recovery: <policy-id>
retention: <policy-id>
monitoring: <dashboard-alert-id>
status: planned|active|deprecated|retired
```

경로·계정·IP 같은 물리 값보다 논리 Route와 계약을 중심으로 관리하고, 물리 구성은 환경별 배포 정보로 연결한다.

## 19. 테스트 기준

### 계약·기능

- 정상, 빈 파일, 최대 크기, 0건 파일
- Header/Trailer·건수·합계·Hash 일치/불일치
- 문자셋·한글·개행·Quote·Padding·Null
- Schema Version 호환·비호환
- 파일명·Manifest·완료 신호

### 복원력

- 전송 중 Network 단절과 재개
- FOS/대외MCA Node 장애·Failover
- 동일 파일 중복·순서 역전·지연 도착
- 디스크·inode·Queue 고갈
- 수신 업무 장애 후 재처리
- DR 전환 중 미완료 Transfer

### 보안

- 위조 송신자·만료 인증서·권한 부족
- 파일 변조·잘못된 서명·Hash 불일치
- 악성코드·압축 폭탄·Zip Slip·경로 조작
- 암호화되지 않은 민감 파일
- 비허용 파일 유형·초과 크기

### 운영

- SLA 경보, File ID 종단 조회, 대사 보고서
- 보존 만료·안전 삭제·Archive/Backup 복구
- Route 변경·Schema Version 병행·Rollback

## 20. 주요 위험과 대응

| 위험 | 영향 | 대응 |
|---|---|---|
| FOS 경유만 정의하고 파일 계약 미비 | 송수신 해석 차이·처리 실패 | Layout·인코딩·완료·대사 계약 중앙화 |
| 작성 중 파일 소비 | 불완전 데이터 반영 | Atomic Rename 또는 Manifest |
| 파일 재전송과 업무 재처리 혼동 | 중복 반영 | File/Transfer/Batch 상태 분리·멱등성 |
| Hash 없는 전송 | 손상·변조 미탐지 | 승인 Checksum과 서명 검증 |
| 대외MCA 책임 경계 불명확 | 장애 위치·재처리 주체 분쟁 | FOS–대외MCA RACI와 상태 연계 |
| 단말·UI 직접 공유폴더 접근 | 우회 전송·감사 단절 | FOS API/Agent만 허용 |
| 파일명에 개인정보 포함 | 로그·목록에서 정보 노출 | 비식별 ID만 사용 |
| 무제한 보존·재시도 | 디스크 고갈·중복 폭주 | Retention·Backoff·최대 횟수 |
| 환경 Route 혼선 | 개발 파일 운영 반영 | 환경별 Credential·Network·Route 분리 |
| HA/DR 중 이중 전송 | 중복 거래 | Fencing·단일 Active·File ID 대사 |

## 21. 검증 체크리스트

- [ ] 장표의 파일 연계 경로 6개가 등록부에 모두 존재하는가?
- [ ] 정보계 단말과 패키지 UI가 FOS를 경유하며 공유폴더에 직접 접근하지 않는가?
- [ ] 정보계와 대내 시스템의 양방향 파일 연계가 FOS를 사용하는가?
- [ ] 정보계와 대외 기관의 양방향 연계가 FOS(대외MCA)를 사용하는가?
- [ ] FOS와 대외MCA의 전송·암호화·상태·재처리 RACI가 명확한가?
- [ ] Interface ID, File ID, Transfer ID와 업무 Batch ID가 연계되는가?
- [ ] 파일명·Layout·문자셋·개행·버전·최대 크기가 정의되어 있는가?
- [ ] Atomic Rename 또는 Manifest 방식의 완료 신호를 사용하는가?
- [ ] 파일 크기·건수·합계·SHA-256 등 무결성을 대사하는가?
- [ ] 동일 File ID 중복과 동일 ID·다른 Hash 충돌을 차단하는가?
- [ ] 전송 재시도와 업무 재처리를 구분하고 감사하는가?
- [ ] 전송·저장 암호화, 전자서명과 키 수명주기를 관리하는가?
- [ ] 악성코드·압축 폭탄·경로 조작·비허용 확장자를 검사하는가?
- [ ] Ready/Processing/Done/Error/Quarantine 상태가 분리되는가?
- [ ] 보존·Archive·삭제·Backup 정책과 소유자가 정의되어 있는가?
- [ ] 예정 도착·전송·처리 완료 SLA와 지연 경보가 있는가?
- [ ] 운영·개발·DR Route·계정·인증서가 분리되는가?
- [ ] FOS/대외MCA 장애·Failover·DR 전환·중복 방지 시험이 완료되었는가?

## 22. 최종 평가

파일 인터페이스 표준은 정보계 단말·패키지·대내 시스템의 파일 흐름을 FOS로 수렴시키고, 대외 기관 연계를 `FOS(대외MCA)`로 분리하여 업무 시스템이 개별 전송 제품과 직접 결합되지 않게 한다. 이 구조가 운영 표준으로 기능하려면 단순 경유 원칙을 넘어 **파일 계약, 완료 신호, 무결성·업무 대사, 멱등성, 구간별 상태와 RACI, 암호화·악성코드 검사, SLA·재처리·DR**를 함께 구현해야 한다. 가장 중요한 통제는 작성 중 파일 소비 방지, File ID 기반 중복 차단, 전송 성공과 업무 반영 성공의 분리, FOS–대외MCA 간 종단 가시성이다.

