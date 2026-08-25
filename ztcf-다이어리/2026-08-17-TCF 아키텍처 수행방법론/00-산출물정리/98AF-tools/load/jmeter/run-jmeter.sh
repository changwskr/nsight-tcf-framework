#!/usr/bin/env bash
set -euo pipefail
: "${JMETER_HOME:?Set JMETER_HOME}"
: "${BASE_URL:?Set BASE_URL}"
: "${SERVICE_ID:?Set SERVICE_ID}"
: "${REQUEST_BODY_FILE:?Set REQUEST_BODY_FILE}"
TARGET_TPS="${TARGET_TPS:-600}"
THREADS="${THREADS:-500}"
DURATION_SEC="${DURATION_SEC:-300}"
RAMP_SEC="${RAMP_SEC:-30}"
PATH_PROP="${PATH_PROP:-/api/service}"
RESULT_JTL="${RESULT_JTL:-result.jtl}"
PLAN="${PLAN:-$(cd "$(dirname "$0")" && pwd)/nsight-service.jmx}"
AUTH_BEARER="${AUTH_BEARER:-}"

exec "$JMETER_HOME/bin/jmeter" -n -t "$PLAN" \
  -JBASE_URL="$BASE_URL" -JPATH="$PATH_PROP" -JSERVICE_ID="$SERVICE_ID" \
  -JAUTH_BEARER="$AUTH_BEARER" -JREQUEST_BODY_FILE="$REQUEST_BODY_FILE" \
  -JTARGET_TPS="$TARGET_TPS" -JTHREADS="$THREADS" -JDURATION_SEC="$DURATION_SEC" \
  -JRAMP_SEC="$RAMP_SEC" -l "$RESULT_JTL" \
  -Jjmeter.save.saveservice.output_format=csv \
  -Jjmeter.save.saveservice.response_code=true \
  -Jjmeter.save.saveservice.successful=true \
  -Jjmeter.save.saveservice.latency=true \
  -Jjmeter.save.saveservice.connect_time=true
