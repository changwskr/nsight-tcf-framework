# OGG 구성도

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_57_37 GMT+9.zip` |
| 원본 이미지 | `44.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **데이터베이스 아키텍처** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **68.4%** |
| 원본 이미지 키 | `85737/44.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
6구성도
SAAHE 구성 하여 계 정 계 DB 서버부하를 최소화함
Fat Ove
계철 게07
계정계02 ANG
ames
증계0602
BAB
ㅣ [
RDW#1
belbdlidlid
내
101050810
ACES
Trai
Pump
Trail
@ Extract
그!
두
Standby Redo Log
Online/Archived Redo Log
LNSn
RFS
LINSn:Log Network Server
단계
RFS;Remote File Server
+ Extract1Log Files 읽어 Committe! Wz
i
이고
AAAI(Source) ㅁ 0에서 발생한 트랜잭션 변경 정 보 는 Redo Ｌ09에 기 록 되며
출 하 여 1121 116로 저장하는
+ Pump:1819하으로 11011 Files 배포하는
10450 프로세스를 통해 중계 서 버의 Standby Redo Log= 실시간 전송
+ Replicat:Target 매메Tralll Files: 적용:
세 스가 Standby Redo Log& Log!110109하 여 Commit
· ACES:Advanced Cluster File System
=|
중계 서 버 의 Extract
된 변경 GOES 추 출하고11211 Files 생성
_고
중계서 버 의 Pump 프 로세스 는 생 성 된 Trail FileS Target(ROW)AH! Trail
sos
프로세스
File 저 장소(4아9)로 전송
Target(RDW) 서버 의 Replicat #2 47t Trail File=
$01 Source DBO|| 발생
‘DB¥t@(Replicat)
Log File
한 HAPSTarget DBO 동일하게반영
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | 6구성도 | - | - |
| 상중단 | SAAHE 구성 하여 계 정 계 DB 서버부하를 최소화함<br>계철 게07<br>계정계02 ANG<br>ames | Fat Ove<br>증계0602<br>BAB | ㅣ [<br>RDW#1 |
| 중단 | belbdlidlid<br>두 | 101050810<br>Trai<br>@ Extract<br>그! | 내<br>ACES<br>Pump<br>Trail |
| 중하단 | Online/Archived Redo Log<br>LNSn<br>단계<br>i | Standby Redo Log<br>RFS | LINSn:Log Network Server<br>RFS;Remote File Server<br>+ Extract1Log Files 읽어 Committe! Wz |
| 하단 | 이고<br>=\|<br>_고<br>‘DB¥t@(Replicat) | AAAI(Source) ㅁ 0에서 발생한 트랜잭션 변경 정 보 는 Redo Ｌ09에 기 록 되며<br>10450 프로세스를 통해 중계 서 버의 Standby Redo Log= 실시간 전송<br>세 스가 Standby Redo Log& Log!110109하 여 Commit<br>중계 서 버 의 Extract<br>된 변경 GOES 추 출하고11211 Files 생성<br>중계서 버 의 Pump 프 로세스 는 생 성 된 Trail FileS Target(ROW)AH! Trail<br>File 저 장소(4아9)로 전송<br>Target(RDW) 서버 의 Replicat #2 47t Trail File=<br>한 HAPSTarget DBO 동일하게반영 | 출 하 여 1121 116로 저장하는<br>+ Pump:1819하으로 11011 Files 배포하는<br>+ Replicat:Target 매메Tralll Files: 적용:<br>· ACES:Advanced Cluster File System<br>sos<br>프로세스<br>$01 Source DBO\|\| 발생<br>Log File |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `AP` | 하단/중앙 | 한 HAPSTarget DBO 동일하게반영 |
| `DB` | 상중단/좌측 | SAAHE 구성 하여 계 정 계 DB 서버부하를 최소화함 |
| `DB` | 중하단/중앙 | Standby Redo Log |
| `DB` | 하단/중앙 | 10450 프로세스를 통해 중계 서 버의 Standby Redo Log= 실시간 전송 |
| `RDW` | 상중단/우측 | RDW#1 |
| `RDW` | 하단/중앙 | Target(RDW) 서버 의 Replicat #2 47t Trail File= |
| `File` | 중하단/우측 | RFS;Remote File Server |
| `File` | 중하단/우측 | + Extract1Log Files 읽어 Committe! Wz |
| `File` | 하단/우측 | + Pump:1819하으로 11011 Files 배포하는 |
| `서버` | 상중단/좌측 | SAAHE 구성 하여 계 정 계 DB 서버부하를 최소화함 |
| `서버` | 하단/중앙 | Target(RDW) 서버 의 Replicat #2 47t Trail File= |

## 3. 이미지에서 확인되는 핵심 내용

- 계정계(Source) → 중계서버(Downstream) → RDW(Target) 흐름이 그려져 있다.
- Source DB의 부담을 최소화하기 위해 중계서버를 구성한다는 문구가 보인다.
- Extract/Trail/Pump/Replicat 등 GoldenGate 처리단계가 표시된다.

## 4. 아키텍처 분석

- 소스 운영 DB에서 로그를 직접 장시간 처리하지 않고 Downstream/중계 경계를 둬 CDC 부하와 장애영향을 줄이는 설계다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `AP`, `DB`, `RDW`, `File`, `서버`
- 이 장표는 **데이터베이스 아키텍처** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `0%` — BAB
  - `0%` — belbdlidlid
  - `17%` — sos
  - `27%` — ACES
  - `37%` — ames
  - `44%` — LNSn
  - `49%` — 계정계02 ANG
  - `54%` — 계철 게07

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `데이터베이스 아키텍처` |
| Source Key | `85737/44.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

