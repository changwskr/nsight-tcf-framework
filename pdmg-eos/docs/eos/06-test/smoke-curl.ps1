# EOS local smoke (PowerShell)
# Requires: pdmg-eos bootRun on 8082

$dir = Join-Path $PSScriptRoot "..\..\..\build\tmp-smoke"
if (-not (Test-Path $dir)) { $dir = Join-Path $env:TEMP "eos-smoke"; New-Item -ItemType Directory -Force -Path $dir | Out-Null }

Set-Content "$dir\empty.json" '{}' -Encoding Ascii
Set-Content "$dir\0120.json" '{"pageNo":1,"pageSize":5}' -Encoding Ascii
Set-Content "$dir\0130.json" '{"resourceId":"RSC20260816000001"}' -Encoding Ascii

function Smoke($svc, $file) {
  $out = curl.exe -s -w "|HTTP=%{http_code}" -X POST "http://127.0.0.1:8082/$svc" `
    -H "Content-Type: application/json" -H "rms_svc_c: $svc" --data-binary "@$file"
  if ($out -match '"RSLT_CD"\s*:\s*"([^"]+)"') { Write-Host "$svc -> $($Matches[1])" }
  else { Write-Host "$svc -> FAIL $out" }
}

@(
  @("eoscoa0110S0","empty.json"),
  @("eoscoa0120S0","0120.json"),
  @("eoscoa0130S0","0130.json"),
  @("eoscoa0140S0","0120.json"),
  @("eoscoa0150S0","0130.json"),
  @("eoscoa0141S0","empty.json"),
  @("eoscoa0151S0","0120.json"),
  @("eoscoa0160S0","0130.json"),
  @("eoscoa0170S0","empty.json"),
  @("eoscoa0180S0","empty.json"),
  @("eoscoa0190S0","empty.json")
) | ForEach-Object { Smoke $_[0] (Join-Path $dir $_[1]) }
