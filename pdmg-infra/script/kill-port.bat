@echo off
setlocal

rem ============================================================
rem  pdmg-infra kill-port script
rem
rem  Kills process(es) listening on a local TCP port (default 8081).
rem
rem  Usage:
rem    kill-port.bat           kill listeners on 8081
rem    kill-port.bat 8081      same
rem    kill-port.bat 9090      kill listeners on 9090
rem
rem  Note: ASCII only in this file (cmd.exe CP949 rem safety).
rem ============================================================

set "PORT=%~1"
if "%PORT%"=="" set "PORT=8081"

echo ------------------------------------------------------------
echo  kill-port : localhost:%PORT%
echo ------------------------------------------------------------

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$ErrorActionPreference='Stop';" ^
  "$port=%PORT%;" ^
  "$pids=@(Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess -Unique);" ^
  "if(-not $pids -or $pids.Count -eq 0){ Write-Host ('No LISTEN process on port {0}' -f $port); exit 0 };" ^
  "foreach($procId in $pids){" ^
  "  if($procId -eq 0){ continue };" ^
  "  try {" ^
  "    $p=Get-Process -Id $procId -ErrorAction Stop;" ^
  "    Write-Host ('Stopping PID {0} ({1})' -f $procId, $p.ProcessName);" ^
  "    Stop-Process -Id $procId -Force -ErrorAction Stop;" ^
  "  } catch {" ^
  "    Write-Host ('Failed PID {0}: {1}' -f $procId, $_.Exception.Message);" ^
  "    exit 1;" ^
  "  }" ^
  "};" ^
  "Start-Sleep -Seconds 1;" ^
  "$left=@(Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue);" ^
  "if($left.Count -gt 0){ Write-Host ('Port {0} still in LISTEN' -f $port); exit 1 };" ^
  "Write-Host ('Port {0} freed' -f $port); exit 0"

set "RC=%ERRORLEVEL%"
echo.
if not "%RC%"=="0" (
  echo [ERROR] kill-port failed. exit=%RC%
  endlocal & exit /b %RC%
)

endlocal & exit /b 0
