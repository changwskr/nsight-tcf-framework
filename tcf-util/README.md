# tcf-util — TCF 공통 유틸리티

Spring에 의존하지 않는 순수 Java 유틸리티 모듈입니다. `tcf-core`, `tcf-web` 및 업무 서비스에서 공통으로 사용합니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-util` |
| 패키지 | `com.nh.nsight.tcf.util`, `com.nh.nsight.tcf.tpmutil` |
| 산출물 | JAR (라이브러리) |

## 주요 클래스

| 클래스 | 설명 |
|--------|------|
| `GuidGenerator` | GUID·TraceId 생성 |
| `DateTimeUtil` | 날짜/시간 포맷·변환 (KST) |

## 의존 관계

```text
tcf-util  (최하위, Spring 없음)
   ↑
tcf-core
```

## 빌드

```bash
gradle :tcf-util:build
```
