#!/usr/bin/env bash
set -euo pipefail
curl -X POST http://localhost:8086/sv/online   -H 'Content-Type: application/json'   -d @docs/sample-requests/sv-sample-inquiry.json
