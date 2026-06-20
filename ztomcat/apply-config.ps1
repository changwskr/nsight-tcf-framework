$ErrorActionPreference = 'Stop'

$ZTomcatHome = Split-Path -Parent $MyInvocation.MyCommand.Path
$CatalinaHome = Join-Path $ZTomcatHome 'apache-tomcat-10.1.34'
$ServerXml = Join-Path $CatalinaHome 'conf\server.xml'
$SetenvSrc = Join-Path $ZTomcatHome 'conf\setenv.bat'
$SetenvDst = Join-Path $CatalinaHome 'bin\setenv.bat'

if (-not (Test-Path $ServerXml)) {
    Write-Host '[ztomcat] server.xml not found. Run install-tomcat.bat first.'
    exit 1
}

Copy-Item -Path $SetenvSrc -Destination $SetenvDst -Force

$LoggingProps = Join-Path $CatalinaHome 'conf\logging.properties'
if (Test-Path $LoggingProps) {
    $logging = Get-Content -Path $LoggingProps -Raw -Encoding UTF8
    $handlers = @(
        '1catalina.org.apache.juli.AsyncFileHandler',
        '2localhost.org.apache.juli.AsyncFileHandler',
        '3manager.org.apache.juli.AsyncFileHandler',
        '4host-manager.org.apache.juli.AsyncFileHandler',
        'java.util.logging.ConsoleHandler'
    )
    $changed = $false
    foreach ($handler in $handlers) {
        $key = "${handler}.encoding"
        if ($logging -notmatch [regex]::Escape($key)) {
            $logging = $logging + "`n${key} = UTF-8"
            $changed = $true
        } elseif ($logging -notmatch "${handler}\.encoding\s*=\s*UTF-8") {
            $logging = $logging -replace "(${handler}\.encoding\s*=\s*).+", '${1}UTF-8'
            $changed = $true
        }
    }
    if ($changed) {
        Set-Content -Path $LoggingProps -Value $logging -Encoding UTF8
        Write-Host '[ztomcat] Applied UTF-8 encoding to logging.properties'
    }
}

$content = Get-Content -Path $ServerXml -Raw -Encoding UTF8
if ($content -notmatch 'URIEncoding="UTF-8"') {
    $content = $content -replace '(<Connector port="8080" protocol="HTTP/1\.1"\s*)', '$1URIEncoding="UTF-8" useBodyEncodingForURI="true" '
    Set-Content -Path $ServerXml -Value $content -Encoding UTF8 -NoNewline
    Write-Host '[ztomcat] Applied UTF-8 Connector settings to server.xml'
}

Write-Host '[ztomcat] Encoding config applied.'
