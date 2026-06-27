@echo off
setlocal
cd /d "%~dp0"
echo [gw-run-local] profile=local port=8100 downstream=bootrun ^(업무별 포트^)
call build.bat run-local
exit /b %errorlevel%
