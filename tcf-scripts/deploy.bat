@echo off
setlocal enabledelayedexpansion

set "PROJECT_HOME=%~dp0.."
for %%I in ("!PROJECT_HOME!") do set "PROJECT_HOME=%%~fI"
set "WEBAPPS=!PROJECT_HOME!\ztomcat\apache-tomcat-10.1.34\webapps"
set "GRADLE_HOME=C:\Programming(23-08-15)\gradle-8.10.1"
set "GRADLE=!GRADLE_HOME!\bin\gradle.bat"

if defined TOMCAT_WEBAPPS set "WEBAPPS=!TOMCAT_WEBAPPS!"

if /i "%~1"=="help" goto :usage
if /i "%~1"=="/?" goto :usage
if /i "%~1"=="-h" goto :usage

if not exist "!WEBAPPS!" goto :webapps_missing
goto :webapps_ok
:webapps_missing
echo [deploy] webapps directory not found: !WEBAPPS!
exit /b 1
:webapps_ok

if defined GRADLE_HOME_OVERRIDE set "GRADLE_HOME=!GRADLE_HOME_OVERRIDE!"
if defined GRADLE_HOME set "GRADLE=!GRADLE_HOME!\bin\gradle.bat"
if not exist "!GRADLE!" for /f "delims=" %%G in ('where gradle.bat 2^>nul') do (
    set "GRADLE=%%G"
    goto :gradle_found
)
:gradle_found
if not exist "!GRADLE!" goto :gradle_missing
goto :gradle_ok
:gradle_missing
echo [deploy] gradle not found.
exit /b 1
:gradle_ok

set "GRADLE_TASKS="
set "DEPLOY_ENTRIES="
set "CLEAN_CTX="
set "DEPLOY_ALL=0"

if "%~1"=="" set "DEPLOY_ALL=1"
if /i "%~1"=="all" set "DEPLOY_ALL=1"

if "!DEPLOY_ALL!"=="1" goto :build_all

:collect_args
if "%~1"=="" goto :args_done
call :resolve_code "%~1"
if errorlevel 1 exit /b 1
shift
goto :collect_args

:args_done
echo [deploy] Building selected WAR^(s^): !GRADLE_TASKS!
goto :run_build

:build_all
echo [deploy] Building all WAR files ...
set "GRADLE_TASKS=buildBusinessWars"
set "DEPLOY_ENTRIES=cc-service:cc.war:cc.war:cc ic-service:ic.war:ic.war:ic pc-service:pc.war:pc.war:pc bc-service:bc.war:bc.war:bc ms-service:ms.war:ms.war:ms sv-service:sv.war:sv.war:sv pd-service:pd.war:pd.war:pd cm-service:cm.war:cm.war:cm eb-service:eb.war:eb.war:eb ep-service:ep.war:ep.war:ep bp-service:bp.war:bp.war:bp bd-service:bd.war:bd.war:bd ss-service:ss.war:ss.war:ss cs-service:cs.war:cs.war:cs ct-service:ct.war:ct.war:ct mg-service:mg.war:mg.war:mg tcf-om:tcf-om.war:00-om.war:om"
set "CLEAN_CTX=cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om"
goto :run_build

:run_build
cd /d "!PROJECT_HOME!"
call "!GRADLE!" !GRADLE_TASKS!
if errorlevel 1 goto :build_failed
goto :build_ok
:build_failed
exit /b 1
:build_ok

echo [deploy] Removing stale exploded directories ...
for %%W in (!CLEAN_CTX!) do call :clean_ctx %%W

echo [deploy] Copying WAR files to !WEBAPPS! ...
for %%M in (!DEPLOY_ENTRIES!) do call :deploy_war %%M

echo.
echo [deploy] Verifying deployed WAR files ...
set "VERIFY_COUNT=0"
set "VERIFY_FAILED=0"
for %%M in (!DEPLOY_ENTRIES!) do call :verify_war %%M
echo.
if !VERIFY_FAILED! gtr 0 goto :verify_failed
echo [deploy] All !VERIFY_COUNT! WAR^(s^) verified in !WEBAPPS!
goto :verify_ok
:verify_failed
echo [deploy] Verification failed: !VERIFY_FAILED! WAR^(s^) missing in !WEBAPPS!
exit /b 1
:verify_ok

if "!DEPLOY_ALL!"=="1" goto :done_all
echo [deploy] Done. Tomcat running: context redeploys automatically ^(~15s^).
exit /b 0
:done_all
echo [deploy] Done. Restart Tomcat if it is already running.
exit /b 0

:clean_ctx
if exist "!WEBAPPS!\%~1" rmdir /s /q "!WEBAPPS!\%~1" 2>nul
exit /b 0

:deploy_war
for /f "tokens=1,2,3,4 delims=:" %%A in ("%~1") do (
    set "SRC=!PROJECT_HOME!\%%A\build\libs\%%B"
    if exist "!SRC!" (
        copy /Y "!SRC!" "!WEBAPPS!\%%C" >nul
        echo   deployed %%C ^(from %%B^)
    ) else (
        echo   missing %%B in %%A
    )
)
exit /b 0

:verify_war
for /f "tokens=1,2,3,4 delims=:" %%A in ("%~1") do (
    if exist "!WEBAPPS!\%%C" (
        set /a VERIFY_COUNT+=1
        echo   [OK] %%C
        dir "!WEBAPPS!\%%C"
    ) else (
        set /a VERIFY_FAILED+=1
        echo   [MISSING] %%C - not found in !WEBAPPS!
    )
)
exit /b 0

:usage
echo Usage:
echo   deploy.bat              Build and deploy all WARs
echo   deploy.bat all          Same as above
echo   deploy.bat sv           Build and deploy one code
echo   deploy.bat sv cc ud     Build and deploy multiple codes
echo.
echo Target webapps:
echo   !WEBAPPS!
echo   ^(override: set TOMCAT_WEBAPPS=...^)
echo.
echo Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om tcf-om
exit /b 0

:resolve_code
set "CODE=%~1"
if /i "!CODE!"=="cc" call :add_entry cc-service cc.war cc.war cc & exit /b 0
if /i "!CODE!"=="ic" call :add_entry ic-service ic.war ic.war ic & exit /b 0
if /i "!CODE!"=="pc" call :add_entry pc-service pc.war pc.war pc & exit /b 0
if /i "!CODE!"=="bc" call :add_entry bc-service bc.war bc.war bc & exit /b 0
if /i "!CODE!"=="ms" call :add_entry ms-service ms.war ms.war ms & exit /b 0
if /i "!CODE!"=="sv" call :add_entry sv-service sv.war sv.war sv & exit /b 0
if /i "!CODE!"=="pd" call :add_entry pd-service pd.war pd.war pd & exit /b 0
if /i "!CODE!"=="cm" call :add_entry cm-service cm.war cm.war cm & exit /b 0
if /i "!CODE!"=="eb" call :add_entry eb-service eb.war eb.war eb & exit /b 0
if /i "!CODE!"=="ep" call :add_entry ep-service ep.war ep.war ep & exit /b 0
if /i "!CODE!"=="bp" call :add_entry bp-service bp.war bp.war bp & exit /b 0
if /i "!CODE!"=="bd" call :add_entry bd-service bd.war bd.war bd & exit /b 0
if /i "!CODE!"=="ss" call :add_entry ss-service ss.war ss.war ss & exit /b 0
if /i "!CODE!"=="cs" call :add_entry cs-service cs.war cs.war cs & exit /b 0
if /i "!CODE!"=="ct" call :add_entry ct-service ct.war ct.war ct & exit /b 0
if /i "!CODE!"=="mg" call :add_entry mg-service mg.war mg.war mg & exit /b 0
if /i "!CODE!"=="om" call :add_entry tcf-om tcf-om.war om.war om & exit /b 0
if /i "!CODE!"=="tcf-om" call :add_entry tcf-om tcf-om.war om.war om & exit /b 0
echo [deploy] Unknown code: !CODE!
echo [deploy] Codes: cc ic pc bc ms sv pd cm eb ep bp bd ss cs ct mg om tcf-om
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
