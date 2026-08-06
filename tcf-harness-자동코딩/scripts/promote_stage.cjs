#!/usr/bin/env node
/**
 * Promote Run to next Workflow stage only if current Gate is PASS / PASS_WITH_EXCEPTION.
 * Usage: node scripts/promote_stage.cjs --id RUN-...
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const args = parseArgs(process.argv.slice(2));
const runId = args.id;
if (!runId) {
  console.error('Usage: node scripts/promote_stage.cjs --id RUN-...');
  process.exit(1);
}

const runDir = path.join(root, 'runs', runId);
const runYamlPath = path.join(runDir, 'run.yaml');
if (!fs.existsSync(runYamlPath)) {
  console.error('run.yaml missing');
  process.exit(1);
}

const runText = fs.readFileSync(runYamlPath, 'utf8');
const workflowId = matchField(runText, /workflow:\s*\r?\n\s*id:\s*([A-Z0-9-]+)/);
const rawStageId = matchField(runText, /currentStageId:\s*([A-Za-z0-9-]+)/);
const statusVersion = Number(matchField(runText, /status:[\s\S]*?\r?\n\s*version:\s*(\d+)/) || '1');

if (!workflowId || !rawStageId) {
  console.error('Cannot parse workflow.id or status.currentStageId from run.yaml');
  process.exit(1);
}

/** Legacy stage IDs from early harness drafts → 참고소스 WF-ONLINE-INQUIRY */
const STAGE_ALIASES = {
  'S30-ANALYSIS': 'S20-ANALYSIS',
  'S50-DESIGN': 'S30-DESIGN',
  'S70-PLAN': 'S40-PLAN',
  'S80-IMPLEMENT': 'S50-IMPLEMENT',
  'S90-BUILD': 'S60-BUILD',
  'S100-TEST': 'S70-TEST',
  'S110-QUALITY': 'S80-VERIFY',
  'S120-TRACE': 'S85-TRACE',
  'S130-FINAL': 'S90-FINAL',
};

const currentStageId = STAGE_ALIASES[rawStageId] || rawStageId;
if (currentStageId !== rawStageId) {
  console.log(`Normalized stage alias ${rawStageId} → ${currentStageId}`);
}

const workflowFile = resolveWorkflowFile(workflowId);
const stages = parseStages(fs.readFileSync(workflowFile, 'utf8'));
if (!stages.length) {
  console.error(`No stages parsed from ${workflowFile}`);
  process.exit(1);
}

const idx = stages.findIndex((s) => s.id === currentStageId);
if (idx < 0) {
  console.error(`Unknown currentStageId: ${currentStageId}`);
  console.error('Known stages:', stages.map((s) => s.id).join(', '));
  process.exit(1);
}

const current = stages[idx];
if (current.gate) {
  const gatePath = path.join(runDir, '70-REVIEW', 'gates', `${current.gate}.json`);
  if (!fs.existsSync(gatePath)) {
    console.error(`Gate result missing: ${gatePath}`);
    process.exit(1);
  }
  const gate = JSON.parse(fs.readFileSync(gatePath, 'utf8'));
  if (!['PASS', 'PASS_WITH_EXCEPTION'].includes(gate.decision)) {
    console.error(`Cannot promote: ${current.gate} decision=${gate.decision}`);
    process.exit(1);
  }
}

let nextText = runText;
if (idx >= stages.length - 1) {
  nextText = setStatus(nextText, { phase: 'COMPLETED', version: statusVersion + 1 });
  atomicWrite(runYamlPath, nextText);
  console.log('Run COMPLETED (last stage)');
  process.exit(0);
}

const next = stages[idx + 1];
const phase = phaseForStage(next.id);
nextText = setStatus(nextText, {
  currentStageId: next.id,
  phase,
  version: statusVersion + 1,
});
atomicWrite(runYamlPath, nextText);

const cpDir = path.join(runDir, '95-CHECKPOINT', 'state');
fs.mkdirSync(cpDir, { recursive: true });
atomicWrite(
  path.join(cpDir, `checkpoint-${Date.now()}.json`),
  JSON.stringify(
    {
      runId,
      from: current.id,
      to: next.id,
      at: new Date().toISOString(),
      stateVersion: statusVersion + 1,
    },
    null,
    2
  ) + '\n'
);

console.log(`Promoted ${current.id} → ${next.id} (phase=${phase})`);

/** Parse only top-level stage entries under spec.stages (4-space list items). */
function parseStages(wf) {
  const stagesBlock = wf.split(/\n\s*stages:\s*\n/)[1];
  if (!stagesBlock) return [];
  // stop at next top-level key if any
  const lines = stagesBlock.split(/\r?\n/);
  const stageChunks = [];
  let cur = null;
  for (const line of lines) {
    const stageStart = line.match(/^    - id:\s*([A-Za-z0-9-]+)\s*$/);
    if (stageStart) {
      if (cur) stageChunks.push(cur);
      cur = { id: stageStart[1], raw: line + '\n' };
      continue;
    }
    if (!cur) continue;
    // nested steps use 8+ spaces; keep collecting until next stage or dedent below 4 spaces content
    if (/^[a-zA-Z]/.test(line)) break;
    cur.raw += line + '\n';
  }
  if (cur) stageChunks.push(cur);

  return stageChunks
    .map((c) => {
      const gateMatch = c.raw.match(/^\s+gate:\s*([A-Z0-9-]+|null)\s*$/m);
      const orderMatch = c.raw.match(/^\s+order:\s*(\d+)\s*$/m);
      return {
        id: c.id,
        gate: !gateMatch || gateMatch[1] === 'null' ? null : gateMatch[1],
        order: orderMatch ? Number(orderMatch[1]) : 0,
      };
    })
    .sort((a, b) => a.order - b.order);
}

function setStatus(text, { currentStageId, phase, version }) {
  // Support LF and CRLF run.yaml
  const replaced = text.replace(/\r?\nstatus:\r?\n[\s\S]*$/m, (block) => {
    let b = block;
    if (currentStageId) {
      b = b.replace(/currentStageId:\s*[A-Za-z0-9-]+/, `currentStageId: ${currentStageId}`);
    }
    if (phase) {
      b = b.replace(/phase:\s*\w+/, `phase: ${phase}`);
    }
    if (version != null) {
      b = b.replace(/^(\s*)version:\s*\d+/m, `$1version: ${version}`);
    }
    return b;
  });
  if (replaced === text && (currentStageId || phase || version != null)) {
    throw new Error('Failed to update status block in run.yaml (CRLF/format mismatch)');
  }
  return replaced;
}

function resolveWorkflowFile(id) {
  const map = {
    'WF-ONLINE-INQUIRY': 'wf-online-inquiry-v1.yaml',
    'WF-CRUD': 'wf-crud-v1.yaml',
    'WF-NEW-BUSINESS-MODULE': 'wf-new-business-module-v1.yaml',
  };
  const file = path.join(root, 'harness', 'workflows', map[id] || 'wf-online-inquiry-v1.yaml');
  if (!fs.existsSync(file)) throw new Error(`workflow file missing: ${file}`);
  return file;
}

function phaseForStage(stageId) {
  if (stageId.includes('INPUT')) return 'REGISTERED';
  if (stageId.includes('BASELINE')) return 'BASELINED';
  if (stageId.includes('ANALYSIS')) return 'ANALYSIS_REVIEW';
  if (stageId.includes('DESIGN') || stageId.includes('PLAN')) return 'DESIGNING';
  if (stageId.includes('IMPLEMENT')) return 'IMPLEMENTING';
  if (stageId.includes('BUILD')) return 'BUILDING';
  if (stageId.includes('TEST') && !stageId.includes('TRACE')) return 'TESTING';
  if (stageId.includes('VERIFY') || stageId.includes('QUALITY') || stageId.includes('TRACE')) {
    return 'VERIFYING';
  }
  if (stageId.includes('FINAL') || stageId.includes('PACKAGE')) return 'PACKAGING';
  return 'RUNNING';
}

function matchField(text, re) {
  const m = text.match(re);
  return m ? m[1] : null;
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
