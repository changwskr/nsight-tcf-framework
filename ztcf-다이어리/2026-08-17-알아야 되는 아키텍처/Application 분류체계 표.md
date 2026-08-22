현재 NSIGHT/PDMG에서 정리해 온 기준을 바탕으로 **어플리케이션 분류체계 표**를 다음과 같이 잡는 것이 가장 관리하기 좋습니다. 핵심은 `대그룹 → 업무 → 세부업무 → 프로그램 → ServiceId`까지 동일한 분류축을 사용하는 것입니다. PDMG 네이밍 역시 `MG → CO → A → 프로그램번호 → 거래구분` 구조로 ServiceId·패키지·클래스·Mapper를 연결하도록 되어 있습니다.

### 1. 전체 분류 계층

| Level | 분류      | 예시          | 설명                     |
| ----- | --------- | ------------- | ------------------------ |
| L1    | 대그룹    | `MG`          | Marketing Group Platform |
| L2    | 업무      | `CO`          | 공통                     |
| L3    | 세부업무  | `A`           | 공통관리                 |
| L4    | 프로그램  | `9000`        | 프로그램 식별번호        |
| L5    | 거래유형  | `S`           | 조회                     |
| L6    | 거래순번  | `0`           | 동일 유형 내 순번        |
| 최종  | ServiceId | `mgcoa9000S0` | 실제 온라인 거래 식별자  |

```text
MG
└─ CO
   └─ A
      └─ 9000
         ├─ S0 조회
         ├─ C0 등록
         ├─ U0 수정
         └─ D0 삭제

              ↓

        mgcoa9000S0
```

### 2. NSIGHT 어플리케이션 업무 분류체계

| 대그룹 | 대그룹명                 | 업무코드 | 업무명           | 영문명                   | 세부업무코드 | 세부업무명      |
| ------ | ------------------------ | -------- | ---------------- | ------------------------ | ------------ | --------------- |
| **MG** | Marketing Group Platform | **CO**   | 공통             | Common                   | A            | 공통관리        |
| MG     | Marketing Group Platform | CO       | 공통             | Common                   | B            | 사용자관리      |
| MG     | Marketing Group Platform | CO       | 공통             | Common                   | C            | 메뉴관리        |
| MG     | Marketing Group Platform | CO       | 공통             | Common                   | D            | 로그관리        |
| MG     | Marketing Group Platform | **IC**   | 통합고객         | Integration Customer     | A            | 고객통합조회    |
| MG     | Marketing Group Platform | IC       | 통합고객         | Integration Customer     | B            | 고객통합관리    |
| MG     | Marketing Group Platform | IC       | 통합고객         | Integration Customer     | C            | 고객통합검색    |
| MG     | Marketing Group Platform | IC       | 통합고객         | Integration Customer     | D            | 고객식별        |
| MG     | Marketing Group Platform | **PC**   | 개인고객         | Private Customer         | A            | 개인고객조회    |
| MG     | Marketing Group Platform | PC       | 개인고객         | Private Customer         | B            | 개인고객관리    |
| MG     | Marketing Group Platform | **BC**   | 기업고객         | Business Customer        | A            | 기업고객조회    |
| MG     | Marketing Group Platform | BC       | 기업고객         | Business Customer        | B            | 기업고객관리    |
| MG     | Marketing Group Platform | **MS**   | Mini Single View | Mini Single View         | A            | 고객요약        |
| MG     | Marketing Group Platform | MS       | Mini Single View | Mini Single View         | B            | 계좌요약        |
| MG     | Marketing Group Platform | MS       | Mini Single View | Mini Single View         | C            | 상품요약        |
| MG     | Marketing Group Platform | **SA**   | 세일즈           | Sale                     | A            | 상담            |
| MG     | Marketing Group Platform | SA       | 세일즈           | Sale                     | B            | 판매            |
| MG     | Marketing Group Platform | SA       | 세일즈           | Sale                     | C            | 추천            |
| MG     | Marketing Group Platform | **PD**   | 상품             | Product                  | A            | 상품조회        |
| MG     | Marketing Group Platform | PD       | 상품             | Product                  | B            | 상품관리        |
| MG     | Marketing Group Platform | **CM**   | 캠페인           | Campaign                 | A            | 캠페인조회      |
| MG     | Marketing Group Platform | CM       | 캠페인           | Campaign                 | B            | 캠페인관리      |
| MG     | Marketing Group Platform | **EB**   | 이벤트배치       | Event Batch              | A            | 배치관리        |
| MG     | Marketing Group Platform | EB       | 이벤트배치       | Event Batch              | B            | 스케줄관리      |
| MG     | Marketing Group Platform | **EP**   | 이벤트포털       | Event Portal             | A            | 포털조회        |
| MG     | Marketing Group Platform | EP       | 이벤트포털       | Event Portal             | B            | 포털관리        |
| MG     | Marketing Group Platform | **BP**   | 상품포털         | Product Portal           | A            | 상품포털조회    |
| MG     | Marketing Group Platform | BP       | 상품포털         | Product Portal           | B            | 상품포털관리    |
| MG     | Marketing Group Platform | **BD**   | 빅데이터         | Big Data                 | A            | 분석조회        |
| MG     | Marketing Group Platform | BD       | 빅데이터         | Big Data                 | B            | 분석관리        |
| MG     | Marketing Group Platform | **SS**   | 세션             | Session                  | A            | 세션조회        |
| MG     | Marketing Group Platform | SS       | 세션             | Session                  | B            | 세션관리        |
| MG     | Marketing Group Platform | **CS**   | 고객서비스       | Customer Service         | A            | 서비스조회      |
| MG     | Marketing Group Platform | CS       | 고객서비스       | Customer Service         | B            | 서비스관리      |
| MG     | Marketing Group Platform | **CT**   | 컨택트           | Contact                  | A            | 컨택트조회      |
| MG     | Marketing Group Platform | CT       | 컨택트           | Contact                  | B            | 컨택트관리      |
| MG     | Marketing Group Platform | **MG**   | 마케팅플랫폼     | Marketing Group Platform | A            | 공통관리        |
| MG     | Marketing Group Platform | MG       | 마케팅플랫폼     | Marketing Group Platform | B            | 사용자관리      |
| MG     | Marketing Group Platform | MG       | 마케팅플랫폼     | Marketing Group Platform | C            | 메뉴관리        |
| MG     | Marketing Group Platform | MG       | 마케팅플랫폼     | Marketing Group Platform | D            | 로그관리        |
| MG     | Marketing Group Platform | **OM**   | 운영관리         | Operation Management     | A            | 사용자·권한관리 |
| MG     | Marketing Group Platform | OM       | 운영관리         | Operation Management     | B            | 메뉴관리        |
| MG     | Marketing Group Platform | OM       | 운영관리         | Operation Management     | C            | 환경설정관리    |
| MG     | Marketing Group Platform | OM       | 운영관리         | Operation Management     | D            | 공통로그관리    |

### 3. 실제 프로그램까지 연결하면

예를 들어 **공통(CO) / 공통관리(A)** 영역의 프로그램 번호가 `9000`이라면 다음처럼 일관되게 연결됩니다.

```text
Application Classification
MG / CO / A
       │
       ▼
Program ID
mgcoa9000
       │
       ├─ mgcoa9000Handler
       ├─ mgcoa9000Facade
       ├─ mgcoa9000Service
       ├─ mgcoa9000DAO
       └─ mgcoa9000-ORA.xml
       │
       ▼
ServiceId
├─ mgcoa9000S0   조회
├─ mgcoa9000C0   등록
├─ mgcoa9000U0   수정
└─ mgcoa9000D0   삭제
```

이 구조는 실제 PDMG 패키지의 `nhnis.mg.co.a`와 Mapper의 `rdw.mg.co.a`가 동일한 업무 분류축을 사용한다는 현재 네이밍 구조와도 맞습니다.

한 가지는 **최종 확정이 필요합니다.** 현재 대그룹 코드가 `MG`인데 업무코드에도 `MG`가 존재하므로 `MG/MG/A`처럼 의미가 중복됩니다. 네이밍 정본에서는 메시지 업무코드 충돌을 피하기 위해 `MM`으로 조정한 사례도 있으므로, **대그룹 `MG`와 업무 `MG`의 코드 중복은 Architecture Decision으로 확정하는 것이 좋습니다.**
