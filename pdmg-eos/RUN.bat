@echo off
setlocal
call "%~dp0script\run.bat" %*
endlocal & exit /b %ERRORLEVEL%
