#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

usage() {
  cat <<'EOF'

Usage: ./tcf-scripts/curl-sample.sh <code>

Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om
Example: ./tcf-scripts/curl-sample.sh sv

EOF
  exit 1
}

[[ $# -eq 1 ]] || usage
code="$(echo "$1" | tr '[:upper:]' '[:lower:]')"

case "$code" in
  cc) port=8081 ;;
  ic) port=8082 ;;
  pc) port=8083 ;;
  bc) port=8084 ;;
  ms) port=8085 ;;
  sv) port=8086 ;;
  pd) port=8087 ;;
  cm) port=8088 ;;
  eb) port=8089 ;;
  ep) port=8090 ;;
  bp) port=8091 ;;
  bd) port=8092 ;;
  ss) port=8093 ;;
  cs) port=8094 ;;
  ct) port=8095 ;;
  mg) port=8096 ;;
  om) port=8097 ;;
  *) echo "[curl] Unknown business code: $code"; usage ;;
esac

body="tcf-ui/src/main/resources/sample-requests/${code}-sample-inquiry.json"
[[ -f "$body" ]] || { echo "[curl] Sample file not found: $body"; exit 1; }

echo "[curl] POST http://localhost:${port}/${code}/online"
curl -s -X POST "http://localhost:${port}/${code}/online" \
  -H "Content-Type: application/json" \
  --data-binary "@${body}"
echo
