#!/usr/bin/env node
/**
 * Record a Gate result JSON under 70-REVIEW/gates/
 * Usage:
 *   node scripts/record_gate.cjs --id RUN-... --gate HG-20 --decision PENDING_APPROVAL --summary "BA 승인 대기"
 *   node scripts/record_gate.cjs --id RUN-... --gate HG-20 --decision PASS --summary "BA approved"
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const args = parseArgs(process.argv.slice(2));
const runId = args.id;
const gateId = args.gate;
const decision = args.decision || 'PENDING_APPROVAL';
const summary = args.summary || '';

const allowed = [
  'PASS',
  'PASS_WITH_EXCEPTION',
  'PENDING_APPROVAL',
  'FAIL',
  'BLOCKED',
  'NOT_APPLICABLE',
  'ERROR',
];

if (!runId || !gateId) {
  console.error('Usage: node scripts/record_gate.cjs --id RUN-... --gate HG-XX --decision PASS|...');
  process.exit(1);
}
if (!allowed.includes(decision)) {
  console.error(`Invalid decision. Allowed: ${allowed.join(', ')}`);
  process.exit(1);
}

const runDir = path.join(root, 'runs', runId);
if (!fs.existsSync(runDir)) {
  console.error(`Run not found: ${runId}`);
  process.exit(1);
}

const gateDir = path.join(runDir, '70-REVIEW', 'gates');
fs.mkdirSync(gateDir, { recursive: true });

const now = new Date().toISOString();
const gateResult = {
  gateResultId: `GTR-${gateId}-${Date.now()}`,
  runId,
  gateId,
  gateVersion: '1.0.0',
  decision,
  qualityScore: decision === 'PASS' || decision === 'PASS_WITH_EXCEPTION' ? 100 : 0,
  hardFailureCount: decision === 'FAIL' ? 1 : 0,
  requiredFailureCount: 0,
  evaluatedAt: now,
  summary,
  ruleResults: [],
  approvalIds: [],
  exceptionIds: [],
};

const outPath = path.join(gateDir, `${gateId}.json`);
atomicWrite(outPath, JSON.stringify(gateResult, null, 2) + '\n');

// append audit
const auditPath = path.join(runDir, '99-ARCHIVE', 'audit', 'gate-events.jsonl');
fs.mkdirSync(path.dirname(auditPath), { recursive: true });
fs.appendFileSync(auditPath, JSON.stringify({ at: now, ...gateResult }) + '\n', 'utf8');

console.log(`Wrote ${path.relative(root, outPath)}`);
console.log(`decision=${decision}`);
if (decision === 'PENDING_APPROVAL') {
  console.log('Next: obtain human approval, then re-run with --decision PASS and promote_stage.cjs');
}

function atomicWrite(file, content) {
  const tmp = file + '.tmp';
  fs.writeFileSync(tmp, content, 'utf8');
  fs.renameSync(tmp, file);
}

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
