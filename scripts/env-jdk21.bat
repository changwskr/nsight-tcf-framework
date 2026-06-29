@echo off
rem NSIGHT TCF — 현재 cmd 세션을 JDK 21로 맞춤 (run-local.bat / build.bat 에서 call)
if exist "%USERPROFILE%\.jdks\temurin-21.0.4\bin\java.exe" (
  set "JAVA_HOME=%USERPROFILE%\.jdks\temurin-21.0.4"
  goto :apply
)
for /d %%J in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do (
  if exist "%%J\bin\java.exe" set "JAVA_HOME=%%J"
)
if not defined JAVA_HOME (
  echo [env-jdk21] JDK 21 not found. Install Temurin 21 to %%USERPROFILE%%\.jdks\temurin-21.0.4
  exit /b 1
)
:apply
set "PATH=%JAVA_HOME%\bin;%PATH%"
exit /b 0
