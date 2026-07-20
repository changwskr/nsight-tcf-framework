#!/usr/bin/env bash
# prepare-offline-bundle.sh — 인터넷 PC에서 오프라인 번들 준비 (Linux/macOS)
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT"

SKIP_WRAPPER=0
SKIP_BUILD=0
for arg in "$@"; do
  case "$arg" in
    --skip-wrapper) SKIP_WRAPPER=1 ;;
    --skip-build) SKIP_BUILD=1 ;;
    -h|--help)
      echo "Usage: $0 [--skip-wrapper] [--skip-build]"
      exit 0
      ;;
  esac
done

echo "[offline] project root: $ROOT"

if [[ "$SKIP_WRAPPER" -eq 0 ]]; then
  echo "[offline] generating Gradle Wrapper (8.10.1) ..."
  gradle wrapper --gradle-version 8.10.1 --distribution-type bin
fi

WRAPPER_DIR="$ROOT/gradle/wrapper"
PROPS="$WRAPPER_DIR/gradle-wrapper.properties"
DIST_ZIP_NAME="gradle-8.10.1-bin.zip"
DIST_ZIP="$WRAPPER_DIR/$DIST_ZIP_NAME"

[[ -f "$PROPS" ]] || { echo "gradle-wrapper.properties missing"; exit 1; }

if [[ ! -f "$DIST_ZIP" ]]; then
  echo "[offline] downloading $DIST_ZIP_NAME ..."
  curl -fsSL -o "$DIST_ZIP" "https://services.gradle.org/distributions/gradle-8.10.1-bin.zip"
fi

# distributionUrl 줄만 교체 (validateDistributionUrl 등은 유지)
tmp="$(mktemp)"
awk -v u="$DIST_ZIP_NAME" 'BEGIN{FS=OFS="="} /^distributionUrl=/{print "distributionUrl=" u; next} {print}' "$PROPS" > "$tmp"
mv "$tmp" "$PROPS"
echo "[offline] wrapper distributionUrl -> $DIST_ZIP_NAME"

run_gradle() {
  if [[ -x "$ROOT/gradlew" ]]; then
    "$ROOT/gradlew" "$@"
  else
    gradle "$@"
  fi
}

if [[ "$SKIP_BUILD" -eq 0 ]]; then
  echo "[offline] online build to warm dependency cache ..."
  sed -i.bak 's/nsight\.dependency\.mode=.*/nsight.dependency.mode=hybrid/' "$ROOT/gradle.properties" || true
  rm -f "$ROOT/gradle.properties.bak"
  run_gradle build -x test --refresh-dependencies
fi

echo "[offline] populateOfflineRepo ..."
run_gradle populateOfflineRepo

sed -i.bak 's/nsight\.dependency\.mode=.*/nsight.dependency.mode=offline/' "$ROOT/gradle.properties"
rm -f "$ROOT/gradle.properties.bak"

echo "[offline] verify offline build ..."
run_gradle verifyOfflineRepo
run_gradle build -x test --offline

echo ""
echo "[offline] DONE. Copy this entire folder to the air-gapped PC."
echo "[offline] On that PC:  ./tcf-scripts/offlinebuild/build-offline.sh build -x test"
echo "[offline]   or:        ./gradlew build -x test --offline"
