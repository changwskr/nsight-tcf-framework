#!/usr/bin/env bash
# 사내(무인터넷) 빌드 래퍼
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"
[[ -x ./gradlew ]] || { echo "[offline] ./gradlew missing. Run prepare-offline-bundle first."; exit 1; }
[[ -f offline-repo/README.txt ]] || { echo "[offline] offline-repo missing."; exit 1; }
exec ./gradlew "$@" --offline
