param(
    [string[]]$Contexts = @('cc','ic','pc','bc','ms','sv','pd','cm','eb','ep','bp','bd','ss','cs','ct','mg','om','batch','ui')
)

$ErrorActionPreference = 'Stop'
$ZTomcatHome = Split-Path -Parent $MyInvocation.MyCommand.Path
$Webapps = Join-Path $ZTomcatHome 'apache-tomcat-10.1.34\webapps'

Write-Host '[ztomcat] Removing exploded WAR directories...'
$failed = @()
foreach ($ctx in $Contexts) {
    $dir = Join-Path $Webapps $ctx
    if (-not (Test-Path $dir)) { continue }
    try {
        Remove-Item -LiteralPath $dir -Recurse -Force
        Write-Host "  removed $ctx/"
    } catch {
        $failed += $ctx
        Write-Host "  FAIL remove $ctx/ — $($_.Exception.Message)"
    }
}

if ($failed.Count -gt 0) {
    Write-Host "[ztomcat] ERROR: could not remove: $($failed -join ', '). Stop Tomcat and retry."
    exit 1
}
