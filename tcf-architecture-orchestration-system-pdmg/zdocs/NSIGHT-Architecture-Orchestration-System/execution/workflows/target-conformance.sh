#!/usr/bin/env sh
set -eu
if [ $# -lt 3 ]; then
  echo "usage: $0 <target-repository-root> <reference-baseline-or-json> <project> [project...]" >&2
  exit 2
fi
REPO=$1; REF=$2; shift 2
HERE=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
CLI="$HERE/bin/pdmg-orchestrator"
RUN_ID=$($CLI create-target-run --repo "$REPO" --reference "$REF" --projects "$@" --mission "PDMG Target Conformance")
echo "RUN_ID=$RUN_ID"
$CLI scan-target --run "$RUN_ID"
$CLI evaluate --run "$RUN_ID" --gate G00 || true
$CLI evaluate --run "$RUN_ID" --gate G10 || true
$CLI evaluate --run "$RUN_ID" --gate G20 || true
$CLI evaluate --run "$RUN_ID" --gate G30 || true
$CLI status --run "$RUN_ID"
