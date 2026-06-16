@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0.."

if "%~1"=="" goto usage

echo [build] Stop Gradle daemons...
call gradle --stop >nul 2>&1

set "GRADLE_TASKS="

:arg_loop
if "%~1"=="" goto run_build
call :resolve_target "%~1"
shift
goto arg_loop

:run_build
if not defined GRADLE_TASKS goto usage
echo [build] gradle %GRADLE_TASKS%
gradle %GRADLE_TASKS%
exit /b %errorlevel%

:resolve_target
set "TARGET=%~1"

if /i "%TARGET%"=="all" (
  set "GRADLE_TASKS=clean buildBusinessWars"
  goto :eof
)
if /i "%TARGET%"=="wars" (call :append_task buildBusinessWars & goto :eof)
if /i "%TARGET%"=="tcf" (
  call :append_task :tcf-util:build
  call :append_task :tcf-core:build
  call :append_task :tcf-web:build
  goto :eof
)
if /i "%TARGET%"=="common" (
  call :append_task :common-etc:build
  call :append_task :common-updownload:build
  goto :eof
)
if /i "%TARGET%"=="ui" (call :append_task :tcf-ui:bootJar & goto :eof)
if /i "%TARGET%"=="tcf-ui" (call :append_task :tcf-ui:bootJar & goto :eof)
if /i "%TARGET%"=="services" (
  call :append_task :cc-service:build
  call :append_task :ic-service:build
  call :append_task :pc-service:build
  call :append_task :bc-service:build
  call :append_task :ms-service:build
  call :append_task :sv-service:build
  call :append_task :pd-service:build
  call :append_task :cm-service:build
  call :append_task :eb-service:build
  call :append_task :ep-service:build
  call :append_task :bp-service:build
  call :append_task :bd-service:build
  call :append_task :ss-service:build
  call :append_task :cs-service:build
  call :append_task :ct-service:build
  call :append_task :mg-service:build
  call :append_task :om-service:build
  goto :eof
)

set "SERVICE=%TARGET%"
if /i not "%TARGET:~-8%"=="-service" set "SERVICE=%TARGET%-service"
call :append_task :!SERVICE!:build
goto :eof

:append_task
set "GRADLE_TASKS=%GRADLE_TASKS% %~1"
goto :eof

:usage
echo.
echo Usage: build.bat ^<target^> [target2 ...]
echo.
echo Targets:
echo   all      clean + buildBusinessWars
echo   wars     buildBusinessWars only
echo   tcf      tcf-util, tcf-core, tcf-web
echo   common   common-etc, common-updownload
echo   ui       tcf-ui bootJar
echo   services all *-service modules
echo   sv ic    service code (ex: sv -> sv-service)
echo.
exit /b 1
