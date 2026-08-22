#!/usr/bin/env bash
set -euo pipefail
PID="${1:?usage: collect-jvm.sh <java-pid> [output-dir]}"
OUT="${2:-metrics/jvm}"
mkdir -p "$OUT"
if ! command -v jcmd >/dev/null 2>&1; then echo "jcmd not found" >&2; exit 2; fi
jcmd "$PID" VM.version > "$OUT/vm-version.txt"
jcmd "$PID" VM.flags > "$OUT/vm-flags.txt"
jcmd "$PID" VM.command_line > "$OUT/vm-command-line.txt"
jcmd "$PID" GC.heap_info > "$OUT/gc-heap-info.txt"
jcmd "$PID" Thread.print -l > "$OUT/thread-dump.txt"
