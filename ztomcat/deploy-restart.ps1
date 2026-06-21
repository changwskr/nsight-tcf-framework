# ztomcat — WAR 배포 + (필요 시) Tomcat 재기동 + health verify
param(
    [switch]$SkipVerify,
    [switch]$Restart,
    [switch]$Help,
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$Codes
)

$ErrorActionPreference = 'Stop'
$ZTomcatHome = Split-Path -Parent $MyInvocation.MyCommand.Path
$FwRoot = (Resolve-Path (Join-Path $ZTomcatHome '..')).Path

$AllContexts = @('cc', 'ic', 'pc', 'bc', 'ms', 'sv', 'pd', 'cm', 'eb', 'ep', 'bp', 'bd', 'ss', 'cs', 'ct', 'mg', 'om', 'batch', 'ui')

function Show-Help {
    @"

Usage: deploy-restart.ps1 [codes...] [options]

전체(19 WAR): stop -> clean -> deploy -> start -> health 확인
일부 WAR:     Tomcat 유지 -> deploy -> autoDeploy (~15s) -> health 확인

Codes (생략 또는 all = 19 WAR 전체):
  cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om batch ui

Options:
  -SkipVerify   health 폴링/검증 생략
  -Restart      일부 WAR만 배포해도 Tomcat 전체 재기동 (기본: autoDeploy)

Examples:
  .\deploy-restart.ps1
  .\deploy-restart.ps1 om ui
  .\deploy-restart.ps1 batch
  .\deploy-restart.ps1 sv om -Restart

Tomcat: $(Join-Path $ZTomcatHome 'apache-tomcat-10.1.34')

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

function Invoke-BatchDashboardCollect {
    $batchBase = 'http://localhost:8080/batch'
    Write-Host '[ztomcat] waiting for tcf-batch context (~90s) ...'
    $deadline = (Get-Date).AddSeconds(90)
    $up = $false
    do {
        Start-Sleep -Seconds 3
        try {
            $health = Invoke-RestMethod -Uri "$batchBase/actuator/health" -TimeoutSec 5
            if ($health.status -eq 'UP') { $up = $true; break }
        } catch { }
    } while ((Get-Date) -lt $deadline)

    if (-not $up) {
        Write-Warning '[ztomcat] tcf-batch not UP yet — POST /batch/jobs/db-status/run 로 수동 갱신하세요.'
        return
    }

    foreach ($job in @('db-status', 'ap-status', 'session-status', 'deploy-status')) {
        try {
            Write-Host "[ztomcat] POST /batch/jobs/$job/run ..."
            Invoke-RestMethod -Method POST -Uri "$batchBase/jobs/$job/run" -TimeoutSec 120 | Out-Null
        } catch {
            Write-Warning "[ztomcat] batch job $job failed: $($_.Exception.Message)"
        }
    }
    Write-Host '[ztomcat] OM dashboard status tables refreshed.'
}

function Invoke-DeployWars {
    param([string[]]$InputCodes)
    $deployBat = Join-Path $ZTomcatHome 'deploy-wars.bat'
    if ($InputCodes.Count -gt 0) {
        & $deployBat @InputCodes
    } else {
        & $deployBat
    }
}

function Invoke-StartTomcatSkipDeploy {
    $prev = $env:ZTOMCAT_SKIP_DEPLOY
    $env:ZTOMCAT_SKIP_DEPLOY = '1'
    try {
        & (Join-Path $ZTomcatHome 'start.ps1')
    } finally {
        if ($null -eq $prev) {
            Remove-Item Env:ZTOMCAT_SKIP_DEPLOY -ErrorAction SilentlyContinue
        } else {
            $env:ZTOMCAT_SKIP_DEPLOY = $prev
        }
    }
}

$contexts = Resolve-Contexts -InputCodes $Codes
$deployAll = ($Codes.Count -eq 0) -or (($Codes | ForEach-Object { $_.ToLowerInvariant() }) -contains 'all')
$fullRestart = $deployAll -or $Restart

if ($fullRestart) {
    Write-Host '[ztomcat] Full restart: stop -> clean -> deploy -> start -> verify'
} else {
    Write-Host "[ztomcat] Rolling deploy ($($contexts -join ' ')): Tomcat 유지, autoDeploy -> verify"
}

$tomcatRunning = Test-TomcatRunning

if ($fullRestart) {
    & (Join-Path $ZTomcatHome 'stop.ps1')
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

    & (Join-Path $ZTomcatHome 'clean-exploded.ps1')
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
}

if ($Codes.Count -gt 0) {
    Invoke-DeployWars -InputCodes $Codes
} else {
    Invoke-DeployWars -InputCodes @()
}
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

if ($contexts -contains 'batch' -and -not $fullRestart) {
    Invoke-BatchDashboardCollect
}

if ($fullRestart) {
    Invoke-StartTomcatSkipDeploy
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
} elseif (-not $tomcatRunning) {
    Write-Host '[ztomcat] Tomcat not running — starting after deploy ...'
    Invoke-StartTomcatSkipDeploy
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
} else {
    Write-Host '[ztomcat] Tomcat running — selected context(s) redeploy automatically (~15s).'
}

if ($SkipVerify) {
    Write-Host '[ztomcat] Done (verify skipped).'
    exit 0
}

$waitHint = if ($fullRestart) { '~5 min' } else { '~30s per context' }
Write-Host "[ztomcat] Waiting for WAR deployments ($waitHint for $($contexts.Count) context(s))..."
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

Write-Host '[ztomcat] Health check (GET /{context}/actuator/health)'
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

Write-Host "[ztomcat] Result: $ok OK, $fail FAIL (total $($contexts.Count))"
if ($fail -gt 0) { exit 1 }
Write-Host '[ztomcat] deploy-restart complete.'
