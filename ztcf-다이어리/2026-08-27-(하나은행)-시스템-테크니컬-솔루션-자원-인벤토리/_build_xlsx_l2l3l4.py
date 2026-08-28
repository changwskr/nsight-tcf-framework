# -*- coding: utf-8 -*-
"""Build TRM extension inventory xlsx from L2L3L4 보완 markdown."""
from __future__ import annotations

import re
from pathlib import Path

import xlsxwriter

DIR = Path(__file__).resolve().parent
MD = DIR / "하나은행_시스템_테크니컬_솔루션_자원_인벤토리_L2L3L4_보완.md"
OUT = DIR / "하나은행_시스템_테크니컬_솔루션_자원_인벤토리_L2L3L4_보완.xlsx"

COLS = [
    "Level1",
    "Level2",
    "Level3",
    "Level4",
    "제품명",
    "개발/공급자",
    "OSS/상용",
    "라이선스",
    "기술지원 여부",
    "컨테이너화",
    "CSP 서비스",
    "Cloud 사용",
    "TRM구분",
    "비고",
]

NEW_L2 = {
    "런타임",
    "배치처리",
    "서버리스",
    "보안",
    "데이터연계",
    "장애관리",
    "백업",
    "재해복구",
    "보안관제",
    "구성관리",
    "리포팅",
}

H2_RE = re.compile(
    r"^##\s+\d+\.\s+(개발환경|개발관리|실행환경|운영환경)\s+[—\-]\s+[★*]?\s*(.+?)\s*(?:\(|$)"
)


def split_row(line: str) -> list[str]:
    s = line.strip()
    if s.startswith("|"):
        s = s[1:]
    if s.endswith("|"):
        s = s[:-1]
    return [c.strip() for c in s.split("|")]


def is_sep(cells: list[str]) -> bool:
    return bool(cells) and all(re.fullmatch(r":?-{3,}:?", c.replace(" ", "")) for c in cells)


def parse_md(text: str) -> tuple[list[dict], list[list[str]]]:
    rows: list[dict] = []
    attach: list[list[str]] = []
    l1 = l2_sec = ""
    in_table = False
    headers: list[str] = []
    is_attach = False

    def flush_ctx():
        nonlocal in_table, headers, is_attach
        in_table = False
        headers = []
        is_attach = False

    for raw in text.splitlines():
        line = raw.rstrip()
        if line.startswith("## "):
            flush_ctx()
            m = H2_RE.match(line)
            if m:
                l1, rest = m.group(1), m.group(2).strip()
                l2_sec = rest.replace("★", "").strip()
            else:
                l1 = l2_sec = ""
            continue
        if line.startswith("#"):
            flush_ctx()
            continue
        if line.startswith("|") and "---" not in line:
            cells = split_row(line)
            if not in_table:
                headers = cells
                in_table = True
                is_attach = headers[:3] == ["이번 항목", "TRM 위치", "구분"]
                continue
            if is_attach:
                if len(cells) >= 3:
                    attach.append(cells[:3])
                continue
            if "제품명" not in headers:
                continue
            rec = dict(zip(headers, cells + [""] * (len(headers) - len(cells))))
            name = rec.get("제품명", "").strip()
            if not name:
                continue
            l3 = rec.get("Level3", "").strip()
            l4 = rec.get("Level4", "").strip()
            l2_cell = rec.get("Level2", "").strip()
            l2 = l2_sec
            if headers[0] == "Level2" and "Level3" not in headers:
                # 소스코드검사: L2=L2, L4=기술요소
                # 로깅/모니터링: 첫 칸이 실제 L3
                if l2_sec in ("로깅", "모니터링"):
                    l3 = l2_cell or l3
                    l2 = l2_sec
                else:
                    l2 = l2_cell or l2_sec
                    l3 = l3 or l2
            if l2_sec == "DBMS":
                l2 = "데이터"
            if l2_sec.startswith("로깅") or (l2_sec == "로깅"):
                l2 = "로깅"
            if l3 in ("설정", "서비스디스커버리"):
                l2 = "Backing Service"
            if l3 == "리포팅":
                l2 = "리포팅"
            trm = "신규L2" if l2 in NEW_L2 else "기존L2확장"
            rows.append(
                {
                    "Level1": l1,
                    "Level2": l2,
                    "Level3": l3,
                    "Level4": l4,
                    "제품명": name,
                    "개발/공급자": rec.get("개발/공급자", ""),
                    "OSS/상용": rec.get("OSS/상용", ""),
                    "라이선스": rec.get("라이선스", ""),
                    "기술지원 여부": rec.get("기술지원", rec.get("기술지원 여부", "")),
                    "컨테이너화": rec.get("컨테이너화", ""),
                    "CSP 서비스": rec.get("CSP 서비스", rec.get("CSP", "")),
                    "Cloud 사용": rec.get("Cloud 사용", rec.get("Cloud", "")),
                    "TRM구분": trm,
                    "비고": rec.get("비고", ""),
                }
            )
            continue
        if in_table and (not line.startswith("|")):
            flush_ctx()
    return rows, attach


def main() -> None:
    rows, attach = parse_md(MD.read_text(encoding="utf-8"))
    if not rows:
        raise SystemExit("no inventory rows parsed")

    wb = xlsxwriter.Workbook(str(OUT))
    title_fmt = wb.add_format(
        {"bold": True, "font_size": 16, "font_name": "맑은 고딕", "align": "left", "valign": "vcenter"}
    )
    warn_fmt = wb.add_format(
        {
            "bold": True,
            "font_size": 10,
            "font_name": "맑은 고딕",
            "font_color": "FFFFFF",
            "bg_color": "C00000",
            "align": "center",
            "valign": "vcenter",
        }
    )
    note_fmt = wb.add_format(
        {
            "bold": True,
            "font_size": 10,
            "font_name": "맑은 고딕",
            "font_color": "7F6000",
            "bg_color": "FFF2CC",
            "align": "left",
            "valign": "vcenter",
            "text_wrap": True,
        }
    )
    label_fmt = wb.add_format(
        {
            "bold": True,
            "font_name": "맑은 고딕",
            "font_size": 10,
            "bg_color": "F2F2F2",
            "border": 1,
            "valign": "vcenter",
        }
    )
    value_fmt = wb.add_format(
        {
            "font_name": "맑은 고딕",
            "font_size": 10,
            "border": 1,
            "valign": "vcenter",
            "text_wrap": True,
        }
    )
    hdr_fmt = wb.add_format(
        {
            "bold": True,
            "font_name": "맑은 고딕",
            "font_size": 9,
            "bg_color": "595959",
            "font_color": "FFFFFF",
            "align": "center",
            "valign": "vcenter",
            "text_wrap": True,
            "border": 1,
        }
    )
    cell = wb.add_format(
        {"font_name": "맑은 고딕", "font_size": 9, "valign": "vcenter", "border": 1, "text_wrap": True}
    )
    cell_c = wb.add_format(
        {"font_name": "맑은 고딕", "font_size": 9, "align": "center", "valign": "vcenter", "border": 1}
    )
    oss_fmt = wb.add_format(
        {
            "font_name": "맑은 고딕",
            "font_size": 9,
            "align": "center",
            "valign": "vcenter",
            "border": 1,
            "bg_color": "E2EFDA",
        }
    )
    comm_fmt = wb.add_format(
        {
            "font_name": "맑은 고딕",
            "font_size": 9,
            "align": "center",
            "valign": "vcenter",
            "border": 1,
            "bg_color": "FCE4D6",
        }
    )
    new_l2_fmt = wb.add_format(
        {
            "font_name": "맑은 고딕",
            "font_size": 9,
            "align": "center",
            "valign": "vcenter",
            "border": 1,
            "bg_color": "FFFF00",
            "bold": True,
        }
    )
    ext_fmt = wb.add_format(
        {
            "font_name": "맑은 고딕",
            "font_size": 9,
            "align": "center",
            "valign": "vcenter",
            "border": 1,
            "bg_color": "FFF2CC",
        }
    )
    l1_colors = {
        "개발환경": "D6EAF8",
        "개발관리": "D5F5E3",
        "실행환경": "FCF3CF",
        "운영환경": "FADBD8",
    }
    l1_fmts = {
        k: wb.add_format(
            {
                "font_name": "맑은 고딕",
                "font_size": 9,
                "align": "center",
                "valign": "vcenter",
                "border": 1,
                "bg_color": c,
                "bold": True,
            }
        )
        for k, c in l1_colors.items()
    }

    n_new = sum(1 for r in rows if r["TRM구분"] == "신규L2")
    n_ext = len(rows) - n_new

    ws0 = wb.add_worksheet("안내")
    ws0.hide_gridlines(2)
    ws0.set_column("A:A", 22)
    ws0.set_column("B:B", 96)
    ws0.set_row(0, 28)
    ws0.merge_range("A1:B1", "시스템 테크니컬 솔루션 자원 인벤토리 — TRM 확장(안)", title_fmt)
    ws0.merge_range("A2:B2", "본 문서는 하나은행의 자산입니다. 대외 반출시 각별한 주의를 요망합니다.", warn_fmt)
    ws0.merge_range(
        "A3:B3",
        "TRM 확장 제안(FACT 아님). 원본 Check List와 섞어 쓰지 말 것. 노란색 = 신규 L2 TRM 항목.",
        note_fmt,
    )
    ws0.set_row(2, 28)
    meta = [
        ("문서", "OSS TRM Inventory 확장(안) — L2·L3·L4 보완"),
        ("기준", "원본 Check List: OSS TRM Inventory + CSP 특성 반영"),
        ("원본 FACT", "하나은행_시스템_테크니컬_솔루션_자원_인벤토리.md / .xlsx"),
        ("행 수", f"{len(rows)} (기존L2확장 {n_ext} / 신규L2 {n_new})"),
        ("시트", "인벤토리 = 제품 목록 / 붙이는위치 = 원본 TRM 삽입 위치 / 컬럼정의 = 범례"),
        ("표시", "신규L2 행의 Level2·TRM구분 = 노란색 (원본 헤더의 노란색 TRM 추가 항목과 동일)"),
    ]
    for i, (k, v) in enumerate(meta, start=5):
        ws0.write(i, 0, k, label_fmt)
        ws0.write(i, 1, v, value_fmt)
        ws0.set_row(i, 22)

    ws1 = wb.add_worksheet("컬럼정의")
    ws1.hide_gridlines(2)
    ws1.set_column("A:A", 18)
    ws1.set_column("B:B", 96)
    ws1.write_row(0, 0, ["컬럼", "헤더 원문·범례"], hdr_fmt)
    defs = [
        ("Level1~4", "계층 분류 (개발환경 / 개발관리 / 실행환경 / 운영환경)"),
        ("제품명", "제품명"),
        ("개발/공급자", "개발/공급자"),
        ("OSS/상용", "OSS/상용 여부"),
        ("라이선스", "OSS의 License 구분 (ex. Apache 2.0, MIT License)"),
        ("기술지원 여부", "상용: 상용소프트웨어 / community: community 지원 가능 / prof/community: 전문업체 지원 및 community 지원 가능"),
        ("컨테이너화", "○"),
        ("CSP 서비스", "제공 CSP 명시"),
        ("Cloud 사용", "○ : private / public cloud 모두 사용 가능  /  public : public 클라우드 사용"),
        ("TRM구분", "기존L2확장 = 원본 L2 아래 L3/L4 추가  /  신규L2 = 원본에 없는 L2 (노란색 TRM)"),
        ("비고", "추가 사유·원본과의 정합"),
    ]
    for i, (k, v) in enumerate(defs, start=1):
        ws1.write(i, 0, k, label_fmt)
        ws1.write(i, 1, v, value_fmt)
        ws1.set_row(i, 28)
    ws1.freeze_panes(1, 0)

    ws2 = wb.add_worksheet("붙이는위치")
    ws2.hide_gridlines(2)
    ws2.set_column("A:A", 42)
    ws2.set_column("B:B", 42)
    ws2.set_column("C:C", 22)
    ws2.write_row(0, 0, ["이번 항목", "TRM 위치", "구분"], hdr_fmt)
    ws2.freeze_panes(1, 0)
    for i, rec in enumerate(attach, start=1):
        for ci, val in enumerate(rec):
            fmt = new_l2_fmt if "신규 L2" in val.replace("**", "") else cell
            ws2.write(i, ci, val.replace("**", ""), fmt)
        ws2.set_row(i, 20)

    ws = wb.add_worksheet("인벤토리")
    ws.hide_gridlines(2)
    widths = [12, 18, 28, 26, 36, 28, 12, 28, 16, 12, 26, 12, 14, 52]
    for i, w in enumerate(widths):
        ws.set_column(i, i, w)
    ws.set_row(0, 36)
    ws.write_row(0, 0, COLS, hdr_fmt)
    ws.freeze_panes(1, 5)
    ws.autofilter(0, 0, len(rows), len(COLS) - 1)
    ws.repeat_rows(0)
    ws.set_landscape()
    ws.set_paper(8)
    ws.fit_to_pages(1, 0)
    ws.set_header("&C시스템 테크니컬 솔루션 자원 인벤토리 — TRM 확장(안)")
    ws.set_footer("&L하나은행 자산 · 대외 반출 주의 · FACT 아님&R&P / &N")

    for ri, row in enumerate(rows, start=1):
        ws.set_row(ri, 32 if row["비고"] else 18)
        for ci, col in enumerate(COLS):
            val = row.get(col, "")
            if col == "Level1":
                ws.write(ri, ci, val, l1_fmts.get(val, cell_c))
            elif col == "Level2" and row["TRM구분"] == "신규L2":
                ws.write(ri, ci, val, new_l2_fmt)
            elif col == "TRM구분":
                ws.write(ri, ci, val, new_l2_fmt if val == "신규L2" else ext_fmt)
            elif col == "OSS/상용":
                fmt = oss_fmt if val == "OSS" else (comm_fmt if val == "상용" else cell_c)
                ws.write(ri, ci, val, fmt)
            elif col in ("컨테이너화", "Cloud 사용", "기술지원 여부"):
                ws.write(ri, ci, val, cell_c)
            else:
                ws.write(ri, ci, val, cell)

    ws.conditional_format(
        1,
        11,
        len(rows),
        11,
        {
            "type": "text",
            "criteria": "containing",
            "value": "public",
            "format": wb.add_format(
                {
                    "bg_color": "D6EAF8",
                    "font_name": "맑은 고딕",
                    "font_size": 9,
                    "align": "center",
                    "border": 1,
                }
            ),
        },
    )

    wb.close()
    print(f"wrote {OUT} rows={len(rows)} attach={len(attach)}")
    from collections import Counter

    print("L1", dict(Counter(r["Level1"] for r in rows)))
    print("TRM", dict(Counter(r["TRM구분"] for r in rows)))


if __name__ == "__main__":
    main()
