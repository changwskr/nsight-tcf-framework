# NSIGHT 자동 하네스 상세설계 패키지

본 패키지는 승인형 자동 하네스 상세설계를 구현 가능한 계약으로 구체화한다.

## 기술 기준

- Java 21, Spring Boot 3.x, Gradle, MyBatis
- Oracle 19c 이상
- OpenAPI 3.1
- JSON Schema Draft 2020-12
- Workflow·Gate as Code(YAML)
- BASE 패키지: `com.nh.nsight.harness`

## 핵심 원칙

1. Agent는 Run 상태나 Gate 결과를 직접 변경하지 않는다.
2. Orchestrator만 상태 전이·Task 배정·Checkpoint·Promotion을 수행한다.
3. Gate Engine만 Artifact 승격 가능 여부를 판정한다.
4. 사람 승인이 필요한 Gate는 자동 PASS할 수 없다.
5. 모든 Artifact·Evidence는 URI·버전·SHA-256 Hash를 가진다.
6. 요구사항부터 ServiceId·프로그램·SQL·DB·테스트까지 양방향 추적한다.
7. 운영 배포, 운영 DB 실행, 승인 없는 Git Push는 범위에서 제외한다.

## 구성

| 디렉터리 | 내용 |
|---|---|
| `architecture` | 모듈·패키지·의존성 구조 |
| `database` | Oracle DDL·인덱스·테이블 정의 |
| `api` | REST API와 OpenAPI 3.1 |
| `schemas` | Agent·Manifest·Workflow·Gate JSON Schema |
| `workflows` | 조회·CRUD·신규 업무 WAR Workflow |
| `gate-rules` | HG-00~HG-90 Gate Rule |
| `workspace` | 작업공간과 Manifest 템플릿 |
| `tools` | 구문·Schema·참조 검증 스크립트 |
| `docs/superpowers/plans` | 구현계획 |

## 검증

```bash
python tools/validate_package.py
```
