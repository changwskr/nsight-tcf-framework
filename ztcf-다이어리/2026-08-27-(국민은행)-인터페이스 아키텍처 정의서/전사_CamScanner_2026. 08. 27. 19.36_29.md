# 대내 채널 통합(MCI) 유형별 처리 원칙 — Sync·Async_Response

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_29.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

**위치:** IV. 인터페이스 처리방안 / 3. 인터페이스 유형별 처리 원칙

**탭:** 상세원칙 및 지침 · 유형별 상세원칙(선택) · 전행 표준전문

Sync 거래는 동일 세션으로 요청전문과 응답전문 모두 처리하며, Async-Response 거래는 거래 요청 프로세스와 다른 별도 프로세스가 거래 응답을 수신함

**소제목:** Sync 거래, Async_Response 거래

### MCI 거래 설명

**4 Sync 거래**
- 채널 - MCI - 상품서비스계 구간의 거래 Path
- 동일 세션 기반으로 채널 요청 전문을 수신하고 응답전문을 송신

**5 Async_Response 거래**
- 채널 - MCI - 상품서비스계 구간의 거래 Path
- 채널 - MCI 구간은 비동기 거래, MCI - 상품서비스계 구간은 동기 거래
- 응답전문을 수신 후 MCI는 Push 송신 Adapter를 통해 채널로 강제 전송

## TEXT 구성도

```text
To-Be MCI 거래

4 Sync 거래
  채널(직원채널, 고객채널, …) ←Sync→ I/F(MCI, 전문관리) ←Sync→ 상품서비스
    상품서비스: 상품서비스, 지급결제, 여신지원, 대행/제휴, 상품서비스, 후처리
  요청/응답이 동일 경로(양방향)

5 Async_Response 거래
  채널(직원채널, 고객채널, … + TCP Receiver)
    --Async--> I/F(MCI, 전문관리 + Push 송신) --Sync--> 상품서비스
  응답: 상품서비스 → Push 송신 → 채널 TCP Receiver
```

- 원문 페이지: 22
