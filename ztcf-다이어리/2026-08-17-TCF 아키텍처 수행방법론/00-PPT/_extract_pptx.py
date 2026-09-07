# -*- coding: utf-8 -*-
import zipfile
import re
import xml.etree.ElementTree as ET
from pathlib import Path

pptx = Path(__file__).with_name("NSIGHT_아키텍처_정의서_통합본_20260825.pptx")
out = Path(__file__).with_name("_extract_NSIGHT_통합본_20260825.txt")

A_NS = "{http://schemas.openxmlformats.org/drawingml/2006/main}"
P_NS = "{http://schemas.openxmlformats.org/presentationml/2006/main}"

with zipfile.ZipFile(pptx, "r") as z:
    names = sorted(
        [n for n in z.namelist() if re.match(r"ppt/slides/slide\d+\.xml$", n)],
        key=lambda n: int(re.search(r"(\d+)", Path(n).name).group(1)),
    )
    print(f"slides={len(names)} size={pptx.stat().st_size}")
    lines = []
    for i, name in enumerate(names, 1):
        root = ET.fromstring(z.read(name))
        texts = []
        for sp in root.iter(P_NS + "sp"):
            paras = []
            for p in sp.iter(A_NS + "p"):
                runs = []
                for t in p.iter(A_NS + "t"):
                    if t.text:
                        runs.append(t.text)
                    if t.tail:
                        runs.append(t.tail)
                if runs:
                    paras.append("".join(runs))
            if paras:
                texts.append("\n".join(paras))
        for tbl in root.iter(A_NS + "tbl"):
            rows = []
            for tr in tbl.iter(A_NS + "tr"):
                cells = []
                for tc in tr.iter(A_NS + "tc"):
                    cell_runs = []
                    for t in tc.iter(A_NS + "t"):
                        if t.text:
                            cell_runs.append(t.text)
                    cells.append("".join(cell_runs).strip())
                if any(cells):
                    rows.append(" | ".join(cells))
            if rows:
                texts.append("[TABLE]\n" + "\n".join(rows))
        body = "\n---\n".join(texts) if texts else "(no text)"
        lines.append(f"\n===== SLIDE {i} ({name}) =====\n{body}\n")
    out.write_text("".join(lines), encoding="utf-8")
    print(f"wrote {out} chars={out.stat().st_size}")
