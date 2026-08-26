# NSIGHT 도메인별 거래처리 URL 처리 구조 분석

## 0. 문서 정보

| 항목 | 내용 |
|---|---|
| 문서명 | NSIGHT 도메인별 거래처리 URL 처리 구조 분석 |
| 분석 대상 | 사용자가 제공한 거래 처리 경로 이미지 5장 |
| 분석 관점 | Domain → URL → Path Prefix → L4 → WEB → WAS → WAR/Application |
| 상태 구분 | `FACT` = 이미지에서 직접 확인, `INTERPRETATION` = 구조 해석, `TO-CONFIRM` = 추가 확인 필요 |
| 작성 목적 | 도메인별 URL 처리 규칙과 실제 배포/라우팅 구조를 하나의 기준 문서로 정리 |

---

# 1. Executive Summary

제공된 자료에서 NSIGHT의 거래 URL 처리 구조는 크게 다음 **3가지 패턴**으로 구분된다.

1. **전용 도메인 + 단일 업무 Prefix + 서비스코드**
   - 미니싱글뷰: `ms.prod.nacf/ms/{서비스코드}`
   - 신용실적: `cr.prod.nacf/cr/{서비스코드}`

2. **공용 도메인 + 업무별 Prefix + 서비스코드**
   - 마케팅플랫폼: `mp.prod.nacf/{업무코드}/{서비스코드}`
   - 예: `/ic`, `/pc`, `/bc`, `/sa`, `/pd`, `/cm`, `/eb`, `/ss`, `/cs`, `/ct`, `/mg`
   - SSO 인증용 `/ws`

3. **패키지 전용 도메인 + 패키지 URL**
   - BI포탈: `pt.prod.nacf/{패키지 URL}`
   - OLAP: `oa.prod.nacf/{패키지 URL}`
   - Self-BI: `sb.prod.nacf/{패키지 URL}`

공통적인 네트워크 처리 경로는 다음과 같다.

```text
사용자 / 정보단말 / 통합업무시스템
        │
        │ HTTP URL
        ▼
도메인(FQDN) + Path
        │
        ▼
내부망 L4
        │
        ├───────────────┐
        ▼               ▼
      WEB #1          WEB #2
        │ \             / │
        │  \           /  │
        ▼   ▼         ▼   ▼
      WAS #1          WAS #2
        │               │
        ▼               ▼
WAR / Package Application
        │
        ▼
서비스코드 또는 패키지 기능
```

핵심적으로 **도메인은 시스템 경계를 선택하고, URL 첫 번째 Path Segment는 업무 Application/WAR를 선택하며, 마지막 `{서비스코드}`가 개별 거래를 식별하는 형태**로 해석할 수 있다.

단, 이미지에는 `{서비스코드}`가 프로젝트의 `ServiceId`와 완전히 동일한 값이라고 명시되어 있지는 않다. 따라서 이 문서에서는 **서비스코드와 ServiceId의 동일성은 `TO-CONFIRM`**으로 관리한다.

---

# 2. 전체 거래 처리 경로

## 2.1 전체 도메인 구조

```text
                           NSIGHT INTERNAL URL ROUTING

┌─────────────────────────────────────────────────────────────────────────┐
│ 사용자 / Client                                                         │
│                                                                         │
│  통합업무시스템     마케팅플랫폼 정보단말       BI포탈 정보단말          │
└────────┬──────────────────┬───────────────────────┬──────────────────────┘
         │                  │                       │
         │                  │                       │
         ▼                  ▼                       ▼

  ms.prod.nacf        mp.prod.nacf           cr.prod.nacf
  /ms/{서비스코드}    /{업무코드}/{서비스코드} /cr/{서비스코드}

         │                  │                       │
         ▼                  ▼                       ▼
      내부망 L4          내부망 L4               내부망 L4
         │                  │                       │
    ┌────┴────┐        ┌────┴────┐             ┌────┴────┐
    ▼         ▼        ▼         ▼             ▼         ▼
 MS WEB#1  MS WEB#2   MP WEB#1  MP WEB#2      CR WEB#1  CR WEB#2
    │ \       / │       │ \       / │           │ \       / │
    │  \     /  │       │  \     /  │           │  \     /  │
    ▼   ▼   ▼   ▼       ▼   ▼   ▼   ▼           ▼   ▼   ▼   ▼
 MS WAS#1 MS WAS#2    MP WAS#1 MP WAS#2        CR WAS#1 CR WAS#2
    │         │          │         │              │         │
    ▼         ▼          ▼         ▼              ▼         ▼
 /ms.war   /ms.war    업무별 WAR  업무별 WAR     /cr.war  /cr.war
                         + /ws.war                 + /ws.war
```

패키지 기반 업무는 별도 URL 구조를 사용한다.

```text
BI Portal Package UI
   │
   ├─ pt.prod.nacf/{패키지 URL}
   │       ↓
   │     내부망 L4
   │       ↓
   │     BI포탈 WEB #1/#2
   │       ↓
   │     BI포탈 WAS #1/#2
   │
   ├─ oa.prod.nacf/{패키지 URL}
   │       ↓
   │     내부망 L4
   │       ↓
   │     OLAP WEB #1/#2
   │       ↓
   │     OLAP WAS #1/#2
   │
   └─ sb.prod.nacf/{패키지 URL}
           ↓
         내부망 L4
           ↓
         Self-BI WEB #1/#2
           ↓
         Self-BI WAS #1/#2
```

---

# 3. 도메인별 URL / Application 매핑

| 구분 | 도메인 | URL Pattern | 1차 Path | 대상 시스템 | WAS 배포단위 | 판정 |
|---|---|---|---|---|---|---|
| 미니싱글뷰 | `ms.prod.nacf` | `http://ms.prod.nacf/ms/{서비스코드}` | `/ms` | 미니싱글뷰 | `/ms.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/ws/{서비스코드}` | `/ws` | SSO 인증 | `/ws.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/ic/{서비스코드}` | `/ic` | 통합고객 | `/ic.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/pc/{서비스코드}` | `/pc` | 개인고객 | `/pc.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/bc/{서비스코드}` | `/bc` | 기업고객 | `/bc.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/sa/{서비스코드}` | `/sa` | 상담·판매 | `/sa.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/pd/{서비스코드}` | `/pd` | 통합상품 | `/pd.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/cm/{서비스코드}` | `/cm` | 캠페인 | `/cm.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/eb/{서비스코드}` | `/eb` | EBM | `/eb.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/ss/{서비스코드}` | `/ss` | 영업지원 | `/ss.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/cs/{서비스코드}` | `/cs` | CS | `/cs.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/ct/{서비스코드}` | `/ct` | 컨텐츠 | `/ct.war` | FACT |
| 마케팅플랫폼 | `mp.prod.nacf` | `http://mp.prod.nacf/mg/{서비스코드}` | `/mg` | 메시지 | `/mg.war` | FACT |
| 신용실적 | `cr.prod.nacf` | `http://cr.prod.nacf/cr/{서비스코드}` | `/cr` | 신용실적 | `/cr.war` | FACT |
| 신용실적 | `cr.prod.nacf` | 인증 Application | `/ws` | SSO 인증 | `/ws.war` | FACT |
| BI포탈 | `pt.prod.nacf` | `http://pt.prod.nacf/{패키지 URL}` | Package URL | BI포탈 | 이미지 미표시 | FACT |
| OLAP | `oa.prod.nacf` | `http://oa.prod.nacf/{패키지 URL}` | Package URL | OLAP | 이미지 미표시 | FACT |
| Self-BI | `sb.prod.nacf` | `http://sb.prod.nacf/{패키지 URL}` | Package URL | Self-BI | 이미지 미표시 | FACT |

---

# 4. URL을 구성하는 3개의 논리 계층

이미지의 URL 구조를 논리적으로 분해하면 다음과 같다.

```text
http://mp.prod.nacf/ic/{서비스코드}
       └────┬─────┘ └┬┘ └────┬────┘
            │         │       │
            │         │       └─ L3. 거래 식별
            │         │          서비스코드
            │         │
            │         └───────── L2. Application / 업무 식별
            │                    ic = 통합고객
            │
            └─────────────────── L1. 시스템 Domain
                                 mp = 마케팅플랫폼
```

따라서 URL을 다음과 같이 모델링할 수 있다.

```text
URL
=
Protocol
+ System Domain
+ Application Prefix
+ Transaction Code
```

예:

```text
http
  +
mp.prod.nacf
  +
/ic
  +
/{서비스코드}
```

이는 **시스템 선택 → Application 선택 → 거래 선택**의 3단계 라우팅 모델로 해석할 수 있다.

---

# 5. 미니싱글뷰 거래 처리 경로

## 5.1 FACT

이미지에서 확인되는 URL은 다음과 같다.

```text
http://ms.prod.nacf/ms/{서비스코드}
```

WAS에는 다음 Application이 배포된다.

```text
/ms.war (미니싱글뷰)
```

물리 경로는 다음과 같다.

```text
미니싱글뷰
(통합업무시스템)
      │
      │ http://ms.prod.nacf/ms/{서비스코드}
      ▼
  내부망 L4
      │
   ┌──┴──┐
   ▼     ▼
WEB #1  WEB #2
   │ \   / │
   │  \ /  │
   ▼   X   ▼
WAS #1  WAS #2
   │       │
   ▼       ▼
/ms.war  /ms.war
```

## 5.2 INTERPRETATION

미니싱글뷰는 **전용 FQDN `ms.prod.nacf`와 단일 Application Prefix `/ms`**를 사용한다.

즉 URL 레벨에서는:

```text
ms.prod.nacf
   │
   └─ /ms
       │
       └─ {서비스코드}
```

와 같이 단순한 구조이다.

이 구조는 하나의 업무 Application을 별도 서비스 도메인으로 분리하여 운영하는 형태로 해석할 수 있다.

---

# 6. 마케팅플랫폼 거래 처리 경로

## 6.1 FACT

마케팅플랫폼은 하나의 도메인 아래 여러 업무 Application을 Path Prefix로 분리한다.

```text
http://mp.prod.nacf/ws/{서비스코드}
http://mp.prod.nacf/ic/{서비스코드}
http://mp.prod.nacf/pc/{서비스코드}
http://mp.prod.nacf/bc/{서비스코드}
http://mp.prod.nacf/sa/{서비스코드}
http://mp.prod.nacf/pd/{서비스코드}
http://mp.prod.nacf/cm/{서비스코드}
http://mp.prod.nacf/eb/{서비스코드}
http://mp.prod.nacf/ss/{서비스코드}
http://mp.prod.nacf/cs/{서비스코드}
http://mp.prod.nacf/ct/{서비스코드}
http://mp.prod.nacf/mg/{서비스코드}
```

각 WAS #1, #2에는 동일한 WAR 목록이 배포되어 있다.

```text
/ws.war  (SSO 인증)
/ic.war  (통합고객)
/pc.war  (개인고객)
/bc.war  (기업고객)
/sa.war  (상담 판매)
/pd.war  (통합상품)
/cm.war  (캠페인)
/eb.war  (EBM)
/ss.war  (영업지원)
/cs.war  (CS)
/ct.war  (컨텐츠)
/mg.war  (메시지)
```

## 6.2 전체 구조

```text
                 마케팅 플랫폼
                   정보 단말
                      │
                      │ mp.prod.nacf
                      ▼
                   내부망 L4
                      │
              ┌───────┴───────┐
              ▼               ▼
         MP WEB #1        MP WEB #2
              │ \             / │
              │  \           /  │
              ▼   ▼         ▼   ▼
         MP WAS #1        MP WAS #2
              │               │
              │               │
              ├─ /ws.war      ├─ /ws.war
              ├─ /ic.war      ├─ /ic.war
              ├─ /pc.war      ├─ /pc.war
              ├─ /bc.war      ├─ /bc.war
              ├─ /sa.war      ├─ /sa.war
              ├─ /pd.war      ├─ /pd.war
              ├─ /cm.war      ├─ /cm.war
              ├─ /eb.war      ├─ /eb.war
              ├─ /ss.war      ├─ /ss.war
              ├─ /cs.war      ├─ /cs.war
              ├─ /ct.war      ├─ /ct.war
              └─ /mg.war      └─ /mg.war
```

## 6.3 INTERPRETATION

마케팅플랫폼의 핵심은 **하나의 FQDN을 공유하면서 업무코드가 Application Routing Key 역할을 하는 구조**이다.

```text
mp.prod.nacf
    │
    ├─ /ws → ws.war
    ├─ /ic → ic.war
    ├─ /pc → pc.war
    ├─ /bc → bc.war
    ├─ /sa → sa.war
    ├─ /pd → pd.war
    ├─ /cm → cm.war
    ├─ /eb → eb.war
    ├─ /ss → ss.war
    ├─ /cs → cs.war
    ├─ /ct → ct.war
    └─ /mg → mg.war
```

따라서 외부 URL의 Path Prefix와 WAR Context가 거의 1:1 대응되는 구조로 볼 수 있다.

---

# 7. 신용실적 거래 처리 경로

## 7.1 FACT

신용실적 URL:

```text
http://cr.prod.nacf/cr/{서비스코드}
```

WAS Application:

```text
/ws.war (SSO 인증)
/cr.war (신용실적)
```

물리 처리 경로:

```text
BI 포탈
(정보 단말)
     │
     │ http://cr.prod.nacf/cr/{서비스코드}
     ▼
 내부망 L4
     │
 ┌───┴───┐
 ▼       ▼
신용실적  신용실적
WEB #1   WEB #2
 │ \       / │
 │  \     /  │
 ▼   ▼   ▼   ▼
신용실적  신용실적
WAS #1   WAS #2
 │         │
 ├─/ws.war ├─/ws.war
 └─/cr.war └─/cr.war
```

## 7.2 INTERPRETATION

신용실적은 미니싱글뷰와 유사하게 **전용 도메인 + 전용 업무 Prefix**를 사용한다.

다만 WAS에는 `/ws.war`가 함께 배포되어 있어, 이미지상으로는 SSO 인증 기능과 신용실적 업무 기능이 동일 WAS군에 공존한다.

---

# 8. BI포탈 / OLAP / Self-BI 패키지 URL 처리

## 8.1 FACT

패키지 UI 영역은 일반 업무 WAR와 달리 `{서비스코드}`가 아니라 `{패키지 URL}` 형식으로 표시되어 있다.

```text
BI포탈
http://pt.prod.nacf/{패키지 URL}

OLAP
http://oa.prod.nacf/{패키지 URL}

Self-BI
http://sb.prod.nacf/{패키지 URL}
```

각 경로는 독립된 L4와 WEB/WAS 이중화 구조를 가진다.

## 8.2 구조

```text
BI포탈 Package UI
      │
      ├──────────────────────────────────────────────┐
      │                                              │
      ▼                                              ▼

pt.prod.nacf/{패키지 URL}                    oa.prod.nacf/{패키지 URL}
      │                                              │
      ▼                                              ▼
   내부망 L4                                       내부망 L4
      │                                              │
  ┌───┴───┐                                      ┌───┴───┐
  ▼       ▼                                      ▼       ▼
PT WEB1  PT WEB2                                OA WEB1  OA WEB2
  │ \     / │                                    │ \     / │
  ▼  \   /  ▼                                    ▼  \   /  ▼
PT WAS1  PT WAS2                                OA WAS1  OA WAS2


                     sb.prod.nacf/{패키지 URL}
                                │
                                ▼
                             내부망 L4
                                │
                           ┌────┴────┐
                           ▼         ▼
                      Self-BI WEB1  WEB2
                           │ \       / │
                           ▼  \     /  ▼
                      Self-BI WAS1  WAS2
```

## 8.3 INTERPRETATION

이 영역은 **업무 Service Code 중심의 자체 Application URL 체계와 패키지 제품 URL 체계를 분리**한 것으로 해석할 수 있다.

즉:

```text
자체 구축 Application
Domain / 업무코드 / 서비스코드

Package Application
Domain / 패키지 URL
```

로 URL 정책이 이원화되어 있다.

---

# 9. 도메인 → Path → WAR 라우팅 모델

이미지 기준으로 라우팅 정책을 표준화하면 다음과 같이 표현할 수 있다.

| 단계 | 입력 | 라우팅 의미 |
|---|---|---|
| 1 | FQDN | 대상 시스템/서비스군 선택 |
| 2 | Path Prefix | 대상 Application/WAR 선택 |
| 3 | 서비스코드 | Application 내부 거래 선택 |
| 4 | L4 | WEB Node 선택 |
| 5 | WEB | WAS Node로 전달 |
| 6 | WAS | WAR/Application 실행 |
| 7 | Application | 서비스코드 기준 업무 처리 |

논리 흐름:

```text
Request
  │
  ▼
FQDN 확인
  │
  ├─ ms.prod.nacf
  ├─ mp.prod.nacf
  ├─ cr.prod.nacf
  ├─ pt.prod.nacf
  ├─ oa.prod.nacf
  └─ sb.prod.nacf
  │
  ▼
Path Prefix 확인
  │
  ├─ /ms
  ├─ /ic
  ├─ /pc
  ├─ /bc
  ├─ /sa
  ├─ /pd
  ├─ /cm
  ├─ /eb
  ├─ /ss
  ├─ /cs
  ├─ /ct
  ├─ /mg
  └─ /cr
  │
  ▼
Application / WAR 결정
  │
  ▼
{서비스코드}
  │
  ▼
개별 거래 실행
```

---

# 10. URL 예시별 처리 흐름

## 10.1 통합고객 거래 예시

```text
http://mp.prod.nacf/ic/{서비스코드}
```

처리 순서:

```text
1. Client
     │
2. mp.prod.nacf DNS/VIP
     │
3. 내부망 L4
     │
4. 마케팅플랫폼 WEB #1 또는 #2
     │
5. 마케팅플랫폼 WAS #1 또는 #2
     │
6. /ic.war
     │
7. {서비스코드}
     │
8. 통합고객 업무 처리
```

## 10.2 개인고객 거래 예시

```text
http://mp.prod.nacf/pc/{서비스코드}
```

```text
Client
  ↓
mp.prod.nacf
  ↓
L4
  ↓
MP WEB
  ↓
MP WAS
  ↓
/pc.war
  ↓
서비스코드
```

## 10.3 미니싱글뷰 거래 예시

```text
http://ms.prod.nacf/ms/{서비스코드}
```

```text
Client
  ↓
ms.prod.nacf
  ↓
L4
  ↓
MS WEB
  ↓
MS WAS
  ↓
/ms.war
  ↓
서비스코드
```

## 10.4 신용실적 거래 예시

```text
http://cr.prod.nacf/cr/{서비스코드}
```

```text
BI포탈 정보단말
  ↓
cr.prod.nacf
  ↓
L4
  ↓
신용실적 WEB
  ↓
신용실적 WAS
  ↓
/cr.war
  ↓
서비스코드
```

---

# 11. 이중화 및 장애 우회 구조

## 11.1 FACT

각 주요 시스템은 다음 구조를 가진다.

```text
L4
 │
 ├─ WEB #1
 └─ WEB #2

WEB #1 ──┬─> WAS #1
         └─> WAS #2

WEB #2 ──┬─> WAS #1
         └─> WAS #2
```

즉 WEB와 WAS가 1:1로 고정 연결된 것이 아니라 **교차 연결**되어 있다.

## 11.2 INTERPRETATION

이 구조의 목적은 WEB 또는 WAS 단일 노드 장애 시 동일 서비스가 다른 노드로 계속 전달될 수 있도록 하는 **2계층 이중화 구조**로 해석할 수 있다.

다만 다음 사항은 이미지에 표시되어 있지 않으므로 추가 확인이 필요하다.

- L4 부하분산 알고리즘
- Health Check 방식
- WEB → WAS 라우팅 방식
- WEB/WAS Active-Active 또는 Active-Standby 여부
- Session Affinity 여부
- WAS Session Replication 여부
- Failover 판단 기준

---

# 12. SSO 인증 Application 분석

## 12.1 FACT

마케팅플랫폼 WAS와 신용실적 WAS에는 다음 Application이 표시되어 있다.

```text
/ws.war (SSO 인증)
```

따라서 이미지 기준으로는 `/ws.war`가 **SSO 인증 기능을 담당하는 공통 Application**으로 사용된다.

## 12.2 TO-CONFIRM

다음 사항은 이미지 만으로 확정할 수 없다.

1. 모든 업무 거래가 `/ws.war`를 선행 호출하는지
2. SSO 토큰이 WEB/WAS에서 검증되는지
3. `/ws` URL이 인증 전용인지 인증+세션관리인지
4. 미니싱글뷰에도 동일 인증 모듈이 별도로 존재하는지
5. SSO 인증 결과가 세션 또는 토큰 중 어떤 방식으로 전달되는지

---

# 13. 핵심 아키텍처 특징

## 13.1 시스템 도메인과 업무 Application을 분리

```text
Domain
  ↓
시스템 서비스군 선택

Path
  ↓
업무 Application 선택

서비스코드
  ↓
개별 거래 선택
```

이 구조는 URL 자체에 **시스템 → 업무 → 거래** 계층이 표현되는 특징이 있다.

## 13.2 마케팅플랫폼은 Multi-WAR 구조

마케팅플랫폼 하나의 WAS에 여러 업무 WAR가 함께 배포되어 있다.

```text
Marketing WAS
   │
   ├─ ws.war
   ├─ ic.war
   ├─ pc.war
   ├─ bc.war
   ├─ sa.war
   ├─ pd.war
   ├─ cm.war
   ├─ eb.war
   ├─ ss.war
   ├─ cs.war
   ├─ ct.war
   └─ mg.war
```

즉 URL의 업무 Prefix와 WAR Context가 직접 연결되는 **Context-Path Routing Architecture**로 해석할 수 있다.

## 13.3 업무 시스템과 패키지 시스템의 URL 규칙이 다름

```text
업무 Application
/{업무코드}/{서비스코드}

Package
/{패키지 URL}
```

따라서 URL 관리 정책은 최소한 다음 2종을 구분해야 한다.

```text
STANDARD BUSINESS URL
PACKAGE URL
```

---

# 14. Architecture Risk / GAP

아래 내용은 이미지에 표시된 구조를 기준으로 한 검토 사항이다.

| ID | 항목 | 상태 | 설명 |
|---|---|---|---|
| URL-GAP-001 | 서비스코드와 ServiceId 관계 | TO-CONFIRM | 이미지에는 `{서비스코드}`만 명시됨 |
| URL-GAP-002 | HTTP / HTTPS 적용 기준 | TO-CONFIRM | 이미지 URL은 `http://`로 표시되지만 실제 운영 TLS 여부는 확인 필요 |
| URL-GAP-003 | L4 Health Check 기준 | TO-CONFIRM | Health Check URI/Port 미표시 |
| URL-GAP-004 | WEB → WAS 라우팅 방식 | TO-CONFIRM | Proxy/AJP/mod_jk 등 구현방식 미표시 |
| URL-GAP-005 | Session 처리 | TO-CONFIRM | Sticky/Replication/JDBC Session 여부 미표시 |
| URL-GAP-006 | SSO 호출 흐름 | TO-CONFIRM | `/ws.war`와 업무 WAR의 인증 연계 시퀀스 미표시 |
| URL-GAP-007 | URL Prefix와 WAR Context 고정관계 | TO-CONFIRM | 운영 설정에서 실제 1:1 매핑인지 확인 필요 |
| URL-GAP-008 | Package URL 표준 | TO-CONFIRM | 패키지별 URL 규칙이 이미지에 상세 미표시 |
| URL-GAP-009 | 장애 시 L4/WEB/WAS Failover | TO-CONFIRM | 이중화 구조는 보이나 Failover 정책 미표시 |
| URL-GAP-010 | Domain DNS/VIP 관리주체 | TO-CONFIRM | 도메인별 VIP 및 DNS 등록 기준 미표시 |

---

# 15. URL 표준화 관점의 분석

> 아래 내용은 이미지에 직접 적힌 사실이 아니라, 제공된 구조를 기준으로 한 아키텍처 해석 및 표준화 제안이다.

현재 URL 패턴은 다음 장점이 있다.

### 장점

1. **업무 식별이 명확함**
   - `/ic`, `/pc`, `/bc` 등으로 URL만 보고 대상 업무를 판단할 수 있다.

2. **WAR 단위 추적성이 높음**
   - `/ic` ↔ `/ic.war`처럼 URL과 배포단위가 직접 대응된다.

3. **운영 장애 분석이 쉬움**
   - 요청 URL로 대상 시스템/업무/WAR을 빠르게 좁힐 수 있다.

4. **라우팅 정책이 단순함**
   - Path Prefix 기반 WEB/WAS 라우팅 규칙을 구성하기 쉽다.

### 유의점

1. **외부 URL과 물리 WAR명이 과도하게 결합될 수 있음**
   - WAR 명칭 변경 또는 Application 통합/분리 시 URL 영향 가능

2. **동일 도메인에 WAR가 다수 존재**
   - 특정 WAR 과부하가 동일 WAS JVM 내 다른 WAR에 영향을 줄 가능성은 별도 Runtime 구조 확인 필요

3. **서비스코드 규칙의 중앙 관리 필요**
   - 업무 Prefix만으로는 개별 거래 추적이 불가능하므로 서비스코드 Registry가 필요

4. **패키지 URL과 자체 업무 URL의 Governance 분리 필요**
   - 자체 표준과 Package Vendor URL이 혼재할 수 있음

---

# 16. 권장 URL 관리 모델

다음 정보를 하나의 URL Registry로 관리하는 것이 적절하다.

| 관리항목 | 예시 |
|---|---|
| 환경 | PROD |
| 시스템 | Marketing Platform |
| Domain | `mp.prod.nacf` |
| Business Code | `IC` |
| URL Prefix | `/ic` |
| WAR | `ic.war` |
| 서비스코드 | 별도 Registry |
| WEB Pool | MP WEB #1/#2 |
| WAS Pool | MP WAS #1/#2 |
| 인증 | SSO |
| L4 VIP | 확인 필요 |
| Health Check URI | 확인 필요 |
| Session Policy | 확인 필요 |
| Timeout | 확인 필요 |
| 담당팀 | 확인 필요 |

권장 관리키:

```text
Environment
+ Domain
+ Path Prefix
+ Service Code
```

예:

```text
PROD
+ mp.prod.nacf
+ /ic
+ {서비스코드}
```

이를 통해 다음 추적성을 구성할 수 있다.

```text
URL
 ↓
Domain
 ↓
Application
 ↓
WAR
 ↓
서비스코드
 ↓
업무 프로그램
 ↓
Runtime
 ↓
WEB/WAS
 ↓
로그/장애
```

---

# 17. 최종 정리

제공된 거래 처리 경로를 기준으로 NSIGHT URL Architecture의 핵심은 다음과 같다.

```text
                    NSIGHT URL ARCHITECTURE

[1] Domain
    시스템 서비스군 선택

        ↓

[2] Path Prefix
    업무 Application / WAR 선택

        ↓

[3] 서비스코드
    개별 거래 선택

        ↓

[4] L4
    WEB Node 분산

        ↓

[5] WEB
    WAS Pool 전달

        ↓

[6] WAS
    WAR/Application 실행

        ↓

[7] 업무 거래 처리
```

도메인별로 보면:

```text
ms.prod.nacf
 └─ /ms/{서비스코드}
      └─ ms.war

mp.prod.nacf
 ├─ /ws/{서비스코드} → ws.war
 ├─ /ic/{서비스코드} → ic.war
 ├─ /pc/{서비스코드} → pc.war
 ├─ /bc/{서비스코드} → bc.war
 ├─ /sa/{서비스코드} → sa.war
 ├─ /pd/{서비스코드} → pd.war
 ├─ /cm/{서비스코드} → cm.war
 ├─ /eb/{서비스코드} → eb.war
 ├─ /ss/{서비스코드} → ss.war
 ├─ /cs/{서비스코드} → cs.war
 ├─ /ct/{서비스코드} → ct.war
 └─ /mg/{서비스코드} → mg.war

cr.prod.nacf
 └─ /cr/{서비스코드}
      ├─ cr.war
      └─ ws.war (SSO 인증)

pt.prod.nacf
 └─ /{패키지 URL}
      └─ BI포탈 Package

oa.prod.nacf
 └─ /{패키지 URL}
      └─ OLAP Package

sb.prod.nacf
 └─ /{패키지 URL}
      └─ Self-BI Package
```

따라서 이 자료는 단순 URL 목록이 아니라 **NSIGHT의 시스템 도메인, 업무 Application, 배포 WAR, 이중화 WEB/WAS 구조를 연결하는 Runtime Routing Baseline**으로 사용할 수 있다.

---

# 18. 후속 확인 항목

Baseline 확정을 위해 다음 자료가 추가로 확인되면 좋다.

- 실제 DNS / VIP 목록
- L4 Virtual Service / Pool / Health Check 설정
- Apache `httpd.conf` / `VirtualHost` / Proxy 설정
- Tomcat `server.xml`
- WAR Context Path 실제 설정
- 서비스코드 Registry 또는 ServiceId 목록
- SSO 인증 시퀀스
- Session / Cookie / Replication 정책
- Timeout 정책
- Package별 실제 URL 목록
- 운영/DR 도메인 및 전환 규칙

이 자료까지 연결하면 다음 수준까지 확장 가능하다.

```text
Domain
→ DNS
→ L4 VIP
→ WEB
→ Apache Routing
→ WAS
→ Tomcat Connector
→ WAR
→ 서비스코드
→ Handler/Service
→ DB/외부연계
→ Log/Trace
```

즉 **URL Architecture → Application Architecture → Runtime Architecture**의 End-to-End Traceability가 완성된다.
