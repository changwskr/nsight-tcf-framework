#!/usr/bin/env node
/**
 * Validate Run workspace layout + run.yaml basics.
 * Usage: node scripts/validate_run.cjs --id RUN-...
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const args = parseArgs(process.argv.slice(2));
const runId = args.id;
if (!runId) {
  console.error('Usage: node scripts/validate_run.cjs --id RUN-...');
  process.exit(1);
}

const runDir = path.join(root, 'runs', runId);
const errors = [];
const warnings = [];

if (!fs.existsSync(runDir)) errors.push(`missing run dir: ${runDir}`);

const required = [
  'run.yaml',
  '00-IN',
  '10-BASELINE',
  '20-ANALYSIS',
  '30-DESIGN',
  '40-IMPLEMENTATION',
  '50-TEST',
  '60-EVIDENCE',
  '70-REVIEW/gates',
  '80-STAGING',
  '90-OUT',
  '95-CHECKPOINT',
  '99-ARCHIVE',
];

for (const rel of required) {
  if (!fs.existsSync(path.join(runDir, rel))) errors.push(`missing: ${rel}`);
}

const runYamlPath = path.join(runDir, 'run.yaml');
if (fs.existsSync(runYamlPath)) {
  const text = fs.readFileSync(runYamlPath, 'utf8');
  for (const key of ['runId:', 'workflow:', 'currentStageId:', 'phase:']) {
    if (!text.includes(key)) errors.push(`run.yaml missing key fragment: ${key}`);
  }
  if (!text.includes(runId)) warnings.push('run.yaml runId may not match folder name');
}

const workflowDir = path.join(root, 'harness', 'workflows');
const gateDir = path.join(root, 'harness', 'gate-rules');
const expectedGates = [
  'hg00-input.yaml',
  'hg10-baseline.yaml',
  'hg20-analysis.yaml',
  'hg30-design.yaml',
  'hg40-implementation.yaml',
  'hg50-build.yaml',
  'hg60-test.yaml',
  'hg70-security-quality.yaml',
  'hg80-trace-drift.yaml',
  'hg90-final.yaml',
];
for (const g of expectedGates) {
  if (!fs.existsSync(path.join(gateDir, g))) errors.push(`missing gate rule: ${g}`);
}
for (const w of ['wf-online-inquiry-v1.yaml', 'wf-crud-v1.yaml', 'wf-new-business-module-v1.yaml']) {
  if (!fs.existsSync(path.join(workflowDir, w))) warnings.push(`workflow missing: ${w}`);
}

const schemaDir = path.join(root, 'harness', 'schemas');
const expectedSchemas = [
  'common-definitions.schema.json',
  'task-input-envelope.schema.json',
  'task-output-envelope.schema.json',
  'artifact-manifest.schema.json',
  'workflow-definition.schema.json',
  'gate-rule-set.schema.json',
  'gate-result.schema.json',
  'run-create-request.schema.json',
  'requirement-register.schema.json',
];
for (const s of expectedSchemas) {
  if (!fs.existsSync(path.join(schemaDir, s))) errors.push(`missing schema: ${s}`);
}

const refDir = path.join(root, '참고소스');
if (!fs.existsSync(refDir)) warnings.push('참고소스/ missing — contract SoT unavailable');


console.log(`validate_run ${runId}`);
if (warnings.length) {
  console.log('WARN:');
  warnings.forEach((w) => console.log(' -', w));
}
if (errors.length) {
  console.log('FAIL:');
  errors.forEach((e) => console.log(' -', e));
  process.exit(1);
}
console.log('RESULT: PASS');

function parseArgs(argv) {
  const out = {};
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a.startsWith('--')) {
      const key = a.slice(2);
      const val = argv[i + 1] && !argv[i + 1].startsWith('--') ? argv[++i] : true;
      out[key] = val;
    }
  }
  return out;
}
