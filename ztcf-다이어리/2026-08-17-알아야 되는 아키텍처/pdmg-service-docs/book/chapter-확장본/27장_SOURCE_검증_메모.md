# 제27장 SOURCE 검증 메모

## 우선 근거
1. `chapter/27장.이미지로그와 GUID 추적.md`
2. `68.트랜잭션 이미지로그 다이어그램.md`
3. `69.GUID 아키텍처 다이어그램.md`
4. `16.Service Context-1.md`
5. `26.시스템후처리-1.md`
6. `22.Business-Service-1.md`, `23.DAO-1.md`, `24.DAO-Mapper-1.md`

## 주요 교정

### ImageLog DataSource
과거/다이어그램 일부: 별도 ImageLog JDBC/DB처럼 표현

재분석 current:
```text
ImageLogHandler(DataSource)
  ↓
@Primary rdwDataSource
```
따라서 별도 Resource가 아니라 같은 RDW Pool을 사용한다. 다만 pre/post 호출 위치가 업무 TX 밖이라 일반 실행에서 업무 Transaction과 commit/rollback을 공유하지 않는다.

### ImageLog 요청 저장
단순 INSERT가 아니다.
```text
UPDATE-first
→ 0건 INSERT
→ 동시 DuplicateKey면 UPDATE retry
```
GUID 재사용은 이전 response/exception을 초기화해 감사시도를 덮을 수 있다.

### 최종 오류 저장
처리된 예외는 `afterCompletion(ex==null)`일 수 있다.
```text
{hdr_nhnis,result}
→ postImagelog
→ result.stdErrCode
→ EXCEPTION_CODE
```
미처리 예외만 `exceptionImagelog(ex)`로 간다.

### 정상/오류 0-row 비대칭
- normal post UPDATE 0: warning, no INSERT
- exception persist UPDATE 0: INSERT

### Masking
현재 확인된 것은 wire 최대 20,000자 제한이다. password/token/PII field masking Source는 확인되지 않았다. 따라서 `truncate != masking`으로 작성했다.

### 관리 거래
`mgcoa8888S0/D0` 제외조건이 확인되지 않아 자기 ImageLog를 만든다. D0가 현재 요청 GUID를 삭제하면 normal post가 0 row로 끝나 실행흔적이 사라질 수 있다.

### GUID 외부전파
`69.GUID 아키텍처 다이어그램`은 외부시스템에 같은 GUID를 전달하는 권장구조를 제시한다. 실제 모든 Client 구현이 확인된 것은 아니므로 STANDARD/TO-BE로 표기했다.
