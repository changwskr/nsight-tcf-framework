# NSIGHT WEB/WAS 기본 최소사양 정리

## 1. 문서 목적

본 문서는 농협 상호금융 NSIGHT 서버 인벤토리 및 Physical Architecture 관리 시 적용할 **WEB/WAS 서버의 기본 최소사양(VM Baseline)** 을 정리한다.

사진의 「서버 구성 최소 사양(VM)」을 기준으로 WEB/WAS 최소 구축사양과 NSIGHT 용량산정 기준의 관계를 구분하여 관리한다.

---

## 2. WEB/WAS 기본 최소사양

| 구분 | CPU | Memory | Disk | Disk 구성 | 용도 |
|---|---:|---:|---:|---|---|
| **WEB** | **2 vCPU** | **16GB** | **60GB** | 엔진 10GB + 로그 50GB | Apache WEB 서버 최소사양 |
| **WAS** | **4 vCPU** | **16GB** | **70GB** | 엔진 20GB + 로그 50GB | Tomcat WAS 서버 최소사양 |

### 기본 Baseline

```text
WEB = 2 vCPU / 16GB / 60GB
WAS = 4 vCPU / 16GB / 70GB
```

---

## 3. NSIGHT 적용 원칙

사진에 제시된 WEB/WAS 사양은 **서버 구성 최소사양**이다.

따라서 다음 세 가지 개념을 구분하여 관리한다.

| 구분 | 의미 | 관리 목적 |
|---|---|---|
| 최소사양 (Minimum Spec) | 서버를 구성하기 위한 최소 VM 기준 | 구축 가능 여부 판단 |
| 실제 할당사양 (Allocated Spec) | 서버 인벤토리에 실제 배정된 CPU/MEM/Disk | 실제 자원관리 |
| 용량산정사양 (Capacity Spec) | TPS, tpmC, Thread, DB Pool 등을 반영하여 산정한 사양 | 운영 성능 및 안정성 검증 |

**핵심 원칙**

> 최소사양과 운영 용량산정사양은 동일한 개념이 아니다.

예를 들어 WAS 최소사양이 `4 vCPU / 16GB`라고 하더라도 실제 NSIGHT 온라인 업무는 TPS, 동시사용자, Tomcat Thread, JVM Heap, HikariCP, DB 처리량 등을 기준으로 더 큰 VM이 필요할 수 있다.

---

## 4. 서버 인벤토리 적용 구조

최종 서버 인벤토리에서는 최소사양과 실제 할당사양을 분리한다.

| 서버 역할 | 최소사양 | 실제 할당사양 | 용량산정 기준 | 판정 |
|---|---|---|---|---|
| WEB | 2C / 16G / 60G | 서버별 실제값 | WEB 부하/tpmC 기준 | 비교 검증 |
| WAS | 4C / 16G / 70G | 서버별 실제값 | TPS/tpmC/Thread/Pool 기준 | 비교 검증 |

권장 관리 컬럼은 다음과 같다.

```text
시스템
 → 서버
 → Hostname
 → 역할(WEB/WAS)
 → 최소 CPU/MEM/Disk
 → 실제 CPU/MEM/Disk
 → tpmC/TPS
 → JVM Heap
 → Tomcat Thread
 → Hikari Pool
 → HA/DR
 → 적정성 판정
```

---

## 5. WEB 아키텍처 적용

NSIGHT WEB 서버는 Apache HTTP Server를 기준으로 한다.

```text
Client
   │
   ▼
GSLB / L4
   │
   ▼
WEB VM
┌──────────────────────┐
│ Apache HTTP Server   │
│                      │
│ Minimum Baseline     │
│ CPU    : 2 vCPU      │
│ Memory : 16GB        │
│ Disk   : 60GB        │
│  ├ Engine : 10GB     │
│  └ Log    : 50GB     │
└──────────┬───────────┘
           │
           ▼
       WAS / Tomcat
```

하나의 Apache 인스턴스에서 여러 포트를 Listen하고 서비스별 Tomcat JVM으로 라우팅하는 구성도 가능하다.

---

## 6. WAS 아키텍처 적용

NSIGHT WAS는 Tomcat 기반이며, WAS 서버(VM) 내부에 하나 이상의 독립 Tomcat JVM Instance를 구성할 수 있다.

```text
WAS VM
Minimum Baseline
4 vCPU / 16GB / 70GB
│
├─ Tomcat JVM Instance #1
│   ├─ Connector Port
│   ├─ Application
│   ├─ JVM Heap
│   ├─ Thread Pool
│   └─ HikariCP
│
└─ Tomcat JVM Instance #2
    ├─ Connector Port
    ├─ Application
    ├─ JVM Heap
    ├─ Thread Pool
    └─ HikariCP
```

WAS 최소 Disk 구성은 다음과 같다.

```text
Total 70GB
├─ Engine : 20GB
└─ Log    : 50GB
```

단, Tomcat JVM 수, 로그량, Heap Dump, GC Log, 배포 Artifact 등의 운영 요구사항에 따라 추가 Disk가 필요할 수 있다.

---

## 7. 최소사양과 NSIGHT 용량산정의 관계

NSIGHT에서는 WEB/WAS 최소사양을 그대로 운영 표준사양으로 간주하지 않는다.

```text
[최소 구축 기준]
WEB : 2C / 16G
WAS : 4C / 16G
        │
        ▼
[업무 부하 산정]
사용자
 → 동시요청자
 → TPS
 → tpmC
        │
        ▼
[Runtime 산정]
CPU / Memory
JVM Heap
Tomcat Thread
Hikari Pool
        │
        ▼
[실제 운영 VM 사양]
서버별 Capacity Spec 확정
```

기존 NSIGHT 용량산정에서는 온라인 AP/WAS에 대해 `8 vCPU / 32GB`, `16 vCPU / 64GB` 등의 별도 운영 용량 기준을 검토해 왔다. 따라서 이번 최소사양은 **Minimum Infrastructure Baseline**으로 관리하고, 성능 용량산정 결과는 별도의 **Runtime/Capacity Baseline**으로 관리한다.

---

## 8. Architecture Gate

### WEB

| 점검항목 | 기준 |
|---|---|
| CPU | 2 vCPU 이상 |
| Memory | 16GB 이상 |
| Disk | 60GB 이상 |
| Engine Disk | 10GB 이상 |
| Log Disk | 50GB 이상 |
| Middleware | Apache |
| 실제 용량 | tpmC/부하 기준 별도 검증 |

### WAS

| 점검항목 | 기준 |
|---|---|
| CPU | 4 vCPU 이상 |
| Memory | 16GB 이상 |
| Disk | 70GB 이상 |
| Engine Disk | 20GB 이상 |
| Log Disk | 50GB 이상 |
| Middleware | Tomcat |
| JVM | Tomcat Instance별 산정 |
| Thread | TPS/응답시간 기준 산정 |
| DB Pool | DB Connection 점유시간 기준 산정 |
| 실제 용량 | TPS/tpmC 기준 별도 검증 |

---

## 9. 최종 기준

NSIGHT 서버 인벤토리에서 WEB/WAS의 기본 최소사양은 다음과 같이 관리한다.

```text
┌─────────────────────────────────────────┐
│ NSIGHT Minimum VM Baseline              │
├─────────────────────────────────────────┤
│ WEB                                     │
│  2 vCPU / 16GB / 60GB                  │
│  Engine 10GB + Log 50GB                │
│                                         │
│ WAS                                     │
│  4 vCPU / 16GB / 70GB                  │
│  Engine 20GB + Log 50GB                │
└─────────────────────────────────────────┘
```

### 최종 원칙

> **WEB 2C/16G/60G, WAS 4C/16G/70G는 최소 구축 Baseline으로 사용한다.**

> **실제 운영 서버의 CPU/MEM/Disk는 서버별 tpmC/TPS 및 Runtime 용량산정 결과를 적용하여 별도로 확정한다.**

> **따라서 `Minimum Spec`, `Allocated Spec`, `Capacity Spec`을 서버 인벤토리에서 반드시 분리 관리한다.**

---

## 10. 서버 인벤토리 연계 권고

향후 71대 통합 서버 인벤토리에는 다음 필드를 추가하여 자동 적정성 판정이 가능하도록 한다.

| 컬럼 | 설명 |
|---|---|
| Minimum CPU | 역할별 최소 CPU |
| Minimum Memory | 역할별 최소 Memory |
| Minimum Disk | 역할별 최소 Disk |
| Allocated CPU | 실제 할당 CPU |
| Allocated Memory | 실제 할당 Memory |
| Allocated Disk | 실제 할당 Disk |
| Capacity CPU | 용량산정 CPU |
| Capacity Memory | 용량산정 Memory |
| tpmC | 용량산정 성능값 |
| TPS | 업무 처리량 |
| Minimum Gate | 최소사양 충족 여부 |
| Capacity Gate | 운영 용량 충족 여부 |
| Final Status | 정상 / 검토 / 증설필요 |

이 구조를 적용하면 서버 인벤토리가 단순 자산목록이 아니라 **Physical Architecture + Capacity Architecture + Runtime Architecture를 연결하는 기준정보**가 된다.
