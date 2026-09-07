const assert = require('node:assert/strict');
const {
  escapeHtml,
  inlineMarkdown,
  markdownToHtml,
  splitChapters,
  classifyChapter,
} = require('./html-builder');

assert.equal(escapeHtml('<tag>&"'), '&lt;tag&gt;&amp;&quot;');
assert.equal(inlineMarkdown('**강조**와 `code`'), '<strong>강조</strong>와 <code>code</code>');

const rendered = markdownToHtml([
  '# 제목',
  '',
  '| 구분 | 상태 |',
  '|---|---|',
  '| Architecture | PASS |',
  '',
  '```text',
  'A → B',
  '```',
].join('\n'));
assert.match(rendered, /<h1[^>]*>제목<\/h1>/);
assert.match(rendered, /<table>/);
assert.match(rendered, /<pre><code>A → B/);

const chapters = splitChapters([
  '# 서문',
  'intro',
  '# 제1장. 왜 다시 짓는가',
  'one',
  '# 제2장. 정보계 패러다임의 전환',
  'two',
].join('\n'));
assert.equal(chapters.length, 3);
assert.equal(chapters[1].number, 1);
assert.equal(chapters[2].title, '정보계 패러다임의 전환');

assert.equal(classifyChapter(4, 'Big Picture'), 'overview');
assert.equal(classifyChapter(10, '데이터플랫폼'), 'data');
assert.equal(classifyChapter(12, 'BI 포탈'), 'bi');

console.log('html-builder tests: PASS');
