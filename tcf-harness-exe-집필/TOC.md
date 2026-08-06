# NSIGHT-TCF 책 집필 목차 (Harness SoT)

> 에이전트는 **이 목차의 항목 단위**로만 집필한다. 기계용 SoT: [`toc.json`](./toc.json)
> 원고 출력: `../ztcfbook/` · 동기화: `node scripts/sync_toc_chapters.cjs`

| 항목 | 값 |
| --- | --- |
| 생성 시각 | 2026-08-05T14:30:01.598Z |
| 항목 수 | 47 |
| 에디션 | ztcfbook |

## 상태 범례

| status | 의미 |
| --- | --- |
| `pending` | 작업 대기 |
| `draft-exists` | ztcfbook에 원고 있음 · 하네스 검수/갱신 가능 |
| `in_progress` | 에이전트 작업 중 |
| `completed` | research~quality 완료 |
| `blocked` | 승인·출처 충돌 등으로 중단 |

## 에이전트 실행 방법

1. `TOC.md` / `toc.json`에서 항목 선택
2. `chapters/{id}/TASK.md` 읽기
3. **풍부한 책 본문**을 `target`(ztcfbook)에 집필 — IN/OUT 메모 금지
4. 완료 후 `toc.json` status 갱신 후 sync 재실행

채팅 예시: `TOC의 CH-22 조회 거래를 자세하고 풍부하게 집필해줘`

## 서문

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-00-00-서문`](./chapters/CH-00-00-서문/TASK.md) | 0 | 서문 | `draft-exists` | [`chapters/CH-00-00-서문`](./chapters/CH-00-00-서문/) | `../ztcfbook/서문/00-서문.md` |

## 제1편 · TCF Framework 이해하기

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-01-01-NSIGHT-TCF란-무엇인가`](./chapters/CH-01-01-NSIGHT-TCF란-무엇인가/TASK.md) | 1 | NSIGHT TCF란 무엇인가 | `completed` | [`chapters/CH-01-01-NSIGHT-TCF란-무엇인가`](./chapters/CH-01-01-NSIGHT-TCF란-무엇인가/) | `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md` |
| [`CH-02-02-전체-시스템-구조`](./chapters/CH-02-02-전체-시스템-구조/TASK.md) | 2 | 전체 시스템 구조 | `completed` | [`chapters/CH-02-02-전체-시스템-구조`](./chapters/CH-02-02-전체-시스템-구조/) | `../ztcfbook/제01편/02-전체-시스템-구조.md` |
| [`CH-03-03-TCF-처리-엔진`](./chapters/CH-03-03-TCF-처리-엔진/TASK.md) | 3 | TCF 처리 엔진 | `draft-exists` | [`chapters/CH-03-03-TCF-처리-엔진`](./chapters/CH-03-03-TCF-처리-엔진/) | `../ztcfbook/제01편/03-TCF-처리-엔진.md` |
| [`CH-04-04-애플리케이션-6계층`](./chapters/CH-04-04-애플리케이션-6계층/TASK.md) | 4 | 애플리케이션 6계층 | `draft-exists` | [`chapters/CH-04-04-애플리케이션-6계층`](./chapters/CH-04-04-애플리케이션-6계층/) | `../ztcfbook/제01편/04-애플리케이션-6계층.md` |

## 제2편 · 개발 표준과 명명규칙

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-05-05-개발-표준-총정리`](./chapters/CH-05-05-개발-표준-총정리/TASK.md) | 5 | 개발 표준 총정리 | `draft-exists` | [`chapters/CH-05-05-개발-표준-총정리`](./chapters/CH-05-05-개발-표준-총정리/) | `../ztcfbook/제02편/05-개발-표준-총정리.md` |
| [`CH-06-06-식별자-명명규칙`](./chapters/CH-06-06-식별자-명명규칙/TASK.md) | 6 | 식별자 명명규칙 | `draft-exists` | [`chapters/CH-06-06-식별자-명명규칙`](./chapters/CH-06-06-식별자-명명규칙/) | `../ztcfbook/제02편/06-식별자-명명규칙.md` |
| [`CH-07-07-코드-DB-명명규칙`](./chapters/CH-07-07-코드-DB-명명규칙/TASK.md) | 7 | 코드·DB 명명규칙 | `draft-exists` | [`chapters/CH-07-07-코드-DB-명명규칙`](./chapters/CH-07-07-코드-DB-명명규칙/) | `../ztcfbook/제02편/07-코드-DB-명명규칙.md` |

## 제3편 · 거래 개발 실무

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-08-08-거래-설계`](./chapters/CH-08-08-거래-설계/TASK.md) | 8 | 거래 설계 (설계 단계) | `draft-exists` | [`chapters/CH-08-08-거래-설계`](./chapters/CH-08-08-거래-설계/) | `../ztcfbook/제03편/08-거래-설계.md` |
| [`CH-09-09-표준-전문과-DTO`](./chapters/CH-09-09-표준-전문과-DTO/TASK.md) | 9 | 표준 전문과 DTO | `draft-exists` | [`chapters/CH-09-09-표준-전문과-DTO`](./chapters/CH-09-09-표준-전문과-DTO/) | `../ztcfbook/제03편/09-표준-전문과-DTO.md` |
| [`CH-10-10-TransactionHandler-개발`](./chapters/CH-10-10-TransactionHandler-개발/TASK.md) | 10 | TransactionHandler 개발 | `draft-exists` | [`chapters/CH-10-10-TransactionHandler-개발`](./chapters/CH-10-10-TransactionHandler-개발/) | `../ztcfbook/제03편/10-TransactionHandler-개발.md` |
| [`CH-11-11-품질-속성-구현`](./chapters/CH-11-11-품질-속성-구현/TASK.md) | 11 | 품질 속성 구현 | `draft-exists` | [`chapters/CH-11-11-품질-속성-구현`](./chapters/CH-11-11-품질-속성-구현/) | `../ztcfbook/제03편/11-품질-속성-구현.md` |

## 제4편 · 보안·인증·통제

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-12-12-세션-로그인-권한`](./chapters/CH-12-12-세션-로그인-권한/TASK.md) | 12 | 세션·로그인·권한 | `draft-exists` | [`chapters/CH-12-12-세션-로그인-권한`](./chapters/CH-12-12-세션-로그인-권한/) | `../ztcfbook/제04편/12-세션-로그인-권한.md` |
| [`CH-13-13-JWT-SSO-Gateway`](./chapters/CH-13-13-JWT-SSO-Gateway/TASK.md) | 13 | JWT · SSO · Gateway | `draft-exists` | [`chapters/CH-13-13-JWT-SSO-Gateway`](./chapters/CH-13-13-JWT-SSO-Gateway/) | `../ztcfbook/제04편/13-JWT-SSO-Gateway.md` |
| [`CH-14-14-거래통제-정책`](./chapters/CH-14-14-거래통제-정책/TASK.md) | 14 | 거래통제·정책 | `draft-exists` | [`chapters/CH-14-14-거래통제-정책`](./chapters/CH-14-14-거래통제-정책/) | `../ztcfbook/제04편/14-거래통제-정책.md` |

## 제5편 · 플랫폼·운영 관리 (OM)

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-15-15-OM-아키텍처와-개발`](./chapters/CH-15-15-OM-아키텍처와-개발/TASK.md) | 15 | OM 아키텍처와 개발 | `draft-exists` | [`chapters/CH-15-15-OM-아키텍처와-개발`](./chapters/CH-15-15-OM-아키텍처와-개발/) | `../ztcfbook/제05편/15-OM-아키텍처와-개발.md` |
| [`CH-16-16-API-Gateway-UI-채널`](./chapters/CH-16-16-API-Gateway-UI-채널/TASK.md) | 16 | API Gateway · UI 채널 | `draft-exists` | [`chapters/CH-16-16-API-Gateway-UI-채널`](./chapters/CH-16-16-API-Gateway-UI-채널/) | `../ztcfbook/제05편/16-API-Gateway-UI-채널.md` |
| [`CH-17-17-Batch-Scheduler-이벤트`](./chapters/CH-17-17-Batch-Scheduler-이벤트/TASK.md) | 17 | Batch · Scheduler · 이벤트 | `draft-exists` | [`chapters/CH-17-17-Batch-Scheduler-이벤트`](./chapters/CH-17-17-Batch-Scheduler-이벤트/) | `../ztcfbook/제05편/17-Batch-Scheduler-이벤트.md` |
| [`CH-18-18-데이터-DB-아키텍처`](./chapters/CH-18-18-데이터-DB-아키텍처/TASK.md) | 18 | 데이터·DB 아키텍처 | `draft-exists` | [`chapters/CH-18-18-데이터-DB-아키텍처`](./chapters/CH-18-18-데이터-DB-아키텍처/) | `../ztcfbook/제05편/18-데이터-DB-아키텍처.md` |

## 제6편 · 환경·빌드·배포

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-19-19-로컬-개발환경`](./chapters/CH-19-19-로컬-개발환경/TASK.md) | 19 | 로컬 개발환경 | `draft-exists` | [`chapters/CH-19-19-로컬-개발환경`](./chapters/CH-19-19-로컬-개발환경/) | `../ztcfbook/제06편/19-로컬-개발환경.md` |
| [`CH-20-20-CICD-릴리즈-DR`](./chapters/CH-20-20-CICD-릴리즈-DR/TASK.md) | 20 | CI/CD · 릴리즈 · DR | `draft-exists` | [`chapters/CH-20-20-CICD-릴리즈-DR`](./chapters/CH-20-20-CICD-릴리즈-DR/) | `../ztcfbook/제06편/20-CICD-릴리즈-DR.md` |

## 제7편 · 테스트·품질 보증

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-21-21-테스트-전략`](./chapters/CH-21-21-테스트-전략/TASK.md) | 21 | 테스트 전략 | `draft-exists` | [`chapters/CH-21-21-테스트-전략`](./chapters/CH-21-21-테스트-전략/) | `../ztcfbook/제07편/21-테스트-전략.md` |

## 제8편 · 실습 — End-to-End 샘플

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-22-22-조회-거래-SV-고객요약`](./chapters/CH-22-22-조회-거래-SV-고객요약/TASK.md) | 22 | 조회 거래 (SV 고객요약) | `completed` | [`chapters/CH-22-22-조회-거래-SV-고객요약`](./chapters/CH-22-22-조회-거래-SV-고객요약/) | `../ztcfbook/제08편/22-조회-거래-SV-고객요약.md` |
| [`CH-23-23-목록-페이징-등록-변경`](./chapters/CH-23-23-목록-페이징-등록-변경/TASK.md) | 23 | 목록·페이징·등록·변경 | `draft-exists` | [`chapters/CH-23-23-목록-페이징-등록-변경`](./chapters/CH-23-23-목록-페이징-등록-변경/) | `../ztcfbook/제08편/23-목록-페이징-등록-변경.md` |

## 제9편 · 모듈별 레퍼런스 (Quick Start)

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-24-24-tcf-core-web-util`](./chapters/CH-24-24-tcf-core-web-util/TASK.md) | 24 | tcf-core · tcf-web · tcf-util | `draft-exists` | [`chapters/CH-24-24-tcf-core-web-util`](./chapters/CH-24-24-tcf-core-web-util/) | `../ztcfbook/제09편/24-tcf-core-web-util.md` |
| [`CH-25-25-tcf-om-ui-uj`](./chapters/CH-25-25-tcf-om-ui-uj/TASK.md) | 25 | tcf-om · tcf-ui · tcf-uj | `draft-exists` | [`chapters/CH-25-25-tcf-om-ui-uj`](./chapters/CH-25-25-tcf-om-ui-uj/) | `../ztcfbook/제09편/25-tcf-om-ui-uj.md` |
| [`CH-26-26-tcf-gateway-jwt`](./chapters/CH-26-26-tcf-gateway-jwt/TASK.md) | 26 | tcf-gateway · tcf-jwt | `draft-exists` | [`chapters/CH-26-26-tcf-gateway-jwt`](./chapters/CH-26-26-tcf-gateway-jwt/) | `../ztcfbook/제09편/26-tcf-gateway-jwt.md` |
| [`CH-27-27-tcf-eai-cache-batch`](./chapters/CH-27-27-tcf-eai-cache-batch/TASK.md) | 27 | tcf-eai · tcf-cache · tcf-batch | `draft-exists` | [`chapters/CH-27-27-tcf-eai-cache-batch`](./chapters/CH-27-27-tcf-eai-cache-batch/) | `../ztcfbook/제09편/27-tcf-eai-cache-batch.md` |
| [`CH-28-28-tcf-cicd-scripts`](./chapters/CH-28-28-tcf-cicd-scripts/TASK.md) | 28 | tcf-cicd · tcf-scripts | `draft-exists` | [`chapters/CH-28-28-tcf-cicd-scripts`](./chapters/CH-28-28-tcf-cicd-scripts/) | `../ztcfbook/제09편/28-tcf-cicd-scripts.md` |
| [`CH-29-29-업무-WAR-ic-pc-ms-sv-pd`](./chapters/CH-29-29-업무-WAR-ic-pc-ms-sv-pd/TASK.md) | 29 | ic · pc · ms · sv · pd (업무 WAR 5) | `draft-exists` | [`chapters/CH-29-29-업무-WAR-ic-pc-ms-sv-pd`](./chapters/CH-29-29-업무-WAR-ic-pc-ms-sv-pd/) | `../ztcfbook/제09편/29-업무-WAR-ic-pc-ms-sv-pd.md` |
| [`CH-30-30-업무-WAR-eb-ep-ss-mg`](./chapters/CH-30-30-업무-WAR-eb-ep-ss-mg/TASK.md) | 30 | eb · ep · ss · mg (업무 WAR 4) | `draft-exists` | [`chapters/CH-30-30-업무-WAR-eb-ep-ss-mg`](./chapters/CH-30-30-업무-WAR-eb-ep-ss-mg/) | `../ztcfbook/제09편/30-업무-WAR-eb-ep-ss-mg.md` |

## 제10편 · 설계 근거와 로드맵

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`CH-31-31-공식-설계안-매핑`](./chapters/CH-31-31-공식-설계안-매핑/TASK.md) | 31 | 공식 설계안 매핑 | `draft-exists` | [`chapters/CH-31-31-공식-설계안-매핑`](./chapters/CH-31-31-공식-설계안-매핑/) | `../ztcfbook/제10편/31-공식-설계안-매핑.md` |
| [`CH-32-32-Gap-보완-향후-과제`](./chapters/CH-32-32-Gap-보완-향후-과제/TASK.md) | 32 | Gap·보완·향후 과제 | `draft-exists` | [`chapters/CH-32-32-Gap-보완-향후-과제`](./chapters/CH-32-32-Gap-보완-향후-과제/) | `../ztcfbook/제10편/32-Gap-보완-향후-과제.md` |

## 부록

| ID | No | 제목 | status | workspace | target |
| --- | --- | --- | --- | --- | --- |
| [`APP-A-A-업무코드-표준표`](./chapters/APP-A-A-업무코드-표준표/TASK.md) | A | 업무코드 표준표 | `draft-exists` | [`chapters/APP-A-A-업무코드-표준표`](./chapters/APP-A-A-업무코드-표준표/) | `../ztcfbook/부록/A-업무코드-표준표.md` |
| [`APP-B-B-ServiceId-명명규칙`](./chapters/APP-B-B-ServiceId-명명규칙/TASK.md) | B | ServiceId 명명규칙 | `draft-exists` | [`chapters/APP-B-B-ServiceId-명명규칙`](./chapters/APP-B-B-ServiceId-명명규칙/) | `../ztcfbook/부록/B-ServiceId-명명규칙.md` |
| [`APP-C-C-거래코드-명명규칙`](./chapters/APP-C-C-거래코드-명명규칙/TASK.md) | C | 거래코드 명명규칙 | `draft-exists` | [`chapters/APP-C-C-거래코드-명명규칙`](./chapters/APP-C-C-거래코드-명명규칙/) | `../ztcfbook/부록/C-거래코드-명명규칙.md` |
| [`APP-D-D-표준-전문-JSON-예시`](./chapters/APP-D-D-표준-전문-JSON-예시/TASK.md) | D | 표준 전문 JSON 예시 | `draft-exists` | [`chapters/APP-D-D-표준-전문-JSON-예시`](./chapters/APP-D-D-표준-전문-JSON-예시/) | `../ztcfbook/부록/D-표준-전문-JSON-예시.md` |
| [`APP-E-E-Mapper-XML-템플릿`](./chapters/APP-E-E-Mapper-XML-템플릿/TASK.md) | E | Mapper XML 템플릿 | `draft-exists` | [`chapters/APP-E-E-Mapper-XML-템플릿`](./chapters/APP-E-E-Mapper-XML-템플릿/) | `../ztcfbook/부록/E-Mapper-XML-템플릿.md` |
| [`APP-F-F-오류코드-표준표`](./chapters/APP-F-F-오류코드-표준표/TASK.md) | F | 오류코드 표준표 | `draft-exists` | [`chapters/APP-F-F-오류코드-표준표`](./chapters/APP-F-F-오류코드-표준표/) | `../ztcfbook/부록/F-오류코드-표준표.md` |
| [`APP-G-G-application-yml-템플릿`](./chapters/APP-G-G-application-yml-템플릿/TASK.md) | G | application yml 템플릿 | `draft-exists` | [`chapters/APP-G-G-application-yml-템플릿`](./chapters/APP-G-G-application-yml-템플릿/) | `../ztcfbook/부록/G-application-yml-템플릿.md` |
| [`APP-H-H-개발-완료-체크리스트`](./chapters/APP-H-H-개발-완료-체크리스트/TASK.md) | H | 개발 완료 체크리스트 | `draft-exists` | [`chapters/APP-H-H-개발-완료-체크리스트`](./chapters/APP-H-H-개발-완료-체크리스트/) | `../ztcfbook/부록/H-개발-완료-체크리스트.md` |
| [`APP-I-I-코드-리뷰-체크리스트`](./chapters/APP-I-I-코드-리뷰-체크리스트/TASK.md) | I | 코드 리뷰 체크리스트 | `draft-exists` | [`chapters/APP-I-I-코드-리뷰-체크리스트`](./chapters/APP-I-I-코드-리뷰-체크리스트/) | `../ztcfbook/부록/I-코드-리뷰-체크리스트.md` |
| [`APP-J-J-운영-전환-체크리스트`](./chapters/APP-J-J-운영-전환-체크리스트/TASK.md) | J | 운영 전환 체크리스트 | `draft-exists` | [`chapters/APP-J-J-운영-전환-체크리스트`](./chapters/APP-J-J-운영-전환-체크리스트/) | `../ztcfbook/부록/J-운영-전환-체크리스트.md` |
| [`APP-K-K-모듈-포트-Context-WAR-매핑표`](./chapters/APP-K-K-모듈-포트-Context-WAR-매핑표/TASK.md) | K | 모듈 포트 Context WAR 매핑표 | `draft-exists` | [`chapters/APP-K-K-모듈-포트-Context-WAR-매핑표`](./chapters/APP-K-K-모듈-포트-Context-WAR-매핑표/) | `../ztcfbook/부록/K-모듈-포트-Context-WAR-매핑표.md` |
| [`APP-L-L-TCF-핵심-테이블-DDL-요약`](./chapters/APP-L-L-TCF-핵심-테이블-DDL-요약/TASK.md) | L | TCF 핵심 테이블 DDL 요약 | `draft-exists` | [`chapters/APP-L-L-TCF-핵심-테이블-DDL-요약`](./chapters/APP-L-L-TCF-핵심-테이블-DDL-요약/) | `../ztcfbook/부록/L-TCF-핵심-테이블-DDL-요약.md` |
| [`APP-M-M-명명규칙-21주제-색인`](./chapters/APP-M-M-명명규칙-21주제-색인/TASK.md) | M | 명명규칙 21주제 색인 | `draft-exists` | [`chapters/APP-M-M-명명규칙-21주제-색인`](./chapters/APP-M-M-명명규칙-21주제-색인/) | `../ztcfbook/부록/M-명명규칙-21주제-색인.md` |
| [`APP-N-N-소스-인덱스`](./chapters/APP-N-N-소스-인덱스/TASK.md) | N | 소스 인덱스 | `draft-exists` | [`chapters/APP-N-N-소스-인덱스`](./chapters/APP-N-N-소스-인덱스/) | `../ztcfbook/부록/N-소스-인덱스.md` |

## 통계

- `completed`: 3
- `draft-exists`: 44
