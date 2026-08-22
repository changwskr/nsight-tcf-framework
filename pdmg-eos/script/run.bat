@echo off
setlocal

rem ============================================================
rem  pdmg-eos run script (port 8082)
rem
rem  Usage:
rem    run.bat                                   bootRun
rem    run.bat --args="--spring.profiles.active=dev"
rem ============================================================

pushd "%~dp0.."
if errorlevel 1 goto :no_project

if not exist "gradlew.bat" goto :no_wrapper

chcp 65001 >nul
set "JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8"

echo ------------------------------------------------------------
echo  PROJECT : "%CD%"
echo  URL     : http://localhost:8082
echo ------------------------------------------------------------
echo.

call gradlew.bat --no-daemon bootRun %*
set "RUN_RESULT=%ERRORLEVEL%"

popd
endlocal & exit /b %RUN_RESULT%

:no_wrapper
echo [ERROR] gradlew.bat not found in "%CD%"
popd
endlocal & exit /b 1

:no_project
echo [ERROR] cannot enter project home "%~dp0.."
endlocal & exit /b 1
