#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_HOME="$(cd "${SCRIPT_DIR}/../.." && pwd)"
MODULE="tcf-gateway"
GRADLE="${GRADLE:-gradle}"

TASK=":${MODULE}:bootWar"
if [[ "${1:-}" == "clean" ]]; then
  TASK="clean ${TASK}"
elif [[ "${1:-}" == "run" ]]; then
  TASK=":${MODULE}:bootRun"
fi

cd "${PROJECT_HOME}"
echo "[gw-build] ${GRADLE} ${TASK}"
"${GRADLE}" ${TASK}

if [[ "${1:-}" == "run" ]]; then
  exit 0
fi

WAR_FILE="${PROJECT_HOME}/${MODULE}/build/libs/gw.war"
if [[ -f "${WAR_FILE}" ]]; then
  echo "[gw-build] OK gw.war"
  ls -la "${WAR_FILE}"
else
  echo "[gw-build] MISSING gw.war" >&2
  exit 1
fi
