@echo off
setlocal
cd /d "%~dp0\..\.."

set "MODULE=tcf-oc"
set "JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8"

if "%SPRING_PROFILES_ACTIVE%"=="" set "SPRING_PROFILES_ACTIVE=local"

echo [oc-run] gradle :%MODULE%:bootRun ^(port 8094^)
call gradlew :%MODULE%:bootRun -Dspring.profiles.active=%SPRING_PROFILES_ACTIVE%
endlocal
