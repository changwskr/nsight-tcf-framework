# NSIGHT OGG 구성도 분석

## 0. 문서 개요

본 문서는 사용자가 제공한 **「OGG 구성도」 장표(4.3 OGG 구성도)**를 기준으로,
NSIGHT 정보계에서 **계정계(Source) → 중계서버(Downstream) → RDW(Target)** 로
변경 데이터를 전달하는 Oracle GoldenGate 기반 실시간 데이터 복제 구조를 분석·정리한 문서이다.

> 원본 장표 핵심 문구  
> **“중계서버를 구성하여 계정계 DB 서버 부하를 최소화함”**

### 작성 원칙

- 원본 장표에서 직접 확인되는 내용은 **[FACT]**로 기록한다.
- 장표의 구조에서 해석되는 아키텍처적 의미는 **[ANALYSIS]**로 구분한다.
- 원본 장표에 없는 OGG 버전, Process Group명, Port, 암호화, Lag 기준 등은 임의로 확정하지 않는다.
- 상세 구현이 확인되지 않는 항목은 **확인 필요(GAP)** 로 관리한다.

---

# 1. 핵심 요약

| 구분 | 구성 |
|---|---|
| Source | 계정계 DB #1 ~ #4 |
| Source 변경정보 | Online / Archived Redo Log |
| Source 전송 프로세스 | `LNSn` |
| Downstream 수신 프로세스 | `RFS` |
| Downstream DB | 중계DB #1 / 중계DB #2 |
| Downstream Redo | Standby Redo Log |
| Downstream OGG | `Extract` + `Pump` |
| Downstream 공유 저장소 | `InfoScale` 기반 Trail File 공유볼륨 |
| Downstream HA | 중계DB #1 ↔ #2 장애 시 Fail Over |
| Target | RDW #1 / RDW #2 |
| Target Trail 저장소 | `ACFS` |
| Target OGG | `Replicat` |
| 최종 목적 | 계정계 Source DB 부하 최소화 + RDW 실시간 변경반영 |

---

# 2. 전체 OGG 아키텍처

```text
                            NSIGHT OGG ARCHITECTURE

┌──────────────────────────────────────────────────────────────────────────┐
│                            계정계 (Source)                               │
│                                                                          │
│   계정계 #1       계정계 #2       계정계 #3       계정계 #4             │
│       │               │               │               │                  │
│       └───────────────┴───────┬───────┴───────────────┘                  │
│                               ▼                                          │
│                             DBMS                                         │
│                               │                                          │
│                    Online / Archived Redo Log                            │
│                               │                                          │
│                              LNSn                                        │
└───────────────────────────────┼──────────────────────────────────────────┘
                                │
                                │ ① Redo 실시간 전송
                                ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                         중계서버 (Downstream)                            │
│                                                                          │
│                    중계DB #1          중계DB #2                          │
│                        │                  │                               │
│                        └──── InfoScale ───┘                               │
│                           장애 시 Fail Over                              │
│                               │                                          │
│                    ┌──────────┴──────────┐                               │
│                    │  Trail File 공유볼륨 │                              │
│                    └──────────┬──────────┘                               │
│                               │                                          │
│                              DBMS                                        │
│                               │                                          │
│                       Standby Redo Log                                   │
│                               ▲                                          │
│                              RFS                                         │
│                               │                                          │
│                    ② Extract / Log Mining                               │
│                               │                                          │
│                               ▼                                          │
│                           Trail File                                     │
│                               │                                          │
│                              Pump                                        │
└───────────────────────────────┼──────────────────────────────────────────┘
                                │
                                │ ③ Trail File 전송
                                ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                             RDW (Target)                                 │
│                                                                          │
│                       RDW #1          RDW #2                              │
│                          │              │                                 │
│                          └──── ACFS ─────┘                                │
│                               │                                          │
│                           Trail File                                     │
│                               │                                          │
│                         ④ Replicat                                       │
│                               │                                          │
│                               ▼                                          │
│                              DBMS                                        │
└──────────────────────────────────────────────────────────────────────────┘
```

---

# 3. 구성 영역별 역할

## 3.1 계정계(Source)

### [FACT]

원본 장표의 Source 영역에는 다음 구성이 표시되어 있다.

```text
계정계(Source)
│
├─ 계정계 #1
├─ 계정계 #2
├─ 계정계 #3
└─ 계정계 #4
     │
     ▼
    DBMS
     │
     ▼
Online / Archived Redo Log
     │
     ▼
    LNSn
```

### 주요 책임

| 항목 | 역할 |
|---|---|
| 계정계 DB | 실제 업무 트랜잭션 수행 |
| Redo Log | 트랜잭션 변경정보 기록 |
| LNSn | 중계서버 방향 Redo 데이터 전송 |

### [ANALYSIS]

이 설계의 핵심은 Source DB에 OGG Extract를 직접 배치하여
Source Redo를 직접 Mining하는 구조가 아니라,
**Source의 Redo를 Downstream DB로 전달한 뒤 Downstream에서 Extract를 수행**하는 데 있다.

즉 Source DB의 역할을 다음 수준으로 제한하려는 의도가 명확하다.

```text
계정계 업무 처리
+
Redo 생성
+
Redo 전송

        ↓

실제 변경정보 Mining은 중계서버에서 수행
```

---

# 4. Downstream 중계서버

## 4.1 [FACT] 중계서버 구성

원본 장표에서는 중계서버를 다음과 같이 구성한다.

```text
중계서버(Downstream)
│
├─ 중계DB #1
│
├─ 중계DB #2
│
├─ 장애 시 Fail Over
│
├─ InfoScale
│   └─ Trail File 공유볼륨
│
├─ DBMS
│   └─ Standby Redo Log
│
├─ RFS
├─ Extract
└─ Pump
```

중계서버는 단순 네트워크 Relay가 아니라 다음 기능을 동시에 가진다.

1. Source Redo 수신
2. Standby Redo Log 유지
3. Extract 수행
4. Trail File 생성
5. Trail File 공유
6. Target으로 Trail File 전송
7. 중계 노드 Fail Over

---

# 5. LNSn → RFS Redo 전송

## 5.1 [FACT]

원본 장표의 ① 단계 설명:

> **Source DB에서 Downstream DB Redo 전송**  
> 계정계(Source) DB에서 발생한 트랜잭션 변경 정보는 Redo Log에 기록되며,  
> LNSn 프로세스를 통해 중계서버의 Standby Redo Log로 실시간 전송

### 흐름

```text
계정계 Transaction
        │
        ▼
Online / Archived Redo Log
        │
       LNSn
        │
        │ 실시간 Redo 전송
        ▼
       RFS
        │
        ▼
Standby Redo Log
```

## 5.2 용어

| 약어 | 원본 정의 |
|---|---|
| `LNSn` | Log Network Server |
| `RFS` | Remote File Server |

---

# 6. Downstream Extract 수행

## 6.1 [FACT]

원본 장표의 ② 단계 설명:

> **Downstream Extract 수행**  
> 중계서버의 Extract 프로세스가 Standby Redo Log를 Log Mining하여  
> Commit된 변경 데이터를 추출하고 Trail File로 생성

### 흐름

```text
Standby Redo Log
       │
       ▼
    Extract
       │
       │ Log Mining
       │ Commit된 변경정보 추출
       ▼
   Trail File
```

## 6.2 Extract의 원본 정의

> **Extract : Log File을 읽어 Commit된 변경 데이터를 추출하여 Trail File로 저장하는 프로세스**

### [ANALYSIS]

이 부분이 계정계 부하 최소화 설계의 핵심 실행 지점이다.

```text
Source에서 Mining
      X

Downstream에서 Mining
      O
```

즉 Source DB의 업무 SQL 처리와 CDC Mining 처리의 자원경합을 줄이고,
실시간 복제용 Processing Cost를 Downstream으로 이동시키는 구조이다.

---

# 7. InfoScale 공유볼륨 및 중계 HA

## 7.1 [FACT]

중계DB #1과 중계DB #2 사이에는 다음 구성이 표현되어 있다.

```text
중계DB #1
    │
    ├──── InfoScale ────┐
    │                   │
    │   Trail File      │
    │   공유볼륨         │
    │                   │
    └────────────── 중계DB #2

        장애 시 Fail Over
```

### [ANALYSIS]

원본 장표의 의도는 중계서버의 단일 장애점을 줄이는 것이다.

중계노드 한 대에서 장애가 발생하더라도
공유 Trail File을 다른 중계노드가 사용할 수 있도록 하여
중계 기능을 Fail Over하는 구조로 해석할 수 있다.

다만 장표만으로는 다음을 확정할 수 없다.

- Active-Standby인지 Active-Active인지
- Extract 프로세스의 자동 재기동 정책
- Pump 프로세스의 자동 재기동 정책
- OGG Checkpoint 파일의 공유 방식
- InfoScale Cluster Resource 설정
- Failover 감지시간
- Failover 후 Process Group Start 순서

---

# 8. Pump를 통한 Trail File 전송

## 8.1 [FACT]

원본 장표의 ③ 단계 설명:

> **Trail File 전송(Pump)**  
> 중계서버의 Pump 프로세스는 생성된 Trail File을  
> Target(RDW) 서버의 Trail File 저장소(ACFS)로 전송

### 흐름

```text
Downstream Trail File
        │
        ▼
       Pump
        │
        │ Trail 전송
        ▼
Target RDW
        │
        ▼
ACFS Trail File
```

## 8.2 Pump의 원본 정의

> **Pump : Target으로 Trail File을 배포하는 프로세스**

### [ANALYSIS]

Pump를 별도 단계로 두면
Extract와 Target 전송 책임이 분리된다.

```text
Extract
= Redo Mining + Local Trail 생성

Pump
= Local Trail → Target Trail 전달
```

따라서 Target 네트워크 장애나 일시적 Target 장애가 발생하더라도
Extract와 Source Redo 수집을 가능한 범위에서 분리하여 관리할 수 있는 구조로 볼 수 있다.

단, 실제 장애 시 Trail Retention 기간이나 디스크 용량 기준은 장표에서 확인되지 않는다.

---

# 9. RDW(Target) 구성

## 9.1 [FACT]

원본 장표의 Target 영역은 다음과 같다.

```text
RDW(Target)
│
├─ RDW #1
├─ RDW #2
│
├─ ACFS
│   └─ Trail File
│
├─ Replicat
│
└─ DBMS
```

## 9.2 [ANALYSIS]

앞 장의 DB 이중화 자료에서 RDW가 RAC 구조로 정의되어 있으므로,
이번 장표의 `RDW #1 / RDW #2 + ACFS`는
Target 측 이중화 DB 환경에서 Trail File을 공유하고 Replicat이 적용하는 형태로 연결해서 볼 수 있다.

다만 **Replicat 자체가 어느 노드에서 실행되는지**,
Active/Standby 구성인지,
Cluster Resource로 관리되는지는 이번 장표에서 확정되지 않는다.

---

# 10. Replicat Target 반영

## 10.1 [FACT]

원본 장표의 ④ 단계 설명:

> **Target DB 반영(Replicat)**  
> Target(RDW) 서버의 Replicat 프로세스가 Trail File을 읽어  
> Source DB에 발생한 변경사항을 Target DB에 동일하게 반영

### 흐름

```text
ACFS Trail File
       │
       ▼
   Replicat
       │
       ▼
Target DBMS
       │
       ▼
Source 변경사항 반영
```

## 10.2 Replicat의 원본 정의

> **Replicat : Target DB에 Trail File을 적용하는 프로세스**

---

# 11. ACFS 구성

## 11.1 [FACT]

Target RDW의 Trail File 저장영역은 `ACFS`로 표시되어 있다.

원본 약어 정의:

> **ACFS : Advanced Cluster File System**

구조:

```text
RDW #1
   │
   ├────── ACFS ──────┐
   │                  │
   │    Trail File    │
   │                  │
   └────────────── RDW #2
```

### [ANALYSIS]

ACFS는 RAC 노드 간 공유 가능한 Trail File 저장영역으로 사용되는 것으로 표현된다.

이에 따라 Replicat 실행노드가 변경되더라도
Target Trail을 동일한 공유 파일시스템에서 접근할 수 있도록 하는 목적을 가진 것으로 해석할 수 있다.

---

# 12. End-to-End 데이터 흐름

```text
1. Source Transaction
        │
        ▼
2. Online / Archived Redo Log
        │
        ▼
3. LNSn
        │
        │ Redo Network Transfer
        ▼
4. RFS
        │
        ▼
5. Downstream Standby Redo Log
        │
        ▼
6. Extract
        │
        │ Log Mining
        ▼
7. InfoScale Shared Trail File
        │
        ▼
8. Pump
        │
        │ Trail Distribution
        ▼
9. RDW ACFS Trail File
        │
        ▼
10. Replicat
        │
        ▼
11. RDW Target DBMS
```

한 줄로 표현하면 다음과 같다.

```text
Source Redo
→ LNSn
→ RFS
→ Standby Redo
→ Extract
→ Trail
→ Pump
→ ACFS Trail
→ Replicat
→ RDW
```

---

# 13. 단계별 원본 설명 통합표

| No | 단계 | 원본 설명 요약 | 주요 프로세스 |
|---:|---|---|---|
| ① | Source DB에서 Downstream DB Redo 전송 | Source 트랜잭션 변경정보를 Redo Log에 기록하고 LNSn으로 중계서버 Standby Redo Log에 실시간 전송 | LNSn / RFS |
| ② | Downstream Extract 수행 | Standby Redo Log를 Log Mining하여 Commit된 변경 데이터를 추출하고 Trail File 생성 | Extract |
| ③ | Trail File 전송(Pump) | 중계서버 Trail File을 Target(RDW)의 ACFS Trail 저장소로 전송 | Pump |
| ④ | Target DB 반영(Replicat) | Target의 Trail File을 읽어 Source 변경사항을 Target DB에 동일하게 적용 | Replicat |

---

# 14. OGG 주요 컴포넌트

| 컴포넌트 | 위치 | 책임 |
|---|---|---|
| LNSn | Source DB | Source Redo 전송 |
| RFS | Downstream DB | Source에서 전달된 Redo 수신 |
| Standby Redo Log | Downstream DB | 실시간 전달 Redo 저장 |
| Extract | Downstream | Redo Log Mining 및 변경정보 추출 |
| Trail File | Downstream / Target | OGG 변경 데이터 전달 파일 |
| InfoScale | Downstream | 중계 Trail 공유 및 Fail Over 기반 |
| Pump | Downstream | Target으로 Trail 전송 |
| ACFS | Target RDW | Target Trail 공유 저장소 |
| Replicat | Target RDW | Trail 변경사항을 Target DB에 적용 |
| DBMS | Source / Downstream / Target | 데이터베이스 처리 |

---

# 15. 아키텍처 의도

## 15.1 Source DB 부하 최소화

원본 장표의 가장 중요한 설계 목적이다.

```text
[일반 Source Extract 구조]

Source DB
├─ 업무 SQL
├─ Redo 생성
└─ OGG Extract / Mining
        ↑
   Source 부하 증가 가능


[본 장표 구조]

Source DB
├─ 업무 SQL
├─ Redo 생성
└─ Redo 전송
       │
       ▼
Downstream
└─ Extract / Mining
```

### [ANALYSIS]

즉 **Log Mining 처리부하를 중계서버로 Offload**하는 구조이다.

---

# 16. 고가용성 관점

이번 장표에서 고가용성이 고려된 구간은 크게 두 곳이다.

## 16.1 Downstream HA

```text
중계DB #1
     ↕
InfoScale
     ↕
중계DB #2

장애 시 Fail Over
```

## 16.2 Target RDW HA

```text
RDW #1
   ↘
    ACFS Shared Trail
   ↗
RDW #2
```

### [ANALYSIS]

전체 구조는 다음과 같이 단일 장애점을 줄이려는 형태이다.

```text
Source DB Cluster
      │
      ▼
Downstream Cluster
      │
      ▼
Target RDW Cluster
```

그러나 각 Cluster의 실제 Failover 자동화 방식은 별도 상세 설계가 필요하다.

---

# 17. OGG와 RDW 아키텍처의 연결

앞 장의 DB 아키텍처에서 표현된:

```text
계정계
   │
   ▼
CDC 중계
   │
   ▼
RDW
```

를 이번 장표는 물리/프로세스 수준으로 다음과 같이 구체화한다.

```text
계정계
   │
   ▼
LNSn
   │
   ▼
RFS / Standby Redo
   │
   ▼
Downstream Extract
   │
   ▼
Trail File
   │
   ▼
Pump
   │
   ▼
RDW ACFS
   │
   ▼
Replicat
   │
   ▼
RDW DB
```

따라서 이번 장표는 **“CDC 중계”의 실제 OGG 처리 메커니즘**을 설명하는 자료로 볼 수 있다.

---

# 18. 아키텍처 Rule 후보

| Rule ID | 규칙 | 상태 |
|---|---|---|
| `OGG-001` | 계정계 Source의 변경정보는 Redo 기반으로 수집한다 | 장표 근거 |
| `OGG-002` | Source DB에서 직접 Extract하지 않고 Downstream 중계서버에서 Extract를 수행한다 | 장표 근거 |
| `OGG-003` | Source Redo는 LNSn → RFS를 통해 Downstream Standby Redo Log로 전달한다 | 장표 근거 |
| `OGG-004` | Downstream Extract는 Commit된 변경정보를 Trail File로 생성한다 | 장표 근거 |
| `OGG-005` | Downstream Trail은 Pump를 통해 RDW Target으로 전송한다 | 장표 근거 |
| `OGG-006` | Target Trail 저장소는 ACFS를 사용한다 | 장표 근거 |
| `OGG-007` | Target Replicat은 Trail File을 읽어 RDW DB에 변경사항을 반영한다 | 장표 근거 |
| `OGG-008` | Downstream은 중계DB #1/#2 Fail Over 구조를 가진다 | 장표 근거 |
| `OGG-009` | 중계 Trail 공유영역은 InfoScale을 사용한다 | 장표 근거 |
| `OGG-010` | Source DB 부하 최소화를 위해 Log Mining을 Downstream으로 분리한다 | 장표 의도 |
| `OGG-011` | OGG Lag과 Trail 적체를 운영 모니터링 항목으로 관리해야 한다 | 제안 |
| `OGG-012` | Failover 시 Extract/Pump/Replicat 재기동 순서를 운영절차로 정의해야 한다 | 제안 |

---

# 19. 확인 필요 GAP

원본 장표만으로는 다음을 확정할 수 없다.

| GAP ID | 항목 | 상태 |
|---|---|---|
| `GAP-OGG-001` | Oracle GoldenGate 정확한 버전 | 미표기 |
| `GAP-OGG-002` | Extract Group명 | 미표기 |
| `GAP-OGG-003` | Pump Group명 | 미표기 |
| `GAP-OGG-004` | Replicat Group명 | 미표기 |
| `GAP-OGG-005` | Trail File Prefix / Directory | 미표기 |
| `GAP-OGG-006` | Checkpoint Table/File 정책 | 미표기 |
| `GAP-OGG-007` | 중계 #1/#2 Active/Standby 상세 | 미표기 |
| `GAP-OGG-008` | InfoScale Cluster Resource 상세 | 미표기 |
| `GAP-OGG-009` | Extract/Pump 자동 Failover | 미표기 |
| `GAP-OGG-010` | RDW Replicat HA 방식 | 미표기 |
| `GAP-OGG-011` | ACFS Mount / Ownership | 미표기 |
| `GAP-OGG-012` | OGG Network Port | 미표기 |
| `GAP-OGG-013` | 전송구간 암호화/TLS | 미표기 |
| `GAP-OGG-014` | Trail File Encryption | 미표기 |
| `GAP-OGG-015` | Lag 허용 기준 | 미표기 |
| `GAP-OGG-016` | Trail Retention 기준 | 미표기 |
| `GAP-OGG-017` | 디스크 Full 예방 기준 | 미표기 |
| `GAP-OGG-018` | DDL Replication 여부 | 미표기 |
| `GAP-OGG-019` | 대용량 Transaction 처리 정책 | 미표기 |
| `GAP-OGG-020` | 장애 재처리 / 재동기화 정책 | 미표기 |
| `GAP-OGG-021` | Initial Load 방식 | 미표기 |
| `GAP-OGG-022` | 데이터 정합성 검증 방법 | 미표기 |
| `GAP-OGG-023` | Source 4개 DB와 Extract 매핑 | 상세 미표기 |
| `GAP-OGG-024` | Target Table/Schema 매핑 | 미표기 |
| `GAP-OGG-025` | OGG Monitoring 도구/대시보드 | 장표 미표기 |

---

# 20. 운영 모니터링 권장항목

아래는 원본 장표에 직접 적힌 값이 아니라,
본 구조를 실제 운영 Baseline으로 만들기 위한 **[ANALYSIS] 권장 관리항목**이다.

| 영역 | 모니터링 항목 |
|---|---|
| Source | Redo 생성량 |
| Redo Transport | LNSn 전송 상태 |
| Downstream | RFS 수신 상태 |
| Downstream | Standby Redo 적체 |
| Extract | Process Status |
| Extract | Extract Lag |
| Trail | Trail 생성량 |
| Trail | Disk Usage |
| Pump | Pump Status |
| Pump | Network Lag |
| Target | Target Trail 적체 |
| Replicat | Replicat Status |
| Replicat | Apply Lag |
| Replicat | Discard / Error |
| HA | InfoScale Cluster 상태 |
| Target FS | ACFS 상태 |
| End-to-End | Source Commit → Target Apply 지연 |

---

# 21. 장애 시나리오 후보

| Test ID | 장애 | 검증내용 |
|---|---|---|
| `OGGT-001` | 중계DB #1 장애 | #2 Fail Over 및 Extract/Pump 복구 |
| `OGGT-002` | 중계DB #2 장애 | 중계 서비스 지속성 |
| `OGGT-003` | InfoScale 공유볼륨 장애 | Trail 접근 영향 |
| `OGGT-004` | Source→Downstream Network 장애 | Redo 전송 재개/복구 |
| `OGGT-005` | RFS 장애 | Standby Redo 수신 복구 |
| `OGGT-006` | Extract 장애 | Checkpoint 기반 재기동 |
| `OGGT-007` | Pump 장애 | Trail 재전송 |
| `OGGT-008` | RDW Network 장애 | Target Trail 적체/복구 |
| `OGGT-009` | ACFS 장애 | Replicat 영향 |
| `OGGT-010` | Replicat 장애 | Apply 재기동 및 정합성 |
| `OGGT-011` | Target DB 장애 | RDW RAC 및 Replicat 영향 |
| `OGGT-012` | Trail Disk Full | 서비스 보호/알림 |
| `OGGT-013` | 장시간 Target 중단 | Trail Retention 및 재처리 |
| `OGGT-014` | 중계 전체 장애 | Source Redo 보존시간 및 복구 가능성 |
| `OGGT-015` | 데이터 불일치 | 재동기화 절차 |

---

# 22. 최종 Big Picture

```text
                      NSIGHT OGG / CDC BIG PICTURE

┌──────────────────── SOURCE ─────────────────────┐
│                                                 │
│ 계정계 #1  #2  #3  #4                          │
│       │                                         │
│      DBMS                                       │
│       │                                         │
│ Online / Archived Redo                          │
│       │                                         │
│      LNSn                                       │
└───────┼─────────────────────────────────────────┘
        │
        │ Redo Transport
        ▼
┌──────────────── DOWNSTREAM ─────────────────────┐
│                                                 │
│       RFS                                       │
│        │                                        │
│ Standby Redo Log                                │
│        │                                        │
│     Extract                                     │
│        │                                        │
│    Trail File                                   │
│        │                                        │
│ ┌──── InfoScale Shared Volume ────┐             │
│ │   중계DB #1 ↔ 중계DB #2         │             │
│ │      장애 시 Fail Over           │             │
│ └──────────────────────────────────┘             │
│        │                                        │
│       Pump                                      │
└───────┼─────────────────────────────────────────┘
        │
        │ Trail Distribution
        ▼
┌───────────────── TARGET RDW ────────────────────┐
│                                                 │
│ RDW #1 ───── ACFS ───── RDW #2                  │
│                │                                │
│           Trail File                            │
│                │                                │
│            Replicat                             │
│                │                                │
│               DBMS                              │
└─────────────────────────────────────────────────┘
```

---

# 23. 결론

이번 OGG 구성도에서 확인되는 핵심은 다음과 같다.

1. **계정계 Source DB 부하를 최소화하기 위해 Downstream 중계서버를 별도로 둔다.**
2. Source의 트랜잭션 변경정보는 Online/Archived Redo Log에 기록된다.
3. `LNSn → RFS`를 통해 Redo가 중계서버의 Standby Redo Log로 실시간 전송된다.
4. `Extract`는 Downstream에서 Standby Redo를 Log Mining하여 Commit된 변경정보를 Trail File로 생성한다.
5. 중계서버는 **중계DB #1/#2 + InfoScale 공유 Trail 볼륨 + 장애 시 Fail Over** 구조로 표현된다.
6. `Pump`가 중계 Trail File을 RDW Target의 ACFS Trail 저장소로 전달한다.
7. Target은 **RDW #1/#2 + ACFS** 구조로 표현된다.
8. `Replicat`은 Target Trail을 읽어 Source의 변경사항을 RDW DB에 동일하게 반영한다.
9. 전체 구조는 **Source Transaction 처리 / CDC Mining / Target Apply를 분리**하여 Source 영향과 장애영역을 줄이려는 구조이다.
10. 운영 Baseline 확정을 위해서는 OGG Group, Checkpoint, Failover 자동화, Lag, Trail Retention, 보안, 정합성 검증 기준을 추가 정의해야 한다.

본 문서는 제공된 장표를 기준으로 한 **NSIGHT OGG / CDC Architecture Working Baseline**으로 활용한다.
