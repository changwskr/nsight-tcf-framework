#!/usr/bin/env bash
set -euo pipefail
OUT="${1:-metrics/host}"
mkdir -p "$OUT"
date -Is > "$OUT/timestamp.txt"
hostname > "$OUT/hostname.txt"
uname -a > "$OUT/uname.txt"
( uptime || true ) > "$OUT/uptime.txt"
( free -b || true ) > "$OUT/memory.txt"
( vmstat 1 5 || true ) > "$OUT/vmstat.txt"
( ps -eo pid,ppid,cmd,%mem,%cpu --sort=-%cpu | head -n 100 || true ) > "$OUT/ps-top.txt"
if command -v ss >/dev/null 2>&1; then ss -s > "$OUT/socket-summary.txt" || true; fi
