#!/usr/bin/env node
'use strict';

const fs = require('fs');
const path = require('path');
const os = require('os');
const { execSync } = require('child_process');

const guideDir = process.argv[2] || path.join(__dirname, '..', 'znsight-guide');
const outDir = process.argv[3] || path.join(__dirname, '_docx-cache');

if (!fs.existsSync(outDir)) fs.mkdirSync(outDir, { recursive: true });

function getDocxText(filePath) {
  const escaped = filePath.replace(/'/g, "''");
  const tmpOut = path.join(os.tmpdir(), `docx-out-${process.pid}-${Math.random().toString(36).slice(2)}.txt`);
  const tmpPs = path.join(os.tmpdir(), `docx-extract-${process.pid}-${Math.random().toString(36).slice(2)}.ps1`);
  const script = `Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [System.IO.Compression.ZipFile]::OpenRead('${escaped}')
try {
  $entry = $zip.Entries | Where-Object { $_.FullName -eq 'word/document.xml' } | Select-Object -First 1
  $sr = New-Object System.IO.StreamReader($entry.Open(), [System.Text.Encoding]::UTF8)
  try { $xml = $sr.ReadToEnd() } finally { $sr.Close() }
} finally { $zip.Dispose() }
$text = $xml -replace '</w:p>', [char]10 -replace '<[^>]+>', ''
$text = $text -replace '&amp;','&' -replace '&lt;','<' -replace '&gt;','>' -replace '&quot;','"'
[System.IO.File]::WriteAllText('${tmpOut.replace(/\\/g, '\\\\')}', $text, [System.Text.UTF8Encoding]::new($false))
`;
  fs.writeFileSync(tmpPs, `\uFEFF${script}`, 'utf8');
  try {
    execSync(`powershell -NoProfile -ExecutionPolicy Bypass -File "${tmpPs}"`, {
      encoding: 'utf8',
      maxBuffer: 20 * 1024 * 1024,
    });
    return fs.readFileSync(tmpOut, 'utf8');
  } finally {
    for (const f of [tmpPs, tmpOut]) {
      try {
        fs.unlinkSync(f);
      } catch {
        /* ignore */
      }
    }
  }
}

const main = {};
const naming = {};

for (const name of fs.readdirSync(guideDir)) {
  if (!name.endsWith('.docx')) continue;
  const m = name.match(/\((\d+)\)\.docx$/);
  if (!m) continue;
  const n = parseInt(m[1], 10);
  const full = path.join(guideDir, name);
  const isNaming = /명명규칙/.test(name);
  const bucket = isNaming ? naming : main;
  bucket[n] = full;
}

for (const [n, file] of Object.entries(main).sort((a, b) => a[0] - b[0])) {
  const text = getDocxText(file);
  const out = path.join(outDir, `docx-${n}.txt`);
  fs.writeFileSync(out, text, 'utf8');
  console.log(`Extracted docx-${n}.txt (${text.length} bytes) <- ${path.basename(file)}`);
}

for (const [n, file] of Object.entries(naming).sort((a, b) => a[0] - b[0])) {
  const text = getDocxText(file);
  const out = path.join(outDir, `naming-${n}.txt`);
  fs.writeFileSync(out, text, 'utf8');
  console.log(`Extracted naming-${n}.txt (${text.length} bytes) <- ${path.basename(file)}`);
}
