@echo off
rem prepare-offline-bundle.bat — run on a PC with internet
setlocal
cd /d "%~dp0..\.."
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0prepare-offline-bundle.ps1" %*
exit /b %ERRORLEVEL%
