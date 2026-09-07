const fs = require('node:fs');
const path = require('node:path');
const storyBuilder = require('../../아키텍처정의서(T3)-스토리 위주/NSIGHT_PDMG_STORY_ARCHITECTURE_COMPLETE_V1/story-html-builder');

function assembleSource(chapterSources, appendixSources) {
  const chapters = chapterSources.join('\n\n---\n\n');
  if (!appendixSources.length) return chapters;
  return `${chapters}\n\n---\n\n# 제14장. Appendix Reference\n\n${appendixSources.join('\n\n---\n\n')}`;
}

function buildReadableHtml(source, masterIndex, manifest) {
  const compatibleManifest = {
    chapter_count: manifest.chapter_count ?? manifest.chapters ?? 13,
    total_text_figures: manifest.total_text_figures ?? manifest.integrated_figures ?? 0,
    overall_architecture_definition: manifest.overall_architecture_definition ?? manifest.architecture_definition ?? 'FINAL',
  };
  return storyBuilder.buildHtml(source, masterIndex, compatibleManifest)
    .replaceAll('NSIGHT PDMG Story Architecture', 'NSIGHT / PDMG Architecture Book')
    .replaceAll('STORY + TEXT ARCHITECTURE · TOP-DOWN → DRILL-DOWN', 'FINAL V5 · INTERACTIVE ARCHITECTURE REFERENCE')
    .replaceAll('Story Architecture', 'Architecture Book')
    .replace('>14 Appendix Reference</button>', '>Appendix</button>')
    .replace('13개 장의 주제를 Architecture 흐름별로 보여줍니다.', '13개 Chapter와 Appendix를 Architecture 흐름별로 보여줍니다.')
    .replace('13개 Chapter의 전체 카드 보드에서', '13개 Chapter와 Appendix의 전체 카드 보드에서')
    .replace('Working Integrated Baseline', 'Final Architecture Definition Edition V5');
}

function loadMarkdownFiles(directory) {
  return fs.readdirSync(directory)
    .filter((name) => name.toLowerCase().endsWith('.md'))
    .sort((a, b) => a.localeCompare(b, 'en'))
    .map((name) => fs.readFileSync(path.join(directory, name), 'utf8'));
}

function main() {
  const directory = __dirname;
  const chapterSources = loadMarkdownFiles(path.join(directory, 'chapters'));
  const appendixSources = loadMarkdownFiles(path.join(directory, 'appendices'));
  const source = assembleSource(chapterSources, appendixSources);
  const masterIndex = fs.readFileSync(path.join(directory, '00_NSIGHT_PDMG_ARCHITECTURE_BOOK_MASTER_INDEX_FINAL_V5.md'), 'utf8');
  const manifest = JSON.parse(fs.readFileSync(path.join(directory, 'NSIGHT_PDMG_ARCHITECTURE_BOOK_MANIFEST_FINAL_V5.json'), 'utf8'));
  const html = buildReadableHtml(source, masterIndex, manifest);
  fs.writeFileSync(path.join(directory, 'NSIGHT_PDMG_ARCHITECTURE_BOOK_FINAL_V5.html'), html, 'utf8');
}

module.exports = { assembleSource, buildReadableHtml, loadMarkdownFiles };
if (require.main === module) main();
