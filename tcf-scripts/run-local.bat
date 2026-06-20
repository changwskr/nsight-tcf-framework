@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0.."

if "%~1"=="" goto usage

set "TARGET_COUNT=0"
set "RUN_ALL=0"
set "RUN_FOREGROUND="
set "BACKGROUND_TARGETS="

:arg_loop
if "%~1"=="" goto after_args
call :queue_target "%~1"
shift
goto arg_loop

:after_args
if "%RUN_ALL%"=="1" goto start_all
if %TARGET_COUNT% gtr 1 goto start_background
if defined RUN_FOREGROUND goto start_foreground
goto usage

:start_foreground
echo [run-local] gradle :%RUN_FOREGROUND%:bootRun
gradle :%RUN_FOREGROUND%:bootRun
exit /b %errorlevel%

:start_background
for %%T in (%BACKGROUND_TARGETS%) do (
  echo [run-local] start %%T in new window
  start "%%T" cmd /k "cd /d "%CD%" && gradle :%%T:bootRun"
)
echo [run-local] started %TARGET_COUNT% service(s)
exit /b 0

:start_all
echo [run-local] starting all business services...
for %%C in (cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om) do (
  echo [run-local] start %%C-service
  start "%%C-service" cmd /k "cd /d "%CD%" && gradle :%%C-service:bootRun"
)
echo [run-local] started 17 service(s)
exit /b 0

:queue_target
set "TARGET=%~1"
set /a TARGET_COUNT+=1

if /i "%TARGET%"=="all" (
  set "RUN_ALL=1"
  goto :eof
)
if /i "%TARGET%"=="ui" (
  set "TARGET=tcf-ui"
)
if /i "%TARGET%"=="tcf-ui" (
  set "TARGET=tcf-ui"
)
if /i "%TARGET%"=="tcf-om" (
  set "TARGET=tcf-om"
)
if /i "%TARGET%"=="ud" (
  set "TARGET=tcf-om"
)
if /i "%TARGET%"=="common-updownload" (
  set "TARGET=tcf-om"
)
if /i "%TARGET%"=="et" (
  set "TARGET=common-etc"
)
if /i "%TARGET%"=="common-etc" (
  set "TARGET=common-etc"
)
if /i not "%TARGET:~-8%"=="-service" (
  if /i not "%TARGET%"=="tcf-ui" if /i not "%TARGET%"=="tcf-om" if /i not "%TARGET%"=="common-etc" set "TARGET=%TARGET%-service"
)

if %TARGET_COUNT%==1 set "RUN_FOREGROUND=%TARGET%"
set "BACKGROUND_TARGETS=!BACKGROUND_TARGETS! %TARGET%"
goto :eof

:usage
echo.
echo Usage: run-local.bat ^<target^> [target2 ...]
echo.
echo Targets:
echo   sv ic     service code (ex: sv -^> sv-service bootRun)
echo   ui        tcf-ui bootRun (port 8099)
echo   tcf-om    tcf-om bootRun
echo   ud        tcf-om bootRun (파일 업·다운로드 내장)
echo   et        common-etc bootRun
echo   all       start all 17 *-service in new windows
echo.
echo Examples:
echo   run-local.bat sv
echo   run-local.bat ic
echo   run-local.bat sv ic
echo   run-local.bat all
echo.
exit /b 1
