[CmdletBinding()]
param(
    [ValidateSet('Contracts', 'Preservation')]
    [string]$Mode = 'Preservation'
)

$ErrorActionPreference = 'Stop'

function Assert-PreservationVerifierRejects {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Root,

        [Parameter(Mandatory = $true)]
        [string]$MutationDescription
    )

    $previousErrorActionPreference = $ErrorActionPreference
    try {
        $ErrorActionPreference = 'Continue'
        & powershell -NoProfile -ExecutionPolicy Bypass -File (Join-Path $Root 'scripts/verify_codex_harness.ps1') -Root $Root 2>$null
        $verifierExitCode = $LASTEXITCODE
    }
    finally {
        $ErrorActionPreference = $previousErrorActionPreference
    }

    if ($verifierExitCode -eq 0) {
        throw "$MutationDescription was accepted by the verifier."
    }
}

function Test-Preservation {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Root
    )

    $manifestPath = Join-Path $Root 'preservation-manifest.json'
    $verifierPath = Join-Path $Root 'scripts/verify_codex_harness.ps1'

    if (-not (Test-Path -LiteralPath $manifestPath -PathType Leaf)) {
        throw "Missing preservation manifest: $manifestPath"
    }

    if (-not (Test-Path -LiteralPath $verifierPath -PathType Leaf)) {
        throw "Missing preservation verifier: $verifierPath"
    }

    $manifest = Get-Content -LiteralPath $manifestPath -Raw | ConvertFrom-Json
    if ($manifest.algorithm -ne 'SHA256') {
        throw "Unknown preservation hash algorithm: $($manifest.algorithm)"
    }

    foreach ($property in $manifest.files.PSObject.Properties) {
        $relativePath = $property.Name
        $expectedHash = [string]$property.Value
        $fullPath = Join-Path $Root ($relativePath -replace '/', [System.IO.Path]::DirectorySeparatorChar)

        if (-not (Test-Path -LiteralPath $fullPath -PathType Leaf)) {
            throw "Missing preserved file: $relativePath"
        }

        if ($expectedHash -notmatch '^[0-9A-Fa-f]{64}$') {
            throw "Unknown preservation hash for: $relativePath"
        }

        $actualHash = (Get-FileHash -LiteralPath $fullPath -Algorithm SHA256).Hash
        if (-not [string]::Equals($actualHash, $expectedHash, [System.StringComparison]::OrdinalIgnoreCase)) {
            throw "Preservation hash mismatch: $relativePath"
        }
    }

    $temporaryParent = Join-Path ([System.IO.Path]::GetTempPath()) ("tcf-harness-preservation-" + [System.Guid]::NewGuid().ToString('N'))
    $leafName = Split-Path -Leaf $Root
    $temporaryRoot = Join-Path $temporaryParent $leafName

    try {
        New-Item -ItemType Directory -Path $temporaryParent | Out-Null
        Copy-Item -LiteralPath $Root -Destination $temporaryParent -Recurse

        $mutatedFile = Join-Path $temporaryRoot 'CLAUDE.md'
        $stream = [System.IO.File]::Open($mutatedFile, [System.IO.FileMode]::Append, [System.IO.FileAccess]::Write)
        try {
            $stream.WriteByte(0x21)
        }
        finally {
            $stream.Dispose()
        }

        Assert-PreservationVerifierRejects -Root $temporaryRoot -MutationDescription 'Mutated preservation copy'

        $manifestMutationRoot = Join-Path $temporaryParent 'manifest-mutation'
        Copy-Item -LiteralPath $Root -Destination $manifestMutationRoot -Recurse

        $manifestMutationPath = Join-Path $manifestMutationRoot 'preservation-manifest.json'
        $manifestMutation = Get-Content -LiteralPath $manifestMutationPath -Raw | ConvertFrom-Json
        [void]$manifestMutation.files.PSObject.Properties.Remove('docs/UI_GUIDE.md')
        $manifestMutation | ConvertTo-Json -Depth 3 | Set-Content -LiteralPath $manifestMutationPath -Encoding utf8

        Assert-PreservationVerifierRejects -Root $manifestMutationRoot -MutationDescription 'Missing manifest entry copy'
    }
    finally {
        if (Test-Path -LiteralPath $temporaryParent) {
            Remove-Item -LiteralPath $temporaryParent -Recurse -Force
        }
    }
}

function Assert-ContainsExactToken {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Path,

        [Parameter(Mandatory = $true)]
        [string]$Token
    )

    $content = Get-Content -LiteralPath $Path -Raw
    if (-not $content.Contains($Token)) {
        throw "Missing required token '$Token': $Path"
    }
}

function Assert-RelativeMarkdownLinksResolve {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Path
    )

    $content = Get-Content -LiteralPath $Path -Raw
    foreach ($match in [regex]::Matches($content, '\[[^\]]+\]\(([^)]+)\)')) {
        $target = $match.Groups[1].Value.Trim()
        if ($target -match '^(#|[a-zA-Z][a-zA-Z0-9+.-]*:)') {
            continue
        }

        $pathWithoutAnchor = $target.Split('#')[0]
        if ([string]::IsNullOrWhiteSpace($pathWithoutAnchor)) {
            continue
        }

        $resolvedPath = Join-Path (Split-Path -Parent $Path) $pathWithoutAnchor
        if (-not (Test-Path -LiteralPath $resolvedPath)) {
            throw "Unresolved relative Markdown link '$target': $Path"
        }
    }
}

function Test-Contracts {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Root
    )

    $requiredFiles = @(
        'AGENTS.md'
        'README.md'
        'TOC.md'
        'toc.json'
        'scripts/sync_toc_chapters.cjs'
        'skills/book-chapter-agent/SKILL.md'
        'skills/book-development/SKILL.md'
        'skills/book-development/references/framework-source-map.md'
        'skills/book-development/references/handoff-protocol.md'
        'skills/book-development/references/chapter-template.md'
        'skills/book-research/SKILL.md'
        'skills/book-draft/SKILL.md'
        'skills/book-factcheck/SKILL.md'
        'skills/book-quality/SKILL.md'
    )

    foreach ($relativePath in $requiredFiles) {
        $fullPath = Join-Path $Root ($relativePath -replace '/', [System.IO.Path]::DirectorySeparatorChar)
        if (-not (Test-Path -LiteralPath $fullPath -PathType Leaf)) {
            throw "Missing book harness file: $relativePath"
        }
    }

    $agentsPath = Join-Path $Root 'AGENTS.md'
    foreach ($token in @(
        '../ztcfbook'
        'TOC.md'
        'toc.json'
        'chapters/{id}'
        'Do not invent'
        'IN/'
        'OUT/'
    )) {
        Assert-ContainsExactToken -Path $agentsPath -Token $token
    }

    $tocJsonPath = Join-Path $Root 'toc.json'
    $toc = Get-Content -LiteralPath $tocJsonPath -Raw -Encoding utf8 | ConvertFrom-Json
    if ($null -eq $toc.entries -or @($toc.entries).Count -lt 30) {
        throw "toc.json must list book entries (found $(@($toc.entries).Count))"
    }
    $chapterDirs = @(Get-ChildItem -LiteralPath (Join-Path $Root 'chapters') -Directory -ErrorAction Stop)
    if ($chapterDirs.Count -lt 30) {
        throw "chapters/ workspace count too low: $($chapterDirs.Count)"
    }
    $sampleDir = $chapterDirs | Where-Object { $_.Name -like 'CH-01-*' } | Select-Object -First 1
    if ($null -eq $sampleDir -or -not (Test-Path -LiteralPath (Join-Path $sampleDir.FullName 'TASK.md') -PathType Leaf)) {
        throw "Missing sample chapter TASK.md under chapters/CH-01-*"
    }

    $skills = @{
        'skills/book-chapter-agent/SKILL.md' = 'book-chapter-agent'
        'skills/book-development/SKILL.md' = 'book-development'
        'skills/book-research/SKILL.md' = 'book-research'
        'skills/book-draft/SKILL.md' = 'book-draft'
        'skills/book-factcheck/SKILL.md' = 'book-factcheck'
        'skills/book-quality/SKILL.md' = 'book-quality'
    }

    foreach ($relativePath in $skills.Keys) {
        $fullPath = Join-Path $Root ($relativePath -replace '/', [System.IO.Path]::DirectorySeparatorChar)
        $content = Get-Content -LiteralPath $fullPath -Raw
        if ($content -notmatch "(?s)\A---\s*\r?\nname: $([regex]::Escape($skills[$relativePath]))\s*\r?\n.*?\r?\n---") {
            throw "Missing YAML frontmatter name '$($skills[$relativePath])': $relativePath"
        }
    }

    $markdownContractFiles = @(
        'AGENTS.md'
        'skills/book-chapter-agent/SKILL.md'
        'skills/book-development/SKILL.md'
        'skills/book-development/references/framework-source-map.md'
        'skills/book-development/references/handoff-protocol.md'
        'skills/book-development/references/chapter-template.md'
        'skills/book-research/SKILL.md'
        'skills/book-draft/SKILL.md'
        'skills/book-factcheck/SKILL.md'
        'skills/book-quality/SKILL.md'
    )

    foreach ($relativePath in $markdownContractFiles) {
        $fullPath = Join-Path $Root ($relativePath -replace '/', [System.IO.Path]::DirectorySeparatorChar)
        Assert-RelativeMarkdownLinksResolve -Path $fullPath
    }
}

switch ($Mode) {
    'Preservation' {
        $root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
        Test-Preservation -Root $root
        Write-Output 'Preservation checks passed.'
    }
    'Contracts' {
        $root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
        Test-Contracts -Root $root
        Write-Output 'Book harness contract checks passed.'
    }
}
