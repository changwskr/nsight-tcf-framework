/*
 * 이미지로그 관리 화면.
 * html data-* 로 OM(7777) / Service(8888) 전환.
 *   data-program-id, data-default-base-url, data-list-api, data-delete-api, data-target-label
 */

const pageCfg = {
  programId: document.documentElement.dataset.programId || 'mkcoa7777',
  defaultBaseUrl: document.documentElement.dataset.defaultBaseUrl || 'http://localhost:8081',
  listApi: document.documentElement.dataset.listApi || '/api/imagelog/list',
  deleteApi: document.documentElement.dataset.deleteApi || '/api/imagelog/delete',
  targetLabel: document.documentElement.dataset.targetLabel || 'pdmk-om'
};

const targetBaseUrlEl = document.getElementById('targetBaseUrl');
const guidEl = document.getElementById('guid');
const serviceIdEl = document.getElementById('serviceId');
const screenIdEl = document.getElementById('screenId');
const optrEnoEl = document.getElementById('optrEno');
const exceptionOnlyEl = document.getElementById('exceptionOnly');
const withinSecondsEl = document.getElementById('withinSeconds');
const withinHintEl = document.getElementById('withinHint');
const withinSecondsGroup = document.getElementById('withinSecondsGroup');
const pageSizeEl = document.getElementById('pageSize');
const resultMetaEl = document.getElementById('resultMeta');
const resultCountEl = document.getElementById('resultCount');
const resultBodyEl = document.getElementById('resultBody');
const detailModalEl = document.getElementById('detailModal');
const detailBodyEl = document.getElementById('detailBody');
const checkAllEl = document.getElementById('checkAll');
const pageInfoEl = document.getElementById('pageInfo');
const prevPageBtn = document.getElementById('prevPageBtn');
const nextPageBtn = document.getElementById('nextPageBtn');

let rowsCache = [];
let pageNo = 1;
let totalPages = 1;
let totalCount = 0;

function text(value) {
  if (value === null || value === undefined || value === '') {
    return '-';
  }
  return String(value);
}

function escapeHtml(value) {
  return text(value)
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;');
}

function currentPageSize() {
  const size = parseInt(pageSizeEl.value, 10);
  if (Number.isNaN(size) || size <= 0) {
    return 20;
  }
  return Math.min(size, 100);
}

function currentWithinSeconds() {
  const raw = withinSecondsEl.value.trim();
  if (!raw) {
    return null;
  }
  const n = parseInt(raw, 10);
  if (Number.isNaN(n) || n <= 0) {
    return null;
  }
  return n;
}

function formatWithinLabel(seconds) {
  if (seconds == null) {
    return '시간창 미적용 · 전체 기간';
  }
  if (seconds < 60) {
    return `현재시각 기준 최근 ${seconds}초 이내 REQUEST_TIME`;
  }
  if (seconds % 60 === 0) {
    return `현재시각 기준 최근 ${seconds / 60}분 이내 REQUEST_TIME`;
  }
  return `현재시각 기준 최근 ${seconds}초 이내 REQUEST_TIME`;
}

function setWithinSeconds(seconds, { syncException } = {}) {
  const value = seconds == null || seconds === '' ? '' : String(seconds);
  withinSecondsEl.value = value;
  const n = currentWithinSeconds();
  withinHintEl.textContent = formatWithinLabel(n);

  withinSecondsGroup.querySelectorAll('.time-chip').forEach((btn) => {
    const chipSec = btn.dataset.seconds || '';
    btn.classList.toggle('active', chipSec === value);
  });

  // 시간창을 고르면 문제건(예외) 추적이 기본 의도 → 예외만 체크 권장
  if (syncException && n != null) {
    exceptionOnlyEl.checked = true;
  }
}

function parseYmdHms(value) {
  const s = String(value || '').replace(/\D/g, '');
  if (s.length < 14) {
    return null;
  }
  const y = Number(s.slice(0, 4));
  const mo = Number(s.slice(4, 6)) - 1;
  const d = Number(s.slice(6, 8));
  const h = Number(s.slice(8, 10));
  const mi = Number(s.slice(10, 12));
  const se = Number(s.slice(12, 14));
  const ms = s.length >= 17 ? Number(s.slice(14, 17)) : 0;
  const dt = new Date(y, mo, d, h, mi, se, ms);
  return Number.isNaN(dt.getTime()) ? null : dt;
}

function elapsedLabel(row) {
  const req = parseYmdHms(pickField(row, 'requestTime', 'REQUEST_TIME'));
  const res = parseYmdHms(pickField(row, 'responseTime', 'RESPONSE_TIME'));
  if (!req || !res) {
    return '-';
  }
  const ms = res.getTime() - req.getTime();
  if (ms < 0) {
    return '-';
  }
  if (ms < 1000) {
    return `${ms}ms`;
  }
  return `${(ms / 1000).toFixed(1)}s`;
}

function buildListBody() {
  const withinSeconds = currentWithinSeconds();
  return {
    hdr_nhnis: {
      sys_comm: {
        std_gbl_id: crypto.randomUUID().replaceAll('-', ''),
        rms_svc_c: pageCfg.programId + 'S0',
        scid: pageCfg.programId,
        optr_eno: 'LOCAL',
        tr_trm_ipadr: '127.0.0.1',
        tr_sysid: 'PDMK-UI',
        sync_dsc: 'S',
        std_tgrm_rqr_rsp_dsc: 'Q'
      }
    },
    dto: {
      guid: guidEl.value.trim() || null,
      serviceId: serviceIdEl.value.trim() || null,
      screenId: screenIdEl.value.trim() || null,
      optrEno: optrEnoEl.value.trim() || null,
      exceptionOnly: !!exceptionOnlyEl.checked,
      withinSeconds: withinSeconds,
      pageNo: pageNo,
      pageSize: currentPageSize()
    }
  };
}

function extractDto(parsed) {
  return parsed && parsed.dto && typeof parsed.dto === 'object' ? parsed.dto : parsed;
}

function extractRows(parsed) {
  const dto = extractDto(parsed);
  if (!dto || typeof dto !== 'object') {
    return [];
  }
  if (Array.isArray(dto.mkcoa7777S0DTOSub0)) {
    return dto.mkcoa7777S0DTOSub0;
  }
  if (Array.isArray(dto.mkcoa8888S0DTOSub0)) {
    return dto.mkcoa8888S0DTOSub0;
  }
  if (Array.isArray(dto.records)) {
    return dto.records;
  }
  return [];
}

function statusBadge(row) {
  if (row && row.exceptionType) {
    return '<span class="badge fail">예외</span>';
  }
  if (row && row.responseTime) {
    return '<span class="badge ok">정상</span>';
  }
  return '<span class="badge">진행</span>';
}

function updatePager() {
  pageInfoEl.textContent = `${pageNo} / ${Math.max(totalPages, 1)} · 전체 ${totalCount}건`;
  prevPageBtn.disabled = pageNo <= 1;
  nextPageBtn.disabled = pageNo >= totalPages || totalPages <= 0;
}

function selectedGuids() {
  return [...resultBodyEl.querySelectorAll('input.row-check:checked')]
      .map((el) => el.value)
      .filter(Boolean);
}

function syncCheckAll() {
  const checks = [...resultBodyEl.querySelectorAll('input.row-check')];
  checkAllEl.checked = checks.length > 0 && checks.every((el) => el.checked);
  checkAllEl.indeterminate = checks.some((el) => el.checked) && !checkAllEl.checked;
}

function renderRows(rows, paging) {
  rowsCache = rows || [];
  totalCount = paging && paging.totalCount != null ? Number(paging.totalCount) : rowsCache.length;
  totalPages = paging && paging.totalPages != null
      ? Number(paging.totalPages)
      : (currentPageSize() <= 0 ? 0 : Math.ceil(totalCount / currentPageSize()));
  if (paging && paging.pageNo != null) {
    pageNo = Number(paging.pageNo);
  }
  resultCountEl.textContent = `${rowsCache.length}건`;
  updatePager();
  checkAllEl.checked = false;
  checkAllEl.indeterminate = false;

  if (!rowsCache.length) {
    resultBodyEl.innerHTML = '<tr><td colspan="10" class="empty">조회 결과가 없습니다.</td></tr>';
    return;
  }

  resultBodyEl.innerHTML = rowsCache.map((row, index) => `
    <tr class="clickable" data-index="${index}">
      <td class="col-check"><input class="row-check" type="checkbox" value="${escapeHtml(row.guid)}"></td>
      <td class="mono">${escapeHtml(row.guid)}</td>
      <td>${escapeHtml(row.serviceId)}</td>
      <td>${escapeHtml(row.screenId)}</td>
      <td>${escapeHtml(row.optrEno)}</td>
      <td>${escapeHtml(row.clientIp)}</td>
      <td class="mono">${escapeHtml(row.requestTime)}</td>
      <td class="mono">${escapeHtml(row.responseTime)}</td>
      <td class="mono">${escapeHtml(elapsedLabel(row))}</td>
      <td>${statusBadge(row)}</td>
    </tr>
  `).join('');
}

function prettyWire(value) {
  if (value == null || value === '') {
    return '';
  }
  const raw = String(value);
  try {
    return JSON.stringify(JSON.parse(raw), null, 2);
  } catch (_e) {
    return raw;
  }
}

function pickField(row, ...keys) {
  if (!row || typeof row !== 'object') {
    return '';
  }
  for (const key of keys) {
    if (row[key] != null && String(row[key]).trim() !== '') {
      return row[key];
    }
  }
  const lowerMap = {};
  Object.keys(row).forEach((k) => {
    lowerMap[k.toLowerCase()] = row[k];
  });
  for (const key of keys) {
    const found = lowerMap[String(key).toLowerCase()];
    if (found != null && String(found).trim() !== '') {
      return found;
    }
  }
  return '';
}

function buildDetailLog(row) {
  const requestMsg = prettyWire(pickField(row, 'requestMsg', 'REQUEST_MSG', 'request_msg'));
  const responseMsg = prettyWire(pickField(row, 'responseMsg', 'RESPONSE_MSG', 'response_msg'));
  const lines = [
    `[이미지로그] ${text(pickField(row, 'guid', 'GUID'))}`,
    `서비스: ${text(pickField(row, 'serviceId', 'SERVICE_ID'))}`,
    `화면: ${text(pickField(row, 'screenId', 'SCREEN_ID'))}`,
    `사용자: ${text(pickField(row, 'optrEno', 'OPTR_ENO'))}`,
    `IP: ${text(pickField(row, 'clientIp', 'CLIENT_IP'))}`,
    `요청시각: ${text(pickField(row, 'requestTime', 'REQUEST_TIME'))}`,
    `응답시각: ${text(pickField(row, 'responseTime', 'RESPONSE_TIME'))}`,
    `소요: ${elapsedLabel(row)}`,
    `예외타입: ${text(pickField(row, 'exceptionType', 'EXCEPTION_TYPE'))}`,
    `예외코드: ${text(pickField(row, 'exceptionCode', 'EXCEPTION_CODE'))}`,
    `예외메시지: ${text(pickField(row, 'exceptionMsg', 'EXCEPTION_MSG'))}`,
    '',
    '---- 요청 전문 ----',
    requestMsg || '(없음)',
    '',
    '---- 응답 전문 ----',
    responseMsg || '(없음)'
  ];
  return lines.join('\n');
}

function renderDetail(row) {
  const requestMsg = prettyWire(pickField(row, 'requestMsg', 'REQUEST_MSG', 'request_msg'));
  const responseMsg = prettyWire(pickField(row, 'responseMsg', 'RESPONSE_MSG', 'response_msg'));
  const fields = [
    ['GUID', pickField(row, 'guid', 'GUID')],
    ['서비스 ID', pickField(row, 'serviceId', 'SERVICE_ID')],
    ['화면 ID', pickField(row, 'screenId', 'SCREEN_ID')],
    ['사용자', pickField(row, 'optrEno', 'OPTR_ENO')],
    ['클라이언트 IP', pickField(row, 'clientIp', 'CLIENT_IP')],
    ['요청시각', pickField(row, 'requestTime', 'REQUEST_TIME')],
    ['응답시각', pickField(row, 'responseTime', 'RESPONSE_TIME')],
    ['소요', elapsedLabel(row)],
    ['예외타입', pickField(row, 'exceptionType', 'EXCEPTION_TYPE')],
    ['예외코드', pickField(row, 'exceptionCode', 'EXCEPTION_CODE')],
    ['예외메시지', pickField(row, 'exceptionMsg', 'EXCEPTION_MSG')]
  ];
  const fullLog = buildDetailLog(row);

  detailBodyEl.innerHTML = `
    <dl class="detail-grid">
      ${fields.map(([label, value]) => `
        <div>
          <dt>${escapeHtml(label)}</dt>
          <dd class="${label.includes('메시지') || label === 'GUID' ? 'mono wrap' : 'mono'}">${escapeHtml(value)}</dd>
        </div>
      `).join('')}
    </dl>
    <div class="wire-block">
      <h3>요청 전문</h3>
      <pre class="error-log mono">${escapeHtml(requestMsg || '(없음)')}</pre>
    </div>
    <div class="wire-block">
      <h3>응답 전문</h3>
      <pre class="error-log mono">${escapeHtml(responseMsg || '(없음)')}</pre>
    </div>
    <div class="error-log-block">
      <div class="error-log-head">
        <span>전체 로그</span>
        <button class="btn-secondary btn-tiny" type="button" id="detailLogCopy">복사</button>
      </div>
      <pre class="error-log mono" id="detailFullLog">${escapeHtml(fullLog)}</pre>
    </div>
  `;
  const copyBtn = detailBodyEl.querySelector('#detailLogCopy');
  if (copyBtn) {
    copyBtn.addEventListener('click', async () => {
      try {
        await navigator.clipboard.writeText(fullLog);
        copyBtn.textContent = '복사됨';
        setTimeout(() => { copyBtn.textContent = '복사'; }, 1200);
      } catch (_e) {
        copyBtn.textContent = '실패';
        setTimeout(() => { copyBtn.textContent = '복사'; }, 1200);
      }
    });
  }
  detailModalEl.hidden = false;
}

function closeDetail() {
  detailModalEl.hidden = true;
}

async function search() {
  resultMetaEl.innerHTML = '<span class="empty">조회 중...</span>';
  resultBodyEl.innerHTML = '<tr><td colspan="10" class="empty">조회 중...</td></tr>';

  const query = new URLSearchParams({ baseUrl: targetBaseUrlEl.value.trim() });
  let result;
  try {
    const response = await fetch(`${pageCfg.listApi}?${query}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(buildListBody())
    });
    result = await response.json();
  } catch (error) {
    resultMetaEl.innerHTML = '<span class="badge fail">중계 실패</span>';
    resultBodyEl.innerHTML = '<tr><td colspan="10" class="empty">조회 실패</td></tr>';
    PdmkErrorPopup.showSimple(error.message || String(error), '중계 오류');
    return;
  }
  const httpOk = result.httpStatus >= 200 && result.httpStatus < 300;

  let parsed = null;
  try {
    parsed = JSON.parse(result.responseBody);
  } catch (error) {
    parsed = null;
  }

  const dto = extractDto(parsed) || {};
  const rows = extractRows(parsed);
  const serviceError = PdmkErrorPopup.errorPayload(parsed);
  const ok = httpOk && !(parsed && parsed.error) && !serviceError;
  renderRows(rows, {
    pageNo: dto.pageNo,
    pageSize: dto.pageSize,
    totalCount: dto.totalCount,
    totalPages: dto.totalPages
  });

  const within = currentWithinSeconds();
  resultMetaEl.innerHTML = `
    <span class="badge ${ok ? 'ok' : 'fail'}">HTTP ${result.httpStatus}</span>
    <span>${result.elapsedMs} ms</span>
    <span>Total: ${totalCount} · page ${pageNo}/${Math.max(totalPages, 1)}</span>
    <span>${escapeHtml(formatWithinLabel(within))}</span>
    ${exceptionOnlyEl.checked ? '<span class="badge fail">예외만</span>' : ''}
    <span>${escapeHtml(result.targetUrl)}</span>
    ${ok ? '' : `<span class="badge fail">${escapeHtml((parsed && (parsed.error || parsed.message)) || '조회 실패')}</span>`}
  `;

  if (!ok) {
    PdmkErrorPopup.showFromResponse(
        parsed,
        result.httpStatus,
        '이미지로그 조회에 실패했습니다.',
        result.responseBody);
  }
}

async function deleteSelected() {
  const guids = selectedGuids();
  if (!guids.length) {
    PdmkErrorPopup.showSimple('삭제할 항목을 선택하세요.', '확인');
    return;
  }
  if (!confirm(`${guids.length}건을 삭제할까요?`)) {
    return;
  }

  const query = new URLSearchParams({ baseUrl: targetBaseUrlEl.value.trim() });
  let result;
  try {
    const response = await fetch(`${pageCfg.deleteApi}?${query}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        hdr_nhnis: {
          sys_comm: {
            std_gbl_id: crypto.randomUUID().replaceAll('-', ''),
            rms_svc_c: pageCfg.programId + 'D0',
            scid: pageCfg.programId,
            optr_eno: 'LOCAL',
            tr_trm_ipadr: '127.0.0.1',
            tr_sysid: 'PDMK-UI',
            sync_dsc: 'S',
            std_tgrm_rqr_rsp_dsc: 'Q'
          }
        },
        dto: { guidList: guids, GUID_LIST: guids }
      })
    });
    result = await response.json();
  } catch (error) {
    PdmkErrorPopup.showSimple(error.message || String(error), '중계 오류');
    return;
  }

  const httpOk = result.httpStatus >= 200 && result.httpStatus < 300;

  let parsed = null;
  try {
    parsed = JSON.parse(result.responseBody);
  } catch (error) {
    parsed = null;
  }
  const dto = extractDto(parsed) || {};
  const serviceError = PdmkErrorPopup.errorPayload(parsed);
  const ok = httpOk && !(parsed && parsed.error) && !serviceError
      && (dto.RSLT_CD == null || dto.RSLT_CD === '0000');
  if (!ok) {
    if (!PdmkErrorPopup.showFromResponse(
        parsed,
        result.httpStatus,
        dto.RSLT_MSG || '삭제에 실패했습니다.',
        result.responseBody)) {
      PdmkErrorPopup.showSimple(
          (parsed && (parsed.error || parsed.message)) || dto.RSLT_MSG || result.responseBody || 'unknown',
          '삭제 실패');
    }
    return;
  }

  alert(`삭제 완료: ${dto.PROC_CNT != null ? dto.PROC_CNT : guids.length}건`);
  await search();
}

function resetFilters() {
  guidEl.value = '';
  serviceIdEl.value = '';
  screenIdEl.value = '';
  optrEnoEl.value = '';
  exceptionOnlyEl.checked = false;
  pageSizeEl.value = '20';
  setWithinSeconds('', { syncException: false });
  pageNo = 1;
  totalPages = 1;
  totalCount = 0;
  resultMetaEl.innerHTML = '<span class="empty">최근 구간을 고르고 조회하세요.</span>';
  renderRows([], { pageNo: 1, totalCount: 0, totalPages: 1 });
}

async function init() {
  const configRes = await fetch('/api/config');
  const config = await configRes.json();
  const configured = (config.targetBaseUrl || '').trim();
  // 페이지별 기본 URL 우선. service(8888)=8080, om(7777)=8081
  if (pageCfg.defaultBaseUrl.includes(':8080')) {
    targetBaseUrlEl.value = configured.includes(':8080') ? configured : pageCfg.defaultBaseUrl;
  } else if (pageCfg.defaultBaseUrl.includes(':8081')) {
    targetBaseUrlEl.value = configured.includes(':8081') ? configured : pageCfg.defaultBaseUrl;
  } else {
    targetBaseUrlEl.value = pageCfg.defaultBaseUrl || configured || 'http://localhost:8080';
  }
  document.getElementById('targetInfo').textContent =
      `대상 ${pageCfg.targetLabel}: ${targetBaseUrlEl.value} · ${pageCfg.programId}`;
  // 기본: 최근 5분 · 전체(예외만 OFF). 「예외만」은 문제 추적 시 사용자가 켠다.
  setWithinSeconds('300', { syncException: false });
  exceptionOnlyEl.checked = false;

  withinSecondsGroup.addEventListener('click', (event) => {
    const btn = event.target.closest('.time-chip');
    if (!btn) {
      return;
    }
    const seconds = btn.dataset.seconds;
    // 시간창만 바꾸고, 예외필터는 자동으로 켜지 않는다(정상 건도 조회 가능).
    setWithinSeconds(seconds === '' ? '' : seconds, { syncException: false });
    pageNo = 1;
    search().catch((error) => PdmkErrorPopup.showSimple('조회 실패: ' + error.message));
  });

  document.getElementById('searchBtn').addEventListener('click', () => {
    pageNo = 1;
    search().catch((error) => PdmkErrorPopup.showSimple('조회 실패: ' + error.message));
  });
  document.getElementById('resetBtn').addEventListener('click', resetFilters);
  document.getElementById('deleteBtn').addEventListener('click', () => {
    deleteSelected().catch((error) => PdmkErrorPopup.showSimple('삭제 실패: ' + error.message));
  });

  prevPageBtn.addEventListener('click', () => {
    if (pageNo <= 1) {
      return;
    }
    pageNo -= 1;
    search().catch((error) => PdmkErrorPopup.showSimple('조회 실패: ' + error.message));
  });
  nextPageBtn.addEventListener('click', () => {
    if (pageNo >= totalPages) {
      return;
    }
    pageNo += 1;
    search().catch((error) => PdmkErrorPopup.showSimple('조회 실패: ' + error.message));
  });

  checkAllEl.addEventListener('change', () => {
    resultBodyEl.querySelectorAll('input.row-check').forEach((el) => {
      el.checked = checkAllEl.checked;
    });
  });

  resultBodyEl.addEventListener('click', (event) => {
    if (event.target.classList.contains('row-check')) {
      syncCheckAll();
      return;
    }
    const tr = event.target.closest('tr[data-index]');
    if (!tr) {
      return;
    }
    const index = Number(tr.dataset.index);
    if (!Number.isNaN(index) && rowsCache[index]) {
      renderDetail(rowsCache[index]);
    }
  });

  detailModalEl.addEventListener('click', (event) => {
    if (event.target.dataset.close === 'true') {
      closeDetail();
    }
  });

  document.addEventListener('keydown', (event) => {
    if (event.key === 'Escape' && !detailModalEl.hidden) {
      closeDetail();
    }
  });
}

init().catch((error) => PdmkErrorPopup.showSimple('화면 초기화 실패: ' + error.message));
