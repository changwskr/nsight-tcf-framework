#!/usr/bin/env sh
set -eu
if [ $# -lt 1 ]; then
  echo "usage: $0 <repository-root>" >&2
  exit 2
fi
HERE=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
CLI="$HERE/bin/pdmg-orchestrator"
RUN_ID=$($CLI scan-reference --repo "$1" --mission "PDMG Reference Bootstrap")
echo "RUN_ID=$RUN_ID"
$CLI evaluate --run "$RUN_ID" --gate RG00 || true
$CLI evaluate --run "$RUN_ID" --gate RG10 || true
$CLI evaluate --run "$RUN_ID" --gate RG20 || true
$CLI evaluate --run "$RUN_ID" --gate RG30 || true
$CLI status --run "$RUN_ID"
