# SOURCE-MAP.md

# PDMG 애플리케이션 아키텍처와 개발 가이드 — 원천자료 라우팅 맵

> 이 파일은 `AGENTS.md`와 `BOOK-WRITING-RULE.md`를 보조하는 **장별 원천자료 탐색 지도**다.  
> Codex는 장을 집필하거나 수정하기 전에 이 파일에서 해당 장의 자료를 확인하고, 문서만 요약하지 말고 **실제 구현 사실 → 재분석/정정 → 기본 설명 → 다이어그램 → 보조자료** 순으로 검증하여 하나의 기술서 본문으로 재구성한다.

---

## 1. 목적

이 Source Map의 목적은 다음과 같다.

1. 1~33장 각각에 어떤 원천자료를 우선 읽어야 하는지 고정한다.
2. 동일 주제의 기본 문서와 `-1.md` 재분석 문서를 함께 사용하되 역할을 구분한다.
3. 다이어그램을 사실 판정의 최종 근거가 아니라 구조 설명 보조자료로 사용한다.
4. 특정 장을 집필할 때 137개 자료 전체를 무작정 읽지 않고, **필수 → 검증 → 보조** 순으로 탐색하게 한다.
5. AS-IS와 TO-BE가 섞이는 것을 방지한다.
6. 장이 뒤로 갈수록 요약되는 현상을 막기 위해 각 장의 **검증 질문**을 함께 고정한다.

---

## 2. 이 파일의 사용 순서

장 `N`을 집필할 때 다음 순서를 따른다.

```text
AGENTS.md
   ↓
BOOK-WRITING-RULE.md
   ↓
BOOK-TOC / 전체 목차
   ↓
SOURCE-MAP.md의 N장 항목
   ↓
N-2장 / N-1장 완성본
   ↓
[필수] 기본 문서
   ↓
[검증] *-1.md / 구현 분석 문서
   ↓
[시각화] 다이어그램 문서
   ↓
[보조] 이슈·설계·Q&A·프롬프트 자료
   ↓
실제 Source / Configuration / Test 확인
   ↓
AS-IS / TO-BE 분리
   ↓
본문 집필
   ↓
품질 Gate
```

`SOURCE-MAP.md`에 파일이 연결되어 있다는 이유만으로 해당 파일의 내용을 정답으로 복사하지 않는다.

---

## 3. 사실 판정 우선순위

PDMG의 현재 구현 사실은 다음 순서로 판정한다.

```text
Priority 1  실제 Source / Configuration / Test
     ↓
Priority 2  소스 기반 재분석·정정 문서 (*-1.md)
     ↓
Priority 3  기본 설명 문서
     ↓
Priority 4  Architecture / Diagram 문서
     ↓
Priority 5  Issue / 설계 / Q&A 문서
     ↓
Priority 6  Prompt / 집필 보조 자료
```

### 문서 충돌 시

- 기본 문서와 `-1.md`가 다르면 `-1.md`의 정정 내용을 우선 검토한다.
- `-1.md`도 실제 소스와 다르면 실제 소스와 설정을 우선한다.
- 다이어그램이 소스와 다르면 다이어그램을 수정 대상으로 본다.
- 설계안은 현재 구현처럼 쓰지 않는다.
- 확인할 수 없는 내용은 `문서 기준`, `설계안`, `확인 필요`로 표시한다.

---

## 4. 자료 유형 정의

| 구분 | 역할 | 집필 시 사용 방법 |
|---|---|---|
| 기본 | 개념·설계 의도·기본 흐름 | 장의 설명 골격을 잡는다. |
| 재분석 | 실제 구현 검증·정정·주의사항 | AS-IS 정합성과 예외 조건을 검증한다. |
| 다이어그램 | 구조·실행 흐름 시각화 | 장 도입부, 실행 흐름, 비교 설명에 사용한다. |
| 심화/Q&A | 특정 문제·경계·장애·대안 | Why, Failure, Anti Pattern, Architecture Decision을 보강한다. |
| 프롬프트 | CRUD 개발·집필 보조 | 제9부 실전 개발과 부록에 사용한다. |
| 편집자료 | 목차·원본 이력 | 책 구조 또는 이력 확인에만 사용한다. |

---

## 5. 파일명 주의사항

압축 원천자료의 실제 파일명을 기준으로 한다.

- 목차 표기 `59.Service 다이어그램.md` → 실제 파일명은 **`59. Service 다이어그램.md`**
- 목차 표기 `73.pdmg 패키지 다이어그램.md` → 실제 파일명은 **`73.pdmg  패키지 다이어그램.md`**
- `MG-NAMING_CONVENTION(원본).md`는 편집 이력 자료로 취급하고 현재 규칙의 최종 근거로 사용하지 않는다.
- `zz.PDMG 애플리케이션 아키텍처와 개발 가이드 목차.md.md`는 책 전체 목차 확인용 편집자료다.

---

# 6. 장별 Source Map

## 제1부. PDMG 아키텍처 이해

### 1장. PDMG 시스템 개요

**집필 목적**  
PDMG를 처음 접하는 독자가 네 개 주요 모듈과 온라인 거래 시스템의 전체 위치를 이해하게 한다.

**필수 자료**
- `46.전체 아키텍처 구조.md`
- `50.기술스택 다이어그램.md`
- `75.PDMG 컴포넌트 다이어그램.md`

**보조 자료**
- `48.pdmg-fw 아키텍처 다이어그램.md`
- `49.pdmg-service 아키텍처 다이어그램.md`
- `51.pdmg-ui 아키텍처 다이어그램.md`
- `47.pdmg-jwt 아키텍처 다이어그램.md`

**소스 검증 대상**
- `pdmg-ui`, `pdmg-service`, `pdmg-fw`, `pdmg-jwt`의 실제 모듈 구성
- 실행 설정, 포트, 모듈 간 호출관계

**반드시 답할 질문**
- PDMG는 무엇을 처리하는 시스템인가?
- 네 개 모듈은 각각 어떤 책임을 가지는가?
- 온라인 요청은 어떤 모듈을 거쳐 이동하는가?
- TCF ON/OFF는 전체 구조에서 어떤 차이를 만드는가?

---

### 2장. 전체 온라인 거래 빅픽처

**기본 자료**
- `05.전체 빅픽처 흐름.md`
- `00.BigPicture Tx 흐름.md`
- `00.Big Picture Image.md`

**재분석·검증 자료**
- `05.전체 빅픽처 흐름-1.md`
- `00.BigPicture Tx 처리-1.md`

**다이어그램**
- `54.http호출 다이어그램.md`
- `48.pdmg-fw 아키텍처 다이어그램.md`
- `49.pdmg-service 아키텍처 다이어그램.md`

**반드시 답할 질문**
- Browser부터 DB까지 요청은 어떤 단계로 이동하는가?
- HTTP 요청, 시스템 선처리, TCF, 업무처리, 시스템 후처리 경계는 어디인가?
- Request Thread와 Worker Thread는 어디에서 바뀌는가?
- 온라인 거래와 DB Transaction은 왜 같은 개념이 아닌가?

---

### 3장. 레이어드 아키텍처와 컴포넌트

**기본 자료**
- `02.어플리케이션 컴포넌트 구조.md`
- `03.어플리케이션 레이어드 아키텍처.md`

**재분석·검증 자료**
- `02.어플리케이션 컴포넌트 구조-1.md`
- `03.어플리케이션 레이어드 아키텍처-1.md`

**다이어그램**
- `74.PDMG 클린 아키텍처 다이어그램.md`
- `75.PDMG 컴포넌트 다이어그램.md`
- `73.pdmg  패키지 다이어그램.md`

**보조 자료**
- `48.pdmg-fw 아키텍처 다이어그램.md`
- `49.pdmg-service 아키텍처 다이어그램.md`

**반드시 답할 질문**
- Framework / Entry / Application / Domain Rule / Contract / Persistence의 책임은 무엇인가?
- 허용되는 의존 방향과 금지되는 역방향 의존은 무엇인가?
- 현재 구현과 목표 구조가 다르다면 어디가 다른가?

---

## 제2부. 식별체계와 개발 구조

### 4장. 애플리케이션 분류와 Service ID

**기본 자료**
- `00.NSIGHT 애플리케이션 코드 분류표.md`
- `09.서비스ID.md`

**재분석·검증 자료**
- `09.서비스ID-1.md`

**다이어그램**
- `61.Service ID 아키텍처 다이어그램.md`
- `62.화면번호-serviceid-controller-service 관계 다이어그램.md`

**보조 자료**
- `53.네이밍 아키텍처 다이어그램.md`
- `06.네이밍 형식-1.md`

**반드시 답할 질문**
- 프로그램 ID, 화면번호, Service ID는 어떻게 다른가?
- Service ID는 실제 요청에서 어떻게 결정되는가?
- Handler Registry, 거래통제, Timeout과 Service ID는 어떻게 연결되는가?

---

### 5장. 네이밍 규칙

**기본 자료**
- `MG-NAMING_CONVENTION.md`
- `00.MG-NAMING_CONVENTION.md`
- `06.네이밍 형식.md`
- `네이밍원칙.md`

**재분석·검증 자료**
- `06.네이밍 형식-1.md`

**다이어그램**
- `53.네이밍 아키텍처 다이어그램.md`

**편집 이력**
- `MG-NAMING_CONVENTION(원본).md`

**반드시 답할 질문**
- 프로그램 접두어와 Service ID는 어떤 관계인가?
- Java package/class/DTO/DAO/Mapper/SQL ID가 어떤 규칙으로 연결되는가?
- 현재 표준과 과거 원본이 다르면 무엇을 기준으로 해야 하는가?

---

### 6장. 패키지와 프로젝트 구조

**기본 자료**
- `04.패키지구조.md`

**재분석·검증 자료**
- `04.패키지구조-1.md`

**다이어그램**
- `73.pdmg  패키지 다이어그램.md`
- `74.PDMG 클린 아키텍처 다이어그램.md`

**보조 자료**
- `49.pdmg-service 아키텍처 다이어그램.md`

**소스 검증 대상**
- 실제 업무 package tree
- Component Scan / Mapper Scan / AOP pointcut 대상 package

**반드시 답할 질문**
- 신규 업무축은 어느 package에 추가하는가?
- entry/application/dto/persistence/client/config/support 책임은 무엇인가?
- package 구조가 Spring scan 및 AOP 적용 범위에 어떤 영향을 주는가?

---

### 7장. 도메인 정의와 호출 방식

**기본 자료**
- `07.도메인 정의 및 호출방식.md`

**재분석·검증 자료**
- `07.도메인 정의 및 호출방식-1.md`

**보조 자료**
- `46.전체 아키텍처 구조.md`
- `54.http호출 다이어그램.md`
- `66.TCF OFF 다이어그램.md`

**반드시 답할 질문**
- PDMG 문서에서 사용하는 “도메인”은 어떤 범위를 의미하는가?
- TCF ON/OFF, UI 직접호출/Relay, 외부시스템 호출은 어떻게 구분되는가?
- 도메인 간 직접 의존을 어디까지 허용하는가?

---

## 제3부. 온라인 요청과 TCF 프레임워크

### 8장. HTTP 요청과 표준 전문

**기본 자료**
- `10.전문.md`
- `11.Http CORS적용.md`
- `12.http요청.md`
- `27.전문 DTO.md`

**재분석·검증 자료**
- `10.전문-1.md`
- `11.Http CORS적용-1.md`
- `12.http요청-1.md`
- `27.전문 DTO-1.md`

**다이어그램**
- `54.http호출 다이어그램.md`

**보조 자료**
- `51.pdmg-ui 아키텍처 다이어그램.md`

**반드시 답할 질문**
- 표준 Header와 업무 Body는 어떻게 분리되는가?
- Service ID와 GUID는 어느 위치에서 전달·보완되는가?
- TCF ON URL과 TCF OFF URL은 어떻게 다른가?
- 문서상 UI Relay와 실제 현행 호출 방식이 다르다면 무엇이 AS-IS인가?

---

### 9장. Filter와 Spring MVC

**기본 자료**
- `13.Filter-DefaultFilter.md`
- `14.Spring MVC.md`

**재분석·검증 자료**
- `13.Filter-DefaultFilter-1.md`
- `14.Spring MVC-1.md`

**심화 자료**
- `30.왜 Filter와 Interceptor을 합치면 안되는가.md`

**보조 다이어그램**
- `54.http호출 다이어그램.md`

**반드시 답할 질문**
- Servlet Filter와 Spring MVC의 경계는 어디인가?
- Request Body caching, JWT 추출, GUID, ServiceContext, MDC는 어느 시점에 처리되는가?
- Filter와 Interceptor를 하나로 합치면 어떤 문제가 생기는가?

---

### 10장. Interceptor와 시스템 선처리

**기본 자료**
- `14.시스템 선후처리.md`
- `15.Interceptor-ServicePrevention.preHandle.md`

**재분석·검증 자료**
- `14.시스템 선후처리-1.md`
- `15.Interceptor-ServicePrevention.preHandle-1.md`

**심화 자료**
- `40.시스템 선처리에서 어떤 필터를 처리하는가.md`

**다이어그램**
- `55.시스템선후처리다이어그램.md`

**반드시 답할 질문**
- Filter가 끝난 뒤 Interceptor가 무엇을 추가로 처리하는가?
- 거래통제는 어떤 식별자를 사용하며 어디에서 차단하는가?
- 시스템 선처리와 업무 Transaction은 같은 경계인가?

---

### 11장. ServiceContext와 GUID

**기본 자료**
- `16.Service Context.md`

**재분석·검증 자료**
- `16.Service Context-1.md`

**심화 자료**
- `41.시스템 컨텍스트 정보는 어디서 생성되는가.md`

**다이어그램**
- `69.GUID 아키텍처 다이어그램.md`
- `70.Service Context 다이어그램.md`

**반드시 답할 질문**
- ServiceContext는 언제 생성되고 언제 제거되는가?
- ThreadLocal, MDC, JWT 사용자정보, GUID와 어떤 관계인가?
- Worker Thread 또는 비동기 Thread로 이동할 때 무엇이 자동 전파되지 않는가?

---

### 12장. TCF 온라인 거래 프레임워크

**기본 자료**
- `16.TCF-OnlineTransactionController.md`
- `17.TCF-TcfFace-Dispatcher.md`
- `26.시스템후처리.md`
- `tcf.md`

**재분석·검증 자료**
- `16.TCF-OnlineTransactionController-1.md`
- `17.TCF-TcfFace-Dispatcher-1.md`
- `26.시스템후처리-1.md`

**다이어그램**
- `56.TCF 아키텍처 다이어그램.md`
- `48.pdmg-fw 아키텍처 다이어그램.md`

**반드시 답할 질문**
- OnlineTransactionController → TcfFacade → STF → Dispatcher → Handler → ETF 흐름은 어떻게 연결되는가?
- TransactionContext는 어떤 역할을 하는가?
- TCF가 담당하는 것과 담당하지 않는 것은 무엇인가?
- 정상/실패 거래에서 후처리는 어떻게 달라지는가?

---

### 13장. TCF OFF 호환 구조

**필수 자료**
- `37.TCF 프레임워크 적용하지 않는 방법.md`
- `38.TCF off 이면 트랜잭션 시작은 어디인가.md`
- `39.TCF on 이면 트랜잭션 시작은 어디인가.md`

**다이어그램**
- `66.TCF OFF 다이어그램.md`

**교차 검증 자료**
- `19.Business-Facade-1.md`
- `20.Tranaactional-rdwTransactionManager-Begin-1.md`
- `20.타임아웃-1.md`

**반드시 답할 질문**
- TCF OFF에서 비활성화되는 Bean과 유지되는 공통 처리는 무엇인가?
- Controller → Facade 직접 호출 시 Transaction은 어디에서 시작되는가?
- TCF ON/OFF가 Timeout, 선후처리, 예외처리에 미치는 영향은 무엇인가?

---

## 제4부. 업무 애플리케이션 구현

### 14장. Handler와 Controller

**기본 자료**
- `18.Business-Handler.md`

**재분석·검증 자료**
- `18.Business-Handler-1.md`

**다이어그램**
- `57.Controller 다이어그램.md`
- `62.화면번호-serviceid-controller-service 관계 다이어그램.md`

**반드시 답할 질문**
- TCF ON에서 Handler가 왜 Inbound Adapter 역할을 하는가?
- TCF OFF의 Controller와 어떤 책임 차이가 있는가?
- Handler/Controller에서 업무 로직이나 Transaction을 과도하게 처리하면 왜 문제가 되는가?

---

### 15장. Business Facade

**기본 자료**
- `19.Business-Facade.md`

**재분석·검증 자료**
- `19.Business-Facade-1.md`

**교차 검증 자료**
- `01.트랜잭션처리 변경-1.md`
- `39.TCF on 이면 트랜잭션 시작은 어디인가.md`
- `38.TCF off 이면 트랜잭션 시작은 어디인가.md`

**반드시 답할 질문**
- Facade는 Handler/Controller와 Service 사이에서 무엇을 조정하는가?
- `@Transactional`이 선언되어 있어도 항상 최외곽 Transaction 경계라고 말할 수 있는가?
- TCF ON + Timeout ON과 TCF OFF에서 Facade의 Transaction 의미가 어떻게 달라지는가?

---

### 16장. 업무 선후처리

**기본 자료**
- `21.업무선처리-BizPrePostAspect.md`
- `25.업무후처리-BizPrePostAspect.md`

**재분석·검증 자료**
- `21.업무선처리-BizPrePostAspect-1.md`
- `25.업무후처리-BizPrePostAspect-1.md`

**다이어그램**
- `58.업무선후처리 다이어그램.md`

**교차 검증 자료**
- `01.트랜잭션처리 변경-1.md`

**반드시 답할 질문**
- BizPrePostAspect의 현재 pointcut은 어디인가?
- 업무 선처리/Service/업무 후처리가 같은 Transaction에 참여하는 조건은 무엇인가?
- 오류 이력 저장을 독립 Transaction으로 분리해야 하는 경우는 언제인가?

---

### 17장. Business Service와 Rule

**기본 자료**
- `22.Business-Service.md`

**재분석·검증 자료**
- `22.Business-Service-1.md`

**다이어그램**
- `59. Service 다이어그램.md`

**교차 검증 자료**
- `03.어플리케이션 레이어드 아키텍처-1.md`
- `49.pdmg-service 아키텍처 다이어그램.md`

**반드시 답할 질문**
- Service와 Rule의 책임은 어떻게 나누는가?
- ServiceContext는 업무 처리 중 어떻게 활용되는가?
- 여러 DAO/외부 Client 호출과 결과 DTO 조립은 어느 계층이 담당하는가?

---

### 18장. DTO와 데이터 계약

**기본 자료**
- `27.전문 DTO.md`

**재분석·검증 자료**
- `27.전문 DTO-1.md`

**다이어그램**
- `63.DTO 다이어그램.md`

**교차 검증 자료**
- `10.전문-1.md`
- `12.http요청-1.md`

**반드시 답할 질문**
- DTOin / DTOout / DTOSub의 역할은 무엇인가?
- 표준 Header, ServiceContext, 업무 DTO를 왜 분리해야 하는가?
- TCF ON/OFF에서 DTO 변환 경로가 어떻게 달라지는가?

---

### 19장. DAO와 MyBatis Mapper

**기본 자료**
- `23.DAO.md`
- `24.DAO-Mapper.md`
- `24.DAO-Namespace.md`

**재분석·검증 자료**
- `23.DAO-1.md`
- `24.DAO-Mapper-1.md`
- `24.DAO-Namespace-1.md`

**다이어그램**
- `60.DAO 다이어그램.md`

**반드시 답할 질문**
- DAO FQCN, Mapper namespace, DAO method, Statement ID가 어떻게 일치해야 하는가?
- Mapper Scan과 Mapper XML 로딩은 어떻게 연결되는가?
- `${}`와 `#{}`의 보안·바인딩 차이는 무엇인가?
- DAO 계층에서 업무 로직을 처리하면 왜 문제가 되는가?

---

### 20장. 대용량 조회와 페이징

**기본 자료**
- `08.대용량 페이징 처리방식.md`

**재분석·검증 자료**
- `08.대용량 페이징 처리방식-1.md`

**다이어그램**
- `76.페이징 아키텍처 다이어그램.md`

**교차 검증 자료**
- `23.DAO-1.md`
- `24.DAO-Mapper-1.md`

**반드시 답할 질문**
- 현재 Offset/ROWNUM 페이징 계약은 무엇인가?
- pageSize 제한과 offset overflow 위험은 무엇인가?
- 깊은 페이지에서 Keyset/Cursor 방식을 언제 고려해야 하는가?
- 정렬 기준과 Index가 성능에 어떤 영향을 주는가?

---

## 제5부. 트랜잭션과 타임아웃

### 21장. Spring 트랜잭션 기초

**기본 자료**
- `20.Tranaactional-rdwTransactionManager-Begin.md`
- `트랜잭션처리.md`

**재분석·검증 자료**
- `20.Tranaactional-rdwTransactionManager-Begin-1.md`

**다이어그램**
- `71.Transaction Manager.md`
- `65.트랜잭션 다이어그램.md`

**반드시 답할 질문**
- DataSource → SqlSessionFactory → SqlSessionTemplate → TransactionManager가 어떻게 연결되는가?
- `@Transactional` proxy가 BEGIN/COMMIT/ROLLBACK을 어떻게 만든다고 설명할 수 있는가?
- Thread와 JDBC Connection binding은 어떤 관계인가?

---

### 22장. PDMG 트랜잭션 경계

**기본 자료**
- `01.트랜잭션처리 변경.md`
- `pdmg-service 트랜잭션흐름.md`
- `트랜잭션처리.md`

**재분석·최우선 검증 자료**
- `01.트랜잭션처리 변경-1.md`

**다이어그램**
- `65.트랜잭션 다이어그램.md`
- `71.Transaction Manager.md`

**심화 자료**
- `39.TCF on 이면 트랜잭션 시작은 어디인가.md`
- `38.TCF off 이면 트랜잭션 시작은 어디인가.md`
- `33.요청쓰레드와 Work쓰레드 분리아키텍처.md`

**중요 판정 규칙**
- TCF ON + Timeout ON에서 Transaction 시작 위치를 기본 문서의 표현만으로 단정하지 않는다.
- Worker Thread 및 `TransactionTemplate`이 최외곽 Transaction을 만드는지 실제 구현을 우선 검증한다.
- Facade `@Transactional`은 이미 열린 Transaction에 참여하는 것인지 별도 시작점인지 실행 조건별로 구분한다.

**반드시 답할 질문**
- TCF ON + Timeout ON / Timeout OFF / TCF OFF 각각에서 최외곽 Transaction은 어디에서 시작되는가?
- 업무 선처리, Service, DAO, 업무 후처리는 어느 경계에 참여하는가?
- 시스템 선후처리와 ImageLog는 동일 Transaction인가?
- 자기호출과 비동기 Thread는 Transaction에 어떤 영향을 주는가?

---

### 23장. 타임아웃과 작업 취소

**기본 자료**
- `20.타임아웃.md`

**재분석·검증 자료**
- `20.타임아웃-1.md`

**설계·심화 자료**
- `2026-08-09-pdmg-online-timeout-executor-design.md`
- `33.요청쓰레드와 Work쓰레드 분리아키텍처.md`
- `34.서비스ID별 타임아웃조정 방법.md`
- `롤백아키텍처 4초지나면롤백되나.md`

**교차 검증 자료**
- `65.트랜잭션 다이어그램.md`
- `71.Transaction Manager.md`
- `01.트랜잭션처리 변경-1.md`

**반드시 답할 질문**
- UI/HTTP Client/서버 작업 Timeout은 각각 무엇을 끊는가?
- `Future.cancel`/interrupt가 JDBC 작업을 즉시 중단시킨다고 단정할 수 있는가?
- Worker Thread Transaction과 Timeout 경계는 어떻게 결합되는가?
- 서비스 ID별 Timeout은 어떤 설정 우선순위로 결정되는가?
- Queue/Pool 고갈 시 어떤 장애가 발생하는가?

---

## 제6부. 인증과 보안

### 24장. JWT 인증 전체 구조

**필수 자료**
- `42.토큰을 생성해서 pdmg-service로 요청시 전달하는 방법.md`
- `46.토큰.md`

**다이어그램**
- `47.pdmg-jwt 아키텍처 다이어그램.md`
- `52.jwt 아키텍처 다이어그램.md`
- `67.로그인 아키텍처 다이어그램.md`

**보조 자료**
- `51.pdmg-ui 아키텍처 다이어그램.md`

**소스 검증 대상**
- pdmg-jwt의 로그인·토큰 발급·재발급 코드
- RSA key 생성/로딩/보관 방식
- Access/Refresh Token claim과 만료 설정

**반드시 답할 질문**
- 로그인부터 Access/Refresh Token 발급까지의 전체 흐름은 무엇인가?
- RS256에서 Private Key와 Public Key는 각각 누가 사용하는가?
- JWKS는 무엇을 공개하며 검증 서비스가 어떻게 사용하는가?
- 현재 구현과 목표 구조가 다르면 AS-IS/TO-BE를 명확히 분리했는가?

---

### 25장. 업무 요청의 JWT 검증

**필수 자료**
- `43.pdmg-service에서 JWT 토큰을 받으면 누가 검증하는가.md`
- `44.pdmg-ui는 JWT를 pdmg-service로 보내고 시스템 선처리에서 검증하는가.md`
- `45.pdmg-ui-로그인-JWT-service-선처리-검증-확인.md`
- `45.시스템선처리에서 JWT의 무엇을 점검하는가.md`

**교차 검증 자료**
- `13.Filter-DefaultFilter-1.md`
- `16.Service Context-1.md`
- `70.Service Context 다이어그램.md`
- `52.jwt 아키텍처 다이어그램.md`

**중요 판정 규칙**
- 현재 `pdmg-fw`의 JWT 검증 방식과 목표 RS256/JWKS 구조를 하나로 섞지 않는다.
- Token/Secret/Private Key를 로그 예제로 노출하지 않는다.

**반드시 답할 질문**
- Authorization Header는 어느 구간에서 읽히는가?
- 서명, 만료, 토큰 타입, 사용자 식별자 검증은 누가 하는가?
- 검증된 사용자정보가 ServiceContext로 어떻게 이동하는가?
- 인증과 거래통제/인가는 어떻게 구분되는가?

---

## 제7부. 예외·로그·운영

### 26장. 예외처리와 표준 오류

**기본 자료**
- `11.예외처리.md`
- `22.exceptionCode처리.md`
- `에러처리.md`

**재분석·검증 자료**
- `11.예외처리-1.md`
- `22.exceptionCode처리-1.md`

**심화 자료**
- `35.에러코드 설정방법.md`
- `36.프로그램 예외 처리 방법.md`

**다이어그램**
- `64.Exception 다이어그램.md`

**반드시 답할 질문**
- 계층별 예외는 어디에서 생성되고 어디에서 표준 오류로 변환되는가?
- `exceptionCode.yml` → MessageCache → 응답은 어떻게 연결되는가?
- Rollback을 위해 예외를 재전파해야 하는 이유는 무엇인가?
- 오류 이력을 별도 Transaction으로 남길 경우 무엇을 주의해야 하는가?

---

### 27장. 이미지로그와 GUID 추적

**필수 자료**
- `68.트랜잭션 이미지로그 다이어그램.md`
- `69.GUID 아키텍처 다이어그램.md`

**교차 검증 자료**
- `16.Service Context-1.md`
- `26.시스템후처리-1.md`
- `55.시스템선후처리다이어그램.md`

**반드시 답할 질문**
- 요청 ImageLog INSERT와 응답 UPDATE는 어느 구간에서 발생하는가?
- 업무 Transaction과 로그 Transaction이 동일한지 분리되는지 어떻게 판단하는가?
- GUID 하나로 요청→업무→DB→응답을 어떻게 추적하는가?
- 민감정보는 어떤 시점에 마스킹해야 하는가?

---

### 28장. 성능 감시와 운영 진단

**필수 자료**
- `성능감시.md`

**다이어그램·추적 보조**
- `68.트랜잭션 이미지로그 다이어그램.md`
- `69.GUID 아키텍처 다이어그램.md`

**교차 검증 자료**
- `20.타임아웃-1.md`
- `33.요청쓰레드와 Work쓰레드 분리아키텍처.md`
- `60.DAO 다이어그램.md`

**반드시 답할 질문**
- HTTP 전체시간, TCF 구간, 업무 TX, Service, SQL 시간을 어떻게 구분해 관찰하는가?
- Service ID와 GUID를 이용해 느린 거래를 어떻게 찾는가?
- Timeout 로그와 실제 Worker 작업 상태를 어떻게 구분하는가?
- 운영 로그에 남기면 안 되는 민감정보는 무엇인가?

---

## 제8부. 환경설정과 배포

### 29장. Spring 환경구성

**기본 자료**
- `28.Spring 환경구성정보.md`

**재분석·검증 자료**
- `28.Spring 환경구성정보-1.md`

**연결 지도**
- `29.환경구성정보와 소스 연관성-이미지순서.md`
- `31.Spring-환경파일-중심-소스연관성.md`

**다이어그램**
- `72.Spring 환경 구성 다이어그램.md`

**반드시 답할 질문**
- Profile/PropertySource/Import는 실제 설정을 어떤 순서로 만든는가?
- `@Value`, `@ConfigurationProperties`, `@ConditionalOnProperty`는 어디에서 사용되는가?
- DataSource/TCF/Filter/Interceptor/Timeout/JWT 설정은 어떤 Bean에 영향을 주는가?
- Secret을 소스나 기본 yml에 고정하면 왜 안 되는가?

---

### 30장. 환경설정에서 소스까지 추적하기

> 전체 목차에는 별도 “관련 문서” 목록이 없으므로 아래는 **원천자료의 실제 주제를 기반으로 보강한 매핑**이다.

**핵심 연결 자료**
- `29.환경구성정보와 소스 연관성-이미지순서.md`
- `31.Spring-환경파일-중심-소스연관성.md`
- `28.Spring 환경구성정보.md`
- `28.Spring 환경구성정보-1.md`
- `72.Spring 환경 구성 다이어그램.md`

**기능별 추적 자료**
- TCF: `37.TCF 프레임워크 적용하지 않는 방법.md`
- Filter: `13.Filter-DefaultFilter-1.md`
- Interceptor: `15.Interceptor-ServicePrevention.preHandle-1.md`
- Timeout: `20.타임아웃-1.md`, `34.서비스ID별 타임아웃조정 방법.md`
- TransactionManager: `20.Tranaactional-rdwTransactionManager-Begin-1.md`
- 오류코드: `22.exceptionCode처리-1.md`
- JWT: `42.토큰을 생성해서 pdmg-service로 요청시 전달하는 방법.md`, `43.pdmg-service에서 JWT 토큰을 받으면 누가 검증하는가.md`

**반드시 답할 질문**
- 설정 키 하나를 보고 어떤 Java Bean과 실행 흐름에 영향을 주는지 추적할 수 있는가?
- 설정 변경 → Bean 생성/비생성 → 요청 Flow 변화까지 연결해서 설명했는가?
- 설정명만 나열하지 않고 변경 영향과 장애 가능성을 설명했는가?

---

## 제9부. 실전 개발 가이드

### 31장. 신규 프로그램 개발

> 전체 목차에는 별도 “관련 문서” 목록이 없으므로 아래는 **신규 프로그램 개발 절차에 필요한 기존 원천자료를 연결한 보강 매핑**이다.

**실전 절차 핵심 자료**
- `CRUD서비스프롬프트가이드.md`
- `32.범용CRUD프롬프트.md`
- `32.범용CRUD프롬프트 사용법.md`
- `범용crudservice프롬프팅.md`

**식별·구조 자료**
- `00.NSIGHT 애플리케이션 코드 분류표.md`
- `09.서비스ID-1.md`
- `MG-NAMING_CONVENTION.md`
- `04.패키지구조-1.md`

**계층 구현 자료**
- Handler: `18.Business-Handler-1.md`
- Facade: `19.Business-Facade-1.md`
- Service: `22.Business-Service-1.md`
- DTO: `27.전문 DTO-1.md`
- DAO: `23.DAO-1.md`
- Mapper: `24.DAO-Mapper-1.md`, `24.DAO-Namespace-1.md`
- 오류: `36.프로그램 예외 처리 방법.md`, `35.에러코드 설정방법.md`

**반드시 답할 질문**
- 요구사항에서 업무축, 프로그램 번호, Service ID를 어떤 순서로 결정하는가?
- DTO→Handler/Controller→Facade→Service/Rule→DAO/Mapper가 어떻게 만들어지는가?
- 오류코드, UI 요청, 샘플 전문, 테스트까지 개발 완료 범위에 포함했는가?
- 기존 표준 소스를 복제만 하지 않고 현재 네이밍·패키지·Transaction 규칙을 검증했는가?

---

### 32장. CRUD 구현 패턴

**필수 자료**
- `CRUD서비스프롬프트가이드.md`
- `범용CRUD프롬프트.md`
- `범용crudservice프롬프팅.md`
- `32.범용CRUD프롬프트.md`
- `32.범용CRUD프롬프트 사용법.md`

**계층별 교차 검증 자료**
- `22.Business-Service-1.md`
- `23.DAO-1.md`
- `24.DAO-Mapper-1.md`
- `27.전문 DTO-1.md`
- `11.예외처리-1.md`
- `01.트랜잭션처리 변경-1.md`

**대용량 조회 보조**
- `08.대용량 페이징 처리방식-1.md`
- `76.페이징 아키텍처 다이어그램.md`

**반드시 답할 질문**
- 조회/상세/등록/수정/삭제가 같은 계층 규칙을 어떻게 공유하는가?
- 쓰기 거래의 처리건수 검증과 Rollback은 어떻게 설계하는가?
- 논리삭제/물리삭제, 동시성, 입력검증, 감사 요구를 어디에서 처리하는가?
- CRUD 프롬프트 자체를 책 본문으로 복사하지 않고 실전 개발 절차로 재구성했는가?

---

### 33장. 테스트와 품질 검증

> 현재 137개 문서에는 **테스트만을 독립적으로 다룬 전용 원천문서가 충분하지 않다.** 따라서 이 장은 실제 저장소의 Test Source, Build 설정, 실행 결과를 최우선 근거로 삼아야 한다. 아래 문서는 테스트 시나리오와 검증 항목을 도출하기 위한 보조자료다.

**실전 검증 기준 자료**
- `CRUD서비스프롬프트가이드.md`
- `32.범용CRUD프롬프트.md`
- `32.범용CRUD프롬프트 사용법.md`

**기능별 테스트 시나리오 근거**
- TCF ON/OFF: `37.TCF 프레임워크 적용하지 않는 방법.md`, `66.TCF OFF 다이어그램.md`
- Transaction/Rollback: `01.트랜잭션처리 변경-1.md`, `65.트랜잭션 다이어그램.md`
- Timeout: `20.타임아웃-1.md`, `2026-08-09-pdmg-online-timeout-executor-design.md`
- Exception: `11.예외처리-1.md`, `64.Exception 다이어그램.md`
- JWT: `43.pdmg-service에서 JWT 토큰을 받으면 누가 검증하는가.md`, `52.jwt 아키텍처 다이어그램.md`
- HTTP/DTO: `12.http요청-1.md`, `27.전문 DTO-1.md`
- DAO/Mapper: `23.DAO-1.md`, `24.DAO-Mapper-1.md`

**실제 Source 우선 확인**
- `src/test` 계열 테스트 코드
- Gradle test task 및 build 설정
- WAR/build 산출물 검증
- Profile별 테스트 설정
- 통합 테스트에서 사용하는 DB/Mock/외부연계 구성

**반드시 답할 질문**
- Handler/Facade/Service/DAO/Controller 각각 무엇을 단위·통합 테스트해야 하는가?
- TCF ON/OFF 두 경로를 모두 검증했는가?
- Commit/Rollback/Checked Exception/예외 삼킴을 실제로 검증했는가?
- Timeout 발생 후 Worker와 DB 작업 상태까지 확인했는가?
- JWT 성공/위조/만료/잘못된 타입 시나리오가 있는가?
- 문서와 실제 구현의 정합성을 마지막 품질 Gate에서 확인하는가?

---

# 7. 모듈별 역방향 Source Map

장 번호에서 자료를 찾는 방식뿐 아니라, 특정 모듈을 수정했을 때 영향을 받는 장을 찾기 위해 다음 역방향 맵을 사용한다.

| 모듈/영역 | 우선 영향 장 |
|---|---|
| `pdmg-ui` | 1, 2, 8, 24, 25, 29, 30, 31, 33 |
| `pdmg-fw` | 1, 2, 3, 8~13, 21~30, 33 |
| `pdmg-service` | 1~8, 14~23, 26~33 |
| `pdmg-jwt` | 1, 24, 25, 29, 30, 33 |
| Service ID / Naming | 4, 5, 6, 7, 10, 12, 14, 23, 28, 31, 32 |
| Transaction | 15, 16, 17, 19, 21, 22, 23, 26, 27, 32, 33 |
| Timeout / Thread | 2, 11, 13, 22, 23, 28, 29, 30, 33 |
| JWT / Security | 8, 9, 11, 24, 25, 26, 29, 30, 33 |
| Exception | 12, 13, 16, 17, 19, 23, 25, 26, 31~33 |
| ImageLog / GUID | 2, 10, 11, 12, 22, 26~28, 33 |
| Environment | 1, 6, 9, 10, 12, 13, 21, 23~26, 29, 30, 33 |

---

# 8. 핵심 교차주제 Source Map

## 8.1 Transaction

최소 읽기 세트:

```text
20.Tranaactional-rdwTransactionManager-Begin.md
        ↓
20.Tranaactional-rdwTransactionManager-Begin-1.md
        ↓
01.트랜잭션처리 변경.md
        ↓
01.트랜잭션처리 변경-1.md
        ↓
39.TCF on 이면 트랜잭션 시작은 어디인가.md
        ↓
38.TCF off 이면 트랜잭션 시작은 어디인가.md
        ↓
33.요청쓰레드와 Work쓰레드 분리아키텍처.md
        ↓
65.트랜잭션 다이어그램.md
        ↓
71.Transaction Manager.md
```

Transaction을 다루는 장에서는 **Annotation 이름보다 실제 BEGIN 경계와 Thread를 먼저 확인**한다.

---

## 8.2 Timeout / Worker Thread

```text
20.타임아웃.md
   ↓
20.타임아웃-1.md
   ↓
2026-08-09-pdmg-online-timeout-executor-design.md
   ↓
33.요청쓰레드와 Work쓰레드 분리아키텍처.md
   ↓
34.서비스ID별 타임아웃조정 방법.md
   ↓
롤백아키텍처 4초지나면롤백되나.md
   ↓
01.트랜잭션처리 변경-1.md
```

Timeout 문서만 보고 `cancel = DB 즉시 중단`으로 단정하지 않는다.

---

## 8.3 TCF ON/OFF

```text
16.TCF-OnlineTransactionController-1.md
   ↓
17.TCF-TcfFace-Dispatcher-1.md
   ↓
tcf.md
   ↓
37.TCF 프레임워크 적용하지 않는 방법.md
   ↓
39.TCF on 이면 트랜잭션 시작은 어디인가.md
   ↓
38.TCF off 이면 트랜잭션 시작은 어디인가.md
   ↓
56.TCF 아키텍처 다이어그램.md
   ↓
66.TCF OFF 다이어그램.md
```

---

## 8.4 JWT

```text
42.토큰을 생성해서 pdmg-service로 요청시 전달하는 방법.md
   ↓
46.토큰.md
   ↓
47.pdmg-jwt 아키텍처 다이어그램.md
   ↓
52.jwt 아키텍처 다이어그램.md
   ↓
43.pdmg-service에서 JWT 토큰을 받으면 누가 검증하는가.md
   ↓
44.pdmg-ui는 JWT를 pdmg-service로 보내고 시스템 선처리에서 검증하는가.md
   ↓
45.pdmg-ui-로그인-JWT-service-선처리-검증-확인.md
   ↓
45.시스템선처리에서 JWT의 무엇을 점검하는가.md
```

JWT는 반드시 **발급**과 **검증**을 분리해서 설명하고, AS-IS HMAC과 TO-BE RS256/JWKS를 혼합하지 않는다.

---

## 8.5 Exception / Error Code

```text
11.예외처리.md
   ↓
11.예외처리-1.md
   ↓
22.exceptionCode처리.md
   ↓
22.exceptionCode처리-1.md
   ↓
35.에러코드 설정방법.md
   ↓
36.프로그램 예외 처리 방법.md
   ↓
에러처리.md
   ↓
64.Exception 다이어그램.md
```

---

## 8.6 신규 CRUD

```text
00.NSIGHT 애플리케이션 코드 분류표.md
   ↓
MG-NAMING_CONVENTION.md
   ↓
09.서비스ID-1.md
   ↓
04.패키지구조-1.md
   ↓
27.전문 DTO-1.md
   ↓
18.Business-Handler-1.md
   ↓
19.Business-Facade-1.md
   ↓
22.Business-Service-1.md
   ↓
23.DAO-1.md
   ↓
24.DAO-Mapper-1.md
   ↓
11.예외처리-1.md
   ↓
CRUD서비스프롬프트가이드.md
   ↓
32.범용CRUD프롬프트.md
```

---

# 9. 전역 편집자료

다음 자료는 특정 한 장에만 귀속시키지 않는다.

### 책 전체 목차
- `zz.PDMG 애플리케이션 아키텍처와 개발 가이드 목차.md.md`

### 과거 네이밍 원본/편집 이력
- `MG-NAMING_CONVENTION(원본).md`

### 모듈 전체 다이어그램
- `48.pdmg-fw 아키텍처 다이어그램.md`
- `49.pdmg-service 아키텍처 다이어그램.md`
- `51.pdmg-ui 아키텍처 다이어그램.md`
- `47.pdmg-jwt 아키텍처 다이어그램.md`

이들은 여러 장에서 구조 이해를 보조하되 현재 구현 사실을 결정하는 최종 근거는 아니다.

---

# 10. Codex 장 집필 시 Source Map 적용 규칙

사용자가 예를 들어 다음과 같이 요청한다.

```text
23장을 집필해.
```

Codex는 이를 다음 작업으로 해석한다.

```text
1. AGENTS.md 읽기
2. BOOK-WRITING-RULE.md 읽기
3. 전체 목차에서 23장의 목적과 소목차 확인
4. SOURCE-MAP.md의 23장 항목 확인
5. 21장/22장 완성본 확인
6. 23장 필수·재분석·설계 자료 읽기
7. 실제 Timeout/Executor/Transaction 설정과 Source 확인
8. 24장 목차를 확인하여 JWT 내용 선침범 방지
9. AS-IS / TO-BE / 일반 기술 설명 분리
10. Why → Architecture → Thread → Transaction → Timeout → Failure → Operation 순으로 재구성
11. 장 본문 작성
12. 요약식으로 끝난 절을 찾아 확장
13. Source Map의 “반드시 답할 질문”을 모두 충족했는지 검증
14. BOOK-WRITING-RULE 품질 Gate 수행
```

---

# 11. Source Map 변경관리

다음 상황에서는 이 파일을 갱신한다.

- 실제 소스 구조가 변경되었을 때
- 신규 원천 문서가 추가되었을 때
- 기존 기본 문서가 폐기되었을 때
- `-1.md` 재분석 결과가 새로 작성되었을 때
- AS-IS/TO-BE 판정이 바뀌었을 때
- 책 목차가 변경되었을 때
- 특정 장이 다른 장으로 이동되거나 분리·통합되었을 때

변경 시 다음을 확인한다.

```text
원천자료 변경
   ↓
SOURCE-MAP 영향 장 확인
   ↓
해당 장의 AS-IS 설명 확인
   ↓
다이어그램 정합성 확인
   ↓
교차주제 영향 확인
   ↓
필요 시 장 본문 수정
```

---

# 12. 최종 원칙

`SOURCE-MAP.md`의 목적은 Codex에게 “어떤 문서를 요약할지” 알려주는 것이 아니다.

목적은 다음이다.

> **현재 장을 제대로 설명하기 위해 어떤 근거를 어떤 순서로 검증해야 하는지 알려주는 것.**

항상 다음을 지킨다.

**기본 문서는 개념을 얻는 데 사용한다.**  
**재분석 문서는 정정과 구현 검증에 사용한다.**  
**다이어그램은 이해를 돕는 데 사용한다.**  
**실제 Source와 Configuration은 AS-IS를 결정하는 데 사용한다.**  
**Prompt 자료는 실전 개발 절차를 만드는 데 사용한다.**

그리고 최종 책은 자료 목록이나 문서 요약집이 아니라 다음 흐름으로 재구성한다.

```text
개념
 → Why
 → PDMG에서의 위치
 → AS-IS 실제 구현
 → 실행 흐름
 → Source / Configuration
 → 정상 시나리오
 → 실패 시나리오
 → Thread / Transaction 영향
 → 주의사항
 → 운영 관점
 → TO-BE / Architecture Decision
 → 개발자 체크리스트
```

