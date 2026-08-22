/**
 * EOS 관리 화면 — Dashboard(0110) + 목록(0120) + 상세(0130).
 * 브라우저 → pdmg-eos(:8082) 직접 POST. 점수/등급 화면 재계산 금지.
 */
(function () {
  const targetBaseUrlEl = document.getElementById('targetBaseUrl');
  const keywordEl = document.getElementById('keyword');
  const riskFilterEl = document.getElementById('riskFilter');
  const pageSizeEl = document.getElementById('pageSize');
  const targetInfoEl = document.getElementById('targetInfo');
  const kpiMetaEl = document.getElementById('kpiMeta');
  const resultMetaEl = document.getElementById('resultMeta');
  const resultCountEl = document.getElementById('resultCount');
  const topBody = document.querySelector('#topTable tbody');
  const listBody = document.querySelector('#listTable tbody');
  const detailPanel = document.getElementById('detailPanel');
  const detailTitle = document.getElementById('detailTitle');
  const detailBody = document.getElementById('detailBody');

  let config = { eosBaseUrl: 'http://localhost:8082', timeoutMs: 10000 };
  let pageNo = 1;

  function newGuid() {
    if (window.crypto && typeof window.crypto.randomUUID === 'function') {
      return window.crypto.randomUUID().replace(/-/g, '');
    }
    return 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'.replace(/x/g, () =>
      ((Math.random() * 16) | 0).toString(16));
  }

  function basePayload(serviceId, scid, dto) {
    return {
      hdr_nhnis: {
        sys_comm: {
          std_gbl_id: newGuid(),
          rms_svc_c: serviceId,
          sync_dsc: 'S',
          tr_sysid: 'PDMGEOS',
          ttl_ug_ync: 0,
          std_tgrm_rqr_rsp_dsc: 'Q',
          std_tgrm_lclc: 'KO',
          tr_trm_ipadr: '127.0.0.1',
          tr_dtm: new Date().toISOString().replace(/\D/g, '').slice(0, 14),
          tr_brc: '10001',
          trmno: 'LOCAL01',
          trm_kdc: '01',
          scid: scid,
          optr_eno: 'LOCAL',
          tr_optrnm: 'EOS_UI'
        }
      },
      dto: dto || {}
    };
  }

  function baseUrl() {
    return (targetBaseUrlEl.value || config.eosBaseUrl || 'http://localhost:8082').trim();
  }

  function parseDto(responseBody) {
    try {
      const parsed = JSON.parse(responseBody || '{}');
      return parsed.dto || parsed;
    } catch (_e) {
      return null;
    }
  }

  function badge(v) {
    const s = v == null || v === '' ? '-' : String(v);
    return `<span class="eos-badge eos-badge--${s}">${s}</span>`;
  }

  function setMeta(el, text, ok) {
    if (!el) return;
    el.innerHTML = ok
      ? `<span class="ok">${text}</span>`
      : `<span class="empty">${text}</span>`;
  }

  async function call(serviceId, scid, dto) {
    const body = basePayload(serviceId, scid, dto);
    const result = await PdmgServiceClient.postPath(
      baseUrl(),
      '/' + serviceId,
      body,
      config.timeoutMs,
      serviceId
    );
    return result;
  }

  function renderKpi(dto) {
    document.querySelectorAll('[data-k]').forEach((el) => {
      const key = el.getAttribute('data-k');
      const v = dto && dto[key];
      el.textContent = v == null ? '—' : String(v);
    });
  }

  function renderTop(list) {
    const rows = Array.isArray(list) ? list : [];
    topBody.innerHTML = rows.length
      ? rows.map((r, i) => `
        <tr data-id="${r.resourceId || ''}">
          <td>${i + 1}</td>
          <td><a href="#" class="eos-open" data-id="${r.resourceId || ''}">${r.resourceId || ''}</a></td>
          <td>${r.name || ''}</td>
          <td>${badge(r.grade)}</td>
          <td>${badge(r.status)}</td>
          <td>${r.remainDays != null ? r.remainDays : ''}</td>
        </tr>`).join('')
      : '<tr><td colspan="6" class="empty">데이터 없음</td></tr>';
  }

  function renderList(dto) {
    const rows = (dto && dto.list) || [];
    const total = dto && dto.totalCount != null ? dto.totalCount : rows.length;
    resultCountEl.textContent = total + '건';
    listBody.innerHTML = rows.length
      ? rows.map((r) => `
        <tr>
          <td>${r.resourceId || ''}</td>
          <td>${r.resourceName || ''}</td>
          <td>${r.productName || ''}</td>
          <td>${r.envCd || ''}</td>
          <td>${badge(r.eosStatusCd)}</td>
          <td>${badge(r.riskGradeCd)}</td>
          <td>${r.remainDays != null ? r.remainDays : ''}</td>
          <td><button type="button" class="btn-secondary eos-open" data-id="${r.resourceId || ''}">상세</button></td>
        </tr>`).join('')
      : '<tr><td colspan="8" class="empty">조건에 맞는 자원이 없습니다</td></tr>';
  }

  async function loadDashboard() {
    const result = await call('eoscoa0110S0', 'eoscoa0110', {});
    const dto = parseDto(result.responseBody);
    if (result.httpStatus !== 200 || !dto || dto.RSLT_CD && dto.RSLT_CD !== '0000') {
      setMeta(kpiMetaEl, `KPI 실패 · HTTP ${result.httpStatus}`, false);
      if (window.PdmgErrorPopup) {
        PdmgErrorPopup.showSimple('Dashboard 조회 실패');
      }
      return;
    }
    renderKpi(dto);
    renderTop(dto.topPriorityList);
    setMeta(kpiMetaEl, `OK · ${result.elapsedMs}ms · ${baseUrl()}`, true);
  }

  async function loadList() {
    const dtoIn = {
      pageNo: pageNo,
      pageSize: parseInt(pageSizeEl.value, 10) || 20
    };
    const kw = (keywordEl.value || '').trim();
    if (kw) dtoIn.resourceName = kw;
    const risk = riskFilterEl.value;
    if (risk) dtoIn.riskGradeCd = risk;

    const result = await call('eoscoa0120S0', 'eoscoa0120', dtoIn);
    const dto = parseDto(result.responseBody);
    if (result.httpStatus !== 200 || !dto) {
      setMeta(resultMetaEl, `목록 실패 · HTTP ${result.httpStatus}`, false);
      return;
    }
    renderList(dto);
    setMeta(resultMetaEl, `OK · page ${dto.pageNo || pageNo} · ${result.elapsedMs}ms`, true);
  }

  async function loadDetail(resourceId) {
    if (!resourceId) return;
    const result = await call('eoscoa0130S0', 'eoscoa0130', { resourceId });
    const dto = parseDto(result.responseBody);
    detailPanel.classList.add('is-open');
    detailTitle.textContent = resourceId;
    detailBody.textContent = JSON.stringify(dto, null, 2);
    detailPanel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
  }

  async function refreshAll() {
    await loadDashboard();
    await loadList();
  }

  document.getElementById('searchBtn').addEventListener('click', () => {
    pageNo = 1;
    loadList();
  });
  document.getElementById('refreshBtn').addEventListener('click', refreshAll);
  keywordEl.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
      pageNo = 1;
      loadList();
    }
  });
  riskFilterEl.addEventListener('change', () => {
    pageNo = 1;
    loadList();
  });

  document.getElementById('kpiCards').addEventListener('click', (e) => {
    const card = e.target.closest('[data-drill]');
    if (!card) return;
    const drill = card.getAttribute('data-drill');
    if (drill === 'CRITICAL' || drill === 'HIGH') {
      riskFilterEl.value = drill;
      pageNo = 1;
      loadList();
    }
  });

  document.body.addEventListener('click', (e) => {
    const a = e.target.closest('.eos-open');
    if (!a) return;
    e.preventDefault();
    loadDetail(a.getAttribute('data-id'));
  });

  async function init() {
    try {
      const configRes = await fetch('/api/config');
      config = await configRes.json();
      targetBaseUrlEl.value = config.eosBaseUrl || config.targetBaseUrl || 'http://localhost:8082';
      targetInfoEl.textContent = targetBaseUrlEl.value;
    } catch (_e) {
      targetInfoEl.textContent = targetBaseUrlEl.value;
    }
    await refreshAll();
  }

  init();
})();
