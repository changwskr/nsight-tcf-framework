@echo off
setlocal
rem Thin wrapper - same pattern as calling script\run.bat directly.
call "%~dp0script\run.bat" %*
endlocal & exit /b %ERRORLEVEL%
