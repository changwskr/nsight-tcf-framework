#!/usr/bin/env bash
set -euo pipefail
TOOLS_DIR="$(cd "$(dirname "$0")/../tools" && pwd)"
: "${ENVIRONMENT:?Set ENVIRONMENT}"
: "${OPERATOR_COMMAND:?Set OPERATOR_COMMAND}"
ARGS=(operator-hook --command "$OPERATOR_COMMAND" --environment "$ENVIRONMENT")
if [[ "${EXECUTE:-false}" == "true" ]]; then ARGS+=(--execute); fi
if [[ -n "${APPROVAL_TOKEN:-}" ]]; then ARGS+=(--approval-token "$APPROVAL_TOKEN"); fi
exec python3 "$TOOLS_DIR/nsight_run_automation.py" "${ARGS[@]}"
