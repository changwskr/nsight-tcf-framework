# local — Tomcat deploy-wars (19 WAR 빌드 + webapps 배포)
param(
    [switch]$SkipSync,
    [switch]$SkipBuild,
    [switch]$NoGradleStop,
    [switch]$Restart,
    [switch]$Help,
    [Parameter(Position = 0)]
    [string[]]$Codes = @(),
    [ValidateSet('dev', 'local')]
    [string]$SyncProfile = 'dev'
)

$ErrorActionPreference = 'Stop'
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$CicdRoot = (Resolve-Path (Join-Path $ScriptDir '../..')).Path
$FwRoot = (Resolve-Path (Join-Path $CicdRoot '..')).Path
$ZTomcatHome = Join-Path $FwRoot 'ztomcat'
$CatalinaHome = Join-Path $ZTomcatHome 'apache-tomcat-10.1.34'
$Webapps = Join-Path $CatalinaHome 'webapps'
$SyncScript = Join-Path $CicdRoot 'scripts/sync-to-framework.ps1'

# module:buildWar:deployWar:context
$AllModules = @(
    @{ Module = 'cc-service';  Src = 'cc.war';       Dest = 'cc.war';       Ctx = 'cc' }
    @{ Module = 'ic-service';  Src = 'ic.war';       Dest = 'ic.war';       Ctx = 'ic' }
    @{ Module = 'pc-service';  Src = 'pc.war';       Dest = 'pc.war';       Ctx = 'pc' }
    @{ Module = 'bc-service';  Src = 'bc.war';       Dest = 'bc.war';       Ctx = 'bc' }
    @{ Module = 'ms-service';  Src = 'ms.war';       Dest = 'ms.war';       Ctx = 'ms' }
    @{ Module = 'sv-service';  Src = 'sv.war';       Dest = 'sv.war';       Ctx = 'sv' }
    @{ Module = 'pd-service';  Src = 'pd.war';       Dest = 'pd.war';       Ctx = 'pd' }
    @{ Module = 'cm-service';  Src = 'cm.war';       Dest = 'cm.war';       Ctx = 'cm' }
    @{ Module = 'eb-service';  Src = 'eb.war';       Dest = 'eb.war';       Ctx = 'eb' }
    @{ Module = 'ep-service';  Src = 'ep.war';       Dest = 'ep.war';       Ctx = 'ep' }
    @{ Module = 'bp-service';  Src = 'bp.war';       Dest = 'bp.war';       Ctx = 'bp' }
    @{ Module = 'bd-service';  Src = 'bd.war';       Dest = 'bd.war';       Ctx = 'bd' }
    @{ Module = 'ss-service';  Src = 'ss.war';       Dest = 'ss.war';       Ctx = 'ss' }
    @{ Module = 'cs-service';  Src = 'cs.war';       Dest = 'cs.war';       Ctx = 'cs' }
    @{ Module = 'ct-service';  Src = 'ct.war';       Dest = 'ct.war';       Ctx = 'ct' }
    @{ Module = 'mg-service';  Src = 'mg.war';       Dest = 'mg.war';       Ctx = 'mg' }
    @{ Module = 'tcf-om';      Src = 'tcf-om.war';   Dest = 'om.war';       Ctx = 'om' }
    @{ Module = 'tcf-batch';   Src = 'tcf-batch.war'; Dest = 'batch.war';   Ctx = 'batch' }
    @{ Module = 'tcf-ui';      Src = 'tcf-ui.war';   Dest = 'ui.war';       Ctx = 'ui' }
)

$ValidCodes = $AllModules | ForEach-Object { $_.Ctx }

function Show-Help {
    @"

Usage: deploy-wars.ps1 [codes...] [options]

tcf-cicd 설정 sync -> WAR 빌드 -> ztomcat webapps 배포 (19 context).

Codes (생략 또는 all = 전체):
  cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om batch ui

Options:
  -SyncProfile dev|local   framework sync 프로파일 (기본 dev — Tomcat setenv)
  -SkipSync                yml/setenv sync 생략
  -SkipBuild               기존 WAR만 복사 (Gradle 생략)
  -NoGradleStop            gradle --stop 생략
  -Restart                 배포 후 ztomcat/start.ps1 실행

Examples:
  .\deploy-wars.ps1
  .\deploy-wars.ps1 all
  .\deploy-wars.ps1 sv om batch
  .\deploy-wars.ps1 -SkipSync -SkipBuild

Tomcat: $CatalinaHome
Webapps: $Webapps

"@
}

if ($Help) { Show-Help; exit 0 }

function Resolve-Gradle {
    if ($env:GRADLE_HOME_OVERRIDE -and (Test-Path "$env:GRADLE_HOME_OVERRIDE\bin\gradle.bat")) {
        return "$env:GRADLE_HOME_OVERRIDE\bin\gradle.bat"
    }
    if ($env:GRADLE_HOME -and (Test-Path "$env:GRADLE_HOME\bin\gradle.bat")) {
        return "$env:GRADLE_HOME\bin\gradle.bat"
    }
    $cmd = Get-Command gradle -ErrorAction SilentlyContinue
    if ($cmd) { return $cmd.Source }
    throw 'gradle not found. Set GRADLE_HOME or add gradle to PATH.'
}

function Resolve-Modules {
    param([string[]]$InputCodes)
    if ($InputCodes.Count -eq 0) { return $AllModules }
    $normalized = $InputCodes | ForEach-Object { $_.ToLowerInvariant() }
    if ($normalized -contains 'all') { return $AllModules }

    $selected = @()
    foreach ($code in $normalized) {
        $m = $AllModules | Where-Object { $_.Ctx -eq $code }
        if (-not $m) {
            throw "Unknown code: $code. Valid: $($ValidCodes -join ' ')"
        }
        $selected += $m
    }
    return $selected
}

function Invoke-BatchDashboardCollect {
    $batchBase = 'http://localhost:8080/batch'
    Write-Host '[deploy-wars] waiting for tcf-batch context (~90s) ...'
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
        Write-Warning '[deploy-wars] tcf-batch not UP yet — Health Check DB/AP는 스케줄(5분) 또는 수동 POST /batch/jobs/db-status/run 후 갱신됩니다.'
        return
    }

    foreach ($job in @('db-status', 'ap-status', 'session-status', 'deploy-status')) {
        try {
            Write-Host "[deploy-wars] POST /batch/jobs/$job/run ..."
            Invoke-RestMethod -Method POST -Uri "$batchBase/jobs/$job/run" -TimeoutSec 120 | Out-Null
        } catch {
            Write-Warning "[deploy-wars] batch job $job failed: $($_.Exception.Message)"
        }
    }
    Write-Host '[deploy-wars] OM dashboard status tables refreshed (OM_DB_STATUS 등).'
}

if (-not (Test-Path (Join-Path $CatalinaHome 'bin/catalina.bat')) -and
    -not (Test-Path (Join-Path $CatalinaHome 'bin/catalina.sh'))) {
    throw "Tomcat not found. Run ztomcat/install-tomcat first: $CatalinaHome"
}

if (-not $SkipSync) {
    if (-not (Test-Path $SyncScript)) { throw "sync script not found: $SyncScript" }
    Write-Host "[deploy-wars] sync $SyncProfile config -> framework"
    & $SyncScript -Profile $SyncProfile
}

$selected = Resolve-Modules -InputCodes $Codes

$Gradle = Resolve-Gradle

Push-Location $FwRoot
try {
    if (-not $SkipBuild) {
        if (-not $NoGradleStop) {
            Write-Host '[deploy-wars] gradle --stop'
            & $Gradle --stop 2>$null | Out-Null
        }

        if ($selected.Count -eq $AllModules.Count) {
            Write-Host '[deploy-wars] gradle buildZtomcatWars'
            & $Gradle buildZtomcatWars
        } else {
            $tasks = [string[]]@($selected | ForEach-Object { ":$($_.Module):bootWar" })
            Write-Host "[deploy-wars] gradle $($tasks -join ' ')"
            & $Gradle @tasks
        }
        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    }

    Write-Host '[deploy-wars] removing stale exploded directories ...'
    $contexts = $selected | ForEach-Object { $_.Ctx }
    if ($selected.Count -eq $AllModules.Count) {
        & (Join-Path $ZTomcatHome 'clean-exploded.ps1')
        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    } else {
        foreach ($ctx in $contexts) {
            $dir = Join-Path $Webapps $ctx
            if (Test-Path $dir) {
                Remove-Item -LiteralPath $dir -Recurse -Force
                Write-Host "  removed $ctx/"
            }
        }
    }

    Write-Host '[deploy-wars] copying WAR files to webapps ...'
    $missing = @()
    foreach ($m in $selected) {
        $from = Join-Path $FwRoot "$($m.Module)/build/libs/$($m.Src)"
        $to = Join-Path $Webapps $m.Dest
        if (Test-Path $from) {
            Copy-Item -Force -Path $from -Destination $to
            Write-Host "  deployed $($m.Dest) (from $($m.Src))"
        } else {
            Write-Host "  missing $($m.Src) in $($m.Module)"
            $missing += $from
        }
    }

    if ($missing.Count -gt 0) {
        throw "WAR build output missing. Run without -SkipBuild or fix Gradle errors."
    }

    $batchDeployed = $selected | Where-Object { $_.Ctx -eq 'batch' }
    if ($batchDeployed -and -not $Restart) {
        Invoke-BatchDashboardCollect
    }
}
finally {
    Pop-Location
}

if ($Restart) {
    Write-Host '[deploy-wars] restarting Tomcat ...'
    $startPs1 = Join-Path (Join-Path $CicdRoot 'local/ztomcat') 'start.ps1'
    if (Test-Path $startPs1) {
        & $startPs1 -SkipSync -SkipDeploy
    } else {
        Write-Warning "start.ps1 not found: $startPs1"
    }
} elseif ($selected.Count -eq $AllModules.Count) {
    Write-Host '[deploy-wars] Done. Restart Tomcat if it is already running.'
} else {
    Write-Host '[deploy-wars] Done. Running Tomcat redeploys context automatically (~15s).'
}
