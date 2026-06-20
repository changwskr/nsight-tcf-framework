#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_HOME="$(cd "${SCRIPT_DIR}/.." && pwd)"

# Tomcat webapps (override: export TOMCAT_WEBAPPS=/path/to/webapps)
if [[ -n "${TOMCAT_WEBAPPS:-}" ]]; then
  WEBAPPS="${TOMCAT_WEBAPPS}"
elif [[ -d "${PROJECT_HOME}/ztomcat/apache-tomcat-10.1.34/webapps" ]]; then
  WEBAPPS="$(cd "${PROJECT_HOME}/ztomcat/apache-tomcat-10.1.34/webapps" && pwd)"
else
  WEBAPPS="${TOMCAT_WEBAPPS:-/opt/tomcat/webapps}"
fi

ALL_MODULES=(
  cc-service:cc.war:cc.war:cc
  ic-service:ic.war:ic.war:ic
  pc-service:pc-service.war:pc.war:pc
  bc-service:bc.war:bc.war:bc
  ms-service:ms.war:ms.war:ms
  sv-service:sv.war:sv.war:sv
  pd-service:pd.war:pd.war:pd
  cm-service:cm.war:cm.war:cm
  eb-service:eb.war:eb.war:eb
  ep-service:ep.war:ep.war:ep
  bp-service:bp.war:bp.war:bp
  bd-service:bd.war:bd.war:bd
  ss-service:ss.war:ss.war:ss
  cs-service:cs.war:cs.war:cs
  ct-service:ct.war:ct.war:ct
  mg-service:mg.war:mg.war:mg
  tcf-om:tcf-om.war:om.war:om
)

usage() {
  cat <<EOF
Usage:
  deploy.sh              Build and deploy all WARs
  deploy.sh all          Same as above
  deploy.sh sv           Build and deploy one code
  deploy.sh sv cc ud     Build and deploy multiple codes

Target webapps:
  ${WEBAPPS}
  (override: export TOMCAT_WEBAPPS=/path/to/webapps)

Gradle:
  export GRADLE_HOME=/path/to/gradle-8.10.1
  or export GRADLE_HOME_OVERRIDE=...

Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om tcf-om
EOF
}

resolve_entry() {
  local code
  code="$(echo "$1" | tr '[:upper:]' '[:lower:]')"
  local entry module _src _dest ctx
  if [[ "${code}" == "tcf-om" ]]; then
    echo "tcf-om:tcf-om.war:om.war:om"
    return 0
  fi
  for entry in "${ALL_MODULES[@]}"; do
    IFS=':' read -r module _src _dest ctx <<< "${entry}"
    if [[ "${ctx}" == "${code}" ]]; then
      echo "${entry}"
      return 0
    fi
  done
  echo "[deploy] Unknown code: ${1}" >&2
  echo "[deploy] Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om tcf-om" >&2
  return 1
}

resolve_gradle() {
  local home="${GRADLE_HOME_OVERRIDE:-${GRADLE_HOME:-}}"

  if [[ -n "${home}" && -x "${home}/bin/gradle" ]]; then
    GRADLE="${home}/bin/gradle"
    return 0
  fi
  if [[ -n "${GRADLE:-}" && -x "${GRADLE}" ]]; then
    return 0
  fi
  if command -v gradle >/dev/null 2>&1; then
    GRADLE="$(command -v gradle)"
    return 0
  fi
  for candidate in \
    "${HOME}/gradle-8.10.1" \
    "/opt/gradle/gradle-8.10.1" \
    "/usr/local/gradle-8.10.1"; do
    if [[ -x "${candidate}/bin/gradle" ]]; then
      GRADLE="${candidate}/bin/gradle"
      return 0
    fi
  done
  echo "[deploy] gradle not found. Set GRADLE_HOME or add gradle to PATH." >&2
  exit 1
}

if [[ "${1:-}" == "help" || "${1:-}" == "-h" || "${1:-}" == "--help" || "${1:-}" == "/?" ]]; then
  usage
  exit 0
fi

if [[ ! -d "${WEBAPPS}" ]]; then
  echo "[deploy] webapps directory not found: ${WEBAPPS}" >&2
  exit 1
fi

resolve_gradle

selected=()
gradle_tasks=()
deploy_all=0

if [[ $# -eq 0 ]]; then
  deploy_all=1
else
  for arg in "$@"; do
    if [[ "$(echo "${arg}" | tr '[:upper:]' '[:lower:]')" == "all" ]]; then
      deploy_all=1
      break
    fi
  done
fi

if [[ "${deploy_all}" -eq 1 ]]; then
  selected=("${ALL_MODULES[@]}")
  gradle_tasks=(buildBusinessWars)
  echo "[deploy] Building all WAR files ..."
else
  for local_code in "$@"; do
    if [[ "$(echo "${local_code}" | tr '[:upper:]' '[:lower:]')" == "all" ]]; then
      continue
    fi
    resolved="$(resolve_entry "${local_code}")"
    selected+=("${resolved}")
    module="${resolved%%:*}"
    gradle_tasks+=(":${module}:bootWar")
  done
  echo "[deploy] Building selected WAR(s): ${gradle_tasks[*]}"
fi

(
  cd "${PROJECT_HOME}"
  "${GRADLE}" "${gradle_tasks[@]}"
)

echo "[deploy] Removing stale exploded directories ..."
for entry in "${selected[@]}"; do
  IFS=':' read -r _module _src _dest ctx <<< "${entry}"
  rm -rf "${WEBAPPS}/${ctx}"
done

echo "[deploy] Copying WAR files to ${WEBAPPS} ..."
for entry in "${selected[@]}"; do
  IFS=':' read -r module src dest _ctx <<< "${entry}"
  from="${PROJECT_HOME}/${module}/build/libs/${src}"
  if [[ -f "${from}" ]]; then
    cp -f "${from}" "${WEBAPPS}/${dest}"
    echo "  deployed ${dest} (from ${src})"
  else
    echo "  missing ${src} in ${module}"
  fi
done

echo
echo "[deploy] Verifying deployed WAR files ..."
verify_count=0
verify_failed=0
for entry in "${selected[@]}"; do
  IFS=':' read -r _module _src dest _ctx <<< "${entry}"
  target="${WEBAPPS}/${dest}"
  if [[ -f "${target}" ]]; then
    verify_count=$((verify_count + 1))
    echo "  [OK] ${dest}"
    ls -lh "${target}"
  else
    verify_failed=$((verify_failed + 1))
    echo "  [MISSING] ${dest} - not found in ${WEBAPPS}"
  fi
done
echo

if [[ "${verify_failed}" -gt 0 ]]; then
  echo "[deploy] Verification failed: ${verify_failed} WAR(s) missing in ${WEBAPPS}" >&2
  exit 1
fi

echo "[deploy] All ${verify_count} WAR(s) verified in ${WEBAPPS}"

if [[ "${deploy_all}" -eq 1 ]]; then
  echo "[deploy] Done. Restart Tomcat if it is already running."
else
  echo "[deploy] Done. Tomcat running: context redeploys automatically (~15s)."
fi
