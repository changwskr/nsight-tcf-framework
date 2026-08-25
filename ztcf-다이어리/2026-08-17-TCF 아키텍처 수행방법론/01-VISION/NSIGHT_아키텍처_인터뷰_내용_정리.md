# NSIGHT 아키텍처 인터뷰 내용 정리

## 1. 문서 목적

본 문서는 농협 상호금융 NSIGHT 차세대 정보계 구축 관련 인터뷰 Q&A 화면을 기준으로,
미들웨어·클라우드/네트워크·관제·데이터베이스·인터페이스 영역의 질문과 답변을 정리하고
향후 아키텍처 설계 및 확인이 필요한 항목을 식별하기 위한 Working Document이다.

> 작성 원칙
> - 인터뷰 화면에서 확인되는 내용을 우선 기록한다.
> - 답변과 아키텍처 해석을 구분한다.
> - 미확정 사항은 확정 사실처럼 기술하지 않는다.
> - 후속 설계가 필요한 사항은 Architecture Action으로 관리한다.

---

## 2. 인터뷰 전체 요약

| No | 영역 | 주요 주제 | 인터뷰 답변/현재 방향 | Architecture Action |
|---:|---|---|---|---|
| 1 | 미들웨어 | DB 연결정보·파라미터 관리 | 일반 파라미터는 변경 추가가 많지 않으며, DB 연결정보는 보안정책 등에 따라 변경 가능 | Configuration과 Secret 관리 분리 |
| 2 | 공통질의 | Redis/IMDG 운영주체 | 테크시스템부에서 Redis를 관리하지 않으며 도입 시 자체 관리 필요 | Redis 운영주체·HA·백업·관제 정의 |
| 3 | 미들웨어 | 거래로그 통합관리 | ELK 기반 로그 수집/관리 방향 | 중앙집중 Logging Architecture 정의 |
| 4 | 미들웨어 | NH Cloud Framework | 현재 PaaS 중심 운영, IaaS 환경 적용 시 별도 검토 필요 | PaaS→IaaS Gap 분석 |
| 5 | 미들웨어 | 거래 Timeout | WEB/WAS 등 구간별 설정 및 변경 필요 시 조정 | End-to-End Timeout Matrix 작성 |
| 6 | 통신/미들웨어 | WEB/WAS 인프라 활용 | 기존 운영장비 활용 가능 여부 및 L4/L7 구성 검토 | 기존 인프라 재사용 범위 확정 |
| 7 | 관제 | APM/모니터링 | 단위업무는 Scouter, 대규모 프로젝트는 InterMax/e2e 계열 사례 | APM 표준 및 통합관제 범위 결정 |
| 8 | 미들웨어 | Git/배포/Parsing | Git 구성 및 배포방안, 데이터 Parsing 관련 검증 필요 | CI/CD 및 전문 Parsing 검증 |
| 9 | 클라우드/네트워크 | DNS/GSLB | DNS/GSLB 사용 및 운영환경 구성 변경 검토 | DNS/GSLB 상세 설계 |
| 10 | 관계 | 배치 제어/자동화 | 배치/서버 운영 자동화 구성 검토 | Batch Scheduler와 운영자동화 책임 분리 |
| 11 | 관제 | HW/SW 관제 | 기존 관제환경 활용, CPU/MEM 등 인프라 지표 수집 | Infra+APM+Transaction 통합관제 |
| 12 | DB | DB 정책/지원 | 규정화된 정책은 제한적이며 필요 시 운영배포/교육자료 활용 | 프로젝트 DB 표준정책 정의 |
| 13 | DB | DB 표준화 | 데이터시스템 표준화 가이드 참조 | DB Object/SQL/데이터 표준 적용 |
| 14 | DB | DB 성능관리 | 별도 성능관리용 SW 제공 없음 | AWR/ASH/APM/Slow SQL 기반 성능관리 설계 |
| 15 | DB | 과거/장기 데이터 | 장기 데이터 처리·보관 관련 참고 기준 존재 | ILM/Archive/Purge 정책 수립 |
| 16 | DB | DBA 권한 | DBA Role 부여는 제한됨 | 최소권한 기반 운영권한 Matrix |
| 17 | 인터페이스 | 타 시스템 온라인 연계 | FOS MAX HTTP Connector, 표준전문 등 활용 | Online Interface 표준 정의 |
| 18 | 인터페이스 | 대용량 파일 | FOS Proxy 및 대용량 파일 전송 고려 | 일반 온라인과 분리된 File/MFT Architecture |

---

## 3. 미들웨어 인터뷰

### 3.1 DB 연결정보 및 파라미터 관리

#### 질문 요지
- 애플리케이션의 DB 연결정보와 파라미터를 Framework Master에서 수신하여 기동하는지
- 파라미터 및 DB 연결정보의 변경주기
- 운영 시 고려해야 할 사항

#### 인터뷰 답변 요약
- 일반 파라미터 정보는 별도의 빈번한 변경이 많지 않음.
- DB 연결정보는 보안정책 등에 따라 일정 주기로 변경될 수 있음.
- Master 솔루션을 통한 일괄관리 여부를 검토할 수 있음.
- 업무에서 변경 가능한 파라미터는 별도 관리 가능.
- DB Password와 같은 민감정보의 관리방식은 별도 고려가 필요함.

#### 아키텍처 정리

```text
Application Configuration
├─ 일반 Application Parameter
├─ 업무 Parameter
├─ DB URL / User
└─ DB Password / Secret
          │
          └─ 보안 관리영역으로 분리
```

#### Architecture Action
1. 일반 환경설정과 Secret을 분리한다.
2. DB Password를 일반 설정파일에 평문 저장하지 않는 방안을 정의한다.
3. 설정 변경 시 재기동 필요 여부와 Dynamic Refresh 가능 범위를 정의한다.
4. 설정 변경 이력·승인·배포 절차를 운영통제와 연결한다.

---

### 3.2 Redis / IMDG

#### 질문 요지
차세대 시스템에서 IMDG 용도로 Redis를 도입할 경우 기존 관리조직에서 관리 가능한지 여부.

#### 인터뷰 답변 요약
- 테크시스템부에서 Redis를 관리하는 구조는 아님.
- Redis 도입 시 프로젝트/도입 조직 차원의 자체 관리가 필요하다는 방향.

#### Architecture Action

Redis 도입 여부를 결정하기 전에 다음 운영 책임을 명확히 해야 한다.

```text
Redis Lifecycle
설치
 → Configuration
 → HA
 → Backup/Restore
 → Monitoring
 → 장애대응
 → Patch/Upgrade
 → Capacity
```

특히 `운영주체`, `장애 대응주체`, `백업주체`, `관제주체`를 R&R로 명문화한다.

---

### 3.3 거래로그 / ELK

#### 인터뷰 답변 요약
거래로그를 중앙에서 관리하기 위한 ELK 기반 수집/관리 방향이 확인된다.

#### 목표 구조

```text
WEB / WAS / Application
        │
        ├─ Access Log
        ├─ Framework Log
        ├─ Transaction Log
        ├─ Error Log
        └─ Audit Log
              │
              ▼
        Log Collector
              │
              ▼
             ELK
        ┌─────┼─────┐
        ▼     ▼     ▼
       검색   관제   장애분석
```

#### 공통 추적키 권고
- GUID / Trace ID
- ServiceId
- 사용자/조작자 식별정보
- Application
- Hostname / JVM Instance
- 거래 시작·종료시각
- 처리시간
- Result/Error Code

---

### 3.4 NH Cloud Framework와 IaaS

#### 인터뷰 핵심
현재 업무 환경이 PaaS 운영모델을 전제로 하는 부분이 있으며,
NSIGHT가 IaaS 기반으로 구성될 경우 PaaS가 제공하던 기능을 누가 대신 수행할지 확인해야 한다.

#### PaaS → IaaS Gap

| PaaS에서 제공 가능한 기능 | IaaS 전환 시 검토사항 |
|---|---|
| Runtime 관리 | Apache/Tomcat/JVM 직접 운영 |
| 배포 | CI/CD 및 배포자동화 구축 |
| Scale | L4/GSLB 및 Scale-Out 설계 |
| Configuration | 중앙 환경설정 관리 |
| Secret | Secret 관리체계 |
| Monitoring | Infra/APM/Transaction 관제 |
| Logging | ELK 연계 |
| 장애복구 | HA/DR 및 운영절차 |
| Patch | OS/Middleware/JDK 패치 책임 |
| Capacity | CPU/MEM/Thread/Pool 용량관리 |

> 핵심 확인사항: **PaaS가 해주던 기능 중 IaaS 전환 후 NSIGHT가 직접 책임져야 하는 기능을 식별한다.**

---

### 3.5 Timeout

#### 인터뷰 요지
WEB/WAS 등의 거래 Timeout 설정 기준과 변경 가능 여부에 대한 확인.

#### Architecture Principle

Timeout은 하나의 숫자가 아니라 구간별 계층으로 정의해야 한다.

```text
DB Query Timeout
      <
Application Transaction Timeout
      <
WEB / Proxy / External Read Timeout
      <
Client Timeout
```

#### 필수 산출물: Timeout Matrix

| 구간 | 설정항목 | 기준값 | 변경주체 | 비고 |
|---|---|---:|---|---|
| DB | Query Timeout | TBD | 프로젝트/DB | SQL 수행한도 |
| Spring | Transaction Timeout | TBD | 프로젝트 | 업무거래 전체한도 |
| 외부연계 | Connect Timeout | TBD | 프로젝트 | 접속 실패 판단 |
| 외부연계 | Read Timeout | TBD | 프로젝트 | 응답대기 |
| Apache/L4 | Proxy/Idle Timeout | TBD | 인프라 | 상위 Timeout |
| Client | Request Timeout | TBD | UI/단말 | 최상위 Timeout |

---

## 4. 클라우드 / 네트워크

### 4.1 DNS / GSLB

#### 인터뷰 답변 요약
- DNS/GSLB 적용 여부 검토가 필요함.
- 차세대 운영환경 구성에 따라 기존 운영환경과 구성 변경 가능성이 있음.

#### 확인 필요사항
- 서비스 FQDN
- GSLB 적용 대상
- 주센터/DR센터 DNS 정책
- Health Check 방식
- Fail-Over/Fail-Back 절차
- TTL
- L4와 GSLB의 책임경계
- DR 전환 시 Session/DB/연계 영향

---

## 5. 관제 Architecture

### 5.1 Application Monitoring

인터뷰 화면에서는 다음 제품/방식이 언급된다.

- Scouter
- InterMax
- e2e 계열 APM/모니터링

제품명 자체보다 **어떤 계층을 누가 관제하는가**가 중요하다.

### 5.2 통합관제 목표

```text
                    통합 관제
                       │
       ┌───────────────┼───────────────┐
       ▼               ▼               ▼
 Infrastructure      Application       Transaction
 CPU/MEM/DISK       JVM/Tomcat         ServiceId
 Network            Thread/GC          TPS
 Process            HikariCP           Response Time
                     SQL               Error Rate
```

### 5.3 핵심 운영지표

| 계층 | 주요 지표 |
|---|---|
| OS/VM | CPU, Memory, Disk, Network, Process |
| JVM | Heap, Metaspace, GC Pause, Thread |
| Tomcat | Busy Thread, maxThreads, Connection |
| DB Pool | Active, Idle, Pending, Timeout |
| DB | Session, Slow SQL, Wait, Lock |
| 거래 | TPS, p95/p99, Error Rate |
| 로그 | Error Code, Trace/GUID, ServiceId |

---

## 6. 배치 및 운영자동화

### 인터뷰 요지
Control-M과 같은 배치 제어 또는 서버 운영자동화 구성에 대한 검토가 필요하다.

### Architecture Boundary

```text
[업무 Batch Scheduler]
├─ Schedule
├─ 선후행
├─ Dependency
├─ Retry / Restart
├─ Failure Handling
└─ SLA

[Server Operation Automation]
├─ Process Start/Stop
├─ Deployment
├─ Health Check
├─ Configuration
└─ 장애복구
```

> **업무 배치 스케줄링과 서버 운영자동화는 별도 책임영역으로 정의한다.**

---

## 7. 데이터베이스 인터뷰

### 7.1 DB 운영정책

인터뷰 결과, 모든 프로젝트 운영기준을 커버하는 단일 DB 정책이 제공되는 구조로 보기는 어렵다.
따라서 프로젝트 DB Architecture에서 필요한 기준을 명시적으로 정의해야 한다.

### 7.2 데이터/DB 표준화

데이터시스템의 표준화 가이드 및 관련 표준 프로세스를 참고하는 방향이 확인된다.

적용 대상:
- 테이블명
- 컬럼명
- 데이터타입
- PK/FK
- Index
- View
- Procedure
- SQL 작성기준
- 데이터 표준용어

### 7.3 DB 성능관리

#### 인터뷰 답변
별도 DB 성능관리용 SW가 제공되지 않는다는 취지의 답변이 확인된다.

#### Architecture Action
다음 수단을 조합한 성능관리 기준을 정의한다.

```text
APM
 + Slow SQL
 + Oracle AWR/ASH
 + DB Session/Wait
 + HikariCP Metric
 + Transaction Log
        ↓
End-to-End DB Performance Analysis
```

### 7.4 장기 데이터 / ILM

과거 데이터 또는 장기 데이터의 처리·보관과 관련된 기준/참고 문서가 존재하는 것으로 확인된다.

프로젝트에서는 다음 정책을 구체화한다.

- Online 보관기간
- Archive 전환시점
- ILM 기준
- Partition 정책
- Purge 기준
- 백업/복구
- 장기보관 데이터 조회방식
- 개인정보/민감정보 보존정책과의 정합성

### 7.5 DBA 권한

#### 인터뷰 답변
DBA Role과 같은 광범위한 권한 부여는 제한되는 방향.

#### Architecture Principle

```text
최소권한
  +
업무별 Role
  +
운영/개발 분리
  +
DDL/DML 권한 분리
  +
승인/감사
```

별도 `DB Account & Privilege Matrix`를 작성한다.

---

## 8. 인터페이스 Architecture

### 8.1 온라인 인터페이스

인터뷰에서는 다음 요소가 확인된다.

- FOS MAX HTTP Connector
- HTTP 기반 연계
- 표준전문
- FOS 관련 연계기준

따라서 온라인 연계는 다음 구조로 표준화할 필요가 있다.

```text
NSIGHT Application
       │
       ▼
TCF / Integration Layer
       │
       ▼
HTTP Connector / FOS
       │
       ▼
대외/타 시스템
```

필수 정의항목:
- 표준전문
- Header
- Service/Transaction ID
- Timeout
- Retry
- Error Mapping
- Logging
- Security
- Idempotency
- 장애격리

---

### 8.2 대용량 파일

인터뷰 화면에서는 FOS Proxy를 통한 대용량 파일 처리와 수백 GB 규모의 파일에 대한 고려사항이 나타난다.

대용량 파일은 일반 온라인 HTTP 거래와 분리한다.

```text
Online Transaction
HTTP/JSON
짧은 Timeout
Thread 기반
       │
       └─ 업무 API

Mass/File Transfer
MFT / FOS / 전용 File Channel
장시간 전송
재시작/이어받기
무결성 검증
       │
       └─ 대용량 파일
```

#### 확인 필요사항
- 최대 파일크기
- FOS Proxy 제한
- HTTP Protocol 제한
- 전송 Timeout
- 재전송/이어받기
- Checksum
- 암호화
- 임시파일 저장공간
- 파일 삭제정책
- MFT 적용 여부

---

## 9. 인터뷰 기반 요구사항 변환

| 요구사항 ID | 요구사항명 | 요구사항 상세 | 전제조건/제약사항 | 출처 |
|---|---|---|---|---|
| INT-MW-001 | 환경설정 관리 | DB 연결정보와 업무 파라미터를 관리 가능한 구조로 구성 | Secret은 일반 설정과 분리 | 미들웨어 인터뷰 |
| INT-MW-002 | Redis 운영관리 | Redis 도입 시 설치·HA·백업·관제·장애대응 주체 정의 | 기존 관리조직의 관리대상이 아닐 수 있음 | 미들웨어 인터뷰 |
| INT-MW-003 | 중앙 로그관리 | WEB/WAS/Application 거래로그를 중앙수집 | ELK 연계 검토 | 미들웨어 인터뷰 |
| INT-MW-004 | IaaS Framework 운영 | PaaS 기능을 IaaS 환경에서 대체할 운영구조 정의 | 기존 PaaS 운영방식 직접 적용 제한 | 미들웨어 인터뷰 |
| INT-MW-005 | Timeout 표준 | WEB/WAS/App/DB/외부연계 구간별 Timeout 정의 | 계층별 Timeout 정합성 확보 | 공통질의 |
| INT-MON-001 | 통합관제 | Infra·JVM·APM·거래 관제 구성 | 기존 관제와 신규 APM 연계 | 관제 인터뷰 |
| INT-DB-001 | DB 표준 | 데이터/DB Object/SQL 표준 적용 | 데이터시스템 표준 가이드 참조 | DB 인터뷰 |
| INT-DB-002 | DB 성능관리 | SQL/Session/Pool/DB 성능관리 체계 구성 | 별도 성능관리 SW 제공 제한 | DB 인터뷰 |
| INT-DB-003 | DB 권한관리 | 최소권한 기반 계정/Role 정책 적용 | DBA Role 제한 | DB 인터뷰 |
| INT-DB-004 | ILM | 장기 데이터 Archive/Purge/조회정책 정의 | 관련 기준문서 확인 필요 | DB 인터뷰 |
| INT-IF-001 | 표준 온라인 인터페이스 | FOS/HTTP/표준전문 기반 연계표준 정의 | 기존 FOS 환경과 정합성 확보 | 인터페이스 인터뷰 |
| INT-IF-002 | 대용량 파일 | 대용량 파일 전용 전송구조 및 운영정책 정의 | FOS Proxy/MFT 제약 확인 | 인터페이스 인터뷰 |
| INT-NW-001 | DNS/GSLB | 서비스 DNS/GSLB 및 DR 전환구조 정의 | 운영 네트워크 정책 확인 | 클라우드/네트워크 인터뷰 |
| INT-BAT-001 | 배치/운영자동화 | Batch Scheduler와 서버 운영자동화 체계 정의 | 제품/운영주체 확정 필요 | 관계/운영 인터뷰 |

---

## 10. Architecture Decision / Open Issue

### 10.1 우선순위 P0

| ID | 과제 | 완료조건 |
|---|---|---|
| AI-01 | PaaS → IaaS Gap 분석 | 기능별 책임주체와 대체구조 확정 |
| AI-02 | Timeout Matrix | WEB/WAS/App/DB/외부연계 전체 값 확정 |
| AI-03 | Logging/ELK Architecture | 수집대상·공통키·보관·검색 기준 확정 |
| AI-04 | Monitoring Architecture | Infra/APM/거래/DB 관제 경계 확정 |
| AI-05 | DB Performance Architecture | AWR/ASH/APM/Pool/Slow SQL 관리체계 확정 |
| AI-06 | Interface Architecture | FOS/HTTP/표준전문/오류/Timeout 기준 확정 |
| AI-07 | 대용량 File Architecture | FOS/MFT/Proxy/재전송/무결성 기준 확정 |

### 10.2 우선순위 P1

| ID | 과제 |
|---|---|
| AI-08 | Configuration/Secret 관리 |
| AI-09 | Redis 운영모델 |
| AI-10 | Batch Architecture |
| AI-11 | DNS/GSLB 상세설계 |
| AI-12 | ILM/Archive/Purge 정책 |
| AI-13 | DB Account & Privilege Matrix |
| AI-14 | Git/CI/CD/배포 표준 |
| AI-15 | 전문 Parsing 검증 |

---

## 11. 전체 Architecture 연결

```text
[사용자/단말]
      │
      ▼
[DNS / GSLB / L4]
      │
      ▼
[Apache WEB]
      │
      ▼
[Tomcat JVM / Application]
      │
      ├───────────────┐
      │               │
      ▼               ▼
[TCF/업무서비스]   [Redis/IMDG]
      │
      ├───────────────┐
      │               │
      ▼               ▼
 [RDW/ADW DB]    [FOS/HTTP/MFT]
                      │
                      ▼
                  타 시스템

운영 횡단영역
────────────────────────────────────────
Configuration / Secret
Logging / ELK
APM / Monitoring
Timeout
Security
Batch / Automation
HA / DR
DB Performance
```

---

## 12. 결론

이번 인터뷰에서 가장 중요한 점은 제품 목록보다 **운영 책임과 아키텍처 경계**가 드러났다는 것이다.

특히 다음 7개 항목은 NSIGHT Architecture Baseline에 직접 반영해야 한다.

1. PaaS에서 IaaS로 전환할 때 플랫폼 책임의 재배치
2. ELK 기반 중앙 거래로그
3. Infra/APM/Transaction 통합관제
4. End-to-End Timeout
5. DB 성능관리 체계
6. Redis 도입 시 운영주체
7. FOS/HTTP/MFT를 포함한 인터페이스 및 대용량 파일 구조

최종적으로 인터뷰 결과는 단순 Q&A로 종료하지 않고 다음 추적구조로 관리한다.

```text
Interview
   ↓
Requirement
   ↓
Architecture Principle
   ↓
Architecture Decision / Open Issue
   ↓
Logical / Physical / Mechanism Design
   ↓
Configuration / Implementation
   ↓
Test / Evidence
   ↓
Operation Baseline
```

이를 통해 인터뷰에서 나온 답변이 실제 설계·설정·시험·운영 기준으로 이어지도록 관리한다.
