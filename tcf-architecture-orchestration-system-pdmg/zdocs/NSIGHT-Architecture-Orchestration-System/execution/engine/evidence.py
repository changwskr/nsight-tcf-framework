from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import UNKNOWN, ensure_dir, now_iso, sha256_file, write_json, write_text


def import_runtime(files: list[Path], out: Path, metadata: dict[str, Any]) -> dict[str, Any]:
    ensure_dir(out)
    entries = []
    for p in files:
        if not p.exists() or not p.is_file():
            continue
        dst = out / p.name
        if p.resolve() != dst.resolve():
            dst.write_bytes(p.read_bytes())
        entries.append({
            "name": p.name,
            "path": str(dst),
            "sha256": sha256_file(dst),
            "size": dst.stat().st_size,
        })
    manifest = {
        "createdAt": now_iso(),
        "architectureBaselineId": metadata.get("architectureBaselineId", UNKNOWN),
        "architectureModelVersion": metadata.get("architectureModelVersion", UNKNOWN),
        "sourceCommit": metadata.get("sourceCommit", UNKNOWN),
        "buildId": metadata.get("buildId", UNKNOWN),
        "artifactHash": metadata.get("artifactHash", UNKNOWN),
        "deploymentId": metadata.get("deploymentId", UNKNOWN),
        "serviceId": metadata.get("serviceId", UNKNOWN),
        "traceId": metadata.get("traceId", UNKNOWN),
        "guid": metadata.get("guid", UNKNOWN),
        "evidence": entries,
        "runtimeEvidencePresent": bool(entries),
    }
    write_json(out / "EVIDENCE-MANIFEST.json", manifest)
    write_text(out / "EVIDENCE-MANIFEST.md", "\n".join([
        "# Runtime Evidence Manifest", "",
        f"- Runtime evidence present: **{manifest['runtimeEvidencePresent']}**",
        f"- ServiceId: `{manifest['serviceId']}`",
        f"- TraceId: `{manifest['traceId']}`",
        f"- Files: **{len(entries)}**",
    ]))
    return manifest


def artifact_manifest(artifact: Path, out: Path, build_id: str = UNKNOWN) -> dict[str, Any]:
    data = {
        "createdAt": now_iso(),
        "buildId": build_id,
        "artifact": str(artifact.resolve()),
        "artifactHash": sha256_file(artifact) if artifact.exists() and artifact.is_file() else UNKNOWN,
        "artifactSize": artifact.stat().st_size if artifact.exists() and artifact.is_file() else 0,
    }
    write_json(out, data)
    return data
