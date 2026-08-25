#!/usr/bin/env bash
set -euo pipefail
: "${ACTUATOR_BASE_URL:?Set ACTUATOR_BASE_URL, e.g. http://127.0.0.1:8080/actuator}"
OUT="${1:-metrics/micrometer}"
mkdir -p "$OUT"
AUTH_HEADER=()
if [[ -n "${ACTUATOR_BEARER:-}" ]]; then AUTH_HEADER=(-H "Authorization: Bearer ${ACTUATOR_BEARER}"); fi
metrics=(
  process.cpu.usage
  system.cpu.usage
  jvm.memory.used
  jvm.memory.max
  jvm.gc.pause
  tomcat.threads.busy
  tomcat.threads.config.max
  hikaricp.connections.active
  hikaricp.connections.pending
  hikaricp.connections.max
)
for m in "${metrics[@]}"; do
  safe=${m//./_}
  curl -fsS "${AUTH_HEADER[@]}" "$ACTUATOR_BASE_URL/metrics/$m" > "$OUT/$safe.json" || printf '{"metric":"%s","status":"UNAVAILABLE"}\n' "$m" > "$OUT/$safe.json"
done
