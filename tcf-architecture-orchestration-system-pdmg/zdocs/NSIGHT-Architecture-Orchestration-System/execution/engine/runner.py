from __future__ import annotations

import subprocess
from pathlib import Path
from typing import Any

from .common import now_iso, write_json, write_text


def run_command(command: list[str], cwd: Path, out_json: Path, label: str) -> dict[str, Any]:
    cp = subprocess.run(command, cwd=str(cwd), stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    data = {
        "label": label,
        "executedAt": now_iso(),
        "cwd": str(cwd.resolve()),
        "command": command,
        "exitCode": cp.returncode,
        "result": "PASS" if cp.returncode == 0 else "FAIL",
        "output": cp.stdout,
    }
    write_json(out_json, data)
    write_text(out_json.with_suffix('.md'), "\n".join([
        f"# Command Evidence — {label}", "",
        f"- Result: **{data['result']}**",
        f"- Exit code: `{data['exitCode']}`",
        f"- CWD: `{data['cwd']}`",
        f"- Command: `{' '.join(command)}`",
        "", "## Output", "", "```text", cp.stdout[-20000:], "```"
    ]))
    return data
