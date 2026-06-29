# NSIGHT TCF — 현재 PowerShell 세션을 JDK 21로 맞춤
$ErrorActionPreference = 'Stop'

$candidates = @(
    (Join-Path $env:USERPROFILE '.jdks\temurin-21.0.4')
)
$candidates += Get-ChildItem 'C:\Program Files\Eclipse Adoptium' -Directory -Filter 'jdk-21*' -ErrorAction SilentlyContinue |
    ForEach-Object { $_.FullName }

foreach ($jdk in $candidates) {
    $java = Join-Path $jdk 'bin\java.exe'
    if (Test-Path $java) {
        $env:JAVA_HOME = $jdk
        $env:Path = "$jdk\bin;" + ($env:Path -split ';' | Where-Object {
            $_ -and $_ -notmatch '\\jdk-1[78]\.0'
        } | Select-Object -Unique) -join ';'
        Write-Host "[env-jdk21] JAVA_HOME=$jdk"
        & $java -version
        return
    }
}

Write-Error '[env-jdk21] JDK 21 not found. Install Temurin 21 to $env:USERPROFILE\.jdks\temurin-21.0.4'
