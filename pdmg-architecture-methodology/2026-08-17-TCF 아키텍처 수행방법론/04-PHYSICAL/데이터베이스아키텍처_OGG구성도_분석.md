# 데이터베이스 아키텍처 — OGG 구성도 분석

> 원본 범위: 데이터베이스 아키텍처의 OGG 구성도  
> 원본 목적: `중계서버를 구성하여 계정계 DB 서버 부하를 최소화함`  
> 핵심 패턴: Oracle GoldenGate Downstream Mining + 중계서버 이중화 + Trail File 전달 + RDW Replicat 적용

---

## 1. 핵심 결론

이 구성은 계정계 Source DB에서 직접 GoldenGate Extract를 실행하지 않고, Redo를 별도 중계 DB로 실시간 전송한 뒤 중계서버에서 Log Mining을 수행하는 Downstream Capture 구조다.

- 계정계 4개 DB Server에서 발생한 변경사항은 Online/Archived Redo Log에 기록된다.
- `LNSn`이 Redo를 중계 DB의 `RFS`로 전송하고, RFS는 Standby Redo Log에 기록한다.
- 중계서버의 `Extract`가 Standby Redo Log를 Mining하여 commit된 변경 데이터를 Trail File로 생성한다.
- 중계서버의 `Pump`가 Trail File을 RDW의 ACFS Trail 저장소로 전달한다.
- RDW의 `Replicat`가 Target Trail을 읽어 Source 변경사항을 RDW DB에 동일하게 반영한다.
- 중계 DB #1·#2와 공유 InfoScale 영역은 Extract·Pump 처리의 Failover 기반을 제공한다.

```text
계정계 Source DB
 Online/Archived Redo Log
          │ ① LNSn → RFS
          ▼
중계 DB Standby Redo Log
          │ ② Extract / Downstream Mining
          ▼
InfoScale Trail File
          │ ③ Pump
          ▼
RDW ACFS Trail File
          │ ④ Replicat
          ▼
       RDW DB
```

---

## 2. 근거 수준

| 수준 | 내용 |
|---|---|
| 확인 사실 | Source 영역에 계정계 #1~#4와 공용 DBMS가 표시됨 |
| 확인 사실 | Source Online/Archived Redo가 LNSn과 RFS를 통해 중계 DB로 전달됨 |
| 확인 사실 | 중계서버에 DB #1·#2, Standby Redo Log, Extract, InfoScale Trail, Pump가 표시됨 |
| 확인 사실 | 중계 DB #1에서 #2로 장애 시 Fail Over가 표시됨 |
| 확인 사실 | RDW #1·#2에 ACFS 공유 Trail과 Replicat가 표시됨 |
| 설계 해석 | 계정계 DB CPU·I/O 부하와 OGG 프로세스 장애영향을 중계 계층으로 분리 |
| 설계 해석 | InfoScale과 ACFS는 각 구간의 공유 Trail 저장소 및 failover 연속성을 제공 |
| 미확정 | InfoScale 제품·버전·volume 구성과 fencing 정책 |
| 미확정 | OGG Microservices/Classic Architecture 여부와 프로세스 개수 |
| 미확정 | Extract·Pump·Replicat process group, trail prefix, checkpoint 위치 |
| 미확정 | Redo transport mode, compression, encryption, sync/async 설정 |

---

## 3. 전체 OGG 구성도

```text
┌──────────────────── 계정계(Source) ────────────────────┐
│ 계정계 #1  계정계 #2  계정계 #3  계정계 #4            │
│       └─────────── Source DBMS ────────────┘           │
│            Online / Archived Redo Log                 │
└────────────────────────┬───────────────────────────────┘
                         │ ① LNSn: Redo Transport
                         ▼
┌────────────────── 중계서버(Downstream) ────────────────┐
│                   RFS                                  │
│                    ↓                                   │
│            Standby Redo Log                           │
│                    ↓ ②                                │
│                 Extract                               │
│                    ↓                                   │
│       ┌──── InfoScale Shared Trail File ────┐         │
│       │ 중계 DB #1  ← Failover →  중계 DB #2│         │
│       └────────────────┬─────────────────────┘         │
│                        │ ③ Pump                        │
└────────────────────────┼───────────────────────────────┘
                         ▼
┌────────────────────── RDW(Target) ─────────────────────┐
│ RDW #1 ─────── ACFS Shared Trail File ─────── RDW #2  │
│                         │                              │
│                         ↓ ④ Replicat                  │
│                       RDW DBMS                         │
└────────────────────────────────────────────────────────┘
```

---

## 4. ① Source DB에서 Downstream DB로 Redo 전송

```text
계정계 Transaction Commit
          ↓
Online Redo Log 기록
          ↓
LNSn
          ↓ Network
RFS
          ↓
중계 DB Standby Redo Log
```

장표 설명:

> 계정계(Source) DB에서 발생한 트랜잭션 변경 정보는 Redo Log에 기록되며, LNSn 프로세스를 통해 중계서버의 Standby Redo Log로 실시간 전송된다.

### 주요 프로세스

| 프로세스 | 전체 명칭 | 역할 |
|---|---|---|
| LNSn | Log Network Server | Source Redo를 원격 Downstream으로 전송 |
| RFS | Remote File Server | 전송된 Redo를 수신하여 Standby Redo Log에 기록 |

### 설계 포인트

- Source에는 OGG Extract를 직접 배치하지 않아 Log Mining CPU·I/O 부하를 줄인다.
- Redo 전송 지연은 전체 OGG 지연의 첫 구간이므로 LNS·RFS·network·SRL 상태를 함께 감시해야 한다.
- Source와 Downstream의 DBID·dictionary·supplemental logging 조건을 맞춰야 한다.
- Archived Log만 사용하는 방식보다 Standby Redo Log 실시간 Mining이 낮은 지연을 제공한다.

### 확인사항

- Redo Transport sync/async와 timeout
- Standby Redo Log group 수·크기·thread 매핑
- Source RAC thread별 SRL 구성
- network encryption·compression
- archive log 보존과 gap 해소 절차

---

## 5. ② Downstream Extract 수행

```text
Standby Redo Log
      ↓ Log Mining
Extract
      ↓ Commit transaction 조립
Local Trail File
      ↓
InfoScale 공유영역
```

장표 설명:

> 중계서버의 Extract 프로세스가 Standby Redo Log를 Log Mining하여 commit된 변경 데이터를 추출하고 Trail File로 생성한다.

### Extract의 역할

- Redo record를 읽어 insert·update·delete transaction을 재구성한다.
- commit된 transaction만 downstream trail에 기록한다.
- checkpoint를 통해 재시작 위치를 관리한다.
- long transaction, DDL, LOB, unsupported datatype을 별도로 관리한다.

### InfoScale 공유영역

```text
중계 DB #1
   └─ Extract/Pump ─┐
                    ├─ InfoScale Shared Trail
중계 DB #2          │
   └─ Failover ─────┘
```

InfoScale은 장표상 공유 활동 영역으로 표시된다. 공유 File System 또는 Cluster Volume을 통해 장애 후 다른 중계 노드가 동일 Trail과 checkpoint에 접근하는 구조로 해석된다.

### 중요 통제

- Extract는 동시에 두 노드에서 실행되지 않도록 cluster resource로 단일 활성화한다.
- split-brain 방지를 위한 fencing과 quorum이 필요하다.
- Trail 보존량은 최대 장애시간과 Pump 지연을 수용해야 한다.
- Trail 암호화와 OS 계정 권한을 적용해야 한다.

---

## 6. 중계서버 Failover 메커니즘

```text
[정상]
중계 DB #1: Active Extract/Pump
중계 DB #2: Standby
InfoScale: Shared Trail

[장애]
#1 장애 감지
   ↓
Cluster가 #1 resource 격리
   ↓
InfoScale volume/file system을 #2에서 활성화
   ↓
#2에서 Extract/Pump 재시작
   ↓
Checkpoint 이후부터 처리 재개
```

### 이중화가 보장하려는 것

- 중계서버 한 대 장애 시 Source Redo 수신과 Trail 생성의 지속성
- 기존 Trail·checkpoint를 사용한 이어받기
- Source 계정계 DB에 직접 OGG 프로세스를 재배치하지 않는 장애 격리

### 주의사항

- 장표의 화살표는 DB #1→#2 Failover를 표현하지만 Active-Active OGG 처리로 해석해서는 안 된다.
- process group의 동시 기동을 방지해야 한다.
- failover 중에도 Source Redo가 보존되도록 SRL·Archive 용량을 확보해야 한다.
- RFS·Redo Transport destination의 재연결 또는 VIP 전환 방식이 필요하다.

---

## 7. ③ Trail File 전송(Pump)

```text
InfoScale Local Trail
          ↓ read
         Pump
          ↓ TCP/IP
RDW ACFS Remote Trail
```

장표 설명:

> 중계서버의 Pump 프로세스는 생성된 Trail File을 Target(RDW) 서버의 Trail File 저장소(ACFS)로 전송한다.

### Pump의 역할

- Extract와 network 전송을 분리한다.
- Target 장애·network 단절 시 Local Trail에 변경을 버퍼링한다.
- routing, filtering, mapping 또는 compression을 적용할 수 있다.
- Target Trail의 sequence와 RBA를 연속적으로 관리한다.

### 용량 산정

```text
필요 Local Trail 용량
  = 시간당 Redo/변경량
  × 최대 Target 단절시간
  × 안전계수
```

Pump 지연이 길어지면 InfoScale Trail이 증가하므로 file system 사용률과 oldest trail age를 감시해야 한다.

---

## 8. RDW ACFS Trail 공유 구성

```text
RDW #1 ─┐
         ├─ ACFS Shared Trail File
RDW #2 ─┘
              ↓
          Replicat
```

- ACFS는 `Advanced Cluster File System`으로 장표에 정의되어 있다.
- RDW RAC 양 노드가 동일 Target Trail에 접근할 수 있게 한다.
- Replicat가 failover될 때 Trail 경로와 checkpoint 접근 연속성을 제공한다.
- ACFS mount, cluster resource, permission, free space를 RAC 노드 전체에서 일관되게 관리해야 한다.

---

## 9. ④ Target DB 반영(Replicat)

```text
ACFS Target Trail
        ↓ read
     Replicat
        ↓ SQL apply
      RDW DB
```

장표 설명:

> Target(RDW) 서버의 Replicat 프로세스가 Trail File을 읽어 Source DB에 발생한 변경사항을 Target DB에 동일하게 반영한다.

### Replicat 역할

- Trail transaction을 target SQL로 변환하여 적용한다.
- source–target table mapping과 column transformation을 수행할 수 있다.
- checkpoint를 기록해 재시작 위치를 보장한다.
- 충돌, constraint, missing row, duplicate key 오류를 처리한다.

### 적용 방식 확인

- Classic Replicat / Coordinated Replicat / Integrated Replicat
- 병렬 apply thread 수와 batch SQL
- target commit frequency
- error handling, discard file, exception table
- DDL replication 여부
- heartbeat table과 end-to-end latency

---

## 10. 프로세스·파일·저장소 전체 목록

```text
프로세스
  LNSn
  RFS
  Extract
  Pump
  Replicat

로그·파일
  Online Redo Log
  Archived Redo Log
  Standby Redo Log
  Local Trail File
  Remote Trail File
  Checkpoint

공유 저장소
  InfoScale
  ACFS
```

| 항목 | 위치 | 핵심 역할 |
|---|---|---|
| Online/Archived Redo | Source | 원천 변경 기록과 복구 근거 |
| Standby Redo Log | Downstream | 실시간 Redo 수신·Mining 원천 |
| Local Trail | InfoScale | Extract 결과 저장·전송 버퍼 |
| Remote Trail | RDW ACFS | Replicat 적용 대기 변경 저장 |
| Checkpoint | 각 OGG Process | 재시작 sequence·RBA 관리 |

---

## 11. End-to-End 데이터 흐름

```text
1. 계정계 transaction commit
2. Redo Log 기록
3. LNSn이 중계서버로 Redo 전송
4. RFS가 Standby Redo Log에 기록
5. Extract가 Redo Mining
6. InfoScale Local Trail 생성
7. Pump가 RDW로 Trail 전송
8. ACFS Remote Trail 저장
9. Replicat가 RDW에 적용
10. Heartbeat·대사로 완료 확인
```

### 지연 구성

```text
총 복제 지연
  = Redo 전송 지연
  + Extract 지연
  + Pump 전송 지연
  + Replicat 적용 지연
```

각 구간을 별도 측정해야 병목 위치를 정확히 찾을 수 있다.

---

## 12. 장애 시나리오별 영향

| 장애 | 영향 | 회복 메커니즘 | 확인사항 |
|---|---|---|---|
| Source LNS 장애 | Redo 전송 중단·지연 | DB process 재시작, archive gap 보완 | destination error·gap |
| Network 단절 | RFS 수신 중단 | Local redo 보존 후 재전송 | archive 보존 용량 |
| 중계 DB #1 장애 | Extract·Pump 중단 | #2 Failover, InfoScale 공유 | fencing·VIP·checkpoint |
| InfoScale 장애 | Local Trail 접근 불가 | storage HA·복구 | quorum, file system 점검 |
| Pump 장애 | RDW 전송 중단 | checkpoint부터 재시작 | Local Trail 여유공간 |
| RDW/ACFS 장애 | Remote Trail 기록 불가 | RAC·ACFS failover | mount·공간·권한 |
| Replicat 장애 | RDW 적용 지연 | checkpoint부터 재시작 | apply error·discard |
| Target constraint 오류 | Replicat abend | 오류 수정·재처리 | data reconciliation |

---

## 13. 계정계 부하 최소화 효과와 한계

### 효과

- Source DB에서 Extract Log Mining CPU·memory를 제거한다.
- OGG process 장애와 patch를 계정계 DB에서 분리한다.
- 여러 Source의 Redo를 중계 계층에서 통합 관리할 수 있다.
- Target 장애 시 중계 Trail이 buffer 역할을 한다.

### 남는 부하

- Source는 Redo 생성과 LNS network 전송을 계속 수행한다.
- supplemental logging으로 Redo량이 증가할 수 있다.
- long transaction·대량 batch는 Redo·network·Trail 용량을 증가시킨다.
- DDL·unsupported datatype은 별도 처리와 테스트가 필요하다.

즉 “부하 최소화”는 Source 부하 제거가 아니라 Mining 부하의 Downstream 이전이다.

---

## 14. 보안 설계

- Source–Downstream 및 Pump–RDW 구간의 network 암호화
- OGG credential store 사용과 평문 password 제거
- OGG OS 계정·DB 계정 최소권한
- Trail File 암호화와 file permission
- 관리 port 방화벽·접근제어
- parameter 변경과 process start/stop 감사
- 개인정보 column filtering·masking 필요성 검토
- 운영·DR·개발 OGG 계정과 Trail 경로 분리

---

## 15. 운영·모니터링 지표

### Redo Transport

- LNS destination status와 error
- RFS 상태, SRL switch, archive gap
- transport lag와 apply-ready lag
- network throughput·latency

### GoldenGate

- Extract `LAG AT CHKPT`
- Pump lag와 Local Trail 사용률
- Replicat lag와 applied RBA
- process status·abend count
- oldest unprocessed trail age
- heartbeat end-to-end latency
- discard/report file 오류

### Storage

- InfoScale·ACFS 사용률과 inode
- Trail 생성·삭제 속도
- file system mount와 cluster resource 상태
- 예상 최대 단절시간 대비 잔여공간

---

## 16. 초기 적재 및 복제 개시 절차

```text
1. Source table·key·datatype 적합성 점검
2. Supplemental Logging 설정
3. 일관된 기준시점 SCN 확보
4. 초기 데이터 Export/Load
5. Extract 시작 위치 지정
6. Pump·Target Trail 구성
7. Replicat를 기준 SCN 이후로 시작
8. row count·checksum·업무 대사
9. heartbeat와 lag 감시 개시
```

초기 적재와 실시간 변경분 연결 지점이 어긋나면 누락 또는 중복이 발생한다. 기준 SCN과 각 process checkpoint를 변경관리 기록으로 남겨야 한다.

---

## 17. 주요 위험과 대응

| 위험 | 영향 | 대응 방향 |
|---|---|---|
| Source SRL/Archive gap | 변경분 누락·복제 중단 | archive 보존과 gap 해소 Runbook |
| 중계 split-brain | Trail 중복·손상 | fencing, quorum, 단일 process 활성화 |
| Trail 공간 고갈 | Extract/Pump abend | 최대 장애시간 기반 sizing·알람 |
| Replicat 병목 | RDW 데이터 지연 | integrated/parallel apply와 DB 튜닝 |
| DDL·datatype 미지원 | schema 불일치·abend | 지원성 matrix와 DDL 절차 |
| checkpoint 손상 | 중복·누락 위험 | checkpoint backup과 복구 절차 |
| End-to-End 대사 부재 | 조용한 데이터 불일치 | heartbeat·row count·checksum |
| Trail 평문 노출 | 개인정보 유출 | 암호화·권한·보존·삭제 통제 |
| Source 부하 과소평가 | Redo·network 증가 | supplemental logging 영향 측정 |

---

## 18. 검증 체크리스트

- [ ] Source RAC thread별 Standby Redo Log가 충분히 구성되어 있는가?
- [ ] Redo Transport mode와 archive gap 복구 절차를 시험했는가?
- [ ] Extract·Pump의 InfoScale Failover가 checkpoint부터 재개되는가?
- [ ] split-brain 방지를 위한 fencing·quorum이 검증되었는가?
- [ ] InfoScale·ACFS Trail 용량이 최대 단절시간을 수용하는가?
- [ ] Replicat 유형과 병렬도, error handling이 정의되어 있는가?
- [ ] DDL·LOB·long transaction·unsupported datatype을 시험했는가?
- [ ] heartbeat와 업무 데이터 대사로 End-to-End 정합성을 확인하는가?
- [ ] OGG 계정·Trail·network 구간에 암호화와 최소권한을 적용했는가?
- [ ] 초기 적재 SCN과 실시간 복제 시작점이 변경관리 이력에 남는가?

---

## 19. 최종 평가

이 OGG 구성은 계정계의 Log Mining 부하를 별도 중계서버로 이전하고, 중계 이중화와 두 단계 Trail 저장소를 통해 Source와 Target의 장애를 완충하는 안정적인 Downstream Capture 패턴이다.

실제 안정성은 단순히 Extract·Pump·Replicat가 존재하는지보다 **Redo gap 복구, InfoScale fencing, Trail 용량, checkpoint 연속성, Replicat 병렬도, End-to-End 대사와 보안 통제**를 얼마나 검증했는지에 달려 있다.
