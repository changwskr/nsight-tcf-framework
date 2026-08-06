

===== PAGE 1 =====
자동 하네스 Pilot 요구사항 정의서
AV 자산평가 내역 조회
1. 도입 전 안내말
본 요구사항은 NSIGHT 자동 하네스가 다음 전체 절차를 정상적으로 수행할 수 있는지 검증하기 위한 최초 Pilot 요건이
다.
요구사항 입력
→ 요구사항 구조화
→ 업무·도메인 분석
→ 화면·거래 설계
→ 프로그램·SQL 설계
→ 구현계획 수립
→ NSIGHT TCF 표준 코드 생성
→ 빌드
→ 단위·통합 테스트
→ 추적성 검증
→ Architecture Gate
→ 결과 패키징
최초 Pilot에서는 자동 하네스 자체의 안정성을 검증해야 하므로 복잡한 등록·변경 거래, 외부 시스템 연계, 다중 DB 트
랜잭션은 제외한다.
2. 문서 개요
2.1 목적
자산평가 담당자가 기준일과 조회조건을 입력하여 담당 지점의 자산평가 내역을 조회할 수 있도록 한다.
2.2 적용범위
구분 적용 내용
업무코드 AV
업무명 자산평가
업무 도메인 AssetValuation
처리 유형 조회
채널 NSIGHT 업무 화면
1


===== PAGE 2 =====
구분 적용 내용
서버 모듈 av-service
온라인 EndpointPOST /av/online
데이터 처리 단일 DB 조회
외부 연계 없음
데이터 변경 없음
2.3 대상 독자
자산평가 업무 담당자
NSIGHT 업무 개발자
프레임워크 개발자
테스트 담당자
애플리케이션 아키텍트
자동 하네스 운영자
2.4 선행조건
항목 조건
사용자 인증JWT 또는 표준 인증 문맥이 생성되어 있어야 한다.
사용자 정보 userId, branchId, channelId가 인증 문맥에 존재해야 한다.
기준 데이터자산평가 조회용 테이블 또는 테스트 데이터가 존재해야 한다.
ServiceId OM 또는 테스트용 Service Catalog에 등록 가능해야 한다.
실행환경 av-service가 로컬 또는 테스트 환경에서 실행 가능해야 한다.
2.5 용어 정의
용어 정의
기준일 자산평가 내역을 조회하는 업무 기준 날짜
자산유형 부동산, 동산, 유가증권 등 평가 대상의 분류
평가금액 기준일 현재 산정된 자산의 평가금액
담당 지점 인증된 사용자의 소속 지점
평가상태 평가 완료, 평가 진행, 평가 취소 등의 상태
• 
• 
• 
• 
• 
• 
2


===== PAGE 3 =====
3. 요구사항 기본정보
항목 값
요구사항 ID REQ-AV-001
요구사항명 자산평가 내역 조회
요구사항 유형기능 요구사항
우선순위 P0
처리 유형 INQUIRY
업무코드 AV
도메인 AssetValuation
화면 ID AV-VAL-0001
이벤트 ID AV-VAL-0001-E01
ServiceId AV.AssetValuation.selectList
거래코드 AV-INQ-0001
Endpoint POST /av/online
Timeout 3초
트랜잭션 Read Only
페이징 적용
개인정보 고객번호 마스킹 적용
외부 시스템 없음
4. 업무 요구사항
4.1 사용자 요구
자산평가 담당자는 화면에서 기준일과 선택 조건을 입력하고 조회 버튼을 누르면 담당 지점에서 관리하는 자산평가 내
역을 조회할 수 있어야 한다.
사용자 로그인
→ 자산평가 내역 조회 화면 진입
→ 기준일 입력
→ 선택 조회조건 입력
→ 조회 버튼 클릭
→ 자산평가 내역 표시
3


===== PAGE 4 =====
4.2 요구사항 본문
REQ-AV-001
시스템은 인증된 사용자가 자산평가 내역 조회를 요청하면,
인증 문맥의 담당 지점과 사용자가 입력한 기준일 및 선택 조건을 기준으로
조회 가능한 자산평가 목록을 반환해야 한다.
조회 결과의 고객번호는 마스킹하여 반환해야 하며,
조회 결과는 페이지 단위로 제공해야 한다.
인증되지 않은 요청, 필수값 누락, 잘못된 날짜,
허용 범위를 초과한 페이지 크기는 업무 프로그램을 실행하기 전에 차단해야 한다.
5. 화면 및 이벤트 요구사항
5.1 화면 정보
항목 내용
화면 ID AV-VAL-0001
화면명 자산평가 내역 조회
화면 유형 목록 조회
기본 조회 여부아니오
권한 자산평가 조회 권한
개인정보 표시고객번호 마스킹
5.2 조회조건
필드 필수 형식 설명
기준일 Y yyyyMMdd 평가 기준 날짜
고객번호 N 숫자 또는 표준 고객번호특정 고객 조회
자산유형 N 코드 평가 대상 자산 분류
평가상태 N 코드 평가 진행 상태
페이지 번호 Y 1 이상 기본값 1
페이지 크기 Y 1~100 기본값 20
4


===== PAGE 5 =====
5.3 이벤트
이벤트 ID 이벤트명 발생 조건 호출 ServiceId
AV-VAL-0001-E01조회 버튼 클릭조회조건 검증 성공AV.AssetValuation.selectList
6. 표준 요청 계약
6.1 요청 Header
{
"header":{
"businessCode":"AV",
"serviceId":"AV.AssetValuation.selectList",
"transactionCode":"AV-INQ-0001",
"processingType":"INQUIRY",
"channelId":"WEBTOP",
"traceId":"자동 생성",
"guid":"자동 생성"
}
}
userId와 branchId는 클라이언트 입력값을 신뢰하지 않고 인증 문맥에서 취득한다.
6.2 요청 Body
{
"body":{
"baseDate":"20260805",
"customerNo":"1234567890",
"assetTypeCode":"REAL_ESTATE",
"valuationStatusCode":"COMPLETED",
"pageNo":1,
"pageSize":20
}
}
6.3 입력 검증 기준
규칙 ID 검증 기준 오류코드
AV-VAL-001기준일은 필수다. AV001
5


===== PAGE 6 =====
규칙 ID 검증 기준 오류코드
AV-VAL-002기준일은 yyyyMMdd 형식이어야 한다. AV002
AV-VAL-003기준일은 현재 업무일보다 미래일 수 없다.AV003
AV-VAL-004페이지 번호는 1 이상이어야 한다. AV004
AV-VAL-005페이지 크기는 1~100이어야 한다. AV005
AV-VAL-006정의되지 않은 자산유형 코드를 사용할 수 없다.AV006
AV-VAL-007정의되지 않은 평가상태 코드를 사용할 수 없다.AV007
AV-VAL-008인증 문맥에 담당 지점이 존재해야 한다.AV008
7. 업무 규칙
규칙 ID 업무 규칙
AV-RULE-001사용자는 자신의 인증 문맥에 설정된 담당 지점의 자산평가 내역만 조회할 수 있다.
AV-RULE-002요청 Body에 branchId가 포함되어도 조회조건으로 사용하지 않는다.
AV-RULE-003고객번호가 입력되면 해당 고객의 자산평가 내역만 조회한다.
AV-RULE-004자산유형이 입력되면 해당 자산유형만 조회한다.
AV-RULE-005평가상태가 입력되면 해당 상태만 조회한다.
AV-RULE-006조회 결과의 고객번호는 서버에서 마스킹하여 반환한다.
AV-RULE-007삭제 또는 취소 처리된 평가정보는 기본 조회 결과에서 제외한다.
AV-RULE-008데이터가 없으면 오류가 아니라 빈 목록을 반환한다.
AV-RULE-009조회 거래에서는 데이터 등록·변경·삭제를 수행하지 않는다.
AV-RULE-010전체 건수와 현재 페이지의 목록을 함께 반환한다.
8. 표준 응답 계약
8.1 정상 응답
{
"header":{
"serviceId":"AV.AssetValuation.selectList",
"transactionCode":"AV-INQ-0001",
"resultCode":"SUCCESS",
"traceId":"TRC-20260805-000001",
6


===== PAGE 7 =====
"guid":"GUID-20260805-000001"
},
"body":{
"pageNo":1,
"pageSize":20,
"totalCount":1,
"items":[
{
"assetId":"AST-000001",
"customerNo":"1234******",
"assetTypeCode":"REAL_ESTATE",
"assetTypeName":"부동산",
"valuationAmount":350000000,
"valuationDate":"20260805",
"valuationStatusCode":"COMPLETED",
"valuationStatusName":"평가완료"
}
]
}
}
8.2 조회 결과 필드
필드 형식 설명
assetId 문자열 자산 식별번호
customerNo 문자열 마스킹된 고객번호
assetTypeCode 문자열 자산유형 코드
assetTypeName 문자열 자산유형명
valuationAmount 숫자 평가금액
valuationDate yyyyMMdd 평가일자
valuationStatusCode문자열 평가상태 코드
valuationStatusName문자열 평가상태명
8.3 데이터 없음 응답
{
"header":{
"serviceId":"AV.AssetValuation.selectList",
"resultCode":"SUCCESS"
},
"body":{
"pageNo":1,
7


===== PAGE 8 =====
"pageSize":20,
"totalCount":0,
"items":[]
}
}
9. 프로그램 구조 요구사항
다음 프로그램은 설계 예시이며 자동 하네스가 실제 프로젝트의 기준 소스를 확인한 후 최종 확정해야 한다.
AvAssetValuationHandler
  ↓
AvAssetValuationFacade
  ↓
AvAssetValuationService
  ├─ AvAssetValuationInquiryRule
  └─ AvAssetValuationDao
       ↓
     AvAssetValuationMapper
       ↓
     AvAssetValuationMapper.xml
9.1 패키지 구조
com.nh.nsight.marketing.av.assetvaluation.handler
com.nh.nsight.marketing.av.assetvaluation.facade
com.nh.nsight.marketing.av.assetvaluation.service
com.nh.nsight.marketing.av.assetvaluation.rule
com.nh.nsight.marketing.av.assetvaluation.dao
com.nh.nsight.marketing.av.assetvaluation.mapper
com.nh.nsight.marketing.av.assetvaluation.dto
9.2 계층별 책임
계층 책임
Handler ServiceId 분기, 요청 DTO 변환, Facade 호출
Facade 조회 유스케이스 조립, Read Only 트랜잭션 경계
Service 조회 처리 흐름
Rule 조회조건과 업무 권한 규칙 검증
DAO 데이터 조회 추상화
8


===== PAGE 9 =====
계층 책임
Mapper SQL 실행
DTO 계층 간 데이터 계약
10. 데이터 설계 요구사항
다음 DB 객체는 Pilot용 설계 예시다.
10.1 대상 테이블
테이블 역할
AV_ASSET_VALUATION자산평가 기본정보
AV_ASSET_MASTER 자산 기본정보
CM_CODE_DETAIL 자산유형·평가상태 코드
10.2 조회조건
WHEREV.BRANCH_ID=:authenticatedBranchId
ANDV.BASE_DATE<=:baseDate
ANDV.DELETE_YN='N'
선택 조건은 값이 입력된 경우에만 적용한다.
customerNo
assetTypeCode
valuationStatusCode
10.3 정렬 기준
평가일자 내림차순
→ 자산 식별번호 오름차순
동일 요청은 항상 동일한 정렬 결과를 반환해야 한다.
9


===== PAGE 10 =====
11. 정상 처리 흐름
1. 사용자가 조회 버튼을 클릭한다.
2. 화면이 필수 입력값을 검증한다.
3. POST /av/online 요청을 전송한다.
4. JWT Filter 또는 Gateway가 사용자를 인증한다.
5. OnlineTransactionController가 요청을 수신한다.
6. TCF가 표준 Header를 검증한다.
7. STF가 권한·거래통제·Timeout을 확인한다.
8. Dispatcher가 ServiceId로 Handler를 선택한다.
9. Handler가 요청 DTO를 생성한다.
10. Facade가 Read Only 트랜잭션을 시작한다.
11. Rule이 조회조건과 사용자 지점 권한을 검증한다.
12. Service가 DAO를 호출한다.
13. Mapper가 페이징 조회 SQL을 실행한다.
14. 고객번호를 서버에서 마스킹한다.
15. ETF가 표준 응답과 거래로그를 생성한다.
16. 화면이 조회 결과를 표시한다.
12. 오류·Timeout·장애 흐름
상황 처리 기준
인증 실패 Handler 실행 전 차단
권한 없음 FORBIDDEN 표준 오류 반환
필수값 누락 입력 검증 오류 반환
잘못된 코드 업무 검증 오류 반환
DB 오류 시스템 오류로 변환하고 원문 DB 메시지는 노출하지 않음
Timeout 3초 초과 시 Timeout 표준 오류 반환
데이터 없음 정상 빈 목록 반환
Mapper 미등록 빌드 또는 통합테스트 Gate 실패
ServiceId 미등록Application 기동 또는 Gate 실패
마스킹 누락 보안 Gate 실패
10


===== PAGE 11 =====
13. 비기능 요구사항
ID 영역 요구사항
NFR-AV-001성능 정상 데이터 기준 p95 응답시간은 3초 이내여야 한다.
NFR-AV-002용량 페이지 크기는 최대 100건으로 제한한다.
NFR-AV-003보안 고객번호 원문을 응답과 일반 로그에 기록하지 않는다.
NFR-AV-004감사 사용자, 지점, ServiceId, TraceId, GUID, 조회조건을 추적할 수 있어야 한다.
NFR-AV-005안정성 조회 실패가 다른 거래나 다른 Run에 영향을 주지 않아야 한다.
NFR-AV-006재현성 동일 Baseline과 입력자료로 동일 소스와 계약을 생성할 수 있어야 한다.
NFR-AV-007호환성 표준 요청·응답 Envelope를 변경하지 않는다.
NFR-AV-008관측성 거래 처리시간과 실행 SQL ID를 Evidence로 남긴다.
14. 수용 기준
AC ID 수용 기준
AC-AV-001유효한 기준일로 요청하면 담당 지점의 자산평가 목록을 반환한다.
AC-AV-002다른 지점의 데이터는 반환하지 않는다.
AC-AV-003고객번호가 입력되면 해당 고객의 내역만 반환한다.
AC-AV-004고객번호가 마스킹되어 반환된다.
AC-AV-005데이터가 없으면 totalCount=0, items=[]를 반환한다.
AC-AV-006미래 기준일을 입력하면 AV003 오류를 반환한다.
AC-AV-007페이지 크기가 100을 초과하면 AV005 오류를 반환한다.
AC-AV-008미인증 요청은 업무 Handler가 실행되지 않는다.
AC-AV-009조회 처리 중 INSERT·UPDATE·DELETE SQL이 실행되지 않는다.
AC-AV-010TraceId와 GUID로 거래로그·테스트 결과를 연결할 수 있다.
AC-AV-011Gradle Build와 전체 자동테스트가 성공한다.
AC-AV-012요구사항–ServiceId–프로그램–SQL–테스트 추적률이 100%다.
AC-AV-013HG-00부터 HG-90까지 필수 Gate를 모두 통과한다.
11


===== PAGE 12 =====
15. 필수 테스트 시나리오
테스트 ID 시나리오 예상 결과
TC-AV-001정상 전체 조회 목록과 전체 건수 반환
TC-AV-002고객번호 조건 조회 해당 고객 데이터만 반환
TC-AV-003자산유형 조건 조회 해당 유형만 반환
TC-AV-004평가상태 조건 조회 해당 상태만 반환
TC-AV-005데이터 없음 정상 빈 목록
TC-AV-006기준일 누락 AV001
TC-AV-007날짜 형식 오류 AV002
TC-AV-008미래 기준일 AV003
TC-AV-009페이지 크기 초과 AV005
TC-AV-010미인증 요청 인증 오류
TC-AV-011다른 지점 데이터 접근결과에서 제외 또는 권한 오류
TC-AV-012고객번호 마스킹 원문 미노출
TC-AV-013DB 예외 표준 시스템 오류
TC-AV-0143초 초과 Timeout 오류
TC-AV-015SQL 변경 후 Drift 검사변경 영향 추적
16. 자동 하네스 입력자료
이 요구사항을 다음 위치에 저장한다.
00-IN/
├─ requirements/
│  └─ REQ-AV-001-자산평가-내역조회.md
├─ samples/
│  ├─ request.json
│  └─ expected-response.json
└─ manifests/
   └─ input-manifest.json
12


===== PAGE 13 =====
16.1 요구사항 Manifest 예시
{
"requirementId":"REQ-AV-001",
"businessCode":"AV",
"domain":"AssetValuation",
"processingType":"INQUIRY",
"serviceId":"AV.AssetValuation.selectList",
"transactionCode":"AV-INQ-0001",
"sourceType":"USER_APPROVED_REQUIREMENT",
"humanApprovalRequired":true,
"targetWorkflowId":"WF-ONLINE-INQUIRY-V1"
}
17. 자동 하네스 생성 대상
하네스는 본 요구사항을 기준으로 다음 산출물을 생성해야 한다.
요구사항 원장
업무·도메인 분석서
화면 설계서
거래 설계서
프로그램 설계서
DTO 정의
SQL·DB 설계서
추적성 매트릭스
ADR
구현계획
Handler
Facade
Service
Rule
DAO
Mapper Interface
Mapper XML
테스트 코드
OM Service Catalog 등록안
거래통제·Timeout 등록안
Build Evidence
Test Evidence
Security Evidence
Traceability Report
Release Manifest
13


===== PAGE 14 =====
18. 사람 승인 대상
자동 하네스가 임의로 확정해서는 안 되는 항목은 다음과 같다.
승인 항목 승인 책임
ServiceId와 거래코드애플리케이션 아키텍트
테이블과 데이터 소유권데이터 아키텍트
고객번호 마스킹 방식보안 담당자
담당 지점 조회권한 업무 담당자·보안 담당자
Timeout 3초 애플리케이션·기술 아키텍트
최종 SQL과 인덱스 DBA
운영 반영 운영 책임자
예외 승인 Architecture Review Board
19. 완료 기준
다음 조건을 모두 만족해야 본 요건을 완료한 것으로 판단한다.
요구사항 승인
+ 설계 산출물 생성
+ 프로그램 생성
+ Gradle Build 성공
+ 단위테스트 성공
+ 통합테스트 성공
+ 개인정보 마스킹 검증
+ ServiceId 등록 검증
+ SQL·DB 추적성 검증
+ 요구사항 추적률 100%
+ Drift 없음
+ HG-00~HG-90 통과
+ 사람 최종승인
+ 90-OUT 결과 패키징
20. 시사점
본 요구사항은 다음 자동 하네스 기능을 한 번에 검증할 수 있다.
14


===== PAGE 15 =====
비정형 요구사항 구조화
→ 인증 문맥과 업무조건 분리
→ ServiceId 기반 거래 설계
→ 6계층 프로그램 생성
→ MyBatis SQL 생성
→ 개인정보 마스킹 검증
→ Build·Test 자동화
→ 요구사항 추적성 검증
→ Gate와 사람 승인
조회 거래 Pilot이 성공하면 다음 순서로 확대하는 것이 적절하다.
자산평가 단건 조회
→ 자산평가 목록 조회
→ 자산평가 등록
→ 자산평가 변경
→ 자산평가 승인
→ 외부 평가기관 연계
21. 마무리말
REQ-AV-001 자산평가 내역 조회는 자동 하네스의 최초 검증 대상으로 적절하다.
기능은 단순하지만 요구사항, 인증, 권한, ServiceId, 프로그램 계층, SQL, 개인정보, 테스트, 증적과 Gate를 모두 포함
하므로 하네스의 전체 실행구조를 검증할 수 있다.
본 Pilot의 성공 기준은 소스가 생성되는 것이 아니다.
요구사항이
설계·코드·SQL·테스트·운영 기준정보와
정확하게 연결되고,
그 연결관계를
자동 검증 증적으로 증명하는 것
이 최종 완료 기준이다.
15