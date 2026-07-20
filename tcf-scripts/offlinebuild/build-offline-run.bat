@echo off
rem Convenience: offline build -x test
setlocal
cd /d "%~dp0..\.."
call "%~dp0build-offline.bat" build -x test
exit /b %ERRORLEVEL%
