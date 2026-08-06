# Security Design — AV.AssetValuation.selectList

- 처리유형: 조회 전용 (readOnly TX)
- 인증: 기존 TCF/API Gateway 세션·JWT 정책 준수 (신규 예외 없음)
- 권한: AV 업무 API 호출 권한 (기존 av-service와 동일)
- 개인정보: 1차 목록 필드에 고객식별번호 미포함 설계
- Secret: 소스·설정에 비밀값 금지
- 감사: 프레임워크 거래 로그에 ServiceId 기록

> SECURITY_ARCHITECT 검토 요청 (HG-30 HUMAN)
