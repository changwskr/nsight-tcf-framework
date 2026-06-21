# tcf-cicd local — WAR 배포 + (필요 시) Tomcat 재기동 + health verify
param(
    [switch]$SkipSync,
    [switch]$SkipVerify,
    [switch]$Restart,
    [switch]$Help,
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$Codes
)

$ErrorActionPreference = 'Stop'
$LocalZtomcat = Split-Path -Parent $MyInvocation.MyCommand.Path
$CicdRoot = (Resolve-Path (Join-Path $LocalZtomcat '../..')).Path
$FwRoot = (Resolve-Path (Join-Path $CicdRoot '..')).Path
$ZTomcatHome = Join-Path $FwRoot 'ztomcat'
$SyncScript = Join-Path $CicdRoot 'scripts/sync-to-framework.ps1'
$DeployScript = Join-Path (Join-Path $CicdRoot 'local/script') 'deploy-wars.ps1'

$AllContexts = @('cc', 'ic', 'pc', 'bc', 'ms', 'sv', 'pd', 'cm', 'eb', 'ep', 'bp', 'bd', 'ss', 'cs', 'ct', 'mg', 'om', 'batch', 'ui')

function Show-Help {
    @"

Usage: deploy-restart.ps1 [codes...] [options]

전체(19 WAR): stop -> clean -> deploy -> start -> health 확인
일부 WAR:     Tomcat 유지 -> deploy -> autoDeploy (~15s) -> health 확인

주의: tcf-cicd/local/ztomcat = PC 로컬 Tomcat 워크플로 폴더이며,
      Spring 프로파일 local(bootRun)과 다릅니다.
      Tomcat WAR는 sync·setenv 모두 dev 프로파일을 사용합니다.

Codes (생략 또는 all = 19 WAR 전체):
  cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om batch ui

Options:
  -SkipSync     dev config sync 생략
  -SkipVerify   health 폴링/검증 생략
  -Restart      일부 WAR만 배포해도 Tomcat 전체 재기동 (기본: autoDeploy)

Examples:
  .\deploy-restart.ps1
  .\deploy-restart.ps1 om ui
  .\deploy-restart.ps1 sv om -Restart
  .\deploy-restart.ps1 -SkipSync

"@
}

if ($Help) { Show-Help; exit 0 }

function Resolve-Contexts {
    param([string[]]$InputCodes)
    if ($InputCodes.Count -eq 0) { return $AllContexts }
    $normalized = $InputCodes | ForEach-Object { $_.ToLowerInvariant() }
    if ($normalized -contains 'all') { return $AllContexts }
    foreach ($code in $normalized) {
        if ($code -notin $AllContexts) {
            throw "Unknown code: $code. Valid: $($AllContexts -join ' ')"
        }
    }
    return @($normalized)
}

function Test-TomcatRunning {
    try {
        $conn = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue
        return $null -ne $conn
    } catch {
        return $false
    }
}

$contexts = Resolve-Contexts -InputCodes $Codes
$deployAll = ($Codes.Count -eq 0) -or (($Codes | ForEach-Object { $_.ToLowerInvariant() }) -contains 'all')
$fullRestart = $deployAll -or $Restart

if ($fullRestart) {
    Write-Host '[local-ztomcat] Full restart: stop -> clean -> deploy -> start -> verify'
} else {
    Write-Host "[local-ztomcat] Rolling deploy ($($contexts -join ' ')): Tomcat 유지, autoDeploy -> verify"
}

if (-not $SkipSync) {
    if (-not (Test-Path $SyncScript)) { throw "sync script not found: $SyncScript" }
    Write-Host '[local-ztomcat] sync dev config -> framework'
    & $SyncScript -Profile dev
}

$tomcatRunning = Test-TomcatRunning

if ($fullRestart) {
    & (Join-Path $LocalZtomcat 'stop.ps1')
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

    & (Join-Path $ZTomcatHome 'clean-exploded.ps1')
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
}

if ($Codes.Count -gt 0) {
    & $DeployScript -SkipSync -SyncProfile dev -Codes $Codes
} else {
    & $DeployScript -SkipSync -SyncProfile dev
}
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

if ($fullRestart) {
    & (Join-Path $LocalZtomcat 'start.ps1') -SkipSync -SkipDeploy
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
} elseif (-not $tomcatRunning) {
    Write-Host '[local-ztomcat] Tomcat not running — starting after deploy ...'
    & (Join-Path $LocalZtomcat 'start.ps1') -SkipSync -SkipDeploy
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
} else {
    Write-Host '[local-ztomcat] Tomcat running — selected context(s) redeploy automatically (~15s).'
}

if ($SkipVerify) {
    Write-Host '[local-ztomcat] Done (verify skipped).'
    exit 0
}

$waitHint = if ($fullRestart) { '~5 min' } else { '~30s per context' }
Write-Host "[local-ztomcat] Waiting for WAR deployments ($waitHint for $($contexts.Count) context(s))..."
$deadline = (Get-Date).AddMinutes(6)
$base = 'http://localhost:8080'

do {
    Start-Sleep -Seconds 15
    $up = 0
    foreach ($ctx in $contexts) {
        try {
            $null = Invoke-WebRequest -Uri "$base/$ctx/actuator/health" -UseBasicParsing -TimeoutSec 5
            $up++
        } catch { }
    }
    Write-Host "  health OK: $up / $($contexts.Count)"
    if ($up -eq $contexts.Count) { break }
} while ((Get-Date) -lt $deadline)

Write-Host '[local-ztomcat] Health check (GET /{context}/actuator/health)'
$ok = 0
$fail = 0
foreach ($ctx in $contexts) {
    $url = "$base/$ctx/actuator/health"
    try {
        $r = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 30
        Write-Host "  OK   $ctx -> $($r.StatusCode)"
        $ok++
    } catch {
        $code = $_.Exception.Response.StatusCode.value__
        if ($code) {
            Write-Host "  FAIL $ctx -> HTTP $code"
        } else {
            Write-Host "  FAIL $ctx -> $($_.Exception.Message)"
        }
        $fail++
    }
}

Write-Host "[local-ztomcat] Result: $ok OK, $fail FAIL (total $($contexts.Count))"
if ($fail -gt 0) { exit 1 }
Write-Host '[local-ztomcat] deploy-restart complete.'
