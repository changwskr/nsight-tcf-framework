const assert = require('node:assert/strict');
const {
  escapeHtml,
  markdownToHtml,
  splitChapters,
  splitTopics,
  buildChapterTabs,
  buildHtml,
} = require('./story-html-builder');

assert.equal(escapeHtml('<A & B>'), '&lt;A &amp; B&gt;');
assert.match(markdownToHtml('# 제목\n\n```text\nA → B\n```'), /<pre><code>A → B<\/code><\/pre>/);

const chapters = splitChapters([
  '# 제1장. 왜 다시 짓는가',
  '# 0. 이 장의 Story',
  '첫 내용',
  '# 1. 장 전체 대표 Architecture',
  '둘째 내용',
  '# 제2장. 정보계 패러다임의 전환',
  '# 0. 이 장의 Story',
  '다음 장',
].join('\n'));
assert.equal(chapters.length, 2);
assert.equal(chapters[0].number, 1);
assert.equal(chapters[1].title, '정보계 패러다임의 전환');
assert.equal(splitTopics(chapters[0].body).length, 2);

const tabs = buildChapterTabs(chapters);
assert.match(tabs, /data-chapter="all"/);
assert.match(tabs, /data-chapter="1"/);
assert.match(tabs, /01 왜 다시 짓는가/);

const page = buildHtml([
  '# 제1장. 왜 다시 짓는가',
  '# 0. 이 장의 Story',
  '카드의 전체 내용',
].join('\n'), '# MASTER INDEX\n소개', {
  chapter_count: 1,
  total_text_figures: 1,
  overall_architecture_definition: 'PASS',
});
assert.match(page, /id="detailView"/);
assert.match(page, /class="detail-back"/);
assert.match(page, /function showDetail\(/);
assert.match(page, /function returnToBoard\(/);
assert.match(page, /data-topic-id="1-1"/);
assert.doesNotMatch(page, /class="expand"/);

const linkedPage = buildHtml([
  '# 제1장. 첫 장', '# 0. 첫 주제', '첫 내용',
  '# 제2장. 다음 장', '# 0. 다음 주제', '다음 내용',
].join('\n'), '# INDEX', {
  chapter_count: 2, total_text_figures: 2, overall_architecture_definition: 'PASS',
});
assert.match(linkedPage, /class="next-index"/);
assert.equal((linkedPage.match(/data-chapter-last="true"/g) || []).length, 2);
assert.match(linkedPage, /data-next-chapter="2"/);
assert.match(linkedPage, /function updateNextIndex\(/);
assert.match(linkedPage, /전체 Architecture로 돌아가기/);
assert.match(linkedPage, /class="chapter-sidebar"/);
assert.match(linkedPage, /id="chapterPageMenu"/);
assert.match(linkedPage, /class="page-prev"/);
assert.match(linkedPage, /class="page-next"/);
assert.match(linkedPage, /function renderChapterMenu\(/);
assert.match(linkedPage, /function updatePageNavigation\(/);

console.log('story-html-builder tests: PASS');
