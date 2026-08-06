/*
 * pdmk-service 전문 테스트용 단일 거래 화면 스크립트.
 * 요청 Body는 {"dto":{...}} 형식(pdmk-fw RequestBody resolver).
 */

let transactions = [];
let config = {};

const targetBaseUrlEl = document.getElementById('targetBaseUrl');
const transactionIdEl = document.getElementById('transactionId');
const requestBodyEl = document.getElementById('requestBody');
const pageFieldsEl = document.getElementById('pageFields');
const pageNoEl = document.getElementById('pageNo');
const pageSizeEl = document.getElementById('pageSize');
const responseBodyEl = document.getElementById('responseBody');
const responseMetaEl = document.getElementById('responseMeta');

function defaultTransactionId() {
  return document.documentElement.dataset.defaultTransactionId || '';
}

function programFilter() {
  return document.documentElement.dataset.programId || '';
}

async function init() {
  const [txRes, configRes] = await Promise.all([
    fetch('/api/transactions'),
    fetch('/api/config')
  ]);
  const all = await txRes.json();
  const programId = programFilter();
  transactions = programId ? all.filter(tx => tx.programId === programId) : all;
  config = await configRes.json();

  targetBaseUrlEl.value = config.targetBaseUrl || 'http://localhost:8080';
  renderTransactionOptions();

  const id = defaultTransactionId();
  const selected = transactions.some(tx => tx.id === id) ? id : transactions[0]?.id;
  if (selected) {
    transactionIdEl.value = selected;
    await selectTransaction(selected);
  }
}

function renderTransactionOptions() {
  transactionIdEl.innerHTML = transactions
    .map(tx => `<option value="${tx.id}">${tx.id} - ${tx.name}</option>`)
    .join('');
}

async function selectTransaction(id) {
  const tx = transactions.find(item => item.id === id);
  if (!tx) return;

  document.getElementById('metaName').innerHTML =
    `${tx.name}<span class="group-tag">${tx.programId}</span>`;
  document.getElementById('metaEndpoint').textContent = `${tx.method} ${tx.path}`;
  document.getElementById('metaDescription').textContent = tx.description;
  requestBodyEl.value = JSON.stringify(tx.sampleRequest, null, 2);
  updatePagingFields(tx.sampleRequest);
  await refreshTargetUrl();
}

function dtoOf(sampleRequest) {
  return sampleRequest && sampleRequest.dto && typeof sampleRequest.dto === 'object'
      ? sampleRequest.dto
      : {};
}

function updatePagingFields(sampleRequest) {
  if (!pageFieldsEl || !pageNoEl || !pageSizeEl) {
    return;
  }

  const dto = dtoOf(sampleRequest);
  const hasPaging = Object.prototype.hasOwnProperty.call(dto, 'pageNo')
      || Object.prototype.hasOwnProperty.call(dto, 'pageSize');
  pageFieldsEl.style.display = hasPaging ? 'grid' : 'none';

  pageNoEl.value = dto.pageNo != null ? dto.pageNo : '1';
  pageSizeEl.value = dto.pageSize != null ? dto.pageSize : '20';
}

function mergePagingIntoPayload(payload) {
  if (!pageNoEl || !pageSizeEl || !pageFieldsEl || pageFieldsEl.style.display === 'none') {
    return payload;
  }

  if (!payload.dto || typeof payload.dto !== 'object') {
    payload.dto = {};
  }

  const pageNo = parseInt(pageNoEl.value, 10);
  if (!Number.isNaN(pageNo)) {
    payload.dto.pageNo = pageNo;
  }

  const pageSize = parseInt(pageSizeEl.value, 10);
  if (!Number.isNaN(pageSize)) {
    payload.dto.pageSize = pageSize;
  }

  return payload;
}

async function refreshTargetUrl() {
  const query = new URLSearchParams({ baseUrl: targetBaseUrlEl.value.trim() });
  const res = await fetch(`/api/transactions/${transactionIdEl.value}/target-url?${query}`);
  document.getElementById('metaTargetUrl').textContent =
    res.ok ? (await res.json()).targetUrl : 'URL 계산 실패';
}

function describeError(parsed, httpStatus) {
  if (parsed && parsed.error) {
    return parsed.hint ? `${parsed.error} · ${parsed.hint}` : parsed.error;
  }
  if (parsed && parsed.message) {
    return parsed.message;
  }
  return `HTTP ${httpStatus} 응답`;
}

async function sendRequest() {
  let payload;
  try {
    payload = JSON.parse(requestBodyEl.value);
  } catch (error) {
    alert('요청 JSON 형식이 올바르지 않습니다.\n' + error.message);
    return;
  }

  payload = mergePagingIntoPayload(payload);
  requestBodyEl.value = JSON.stringify(payload, null, 2);

  responseMetaEl.innerHTML = '<span class="empty">요청 중...</span>';
  responseBodyEl.value = '';

  const query = new URLSearchParams({ baseUrl: targetBaseUrlEl.value.trim() });
  const response = await fetch(`/api/relay/${transactionIdEl.value}?${query}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  const result = await response.json();
  const httpOk = result.httpStatus >= 200 && result.httpStatus < 300;

  let parsed = null;
  try {
    parsed = JSON.parse(result.responseBody);
    responseBodyEl.value = JSON.stringify(parsed, null, 2);
  } catch (error) {
    responseBodyEl.value = result.responseBody || '';
  }

  const ok = httpOk && !(parsed && parsed.error);
  responseMetaEl.innerHTML = `
    <span class="badge ${ok ? 'ok' : 'fail'}">HTTP ${result.httpStatus}</span>
    <span>${result.elapsedMs} ms</span>
    <span>${result.targetUrl}</span>
    ${ok ? '' : `<span class="badge fail">${describeError(parsed, result.httpStatus)}</span>`}
  `;
}

targetBaseUrlEl.addEventListener('change', refreshTargetUrl);
transactionIdEl.addEventListener('change', () => selectTransaction(transactionIdEl.value));
document.getElementById('reloadSampleBtn').addEventListener('click', () => selectTransaction(transactionIdEl.value));
document.getElementById('sendBtn').addEventListener('click', sendRequest);

init().catch(error => alert('화면 초기화 실패: ' + error.message));
