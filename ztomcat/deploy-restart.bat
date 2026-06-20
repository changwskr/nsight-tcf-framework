@echo off
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0deploy-restart.ps1"
exit /b %ERRORLEVEL%
