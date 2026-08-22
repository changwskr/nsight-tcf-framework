const SCID = 'INF-220';
let pageNo = 1, totalPages = 1, totalCount = 0, rowsCache = [], editMode = 'create', orgAppId = null;
const $ = (id) => document.getElementById(id);
const APP_TYPES = ['ONLINE', 'BATCH', 'UI'];

function pageSize() {
  const n = parseInt($('pageSize').value, 10);
  return Number.isNaN(n) || n <= 0 ? 10 : Math.min(n, 100);
}
function updatePager() {
  $('pageInfo').textContent = `${pageNo} / ${Math.max(totalPages, 1)} · 전체 ${totalCount}건`;
  $('prevPageBtn').disabled = pageNo <= 1;
  $('nextPageBtn').disabled = pageNo >= totalPages || totalPages <= 0;
}
function unlockAppId() {
  const el = $('formAppId');
  el.removeAttribute('disabled');
  el.removeAttribute('readonly');
  el.disabled = false;
  el.readOnly = false;
  el.style.pointerEvents = 'auto';
  el.tabIndex = 0;
}
function parseTypes(r) {
  if (Array.isArray(r.appTypeList) && r.appTypeList.length) {
    return r.appTypeList.map((t) => String(t).toUpperCase()).filter((t) => APP_TYPES.includes(t));
  }
  const raw = (r.appTypeCd || '').toUpperCase();
  if (!raw) return ['ONLINE'];
  return raw.split(/[,|/;\\s]+/).map((t) => t.trim()).filter((t) => APP_TYPES.includes(t));
}
function selectedTypes() {
  return [...document.querySelectorAll('#formAppTypeList input[name="appType"]:checked')].map((el) => el.value);
}
function setTypeChecks(types) {
  const set = new Set((types || []).map((t) => String(t).toUpperCase()));
  document.querySelectorAll('#formAppTypeList input[name="appType"]').forEach((el) => {
    el.checked = set.has(el.value);
  });
}
function render(rows) {
  rowsCache = rows || [];
  $('resultCount').textContent = `${totalCount}건`;
  updatePager();
  if (!rowsCache.length) {
    $('resultBody').innerHTML = '<tr><td colspan="8" class="empty">데이터 없음</td></tr>';
    return;
  }
  $('resultBody').innerHTML = rowsCache.map((r, i) => {
    const types = parseTypes(r);
    const typeHtml = types.length
      ? types.map((t) => `<span class="type-pill">${InfraApi.escapeHtml(t)}</span>`).join(' ')
      : '-';
    return `<tr data-index="${i}">
    <td class="mono">${InfraApi.escapeHtml(r.appId)}</td><td>${InfraApi.escapeHtml(r.appName)}</td>
    <td>${typeHtml}</td>
    <td class="mono">${InfraApi.escapeHtml(r.systemId)}</td><td>${InfraApi.escapeHtml(r.langCd)}</td>
    <td>${InfraApi.statusBadge(r.statusCd)}</td><td>${InfraApi.escapeHtml(r.remark)}</td>
    <td><button class="btn-icon" data-action="edit" type="button">수정</button>
        <button class="btn-icon" data-action="delete" type="button">삭제</button></td></tr>`;
  }).join('');
}
async function search(reset) {
  if (reset) pageNo = 1;
  $('resultMeta').textContent = '조회 중…';
  const res = await InfraApi.postService('ifina2200S0', {
    keyword: $('keyword').value.trim() || null,
    systemId: $('systemId').value.trim() || null,
    pageNo,
    pageSize: pageSize()
  }, SCID);
  const dto = res.dto || {};
  const rows = Array.isArray(dto.ifina2200S0DTOSub0) ? dto.ifina2200S0DTOSub0 : [];
  totalCount = dto.totalCount != null ? Number(dto.totalCount) : rows.length;
  totalPages = dto.totalPages != null ? Number(dto.totalPages) : Math.max(1, Math.ceil(totalCount / pageSize()));
  pageNo = dto.pageNo != null ? Number(dto.pageNo) : pageNo;
  render(rows);
  $('resultMeta').textContent = `HTTP ${res.httpStatus} · ${res.elapsedMs}ms · ifina2200S0`;
}
function openCreate() {
  editMode = 'create';
  orgAppId = null;
  $('editTitle').textContent = 'Application 등록';
  unlockAppId();
  $('formAppId').value = '';
  $('formAppName').value = '';
  $('formSystemId').value = 'SYS-MKTG';
  if (window.InfraCombo) InfraCombo.set($('formSystemId'), 'SYS-MKTG');
  setTypeChecks(['ONLINE']);
  $('formLangCd').value = 'JAVA';
  $('formStatusCd').value = 'CONFIRMED';
  $('formRemark').value = '';
  $('editModal').hidden = false;
  $('formAppId').focus();
}
function openEdit(r) {
  editMode = 'edit';
  orgAppId = r.appId || '';
  $('editTitle').textContent = 'Application 수정';
  unlockAppId();
  $('formAppId').value = r.appId || '';
  $('formAppName').value = r.appName || '';
  if (window.InfraCombo) InfraCombo.set($('formSystemId'), r.systemId || 'SYS-MKTG');
  else $('formSystemId').value = r.systemId || 'SYS-MKTG';
  setTypeChecks(parseTypes(r));
  $('formLangCd').value = r.langCd || 'JAVA';
  $('formStatusCd').value = r.statusCd || 'DISCOVERED';
  $('formRemark').value = r.remark || '';
  $('editModal').hidden = false;
  $('formAppId').focus();
}
async function save() {
  const appTypeList = selectedTypes();
  if (!appTypeList.length) {
    alert('실행형태를 1개 이상 선택하세요 (ONLINE/BATCH/UI)');
    return;
  }
  const payload = {
    appId: $('formAppId').value.trim(),
    appName: $('formAppName').value.trim(),
    systemId: $('formSystemId').value.trim(),
    appTypeList,
    appTypeCd: appTypeList.join(','),
    langCd: $('formLangCd').value,
    statusCd: $('formStatusCd').value,
    remark: $('formRemark').value.trim()
  };
  if (!payload.appId || !payload.appName || !payload.systemId) {
    alert('App ID/명/시스템은 필수');
    return;
  }
  if (editMode === 'edit') {
    payload.orgAppId = orgAppId || payload.appId;
    if (payload.orgAppId !== payload.appId) {
      if (!confirm(`App ID를 변경할까요?\n${payload.orgAppId} → ${payload.appId}\n관련 매핑/세션도 함께 갱신됩니다.`)) {
        return;
      }
    }
  }
  const res = await InfraApi.postService(editMode === 'create' ? 'ifina2200C0' : 'ifina2200U0', payload, SCID);
  const dto = res.dto || {};
  if (dto.RSLT_CD && dto.RSLT_CD !== '0000') {
    alert(`${dto.RSLT_CD}: ${dto.RSLT_MSG || ''}`);
    return;
  }
  $('editModal').hidden = true;
  await search(editMode === 'create');
}
async function removeRow(r) {
  if (!confirm(`삭제할까요?\n${r.appId}\n\nApp 연결(맵)·매핑 세션·인터페이스 참조도 함께 정리됩니다.`)) return;
  const res = await InfraApi.postService('ifina2200D0', { appIdList: [r.appId] }, SCID);
  const dto = res.dto || {};
  if (!res.ok || (dto.RSLT_CD && dto.RSLT_CD !== '0000')) {
    if (!res.errorShown) {
      alert(`${dto.RSLT_CD || res.httpStatus}: ${dto.RSLT_MSG || '삭제 실패'}`);
    }
    return;
  }
  await search(false);
}
$('searchBtn').onclick = () => search(true);
$('addBtn').onclick = openCreate;
$('saveBtn').onclick = () => save().catch(console.error);
$('prevPageBtn').onclick = () => { if (pageNo > 1) { pageNo--; search(false); } };
$('nextPageBtn').onclick = () => { if (pageNo < totalPages) { pageNo++; search(false); } };
$('keyword').onkeydown = (e) => { if (e.key === 'Enter') search(true); };
$('resultBody').onclick = (e) => {
  const btn = e.target.closest('[data-action]');
  if (!btn) return;
  const r = rowsCache[Number(btn.closest('tr').dataset.index)];
  if (!r) return;
  if (btn.dataset.action === 'edit') openEdit(r);
  if (btn.dataset.action === 'delete') removeRow(r).catch(console.error);
};
$('editModal').querySelectorAll('[data-close="true"]').forEach((el) => {
  el.onclick = () => { $('editModal').hidden = true; };
});
(async () => {
  if (window.InfraCombo) await InfraCombo.boot();
  await search(true);
})().catch(console.error);
