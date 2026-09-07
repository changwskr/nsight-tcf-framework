# -*- coding: utf-8 -*-
"""Augment PPT analysis MD with methodology crosswalk."""
from pathlib import Path
import re

base = Path(r"c:\Programming(23-08-15)\nsight-tcf-framework\ztcf-다이어리\2026-08-17-TCF 아키텍처 수행방법론")
ppt_dir = base / "00-PPT"
md_path = ppt_dir / "NSIGHT_아키텍처_정의서_통합본_20260825_분석.md"
text = md_path.read_text(encoding="utf-8")

def list_md(folder: str):
    p = base / folder
    return sorted(
        [
            f.name
            for f in p.glob("*.md")
            if " copy" not in f.name and not f.name.endswith(" - 원본.md")
        ]
    )


def link(folder: str, name: str) -> str:
    # relative from 00-PPT
    return f"[`{name}`](../{folder}/{name})"


vision = list_md("01-VISION")
big = list_md("02-BIG-PICTURE")
logical = list_md("03-LOGICAL")
physical = list_md("04-PHYSICAL")
mech = list_md("05-MECHANISM")
runtime = list_md("06-RUNTIME")

# helper to find by substring
def find(files, *keys):
    out = []
    for f in files:
        if all(k in f for k in keys):
            out.append(f)
    return out


crosswalk = []
crosswalk.append("## 0. 방법론(01~06) 교차 점검 요약")
crosswalk.append("")
crosswalk.append("> 본 절은 PPT 통합본 분석 결과를 `01-VISION`~`06-RUNTIME` 기존 분석·정책 문서와 대조한 **교차 점검**이다. PPT는 장표 원천, 방법론 폴더는 텍스트 상세·운영 게이트·구현 연계본으로 읽는다.")
crosswalk.append("")
crosswalk.append("| 방법론 폴더 | 문서 수(작업본) | PPT 대응 장 | 정합도 | 비고 |")
crosswalk.append("|---|---:|---|---|---|")
crosswalk.append(f"| [`01-VISION`](../01-VISION/) | {len(vision)} | 1장 개요·개편방향 | 높음 | 비전·NFR·SLA·HW/SW 개요 |")
crosswalk.append(f"| [`02-BIG-PICTURE`](../02-BIG-PICTURE/) | {len(big)} | 1.2~1.4 | 높음 | 도메인·분류·시스템그룹·전체구조 |")
crosswalk.append(f"| [`03-LOGICAL`](../03-LOGICAL/) | {len(logical)} | 2장 + 6장 IF | 높음 | 환경별 시스템·레이어드·IF 표준 |")
crosswalk.append(f"| [`04-PHYSICAL`](../04-PHYSICAL/) | {len(physical)} | 3~5장 | 높음 | HW/SW/DB/용량/호스트·포트 |")
crosswalk.append(f"| [`05-MECHANISM`](../05-MECHANISM/) | {len(mech)} | 8~9장 | 높음 | FW·거래·GUID·파일·SSO·형상배포 |")
crosswalk.append(f"| [`06-RUNTIME`](../06-RUNTIME/) | {len(runtime)} | 7장 | 높음 | 업무처리유형 6종 + 검증 |")
crosswalk.append("")
crosswalk.append("### PPT에만 약하거나 목차 Gap인 영역 → 방법론에서 보완")
crosswalk.append("")
crosswalk.append("| PPT Gap | 방법론에서 읽을 문서 |")
crosswalk.append("|---|---|")
crosswalk.append("| 10장 업무 솔루션(SELF-BI/OLAP/EBM) 독립 장표 약함 | `03-LOGICAL` BI/마케팅 레이어드·IF, `06-RUNTIME` 마케팅이벤트·데이터분석제공 |")
crosswalk.append("| 11장 모니터링·가용성·확장·DR·백업 약함 | `01-VISION` NFR/SLA, `04-PHYSICAL` HA-DR·인벤토리, `06-RUNTIME` NFR-VALIDATION |")
crosswalk.append("| 용량·Capacity 수치 표 | `04-PHYSICAL` 물리기술아키텍처_시스템용량_분석 |")
crosswalk.append("| 개발/운영 빌드·배포 파이프 | `05-MECHANISM` 형상배포·통합개발환경 |")
crosswalk.append("| 신용실적·비즈메타 상세 IF | `03-LOGICAL` BI포탈_신용실적 · 비즈메타 연관관계 |")
crosswalk.append("")
crosswalk.append("### 방법론에 풍부·PPT는 요약인 영역")
crosswalk.append("")
crosswalk.append("- **레이어드 아키텍처**(마케팅/데이터/BI/거버넌스/선도) — PPT 논리도 대비 방법론 문서가 계층·WAS·저장소까지 상세.")
crosswalk.append("- **인터페이스 표준 4종**(정보계/온라인/파일/데이터) — PPT 표 + 방법론 분석 MD가 쌍.")
crosswalk.append("- **메커니즘**(Upload/Download/RD/Inbound/SSO/Exception/거래로그) — PPT 9장 제목 나열, 방법론에 구조 분석 완비.")
crosswalk.append("- **런타임 유형 6종** — PPT 7.1과 `06-RUNTIME` 상세분석이 1:1.")
crosswalk.append("")
crosswalk.append("---")
crosswalk.append("")

# Detailed mapping section
map_sec = []
map_sec.append("## 3.A PPT 장 ↔ 방법론 문서 매핑")
map_sec.append("")
map_sec.append("### 1장 아키텍처 정의 → `01-VISION` + `02-BIG-PICTURE`")
map_sec.append("")
map_sec.append("| PPT 주제 | 권장 방법론 문서 |")
map_sec.append("|---|---|")
for f in find(vision, "VISION_개요") + find(vision, "VISION_차세대") + find(vision, "ARCHITECTURE") + find(vision, "NFR") + find(vision, "SLA"):
    map_sec.append(f"| 개요·비전·NFR | {link('01-VISION', f)} |")
for f in find(big, "어플리케이션도메인") + find(big, "어플리케이션분류") + find(big, "데이터주제") + find(big, "시스템그룹") + find(big, "전체시스템") + find(big, "최상위") + find(big, "주요시스템") + find(big, "구축방향") + find(big, "도메인개념"):
    map_sec.append(f"| 도메인·분류·구조 | {link('02-BIG-PICTURE', f)} |")
map_sec.append("")

map_sec.append("### 2장 논리 기술 + 6장 인터페이스 → `03-LOGICAL`")
map_sec.append("")
map_sec.append("| PPT 주제 | 권장 방법론 문서 |")
map_sec.append("|---|---|")
# environment docs
for key in ["운영환경", "DR환경", "개발환경", "선도"]:
    for f in find(logical, key)[:6]:
        map_sec.append(f"| {key} 시스템 구성 | {link('03-LOGICAL', f)} |")
for f in find(logical, "레이어드"):
    map_sec.append(f"| 논리 레이어드 | {link('03-LOGICAL', f)} |")
for f in find(logical, "기술컴포넌트"):
    map_sec.append(f"| 기술 컴포넌트 | {link('03-LOGICAL', f)} |")
for f in find(logical, "인터페이스"):
    map_sec.append(f"| 인터페이스 | {link('03-LOGICAL', f)} |")
for f in find(logical, "ZONE"):
    map_sec.append(f"| IT Zone | {link('03-LOGICAL', f)} |")
map_sec.append("")

map_sec.append("### 3~5장 물리·DB·시스템표준 → `04-PHYSICAL`")
map_sec.append("")
map_sec.append("| PPT 주제 | 권장 방법론 문서 |")
map_sec.append("|---|---|")
for f in find(physical, "하드웨어") + find(physical, "소프트웨어") + find(physical, "시스템용량") + find(physical, "시스템표준") + find(physical, "데이터베이스") + find(physical, "HA-DR") + find(physical, "PHYSICAL") + find(physical, "인벤토리")[:3]:
    map_sec.append(f"| 물리/DB/표준 | {link('04-PHYSICAL', f)} |")
# catch remaining important
for f in physical:
    if any(k in f for k in ["DB아키텍처", "DB이중화", "OGG", "OLTP", "호스트", "파일시스템", "사용자계정", "서버포트", "용량"]):
        map_sec.append(f"| 물리/DB/표준 | {link('04-PHYSICAL', f)} |")
# dedupe preserving order
seen = set()
deduped = []
for line in map_sec:
    if line.startswith("| 물리") and line in seen:
        continue
    if line.startswith("| 물리"):
        seen.add(line)
    deduped.append(line)
map_sec = deduped
map_sec.append("")

map_sec.append("### 7장 런타임 → `06-RUNTIME`")
map_sec.append("")
map_sec.append("| PPT 업무 처리 유형 | 권장 방법론 문서 |")
map_sec.append("|---|---|")
pairs = [
    ("전체/개요", "전체업무처리유형"),
    ("채널", "채널업무처리유형"),
    ("연계", "연계업무처리유형"),
    ("마케팅 이벤트", "마케팅이벤트"),
    ("데이터 분석/제공", "데이터분석제공"),
    ("파일 연계", "파일연계"),
    ("배치", "배치업무처리유형"),
]
for label, key in pairs:
    for f in find(runtime, key):
        map_sec.append(f"| {label} | {link('06-RUNTIME', f)} |")
for f in find(runtime, "VALIDATION") + find(runtime, "SCENARIOS") + find(runtime, "GAPS"):
    map_sec.append(f"| 검증·시나리오 | {link('06-RUNTIME', f)} |")
map_sec.append("")

map_sec.append("### 8~9장 표준화·구성 요소 → `05-MECHANISM`")
map_sec.append("")
map_sec.append("| PPT 주제 | 권장 방법론 문서 |")
map_sec.append("|---|---|")
mech_keys = [
    ("온라인 프레임워크", "온라인"),
    ("배치 프레임워크", "배치"),
    ("단말", "단말"),
    ("거래 처리", "거래"),
    ("GUID", "GUID"),
    ("캐릭터셋", "캐릭터"),
    ("전문", "전문"),
    ("File Upload", "업로드"),
    ("File Download", "다운로드"),
    ("RD", "RD"),
    ("Inbound", "INBOUND"),
    ("SSO", "SSO"),
    ("Exception", "예외"),
    ("거래로그", "거래로그"),
    ("통합개발환경", "통합개발"),
    ("형상배포", "형상배포"),
]
used = set()
for label, key in mech_keys:
    for f in find(mech, key):
        if f in used:
            continue
        used.add(f)
        map_sec.append(f"| {label} | {link('05-MECHANISM', f)} |")
map_sec.append("")
map_sec.append("---")
map_sec.append("")

# Update header note
header_old = """> **원본**: [`NSIGHT_아키텍처_정의서_통합본_20260825.pptx`](./NSIGHT_아키텍처_정의서_통합본_20260825.pptx)  
> **위치**: `ztcf-다이어리/2026-08-17-TCF 아키텍처 수행방법론/00-PPT/`  
> **분석일**: 2026-08-26  
> **규모**: 슬라이드 **163**장 · 파일 약 649KB  
> **원칙**: PPT에서 추출한 텍스트를 기준으로 장·절 구조를 재구성한다. 도식·이미지 전용 슬라이드는 텍스트가 빈약할 수 있다."""

header_new = """> **원본**: [`NSIGHT_아키텍처_정의서_통합본_20260825.pptx`](./NSIGHT_아키텍처_정의서_통합본_20260825.pptx)  
> **위치**: `ztcf-다이어리/2026-08-17-TCF 아키텍처 수행방법론/00-PPT/`  
> **분석일**: 2026-08-26 (방법론 01~06 교차 보완)  
> **규모**: 슬라이드 **163**장 · 파일 약 649KB  
> **원칙**: PPT 텍스트 추출 + `01-VISION`~`06-RUNTIME` 기존 분석 문서 교차 점검. 도식 전용 슬라이드는 텍스트가 빈약할 수 있으므로 방법론 MD를 병행한다.  
> **원본 스냅샷**: [`NSIGHT_아키텍처_정의서_통합본_20260825_분석 - 원본.md`](./NSIGHT_아키텍처_정의서_통합본_20260825_분석%20-%20원본.md)"""

if header_old in text:
    text = text.replace(header_old, header_new, 1)
else:
    # fallback soft update
    text = text.replace("**분석일**: 2026-08-26", "**분석일**: 2026-08-26 (방법론 01~06 교차 보완)", 1)

# Insert section 0 after first --- following title block
marker = "\n---\n\n## 1. 한 줄 요약"
if marker in text:
    text = text.replace(marker, "\n---\n\n" + "\n".join(crosswalk) + marker, 1)
else:
    text = "\n".join(crosswalk) + "\n" + text

# Insert 3.A after section 3 block (before ## 4.)
marker4 = "\n## 4. 장별 상세 분석"
if marker4 in text:
    # enhance section 3 table first
    old_table = """| 장 | 목차(PPT) | 주요 슬라이드 |
|---|---|---|
| 1 | 아키텍처 정의 | 3~22 |
| 2 | 논리 기술 아키텍처 | 23~62 |
| 3 | 물리 인프라 아키텍처 | 63~77 |
| 4 | 데이터베이스 아키텍처 | 78~83 |
| 5 | 시스템 표준 정의 | 84~89 |
| 6 | 인터페이스 아키텍처 | 90~100 |
| 7 | 런타임 아키텍처 | 101~108 |
| 8 | 아키텍처 표준화 | 109~138 (일부 반복) |
| 9 | 아키텍처 구성 요소 | 139~162 |
| 10 | 업무 솔루션 아키텍처 | 목차 표기(SELF-BI/OLAP/EBM/데이터흐름) — **본 PPT 후반에 독립 장표 약함** |
| 11 | 기타(모니터링·가용성·확장·DR·백업) | 목차 표기 — **본 PPT 본문 슬라이드 약함** |"""

    new_table = """| 장 | 목차(PPT) | 주요 슬라이드 | 방법론 폴더 |
|---|---|---|---|
| 1 | 아키텍처 정의 | 3~22 | `01-VISION` · `02-BIG-PICTURE` |
| 2 | 논리 기술 아키텍처 | 23~62 | `03-LOGICAL` |
| 3 | 물리 인프라 아키텍처 | 63~77 | `04-PHYSICAL` |
| 4 | 데이터베이스 아키텍처 | 78~83 | `04-PHYSICAL` (DB*) |
| 5 | 시스템 표준 정의 | 84~89 | `04-PHYSICAL` (시스템표준*) |
| 6 | 인터페이스 아키텍처 | 90~100 | `03-LOGICAL` (인터페이스*) |
| 7 | 런타임 아키텍처 | 101~108 | `06-RUNTIME` |
| 8 | 아키텍처 표준화 | 109~138 (일부 반복) | `05-MECHANISM` |
| 9 | 아키텍처 구성 요소 | 139~162 | `05-MECHANISM` |
| 10 | 업무 솔루션 아키텍처 | 목차만·본문 약함 | `03-LOGICAL` 레이어드/IF · `06-RUNTIME` 로 **보완 권고** |
| 11 | 기타(모니터링·가용성·확장·DR·백업) | 목차만·본문 약함 | `01-VISION` NFR/SLA · `04-PHYSICAL` HA-DR · `06-RUNTIME` NFR-VALIDATION |"""

    if old_table in text:
        text = text.replace(old_table, new_table, 1)

    text = text.replace(marker4, "\n" + "\n".join(map_sec) + marker4, 1)

# Strengthen section 5 conclusions
old_conc = """## 5. 핵심 결론

1. **환경 4축**: 운영 · DR · 개발 · 선도 를 동일 논리(마케팅/데이터/BI/거버넌스/IT지원)로 반복 기술한다.
2. **플랫폼 중심**: 마케팅플랫폼 · 데이터플랫폼 · BI포탈 · 데이터거버넌스가 논리·물리·IF·런타임의 공통 축이다.
3. **표준 계층**: 시스템 표준(호스트/FS/계정/포트) → 인터페이스 표준 → 거래/전문/GUID/캐릭터셋 → 프레임워크 구성요소로 내려간다.
4. **런타임 유형 6종**: 채널 · 연계 · 마케팅 이벤트 · 데이터 분석/제공 · 파일 연계 · 배치.
5. **프레임워크**: 단말 · 온라인 · 배치, Upload/Download · RD · Inbound · SSO · 선후처리 · Exception · 거래로그 · Master Solution · 상용 F/W 대비 기능.
6. **목차 Gap**: 10(업무 솔루션)·11(기타)은 목차에만 있고 본 PPT 슬라이드 밀도가 낮다 → 별도 장표/문서와 교차 확인 필요."""

new_conc = """## 5. 핵심 결론

1. **환경 4축**: 운영 · DR · 개발 · 선도 를 동일 논리(마케팅/데이터/BI/거버넌스/IT지원)로 반복 기술한다. → `03-LOGICAL` 환경별 시스템 구성 분석과 정합.
2. **플랫폼 중심**: 마케팅플랫폼 · 데이터플랫폼 · BI포탈 · 데이터거버넌스가 논리·물리·IF·런타임의 공통 축이다. → 레이어드·IF 분석 MD가 상세본.
3. **표준 계층**: 시스템 표준(호스트/FS/계정/포트) → 인터페이스 표준 → 거래/전문/GUID/캐릭터셋 → 프레임워크 구성요소로 내려간다. → `04-PHYSICAL` + `05-MECHANISM`.
4. **런타임 유형 6종**: 채널 · 연계 · 마케팅 이벤트 · 데이터 분석/제공 · 파일 연계 · 배치. → `06-RUNTIME` 상세분석과 1:1.
5. **프레임워크**: 단말 · 온라인 · 배치, Upload/Download · RD · Inbound · SSO · 선후처리 · Exception · 거래로그 · Master Solution · 상용 F/W 대비 기능. → `05-MECHANISM` 메커니즘 문서군.
6. **목차 Gap (방법론으로 닫기)**:
   - **10장 업무 솔루션**: PPT 약함 → BI/마케팅 레이어드·신용실적 IF·마케팅이벤트 런타임으로 보완.
   - **11장 기타(가용성·DR·백업)**: PPT 약함 → NFR/SLA · HA-DR · 물리 인벤토리 · NFR-VALIDATION으로 보완.
7. **읽기 순서 권고**: PPT 통합본(본 문서)으로 지형 파악 → 해당 장 방법론 MD로 심화 → `00-산출물정리` 게이트/런북으로 실행 증거 연결.
8. **중복·반복**: PPT 8장 표준화 블록이 두 번(약 110~123 / 125~138) 반복된다. 방법론에서는 단일 메커니즘 문서로 통합 관리하는 것이 좋다."""

if old_conc in text:
    text = text.replace(old_conc, new_conc, 1)
else:
    # try replace just the numbered list start
    text = re.sub(
        r"## 5\. 핵심 결론\n\n1\. \*\*환경 4축\*\*.*?6\. \*\*목차 Gap\*\*[^\n]*",
        new_conc.replace("## 5. 핵심 결론\n\n", "## 5. 핵심 결론\n\n"),
        text,
        count=1,
        flags=re.S,
    )

# Replace section 7 related docs
old_rel = """## 7. 관련 문서 (다이어리·방법론)

- [빅픽처 최상위아키텍처구성 전체시스템구조 분석](../02-BIG-PICTURE/빅픽처_최상위아키텍처구성_전체시스템구조_분석.md)
- [빅픽처 전체시스템아키텍처구조정의 상세분석](../02-BIG-PICTURE/빅픽처_전체시스템아키텍처구조정의_상세분석.md)
- [03-LOGICAL](../03-LOGICAL/) · [04-PHYSICAL](../04-PHYSICAL/) · [05-MECHANISM](../05-MECHANISM/) · [06-RUNTIME](../06-RUNTIME/)
- [2026-08-22 아키텍처이미지 원본 본석/분석](../../2026-08-22-아키텍처이미지%20원본%20본석/분석/)
- [README-index.md](../../../README-index.md)"""

new_rel = f"""## 7. 관련 문서 (다이어리·방법론)

### 허브

- [`01-VISION/`](../01-VISION/) ({len(vision)} md)
- [`02-BIG-PICTURE/`](../02-BIG-PICTURE/) ({len(big)} md)
- [`03-LOGICAL/`](../03-LOGICAL/) ({len(logical)} md)
- [`04-PHYSICAL/`](../04-PHYSICAL/) ({len(physical)} md)
- [`05-MECHANISM/`](../05-MECHANISM/) ({len(mech)} md)
- [`06-RUNTIME/`](../06-RUNTIME/) ({len(runtime)} md)
- [2026-08-22 아키텍처이미지 원본 본석/분석](../../2026-08-22-아키텍처이미지%20원본%20본석/분석/)
- [README-index.md](../../../README-index.md)

### 우선 진입 (장별)

| 장 | 우선 문서 |
|---|---|
| 1 | {link('01-VISION', find(vision,'VISION_개요')[0]) if find(vision,'VISION_개요') else '`01-VISION`'} · {link('02-BIG-PICTURE', find(big,'최상위')[0]) if find(big,'최상위') else '`02-BIG-PICTURE`'} |
| 2·6 | `03-LOGICAL` 레이어드* · 인터페이스* · 환경별 시스템 구성* |
| 3~5 | `04-PHYSICAL` 하드웨어/소프트웨어/DB/시스템표준/시스템용량 |
| 7 | `06-RUNTIME` 런타임아키텍처_*업무처리유형* |
| 8~9 | `05-MECHANISM` 온라인/배치 FW · GUID · 파일 · SSO · 형상배포 |"""

if old_rel in text:
    text = text.replace(old_rel, new_rel, 1)
else:
    # replace from ## 7. to ## 8.
    text = re.sub(
        r"## 7\. 관련 문서.*?(?=\n## 8\. )",
        new_rel + "\n\n",
        text,
        count=1,
        flags=re.S,
    )

# Add section 5.A checklist before section 6 if not present
checklist = """
## 5.A 교차 점검 체크리스트

- [ ] PPT 1장 개편 방향 ↔ `01-VISION` 차세대 개편 기본방향 문서 표현이 일치하는가?
- [ ] PPT 분류체계·시스템그룹 ↔ `02-BIG-PICTURE` 분류/그룹 분석과 코드·명칭이 맞는가?
- [ ] PPT 운영/DR/개발/선도 구성 ↔ `03-LOGICAL` 동명 분석 문서와 노드가 맞는가?
- [ ] PPT HW/SW/DB/표준 ↔ `04-PHYSICAL` 대응 분석·인벤토리와 수치/명칭이 맞는가?
- [ ] PPT IF 표준 표(온라인/파일/데이터) ↔ `03-LOGICAL` 인터페이스 표준 분석과 매체(APIM/FOS/CDC/ETL/GSE)가 맞는가?
- [ ] PPT 업무처리유형 6종 ↔ `06-RUNTIME` 상세분석 파일이 모두 존재하는가?
- [ ] PPT 프레임워크·연계 메커니즘 ↔ `05-MECHANISM` Upload/RD/Inbound/SSO/Exception 문서가 있는가?
- [ ] PPT 10·11장 Gap을 방법론 문서로 대체 읽기 경로가 합의되었는가?

---
"""

if "## 5.A 교차 점검 체크리스트" not in text:
    text = text.replace("\n## 6. 슬라이드 제목 전수 목록", checklist + "\n## 6. 슬라이드 제목 전수 목록", 1)

md_path.write_text(text, encoding="utf-8")
print("updated", md_path)
print("chars", len(text))

# sync copies
copies = [
    base.parent / "2026-08-22-아키텍처이미지 원본 본석" / "분석" / md_path.name,
    Path(r"c:\Programming(23-08-15)\nsight-tcf-framework\pdmg-architecture-methodology\2026-08-17-TCF 아키텍처 수행방법론\00-PPT") / md_path.name,
]
for c in copies:
    if c.parent.exists():
        # for analysis folder, adjust relative links slightly? keep same structure note
        c.write_text(text, encoding="utf-8")
        print("synced", c)
