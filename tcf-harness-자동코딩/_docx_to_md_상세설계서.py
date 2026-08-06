# -*- coding: utf-8 -*-
"""Convert NSIGHT 상세설계서.docx to Markdown with document-order tables."""
from __future__ import annotations

from pathlib import Path

from docx import Document
from docx.document import Document as DocumentObject
from docx.oxml.ns import qn
from docx.table import Table
from docx.text.paragraph import Paragraph


def iter_block_items(parent: DocumentObject):
    body = parent.element.body
    for child in body.iterchildren():
        if child.tag == qn("w:p"):
            yield Paragraph(child, parent)
        elif child.tag == qn("w:tbl"):
            yield Table(child, parent)


def escape_cell(text: str) -> str:
    t = (text or "").replace("\r\n", "\n").replace("\r", "\n")
    t = " / ".join(part.strip() for part in t.split("\n") if part.strip())
    t = t.replace("|", "\\|")
    return t.strip()


def table_to_md(table: Table) -> list[str]:
    rows = []
    for row in table.rows:
        cells = [escape_cell(c.text) for c in row.cells]
        # collapse consecutive duplicate merged cells visually still OK as same text
        rows.append(cells)
    if not rows:
        return []
    width = max(len(r) for r in rows)
    norm = [r + [""] * (width - len(r)) for r in rows]
    # drop fully empty trailing rows
    while norm and all(not c for c in norm[-1]):
        norm.pop()
    if not norm:
        return []
    header = norm[0]
    out = [
        "| " + " | ".join(header) + " |",
        "| " + " | ".join("---" for _ in header) + " |",
    ]
    for r in norm[1:]:
        out.append("| " + " | ".join(r) + " |")
    return out


def is_code_like(text: str) -> bool:
    if "\n" in text:
        return True
    markers = (
        "→",
        "├",
        "└",
        "│",
        "┌",
        "▼",
        "↓",
        "{",
        "}",
        "workflowId:",
        "ruleId:",
        "checkpointId:",
        "idempotencyKey",
        "Quality Score",
        "UPDATE ",
        "sha256:",
    )
    return any(m in text for m in markers)


def convert(docx_path: Path, md_path: Path) -> None:
    doc = Document(str(docx_path))
    lines: list[str] = []
    title_done = False
    in_code = False
    pending_bullets: list[str] = []

    def flush_bullets():
        nonlocal pending_bullets
        if pending_bullets:
            lines.append("")
            lines.extend(f"- {b}" for b in pending_bullets)
            lines.append("")
            pending_bullets = []

    def close_code():
        nonlocal in_code
        if in_code:
            lines.append("```")
            lines.append("")
            in_code = False

    for block in iter_block_items(doc):
        if isinstance(block, Table):
            flush_bullets()
            close_code()
            md_rows = table_to_md(block)
            if md_rows:
                lines.append("")
                lines.extend(md_rows)
                lines.append("")
            continue

        p: Paragraph = block
        style = (p.style.name if p.style else "") or ""
        text = (p.text or "").rstrip()
        if not text:
            continue

        # Title
        if not title_done and style.startswith("Heading") and (
            "상세설계서" in text or text.startswith("NSIGHT")
        ):
            flush_bullets()
            close_code()
            lines.append(f"# {text}")
            lines.append("")
            lines.append(f"> 원본: `{docx_path.name}`  ")
            lines.append("> 변환: Markdown (구조·표·흐름 보존)")
            lines.append("")
            lines.append("---")
            lines.append("")
            title_done = True
            continue

        if style.startswith("Heading 1"):
            flush_bullets()
            close_code()
            # skip duplicate "3. 본문" wrapper if empty-ish — keep as H1
            lines.append("")
            lines.append(f"## {text}")
            lines.append("")
            continue

        if style.startswith("Heading 2"):
            flush_bullets()
            close_code()
            # document subtitle right under title
            if title_done and text.startswith("Harness Orchestrator"):
                lines.append("")
                lines.append(f"**{text}**")
                lines.append("")
                lines.append("---")
                lines.append("")
                continue
            lines.append("")
            lines.append(f"### {text}")
            lines.append("")
            continue

        if style.startswith("Heading 3"):
            flush_bullets()
            close_code()
            lines.append("")
            lines.append(f"#### {text}")
            lines.append("")
            continue

        if style == "Compact" or style.startswith("List"):
            close_code()
            pending_bullets.append(text)
            continue

        if style == "Source Code" or (style in ("Body Text", "First Paragraph") and is_code_like(text) and "\n" in text):
            flush_bullets()
            if style == "Source Code" or "\n" in text:
                if not in_code:
                    lines.append("")
                    # pick fence language heuristically
                    lang = "text"
                    stripped = text.lstrip()
                    if stripped.startswith("{") or stripped.startswith("["):
                        lang = "json"
                    elif stripped.startswith("workflowId:") or stripped.startswith("ruleId:") or stripped.startswith("checkpointId:"):
                        lang = "yaml"
                    elif "UPDATE " in text or "SET status" in text:
                        lang = "sql"
                    lines.append(f"```{lang}")
                    in_code = True
                lines.append(text)
                # Source Code paragraphs are usually one block each — close after each paragraph
                # but multi-paragraph code sequences sometimes appear; close if next isn't source.
                # Safer: close after each Source Code paragraph for JSON/YAML examples.
                close_code()
                continue

        flush_bullets()
        close_code()
        lines.append(text)
        lines.append("")

    flush_bullets()
    close_code()

    # tidy excess blank lines
    cleaned: list[str] = []
    blank = 0
    for line in lines:
        if line.strip() == "":
            blank += 1
            if blank <= 2:
                cleaned.append("")
        else:
            blank = 0
            cleaned.append(line)
    while cleaned and cleaned[0] == "":
        cleaned.pop(0)
    while cleaned and cleaned[-1] == "":
        cleaned.pop()
    cleaned.append("")

    md_path.write_text("\n".join(cleaned), encoding="utf-8")
    print("wrote", md_path.name, "bytes", md_path.stat().st_size, "lines", len(cleaned))


def main():
    base = Path(__file__).resolve().parent
    cands = sorted(base.glob("*상세설계서*.docx"))
    if not cands:
        raise SystemExit("no detailed design docx")
    convert(cands[0], base / "NSIGHT-자동-하네스-상세설계서.md")


if __name__ == "__main__":
    main()
