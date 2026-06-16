#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

SERVICE_CODES=(cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om)

usage() {
  cat <<'EOF'

Usage: build.sh <target> [target2 ...]

Targets:
  all       clean + buildBusinessWars
  wars      buildBusinessWars only
  tcf       tcf-util, tcf-core, tcf-web
  common    common-etc, common-updownload
  ui        tcf-ui bootJar
  services  all *-service modules
  sv ic     service code (ex: sv -> sv-service)

Examples:
  ./build.sh sv
  ./build.sh tcf sv
  ./build.sh tcf ic ui
  ./build.sh all

EOF
  exit 1
}

is_service_code() {
  local code="$1"
  for c in "${SERVICE_CODES[@]}"; do
    [[ "$c" == "$code" ]] && return 0
  done
  return 1
}

gradle --stop >/dev/null 2>&1 || true

TASKS=()
append() { TASKS+=("$1"); }

resolve_target() {
  local target
  target="$(echo "$1" | tr '[:upper:]' '[:lower:]')"

  case "$target" in
    all)
      echo "[build] gradle clean buildBusinessWars"
      gradle clean buildBusinessWars
      exit 0
      ;;
    wars) append buildBusinessWars ;;
    tcf)
      append :tcf-util:build
      append :tcf-core:build
      append :tcf-web:build
      ;;
    common)
      append :common-etc:build
      append :common-updownload:build
      ;;
    ui|tcf-ui) append :tcf-ui:bootJar ;;
    services)
      for code in "${SERVICE_CODES[@]}"; do
        append ":${code}-service:build"
      done
      ;;
    *-service)
      append ":${target}:build"
      ;;
    *)
      if is_service_code "$target"; then
        append ":${target}-service:build"
      else
        echo "[build] Unknown target: $target" >&2
        usage
      fi
      ;;
  esac
}

[[ $# -eq 0 ]] && usage

for arg in "$@"; do
  resolve_target "$arg"
done

[[ ${#TASKS[@]} -eq 0 ]] && usage

echo "[build] gradle ${TASKS[*]}"
gradle "${TASKS[@]}"
