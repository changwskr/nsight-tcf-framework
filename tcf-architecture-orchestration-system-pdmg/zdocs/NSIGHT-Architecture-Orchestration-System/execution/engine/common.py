from __future__ import annotations

import hashlib
import json
import os
import re
import shutil
import subprocess
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Iterable

UNKNOWN = "UNKNOWN"
DEFAULT_EXCLUDES = {
    ".git", ".gradle", ".idea", "build", "bin", "target", "logs", "log",
    "generated", "history", "duplicate", "duplicates", "node_modules", "out",
}


def now_iso() -> str:
    return datetime.now(timezone.utc).astimezone().isoformat(timespec="seconds")


def timestamp_id() -> str:
    return datetime.now().strftime("%Y%m%d-%H%M%S")


def ensure_dir(path: Path) -> Path:
    path.mkdir(parents=True, exist_ok=True)
    return path


def read_json(path: Path, default: Any = None) -> Any:
    if not path.exists():
        return default
    with path.open("r", encoding="utf-8") as f:
        return json.load(f)


def write_json(path: Path, data: Any) -> None:
    ensure_dir(path.parent)
    with path.open("w", encoding="utf-8", newline="\n") as f:
        json.dump(data, f, ensure_ascii=False, indent=2, sort_keys=False)
        f.write("\n")


def write_text(path: Path, text: str) -> None:
    ensure_dir(path.parent)
    path.write_text(text.rstrip() + "\n", encoding="utf-8")


def sha256_file(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def sha256_text(text: str) -> str:
    return hashlib.sha256(text.encode("utf-8")).hexdigest()


def safe_rel(path: Path, root: Path) -> str:
    try:
        return path.resolve().relative_to(root.resolve()).as_posix()
    except ValueError:
        return path.resolve().as_posix()


def is_excluded(path: Path, root: Path, extra: Iterable[str] = ()) -> bool:
    excluded = DEFAULT_EXCLUDES | set(extra)
    try:
        rel = path.resolve().relative_to(root.resolve())
    except ValueError:
        return True
    return any(part in excluded for part in rel.parts)


def iter_files(root: Path, extra_excludes: Iterable[str] = ()):
    for dirpath, dirnames, filenames in os.walk(root):
        d = Path(dirpath)
        dirnames[:] = [
            x for x in dirnames
            if x not in DEFAULT_EXCLUDES and x not in set(extra_excludes)
        ]
        for name in filenames:
            p = d / name
            if not is_excluded(p, root, extra_excludes):
                yield p


def run_git(repo: Path, *args: str) -> str:
    try:
        cp = subprocess.run(
            ["git", "-C", str(repo), *args],
            stdout=subprocess.PIPE,
            stderr=subprocess.DEVNULL,
            text=True,
            timeout=10,
            check=True,
        )
        out = cp.stdout.strip()
        return out or UNKNOWN
    except Exception:
        return UNKNOWN


def git_facts(repo: Path) -> dict[str, str]:
    return {
        "branch": run_git(repo, "branch", "--show-current"),
        "commit": run_git(repo, "rev-parse", "HEAD"),
        "remote": run_git(repo, "config", "--get", "remote.origin.url"),
    }


def copy_with_hash(src: Path, dst: Path) -> dict[str, str | int]:
    ensure_dir(dst.parent)
    shutil.copy2(src, dst)
    return {
        "source": str(src),
        "path": str(dst),
        "sha256": sha256_file(dst),
        "size": dst.stat().st_size,
    }


def slug(value: str) -> str:
    return re.sub(r"[^A-Za-z0-9_.-]+", "-", value).strip("-") or "item"
