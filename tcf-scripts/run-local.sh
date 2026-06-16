#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

SERVICE_CODES=(cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om)

usage() {
  cat <<'EOF'

Usage: run-local.sh <target> [target2 ...]

Targets:
  sv ic     service code (ex: sv -> sv-service bootRun)
  ui        tcf-ui bootRun (port 8099)
  ud        common-updownload bootRun
  et        common-etc bootRun
  all       start all 17 *-service in background

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
    ud|common-updownload) echo "common-updownload" ;;
    et|common-etc) echo "common-etc" ;;
    *-service) echo "$target" ;;
    *) echo "${target}-service" ;;
  esac
}

is_service_code() {
  local code="$1"
  for c in "${SERVICE_CODES[@]}"; do
    [[ "$c" == "$code" ]] && return 0
  done
  return 1
}

[[ $# -eq 0 ]] && usage

if [[ $# -eq 1 && "$(echo "$1" | tr '[:upper:]' '[:lower:]')" == "all" ]]; then
  for code in "${SERVICE_CODES[@]}"; do
    echo "[run-local] start ${code}-service"
    gradle ":${code}-service:bootRun" &
  done
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
