# G20 Execution Result — Big Picture / Logical

## 결과

- STEP 04 Big Picture: 완료
- STEP 05 Logical Architecture: 완료
- G20: **CONDITIONAL PASS**
- 다음 단계: **G30 Physical Architecture**

## 핵심 확정

1. NSIGHT 전체를 10개 책임 Zone으로 재구성했다.
2. `MP/RD/AD/DG/BL/IM`은 시스템 관리축, Architecture Zone은 책임축으로 분리했다.
3. ServiceId를 Logical Architecture의 핵심 거래/추적 Key로 유지한다.
4. Domain 간 연계는 공개 ServiceId/Contract를 사용하고 타 Domain DAO/Mapper/Table 직접 접근을 금지한다.
5. Cross-Domain HTTP 호출은 별도 Local Transaction이며 멱등성/보상/재처리를 적용한다.
6. WEB Server/Apache, WAS Server/Tomcat JVM, Application/WAR를 독립 관리단위로 분리한다.
7. G30에서 71대 서버와 Logical Architecture를 실제 매핑한다.
