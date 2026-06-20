#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

SERVICE_CODES=(cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg)

usage() {
  cat <<'EOF'

Usage: run-local.sh <target> [target2 ...]

Targets:
  sv ic     service code (ex: sv -> sv-service bootRun)
  ui        tcf-ui bootRun (port 8099)
  om        tcf-om bootRun (port 8097)
  batch     tcf-batch bootRun (port 8098)
  ud        tcf-om bootRun (파일 업·다운로드 내장)
  all       start 16 *-service + tcf-om in background

Examples:
  ./run-local.sh sv
  ./run-local.sh ic
  ./run-local.sh sv ic
  ./run-local.sh all

EOF
  exit 1
}

resolve_service() {
  local target
  target="$(echo "$1" | tr '[:upper:]' '[:lower:]')"
  case "$target" in
    ui|tcf-ui) echo "tcf-ui" ;;
    om|tcf-om|ud|common-updownload) echo "tcf-om" ;;
    batch|tcf-batch) echo "tcf-batch" ;;
    *-service) echo "$target" ;;
    *) echo "${target}-service" ;;
  esac
}

[[ $# -eq 0 ]] && usage

if [[ $# -eq 1 && "$(echo "$1" | tr '[:upper:]' '[:lower:]')" == "all" ]]; then
  for code in "${SERVICE_CODES[@]}"; do
    echo "[run-local] start ${code}-service"
    gradle ":${code}-service:bootRun" &
  done
  echo "[run-local] start tcf-om"
  gradle ":tcf-om:bootRun" &
  wait
  exit 0
fi

if [[ $# -eq 1 ]]; then
  service="$(resolve_service "$1")"
  echo "[run-local] gradle :${service}:bootRun"
  exec gradle ":${service}:bootRun"
fi

for arg in "$@"; do
  service="$(resolve_service "$arg")"
  echo "[run-local] start ${service} in background"
  gradle ":${service}:bootRun" &
done
wait
