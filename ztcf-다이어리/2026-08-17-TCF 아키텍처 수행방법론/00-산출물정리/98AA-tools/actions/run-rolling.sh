#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
: "${OPERATOR_COMMAND:?Set OPERATOR_COMMAND to an approved environment-specific command}"
export EXECUTE="${EXECUTE:-false}"
exec "$SCRIPT_DIR/operator-hook.sh"
