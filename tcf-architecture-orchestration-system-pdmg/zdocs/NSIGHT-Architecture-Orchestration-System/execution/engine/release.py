from __future__ import annotations

import shutil
from pathlib import Path
from typing import Any

from .common import ensure_dir, now_iso, read_json, sha256_file, timestamp_id, write_json, write_text


def release(run_dir: Path, gate_result: Path, release_root: Path, kind: str, previous: str | None = None) -> dict[str, Any]:
    gate = read_json(gate_result, {})
    if gate.get("decision") != "PASS":
        raise RuntimeError(f"Final gate is not PASS: {gate.get('decision')}")
    stamp = timestamp_id()
    prefix = "PDMG-REF" if kind == "reference" else "PDMG-TGT"
    baseline_id = f"{prefix}-{stamp}"
    model_version = f"MODEL-{stamp}"
    dest = ensure_dir(release_root / baseline_id)
    evidence_dir = ensure_dir(dest / "evidence-package")
    copied = []
    for p in run_dir.rglob("*"):
        if not p.is_file():
            continue
        rel = p.relative_to(run_dir)
        if any(x in {"build", ".gradle", "target"} for x in rel.parts):
            continue
        dst = evidence_dir / rel
        ensure_dir(dst.parent)
        shutil.copy2(p, dst)
        copied.append({"path": rel.as_posix(), "sha256": sha256_file(dst), "size": dst.stat().st_size})
    data = {
        "releasedAt": now_iso(),
        "kind": kind,
        "architectureBaselineId": baseline_id,
        "architectureModelVersion": model_version,
        "sourceRun": str(run_dir.resolve()),
        "finalGate": gate.get("gateId"),
        "previousBaseline": previous,
        "previousStatus": "SUPERSEDED" if previous else None,
        "files": copied,
        "status": "RELEASED",
    }
    write_json(dest / "BASELINE-RELEASE.json", data)
    write_text(dest / "BASELINE-RELEASE.md", "\n".join([
        f"# Baseline Release — {baseline_id}", "",
        f"- Model: `{model_version}`",
        f"- Kind: `{kind}`",
        f"- Previous baseline: `{previous or 'NONE'}`",
        f"- Evidence files: **{len(copied)}**",
        "- Status: **RELEASED**",
    ]))
    return data
