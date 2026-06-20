@echo off
setlocal enabledelayedexpansion
set "ZTOMCAT_HOME=%~dp0"
set "PROJECT_HOME=%ZTOMCAT_HOME%.."
set "CATALINA_HOME=%ZTOMCAT_HOME%apache-tomcat-10.1.34"
set "WEBAPPS=%CATALINA_HOME%\webapps"

if /i "%~1"=="help" goto :usage
if /i "%~1"=="/?" goto :usage
if /i "%~1"=="-h" goto :usage

if not exist "%CATALINA_HOME%\bin\catalina.bat" (
    echo [ztomcat] Tomcat not found. Run install-tomcat.bat first.
    exit /b 1
)

if exist "%USERPROFILE%\.jdks\temurin-21.0.4" (
    set "JAVA_HOME=%USERPROFILE%\.jdks\temurin-21.0.4"
)

set "GRADLE=C:\Programming(23-08-15)\gradle-8.10.1\bin\gradle.bat"
if not exist "%GRADLE%" (
    where gradle >nul 2>&1
    if errorlevel 1 (
        echo [ztomcat] gradle not found.
        exit /b 1
    )
    set "GRADLE=gradle"
)

set "GRADLE_TASKS="
set "DEPLOY_ENTRIES="
set "CLEAN_CTX="

:collect_args
if "%~1"=="" goto :args_done
if /i "%~1"=="all" (
    set "GRADLE_TASKS="
    set "DEPLOY_ENTRIES="
    set "CLEAN_CTX="
    goto :args_done
)
call :resolve_code "%~1"
if errorlevel 1 exit /b 1
shift
goto :collect_args

:args_done
if not defined GRADLE_TASKS (
    set "GRADLE_TASKS=buildZtomcatWars"
    set "DEPLOY_ALL=1"
    set "DEPLOY_ENTRIES=cc-service:cc.war:cc.war:cc ic-service:ic.war:ic.war:ic pc-service:pc.war:pc.war:pc bc-service:bc.war:bc.war:bc ms-service:ms.war:ms.war:ms sv-service:sv.war:sv.war:sv pd-service:pd.war:pd.war:pd cm-service:cm.war:cm.war:cm eb-service:eb.war:eb.war:eb ep-service:ep.war:ep.war:ep bp-service:bp.war:bp.war:bp bd-service:bd.war:bd.war:bd ss-service:ss.war:ss.war:ss cs-service:cs.war:cs.war:cs ct-service:ct.war:ct.war:ct mg-service:mg.war:mg.war:mg tcf-om:tcf-om.war:om.war:om tcf-batch:tcf-batch.war:batch.war:batch tcf-ui:tcf-ui.war:ui.war:ui"
    echo [ztomcat] Building all WAR files ...
) else (
    set "DEPLOY_ALL=0"
    echo [ztomcat] Building selected WAR^(s^): !GRADLE_TASKS!
)

pushd "%PROJECT_HOME%"
call "%GRADLE%" !GRADLE_TASKS!
if errorlevel 1 (
    popd
    exit /b 1
)
popd

echo [ztomcat] Removing stale exploded directories ...
if "!DEPLOY_ALL!"=="1" (
    powershell -NoProfile -ExecutionPolicy Bypass -File "%ZTOMCAT_HOME%clean-exploded.ps1"
    if errorlevel 1 exit /b 1
) else (
    for %%W in (!CLEAN_CTX!) do (
        if exist "%WEBAPPS%\%%W" (
            rmdir /s /q "%WEBAPPS%\%%W"
            if exist "%WEBAPPS%\%%W" (
                echo [ztomcat] ERROR: could not remove %%W/ — stop Tomcat and retry.
                exit /b 1
            )
        )
    )
)

echo [ztomcat] Copying WAR files to webapps ...
for %%M in (!DEPLOY_ENTRIES!) do call :deploy_war %%M

if "!DEPLOY_ALL!"=="1" (
    echo [ztomcat] Done. Restart Tomcat if it is already running.
) else (
    echo [ztomcat] Done. Tomcat running: /{code} context redeploys automatically ^(~15s^).
)
exit /b 0

:deploy_war
for /f "tokens=1,2,3,4 delims=:" %%A in ("%~1") do (
    set "SRC=%PROJECT_HOME%\%%A\build\libs\%%B"
    if exist "!SRC!" (
        copy /Y "!SRC!" "%WEBAPPS%\%%C" >nul
        echo   deployed %%C ^(from %%B^)
    ) else (
        echo   missing %%B in %%A
    )
)
exit /b 0

:usage
echo Usage:
echo   deploy-wars.bat              Build and deploy all 19 WARs
echo   deploy-wars.bat all          Same as above
echo   deploy-wars.bat sv           Build and deploy one code ^(e.g. sv.war -^> /sv^)
echo   deploy-wars.bat sv cc om     Build and deploy multiple codes
echo.
echo Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om batch ui
exit /b 0

:resolve_code
set "CODE=%~1"
if /i "%CODE%"=="cc" call :add_entry cc-service cc.war cc.war cc & exit /b 0
if /i "%CODE%"=="ic" call :add_entry ic-service ic.war ic.war ic & exit /b 0
if /i "%CODE%"=="pc" call :add_entry pc-service pc.war pc.war pc & exit /b 0
if /i "%CODE%"=="bc" call :add_entry bc-service bc.war bc.war bc & exit /b 0
if /i "%CODE%"=="ms" call :add_entry ms-service ms.war ms.war ms & exit /b 0
if /i "%CODE%"=="sv" call :add_entry sv-service sv.war sv.war sv & exit /b 0
if /i "%CODE%"=="pd" call :add_entry pd-service pd.war pd.war pd & exit /b 0
if /i "%CODE%"=="cm" call :add_entry cm-service cm.war cm.war cm & exit /b 0
if /i "%CODE%"=="eb" call :add_entry eb-service eb.war eb.war eb & exit /b 0
if /i "%CODE%"=="ep" call :add_entry ep-service ep.war ep.war ep & exit /b 0
if /i "%CODE%"=="bp" call :add_entry bp-service bp.war bp.war bp & exit /b 0
if /i "%CODE%"=="bd" call :add_entry bd-service bd.war bd.war bd & exit /b 0
if /i "%CODE%"=="ss" call :add_entry ss-service ss.war ss.war ss & exit /b 0
if /i "%CODE%"=="cs" call :add_entry cs-service cs.war cs.war cs & exit /b 0
if /i "%CODE%"=="ct" call :add_entry ct-service ct.war ct.war ct & exit /b 0
if /i "%CODE%"=="mg" call :add_entry mg-service mg.war mg.war mg & exit /b 0
if /i "%CODE%"=="om" call :add_entry tcf-om tcf-om.war om.war om & exit /b 0
if /i "%CODE%"=="batch" call :add_entry tcf-batch tcf-batch.war batch.war batch & exit /b 0
if /i "%CODE%"=="ui" call :add_entry tcf-ui tcf-ui.war ui.war ui & exit /b 0
echo [ztomcat] Unknown code: %CODE%
echo [ztomcat] Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om batch ui
exit /b 1

:add_entry
if defined GRADLE_TASKS (
    set "GRADLE_TASKS=!GRADLE_TASKS! :%~1:bootWar"
) else (
    set "GRADLE_TASKS=:%~1:bootWar"
)
set "DEPLOY_ENTRIES=!DEPLOY_ENTRIES! %~1:%~2:%~3:%~4"
set "CLEAN_CTX=!CLEAN_CTX! %~4"
exit /b 0
