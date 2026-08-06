#!/usr/bin/env node
/**
 * Sync contract package from 참고소스 → harness/ (robocopy-safe on Windows)
 * Usage: node scripts/sync_contracts_from_ref.cjs
 */
const { spawnSync } = require('child_process');
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const ref = path.join(root, '참고소스');
const dest = path.join(root, 'harness');

if (!fs.existsSync(ref)) {
  console.error('참고소스 not found');
  process.exit(1);
}

const dirs = [
  'workflows',
  'gate-rules',
  'schemas',
  'workspace',
  'api',
  'database',
  'architecture',
  'tools',
];
const files = ['README.md', 'FILE_INDEX.md', 'validation_report.txt', 'CHECKSUMS.sha256'];

fs.mkdirSync(dest, { recursive: true });

for (const d of dirs) {
  const s = path.join(ref, d);
  const t = path.join(dest, d);
  if (!fs.existsSync(s)) {
    console.log('SKIP', d);
    continue;
  }
  fs.mkdirSync(t, { recursive: true });
  // /E copy subdirs, /NFL /NDL quiet-ish, /NJH /NJS minimal
  const r = spawnSync(
    'robocopy',
    [s, t, '/E', '/IS', '/IT', '/NFL', '/NDL', '/NJH', '/NJS', '/NC', '/NS'],
    { encoding: 'utf8' }
  );
  // robocopy exit 0-7 = success
  if (r.status > 7) {
    console.error('robocopy failed', d, r.status, r.stderr);
    process.exit(1);
  }
  console.log('SYNC', d);
}

for (const f of files) {
  const s = path.join(ref, f);
  if (!fs.existsSync(s)) continue;
  fs.copyFileSync(s, path.join(dest, f));
  console.log('SYNC', f);
}

fs.writeFileSync(
  path.join(dest, 'CONTRACT_SOT.md'),
  `# Contract SoT\n\nSynced from \`참고소스/\` at ${new Date().toISOString()}\n\nRun: \`python harness/tools/validate_package.py\`\n`,
  'utf8'
);
console.log('Done.');
