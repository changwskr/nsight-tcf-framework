# EOS Service DTO Spec (M11)

> 패키지: `nhnis.eos.co.a.dto`  
> 명명: `eoscoaNNNN{S0|C0|U0|…}DTO{in|out}`  
> 타입: FW `DataObject` + FieldProperty (문자열 중심, 목록은 List\<out\> 또는 nested)  
> 서버전용: Client 전송값이 있어도 서버가 재계산·덮어씀

범례: **S**=서버산출, **R**=필수요청, **O**=옵션

---

## 1. Dashboard — `eoscoa0110S0`

### in
| Field | R/O | 설명 |
|-------|-----|------|
| orgCd | O | 조직 필터 |
| envCd | O | 환경 |
| asOfYmd | O | 기준일(기본 오늘) **미사용 시 서버오늘** |

### out
| Field | 설명 |
|-------|------|
| totalCnt **S** | 총 자원 |
| criticalCnt **S** | |
| highCnt **S** | |
| mediumCnt **S** | |
| lowCnt **S** | |
| riskStatusCnt **S** | KPI「위험상태」(POLICY) |
| exceptionNeedCnt **S** | 예외필요 |
| actionInProgressCnt **S** | 조치중 |
| topPriorityList **S** | List: resourceId, name, grade, status, remainDays |
| kpiPolicyId **S** | 적용 정책 |

---

## 2. Resource List — `eoscoa0120S0`

### in
| Field | R/O |
|-------|-----|
| resourceName | O (like) |
| resourceTypeCd | O |
| envCd / orgCd / eosStatusCd / riskGradeCd | O |
| exceptionActiveYn | O |
| productId / versionId | O |
| pageNo / pageSize | R (default) |

### out (row)
| Field | 설명 |
|-------|------|
| resourceId, resourceName | |
| productName, versionNo | join |
| envCd, orgCd | |
| eosStatusCd, riskGradeCd | |
| exceptionActiveYn | |
| remainDays **S** | |
| ownerUserId | |

`0120E0`: 동일 in, fileToken/url out (P1 구현 가능).

---

## 3. Resource Detail — `eoscoa0130S0/C0/U0/D0`

### 0130S0 in
| resourceId | R |

### 0130S0 out
헤더: resource* + product/version/lfc(eosYmd,eolYmd) + remainDays**S** + 최신 risk 요약 + active exception 요약  
탭 요약 카운트: riskHistCnt, actionCnt, excCnt

### 0130C0 in
| Field | R/O |
|-------|-----|
| resourceName | R |
| versionId | R |
| envCd | R |
| centerCd, hostName, ipAddr, nsightAreaCd, orgCd, ownerUserId, remark | O |

**out:** resourceId **S**, eosStatusCd **S**

### 0130U0 in
resourceId **R** + 변경 가능 필드( versionId 포함 시 상태 재계산 **S** )

### 0130D0 in
| resourceId | R |
| disposeReason | R |

---

## 4. Lifecycle — `eoscoa0140S0/C0/U0/U1`

### 0140S0 in
productId / versionId / resourceTypeCd / page*

### out row
productId, productName, versionId, versionNo, eosYmd, eolYmd, effectiveFromDtm, evidenceId

### 0140C0 / U0 in
| Field | R/O |
|-------|-----|
| versionId | R |
| gaYmd, eosYmd, eolYmd | O (최소 하나 정책) |
| evidenceId / sourceDesc | O/R 정책 |
| changeReason | R (U0) |

### 0140U1 in
0140U0 필드 + `recalcInstanceYn` (default Y)

### 0140U1 out
| affectedResourceCnt **S** | |
| statusChangedCnt **S** | |

---

## 5. Risk — `eoscoa0150S0/C0`

### 0150S0 in
| resourceId | R |

### out
current: assessId, totalScore**S**, riskGradeCd**S**, assessorId, assessDtm, commentTxt, scores[] {itemCd, score}  
history[]: 동일 요약

### 0150C0 in
| Field | R/O |
|-------|-----|
| resourceId | R |
| scoreBiz, scoreEnv, scoreExp, scoreSec, scoreImp, scoreAlt, scoreEos | R (1~5) |
| commentTxt | O |
| ~~totalScore~~ / ~~riskGradeCd~~ | **무시** |

### out
assessId **S**, totalScore **S**, riskGradeCd **S**, followUpActionYn **S**, followUpExceptionYn **S**

---

## 6. Action — `eoscoa0160*` / `0165U0`

### 0160S0 in
resourceId **R** | actionId O

### 0160C0 / U0 in (주요)
| Field | R/O |
|-------|-----|
| resourceId | R (C0) |
| actionId | R (U0) |
| actionTypeCd | R |
| curVersionId, tgtVersionId | O/R 유형별 |
| detailTxt, impactTxt, testPlanTxt | R |
| prereqTxt | O |
| cutoverTypeCd, outageYn | R |
| offhoursYn, drVerifyYn | O |
| rollbackCondTxt, rollbackProcTxt, rollbackTargetTxt | R |
| orgCd, ownerUserId | R |
| planStartYmd, planEndYmd | R |
| issueTxt | O |

### 0160U1 in
| actionId | R |
| toStatusCd | R |
| reasonTxt | O |

### 0160U2 in
| actionId | R |
| actualEndYmd | R |
| evidenceIds[] | R |
| verifyRequestComment | O |

### 0165U0 in
| actionId | R |
| approveYn | R (Y/N) |
| verifyResultCd | R |
| verifyComment | O |

**out 공통:** actionId, statusCd **S**

---

## 7. Exception — `0170*` / `0180*`

### 0170C0 in
| Field | R/O |
|-------|-----|
| resourceId | R |
| startYmd, endYmd | R |
| reasonTxt, blockerTxt, mitigationTxt | R |
| finalPlanTxt, finalTargetYmd | R |
| monthlyCheckYn | R |
| exitCriteriaTxt | R |

**out:** excReqId **S**, statusCd **S**  
reqOrgCd / reqUserId / reqDtm → **S**(세션)

### 0180U0 in
| Field | R/O |
|-------|-----|
| excReqId | R |
| decisionCd | R (APPROVE/CONDITIONAL/REJECT) |
| conditionTxt | 조건부 R |
| rejectReasonTxt | 반려 R |

**out:** apprId **S**, exceptionActiveYn **S** (자원)

### 0180U1 in
| Field | R/O |
|-------|-----|
| baseApprId | R |
| newStartYmd, newEndYmd | R |
| extendReasonTxt | R |

---

## 8. Monthly Check — `0190S0/C0`

### 0190S0 in
checkYm O, orgCd O, checkStatusCd O (MISS/DONE)

### 0190C0 in
| excReqId, checkYm | R |
| mitigationOkYn | R |
| residualRiskTxt, planProgressTxt, issueTxt | O |
| nextCheckYmd | O |

---

## 9. Policy/Code — `0141S0/C0/U0`

### Code
grpId, code, codeName, sortOrd, useYn, validFromYmd, validToYmd

### Policy
policyTypeCd, policyKey, policyVal, effectiveFromDtm, changeReason

---

## 10. Audit — `0151S0`

### in
fromDtm, toDtm, entityType, entityId, userId, actionCd, traceId, page*

### out row
histId, traceId, userId, orgCd, serviceId, entityType, entityId, actionCd, resultCd, reasonTxt, eventDtm  
detail: beforeJson, afterJson (상세 조회사)

---

## 11. Nested / List 규칙

- 목록 out: `list` + `totalCount` + `pageNo` + `pageSize`
- 코드성 필드는 `*Cd`, 일자는 `*Ymd`, 일시는 `*Dtm`
- Boolean성: `*Yn` (`Y`/`N` 문자열)
