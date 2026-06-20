# tcf-web — TCF HTTP 레이어

`TCF.process()`를 HTTP 엔드포인트로 노출하고, REST 어댑터·필터·전역 예외 처리·거래로그 DB 연동·WAR 배포 부트스트랩을 제공합니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-web` |
| 패키지 | `com.nh.nsight.tcf.web` |
| 산출물 | JAR (라이브러리) |

## 주요 구성

| 구성요소 | 설명 |
|----------|------|
| `OnlineTransactionController` | `POST /online`, `POST /{businessCode}/online` |
| `TcfGateway` | REST·multipart 등 비표준 진입점 → `TCF.process()` 위임 |
| `GuidMdcCleanupFilter` | 요청 종료 시 MDC·Context 정리 |
| `GlobalStandardExceptionHandler` | 표준 오류 응답 변환 |
| `TcfAutoConfiguration` | Spring Boot 자동 구성 |
| `TcfPrimaryDataSourceAutoConfiguration` | 다중 DS 환경 기본 DataSource |
| `TcfTransactionLogDataSourceConfiguration` | H2 기반 공유 거래로그 DB |
| `NsightWarBootstrap` | WAR(Tomcat) 배포 시 context path·프로파일 초기화 |

## API 엔드포인트

| Method | Path | 설명 |
|--------|------|------|
| POST | `/online` | 표준 JSON 거래 (header.serviceId 필수) |
| POST | `/{businessCode}/online` | 업무코드 경로 기반 거래 |

Tomcat 예: `POST http://localhost:8080/sv/online`

## TcfGateway 사용 예

업무 모듈의 REST Controller가 서비스를 직접 호출하지 않고 TCF 파이프라인을 거치도록 할 때 사용합니다.

```java
StandardResponse<Object> response = tcfGateway.invoke(
    TcfInvokeRequest.builder("UD.File.list", "UD-LST-0001", "INQUIRY")
        .body(body)
        .userId(userId)
        .clientIp(clientIp)
        .build()
);
```

## 거래로그 DB

bootRun·Tomcat 공통으로 프로젝트 `data/nsight-txlog/` H2 파일을 공유합니다.

- 시스템 프로퍼티: `nsight.txlog.path`
- bootRun: Gradle `bootRun`이 자동 설정
- ztomcat: `ztomcat/conf/setenv.bat`의 `-Dnsight.txlog.path`

## Tomcat 프로파일

`application-tomcat.yml` — WAR 배포 시 `local,tomcat` 프로파일과 함께 로드됩니다.

## 의존 관계

```text
tcf-util → tcf-core → tcf-web → *-service / tcf-om / tcf-batch
```

## 빌드

```bash
gradle :tcf-web:build
```
