# 제안 도서명

**PDMG 애플리케이션 아키텍처와 개발 가이드**  
부제: _pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt 기반 온라인 거래 시스템의 설계와 구현_

`pdmg-service/docs`에는 동일 주제의 원본, 재분석본(`-1.md`), 요약본, 다이어그램 문서가 함께 있습니다. 책에서는 파일 순서대로 나열하기보다 중복을 통합해 아래처럼 구성하는 것이 좋습니다.

# 전체 목차

## 머리말

- 이 책의 목적
- 대상 독자
  - PDMG 신규 개발자
  - 업무 서비스 개발자
  - 프레임워크 개발자
  - 애플리케이션 아키텍트
  - 운영·장애 대응 담당자
- AS-IS 기준과 TO-BE 제안의 구분
- 예제 프로그램과 표기법
- 저장소 및 모듈 구성
- 이 책을 읽는 순서

---

# 제1부. PDMG 아키텍처 이해

## 1장. PDMG 시스템 개요

### 1.1 PDMG의 목적

### 1.2 전체 시스템 구성

### 1.3 주요 모듈

- `pdmg-ui`
- `pdmg-service`
- `pdmg-fw`
- `pdmg-jwt`

### 1.4 모듈 간 의존 관계

### 1.5 기술 스택

### 1.6 로컬 실행 포트와 호출 관계

### 1.7 PDMG가 처리하는 온라인 거래

### 1.8 두 가지 실행 경로: TCF ON과 OFF

### 1.9 핵심 용어

- 화면번호
- 프로그램 ID
- Service ID
- GUID
- ServiceContext
- TransactionContext
- STF·ETF
- 업무 선후처리
- 이미지로그

관련 문서:

- `46.전체 아키텍처 구조.md`
- `50.기술스택 다이어그램.md`
- `75.PDMG 컴포넌트 다이어그램.md`

---

## 2장. 전체 온라인 거래 빅픽처

### 2.1 한눈에 보는 전체 요청 흐름

### 2.2 UI에서 업무 DB까지의 호출

### 2.3 HTTP 요청 구간

### 2.4 시스템 선처리 구간

### 2.5 TCF 거래 실행 구간

### 2.6 업무 처리 구간

### 2.7 시스템 후처리 구간

### 2.8 정상 거래 흐름

### 2.9 실패 거래 흐름

### 2.10 트랜잭션과 온라인 거래의 차이

### 2.11 요청 Thread와 Worker Thread

### 2.12 전체 흐름에서 반드시 기억할 경계

관련 문서:

- `00.Big Picture Image.md`
- `00.BigPicture Tx 처리-1.md`
- `00.BigPicture Tx 흐름.md`
- `05.전체 빅픽처 흐름.md`
- `05.전체 빅픽처 흐름-1.md`
- `54.http호출 다이어그램.md`

---

## 3장. 레이어드 아키텍처와 컴포넌트

### 3.1 PDMG 레이어 구성

### 3.2 Framework 레이어

### 3.3 Entry 레이어

### 3.4 Application 레이어

### 3.5 Domain Rule 레이어

### 3.6 Contract·DTO 레이어

### 3.7 Persistence 레이어

### 3.8 Client와 외부 연계

### 3.9 Config와 Support

### 3.10 컴포넌트별 책임

### 3.11 허용되는 의존 방향

### 3.12 금지되는 역방향 의존

### 3.13 PDMG와 클린 아키텍처 비교

### 3.14 현재 구조와 목표 구조

관련 문서:

- `02.어플리케이션 컴포넌트 구조.md`
- `02.어플리케이션 컴포넌트 구조-1.md`
- `03.어플리케이션 레이어드 아키텍처.md`
- `03.어플리케이션 레이어드 아키텍처-1.md`
- `74.PDMG 클린 아키텍처 다이어그램.md`

---

# 제2부. 식별체계와 개발 구조

## 4장. 애플리케이션 분류와 Service ID

### 4.1 애플리케이션 코드 분류체계

### 4.2 대구분 코드

### 4.3 업무구분 코드

### 4.4 세부업무 코드

### 4.5 프로그램 식별번호

### 4.6 거래구분 코드

- `S`: 조회
- `C`: 등록
- `U`: 수정
- `D`: 삭제
- `A`: 업무 실행
- `R`: 보고서

### 4.7 Service ID 구성 공식

### 4.8 프로그램 ID와 Service ID의 차이

### 4.9 화면번호와 Service ID 관계

### 4.10 Service ID와 URL

### 4.11 Service ID와 Handler Registry

### 4.12 Service ID와 거래 통제

### 4.13 Service ID와 타임아웃

### 4.14 신규 Service ID 생성 절차

### 4.15 Service ID 오류 처리

관련 문서:

- `00.NSIGHT 애플리케이션 코드 분류표.md`
- `09.서비스ID.md`
- `09.서비스ID-1.md`
- `61.Service ID 아키텍처 다이어그램.md`
- `62.화면번호-serviceid-controller-service 관계 다이어그램.md`

---

## 5장. 네이밍 규칙

### 5.1 네이밍 규칙의 목적

### 5.2 프로그램 접두어

### 5.3 Service ID 명명법

### 5.4 Java 패키지 명명법

### 5.5 클래스·파일 명명법

### 5.6 Handler 명명법

### 5.7 Controller 명명법

### 5.8 Facade 명명법

### 5.9 Service·Rule 명명법

### 5.10 DTO 명명법

### 5.11 DAO 명명법

### 5.12 Mapper 파일명

### 5.13 SQL Statement ID

### 5.14 Java 필드와 DB 컬럼

### 5.15 오류 코드 명명법

### 5.16 금지하는 이름

### 5.17 신규 프로그램 네이밍 체크리스트

관련 문서:

- `MG-NAMING_CONVENTION.md`
- `00.MG-NAMING_CONVENTION.md`
- `06.네이밍 형식.md`
- `06.네이밍 형식-1.md`
- `53.네이밍 아키텍처 다이어그램.md`
- `네이밍원칙.md`

`MG-NAMING_CONVENTION(원본).md`는 본문이 아니라 편집 이력 자료로 분류하는 것이 좋습니다.

---

## 6장. 패키지와 프로젝트 구조

### 6.1 패키지 설계 원칙

### 6.2 업무 패키지 기본 형식

### 6.3 `entry` 패키지

### 6.4 `application` 패키지

### 6.5 `dto` 패키지

### 6.6 `persistence` 패키지

### 6.7 `client` 패키지

### 6.8 `config` 패키지

### 6.9 `support` 패키지

### 6.10 Mapper 리소스 경로

### 6.11 Component Scan

### 6.12 Mapper Scan

### 6.13 Aspect Pointcut와 패키지

### 6.14 신규 업무축 추가 방법

### 6.15 테스트 패키지

### 6.16 금지 패키지 패턴

관련 문서:

- `04.패키지구조.md`
- `04.패키지구조-1.md`
- `73.pdmg 패키지 다이어그램.md`

---

## 7장. 도메인 정의와 호출 방식

### 7.1 PDMG에서 도메인의 의미

### 7.2 시스템 도메인

### 7.3 업무·세부업무 도메인

### 7.4 프로그램 도메인

### 7.5 거래 도메인

### 7.6 도메인 간 의존 관계

### 7.7 TCF ON 호출

### 7.8 TCF OFF 호출

### 7.9 UI 직접 호출

### 7.10 UI Relay 호출

### 7.11 외부 시스템 호출

### 7.12 동기·비동기 호출

### 7.13 신규 도메인 추가 절차

관련 문서:

- `07.도메인 정의 및 호출방식.md`
- `07.도메인 정의 및 호출방식-1.md`

---

# 제3부. 온라인 요청과 TCF 프레임워크

## 8장. HTTP 요청과 표준 전문

### 8.1 HTTP 호출 구조

### 8.2 표준 요청 전문

### 8.3 `hdr_nhnis` 구조

### 8.4 `sys_comm` 공통 Header

### 8.5 업무 DTO Body

### 8.6 GUID와 Service ID

### 8.7 TCF ON `/online` 호출

### 8.8 TCF OFF 직접 URL 호출

### 8.9 JSON 역직렬화

### 8.10 표준 응답 전문

### 8.11 HTTP 상태 코드

### 8.12 CORS 처리

### 8.13 UI Relay와 직접 호출 비교

관련 문서:

- `10.전문.md`
- `10.전문-1.md`
- `11.Http CORS적용.md`
- `11.Http CORS적용-1.md`
- `12.http요청.md`
- `12.http요청-1.md`
- `27.전문 DTO.md`
- `27.전문 DTO-1.md`
- `54.http호출 다이어그램.md`

---

## 9장. Filter와 Spring MVC

### 9.1 Servlet Filter의 위치

### 9.2 DefaultFilter 생성 조건

### 9.3 Request Body 캐싱

### 9.4 JWT 토큰 추출

### 9.5 GUID 생성·보완

### 9.6 ServiceContext 생성

### 9.7 MDC와 로그 Context

### 9.8 Spring MVC 요청 변환

### 9.9 ArgumentResolver

### 9.10 응답 변환

### 9.11 Filter 종료 정리

### 9.12 Filter와 Interceptor를 합치면 안 되는 이유

### 9.13 Filter 비활성화 영향

관련 문서:

- `13.Filter-DefaultFilter.md`
- `13.Filter-DefaultFilter-1.md`
- `14.Spring MVC.md`
- `14.Spring MVC-1.md`
- `30.왜 Filter와 Interceptor을 합치면 안되는가.md`

---

## 10장. Interceptor와 시스템 선처리

### 10.1 Interceptor 실행 시점

### 10.2 `preHandle` 처리

### 10.3 요청 전문 확인

### 10.4 Service ID 결정

### 10.5 시스템 공통 검증

### 10.6 거래 통제

- 서비스별
- 사용자별
- IP별
- 영업점별
- 채널별
- 시간대별

### 10.7 이미지로그 요청 등록

### 10.8 Controller 진입 허용·차단

### 10.9 `afterCompletion`

### 10.10 시스템 선후처리와 업무 트랜잭션 관계

관련 문서:

- `14.시스템 선후처리.md`
- `14.시스템 선후처리-1.md`
- `15.Interceptor-ServicePrevention.preHandle.md`
- `15.Interceptor-ServicePrevention.preHandle-1.md`
- `40.시스템 선처리에서 어떤 필터를 처리하는가.md`
- `55.시스템선후처리다이어그램.md`

---

## 11장. ServiceContext와 GUID

### 11.1 ServiceContext의 목적

### 11.2 ServiceContext 데이터 구조

### 11.3 생성 시점

### 11.4 ServiceContextHolder

### 11.5 ThreadLocal 저장

### 11.6 계층별 Context 활용

### 11.7 JWT 사용자정보 연계

### 11.8 GUID 생성 규칙

### 11.9 TransactionContext와의 관계

### 11.10 이미지로그와의 관계

### 11.11 외부 시스템으로 GUID 전파

### 11.12 비동기 Thread에서의 Context

### 11.13 Context 정리

### 11.14 Context 정보 보안

관련 문서:

- `16.Service Context.md`
- `16.Service Context-1.md`
- `41.시스템 컨텍스트 정보는 어디서 생성되는가.md`
- `69.GUID 아키텍처 다이어그램.md`
- `70.Service Context 다이어그램.md`

---

## 12장. TCF 온라인 거래 프레임워크

### 12.1 TCF의 책임

### 12.2 OnlineTransactionController

### 12.3 TransactionContext

### 12.4 TcfFacade

### 12.5 STF 시스템 선처리

### 12.6 TransactionDispatcher

### 12.7 TransactionHandler Registry

### 12.8 Handler 선택과 실행

### 12.9 ETF 시스템 후처리

### 12.10 정상 거래 흐름

### 12.11 실패 거래 흐름

### 12.12 TCF ON/OFF 조건부 Bean

### 12.13 TCF가 담당하지 않는 영역

관련 문서:

- `16.TCF-OnlineTransactionController.md`
- `16.TCF-OnlineTransactionController-1.md`
- `17.TCF-TcfFace-Dispatcher.md`
- `17.TCF-TcfFace-Dispatcher-1.md`
- `26.시스템후처리.md`
- `26.시스템후처리-1.md`
- `56.TCF 아키텍처 다이어그램.md`
- `tcf.md`

---

## 13장. TCF OFF 호환 구조

### 13.1 TCF OFF의 목적

### 13.2 비활성화되는 컴포넌트

### 13.3 직접 Controller 호출

### 13.4 Controller에서 Facade 호출

### 13.5 Service 직접 호출의 문제

### 13.6 TCF OFF 트랜잭션

### 13.7 TCF OFF 업무 선후처리

### 13.8 시스템 선후처리 변화

### 13.9 타임아웃 처리

### 13.10 예외처리 영향

### 13.11 TCF ON/OFF 공통 구조 유지 방법

관련 문서:

- `37.TCF 프레임워크 적용하지 않는 방법.md`
- `38.TCF off 이면 트랜잭션 시작은 어디인가.md`
- `39.TCF on 이면 트랜잭션 시작은 어디인가.md`
- `66.TCF OFF 다이어그램.md`

---

# 제4부. 업무 애플리케이션 구현

## 14장. Handler와 Controller

### 14.1 Inbound Adapter의 역할

### 14.2 TransactionHandler

### 14.3 `serviceIds()` 등록

### 14.4 `handle()` 거래 분기

### 14.5 Handler Registry

### 14.6 업무 Controller

### 14.7 TCF ON/OFF 진입점 비교

### 14.8 Controller 입력 검증

### 14.9 Controller와 Facade 관계

### 14.10 Controller에서 Service 직접 호출 문제

### 14.11 Controller에 트랜잭션을 선언할 때의 영향

### 14.12 Handler·Controller에서 금지하는 처리

관련 문서:

- `18.Business-Handler.md`
- `18.Business-Handler-1.md`
- `57.Controller 다이어그램.md`

---

## 15장. Business Facade

### 15.1 Facade의 목적

### 15.2 Handler와 Service 사이의 경계

### 15.3 Object·Map에서 DTO 변환

### 15.4 유스케이스 조정

### 15.5 여러 Service 호출

### 15.6 `@Transactional` 선언

### 15.7 TCF ON Facade

### 15.8 TCF OFF Facade

### 15.9 Facade 자기 호출 문제

### 15.10 Facade에서 처리하지 않아야 할 것

관련 문서:

- `19.Business-Facade.md`
- `19.Business-Facade-1.md`

---

## 16장. 업무 선후처리

### 16.1 BizPrePostAspect의 목적

### 16.2 현재 Service 포인트컷

### 16.3 Facade 트랜잭션과 실행 순서

### 16.4 업무 선처리

### 16.5 Business Service 실행

### 16.6 정상 업무 후처리

### 16.7 오류 업무 후처리

### 16.8 후처리 UPDATE의 트랜잭션 참여

### 16.9 오류 이력과 `REQUIRES_NEW`

### 16.10 예외 재전파

### 16.11 자기 호출과 AOP

### 16.12 비동기 실행 시 제약

관련 문서:

- `21.업무선처리-BizPrePostAspect.md`
- `21.업무선처리-BizPrePostAspect-1.md`
- `25.업무후처리-BizPrePostAspect.md`
- `25.업무후처리-BizPrePostAspect-1.md`
- `58.업무선후처리 다이어그램.md`

---

## 17장. Business Service와 Rule

### 17.1 Service의 책임

### 17.2 입력값 확인

### 17.3 ServiceContext 활용

### 17.4 업무 처리 순서

### 17.5 Rule 호출

### 17.6 조회 Service

### 17.7 등록 Service

### 17.8 수정 Service

### 17.9 삭제 Service

### 17.10 여러 DAO 호출

### 17.11 외부 Client 호출

### 17.12 결과 DTO 조립

### 17.13 Service 예외 처리

### 17.14 Service가 담당하지 않는 영역

관련 문서:

- `22.Business-Service.md`
- `22.Business-Service-1.md`
- `59.Service 다이어그램.md`

---

## 18장. DTO와 데이터 계약

### 18.1 DTO의 역할

### 18.2 `DTOin`

### 18.3 `DTOout`

### 18.4 `DTOSub`

### 18.5 TCF ON DTO 변환

### 18.6 TCF OFF DTO 변환

### 18.7 Bean Validation

### 18.8 표준 Header와 업무 DTO 분리

### 18.9 DTO와 ServiceContext

### 18.10 DTO와 DAO

### 18.11 DTO와 도메인 모델

### 18.12 필드 네이밍

### 18.13 응답 DTO 조립

관련 문서:

- `27.전문 DTO.md`
- `27.전문 DTO-1.md`
- `63.DTO 다이어그램.md`

---

## 19장. DAO와 MyBatis Mapper

### 19.1 Persistence 계층의 책임

### 19.2 DAO Interface

### 19.3 `@RDWMapper`

### 19.4 Mapper Scan

### 19.5 Mapper XML 경로

### 19.6 DAO FQCN과 Namespace

### 19.7 DAO 메서드와 Statement ID

### 19.8 SELECT

### 19.9 INSERT

### 19.10 UPDATE

### 19.11 DELETE

### 19.12 MyBatis 파라미터 바인딩

### 19.13 `${}` 사용 시 위험

### 19.14 Result Mapping

### 19.15 DB 예외 전파

### 19.16 DAO에서 금지하는 처리

관련 문서:

- `23.DAO.md`
- `23.DAO-1.md`
- `24.DAO-Mapper.md`
- `24.DAO-Mapper-1.md`
- `24.DAO-Namespace.md`
- `24.DAO-Namespace-1.md`
- `60.DAO 다이어그램.md`

---

## 20장. 대용량 조회와 페이징

### 20.1 페이징 계약

### 20.2 입력 DTO

### 20.3 출력 DTO

### 20.4 전체 건수 조회

### 20.5 목록 조회

### 20.6 Oracle ROWNUM 페이징

### 20.7 Offset 계산

### 20.8 pageSize 제한

### 20.9 깊은 페이지 문제

### 20.10 키셋·Cursor 페이징

### 20.11 정렬 기준과 인덱스

### 20.12 조회 트랜잭션

### 20.13 성능 검증

관련 문서:

- `08.대용량 페이징 처리방식.md`
- `08.대용량 페이징 처리방식-1.md`

---

# 제5부. 트랜잭션과 타임아웃

## 21장. Spring 트랜잭션 기초

### 21.1 PDMG의 트랜잭션 원칙

### 21.2 RdwDataSourceConfig

### 21.3 RDW DataSource

### 21.4 SqlSessionFactory

### 21.5 SqlSessionTemplate

### 21.6 `rdwTransactionManager`

### 21.7 Spring TransactionInterceptor

### 21.8 현재 Thread와 Connection

### 21.9 `@Transactional` 실행 과정

### 21.10 Commit

### 21.11 Rollback

### 21.12 Checked Exception 롤백

### 21.13 예외를 삼킬 때 발생하는 문제

관련 문서:

- `20.Tranaactional-rdwTransactionManager-Begin.md`
- `20.Tranaactional-rdwTransactionManager-Begin-1.md`
- `71.Transaction Manager.md`
- `트랜잭션처리.md`

---

## 22장. PDMG 트랜잭션 경계

### 22.1 트랜잭션은 어디에서 시작되는가

### 22.2 Facade 트랜잭션

### 22.3 업무 선처리의 트랜잭션 참여

### 22.4 Service와 DAO의 참여

### 22.5 업무 후처리의 참여

### 22.6 시스템 선처리와 트랜잭션

### 22.7 시스템 후처리와 트랜잭션

### 22.8 이미지로그 트랜잭션

### 22.9 여러 DAO를 묶는 방법

### 22.10 여러 DataSource 처리

### 22.11 전파 속성

- `REQUIRED`
- `REQUIRES_NEW`
- `MANDATORY`

### 22.12 자기 호출 문제

### 22.13 비동기 Thread와 트랜잭션

### 22.14 TCF ON/OFF 비교

관련 문서:

- `01.트랜잭션처리 변경.md`
- `01.트랜잭션처리 변경-1.md`
- `65.트랜잭션 다이어그램.md`
- `pdmg-service 트랜잭션흐름.md`
- `트랜잭션처리.md`

---

## 23장. 타임아웃과 작업 취소

### 23.1 타임아웃의 목적

### 23.2 UI 타임아웃

### 23.3 HTTP Client 타임아웃

### 23.4 OnlineTimeoutExecutor

### 23.5 공통 타임아웃 설정

### 23.6 서비스별 타임아웃

### 23.7 요청 Thread와 Worker Thread

### 23.8 작업 취소 요청

### 23.9 JDBC 작업 중단의 한계

### 23.10 트랜잭션 롤백 조건

### 23.11 ETF 사후 타임아웃의 한계

### 23.12 TimeoutExecutor와 트랜잭션 경계

### 23.13 Pool과 Queue 관리

### 23.14 권장 타임아웃 조합

관련 문서:

- `20.타임아웃.md`
- `20.타임아웃-1.md`
- `2026-08-09-pdmg-online-timeout-executor-design.md`
- `33.요청쓰레드와 Work쓰레드 분리아키텍처.md`
- `34.서비스ID별 타임아웃조정 방법.md`
- `롤백아키텍처 4초지나면롤백되나.md`

---

# 제6부. 인증과 보안

## 24장. JWT 인증 전체 구조

### 24.1 JWT 인증의 목적

### 24.2 `pdmg-ui` 로그인 화면

### 24.3 `pdmg-jwt` 인증 요청

### 24.4 사용자와 비밀번호 검증

### 24.5 Access Token 발급

### 24.6 Refresh Token 발급

### 24.7 JWT Header와 Claim

### 24.8 RS256 서명

### 24.9 RSA Private Key

### 24.10 JWKS Public Key

### 24.11 Refresh Token Rotation

### 24.12 로그아웃과 토큰 폐기

### 24.13 Denylist

### 24.14 SSO 연계

### 24.15 내부 호출 공유 비밀키

관련 문서:

- `42.토큰을 생성해서 pdmg-service로 요청시 전달하는 방법.md`
- `46.토큰.md`
- `47.pdmg-jwt 아키텍처 다이어그램.md`
- `52.jwt 아키텍처 다이어그램.md`
- `67.로그인 아키텍처 다이어그램.md`

---

## 25장. 업무 요청의 JWT 검증

### 25.1 UI의 Access Token 저장

### 25.2 Authorization Header

### 25.3 DefaultFilter 토큰 추출

### 25.4 토큰 서명 검증

### 25.5 만료·타입 검증

### 25.6 사용자 ID 추출

### 25.7 ServiceContext 저장

### 25.8 인증과 인가의 차이

### 25.9 거래 통제와 JWT Claim

### 25.10 현재 HMAC 검증 구조

### 25.11 RS256과 HMAC 불일치

### 25.12 JWKS 기반 검증 전환

### 25.13 UI Authorization Header 보완

### 25.14 Token·Secret 로그 금지

관련 문서:

- `43.pdmg-service에서 JWT 토큰을 받으면 누가 검증하는가.md`
- `44.pdmg-ui는 JWT를 pdmg-service로 보내고 시스템 선처리에서 검증하는가.md`
- `45.pdmg-ui-로그인-JWT-service-선처리-검증-확인.md`
- `45.시스템선처리에서 JWT의 무엇을 점검하는가.md`

이 장은 반드시 다음 두 가지를 구분해 써야 합니다.

```text
AS-IS
└─ pdmg-fw JwtProvider의 HMAC 검증

목표 구조
└─ pdmg-jwt JWKS를 사용하는 RS256 검증
```

---

# 제7부. 예외·로그·운영

## 26장. 예외처리와 표준 오류

### 26.1 예외처리 원칙

### 26.2 계층별 예외

### 26.3 입력 검증 예외

### 26.4 인증 예외

### 26.5 인가 예외

### 26.6 Service ID 오류

### 26.7 업무 예외

### 26.8 DAO·DB 예외

### 26.9 외부 연계 예외

### 26.10 타임아웃 예외

### 26.11 GlobalExceptionHandler

### 26.12 `exceptionCode.yml`

### 26.13 MessageCache

### 26.14 HTTP 상태 결정

### 26.15 표준 오류 응답

### 26.16 오류 이력의 독립 트랜잭션

### 26.17 내부정보 노출 방지

### 26.18 UI 오류 팝업

관련 문서:

- `11.예외처리.md`
- `11.예외처리-1.md`
- `22.exceptionCode처리.md`
- `22.exceptionCode처리-1.md`
- `35.에러코드 설정방법.md`
- `36.프로그램 예외 처리 방법.md`
- `64.Exception 다이어그램.md`
- `에러처리.md`

---

## 27장. 이미지로그와 GUID 추적

### 27.1 이미지로그의 목적

### 27.2 `TB_FW_IMAGE_LOG`

### 27.3 요청 ImageLog INSERT

### 27.4 업무 처리

### 27.5 응답 ImageLog UPDATE

### 27.6 정상 거래 이미지로그

### 27.7 실패 거래 이미지로그

### 27.8 업무 트랜잭션과의 분리

### 27.9 이미지로그 조회 `mgcoa8888S0`

### 27.10 이미지로그 삭제 `mgcoa8888D0`

### 27.11 자기 자신에 대한 로그 생성

### 27.12 감사 로그 보존

### 27.13 민감정보 마스킹

### 27.14 GUID 기반 장애 추적

관련 문서:

- `68.트랜잭션 이미지로그 다이어그램.md`
- `69.GUID 아키텍처 다이어그램.md`

---

## 28장. 성능 감시와 운영 진단

### 28.1 성능 감시 대상

### 28.2 HTTP 전체 처리시간

### 28.3 TCF 구간별 처리시간

### 28.4 업무 트랜잭션 시간

### 28.5 Service 실행시간

### 28.6 SQL 실행시간

### 28.7 타임아웃 로그

### 28.8 GUID와 Service ID 로그

### 28.9 실시간 거래 진단

### 28.10 로그 파일 구성

### 28.11 현재 감시의 한계

### 28.12 운영 지표 권장안

### 28.13 개인정보와 Token 로그 금지

관련 문서:

- `성능감시.md`
- `68.트랜잭션 이미지로그 다이어그램.md`
- `69.GUID 아키텍처 다이어그램.md`

---

# 제8부. 환경설정과 배포

## 29장. Spring 환경구성

### 29.1 Spring Environment

### 29.2 PropertySource 우선순위

### 29.3 공통 설정

### 29.4 Local Profile

### 29.5 Dev Profile

### 29.6 Prod Profile

### 29.7 `@Value`

### 29.8 `@ConfigurationProperties`

### 29.9 `@ConditionalOnProperty`

### 29.10 `application-fw-defaults.yml`

### 29.11 `exceptionCode.yml` Import

### 29.12 DataSource 설정

### 29.13 TCF 설정

### 29.14 Filter·Interceptor 설정

### 29.15 타임아웃 설정

### 29.16 JWT 설정

### 29.17 UI 대상 서버 설정

### 29.18 Secret 외부 주입

### 29.19 시작 시 설정 검증

관련 문서:

- `28.Spring 환경구성정보.md`
- `28.Spring 환경구성정보-1.md`
- `29.환경구성정보와 소스 연관성-이미지순서.md`
- `31.Spring-환경파일-중심-소스연관성.md`
- `72.Spring 환경 구성 다이어그램.md`

---

## 30장. 환경설정에서 소스까지 추적하기

### 30.1 설정 키를 읽는 방법

### 30.2 설정값과 Java Bean 관계

### 30.3 TCF 활성화 값과 생성 Bean

### 30.4 Filter 활성화 값과 요청 처리

### 30.5 Timeout 값과 Executor

### 30.6 DataSource 값과 TransactionManager

### 30.7 JWT 설정과 Token Issuer

### 30.8 JWKS 설정과 Token Decoder

### 30.9 Exception Code와 오류 응답

### 30.10 UI 설정과 대상 URL

### 30.11 설정 변경 시 영향도 점검

---

# 제9부. 실전 개발 가이드

## 31장. 신규 프로그램 개발

### 31.1 요구사항 분석

### 31.2 업무축 결정

### 31.3 프로그램 번호 결정

### 31.4 Service ID 정의

### 31.5 화면·API 계약 정의

### 31.6 DTO 작성

### 31.7 Handler 작성

### 31.8 Controller 작성

### 31.9 Facade 작성

### 31.10 Service와 Rule 작성

### 31.11 DAO 작성

### 31.12 Mapper XML 작성

### 31.13 오류 코드 추가

### 31.14 UI 거래 등록

### 31.15 샘플 요청 작성

### 31.16 테스트 작성

### 31.17 빌드와 검증

### 31.18 완료 체크리스트

---

## 32장. CRUD 구현 패턴

### 32.1 목록 조회

### 32.2 상세 조회

### 32.3 등록

### 32.4 수정

### 32.5 삭제

### 32.6 논리 삭제와 물리 삭제

### 32.7 처리 건수 검증

### 32.8 동시성 처리

### 32.9 트랜잭션 적용

### 32.10 입력 검증

### 32.11 오류 코드

### 32.12 보안과 감사

### 32.13 CRUD 테스트

관련 문서:

- `CRUD서비스프롬프트가이드.md`
- `범용CRUD프롬프트.md`
- `범용crudservice프롬프팅.md`
- `32.범용CRUD프롬프트.md`
- `32.범용CRUD프롬프트 사용법.md`

프롬프트 원문 전체는 본문보다 부록에 두는 편이 읽기 좋습니다.

---

## 33장. 테스트와 품질 검증

### 33.1 테스트 전략

### 33.2 Handler 테스트

### 33.3 Facade 트랜잭션 테스트

### 33.4 Service와 Rule 단위 테스트

### 33.5 DAO·Mapper 통합 테스트

### 33.6 Controller API 테스트

### 33.7 TCF ON 테스트

### 33.8 TCF OFF 테스트

### 33.9 예외와 Rollback 테스트

### 33.10 타임아웃 테스트

### 33.11 JWT 성공·실패·만료 테스트

### 33.12 UI 요청 테스트

### 33.13 빌드와 WAR 검증

### 33.14 문서와 구현 정합성 확인

---

# 부록

## 부록 A. 전체 아키텍처 다이어그램 모음

- 전체 시스템
- 기술 스택
- 모듈
- 패키지
- 컴포넌트
- 클린 아키텍처
- HTTP 호출
- TCF
- 시스템 선후처리
- 업무 선후처리
- 트랜잭션
- JWT
- 로그인
- 예외처리
- 이미지로그
- GUID
- ServiceContext

`46~75`번 다이어그램 문서를 이 부록에 통합합니다.

## 부록 B. 네이밍 참조표

- 애플리케이션 코드
- Service ID
- 패키지
- 클래스
- DTO
- DAO
- Mapper
- SQL ID
- 오류 코드

## 부록 C. 환경설정 키 참조표

- 설정 키
- 기본값
- 환경별 값
- 주입 클래스
- 사용하는 Bean
- 변경 영향
- 보안 등급

## 부록 D. TCF ON/OFF 비교표

- URL
- Controller
- Handler
- STF·ETF
- 트랜잭션
- 타임아웃
- 예외처리
- 이미지로그
- 적용 용도

## 부록 E. CRUD 개발 프롬프트

- 최소 입력 양식
- 전체 CRUD 프롬프트
- 조회 전용 프롬프트
- 쓰기 거래 프롬프트
- 테스트 프롬프트
- 검토 체크리스트

## 부록 F. 장애 대응 체크리스트

- Handler를 찾지 못할 때
- 트랜잭션이 시작되지 않을 때
- Rollback되지 않을 때
- Mapper를 찾지 못할 때
- JWT 검증이 실패할 때
- GUID가 이어지지 않을 때
- 이미지로그가 남지 않을 때
- 타임아웃이 작동하지 않을 때
- Context가 누수될 때

## 부록 G. 용어집

- TCF
- STF
- ETF
- Service ID
- GUID
- ServiceContext
- TransactionContext
- Facade
- Handler
- ImageLog
- JWKS
- JTI
- Denylist

# 편집 원칙

현재 문서는 다음 기준으로 통합하는 것이 좋습니다.

```text
기본 문서
예: 03.어플리케이션 레이어드 아키텍처.md
       │
       └─ 개념과 기본 설명으로 사용

재분석 문서
예: 03.어플리케이션 레이어드 아키텍처-1.md
       │
       └─ 구현 검증·주의사항·정정 내용으로 사용

다이어그램 문서
예: 75.PDMG 컴포넌트 다이어그램.md
       │
       └─ 장 도입부 또는 부록 그림으로 사용

설계·Plan 문서
docs/superpowers/*
       │
       └─ 본문 근거 또는 편집자 참고자료로 사용
```

중복 파일은 책에 그대로 반복하지 않고 다음 구조로 합치는 것이 좋습니다.

```text
개념
 → AS-IS 실제 구현
 → 실행 흐름
 → 설정과 소스
 → 주의사항
 → 예제
 → 점검 체크리스트
```

가장 읽기 좋은 책의 중심 흐름은 다음과 같습니다.

```text
PDMG 전체 이해
 → 식별체계와 패키지
 → HTTP·TCF 요청
 → Handler·Facade·Service·DAO
 → 트랜잭션·타임아웃
 → JWT·보안
 → 예외·로그·운영
 → 환경설정
 → 신규 CRUD 개발
```

이 구성이면 아키텍트는 제1~8부를 참조하고, 개발자는 제2~5부와 제9부를 실전 가이드로 활용할 수 있습니다.
