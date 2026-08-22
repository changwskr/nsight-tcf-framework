#!/usr/bin/env bash
set -euo pipefail
: "${TIMEOUT_URL:?Set TIMEOUT_URL to an approved test-only ServiceId endpoint}"
: "${REQUEST_BODY_FILE:?Set REQUEST_BODY_FILE}"
OUT="${1:-logs}"
mkdir -p "$OUT"
GUID="${GUID:-$(python3 - <<'PY'
import uuid; print(uuid.uuid4())
PY
)}"
START=$(date +%s%3N)
set +e
curl -sS --max-time "${CLIENT_TIMEOUT_SEC:-8}" -o "$OUT/timeout-response.json" -w '%{http_code}\n' \
  -H 'Content-Type: application/json' \
  -H "ServiceId: ${SERVICE_ID:-MG.TEST.timeout}" \
  -H "X-NSIGHT-GUID: $GUID" \
  ${AUTH_BEARER:+-H "Authorization: Bearer $AUTH_BEARER"} \
  --data-binary "@$REQUEST_BODY_FILE" "$TIMEOUT_URL" > "$OUT/http-code.txt" 2> "$OUT/curl-error.txt"
RC=$?
set -e
END=$(date +%s%3N)
printf '{"guid":"%s","curl_rc":%s,"elapsed_ms":%s,"start_ms":%s,"end_ms":%s}\n' "$GUID" "$RC" "$((END-START))" "$START" "$END" > "$OUT/client-timeout.json"
exit 0
