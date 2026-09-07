const fs = require('node:fs');
const path = require('node:path');
const baseBuilder = require('../../아키텍처정의서(T5-Final)/NSIGHT_PDMG_ARCHITECTURE_BOOK_FINAL_V5_COMPLETE/html-builder');

const { escapeHtml, markdownToHtml } = baseBuilder;

function splitChapters(markdown) {
  const lines = markdown.replace(/\r/g, '').split('\n');
  const starts = [];
  lines.forEach((line, index) => {
    const match = line.match(/^# 제(\d+)장\.\s*(.+)$/);
    if (match) starts.push({ index, number: Number(match[1]), title: match[2].trim() });
  });
  return starts.map((start, index) => ({
    number: start.number,
    title: start.title,
    body: lines.slice(start.index + 1, starts[index + 1]?.index ?? lines.length).join('\n'),
  }));
}

function splitTopics(body) {
  const lines = body.replace(/\r/g, '').split('\n');
  const topics = [];
  let current = { title: '장 소개', body: [] };
  for (const line of lines) {
    const heading = line.match(/^#\s+(.+)$/);
    if (heading) {
      if (current.body.some((item) => item.trim())) topics.push({ title: current.title, body: current.body.join('\n') });
      current = { title: heading[1].trim(), body: [] };
    } else current.body.push(line);
  }
  if (current.body.some((item) => item.trim())) topics.push({ title: current.title, body: current.body.join('\n') });
  return topics;
}

function compactTitle(number, title) {
  const short = title
    .replace('아키텍처 6단계 수립 방법론', '아키텍처 6단계 방법론')
    .replace('표준화와 10년 지속 가능성', '표준화·지속 가능성');
  return `${String(number).padStart(2, '0')} ${short}`;
}

function buildChapterTabs(chapters) {
  return [`<button class="chapter-tab" role="tab" aria-selected="true" data-chapter="all">전체</button>`, ...chapters.map((chapter) =>
    `<button class="chapter-tab" role="tab" aria-selected="false" data-chapter="${chapter.number}">${escapeHtml(compactTitle(chapter.number, chapter.title))}</button>`
  )].join('');
}

function laneFor(title, index, total) {
  const value = title.toLowerCase();
  if (/story|전체|opening|배경|전환|landscape|big picture/.test(value)) return 'story';
  if (/evidence|decision|판정|pass|gap|adr|conformance|정상패턴|금지패턴|closing|handoff|지속|표준/.test(value)) return 'evidence';
  if (/runtime|physical|물리|failure|security|timeout|transaction|운영|배포|센터|dr|capacity|복구|관측/.test(value)) return 'runtime';
  if (/logical|논리|responsibility|boundary|component|contract|interface|application|platform|data|service|구조|책임|경계/.test(value)) return 'logical';
  if (index < Math.max(2, total * 0.22)) return 'story';
  if (index > total * 0.72) return 'evidence';
  return index % 2 ? 'logical' : 'runtime';
}

function summaryFor(body) {
  return body
    .replace(/```[\s\S]*?```/g, ' ')
    .replace(/^#{1,6}\s+/gm, '')
    .replace(/\|/g, ' ')
    .replace(/[*_`>#-]/g, '')
    .replace(/\s+/g, ' ')
    .trim()
    .slice(0, 150);
}

function renderTopic(chapter, topic, index, total, nextChapter) {
  const lane = laneFor(topic.title, index, total);
  const summary = summaryFor(topic.body);
  const finalAttributes = index === total - 1
    ? ` data-chapter-last="true" data-next-chapter="${nextChapter?.number ?? 'all'}"`
    : '';
  return `<details class="topic-card" data-chapter="${chapter.number}" data-lane="${lane}" data-topic-id="${chapter.number}-${index + 1}"${finalAttributes}>
    <summary><span class="topic-no">${String(chapter.number).padStart(2, '0')} · ${String(index + 1).padStart(2, '0')}</span><strong>${escapeHtml(topic.title)}</strong><span class="summary-copy">${escapeHtml(summary)}${summary.length === 150 ? '…' : ''}</span><span class="read-more">내용 보기 <span aria-hidden="true">→</span></span></summary>
    <div class="topic-content">${markdownToHtml(topic.body)}</div>
  </details>`;
}

function buildHtml(source, indexSource, manifest) {
  const chapters = splitChapters(source);
  const lanes = [
    ['story', 'Story · 전체상', '01', '왜 필요한가와 장 전체 그림'],
    ['logical', '책임 · 논리', '02', 'Boundary, Component, Contract'],
    ['runtime', 'Runtime · 운영', '03', '실행, 장애, 보안, 물리 구조'],
    ['evidence', 'Decision · Evidence', '04', '판정, GAP, ADR, 검증 기준'],
  ];
  const allTopics = chapters.flatMap((chapter, chapterIndex) => {
    const topics = splitTopics(chapter.body);
    return topics.map((topic, index) => renderTopic(chapter, topic, index, topics.length, chapters[chapterIndex + 1]));
  });
  const laneMarkup = lanes.map(([id, title, number, caption]) => `<section class="board-lane" data-lane="${id}"><header><span>${number}</span><div><h2>${title}</h2><p>${caption}</p></div><b class="lane-count">0</b></header><div class="lane-cards" id="lane-${id}"></div></section>`).join('');
  const titleMap = Object.fromEntries(chapters.map((chapter) => [chapter.number, chapter.title]));
  const intro = summaryFor(indexSource).slice(0, 230);
  return `<!doctype html><html lang="ko"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>NSIGHT PDMG Story Architecture</title>
  <style>
  :root{--navy:#153552;--blue:#2864c7;--sky:#e8f2fb;--paper:#f5f8fb;--ink:#172536;--muted:#6d7c8b;--line:#d7e2eb;--card:#fff;--shadow:0 5px 16px rgba(25,62,91,.08)}*{box-sizing:border-box}html{scroll-behavior:smooth}body{margin:0;background:var(--paper);color:var(--ink);font-family:"Pretendard","Noto Sans KR","Segoe UI",sans-serif;line-height:1.58}.topline{height:4px;background:linear-gradient(90deg,#204e9e,#2c78d6,#25a9bd)}.hero{max-width:1480px;margin:auto;padding:28px 24px 20px;display:flex;justify-content:space-between;gap:24px;align-items:end}.eyebrow{font-size:.72rem;font-weight:850;letter-spacing:.14em;color:var(--blue)}h1{margin:3px 0 8px;font-size:clamp(1.8rem,4vw,3.15rem);line-height:1.06;letter-spacing:-.045em}.hero p{margin:0;max-width:850px;color:var(--muted);font-size:.9rem}.stats{display:flex;gap:8px;flex-wrap:wrap;justify-content:flex-end}.stat{background:white;border:1px solid var(--line);border-radius:999px;padding:7px 11px;font-size:.75rem;white-space:nowrap}.stat b{color:var(--blue);margin-right:4px}.chapter-nav{position:sticky;top:0;z-index:30;background:rgba(255,255,255,.96);backdrop-filter:blur(12px);border-block:1px solid var(--line)}.chapter-tabs{max-width:1480px;margin:auto;padding:0 20px;display:flex;overflow:auto;scrollbar-width:thin}.chapter-tab{border:0;border-bottom:3px solid transparent;background:transparent;padding:15px 13px 12px;color:#586979;font-weight:750;white-space:nowrap;cursor:pointer}.chapter-tab:hover{background:#f1f6fa;color:var(--navy)}.chapter-tab[aria-selected="true"]{color:var(--blue);border-bottom-color:var(--blue)}.controls{max-width:1480px;margin:0 auto;padding:16px 24px 10px;display:flex;gap:12px;justify-content:space-between;align-items:center}.context h2{font-size:1rem;margin:0}.context p{font-size:.78rem;color:var(--muted);margin:2px 0 0}.actions{display:flex;gap:8px}.search,.expand{border:1px solid var(--line);background:white;border-radius:7px;padding:9px 12px;color:var(--ink)}.search{width:min(310px,36vw)}.expand{cursor:pointer;font-weight:700}.board-wrap{max-width:1500px;margin:auto;padding:8px 16px 64px;overflow-x:auto}.board{display:grid;grid-template-columns:repeat(4,minmax(285px,1fr));gap:12px;min-width:1160px}.board-lane{background:var(--sky);border:1px solid #dceaf5;border-radius:12px;padding:10px;align-self:start}.board-lane>header{display:flex;gap:10px;align-items:center;padding:7px 5px 13px}.board-lane>header>span{display:grid;place-items:center;width:30px;height:30px;border-radius:8px;background:var(--navy);color:white;font-size:.69rem;font-weight:850}.board-lane h2{margin:0;font-size:.95rem}.board-lane header p{margin:1px 0 0;font-size:.68rem;color:var(--muted)}.lane-count{margin-left:auto;background:white;color:var(--blue);min-width:25px;height:25px;border-radius:99px;display:grid;place-items:center;font-size:.72rem}.lane-cards{display:grid;gap:8px}.topic-card{background:var(--card);border:1px solid #dfe8ef;border-radius:8px;box-shadow:var(--shadow);overflow:hidden}.topic-card[hidden]{display:none}.topic-card summary{list-style:none;cursor:pointer;padding:15px;display:flex;flex-direction:column;gap:5px}.topic-card summary::-webkit-details-marker{display:none}.topic-card summary:hover{background:#fbfdff}.topic-no{font-size:.63rem;color:var(--blue);font-weight:900;letter-spacing:.08em}.topic-card strong{font-size:.88rem;line-height:1.35}.summary-copy{font-size:.75rem;line-height:1.55;color:#5d6c79}.read-more{font-size:.7rem;color:var(--blue);font-weight:800;margin-top:3px}.topic-card[open]{grid-column:1/-1;border-color:#8fb8df}.topic-card[open] .summary-copy{display:none}.topic-content{border-top:1px solid var(--line);padding:5px 17px 20px;font-size:.82rem;overflow:hidden}.topic-content h1,.topic-content h2,.topic-content h3,.topic-content h4{color:var(--navy);line-height:1.3;margin:1.5em 0 .55em}.topic-content h1{font-size:1.25rem}.topic-content h2{font-size:1.08rem}.topic-content h3{font-size:.96rem}.topic-content p{margin:.65em 0}.topic-content code{font-family:"Cascadia Code",Consolas,monospace;background:#eef3f7;color:#145272;padding:.1em .3em;border-radius:3px}.topic-content pre{background:#102b43;color:#dfedf5;border-left:3px solid #40bfd0;border-radius:6px;padding:15px;overflow:auto;line-height:1.45}.topic-content pre code{background:transparent;color:inherit;padding:0}.topic-content blockquote{margin:12px 0;padding:8px 13px;border-left:3px solid #2c78d6;background:#edf6fd}.table-wrap{overflow:auto}table{border-collapse:collapse;width:100%;font-size:.76rem}th,td{padding:8px;border:1px solid #dce4ea;text-align:left}th{background:#183c5b;color:white}.empty{display:none;text-align:center;color:var(--muted);padding:60px}.detail-view{max-width:1380px;margin:24px auto 72px;padding:0 24px}.detail-view[hidden]{display:none}.detail-layout{display:grid;grid-template-columns:255px minmax(0,1fr);gap:18px;align-items:start}.chapter-sidebar{position:sticky;top:68px;background:white;border:1px solid var(--line);border-radius:10px;box-shadow:var(--shadow);max-height:calc(100vh - 88px);overflow:auto}.chapter-sidebar details>summary{list-style:none;padding:17px 18px;border-bottom:1px solid var(--line);font-weight:850;color:var(--navy);cursor:pointer}.chapter-sidebar details>summary::-webkit-details-marker{display:none}.chapter-page-menu{padding:8px;display:grid;gap:3px}.chapter-page-link{border:0;background:transparent;text-align:left;border-radius:6px;padding:9px 10px;color:#586979;font-size:.76rem;line-height:1.35;cursor:pointer}.chapter-page-link:hover{background:#eef5fa;color:var(--navy)}.chapter-page-link.active{background:var(--navy);color:white;font-weight:800}.detail-shell{background:white;border:1px solid var(--line);border-radius:12px;box-shadow:0 16px 45px rgba(25,62,91,.1);overflow:hidden;min-width:0}.detail-header{padding:27px 34px 22px;background:linear-gradient(135deg,#153552,#24587e);color:white}.detail-back{border:1px solid rgba(255,255,255,.4);background:rgba(255,255,255,.1);color:white;border-radius:7px;padding:8px 11px;font-weight:750;cursor:pointer}.detail-kicker{display:block;margin-top:22px;color:#8ce3e8;font-size:.7rem;font-weight:850;letter-spacing:.12em}.detail-header h2{font-size:clamp(1.5rem,4vw,2.55rem);line-height:1.15;margin:5px 0 7px;letter-spacing:-.035em}.detail-header p{margin:0;color:#d7e7f0;font-size:.82rem}.detail-body{padding:16px 42px 48px;font-size:.98rem;line-height:1.8}.detail-body h1,.detail-body h2,.detail-body h3,.detail-body h4{color:var(--navy);line-height:1.3;margin:1.7em 0 .6em}.detail-body pre{background:#102b43;color:#dfedf5;border-left:4px solid #40bfd0;border-radius:7px;padding:20px;overflow:auto;line-height:1.5}.detail-body pre code{background:transparent;color:inherit}.page-nav{display:grid;grid-template-columns:1fr 1fr;gap:12px;padding:0 42px 34px}.page-nav button{border:1px solid var(--line);background:#f7fafc;border-radius:8px;padding:13px 15px;color:var(--navy);font-weight:800;cursor:pointer}.page-nav button:last-child{text-align:right}.page-nav button:disabled{opacity:.4;cursor:not-allowed}.next-index{margin:0 42px 42px;padding:25px 27px;border-radius:10px;background:linear-gradient(135deg,#eef6fc,#e5f1fb);border:1px solid #cfe1ef}.next-index[hidden]{display:none}.next-label{font-size:.68rem;font-weight:900;letter-spacing:.14em;color:var(--blue)}.next-index h3{font-size:1.45rem;margin:7px 0 5px;color:var(--navy)}.next-index p{color:var(--muted);margin:0 0 18px}.next-button{border:0;border-radius:7px;background:var(--blue);color:white;padding:11px 15px;font-weight:800;cursor:pointer}.source-footer{background:var(--navy);color:#bbcfdd;padding:24px max(24px,calc((100vw - 1440px)/2));font-size:.74rem}@media(max-width:900px){.detail-layout{grid-template-columns:1fr}.chapter-sidebar{position:static;max-height:none}.chapter-sidebar details:not([open]) .chapter-page-menu{display:none}}@media(max-width:760px){.hero{align-items:start;flex-direction:column}.stats{justify-content:flex-start}.controls{align-items:stretch;flex-direction:column}.actions{width:100%}.search{width:100%;flex:1}.board-wrap{padding-left:10px}.chapter-tabs{padding-left:8px}.detail-view{padding:0 12px}.detail-header{padding:22px}.detail-body{padding:10px 21px 35px}.page-nav{padding:0 21px 26px}.next-index{margin:0 21px 25px}}@media print{.chapter-nav,.actions,.detail-back,.next-button,.chapter-sidebar,.page-nav{display:none}.board{display:block;min-width:0}.board-lane{margin-bottom:14px;break-inside:avoid}.topic-card{box-shadow:none}.topic-card .topic-content{display:block}.board-wrap{overflow:visible}.hero{padding-top:15px}.detail-layout{display:block}}
  </style></head><body><div class="topline"></div><header class="hero"><div><div class="eyebrow">STORY + TEXT ARCHITECTURE · TOP-DOWN → DRILL-DOWN</div><h1>NSIGHT / PDMG<br>Story Architecture</h1><p>${escapeHtml(intro)}</p></div><div class="stats"><span class="stat"><b>${manifest.chapter_count}</b> Chapters</span><span class="stat"><b>${manifest.total_text_figures}</b> Text Figures</span><span class="stat"><b>${manifest.overall_architecture_definition}</b> Definition</span></div></header>
  <nav class="chapter-nav" aria-label="Chapter 탐색"><div class="chapter-tabs" role="tablist">${buildChapterTabs(chapters)}</div></nav>
  <section class="controls"><div class="context"><h2 id="activeTitle">전체 Chapter</h2><p id="activeMeta">13개 장의 주제를 Architecture 흐름별로 보여줍니다.</p></div><div class="actions"><input class="search" type="search" placeholder="현재 Chapter에서 검색" aria-label="주제 검색"></div></section>
  <main class="board-wrap"><div class="board">${laneMarkup}</div><div class="empty">검색 결과가 없습니다.</div></main><article class="detail-view" id="detailView" hidden><div class="detail-layout"><aside class="chapter-sidebar"><details open><summary>이 장의 페이지</summary><nav class="chapter-page-menu" id="chapterPageMenu" aria-label="현재 Chapter 페이지"></nav></details></aside><div class="detail-shell"><header class="detail-header"><button type="button" class="detail-back">← 보드로 돌아가기</button><span class="detail-kicker" id="detailKicker"></span><h2 id="detailTitle"></h2><p id="detailMeta"></p></header><div class="detail-body" id="detailBody"></div><nav class="page-nav" aria-label="페이지 이동"><button class="page-prev" type="button">← Prev</button><button class="page-next" type="button">Next →</button></nav><section class="next-index" hidden><span class="next-label">NEXT INDEX</span><h3 id="nextTitle"></h3><p id="nextCopy"></p><button class="next-button" type="button">다음 Chapter로 이동 →</button></section></div></div></article><div id="topicStore" hidden>${allTopics.join('')}</div>
  <footer class="source-footer">NSIGHT PDMG Story Architecture · Working Integrated Baseline · 2026-09-01</footer>
  <script>const titleMap=${JSON.stringify(titleMap)};const tabs=[...document.querySelectorAll('.chapter-tab')],store=document.querySelector('#topicStore'),cards=[...store.querySelectorAll('.topic-card')],search=document.querySelector('.search'),empty=document.querySelector('.empty'),boardWrap=document.querySelector('.board-wrap'),controls=document.querySelector('.controls'),detailView=document.querySelector('#detailView'),nextIndex=document.querySelector('.next-index'),nextButton=document.querySelector('.next-button'),pageMenu=document.querySelector('#chapterPageMenu'),prevButton=document.querySelector('.page-prev'),nextPageButton=document.querySelector('.page-next');document.querySelectorAll('.lane-cards').forEach(lane=>cards.filter(card=>card.dataset.lane===lane.id.replace('lane-','')).forEach(card=>lane.append(card)));let active='all',nextTarget='all',currentCard=null;function filter(){const query=search.value.trim().toLocaleLowerCase('ko');let total=0;cards.forEach(card=>{const matchChapter=active==='all'||card.dataset.chapter===active;const matchText=!query||card.textContent.toLocaleLowerCase('ko').includes(query);card.hidden=!(matchChapter&&matchText);if(!card.hidden)total++});document.querySelectorAll('.board-lane').forEach(lane=>{const visible=[...lane.querySelectorAll('.topic-card')].filter(card=>!card.hidden).length;lane.querySelector('.lane-count').textContent=visible;lane.hidden=visible===0});empty.style.display=total?'none':'block';document.querySelector('#activeTitle').textContent=active==='all'?'전체 Chapter':'CHAPTER '+String(active).padStart(2,'0')+' · '+titleMap[active];document.querySelector('#activeMeta').textContent=total+'개 주제 카드 표시'}function chapterCards(chapter){return cards.filter(card=>card.dataset.chapter===String(chapter))}function renderChapterMenu(chapter,currentId){pageMenu.replaceChildren();chapterCards(chapter).forEach(card=>{const button=document.createElement('button');button.type='button';button.className='chapter-page-link'+(card.dataset.topicId===currentId?' active':'');button.textContent=card.querySelector('.topic-no').textContent+'  '+card.querySelector('summary strong').textContent;button.addEventListener('click',()=>showDetail(card));pageMenu.append(button)});pageMenu.querySelector('.active')?.scrollIntoView({block:'nearest'})}function updatePageNavigation(card){const pages=chapterCards(card.dataset.chapter),index=pages.indexOf(card);prevButton.disabled=index===0;prevButton.textContent=index===0?'← Prev': '← '+pages[index-1].querySelector('summary strong').textContent;nextPageButton.textContent=index===pages.length-1?'NEXT INDEX ↓':pages[index+1].querySelector('summary strong').textContent+' →'}function updateNextIndex(card){const isLast=card.dataset.chapterLast==='true';nextIndex.hidden=!isLast;if(!isLast)return;nextTarget=card.dataset.nextChapter;if(nextTarget==='all'){document.querySelector('#nextTitle').textContent='전체 Architecture로 돌아가기';document.querySelector('#nextCopy').textContent='13개 Chapter의 전체 카드 보드에서 아키텍처 흐름을 다시 살펴봅니다.';nextButton.textContent='전체 카드 보드로 이동 →'}else{document.querySelector('#nextTitle').textContent='CHAPTER '+String(nextTarget).padStart(2,'0')+' · '+titleMap[nextTarget];document.querySelector('#nextCopy').textContent='현재 장의 결론을 다음 Architecture 이야기로 자연스럽게 이어갑니다.';nextButton.textContent='다음 Chapter로 이동 →'}}function showDetail(card,pushState=true){currentCard=card;const chapter=card.dataset.chapter;document.querySelector('#detailKicker').textContent='CHAPTER '+String(chapter).padStart(2,'0')+' · '+titleMap[chapter];document.querySelector('#detailTitle').textContent=card.querySelector('summary strong').textContent;document.querySelector('#detailMeta').textContent=card.querySelector('.topic-no').textContent+' · 전체 내용';document.querySelector('#detailBody').innerHTML=card.querySelector('.topic-content').innerHTML;renderChapterMenu(chapter,card.dataset.topicId);updatePageNavigation(card);updateNextIndex(card);boardWrap.hidden=true;controls.hidden=true;detailView.hidden=false;if(pushState)history.pushState({topic:card.dataset.topicId},'', '#topic-'+card.dataset.topicId);window.scrollTo({top:document.querySelector('.chapter-nav').offsetTop,behavior:'smooth'})}function returnToBoard(pushState=true){detailView.hidden=true;boardWrap.hidden=false;controls.hidden=false;if(pushState)history.pushState({},'',location.pathname+location.search);window.scrollTo({top:document.querySelector('.chapter-nav').offsetTop,behavior:'smooth'})}function selectChapter(target){tabs.forEach(item=>item.setAttribute('aria-selected',String(item.dataset.chapter===target)));active=target;if(active==='all')search.value='';returnToBoard(false);history.pushState({},'',location.pathname+location.search);filter()}function movePage(direction){const pages=chapterCards(currentCard.dataset.chapter),index=pages.indexOf(currentCard);if(direction<0&&index>0)showDetail(pages[index-1]);else if(direction>0&&index<pages.length-1)showDetail(pages[index+1]);else if(direction>0)nextIndex.scrollIntoView({behavior:'smooth',block:'center'})}cards.forEach(card=>card.querySelector('summary').addEventListener('click',event=>{event.preventDefault();showDetail(card)}));tabs.forEach(tab=>tab.addEventListener('click',()=>selectChapter(tab.dataset.chapter)));document.querySelector('.detail-back').addEventListener('click',()=>returnToBoard());prevButton.addEventListener('click',()=>movePage(-1));nextPageButton.addEventListener('click',()=>movePage(1));nextButton.addEventListener('click',()=>{if(nextTarget==='all')selectChapter('all');else{const first=chapterCards(nextTarget)[0];tabs.forEach(item=>item.setAttribute('aria-selected',String(item.dataset.chapter===nextTarget)));active=nextTarget;showDetail(first)}});search.addEventListener('input',filter);window.addEventListener('popstate',()=>{const match=location.hash.match(/^#topic-(.+)$/);const card=match&&cards.find(item=>item.dataset.topicId===match[1]);if(card)showDetail(card,false);else returnToBoard(false)});document.addEventListener('keydown',event=>{if(event.key==='Escape'&&!detailView.hidden)returnToBoard();if(event.key==='ArrowLeft'&&!detailView.hidden)movePage(-1);if(event.key==='ArrowRight'&&!detailView.hidden)movePage(1);if(event.key==='/'&&detailView.hidden&&document.activeElement!==search){event.preventDefault();search.focus()}});filter();const initial=location.hash.match(/^#topic-(.+)$/);if(initial){const card=cards.find(item=>item.dataset.topicId===initial[1]);if(card)showDetail(card,false)}</script></body></html>`;
}

function main() {
  const directory = __dirname;
  const source = fs.readFileSync(path.join(directory, 'NSIGHT_PDMG_STORY_ARCHITECTURE_FINAL_INTEGRATED.md'), 'utf8');
  const indexSource = fs.readFileSync(path.join(directory, '00_NSIGHT_PDMG_STORY_ARCHITECTURE_MASTER_INDEX.md'), 'utf8');
  const manifest = JSON.parse(fs.readFileSync(path.join(directory, 'NSIGHT_PDMG_STORY_ARCHITECTURE_MANIFEST.json'), 'utf8'));
  fs.writeFileSync(path.join(directory, 'NSIGHT_PDMG_STORY_ARCHITECTURE_COMPLETE_V1.html'), buildHtml(source, indexSource, manifest), 'utf8');
}

module.exports = { escapeHtml, markdownToHtml, splitChapters, splitTopics, buildChapterTabs, laneFor, buildHtml };
if (require.main === module) main();
