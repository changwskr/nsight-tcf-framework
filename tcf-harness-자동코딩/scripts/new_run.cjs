#!/usr/bin/env node
/**
 * Create a new Run workspace under runs/{runId}/
 * Usage:
 *   node scripts/new_run.cjs --id RUN-20260806-0001 --workflow WF-ONLINE-INQUIRY
 *   node scripts/new_run.cjs --id RUN-... --workflow WF-CRUD --business AV --module av-service
 */
const fs = require('fs');
const path = require('path');
const crypto = require('crypto');

const root = path.resolve(__dirname, '..');
const args = parseArgs(process.argv.slice(2));

const runId = args.id || defaultRunId();
const workflowId = args.workflow || 'WF-ONLINE-INQUIRY';
const businessCode = args.business || 'AV';
const moduleName = args.module || 'av-service';
const branch = args.branch || 'develop';
const commitSha = args.commit || '0000000000000000000000000000000000000000';

const runDir = path.join(root, 'runs', runId);
if (fs.existsSync(runDir)) {
  console.error(`Run already exists: ${runDir}`);
  process.exit(1);
}

const dirs = [
  '00-IN/requirements',
  '00-IN/source',
  '00-IN/database',
  '00-IN/reference',
  '00-IN/constraints',
  '00-IN/quarantine',
  '10-BASELINE',
  '20-ANALYSIS/requirements',
  '20-ANALYSIS/domain',
  '20-ANALYSIS/assumptions',
  '20-ANALYSIS/gaps',
  '20-ANALYSIS/evidence',
  '20-ANALYSIS/trace',
  '30-DESIGN/architecture',
  '30-DESIGN/screen',
  '30-DESIGN/transaction',
  '30-DESIGN/program',
  '30-DESIGN/data',
  '30-DESIGN/interface',
  '30-DESIGN/security',
  '30-DESIGN/operation',
  '30-DESIGN/adr',
  '30-DESIGN/plan',
  '40-IMPLEMENTATION/worktree',
  '40-IMPLEMENTATION/generated/source',
  '40-IMPLEMENTATION/generated/resources',
  '40-IMPLEMENTATION/generated/mapper',
  '40-IMPLEMENTATION/generated/sql',
  '40-IMPLEMENTATION/generated/config',
  '40-IMPLEMENTATION/generated/om',
  '40-IMPLEMENTATION/patches',
  '40-IMPLEMENTATION/diff',
  '40-IMPLEMENTATION/rejected',
  '50-TEST/unit',
  '50-TEST/integration',
  '50-TEST/contract',
  '50-TEST/architecture',
  '50-TEST/security',
  '50-TEST/performance',
  '50-TEST/fixtures',
  '60-EVIDENCE/build',
  '60-EVIDENCE/test',
  '60-EVIDENCE/quality',
  '60-EVIDENCE/security',
  '60-EVIDENCE/trace',
  '60-EVIDENCE/drift',
  '60-EVIDENCE/runtime',
  '60-EVIDENCE/tool-invocations',
  '70-REVIEW/gates',
  '70-REVIEW/approvals',
  '70-REVIEW/exceptions',
  '70-REVIEW/issues',
  '70-REVIEW/comments',
  '80-STAGING/documents',
  '80-STAGING/source',
  '80-STAGING/sql',
  '80-STAGING/configuration',
  '80-STAGING/evidence',
  '90-OUT/documents',
  '90-OUT/source-package',
  '90-OUT/database-package',
  '90-OUT/om-package',
  '90-OUT/test-package',
  '90-OUT/evidence-package',
  '90-OUT/final-report',
  '95-CHECKPOINT/state',
  '95-CHECKPOINT/manifests',
  '95-CHECKPOINT/recovery',
  '99-ARCHIVE/audit',
  '99-ARCHIVE/logs',
];

for (const d of dirs) {
  fs.mkdirSync(path.join(runDir, d), { recursive: true });
  const keep = path.join(runDir, d, '.gitkeep');
  if (!fs.existsSync(keep)) fs.writeFileSync(keep, '');
}

const now = new Date().toISOString().replace(/\.\d{3}Z$/, '+00:00');
const runYaml = `apiVersion: harness.nsight/v1
kind: HarnessRun
metadata:
  runId: ${runId}
  projectId: NSIGHT-TCF
  createdAt: '${now}'
  createdBy: harness-user
spec:
  workflow:
    id: ${workflowId}
    version: 1.0.0
  baseline:
    repository: nsight-tcf-framework
    branch: ${branch}
    commitSha: ${commitSha}
  scope:
    businessCode: ${businessCode}
    domain: TBD
    targetModules:
      - ${moduleName}
  policies:
    autoRetry: true
    maxParallelTasks: 3
    networkPolicy: DENY_BY_DEFAULT
    promotionMode: GATE_CONTROLLED
status:
  phase: REGISTERED
  currentStageId: S00-INPUT
  version: 1
`;

fs.writeFileSync(path.join(runDir, 'run.yaml'), runYaml, 'utf8');
fs.writeFileSync(
  path.join(runDir, '00-IN', 'requirements', 'README.md'),
  `# ${runId} 입력 요구사항\n\n이 폴더에 원본 요구자료를 넣고 Intake Stage를 진행하세요.\n`,
  'utf8'
);
fs.writeFileSync(
  path.join(runDir, 'TASK.md'),
  `# ${runId}\n\n- Workflow: \`${workflowId}\`\n- Business: \`${businessCode}\`\n- Module: \`${moduleName}\`\n- Current: \`S00-INPUT\` / HG-00\n\n## Next\n\n1. Put requirement files into \`00-IN/requirements/\`\n2. Ask agent: \"Run ${runId} Intake(HG-00) 진행해줘\"\n`,
  'utf8'
);

console.log(`Created ${path.relative(root, runDir)}`);
console.log(`Workflow=${workflowId} stage=S00-INPUT`);

function defaultRunId() {
  const d = new Date();
  const y = d.getFullYear();
  const m = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const suffix = crypto.randomBytes(2).toString('hex');
  return `RUN-${y}${m}${day}-${suffix}`;
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
