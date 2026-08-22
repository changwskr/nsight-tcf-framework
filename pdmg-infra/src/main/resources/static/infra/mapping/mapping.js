const SCID = 'INF-240';
const $ = (id) => document.getElementById(id);

let cache = {
  systems: [], domains: [], units: [], details: [], apps: [],
  maps: [], bizAppMaps: [], session: {}, selectedApp: {}, selectedBiz: {},
  appRuntime: {}, bizTree: {}, previewLines: [], overviewUnits: [],
  mapSummary: [], resultSummary: []
};
let selectedBizCd = null;
let selectedDetailCd = null;
let selectedDomainCd = null;
let selectedAppId = null;
let selectedGroupId = null;
const selectedPickIds = new Set();
const selectedGroupIds = new Set();

function toast(msg) {
  const el = $('toast');
  el.textContent = msg;
  el.hidden = false;
  clearTimeout(toast._t);
  toast._t = setTimeout(() => { el.hidden = true; }, 2200);
}

function fmtDtm(v) {
  if (!v || String(v).length < 8) return v || '-';
  const s = String(v);
  return `${s.slice(0,4)}-${s.slice(4,6)}-${s.slice(6,8)}` + (s.length >= 14 ? ` ${s.slice(8,10)}:${s.slice(10,12)}` : '');
}

function statusDot(status) {
  if (status === 'DONE') return '<span class="map-status-dot done" title="매핑완료">●</span>';
  if (status === 'PARTIAL') return '<span class="map-status-dot partial" title="부분매핑">●</span>';
  if (status === 'ERROR') return '<span class="map-status-dot error" title="오류">●</span>';
  return '<span class="map-status-dot none" title="미매핑">●</span>';
}

function statusLabel(status) {
  if (status === 'DONE' || status === 'CONFIRMED') return '<span class="map-pill done">매핑완료</span>';
  if (status === 'PARTIAL' || status === 'VALIDATED') return '<span class="map-pill partial">부분매핑</span>';
  if (status === 'DRAFT') return '<span class="map-pill draft">임시</span>';
  return '<span class="map-pill none">미매핑</span>';
}

function fillSelect(sel, rows, valueKey, labelFn, selected) {
  const cur = selected != null ? selected : sel.value;
  sel.innerHTML = (rows || []).map((r) => {
    const v = r[valueKey];
    return `<option value="${InfraApi.escapeHtml(v)}">${InfraApi.escapeHtml(labelFn(r))}</option>`;
  }).join('');
  if (cur && [...sel.options].some((o) => o.value === cur)) sel.value = cur;
  else if (sel.options.length) sel.selectedIndex = 0;
}

function payloadBase() {
  return {
    systemId: $('systemId').value || 'SYS-MKTG',
    domainCd: selectedDomainCd || $('domainCd').value || null,
    bizCd: selectedBizCd || null,
    detailCd: selectedDetailCd || null,
    envCd: $('envCd').value || 'PROD',
    appId: selectedAppId || null,
    appKeyword: ($('appKeyword') && $('appKeyword').value.trim()) || null
  };
}

async function load(opts = {}) {
  if (opts.keepApp === false) selectedAppId = null;
  $('resultMeta').textContent = '조회 중…';
  const res = await InfraApi.postService('ifina2400S0', payloadBase(), SCID);
  const dto = res.dto || {};
  cache = {
    systems: dto.systems || [],
    domains: dto.domains || [],
    units: dto.units || [],
    details: dto.details || [],
    apps: dto.apps || [],
    maps: dto.maps || [],
    bizAppMaps: dto.bizAppMaps || [],
    session: dto.session || {},
    selectedApp: dto.selectedApp || {},
    selectedBiz: dto.selectedBiz || {},
    appRuntime: dto.appRuntime || {},
    bizTree: dto.bizTree || {},
    previewLines: dto.previewLines || [],
    overviewUnits: dto.overviewUnits || [],
    mapSummary: dto.mapSummary || [],
    resultSummary: dto.resultSummary || []
  };
  if (cache.selectedBiz.bizCd) selectedBizCd = cache.selectedBiz.bizCd;
  if (cache.selectedBiz.detailCd) selectedDetailCd = cache.selectedBiz.detailCd;
  if (cache.selectedBiz.domainCd) selectedDomainCd = cache.selectedBiz.domainCd;
  if (!selectedAppId && cache.selectedApp.appId) selectedAppId = cache.selectedApp.appId;
  if (!selectedGroupIds.size && (cache.appRuntime.groups || []).length) {
    selectedGroupIds.add(cache.appRuntime.groups[0].groupId);
    selectedGroupId = cache.appRuntime.groups[0].groupId;
  }

  fillSelect($('systemId'), cache.systems, 'systemId', (r) => `${r.systemId} - ${r.systemName || ''}`, payloadBase().systemId || 'SYS-MKTG');
  fillSelect($('domainCd'), cache.domains, 'domainCd', (r) => `${r.domainCd} - ${r.nameKo || ''}`, selectedDomainCd || 'MG');

  renderAll();
  $('resultMeta').textContent = `HTTP ${res.httpStatus} · ${res.elapsedMs}ms · ifina2400S0`;
}

function renderAll() {
  renderBizTree();
  renderBizCard();
  renderAppTable();
  renderRuntime();
  renderResultSummary();
  renderPreviewFlow();
  renderSession();
}

function renderBizTree() {
  const units = (cache.bizTree && cache.bizTree.units) || [];
  const domain = cache.domains.find((d) => d.domainCd === (selectedDomainCd || $('domainCd').value)) || {};
  const system = cache.systems.find((s) => s.systemId === $('systemId').value) || {};
  const kw = (($('treeKeyword') && $('treeKeyword').value) || '').trim().toLowerCase();
  const statusFilter = $('mapStatusFilter').value;

  let html = `<div class="map-tree-root-label">
    <strong class="mono">${InfraApi.escapeHtml(domain.domainCd || system.systemId || '')}</strong>
    <span>${InfraApi.escapeHtml(domain.nameKo || system.systemName || '')}</span>
  </div>`;

  units.forEach((unit) => {
    let details = unit.details || [];
    if (statusFilter && unit.mapStatus !== statusFilter) return;
    if (kw) {
      const unitHit = String(unit.bizCd).toLowerCase().includes(kw)
        || String(unit.nameKo || '').toLowerCase().includes(kw);
      details = details.filter((d) => {
        const hay = `${unit.bizCd} ${unit.nameKo} ${d.detailCd} ${d.functionCd} ${d.nameKo}`.toLowerCase();
        return hay.includes(kw);
      });
      if (!unitHit && !details.length) return;
      if (unitHit && !details.length) details = unit.details || [];
    }
    const open = selectedBizCd === unit.bizCd;
    const unitActive = open && !selectedDetailCd ? ' is-active' : '';
    const cntLabel = `${unit.mappedCnt || 0}/${unit.detailCnt || details.length || 0}`;
    html += `<details class="map-tree-unit-block" ${open ? 'open' : ''}>
      <summary class="map-tree-item map-tree-biz${unitActive}"
        data-biz="${InfraApi.escapeHtml(unit.bizCd || '')}"
        data-domain="${InfraApi.escapeHtml(selectedDomainCd || $('domainCd').value || '')}">
        ${statusDot(unit.mapStatus)}
        <span class="mono">${InfraApi.escapeHtml(unit.bizCd || '')}</span>
        <span>${InfraApi.escapeHtml(unit.nameKo || '')}</span>
        <em>${InfraApi.escapeHtml(cntLabel)}</em>
      </summary>`;
    details.forEach((det) => {
      const short = det.functionCd || det.detailShortCd || '';
      const active = det.detailCd === selectedDetailCd ? ' is-active' : '';
      html += `<button type="button" class="map-tree-item map-tree-detail${active}"
        data-detail="${InfraApi.escapeHtml(det.detailCd || '')}"
        data-biz="${InfraApi.escapeHtml(unit.bizCd || '')}"
        data-domain="${InfraApi.escapeHtml(selectedDomainCd || $('domainCd').value || '')}">
        ${statusDot(unit.mapStatus)}
        <span class="mono">${InfraApi.escapeHtml(short)}</span>
        <span>${InfraApi.escapeHtml(det.nameKo || '')}</span>
      </button>`;
    });
    html += '</details>';
  });
  $('bizTreePanel').innerHTML = html || '<div class="empty">업무 없음</div>';
}

function bizMapStatus() {
  const units = (cache.bizTree && cache.bizTree.units) || [];
  for (const u of units) {
    if (u.bizCd === selectedBizCd) return u.mapStatus || 'NONE';
  }
  return cache.bizAppMaps.length ? 'PARTIAL' : 'NONE';
}

function renderBizCard() {
  const b = cache.selectedBiz || {};
  const s = cache.session || {};
  const primary = (cache.bizAppMaps || []).find((m) => m.primaryYn === 'Y') || (cache.bizAppMaps || [])[0];
  const st = bizMapStatus();
  $('cardSystem').textContent = b.systemId || b.domainCd || '-';
  $('cardDomain').textContent = b.domainCd ? `${b.domainCd}${b.domainName ? '-' + b.domainName : ''}` : '-';
  $('cardBiz').textContent = b.bizCd ? `${b.bizCd}${b.bizName ? '-' + b.bizName : ''}` : '-';
  $('cardDetail').textContent = b.detailCd
    ? `${b.functionCd || b.detailShortCd || ''} ${b.detailName || ''}`.trim() || b.detailCd
    : (b.detailCnt != null ? `세부 ${b.detailCnt}건` : '-');
  $('cardKey').textContent = b.mappingKey
    || [b.domainCd, b.bizCd, b.detailCd].filter(Boolean).join('/')
    || '-';
  $('cardPrimaryApp').textContent = primary ? primary.appId : '-';
  $('cardChgUser').textContent = s.chgUserId || '-';
  $('cardChgDtm').textContent = fmtDtm(s.chgDtm);
  const badge = $('cardStatusBadge');
  badge.className = 'map-badge ' + (st === 'DONE' ? 'done' : st === 'PARTIAL' ? 'partial' : 'none');
  badge.textContent = st === 'DONE' ? '매핑완료' : st === 'PARTIAL' ? '부분매핑' : '미매핑';
}

function appRowStatus(appId) {
  const linked = (cache.bizAppMaps || []).find((m) => m.appId === appId);
  if (!linked) return { label: statusLabel('NONE'), db: '-', role: '-', mapped: false, statusCd: null };
  const isSelected = appId === selectedAppId;
  const rt = isSelected ? (cache.appRuntime || {}) : {};
  const dbOk = isSelected ? !!rt.dbLinked : null;
  return {
    label: statusLabel(linked.statusCd === 'CONFIRMED' ? 'DONE' : linked.statusCd === 'VALIDATED' ? 'PARTIAL' : linked.statusCd || 'PARTIAL'),
    db: dbOk === true ? '연결' : dbOk === false ? '미연결' : (linked.statusCd === 'CONFIRMED' ? '연결' : '-'),
    role: linked.mapRoleCd || '-',
    mapped: true,
    primary: linked.primaryYn === 'Y',
    statusCd: linked.statusCd
  };
}

function renderAppTable() {
  const linkedIds = new Set((cache.bizAppMaps || []).map((m) => m.appId));
  const apps = [...(cache.apps || [])];
  apps.sort((a, b) => {
    const la = linkedIds.has(a.appId) ? 0 : 1;
    const lb = linkedIds.has(b.appId) ? 0 : 1;
    if (la !== lb) return la - lb;
    return String(a.appId).localeCompare(String(b.appId));
  });

  $('appMapBody').innerHTML = apps.length ? apps.map((a) => {
    const st = appRowStatus(a.appId);
    const focus = a.appId === selectedAppId ? 'checked' : '';
    const pick = selectedPickIds.has(a.appId) ? 'checked' : '';
    const typeLabel = Array.isArray(a.appTypeList) && a.appTypeList.length
      ? a.appTypeList.join(',')
      : (a.appTypeCd || a.langCd || '-');
    return `<tr class="${a.appId === selectedAppId ? 'is-selected' : ''} ${st.mapped ? 'is-mapped' : ''}" data-app="${InfraApi.escapeHtml(a.appId)}">
      <td><input type="checkbox" name="appPick" value="${InfraApi.escapeHtml(a.appId)}" ${pick}></td>
      <td><input type="radio" name="appPrimary" value="${InfraApi.escapeHtml(a.appId)}" ${focus} title="인프라 조회 대상"></td>
      <td class="mono">${InfraApi.escapeHtml(a.appId || '')}</td>
      <td>${InfraApi.escapeHtml(a.appName || '')}</td>
      <td>${InfraApi.escapeHtml(typeLabel)}</td>
      <td>${InfraApi.escapeHtml(st.role)}${st.primary ? ' ★' : ''}</td>
      <td>${st.label}</td>
      <td>${InfraApi.escapeHtml(st.db)}</td>
      <td>
        ${st.mapped
          ? `<button class="btn-icon" type="button" data-action="unlink" data-app="${InfraApi.escapeHtml(a.appId)}">해제</button>`
          : `<button class="btn-icon" type="button" data-action="link" data-app="${InfraApi.escapeHtml(a.appId)}">연결</button>`}
      </td>
    </tr>`;
  }).join('') : '<tr><td colspan="9" class="empty">Application 없음</td></tr>';
}

function renderRuntime() {
  const rt = cache.appRuntime || {};
  const app = cache.selectedApp || {};
  $('infraAppHead').textContent = rt.appId
    ? `선택 App ${rt.appId}${rt.appName ? ' ' + rt.appName : ''}`
    : '선택한 Application의 연결 자원';

  const groups = rt.groups || [];
  const servers = rt.servers || [];
  if (!selectedGroupIds.size && groups.length) {
    selectedGroupIds.add(groups[0].groupId);
    selectedGroupId = groups[0].groupId;
  }

  $('groupBody').innerHTML = groups.length ? groups.map((g) => {
    const checked = selectedGroupIds.has(g.groupId) ? 'checked' : '';
    return `<tr data-group="${InfraApi.escapeHtml(g.groupId || '')}">
      <td><input type="checkbox" name="groupPick" value="${InfraApi.escapeHtml(g.groupId || '')}" ${checked}></td>
      <td class="mono">${InfraApi.escapeHtml(g.groupId || '')}</td>
      <td>${InfraApi.escapeHtml(g.groupName || '')}</td>
      <td>${InfraApi.escapeHtml(g.techRoleCd || '-')}</td>
      <td><span class="map-pill done">운영중</span></td>
    </tr>`;
  }).join('') : '<tr><td colspan="5" class="empty">연결 Server Group 없음 (INF-230)</td></tr>';

  const focusServers = [];
  groups.forEach((g) => {
    if (selectedGroupIds.has(g.groupId)) {
      (g.servers || []).forEach((s) => focusServers.push({ ...s, groupId: g.groupId }));
    }
  });
  const summaryRows = focusServers.length ? focusServers : servers;
  $('serverSummaryBody').innerHTML = summaryRows.length ? summaryRows.map((s) => `<tr>
    <td class="mono">${InfraApi.escapeHtml(s.assetId || '')}</td>
    <td>${InfraApi.escapeHtml(s.assetName || '')}</td>
    <td class="mono">${InfraApi.escapeHtml(s.ipAddr || '-')}</td>
    <td><span class="map-pill done">운영중</span></td>
  </tr>`).join('') : '<tr><td colspan="4" class="empty">-</td></tr>';

  $('serverBody').innerHTML = servers.length ? servers.map((s) => `<tr>
    <td class="mono">${InfraApi.escapeHtml(s.assetId || '')}</td>
    <td>${InfraApi.escapeHtml(s.assetName || '')}</td>
    <td class="mono">${InfraApi.escapeHtml(s.groupId || '-')}</td>
    <td><span class="map-pill done">운영중</span></td>
  </tr>`).join('') : '<tr><td colspan="4" class="empty">-</td></tr>';

  const dbs = rt.databases || [];
  $('dbBody').innerHTML = dbs.length ? dbs.map((d) => `<tr>
    <td class="mono">${InfraApi.escapeHtml(d.dbId || '')}</td>
    <td>${InfraApi.escapeHtml(d.dbName || '')}</td>
    <td>${InfraApi.escapeHtml(d.engineCd || '')}</td>
    <td>${InfraApi.escapeHtml(d.versionNo || '')}</td>
  </tr>`).join('') : '<tr><td colspan="4" class="empty">-</td></tr>';

  const mws = rt.middlewares || [];
  $('mwBody').innerHTML = mws.length ? mws.map((m) => `<tr>
    <td>${InfraApi.escapeHtml(m.productName || '')}</td>
    <td>${InfraApi.escapeHtml(m.versionNo || '')}</td>
    <td class="mono">${InfraApi.escapeHtml(m.assetId || '')}</td>
  </tr>`).join('') : '<tr><td colspan="3" class="empty">-</td></tr>';

  const nets = rt.networks || [];
  if ($('netBody')) {
    $('netBody').innerHTML = nets.length ? nets.map((n) => `<tr>
      <td class="mono">${InfraApi.escapeHtml(n.endpointId || '')}</td>
      <td>${InfraApi.escapeHtml(n.assetName || n.assetId || '')}</td>
      <td class="mono">${InfraApi.escapeHtml(n.address || '')}</td>
      <td>${InfraApi.escapeHtml(n.portNo || '')}</td>
      <td>${InfraApi.escapeHtml(n.protocolCd || '')}</td>
      <td>${n.primaryYn === 'Y' ? 'Y' : 'N'}</td>
    </tr>`).join('') : '<tr><td colspan="6" class="empty">Endpoint 없음</td></tr>';
  }
}

function renderResultSummary() {
  const rows = cache.resultSummary || [];
  if (!$('resultSummaryBody')) return;
  $('resultSummaryBody').innerHTML = rows.length ? rows.map((r) => `<tr>
    <td>${InfraApi.escapeHtml(r.detailLabel || '-')}</td>
    <td>${InfraApi.escapeHtml(String(r.appCnt ?? 0))}</td>
    <td>${InfraApi.escapeHtml(String(r.groupCnt ?? 0))}</td>
    <td>${InfraApi.escapeHtml(String(r.serverCnt ?? 0))}</td>
    <td>${InfraApi.escapeHtml(r.dbLabel || '-')}</td>
    <td>${statusLabel(r.mapStatus || 'NONE')}</td>
  </tr>`).join('') : '<tr><td colspan="6" class="empty">업무를 선택하세요</td></tr>';
}

function renderPreviewFlow() {
  const b = cache.selectedBiz || {};
  const maps = cache.bizAppMaps || [];
  const rt = cache.appRuntime || {};
  if (!b.bizCd) {
    $('previewFlow').innerHTML = '<div class="empty">업무를 선택하세요.</div>';
    return;
  }

  let appsHtml = maps.map((m) => {
    const isFocus = m.appId === selectedAppId;
    let infra = '';
    if (isFocus) {
      const groups = rt.groups || [];
      infra = groups.map((g) => {
        const cnt = (g.servers || []).length;
        return `<div class="map-flow-node map-flow-group">
          <strong class="mono">${InfraApi.escapeHtml(g.groupId || '')}</strong>
          ${cnt ? `<em>${cnt}대</em>` : ''}
        </div>`;
      }).join('');
      (rt.databases || []).forEach((d) => {
        infra += `<div class="map-flow-node map-flow-db"><strong class="mono">${InfraApi.escapeHtml(d.dbId || '')}</strong></div>`;
      });
    }
    return `<div class="map-flow-branch">
      <div class="map-flow-node map-flow-app ${isFocus ? 'is-focus' : ''}">
        <strong class="mono">${InfraApi.escapeHtml(m.appId || '')}</strong>
        <span>${InfraApi.escapeHtml(m.appName || '')}</span>
      </div>
      ${infra ? `<div class="map-flow-infra">${infra}</div>` : ''}
    </div>`;
  }).join('');

  if (!appsHtml) {
    appsHtml = '<div class="map-flow-node muted">Application 미연결</div>';
  }

  const detailNode = b.detailCd
    ? `<span class="map-flow-arrow">→</span>
      <div class="map-flow-node is-active"><strong class="mono">${InfraApi.escapeHtml(b.functionCd || b.detailShortCd || b.detailCd || '')}</strong><span>${InfraApi.escapeHtml(b.detailName || '')}</span></div>`
    : '';

  $('previewFlow').innerHTML = `
    <div class="map-flow-chain">
      <div class="map-flow-node"><strong class="mono">${InfraApi.escapeHtml(b.systemId || '')}</strong><span>${InfraApi.escapeHtml(b.systemName || '')}</span></div>
      <span class="map-flow-arrow">→</span>
      <div class="map-flow-node"><strong class="mono">${InfraApi.escapeHtml(b.domainCd || '')}</strong><span>${InfraApi.escapeHtml(b.domainName || '')}</span></div>
      <span class="map-flow-arrow">→</span>
      <div class="map-flow-node"><strong class="mono">${InfraApi.escapeHtml(b.bizCd || '')}</strong><span>${InfraApi.escapeHtml(b.bizName || '')}</span></div>
      ${detailNode}
    </div>
    <div class="map-flow-apps">${appsHtml}</div>`;
}

function renderSession() {
  const s = cache.session || {};
  $('sessionStatus').textContent = s.statusCd || 'DRAFT';
  $('sessionDtm').textContent = fmtDtm(s.chgDtm);
  $('sessionUser').textContent = s.chgUserId || '-';
}

function setTab(tab) {
  document.querySelectorAll('.map-tab').forEach((btn) => btn.classList.toggle('is-active', btn.dataset.tab === tab));
  document.querySelectorAll('.map-tab-panel').forEach((panel) => panel.classList.toggle('is-active', panel.dataset.panel === tab));
}

async function selectBiz(bizCd, domainCd, detailCd) {
  selectedBizCd = bizCd;
  selectedDomainCd = domainCd || $('domainCd').value;
  selectedDetailCd = detailCd || null;
  selectedAppId = null;
  selectedGroupId = null;
  selectedGroupIds.clear();
  selectedPickIds.clear();
  await load({ keepApp: false });
}

function pickedAppIds() {
  return [...document.querySelectorAll('input[name="appPick"]:checked')].map((el) => el.value);
}

function syncPickFromDom() {
  selectedPickIds.clear();
  pickedAppIds().forEach((id) => selectedPickIds.add(id));
}

function resolveMapRole(app) {
  const manual = ($('mapRoleCd') && $('mapRoleCd').value) || '';
  if (manual && manual !== 'PRIMARY') return manual;
  const types = (app && Array.isArray(app.appTypeList) && app.appTypeList.length)
    ? app.appTypeList.map((t) => String(t).toUpperCase())
    : String((app && app.appTypeCd) || '').toUpperCase().split(',');
  if (types.includes('UI')) return 'UI';
  if (types.includes('BATCH') && !types.includes('ONLINE')) return 'BATCH';
  if (types.includes('ONLINE')) return 'ONLINE';
  if (types.includes('BATCH')) return 'BATCH';
  return manual || 'PRIMARY';
}

async function linkOneBizApp(appId, opts = {}) {
  const b = cache.selectedBiz || {};
  const app = (cache.apps || []).find((a) => a.appId === appId) || {};
  const alreadyPrimary = (cache.bizAppMaps || []).some((m) => m.primaryYn === 'Y');
  const res = await InfraApi.postService('ifina2400C0', {
    linkType: 'BIZ_APP',
    systemId: $('systemId').value,
    domainCd: b.domainCd || selectedDomainCd || $('domainCd').value,
    bizCd: b.bizCd || selectedBizCd,
    appId,
    envCd: $('envCd').value || 'PROD',
    mapRoleCd: opts.mapRoleCd || resolveMapRole(app),
    primaryYn: opts.primaryYn || (alreadyPrimary ? 'N' : 'Y')
  }, SCID);
  return res.dto || {};
}

async function linkBizApp(appId) {
  if (!selectedBizCd) { alert('업무코드를 선택하세요.'); return; }
  syncPickFromDom();
  let targets = appId ? [appId] : [...selectedPickIds];
  if (!targets.length) {
    const focus = selectedAppId || (document.querySelector('input[name="appPrimary"]:checked') || {}).value;
    if (focus) targets = [focus];
  }
  // 이미 연결된 앱 제외
  const linked = new Set((cache.bizAppMaps || []).map((m) => m.appId));
  targets = targets.filter((id) => id && !linked.has(id));
  if (!targets.length) {
    alert('연결할 Application을 선택하세요.\n(체크박스로 여러 App을 고른 뒤 [선택 App 연결]을 누르세요)');
    return;
  }

  let ok = 0;
  const errors = [];
  for (const id of targets) {
    const dto = await linkOneBizApp(id);
    if (dto.RSLT_CD && dto.RSLT_CD !== '0000') {
      errors.push(`${id}: ${dto.RSLT_CD} ${dto.RSLT_MSG || ''}`);
    } else {
      ok += Number(dto.PROC_CNT || 1);
      // 다음 연결은 보조로
      if (!(cache.bizAppMaps || []).some((m) => m.primaryYn === 'Y')) {
        cache.bizAppMaps = [...(cache.bizAppMaps || []), { appId: id, primaryYn: 'Y' }];
      } else {
        cache.bizAppMaps = [...(cache.bizAppMaps || []), { appId: id, primaryYn: 'N' }];
      }
    }
  }
  if (errors.length) alert(errors.join('\n'));
  if (ok > 0) {
    selectedAppId = targets[targets.length - 1];
    selectedPickIds.clear();
    toast(`App 연결 ${ok}건`);
    await load(); // ③ 표·④ 인프라·⑥ 미리보기 즉시 갱신
  }
}

async function unlinkBizApp(appId) {
  if (!selectedBizCd) { alert('업무코드를 선택하세요.'); return; }
  syncPickFromDom();
  let targets = appId ? [appId] : [...selectedPickIds];
  if (!targets.length && selectedAppId) targets = [selectedAppId];
  const linked = new Set((cache.bizAppMaps || []).map((m) => m.appId));
  targets = targets.filter((id) => linked.has(id));
  if (!targets.length) { alert('해제할 App을 선택하세요.'); return; }
  if (!confirm(`연결 해제?\n${selectedBizCd} ↔ ${targets.join(', ')}`)) return;

  let ok = 0;
  const errors = [];
  for (const id of targets) {
    const res = await InfraApi.postService('ifina2400D0', {
      unlinkType: 'BIZ_APP',
      systemId: $('systemId').value,
      bizCd: selectedBizCd,
      appId: id,
      envCd: $('envCd').value || 'PROD'
    }, SCID);
    const dto = res.dto || {};
    if (dto.RSLT_CD && dto.RSLT_CD !== '0000') {
      errors.push(`${id}: ${dto.RSLT_CD} ${dto.RSLT_MSG || ''}`);
    } else {
      ok += Number(dto.PROC_CNT || 1);
    }
  }
  if (errors.length) alert(errors.join('\n'));
  if (ok > 0) {
    selectedPickIds.clear();
    selectedAppId = null;
    toast(`연결해제 ${ok}건`);
    await load({ keepApp: false });
  }
}

async function sessionAction(action) {
  if (!selectedBizCd || !selectedAppId) {
    alert('업무코드와 Application을 선택하세요.');
    return;
  }
  const op = (typeof InfraApi.getOperator === 'function') ? InfraApi.getOperator() : { optrEno: 'E0000001' };
  const res = await InfraApi.postService('ifina2400U0', {
    systemId: $('systemId').value,
    bizCd: selectedBizCd,
    appId: selectedAppId,
    envCd: $('envCd').value || 'PROD',
    action,
    chgUserId: op.optrEno || 'E0000001'
  }, SCID);
  const dto = res.dto || {};
  if (dto.RSLT_CD && dto.RSLT_CD !== '0000') {
    $('sessionWarn').textContent = dto.RSLT_MSG || '';
    alert(`${dto.RSLT_CD}: ${dto.RSLT_MSG || ''}`);
    return;
  }
  if (dto.warnings && dto.warnings.length) {
    $('sessionWarn').textContent = `경고: ${dto.warnings.join(', ')}`;
    toast(`경고: ${dto.warnings.join(', ')}`);
  } else {
    $('sessionWarn').textContent = '';
    toast(`${action} → ${dto.statusCd || ''}`);
  }
  await load();
}

function resetForm() {
  $('appKeyword').value = '';
  $('treeKeyword').value = '';
  $('mapStatusFilter').value = '';
  $('envCd').value = 'PROD';
  selectedBizCd = null;
  selectedDetailCd = null;
  selectedDomainCd = null;
  selectedAppId = null;
  selectedGroupId = null;
  selectedGroupIds.clear();
  selectedPickIds.clear();
  load({ keepApp: false }).catch(console.error);
}

$('searchBtn').onclick = () => load().catch(console.error);
$('filterSearchBtn').onclick = () => load().catch(console.error);
$('resetBtn').onclick = resetForm;
$('appSearchBtn').onclick = () => load().catch(console.error);
$('linkBizAppBtn').onclick = () => linkBizApp().catch(console.error);
$('unlinkBizAppBtn').onclick = () => unlinkBizApp().catch(console.error);
$('draftBtn').onclick = () => sessionAction('DRAFT').catch(console.error);
$('validateBtn').onclick = () => sessionAction('VALIDATE').catch(console.error);
$('confirmBtn').onclick = () => sessionAction('CONFIRM').catch(console.error);
$('cancelBtn').onclick = () => sessionAction('CANCEL').catch(console.error);
$('excelBtn').onclick = () => toast('엑셀 내보내기는 후속 구현 예정입니다.');
$('helpBtn').onclick = () => toast('INF-240: 업무코드에 Application을 여러 개 연결할 수 있습니다. 체크 후 [선택 App 연결]');

$('systemId').onchange = () => {
  selectedDomainCd = null; selectedBizCd = null; selectedDetailCd = null; selectedAppId = null; selectedPickIds.clear(); selectedGroupIds.clear();
  load({ keepApp: false }).catch(console.error);
};
$('domainCd').onchange = () => {
  selectedDomainCd = $('domainCd').value; selectedBizCd = null; selectedDetailCd = null; selectedAppId = null; selectedPickIds.clear(); selectedGroupIds.clear();
  load({ keepApp: false }).catch(console.error);
};
$('envCd').onchange = () => load({ keepApp: false }).catch(console.error);
$('mapStatusFilter').onchange = () => renderBizTree();
$('treeKeyword').oninput = () => renderBizTree();

$('runtimeTabs').onclick = (ev) => {
  const btn = ev.target.closest('.map-tab');
  if (btn) setTab(btn.dataset.tab);
};

$('bizTreePanel').onclick = (ev) => {
  const detailBtn = ev.target.closest('[data-detail]');
  if (detailBtn) {
    selectBiz(detailBtn.dataset.biz, detailBtn.dataset.domain, detailBtn.dataset.detail).catch(console.error);
    return;
  }
  const row = ev.target.closest('summary[data-biz]');
  if (!row) return;
  if (ev.target.closest('summary') && ev.offsetX < 18) return;
  selectBiz(row.dataset.biz, row.dataset.domain, null).catch(console.error);
};

$('appMapBody').onclick = (ev) => {
  const btn = ev.target.closest('[data-action]');
  if (btn) {
    if (btn.dataset.action === 'link') linkBizApp(btn.dataset.app).catch(console.error);
    if (btn.dataset.action === 'unlink') unlinkBizApp(btn.dataset.app).catch(console.error);
    return;
  }
  const pick = ev.target.closest('input[name="appPick"]');
  if (pick) {
    if (pick.checked) selectedPickIds.add(pick.value);
    else selectedPickIds.delete(pick.value);
    return;
  }
  const radio = ev.target.closest('input[name="appPrimary"]');
  if (radio) {
    selectedAppId = radio.value;
    selectedGroupId = null;
    load().catch(console.error);
    return;
  }
  const tr = ev.target.closest('tr[data-app]');
  if (tr && tr.dataset.app) {
    selectedAppId = tr.dataset.app;
    selectedGroupId = null;
    load().catch(console.error);
  }
};

$('groupBody').onclick = (ev) => {
  const check = ev.target.closest('input[name="groupPick"]');
  const tr = ev.target.closest('tr[data-group]');
  if (check) {
    if (check.checked) selectedGroupIds.add(check.value);
    else selectedGroupIds.delete(check.value);
    selectedGroupId = [...selectedGroupIds][0] || null;
    renderRuntime();
    return;
  }
  if (tr && tr.dataset.group) {
    const gid = tr.dataset.group;
    if (selectedGroupIds.has(gid)) selectedGroupIds.delete(gid);
    else selectedGroupIds.add(gid);
    selectedGroupId = [...selectedGroupIds][0] || null;
    renderRuntime();
  }
};

load().catch(console.error);
