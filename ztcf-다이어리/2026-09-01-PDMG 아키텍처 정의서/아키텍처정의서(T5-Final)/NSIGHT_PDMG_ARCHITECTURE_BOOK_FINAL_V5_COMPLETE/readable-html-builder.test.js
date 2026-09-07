const assert = require('node:assert/strict');
const {
  assembleSource,
  buildReadableHtml,
} = require('./readable-html-builder');

const source = assembleSource(
  ['# 제1장. 첫 장\n# 0. 개요\n본문'],
  ['# APPENDIX A. Registry\n\n부록 본문'],
);
assert.match(source, /# 제1장\. 첫 장/);
assert.match(source, /# 제14장\. Appendix Reference/);
assert.match(source, /# APPENDIX A\. Registry/);

const html = buildReadableHtml(source, '# MASTER INDEX\n소개', {
  chapter_count: 1,
  total_text_figures: 1,
  overall_architecture_definition: 'PASS',
});
assert.match(html, /NSIGHT \/ PDMG Architecture Book/);
assert.match(html, />Appendix<\/button>/);
assert.match(html, /class="chapter-sidebar"/);
assert.match(html, /class="page-prev"/);
assert.match(html, /class="page-next"/);
assert.match(html, /class="next-index"/);

console.log('readable-html-builder tests: PASS');
