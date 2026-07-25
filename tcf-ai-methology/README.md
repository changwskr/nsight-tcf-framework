# tcf-ai-methology — NSIGHT Model Studio (Spring Boot)

Python MVP(`ref/nsight_model_studio`)를 **Spring Boot 3.3 / JDK 21** 모듈로 이식한 업무모델 자동화 도구입니다.

## 기능

- 프로젝트·업무코드·도메인·패키지 프로파일 정의
- 화면·이벤트·ServiceId·거래코드·Timeout·권한 정의
- 테이블·필드·DTO 역할(요청/조건/응답) 정의
- 모델·Workspace 검증
- 동일 도메인 ServiceId → **단일 Handler 병합** 코드 생성
- Java / Mapper XML / DDL / OM Catalog / 설계서 / 추적성 CSV / ZIP

## 실행

```bat
run.bat
```

또는

```bash
./gradlew :tcf-ai-methology:bootRun
```

브라우저: http://127.0.0.1:8787

## 테스트

```bash
./gradlew :tcf-ai-methology:test
```

## API (기존 Python과 동일 경로)

| Method | Path | 설명 |
|--------|------|------|
| GET | `/api/health` | 헬스 |
| GET | `/api/models` | 모델 목록 (`?q=` 검색) |
| GET/POST/PUT/DELETE | `/api/models/{id}` | CRUD |
| POST | `/api/models/{id}/duplicate` | 복제 |
| POST | `/api/models/reseed` | classpath 시드로 DB 교체 적재 |
| GET | `/api/sample` | 샘플 모델 |
| POST | `/api/validate` | 단건 검증 |
| POST | `/api/validate-workspace` | Workspace 검증 |
| POST | `/api/preview` | 산출물 미리보기 |
| POST | `/api/generate` | ZIP 생성 |
| POST | `/api/generate-saved` | 저장 모델 ZIP |

모델 저장: **H2 파일 DB** `%USERPROFILE%/nsight-model-studio/models-db`  
- 테이블 `business_model` (키 컬럼 + JSON payload)  
- H2 콘솔: http://127.0.0.1:8787/h2-console (JDBC URL은 기동 로그/`application.yml` 참고)  
- 최초 기동 시 DB가 비어 있으면 레거시 `models.json` 또는 seed를 자동 이관  
- 도메인 시드 재반영: `POST /api/models/reseed` (또는 DB 파일 삭제 후 재기동)  
- JDBC URL 변경: `NSIGHT_MODEL_STUDIO_DB_URL`

시드(`data/models-seed.json`): 프레임워크 Handler·schema 분석 기반 **41건**  
인벤토리: `docs/DOMAIN_MODEL_INVENTORY.md`  
재생성: `node tcf-ai-methology/generate-domain-models.js`  
DB 반영: `POST /api/models/reseed` (또는 DB 비운 뒤 재기동)

## 모듈 구조

```text
tcf-ai-methology/
├── src/main/java/.../aimethodology/
│   ├── AiMethodologyApplication.java
│   ├── config / store / validation / generator / web
│   └── ...
├── src/main/resources/
│   ├── application.yml
│   ├── static/          # UI (Python static 이식)
│   └── data/            # sample · seed
├── docs/                # 방법론 문서
└── ref/nsight_model_studio/   # 원본 Python 참조
```

## 참고

- 원본: `ref/nsight_model_studio/`
- 방법론: `docs/NSIGHT_Automated_Development_Methodology.md`
- 생성 코드는 초안이며 Diff·Compile·Test·리뷰 후 업무 모듈에 반영하십시오.
