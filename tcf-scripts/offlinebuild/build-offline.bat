@echo off
rem Offline build wrapper (no internet). Requires offline-repo/ from prepare-offline-bundle.
setlocal
cd /d "%~dp0..\.."
if not exist "gradlew.bat" (
  echo [offline] gradlew.bat not found. Run tcf-scripts\offlinebuild\prepare-offline-bundle.bat on a PC with internet first.
  exit /b 1
)
if not exist "offline-repo\README.txt" (
  echo [offline] offline-repo not found. Run prepare-offline-bundle first.
  exit /b 1
)
call ".\gradlew.bat" %* --offline
exit /b %ERRORLEVEL%
