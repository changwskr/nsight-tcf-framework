const fs = require('node:fs');
const path = require('node:path');

function escapeHtml(value) {
  return String(value)
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;');
}

function inlineMarkdown(value) {
  let text = escapeHtml(value);
  const stash = [];
  text = text.replace(/`([^`]+)`/g, (_, code) => {
    stash.push(`<code>${code}</code>`);
    return `\u0000${stash.length - 1}\u0000`;
  });
  text = text
    .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>')
    .replace(/\*([^*]+)\*/g, '<em>$1</em>')
    .replace(/\[([^\]]+)\]\(([^)]+)\)/g, '<a href="$2">$1</a>');
  return text.replace(/\u0000(\d+)\u0000/g, (_, index) => stash[Number(index)]);
}

function slugify(text) {
  return text.toLowerCase().replace(/<[^>]+>/g, '').replace(/[^\p{L}\p{N}]+/gu, '-').replace(/^-|-$/g, '');
}

function markdownToHtml(markdown) {
  const lines = markdown.replace(/\r/g, '').split('\n');
  const out = [];
  let paragraph = [];
  let listType = null;
  let inFence = false;
  let fence = [];

  const flushParagraph = () => {
    if (paragraph.length) out.push(`<p>${inlineMarkdown(paragraph.join(' '))}</p>`);
    paragraph = [];
  };
  const closeList = () => {
    if (listType) out.push(`</${listType}>`);
    listType = null;
  };

  for (let i = 0; i < lines.length; i += 1) {
    const line = lines[i];
    if (/^```/.test(line)) {
      flushParagraph(); closeList();
      if (inFence) {
        out.push(`<pre><code>${escapeHtml(fence.join('\n'))}</code></pre>`);
        fence = [];
      }
      inFence = !inFence;
      continue;
    }
    if (inFence) { fence.push(line); continue; }

    const heading = line.match(/^(#{1,6})\s+(.+)$/);
    if (heading) {
      flushParagraph(); closeList();
      const level = heading[1].length;
      const content = inlineMarkdown(heading[2]);
      out.push(`<h${level} id="${slugify(heading[2])}">${content}</h${level}>`);
      continue;
    }
    if (/^\s*---+\s*$/.test(line)) { flushParagraph(); closeList(); out.push('<hr>'); continue; }
    if (/^\|.*\|\s*$/.test(line) && /^\|?\s*:?-+/.test(lines[i + 1] || '')) {
      flushParagraph(); closeList();
      const rows = [];
      const cells = (row) => row.trim().replace(/^\||\|$/g, '').split('|').map((cell) => cell.trim());
      rows.push(cells(line)); i += 2;
      while (i < lines.length && /^\|.*\|\s*$/.test(lines[i])) { rows.push(cells(lines[i])); i += 1; }
      i -= 1;
      out.push(`<div class="table-wrap"><table><thead><tr>${rows[0].map((c) => `<th>${inlineMarkdown(c)}</th>`).join('')}</tr></thead><tbody>${rows.slice(1).map((row) => `<tr>${row.map((c) => `<td>${inlineMarkdown(c)}</td>`).join('')}</tr>`).join('')}</tbody></table></div>`);
      continue;
    }
    const item = line.match(/^\s*([-*]|\d+\.)\s+(.+)$/);
    if (item) {
      flushParagraph();
      const target = /\d+\./.test(item[1]) ? 'ol' : 'ul';
      if (listType !== target) { closeList(); listType = target; out.push(`<${target}>`); }
      out.push(`<li>${inlineMarkdown(item[2])}</li>`);
      continue;
    }
    if (/^>\s?/.test(line)) { flushParagraph(); closeList(); out.push(`<blockquote>${inlineMarkdown(line.replace(/^>\s?/, ''))}</blockquote>`); continue; }
    if (!line.trim()) { flushParagraph(); closeList(); continue; }
    paragraph.push(line.trim());
  }
  if (inFence && fence.length) out.push(`<pre><code>${escapeHtml(fence.join('\n'))}</code></pre>`);
  flushParagraph(); closeList();
  return out.join('\n');
}

function splitChapters(markdown) {
  const lines = markdown.replace(/\r/g, '').split('\n');
  const starts = [];
  lines.forEach((line, index) => {
    const match = line.match(/^# 제(\d+)장\.\s*(.+)$/);
    if (match) starts.push({ index, number: Number(match[1]), title: match[2].trim() });
  });
  const chapters = [{ number: 0, title: 'Executive Overview', body: lines.slice(0, starts[0]?.index ?? lines.length).join('\n') }];
  starts.forEach((start, index) => {
    const end = starts[index + 1]?.index ?? lines.length;
    chapters.push({ number: start.number, title: start.title, body: lines.slice(start.index + 1, end).join('\n') });
  });
  return chapters;
}

function classifyChapter(number) {
  if ([0, 1, 2, 3, 4].includes(number)) return 'overview';
  if ([6, 7].includes(number)) return 'infra';
  if ([5, 8, 9].includes(number)) return 'runtime';
  if (number === 10) return 'data';
  if (number === 11) return 'marketing';
  if (number === 12) return 'bi';
  return 'governance';
}

function chapterChannels(number) {
  const channels = [classifyChapter(number)];
  if ([0, 4, 9].includes(number)) channels.push('interface');
  if ([3, 13].includes(number) && !channels.includes('governance')) channels.push('governance');
  return channels;
}

function splitTopics(body) {
  const lines = body.split('\n');
  const topics = [];
  let current = { title: '개요', lines: [] };
  for (const line of lines) {
    const heading = line.match(/^#\s+(.+)$/);
    if (heading) {
      if (current.lines.some((item) => item.trim())) topics.push(current);
      current = { title: heading[1].trim(), lines: [] };
    } else current.lines.push(line);
  }
  if (current.lines.some((item) => item.trim())) topics.push(current);
  return topics;
}

function renderChapter(chapter) {
  const label = chapter.number ? `CH ${String(chapter.number).padStart(2, '0')}` : 'MASTER';
  const topics = splitTopics(chapter.body);
  const topicHtml = topics.map((topic, index) => `
    <details class="topic" ${index === 0 ? 'open' : ''}>
      <summary><span>${escapeHtml(topic.title)}</span><span class="chevron" aria-hidden="true">⌄</span></summary>
      <div class="topic-body">${markdownToHtml(topic.lines.join('\n'))}</div>
    </details>`).join('');
  return `<article class="chapter-card" data-channels="${chapterChannels(chapter.number).join(' ')}" data-title="${escapeHtml(chapter.title)}">
    <header class="chapter-head"><span class="chapter-index">${label}</span><div><h2>${escapeHtml(chapter.title)}</h2><p>${topics.length}개 주제</p></div></header>
    <div class="topic-grid">${topicHtml}</div>
  </article>`;
}

function buildDocument(markdown, quality, manifest) {
  const chapters = splitChapters(markdown);
  const tabs = [
    ['all', '전체'], ['overview', '전체 개요'], ['interface', 'UI / 인증'], ['runtime', '업무 Runtime'],
    ['data', 'Data'], ['marketing', 'Marketing'], ['bi', 'BI'], ['infra', 'Infra / DR'], ['governance', 'Governance'],
  ];
  const tabButtons = tabs.map(([id, label], index) => `<button class="tab" role="tab" aria-selected="${index === 0}" data-channel="${id}">${label}</button>`).join('');
  const cards = chapters.map(renderChapter).join('\n');
  return `<!doctype html>
<html lang="ko"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>NSIGHT / PDMG Architecture Book · Final V5</title>
<style>
:root{--ink:#17202a;--muted:#697386;--paper:#f4f2ed;--card:#fff;--line:#dcd8ce;--navy:#112b46;--cyan:#36bed0;--orange:#f28c45;--shadow:0 18px 50px rgba(18,39,59,.09)}*{box-sizing:border-box}html{scroll-behavior:smooth}body{margin:0;color:var(--ink);background:var(--paper);font-family:"Pretendard","Noto Sans KR","Segoe UI",sans-serif;line-height:1.72}.masthead{background:linear-gradient(125deg,#0d2339,#173b5d 62%,#17485f);color:white;padding:54px max(24px,calc((100vw - 1320px)/2));position:relative;overflow:hidden}.masthead:after{content:"";position:absolute;width:420px;height:420px;border:1px solid rgba(92,221,230,.24);border-radius:50%;right:-80px;top:-230px;box-shadow:0 0 0 70px rgba(92,221,230,.04),0 0 0 140px rgba(92,221,230,.03)}.eyebrow{color:#75e1e7;font-weight:800;letter-spacing:.16em;font-size:.75rem}.masthead h1{font-family:Georgia,"Noto Serif KR",serif;font-size:clamp(2.1rem,5vw,4.8rem);max-width:900px;line-height:1.05;margin:.35rem 0 1rem;letter-spacing:-.045em}.masthead p{color:#d7e5ee;max-width:780px;font-size:1.02rem}.metrics{display:flex;gap:10px;flex-wrap:wrap;margin-top:24px}.metric{border:1px solid rgba(255,255,255,.18);background:rgba(255,255,255,.07);padding:8px 12px;border-radius:6px;font-size:.8rem}.metric b{color:#fff;margin-right:6px}.toolbar{position:sticky;top:0;z-index:20;background:rgba(244,242,237,.94);backdrop-filter:blur(14px);border-bottom:1px solid var(--line)}.toolbar-inner{max-width:1320px;margin:auto;padding:12px 24px;display:flex;gap:14px;align-items:center}.tabs{display:flex;gap:6px;overflow:auto;scrollbar-width:none;flex:1}.tab{border:0;background:transparent;color:#536071;padding:10px 13px;border-radius:6px;white-space:nowrap;font-weight:750;cursor:pointer}.tab:hover{background:#e7e4dc}.tab[aria-selected="true"]{background:var(--navy);color:white}.search{width:min(280px,28vw);border:1px solid var(--line);border-radius:7px;background:white;padding:10px 13px;color:var(--ink)}main{max-width:1320px;margin:0 auto;padding:36px 24px 80px}.results-bar{display:flex;align-items:center;justify-content:space-between;margin-bottom:18px;color:var(--muted);font-size:.88rem}.expand-btn{border:1px solid var(--line);background:#fff;border-radius:6px;padding:8px 11px;cursor:pointer}.chapter-list{display:grid;gap:22px}.chapter-card{background:var(--card);border:1px solid var(--line);box-shadow:var(--shadow);border-radius:10px;overflow:hidden}.chapter-card[hidden]{display:none}.chapter-head{display:flex;gap:16px;align-items:center;padding:24px 26px;border-bottom:1px solid var(--line);background:linear-gradient(90deg,#fff,#fbfaf6)}.chapter-index{font-size:.72rem;letter-spacing:.13em;font-weight:900;color:var(--orange);border-right:2px solid var(--orange);padding-right:14px}.chapter-head h2{font-family:Georgia,"Noto Serif KR",serif;font-size:1.55rem;margin:0;letter-spacing:-.025em}.chapter-head p{margin:0;color:var(--muted);font-size:.8rem}.topic-grid{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:1px;background:var(--line)}.topic{background:white;min-width:0}.topic[open]{grid-column:1/-1}.topic summary{list-style:none;padding:17px 20px;display:flex;justify-content:space-between;gap:12px;cursor:pointer;font-weight:800;color:#243b52}.topic summary::-webkit-details-marker{display:none}.topic summary:hover{background:#f7f7f3}.chevron{color:var(--cyan);font-size:1.15rem;transition:.2s}.topic[open] .chevron{transform:rotate(180deg)}.topic-body{padding:4px 26px 28px;border-top:1px solid #ebe8e1}.topic-body h2,.topic-body h3,.topic-body h4{line-height:1.3;margin:1.8em 0 .6em;color:#17324b}.topic-body p{margin:.7em 0}.topic-body code{font-family:"Cascadia Code",Consolas,monospace;background:#eef2f4;padding:.12em .36em;border-radius:4px;color:#14556a}.topic-body pre{background:#10283e;color:#dceaf1;padding:20px;border-radius:7px;overflow:auto;line-height:1.5;border-left:4px solid var(--cyan)}.topic-body pre code{background:none;color:inherit;padding:0}.topic-body strong{color:#0f5264}.topic-body a{color:#087a8b}.topic-body ul,.topic-body ol{padding-left:1.4rem}.topic-body blockquote{margin:1rem 0;padding:10px 16px;border-left:3px solid var(--orange);background:#fff7ef;color:#604a36}.topic-body hr{border:0;border-top:1px solid var(--line);margin:28px 0}.table-wrap{overflow:auto;margin:16px 0}table{border-collapse:collapse;width:100%;font-size:.9rem}th{background:#173b5d;color:white;text-align:left}th,td{padding:10px 12px;border:1px solid #dfe3e5}tbody tr:nth-child(even){background:#f7f8f7}.empty{display:none;text-align:center;padding:80px 20px;color:var(--muted)}footer{background:#0d2339;color:#b9cbd6;padding:30px max(24px,calc((100vw - 1320px)/2));font-size:.82rem}@media(max-width:780px){.masthead{padding-top:38px}.toolbar-inner{align-items:stretch;flex-direction:column}.search{width:100%}.topic-grid{grid-template-columns:1fr}.topic[open]{grid-column:auto}.chapter-head{padding:20px}.topic-body{padding:4px 18px 24px}}@media print{.toolbar,.expand-btn{display:none}.chapter-card{box-shadow:none;break-inside:avoid}.topic{display:block}.topic-body{display:block}.masthead{padding:30px;background:#173b5d}.chapter-list{gap:12px}}
</style></head><body>
<header class="masthead"><div class="eyebrow">FINAL ARCHITECTURE DEFINITION · V5</div><h1>NSIGHT / PDMG<br>Architecture Book</h1><p>책임·경계·계약·실행·물리·증거를 하나의 흐름으로 연결한 아키텍처 정의서. 채널을 선택하고 각 장의 주제 카드를 펼쳐 원문을 탐색하세요.</p><div class="metrics"><span class="metric"><b>${manifest.chapters}</b> Chapters</span><span class="metric"><b>${manifest.integrated_figures}</b> Figures</span><span class="metric"><b>${manifest.architecture_definition}</b> Definition</span><span class="metric"><b>${manifest.current_pdmg_conformance}</b> Conformance</span></div></header>
<nav class="toolbar" aria-label="아키텍처 채널"><div class="toolbar-inner"><div class="tabs" role="tablist">${tabButtons}</div><input class="search" type="search" placeholder="주제·용어 검색" aria-label="문서 검색"></div></nav>
<main><div class="results-bar"><span id="resultCount"></span><button class="expand-btn" id="expandAll">보이는 카드 모두 펼치기</button></div><section class="chapter-list">${cards}</section><div class="empty" id="empty">검색 결과가 없습니다.</div></main>
<footer>NSIGHT / PDMG Architecture Book · Final V5 · 2026-09-01<br>${escapeHtml(quality.split('\n').find((line) => line.includes('FINAL')) || 'Architecture Definition Edition')}</footer>
<script>
const tabs=[...document.querySelectorAll('.tab')],cards=[...document.querySelectorAll('.chapter-card')],search=document.querySelector('.search'),count=document.querySelector('#resultCount'),empty=document.querySelector('#empty');let channel='all';
function applyFilter(){const query=search.value.trim().toLocaleLowerCase('ko');let visible=0;cards.forEach(card=>{const channelMatch=channel==='all'||card.dataset.channels.split(' ').includes(channel);const textMatch=!query||card.textContent.toLocaleLowerCase('ko').includes(query);card.hidden=!(channelMatch&&textMatch);if(!card.hidden)visible++});count.textContent=visible+'개 장 표시';empty.style.display=visible?'none':'block'}
tabs.forEach(tab=>tab.addEventListener('click',()=>{tabs.forEach(item=>item.setAttribute('aria-selected','false'));tab.setAttribute('aria-selected','true');channel=tab.dataset.channel;applyFilter()}));search.addEventListener('input',applyFilter);document.querySelector('#expandAll').addEventListener('click',event=>{const visibleDetails=cards.filter(c=>!c.hidden).flatMap(c=>[...c.querySelectorAll('details')]);const openAll=visibleDetails.some(d=>!d.open);visibleDetails.forEach(d=>d.open=openAll);event.currentTarget.textContent=openAll?'보이는 카드 모두 접기':'보이는 카드 모두 펼치기'});document.addEventListener('keydown',event=>{if(event.key==='/'&&document.activeElement!==search){event.preventDefault();search.focus()}});applyFilter();
</script></body></html>`;
}

function main() {
  const directory = __dirname;
  const source = fs.readFileSync(path.join(directory, 'NSIGHT_PDMG_ARCHITECTURE_BOOK_FINAL_V5.md'), 'utf8');
  const quality = fs.readFileSync(path.join(directory, 'NSIGHT_PDMG_ARCHITECTURE_BOOK_FINAL_QUALITY_REPORT_V5.md'), 'utf8');
  const manifest = JSON.parse(fs.readFileSync(path.join(directory, 'NSIGHT_PDMG_ARCHITECTURE_BOOK_MANIFEST_FINAL_V5.json'), 'utf8'));
  const output = buildDocument(source, quality, manifest);
  fs.writeFileSync(path.join(directory, 'NSIGHT_PDMG_ARCHITECTURE_BOOK_FINAL_V5.html'), output, 'utf8');
}

module.exports = { escapeHtml, inlineMarkdown, markdownToHtml, splitChapters, classifyChapter, buildDocument };
if (require.main === module) main();
