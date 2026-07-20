# =============================================================================
# prepare-offline-bundle.ps1
# 인터넷 가능한 PC에서 1회 실행 → 사내(무인터넷)로 복사할 번들 준비
#
# 결과물:
#   - gradle/wrapper/* + gradlew(.bat)     Gradle Wrapper
#   - gradle/wrapper/gradle-*-bin.zip     배포용 Gradle 배포본 (오프라인)
#   - offline-repo/                       Maven 레이아웃 의존성/플러그인
#   - gradle.properties                   nsight.dependency.mode=offline
# =============================================================================
param(
    [switch]$SkipWrapper,
    [switch]$SkipBuild,
    [switch]$Help
)

$ErrorActionPreference = 'Stop'
$Root = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
Set-Location $Root

function Show-Help {
    @"

Usage: tcf-scripts\offlinebuild\prepare-offline-bundle.ps1 [options]

Run on a PC with internet, then copy the whole project folder to the air-gapped site.

Options:
  -SkipWrapper  Skip Gradle Wrapper generation
  -SkipBuild    Skip build (when cache is already warm)
  -Help         This help

"@
}

if ($Help) { Show-Help; exit 0 }

Write-Host "[offline] project root: $Root"

# --- 1) Gradle Wrapper ---
if (-not $SkipWrapper) {
    Write-Host "[offline] generating Gradle Wrapper (8.10.1) ..."
    & gradle wrapper --gradle-version 8.10.1 --distribution-type bin
    if ($LASTEXITCODE -ne 0) { throw "gradle wrapper failed" }
}

$WrapperProps = Join-Path $Root 'gradle\wrapper\gradle-wrapper.properties'
if (-not (Test-Path $WrapperProps)) {
    throw "gradle-wrapper.properties not found. Run without -SkipWrapper."
}

# --- 2) Gradle 배포 zip 을 프로젝트에 내장 + file:// URL ---
$WrapperDir = Join-Path $Root 'gradle\wrapper'
$DistZipName = 'gradle-8.10.1-bin.zip'
$DistZip = Join-Path $WrapperDir $DistZipName

if (-not (Test-Path $DistZip)) {
    Write-Host "[offline] downloading $DistZipName into gradle/wrapper/ ..."
    $Url = 'https://services.gradle.org/distributions/gradle-8.10.1-bin.zip'
    Invoke-WebRequest -Uri $Url -OutFile $DistZip -UseBasicParsing
}

# distributionUrl 을 로컬 zip 상대경로로 고정 (인터넷 불필요)
$lines = Get-Content $WrapperProps
$lines = $lines | ForEach-Object {
    if ($_ -match '^distributionUrl=') { "distributionUrl=$DistZipName" } else { $_ }
}
Set-Content -Path $WrapperProps -Value $lines -Encoding ASCII
Write-Host "[offline] wrapper distributionUrl -> $DistZipName"

function Invoke-NsightGradle {
    param([Parameter(ValueFromRemainingArguments = $true)]$GradleArgs)
    $gw = Join-Path $Root 'gradlew.bat'
    if (Test-Path $gw) {
        & $gw @GradleArgs
    } else {
        & gradle @GradleArgs
    }
    if ($null -ne $LASTEXITCODE -and $LASTEXITCODE -ne 0) {
        throw "gradle failed: $($GradleArgs -join ' ')"
    }
}

# --- 3) 온라인으로 전체 해석·빌드 (캐시 채움) ---
if (-not $SkipBuild) {
    Write-Host "[offline] online build to warm dependency cache ..."
    # populate 전: hybrid/online 으로 원격 접근
    $gp = Join-Path $Root 'gradle.properties'
    $gpText = Get-Content $gp -Raw
    $gpText = $gpText -replace 'nsight\.dependency\.mode=\w+', 'nsight.dependency.mode=hybrid'
    Set-Content -Path $gp -Value $gpText -NoNewline -Encoding UTF8
    Invoke-NsightGradle 'build' '-x' 'test' '--refresh-dependencies'
}

# --- 4) offline-repo 채우기 ---
Write-Host "[offline] populateOfflineRepo ..."
Invoke-NsightGradle 'populateOfflineRepo'

# --- 5) offline 모드로 고정 ---
$gp = Join-Path $Root 'gradle.properties'
$gpText = Get-Content $gp -Raw
if ($gpText -match 'nsight\.dependency\.mode=') {
    $gpText = $gpText -replace 'nsight\.dependency\.mode=\w+', 'nsight.dependency.mode=offline'
} else {
    $gpText = $gpText.TrimEnd() + "`r`nnsight.dependency.mode=offline`r`n"
}
Set-Content -Path $gp -Value $gpText -NoNewline -Encoding UTF8
Write-Host "[offline] nsight.dependency.mode=offline"

# --- 6) 오프라인 검증 ---
Write-Host "[offline] verify offline build ..."
Invoke-NsightGradle 'verifyOfflineRepo'
Invoke-NsightGradle 'build' '-x' 'test' '--offline'

Write-Host ""
Write-Host "[offline] DONE. Copy this entire folder to the air-gapped PC."
Write-Host "[offline] On that PC:  .\tcf-scripts\offlinebuild\build-offline-run.bat"
Write-Host "[offline]   or:        .\gradlew.bat build -x test --offline"
Write-Host "[offline] Included: gradle/wrapper/$DistZipName , offline-repo/"
