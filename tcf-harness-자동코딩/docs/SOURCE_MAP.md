# 소스·문서 맵 (자동코딩 하네스)

에이전트가 구현 시 우선 참조할 위치.

| 우선순위 | 경로 | 용도 |
| ---: | --- | --- |
| 1 | `../tcf-core`, `../tcf-web`, `../tcf-*` | 프레임워크 API |
| 1 | `../*-service` (예: `sv-service`, `av-service`) | Handler~Mapper 실코드 패턴 |
| 2 | `../zdocs-1`, `../zarchitecture`, `../zguide`, `../zman` | 설계·가이드 |
| 2 | `../ztcfbook` | 서술형 해설 (참고) |
| 3 | `NSIGHT-자동-하네스-요구사항-정의서.md` | FR/BR/Gate 요구 |
| 3 | `NSIGHT-자동-하네스-상세설계서.md` | Orchestrator·계약·작업공간 |
| 3 | `코딩지침/` | 구현 계약 패키지 원본 |
| 3 | `harness/` | 이 하네스가 실행에 쓰는 계약 복사본 |

식별자·계층·ServiceId는 **실코드에서 확인**하고, 없으면 Gap으로 기록한다.
