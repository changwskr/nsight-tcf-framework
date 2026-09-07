# -*- coding: utf-8 -*-
"""Build markdown analysis from extracted PPTX text."""
from pathlib import Path
import re

extract = Path("_extract_NSIGHT_통합본_20260825.txt").read_text(encoding="utf-8")
parts = re.split(r"\n===== SLIDE (\d+) \([^\)]+\) =====\n", extract)
slides = {}
i = 1
while i < len(parts):
    num = int(parts[i])
    body = parts[i + 1]
    i += 2
    blocks = [b.strip() for b in body.split("\n---\n") if b.strip() and b.strip() != "(no text)"]
    slides[num] = blocks

# Chapter map by slide ranges from titles / TOC
chapters = [
    (1, 2, "목차", "전체 목차 (1~11장)"),
    (3, 3, "1.1 개요", "문서 목적·정의·적용 범위"),
    (4, 4, "1.1 개편 기본 방향", "차세대 정보계 개편 4대 방향"),
    (5, 5, "1.2 개념 아키텍처", "채널·정보계·유관 개념도"),
    (6, 6, "1.2 어플리케이션 도메인", "도메인 구성 정의"),
    (7, 12, "1.2 어플리케이션 분류 체계", "분류 체계 1/5~5/5"),
    (13, 18, "1.4 시스템 그룹·구조", "시스템 그룹 / 아키텍처 구조"),
    (19, 20, "1.3 데이터 주제영역", "주제영역 구성 정의"),
    (21, 22, "1.4 전체 시스템·서버", "전체 구조 정의·대상 서버 식별"),
    (23, 24, "목차(논리)", "논리 기술 아키텍처 진입"),
    (25, 31, "2.2 운영환경 시스템", "운영 구축 대상·플랫폼 구성"),
    (32, 34, "2.2 DR환경 시스템", "DR 구축 대상·플랫폼 구성"),
    (35, 40, "2.2 개발환경 시스템", "개발 구축 대상·플랫폼 구성"),
    (41, 44, "2.2 선도환경 시스템", "선도 구축 대상·플랫폼 구성"),
    (45, 58, "2.3 기술 컴포넌트", "공통·플랫폼별·IT지원 컴포넌트"),
    (59, 62, "2.4 논리 기술 아키텍처", "마케팅·데이터·BI·거버넌스 논리도"),
    (63, 63, "목차(물리)", "물리 인프라 진입"),
    (64, 70, "3.1 하드웨어 구성도", "운영/DR/개발/선도 HW"),
    (71, 76, "3.2 소프트웨어 구성도", "환경별 SW 구성"),
    (77, 77, "3.4 소프트웨어 목록", "SW 목록"),
    (78, 78, "목차(DB)", "데이터베이스 아키텍처 진입"),
    (79, 83, "4. 데이터베이스 아키텍처", "구성·이중화·OGG·OLTP/배치"),
    (84, 84, "목차(시스템표준)", "시스템 표준 진입"),
    (85, 89, "5. 시스템 표준 정의", "호스트·FS·계정·포트"),
    (90, 90, "목차(인터페이스)", "인터페이스 아키텍처 진입"),
    (91, 94, "6.1 인터페이스 표준", "정보계·온라인·파일·데이터 표준"),
    (95, 100, "6.2 인터페이스 구성도", "Context·플랫폼별 IF"),
    (101, 101, "목차(런타임)", "런타임 아키텍처 진입"),
    (102, 108, "7.1 업무 처리 유형", "채널·연계·이벤트·분석·파일·배치"),
    (109, 109, "목차(표준화)", "아키텍처 표준화 진입"),
    (110, 123, "8. 아키텍처 표준화 (1)", "단말·FW·거래·GUID·캐릭터셋"),
    (124, 138, "8. 아키텍처 표준화 (2/반복)", "표준화 장표 반복 블록"),
    (139, 139, "목차(구성요소)", "아키텍처 구성 요소 진입"),
    (140, 161, "9. 아키텍처 구성 요소", "단말·온라인·연계·예외·Master·상용FW 대비"),
    (162, 162, "9.3 배치 프레임워크", "배치 프레임워크"),
    (163, 163, "목차(말미)", "종료 목차"),
]

def summarize_blocks(blocks, limit=12):
    """Pick non-trivial bullets for summary."""
    picks = []
    skip_titles = {"목차", "1. 아키텍처 정의", "목적", "정의", "적용 범위"}
    for b in blocks:
        if b.startswith("[TABLE]"):
            rows = b.splitlines()[1:6]
            picks.append("표: " + " / ".join(r[:60] for r in rows[:3]))
            continue
        flat = " ".join(b.split())
        if len(flat) < 8:
            continue
        if flat in skip_titles:
            continue
        if flat.startswith("1. 아키텍처") and len(flat) < 40:
            continue
        picks.append(flat[:220])
        if len(picks) >= limit:
            break
    return picks

out_lines = []
out_lines.append("# NSIGHT 아키텍처 정의서 통합본 (20260825) — PPT 분석")
out_lines.append("")
out_lines.append("> **원본**: [`NSIGHT_아키텍처_정의서_통합본_20260825.pptx`](./NSIGHT_아키텍처_정의서_통합본_20260825.pptx)  ")
out_lines.append("> **위치**: `ztcf-다이어리/2026-08-17-TCF 아키텍처 수행방법론/00-PPT/`  ")
out_lines.append("> **분석일**: 2026-08-26  ")
out_lines.append("> **규모**: 슬라이드 **163**장 · 파일 약 649KB  ")
out_lines.append("> **원칙**: PPT에서 추출한 텍스트를 기준으로 장·절 구조를 재구성한다. 도식·이미지 전용 슬라이드는 텍스트가 빈약할 수 있다.")
out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 1. 한 줄 요약")
out_lines.append("")
out_lines.append("본 PPT는 차세대 정보계의 **Technical · Application · Data** 아키텍처를 한 권으로 묶은 **통합 정의서**다. ")
out_lines.append("개요·분류·시스템 구조부터 논리/물리/DB/표준/인터페이스/런타임/표준화/프레임워크 구성 요소까지를 **운용 환경(운영·DR·개발·선도)** 축으로 전개한다.")
out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 2. 문서 목적·적용 범위 (슬라이드 3)")
out_lines.append("")
out_lines.append("| 항목 | 내용 |")
out_lines.append("|---|---|")
out_lines.append("| 목적 | 인프라·개발·유관 담당자가 아키텍처를 이해하고 업무에 활용하도록 정보 제공 |")
out_lines.append("| 정의 | 앱 구성·분류, 운용환경별 논리/물리 단위·기술요소, 표준·동작 원리, 업무별 동작 원리 |")
out_lines.append("| Technical | 운용환경별 논리·물리 구성, 센터 구분, 백업·가용성 |")
out_lines.append("| Application | 구성·분류·동작 원리, 표준화 |")
out_lines.append("| Data | 데이터 주제영역 (상세 가이드는 영역별 문서 참조) |")
out_lines.append("")
out_lines.append("### 개편 기본 방향 (슬라이드 4)")
out_lines.append("")
out_lines.append("1. **고객 중심 서비스 강화** — 채널 연계, CX, UI/UX  ")
out_lines.append("2. **데이터 기반 의사결정 강화** — 실시간 수집·즉시 제공·현업 활용  ")
out_lines.append("3. **통합 정보 활용 기반** — 고객·상품·채널 통합 조회  ")
out_lines.append("4. **유연·안정 운영체계** — 표준 구조, 확장 플랫폼, 안정 운영  ")
out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 3. 전체 목차 ↔ 슬라이드 맵")
out_lines.append("")
out_lines.append("| 장 | 목차(PPT) | 주요 슬라이드 |")
out_lines.append("|---|---|---|")
out_lines.append("| 1 | 아키텍처 정의 | 3~22 |")
out_lines.append("| 2 | 논리 기술 아키텍처 | 23~62 |")
out_lines.append("| 3 | 물리 인프라 아키텍처 | 63~77 |")
out_lines.append("| 4 | 데이터베이스 아키텍처 | 78~83 |")
out_lines.append("| 5 | 시스템 표준 정의 | 84~89 |")
out_lines.append("| 6 | 인터페이스 아키텍처 | 90~100 |")
out_lines.append("| 7 | 런타임 아키텍처 | 101~108 |")
out_lines.append("| 8 | 아키텍처 표준화 | 109~138 (일부 반복) |")
out_lines.append("| 9 | 아키텍처 구성 요소 | 139~162 |")
out_lines.append("| 10 | 업무 솔루션 아키텍처 | 목차 표기(SELF-BI/OLAP/EBM/데이터흐름) — **본 PPT 후반에 독립 장표 약함** |")
out_lines.append("| 11 | 기타(모니터링·가용성·확장·DR·백업) | 목차 표기 — **본 PPT 본문 슬라이드 약함** |")
out_lines.append("")
out_lines.append("> 슬라이드 1~2 목차에 10·11장이 있으나, 추출 제목 기준으로는 **9장(프레임워크) 이후 배치로 종료**된다. 10·11장은 후속 보완 또는 별도 산출물로 본다.")
out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 4. 장별 상세 분석")
out_lines.append("")

for start, end, title, desc in chapters:
    out_lines.append(f"### {title} (슬라이드 {start}–{end})")
    out_lines.append("")
    out_lines.append(f"- **범위 요약**: {desc}")
    # gather picks across range
    picks = []
    for sn in range(start, end + 1):
        if sn not in slides:
            continue
        for p in summarize_blocks(slides[sn], limit=4):
            if p not in picks:
                picks.append(p)
            if len(picks) >= 10:
                break
        if len(picks) >= 10:
            break
    if picks:
        out_lines.append("- **추출 포인트**:")
        for p in picks:
            out_lines.append(f"  - {p}")
    else:
        out_lines.append("- **추출 포인트**: (텍스트 적음 — 도식 중심 슬라이드일 가능성)")
    out_lines.append("")

out_lines.append("---")
out_lines.append("")
out_lines.append("## 5. 핵심 결론")
out_lines.append("")
out_lines.append("1. **환경 4축**: 운영 · DR · 개발 · 선도 를 동일 논리(마케팅/데이터/BI/거버넌스/IT지원)로 반복 기술한다.")
out_lines.append("2. **플랫폼 중심**: 마케팅플랫폼 · 데이터플랫폼 · BI포탈 · 데이터거버넌스가 논리·물리·IF·런타임의 공통 축이다.")
out_lines.append("3. **표준 계층**: 시스템 표준(호스트/FS/계정/포트) → 인터페이스 표준 → 거래/전문/GUID/캐릭터셋 → 프레임워크 구성요소로 내려간다.")
out_lines.append("4. **런타임 유형 6종**: 채널 · 연계 · 마케팅 이벤트 · 데이터 분석/제공 · 파일 연계 · 배치.")
out_lines.append("5. **프레임워크**: 단말 · 온라인 · 배치, Upload/Download · RD · Inbound · SSO · 선후처리 · Exception · 거래로그 · Master Solution · 상용 F/W 대비 기능.")
out_lines.append("6. **목차 Gap**: 10(업무 솔루션)·11(기타)은 목차에만 있고 본 PPT 슬라이드 밀도가 낮다 → 별도 장표/문서와 교차 확인 필요.")
out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 6. 슬라이드 제목 전수 목록")
out_lines.append("")
out_lines.append("| # | 제목(첫 텍스트 블록) |")
out_lines.append("|---:|---|")
for sn in sorted(slides):
    title = slides[sn][0].split("\n")[0][:80] if slides[sn] else "(empty)"
    title = title.replace("|", "\\|")
    out_lines.append(f"| {sn} | {title} |")

out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 7. 관련 문서 (다이어리·방법론)")
out_lines.append("")
out_lines.append("- [빅픽처 최상위아키텍처구성 전체시스템구조 분석](../02-BIG-PICTURE/빅픽처_최상위아키텍처구성_전체시스템구조_분석.md)")
out_lines.append("- [빅픽처 전체시스템아키텍처구조정의 상세분석](../02-BIG-PICTURE/빅픽처_전체시스템아키텍처구조정의_상세분석.md)")
out_lines.append("- [03-LOGICAL](../03-LOGICAL/) · [04-PHYSICAL](../04-PHYSICAL/) · [05-MECHANISM](../05-MECHANISM/) · [06-RUNTIME](../06-RUNTIME/)")
out_lines.append("- [2026-08-22 아키텍처이미지 원본 본석/분석](../../2026-08-22-아키텍처이미지%20원본%20본석/분석/)")
out_lines.append("- [README-index.md](../../../README-index.md)")
out_lines.append("")
out_lines.append("---")
out_lines.append("")
out_lines.append("## 8. 추출 산출물")
out_lines.append("")
out_lines.append("| 파일 | 설명 |")
out_lines.append("|---|---|")
out_lines.append("| `_extract_NSIGHT_통합본_20260825.txt` | 슬라이드별 원문 텍스트 추출 |")
out_lines.append("| `_slide_titles.txt` | 슬라이드 번호·제목 목록 |")
out_lines.append("| `_extract_pptx.py` | 추출 스크립트 |")
out_lines.append("")
out_lines.append("> 본 분석 MD는 PPT 텍스트 추출 기반이다. 표·도식의 시각 레이아웃·색 강조는 원본 PPT를 병행 열람한다.")

Path("NSIGHT_아키텍처_정의서_통합본_20260825_분석.md").write_text(
    "\n".join(out_lines), encoding="utf-8"
)
print("wrote analysis md, lines=", len(out_lines))
