# NSIGHT 시스템 표준 정의 분석
## 서버 호스트명 · 파일시스템 · 사용자 계정 · 서비스 포트

## 0. 문서 개요

본 문서는 제공된 **시스템 표준 정의 장표(페이지 94~98)**를 기준으로 다음 항목을 통합 분석한 문서이다.

1. 서버 호스트 명명규칙
2. Linux OS 표준 파일시스템
3. AP/DB 서버 업무·운영 디렉터리
4. 서버 사용자/엔진/솔루션 계정
5. 서버 및 서버 간 서비스 방화벽 포트

### 작성 원칙

- 장표에서 직접 확인되는 내용은 **[FACT]**로 기록한다.
- 장표 자체에 서로 다른 표기가 있는 경우 임의 수정하지 않고 **불일치/GAP**으로 기록한다.
- 공란으로 표시된 포트, 계정, 경로는 일반적인 제품 기본값으로 임의 보완하지 않는다.
- 운영 적용 전에 원본 설계서/설정파일/방화벽 정책과 대조가 필요한 항목은 **확인 필요**로 분리한다.

---

# 1. 전체 시스템 표준 구조

```text
                         NSIGHT SYSTEM STANDARD

┌──────────────────────────────────────────────────────────────┐
│ ① Server Identity                                           │
│                                                              │
│ Hostname                                                     │
│ 법인 + Application + Server + 환경 + 용도 + 순번             │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│ ② OS / File System                                          │
│                                                              │
│ vg_os                                                        │
│ /, /boot, /etc, /usr, /home, /tmp, /var ...                 │
│                                                              │
│ 업무영역                                                     │
│ /pgm /aplog /userdir                                        │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│ ③ OS Account                                                │
│                                                              │
│ apache / tomcat / 업무계정 / nexus / openpop / ETL / oracle │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│ ④ Service / Firewall Port                                   │
│                                                              │
│ SSH 22 / Apache 80 / Tomcat 8080 / Oracle 15310             │
│ GitLab 80,443 / 기타 제품 포트 확인필요                     │
└──────────────────────────────────────────────────────────────┘
```

---

# 2. 서버 호스트 명명규칙

## 2.1 [FACT] 기본 원칙

원본 장표(페이지 94)는 다음과 같이 정의한다.

> **서버 호스트명은 12자리, 소문자로 아래와 같은 명명 규칙을 적용함**

논리적인 규칙은 다음 6개 영역으로 구성된다.

```text
[법인 2]
+
[대구분코드 2 + 업무구분코드 2]
+
[서버 1]
+
[운영 1]
+
[용도 2]
+
[순번 2]
```

즉 길이는:

```text
2 + 4 + 1 + 1 + 2 + 2 = 12자리
```

---

# 3. 호스트명 세부 코드

## 3.1 ① 법인 코드 — 2자리

| 법인 | 코드 |
|---|---|
| 상호금융 | `sb` |
| 중앙회(상호금융 제외) | `nh` |
| 은행 | `nb` |
| 금융지주 | `fg` |
| 경제지주 | `ag` |

---

## 3.2 ② Application 코드 — 4자리

원본 정의:

```text
대구분 코드(2)
+
업무구분 코드(2)
```

장표에는 다음 주석이 있다.

> **※ 애플리케이션 코드 정의서 참조**

예:

```text
mp + co = mpco
rd + co = rdco
```

---

## 3.3 ③ 서버/플랫폼 코드 — 1자리

| 플랫폼 | 코드 |
|---|:---:|
| Unix-IBM | `i` |
| Unix-HP | `h` |
| Unix-Oracle | `o` |
| Linux(EMC/HP) | `l` |
| Window(EMC/HP) | `w` |
| Exadata(Oracle) | `x` |

---

## 3.4 ④ 운영환경 코드 — 1자리

| 환경 | 코드 |
|---|:---:|
| 운영서버 | `o` |
| 검증서버 | `v` |
| 개발서버 | `t` |

---

## 3.5 ⑤ 서버 용도 코드 — 2자리

| 용도 | 코드 |
|---|:---:|
| AP 서버 | `ap` |
| DB 서버 | `db` |
| WEB 서버 | `wb` |
| WAS 서버 | `ws` |
| 백업 서버 | `bk` |
| 배치 서버 | `bt` |
| ETL 서버 | `tl` |

---

## 3.6 ⑥ 순번 — 2자리

| 센터 | 범위 |
|---|---|
| 주센터(의왕) | `01 ~ 49` |
| DR센터(안성) | `51 ~ 99` |

---

# 4. 호스트명 조합 공식

권장 해석은 다음과 같다.

```text
Hostname
=
법인(2)
+ Application(4)
+ Server Platform(1)
+ Environment(1)
+ Role(2)
+ Sequence(2)
```

예:

```text
sbmpcolowb01
│ │  ││ │ │
│ │  ││ │ └─ 01 : 주센터 #01
│ │  ││ └── wb : WEB
│ │  │└──── o  : 운영
│ │  └───── l  : Linux
│ └──────── mpco : 마케팅플랫폼/공통
└────────── sb   : 상호금융
```

---

# 5. 원본 작성 예시

원본 장표의 작성 예시는 다음과 같다.

| 환경 | 서버 | Hostname |
|---|---|---|
| 운영 | 마케팅플랫폼 WEB #01 | `sbmpcolowb01` |
| DR | 마케팅플랫폼 WEB #51 | `sbmpcolowb51` |
| 개발 | 마케팅플랫폼 WEB #01 | `sbmpcoltwb01` |
| 운영 | RDW 어플라이언스 #01 | `sbrdcoxodb01` |
| DR | RDW 어플라이언스 #51 | `sbrdcoxodb51` |
| 개발 | RDW 어플라이언스 #01 | `sbrdcoxtdb01` |

---

# 6. 호스트명 장표 내 표기 불일치

## 6.1 [GAP] 조합 그림과 작성 예시의 코드 순서

원본의 중앙 조합 그림은 육안상 다음과 같이 보인다.

```text
sb + mpco + o + l + wb + 01
```

하지만 같은 장표의 코드 정의와 실제 작성 예시는:

```text
sb + mpco + l + o + wb + 01
            │   │
            │   └─ 운영 o
            └───── Linux l
```

로 해석된다.

실제 작성 예:

```text
sbmpcolowb01
```

따라서 **③ 서버코드와 ④ 운영코드의 중앙 예시 그림 순서가 뒤바뀌어 보이는 원본 장표 불일치**가 있다.

### 판정

```text
코드 정의 + 실제 작성 예시
→ [Server Platform] + [Environment]

중앙 도식
→ 육안상 [Environment] + [Server Platform]
```

**최종 표준 확정 시 원본 문서 담당자 확인 필요.**

---

# 7. Linux OS 표준 파일시스템

## 7.1 [FACT] Volume Group

페이지 95에서는 Linux OS 표준 파일시스템의 Volume Group을 다음과 같이 표시한다.

```text
/dev/mapper/vg_os
```

이 Volume Group 아래에 OS 기본 Mount Point가 구성되어 있다.

---

# 8. Linux 기본 Mount Point

| Mount Point | 설명 |
|---|---|
| `/` | root 디렉터리, 모든 디렉터리의 시작점 |
| `/boot` | 부트 로더 디렉터리 |
| `/etc` | 시스템 전역 설정 파일 |
| `/dev` | 시스템 디바이스(장치파일) |
| `/root` | root 계정 홈 디렉터리 |
| `/usr` | 일반사용자들 공통 파일 |
| `/home` | 사용자 계정의 홈 디렉터리 |
| `/tmp` | 임시 파일 저장소 |
| `/var` | 시스템 운용 중에 생성되는 임시 데이터 및 로그 저장소 |
| `/var/crash` | 시스템 덤프 파일 저장소 |
| `/userdir` | 업무 계정 홈 저장소 |
| 그 외 SW명 | WEB, WAS, 서버보안, 관제 등 시스템 소프트웨어 설치 디렉터리 |

### 원본 주석

> **업무 구분 및 특성에 따라 파일시스템 명명 규칙 및 할당 위치는 변경될 수 있음**

---

# 9. 파일시스템 계층 해석

```text
/dev/mapper/vg_os
│
├─ /
├─ /boot
├─ /etc
├─ /dev
├─ /root
├─ /usr
├─ /home
├─ /tmp
├─ /var
├─ /var/crash
├─ /userdir
└─ [기타 SW 설치 디렉터리]
```

### [ANALYSIS]

원본 구조는 다음 두 영역을 구분하려는 것으로 볼 수 있다.

```text
OS Standard Area
+
Business / Software Area
```

즉 OS 기본 경로와 업무/솔루션 설치 경로를 구분하는 기준이다.

---

# 10. AP/DB 서버 디렉터리 3대 영역

페이지 96은 다음과 같이 선언한다.

> **AP, DB 서버의 디렉터리는 크게 3가지로 분류한다.**

핵심 3대 영역은 다음과 같다.

| 영역 | 용도 | 기본 디렉터리 | HA 적용시스템 디렉터리 |
|---|---|---|---|
| Program Area | 프로그램 및 Schema | `/pgm` | `/pgm_hostname` |
| Application Log Area | 프로그램 실행 시 발생되는 로그 | `/aplog` | `/aplog_hostname` |
| Data Area | 프로그램 실행 시 입·출력되는 파일 | `/userdir` | `/userdir_hostname` |

---

# 11. HA 적용 시 디렉터리 명명규칙

원본 구조:

```text
기본
/pgm
/aplog
/userdir
```

HA 적용 시스템:

```text
/pgm_hostname
/aplog_hostname
/userdir_hostname
```

### [ANALYSIS]

HA 환경에서는 동일 파일시스템 또는 공유자원 내에서 서버별 데이터/로그/프로그램 경계를 식별하기 위해
Hostname을 디렉터리명에 포함하는 구조로 해석할 수 있다.

---

# 12. 시스템/솔루션 설치 디렉터리

| 구분 | 용도 | 기본 디렉터리 | HA 적용 |
|---|---|---|---|
| Oracle S/W | 오라클 데이터베이스 엔진 | `/nhod` | N/A |
| 서버보안 | 서버보안(Secuve TOS) 설치 디렉터리 | `/usr/local/TOS` | N/A |
| 서버운영 관리 | 서버운영 관리 설치 디렉터리 | `/sepa` | N/A |
| 배치작업 관리 | 배치작업 관리 설치 디렉터리 | `/seba` | N/A |
| SMS | SMS(HP Openview) Agent 설치 디렉터리 | `/SMS` | N/A |
| 통합관제 | 통합관제(perfmon) Agent 설치 디렉터리 | `/SMT/perfmon` | N/A |

원본 주석:

> **업무 구분 및 특성에 따라 파일시스템 명명 규칙 및 할당 위치는 변경될 수 있음**

---

# 13. 파일시스템 전체 구조

```text
Linux Server
│
├─ OS Area
│  └─ /dev/mapper/vg_os
│     ├─ /
│     ├─ /boot
│     ├─ /etc
│     ├─ /dev
│     ├─ /root
│     ├─ /usr
│     ├─ /home
│     ├─ /tmp
│     ├─ /var
│     └─ /var/crash
│
├─ Business Area
│  ├─ /pgm
│  ├─ /aplog
│  └─ /userdir
│
├─ HA Business Area
│  ├─ /pgm_hostname
│  ├─ /aplog_hostname
│  └─ /userdir_hostname
│
└─ Solution / Management
   ├─ /nhod
   ├─ /usr/local/TOS
   ├─ /sepa
   ├─ /seba
   ├─ /SMS
   └─ /SMT/perfmon
```

---

# 14. 사용자 계정 표준

페이지 97의 기본 원칙:

> **서버 접속 및 소프트웨어 설치를 위한 사용자 계정은 동일한 권한을 가질 수 있도록 전 시스템에 동일하게 부여**

계정은 크게 다음과 같이 분류된다.

```text
User Account
│
├─ 엔진계정
├─ 업무계정
└─ 사용자/솔루션계정
   ├─ Nexus
   ├─ SQL 품질
   ├─ ETL
   └─ Oracle
```

---

# 15. 엔진 계정

| 계정 항목 | 계정명 | 그룹명 | 설명 |
|---|---|---|---|
| WEB 서버(apache) 엔진 계정 | `apache` | `apache` | Apache WEB 서버 엔진 계정 |
| WAS 서버(tomcat) 엔진 계정 | `tomcat` | `tomcat` | Tomcat WAS 서버 엔진 계정 |

---

# 16. 업무 계정

원본에서는 업무팀 사용계정의 계정명/그룹명을 Application Code 기반으로 정의한다.

주석:

> **대구분 코드(2) + 업무구분 코드(2) : 어플리케이션 코드 정의서 참조**

그리고 그룹 부여 원칙은 다음과 같다.

> **그룹명은 관련 그룹명을 할당**  
> 예) **WEB 업무는 apache 그룹, WAS 업무는 tomcat 그룹**

### 구조화

```text
업무계정명
=
대구분코드(2)
+
업무구분코드(2)
```

예시적 해석:

```text
mp + co
→ mpco
```

단, 실제 업무계정 문자열은 애플리케이션 코드 정의서를 기준으로 확정해야 한다.

---

# 17. Nexus / SQL 품질 계정

| 계정 항목 | 계정명 | 그룹명 | 설명 |
|---|---|---|---|
| Nexus 오픈소스 저장소 계정 | `nexus` | `cicd` | 라이브러리 소프트웨어 설치 및 관리 계정 |
| SQL 품질 계정 | `openpop` | `openpop` | SQL 품질 프로그램 설치 및 관리 계정 |

---

# 18. ETL 솔루션 계정

| 계정명 | 그룹명 | 설명 |
|---|---|---|
| `dsadm` | `dstage` | DataStage 관리자 계정 |
| `wasadm` | `dstage` | Websphere Application Server 관리자 계정 |
| `db2inst` | `db2grp` | DB2 Instance 소유자 계정 |
| `db2fenc` | `db2grp` | DB2 Fenced 계정 |
| `xmeta` | `db2grp` | Metadata Repository 소유자 계정 |
| `xmetasr` | `db2grp` | Metadata Repository 사용자 계정 |
| `dsodb` | `db2grp` | Operation Database 소유자 계정 |

---

# 19. Oracle 계정

| 계정 항목 | 계정명 | 그룹명 | 설명 |
|---|---|---|---|
| 오라클 계정 | `oracle` | `oragrid` | Oracle Database 계정 |

---

# 20. 계정 표준 주석

원본은 다음과 같은 예외 가능성을 명시한다.

> **업무 구분 및 특성에 따라 시스템 사용자 계정 명명 규칙은 변경될 수 있음**

따라서 계정 기준은:

```text
Default Standard
+
업무 특성에 따른 승인된 예외
```

형태로 관리하는 것이 적절하다.

---

# 21. 서비스 포트 현황

페이지 98은 다음 목적으로 포트를 정의한다.

> **서버와 서버간 서비스를 위한 방화벽 포트**

원본에서 값이 명확하게 입력된 포트는 다음과 같다.

| 구분 | 이름 | 포트 | 설명 | 비고 |
|---|---|---:|---|---|
| OS | SSH/SFTP | `22` | 서버 접속 및 파일 송수신을 위한 포트 | |
| WEB | Apache | `80` | WEB서버 서비스 포트 | |
| WAS | Tomcat | `8080` | WAS서버 서비스 포트 | |
| DBMS | Oracle | `15310` | DBMS 서비스 포트 | 개발환경 서비스 포트 `1531` |
| 소스관리 | GitLab | `80, 443` | 장표 설명 공란 | |

---

# 22. 포트 미정/공란 항목

다음 항목은 원본 장표에 제품명은 있으나 Port 컬럼이 공란이다.

| 구분 | 제품명 | 포트 |
|---|---|---|
| 단말UI | WebTopSuite | **미표기** |
| 데이터품질 | DQ Miner | **미표기** |
| 비즈메타 | Meta Miner | **미표기** |
| 데이터흐름 | Data Hawk | **미표기** |
| CDC | Oracle Golden Gate | **미표기** |
| BI 포탈 | Data Eye | **미표기** |
| BI 포탈 | BI MATRIX | **미표기** |
| 마케팅 플랫폼 | Kafka | **미표기** |
| ETL | DataStage | **미표기** |
| 배포관리 | GitLab Runner | **미표기** |
| SQL 품질 | OpenPOP | **미표기** |
| 이행 | SQL Canvas | **미표기** |

### 중요

이 항목은 일반적인 제품 Default Port로 채우면 안 된다.

```text
장표 Port 공란
≠
Port 없음
```

실제 방화벽 정책 확정 전 다음 자료를 확인해야 한다.

- 제품 설치설계서
- 서버별 서비스 설정
- `httpd.conf`
- `server.xml`
- Oracle Listener 설정
- Kafka Listener 설정
- GoldenGate 설정
- DataStage 설치정보
- GitLab Runner 연결정보
- 방화벽 신청서

---

# 23. Oracle 서비스 포트 확인사항

원본 표:

```text
Oracle 운영 DBMS 서비스 포트 : 15310
비고 : 개발환경 서비스 포트 1531
```

### [GAP]

운영 포트 `15310`과 개발 포트 `1531`은 자릿수가 다르므로,
다음 사항을 확인해야 한다.

- 개발환경 포트 `1531`이 정확한 값인지
- `15310`의 환경별 파생 포트 체계가 있는지
- RDW/ADW/RAC Service별 포트가 동일한지
- Listener/SCAN Listener와의 관계

본 문서에서는 원본 표기를 그대로 유지한다.

---

# 24. 시스템 표준 간 연결

이번 5개 장표는 독립적인 표가 아니라 하나의 운영표준으로 연결된다.

```text
Application Code
       │
       ├──────────────┐
       ▼              ▼
   Hostname        업무계정
       │
       ▼
Server / Environment / Role
       │
       ├──────────────┐
       ▼              ▼
File System       Engine Account
       │              │
       └──────┬───────┘
              ▼
          Service Port
              │
              ▼
       Firewall Policy
```

---

# 25. 서버 한 대의 표준 예시

마케팅플랫폼 운영 WEB #01을 기준으로 하면:

```text
System
└─ Marketing Platform / Common

Hostname
└─ sbmpcolowb01

분해
├─ sb   : 상호금융
├─ mpco : Application Code
├─ l    : Linux
├─ o    : 운영
├─ wb   : WEB
└─ 01   : 의왕 #01

Engine
└─ Apache

OS Account
└─ apache:apache

Service
└─ TCP/80

File System
├─ OS Standard Area
├─ /pgm 또는 HA 시 /pgm_hostname
├─ /aplog 또는 HA 시 /aplog_hostname
└─ /userdir 또는 HA 시 /userdir_hostname
```

---

# 26. WAS 서버 표준 예시

```text
WAS Server
│
├─ Hostname
│  └─ ...lows01
│
├─ Engine Account
│  └─ tomcat:tomcat
│
├─ Service Port
│  └─ 8080
│
├─ Program
│  └─ /pgm[_hostname]
│
├─ Log
│  └─ /aplog[_hostname]
│
└─ Data
   └─ /userdir[_hostname]
```

---

# 27. Architecture Rule 후보

| Rule ID | 규칙 | 상태 |
|---|---|---|
| `STD-HOST-001` | Hostname은 12자리 소문자로 관리한다 | 장표 근거 |
| `STD-HOST-002` | Hostname은 법인+Application+Server+환경+용도+순번으로 구성한다 | 장표 근거 |
| `STD-HOST-003` | 주센터 순번은 01~49를 사용한다 | 장표 근거 |
| `STD-HOST-004` | DR센터 순번은 51~99를 사용한다 | 장표 근거 |
| `STD-FS-001` | Linux OS 기본 파일시스템은 표준 Mount 구조를 적용한다 | 장표 근거 |
| `STD-FS-002` | AP/DB 업무영역은 Program/Log/Data Area로 분리한다 | 장표 근거 |
| `STD-FS-003` | HA 적용시스템은 핵심 업무 디렉터리에 hostname을 부여한다 | 장표 근거 |
| `STD-FS-004` | 시스템 SW 설치영역은 업무 데이터와 분리한다 | 장표 취지 |
| `STD-ACC-001` | Apache 엔진계정은 apache/apache를 사용한다 | 장표 근거 |
| `STD-ACC-002` | Tomcat 엔진계정은 tomcat/tomcat을 사용한다 | 장표 근거 |
| `STD-ACC-003` | 업무계정은 Application Code 체계와 연계한다 | 장표 근거 |
| `STD-ACC-004` | 동일 역할 계정은 전 시스템에 동일 권한 기준을 적용한다 | 장표 근거 |
| `STD-PORT-001` | SSH/SFTP 서비스는 22번을 사용한다 | 장표 근거 |
| `STD-PORT-002` | Apache 서비스 포트는 80으로 정의되어 있다 | 장표 근거 |
| `STD-PORT-003` | Tomcat 서비스 포트는 8080으로 정의되어 있다 | 장표 근거 |
| `STD-PORT-004` | Oracle 운영 서비스 포트는 15310으로 정의되어 있다 | 장표 근거 |
| `STD-PORT-005` | GitLab 포트는 80/443으로 정의되어 있다 | 장표 근거 |
| `STD-PORT-006` | 표에 없는 솔루션 포트는 제품 설치설계 기준으로 별도 확정한다 | 분석 |

---

# 28. 확인 필요 GAP

| GAP ID | 항목 | 상태 |
|---|---|---|
| `GAP-STD-001` | Hostname 중앙 도식의 Server/Environment 코드 순서 | **장표 내 불일치** |
| `GAP-STD-002` | Application Code 전체 코드표 | 별도 정의서 필요 |
| `GAP-STD-003` | 검증환경 실제 Hostname 예시 | 미제시 |
| `GAP-STD-004` | 파일시스템별 실제 용량 | 미표기 |
| `GAP-STD-005` | 파일시스템 Type(XFS 등) | 미표기 |
| `GAP-STD-006` | LVM LV명 | 미표기 |
| `GAP-STD-007` | `/pgm`, `/aplog`, `/userdir` Owner/Group/Permission | 미표기 |
| `GAP-STD-008` | HA 파일시스템 공유방식 | 미표기 |
| `GAP-STD-009` | 계정 UID/GID 기준 | 미표기 |
| `GAP-STD-010` | sudo 정책 | 미표기 |
| `GAP-STD-011` | 서비스 계정 로그인 허용/금지 | 미표기 |
| `GAP-STD-012` | 비밀번호/키 관리 기준 | 미표기 |
| `GAP-STD-013` | Oracle 1531 개발포트 정합성 | 확인 필요 |
| `GAP-STD-014` | WebTopSuite 포트 | 미표기 |
| `GAP-STD-015` | DQ Miner 포트 | 미표기 |
| `GAP-STD-016` | Meta Miner 포트 | 미표기 |
| `GAP-STD-017` | Data Hawk 포트 | 미표기 |
| `GAP-STD-018` | Oracle GoldenGate 포트 | 미표기 |
| `GAP-STD-019` | Data Eye 포트 | 미표기 |
| `GAP-STD-020` | BI MATRIX 포트 | 미표기 |
| `GAP-STD-021` | Kafka 포트 | 미표기 |
| `GAP-STD-022` | DataStage 포트 | 미표기 |
| `GAP-STD-023` | GitLab Runner 연결포트 | 미표기 |
| `GAP-STD-024` | OpenPOP 포트 | 미표기 |
| `GAP-STD-025` | SQL Canvas 포트 | 미표기 |
| `GAP-STD-026` | 서버간 Source/Destination 방화벽 Matrix | 미표기 |
| `GAP-STD-027` | 환경별 방화벽 정책 차이 | 미표기 |

---

# 29. 운영 검증 체크리스트

| Check ID | 검증사항 |
|---|---|
| `CHK-001` | 모든 Hostname이 12자리인지 검사 |
| `CHK-002` | Hostname이 소문자인지 검사 |
| `CHK-003` | 법인코드가 표준 코드인지 검사 |
| `CHK-004` | Application Code가 코드정의서와 일치하는지 검사 |
| `CHK-005` | Server Platform 코드와 실제 OS가 일치하는지 검사 |
| `CHK-006` | 운영/개발/검증 환경코드가 실제 환경과 일치하는지 검사 |
| `CHK-007` | 센터 순번이 의왕 01~49 / 안성 51~99 범위를 지키는지 검사 |
| `CHK-008` | `/pgm`, `/aplog`, `/userdir` 존재 여부 |
| `CHK-009` | HA 서버의 `_hostname` 디렉터리 적용 여부 |
| `CHK-010` | apache/tomcat 계정 및 그룹 존재 여부 |
| `CHK-011` | 업무계정이 Application Code와 일치하는지 검사 |
| `CHK-012` | 디렉터리 Owner/Group이 계정정책과 일치하는지 검사 |
| `CHK-013` | Firewall Open Port가 서비스 목록과 일치하는지 검사 |
| `CHK-014` | Port가 실제 Listener 설정과 일치하는지 검사 |
| `CHK-015` | 공란 솔루션 Port가 별도 설계서에서 확정되었는지 확인 |

---

# 30. 최종 표준 Big Picture

```text
                         SYSTEM STANDARD BASELINE

Application Classification
         │
         ▼
Application Code
         │
         ├─────────────────────────────┐
         ▼                             ▼
      Hostname                     Business Account
         │
         ▼
Server Identity
         │
         ├─ Platform
         ├─ Environment
         ├─ Role
         └─ Center / Sequence
         │
         ▼
OS / File System
         │
         ├─ OS Area
         ├─ Program Area
         ├─ Log Area
         ├─ Data Area
         └─ Solution Area
         │
         ▼
Engine / Solution Account
         │
         ▼
Service Listener
         │
         ▼
Firewall Port
         │
         ▼
Operation / Monitoring / Audit
```

---

# 31. 결론

제공된 5개 시스템 표준 장표에서 확인되는 핵심은 다음과 같다.

1. **서버 Hostname은 12자리 소문자 표준**을 사용한다.
2. Hostname은 **법인 + Application Code + 서버 플랫폼 + 환경 + 용도 + 순번** 체계로 구성된다.
3. 주센터(의왕)는 `01~49`, DR센터(안성)는 `51~99`의 순번을 사용한다.
4. Linux OS는 `/dev/mapper/vg_os` 아래 표준 OS 파일시스템 구조를 사용한다.
5. AP/DB 서버 업무 디렉터리는 **Program Area / Application Log Area / Data Area**의 3개 핵심영역으로 분리한다.
6. HA 시스템은 `/pgm_hostname`, `/aplog_hostname`, `/userdir_hostname` 형태의 디렉터리를 사용한다.
7. Apache와 Tomcat은 각각 `apache`, `tomcat` 엔진계정을 사용한다.
8. 업무계정은 **대구분 코드(2)+업무구분 코드(2)**의 Application Code 체계와 연결된다.
9. Nexus, OpenPOP, DataStage, DB2, Oracle 등 솔루션별 전용계정을 별도로 정의한다.
10. 장표상 확정 포트는 **SSH/SFTP 22, Apache 80, Tomcat 8080, Oracle 15310, GitLab 80/443**이다.
11. Oracle 개발환경 비고에는 `1531`이 별도로 표기되어 있어 정합성 확인이 필요하다.
12. WebTopSuite, DQ Miner, Meta Miner, Data Hawk, GoldenGate, BI, Kafka, DataStage 등 다수 솔루션의 포트는 장표상 공란이므로 별도 설치/방화벽 설계에서 확정해야 한다.
13. Hostname 중앙 조합 그림과 실제 코드정의/작성예시 사이에 **Server/Environment 코드 순서 불일치가 보이므로 표준 확정 전에 원본 검증이 필요하다.**

본 문서는 제공된 페이지 94~98 장표를 기준으로 한 **NSIGHT System Standard Working Baseline**으로 활용한다.
