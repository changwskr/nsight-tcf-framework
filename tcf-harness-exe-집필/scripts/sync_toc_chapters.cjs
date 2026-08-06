#!/usr/bin/env node
'use strict';

/**
 * Sync harness TOC + per-chapter agent workspaces from ztcfbook/_gen-book-chapters.cjs
 *
 * Usage (from repo root or harness root):
 *   node tcf-harness-exe-집필/scripts/sync_toc_chapters.cjs
 *   node scripts/sync_toc_chapters.cjs
 */

const fs = require('fs');
const path = require('path');

const HARNESS = path.resolve(__dirname, '..');
const REPO = path.resolve(HARNESS, '..');
const GEN = path.join(REPO, 'ztcfbook', '_gen-book-chapters.cjs');
const OUT_TOC_JSON = path.join(HARNESS, 'toc.json');
const OUT_TOC_MD = path.join(HARNESS, 'TOC.md');
const CHAPTERS_DIR = path.join(HARNESS, 'chapters');

function loadBookAndAppendix() {
  const src = fs.readFileSync(GEN, 'utf8');
  const bookMatch = src.match(/const BOOK = (\[[\s\S]*?\n\];)/);
  if (!bookMatch) throw new Error('BOOK array not found in _gen-book-chapters.cjs');
  const BOOK = eval(bookMatch[1].replace(/;\s*$/, ''));

  // APPENDICES block: const APPENDICES = [ ... ];
  const appMatch = src.match(/const APPENDICES = (\[[\s\S]*?\n\];)/);
  const APPENDICES = appMatch ? eval(appMatch[1].replace(/;\s*$/, '')) : [];
  return { BOOK, APPENDICES };
}

function pad2(n) {
  return String(n).padStart(2, '0');
}

function workspaceId(kind, no, slug) {
  if (kind === 'appendix') return `APP-${no}-${slug}`;
  if (no === 0 || no === '0') return `CH-00-${slug}`;
  return `CH-${pad2(no)}-${slug}`;
}

function ensureDir(p) {
  fs.mkdirSync(p, { recursive: true });
}

function writeIfMissing(file, contents) {
  if (!fs.existsSync(file)) fs.writeFileSync(file, contents, 'utf8');
}

function chapterExists(relTarget) {
  return fs.existsSync(path.join(REPO, 'ztcfbook', relTarget));
}

function buildEntries(BOOK, APPENDICES) {
  const entries = [];
  let order = 0;

  for (const part of BOOK) {
    for (const ch of part.chapters) {
      order += 1;
      const target = `${part.dir}/${ch.slug}.md`;
      const id = workspaceId('chapter', ch.no, ch.slug);
      const sections = (ch.sections || []).map((s) => ({
        id: s.id,
        title: s.title,
        sources: s.sources || [],
      }));
      const allSources = [...new Set(sections.flatMap((s) => s.sources))];
      const exists = chapterExists(target);
      entries.push({
        order,
        id,
        kind: 'chapter',
        part: part.dir,
        partTitle: part.partTitle,
        no: ch.no,
        title: ch.title,
        slug: ch.slug,
        target: `../ztcfbook/${target}`,
        workspace: `chapters/${id}`,
        sections,
        sources: allSources,
        status: id === 'CH-01-01-NSIGHT-TCF란-무엇인가' && exists
          ? 'completed'
          : exists
            ? 'draft-exists'
            : 'pending',
        agent: 'book-chapter-agent',
      });
    }
  }

  for (const app of APPENDICES) {
    order += 1;
    const target = `부록/${app.slug}.md`;
    const id = workspaceId('appendix', app.id, app.slug);
    const sources = app.sources || [];
    const exists = chapterExists(target);
    entries.push({
      order,
      id,
      kind: 'appendix',
      part: '부록',
      partTitle: '부록',
      no: app.id,
      title: app.title,
      slug: app.slug,
      target: `../ztcfbook/${target}`,
      workspace: `chapters/${id}`,
      sections: [{ id: String(app.id), title: app.title, sources }],
      sources,
      status: exists ? 'draft-exists' : 'pending',
      agent: 'book-chapter-agent',
    });
  }

  // Appendices K-N may exist on disk but not in APPENDICES array
  const known = new Set(entries.filter((e) => e.part === '부록').map((e) => e.slug));
  const appendixDir = path.join(REPO, 'ztcfbook', '부록');
  if (fs.existsSync(appendixDir)) {
    for (const name of fs.readdirSync(appendixDir).filter((n) => n.endsWith('.md') && n !== 'README.md')) {
      const slug = name.replace(/\.md$/, '');
      if (known.has(slug)) continue;
      const letter = slug.split('-')[0];
      order += 1;
      const id = `APP-${letter}-${slug}`;
      entries.push({
        order,
        id,
        kind: 'appendix',
        part: '부록',
        partTitle: '부록',
        no: letter,
        title: slug.replace(/^[A-Z]-/, '').replace(/-/g, ' '),
        slug,
        target: `../ztcfbook/부록/${name}`,
        workspace: `chapters/${id}`,
        sections: [],
        sources: [],
        status: 'draft-exists',
        agent: 'book-chapter-agent',
        note: 'disk-only; not in _gen APPENDICES',
      });
    }
  }

  return entries;
}

function renderTaskMd(entry) {
  const sectionLines = (entry.sections || [])
    .map((s) => `- **${s.id}** ${s.title}\n  - sources: ${(s.sources || []).map((x) => `\`${x}\``).join(', ') || '(none)'}`)
    .join('\n');
  const sourceLines = (entry.sources || []).map((s) => `- \`${s}\``).join('\n') || '- (조사 후 절별 출처를 본문에 명시)';

  return `# TASK: ${entry.id}

## 이 작업은 책을 쓰는 일이다

산출물의 중심은 \`IN/\`·\`OUT/\` 메모가 아니라 **원고 본문** \`${entry.target}\` 이다.  
독자가 이 장만 읽어도 개념·흐름·코드·실수·검증까지 이해할 수 있게 **자세하고 풍부하게** 쓴다.

| 항목 | 값 |
| --- | --- |
| 목차 ID | \`${entry.id}\` |
| 편 | ${entry.partTitle} (\`${entry.part}\`) |
| 번호 | ${entry.no} |
| 제목 | ${entry.title} |
| 원고 | \`${entry.target}\` |
| 에디션 | \`ztcfbook\` (변경 시 승인) |

## 집필 목표 (풍부함)

1. **왜** 이 장이 필요한지 서문으로 연다 (문제·맥락·독자).
2. 절마다 **설명 + 표/흐름 + 실제 코드·경로 발췌 + 주의(실수) + 확인 방법**을 둔다.
3. 코드는 저장소 **실파일**을 인용한다. 가상 예시면 \`(예시)\`를 붙인다.
4. 설계서와 코드가 다르면 Gap을 숨기지 말고 본문에 쓴다.
5. 분량: 실습·핵심 장은 **얇은 요약서가 아니라 가이드 챕터** 수준 (여러 소절·풍부한 서술).
6. 장 말미: 장 요약 · 이전/다음 · 출처 색인.

\`analysis-summary.md\` 같은 핸드오프 파일을 만들지 마라. 검증 메모가 필요하면 본문 「디버깅·검증」 절에 녹인다. 상태는 \`toc.json\`만 갱신한다.

## 절 구성

${sectionLines || '- (부록 — 절은 조사 후 본문에서 확정)'}

필요하면 절을 **세분**해도 된다 (예: 22.2.1 Handler, 22.2.2 Facade…). 목차 대절 ID는 유지하되 소절을 풍부히 한다.

## 읽을 출처

${sourceLines}

코드 SoT 후보: \`sv-service\`, \`tcf-core\`, \`tcf-web\`, \`*-service\` 등 해당 장 주제 모듈.

## 규칙

1. CRITICAL: 출처·코드에 없는 ServiceId·포트·SQL·패키지 창작 금지.
2. CRITICAL: \`node _gen-book-*.cjs\` 무단 실행 금지.
3. CRITICAL: 이 항목의 \`target\`만 수정. 다른 장 동시 개편 금지.
4. CRITICAL: \`IN/\`·\`OUT/\` 디렉터리를 새로 만들지 마라.
5. 문체: \`docs/UI_GUIDE.md\` (풍부한 서술 가이드).

## 완료 조건

- \`${entry.target}\` 이 위 「집필 목표」를 충족
- 출처 색인·네비 유지
- \`toc.json\` 해당 항목 \`status=completed\` 후 \`node scripts/sync_toc_chapters.cjs\`
`;
}

function removeDirIfExists(p) {
  if (fs.existsSync(p)) fs.rmSync(p, { recursive: true, force: true });
}

function scaffoldWorkspace(entry) {
  const root = path.join(HARNESS, entry.workspace);
  ensureDir(root);
  // Book-first: do not recreate IN/OUT work-order folders
  removeDirIfExists(path.join(root, 'IN'));
  removeDirIfExists(path.join(root, 'OUT'));
  fs.writeFileSync(path.join(root, 'TASK.md'), renderTaskMd(entry), 'utf8');
}

function renderTocMd(toc) {
  const byPart = new Map();
  for (const e of toc.entries) {
    const key = e.partTitle || e.part;
    if (!byPart.has(key)) byPart.set(key, []);
    byPart.get(key).push(e);
  }

  const lines = [];
  lines.push('# NSIGHT-TCF 책 집필 목차 (Harness SoT)');
  lines.push('');
  lines.push('> 에이전트는 **이 목차의 항목 단위**로만 집필한다. 기계용 SoT: [`toc.json`](./toc.json)');
  lines.push('> 원고 출력: `../ztcfbook/` · 동기화: `node scripts/sync_toc_chapters.cjs`');
  lines.push('');
  lines.push('| 항목 | 값 |');
  lines.push('| --- | --- |');
  lines.push(`| 생성 시각 | ${toc.generatedAt} |`);
  lines.push(`| 항목 수 | ${toc.entries.length} |`);
  lines.push(`| 에디션 | ${toc.edition} |`);
  lines.push('');
  lines.push('## 상태 범례');
  lines.push('');
  lines.push('| status | 의미 |');
  lines.push('| --- | --- |');
  lines.push('| `pending` | 작업 대기 |');
  lines.push('| `draft-exists` | ztcfbook에 원고 있음 · 하네스 검수/갱신 가능 |');
  lines.push('| `in_progress` | 에이전트 작업 중 |');
  lines.push('| `completed` | research~quality 완료 |');
  lines.push('| `blocked` | 승인·출처 충돌 등으로 중단 |');
  lines.push('');
  lines.push('## 에이전트 실행 방법');
  lines.push('');
  lines.push('1. `TOC.md` / `toc.json`에서 항목 선택');
  lines.push('2. `chapters/{id}/TASK.md` 읽기');
  lines.push('3. **풍부한 책 본문**을 `target`(ztcfbook)에 집필 — IN/OUT 메모 금지');
  lines.push('4. 완료 후 `toc.json` status 갱신 후 sync 재실행');
  lines.push('');
  lines.push('채팅 예시: `TOC의 CH-22 조회 거래를 자세하고 풍부하게 집필해줘`');
  lines.push('');

  for (const [partTitle, list] of byPart) {
    lines.push(`## ${partTitle}`);
    lines.push('');
    lines.push('| ID | No | 제목 | status | workspace | target |');
    lines.push('| --- | --- | --- | --- | --- | --- |');
    for (const e of list) {
      const title = e.title.replace(/\|/g, '\\|');
      lines.push(
        `| [\`${e.id}\`](./${e.workspace}/TASK.md) | ${e.no} | ${title} | \`${e.status}\` | [\`${e.workspace}\`](./${e.workspace}/) | \`${e.target}\` |`,
      );
    }
    lines.push('');
  }

  lines.push('## 통계');
  lines.push('');
  const counts = {};
  for (const e of toc.entries) counts[e.status] = (counts[e.status] || 0) + 1;
  for (const [k, v] of Object.entries(counts).sort()) lines.push(`- \`${k}\`: ${v}`);
  lines.push('');
  return lines.join('\n');
}

function preserveStatuses(oldToc, entries) {
  if (!oldToc || !Array.isArray(oldToc.entries)) return entries;
  const map = new Map(oldToc.entries.map((e) => [e.id, e.status]));
  return entries.map((e) => {
    const prev = map.get(e.id);
    if (!prev) return e;
    // keep completed/in_progress/blocked; refresh draft-exists/pending from disk unless completed
    if (prev === 'completed' || prev === 'in_progress' || prev === 'blocked') {
      return { ...e, status: prev };
    }
    return e;
  });
}

function main() {
  const { BOOK, APPENDICES } = loadBookAndAppendix();
  let entries = buildEntries(BOOK, APPENDICES);

  let oldToc = null;
  if (fs.existsSync(OUT_TOC_JSON)) {
    try {
      oldToc = JSON.parse(fs.readFileSync(OUT_TOC_JSON, 'utf8'));
    } catch (_) {
      oldToc = null;
    }
  }
  entries = preserveStatuses(oldToc, entries);

  // Mark CH-01 completed if sample phase completed
  const samplePhase = path.join(HARNESS, 'phases', '1-sample-chapter-01', 'index.json');
  if (fs.existsSync(samplePhase)) {
    try {
      const idx = JSON.parse(fs.readFileSync(samplePhase, 'utf8'));
      const allDone = (idx.steps || []).every((s) => s.status === 'completed');
      if (allDone) {
        entries = entries.map((e) =>
          e.id.startsWith('CH-01-') ? { ...e, status: 'completed', lastPhase: '1-sample-chapter-01' } : e,
        );
      }
    } catch (_) {}
  }

  ensureDir(CHAPTERS_DIR);
  for (const e of entries) scaffoldWorkspace(e);

  const toc = {
    version: 1,
    edition: 'ztcfbook',
    sourceMap: '../ztcfbook/_gen-book-chapters.cjs',
    generatedAt: new Date().toISOString(),
    workflow: ['research', 'outline-approval', 'draft', 'fact-check', 'quality'],
    agentSkill: 'book-chapter-agent',
    entries,
  };

  fs.writeFileSync(OUT_TOC_JSON, JSON.stringify(toc, null, 2) + '\n', 'utf8');
  fs.writeFileSync(OUT_TOC_MD, renderTocMd(toc), 'utf8');

  console.log(`Wrote ${OUT_TOC_JSON}`);
  console.log(`Wrote ${OUT_TOC_MD}`);
  console.log(`Scaffolded ${entries.length} chapter workspaces under chapters/`);
}

main();
