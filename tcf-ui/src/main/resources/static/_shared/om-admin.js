/**
 * NSIGHT OM 운영관리 포털 공통 유틸 (tcf-ui)
 * tcf-om(8097) API를 /api/relay/OM/online 으로 호출합니다.
 */
window.OmAdmin = (function () {
  const BUSINESS_CODE = 'OM';
  const SESSION_KEY = 'nsight.om.session';
  const NAV_PRIMARY = [
    { id: 'dashboard', label: '운영 대시보드', href: '/om/admin/dashboard.html' },
    { id: 'transaction-log', label: '거래로그 조회', href: '/om/admin/transaction-log.html' },
    { id: 'service-catalog', label: 'ServiceId 관리', href: '/om/admin/service-catalog.html' },
    { id: 'message-composer', label: '공통 전문 조립', href: '/om/admin/message-composer.html' },
    { id: 'user-auth', label: '사용자 / 권한 / 메뉴', href: '/om/admin/user-auth.html' },
    { id: 'session', label: '세션 관리', href: '/om/admin/session.html' },
    { id: 'audit-log', label: '감사로그 조회', href: '/om/admin/audit-log.html' }
  ];

  const NAV_SECONDARY = [
    { id: 'error-code', label: '오류코드 / 메시지', href: '/om/admin/error-code.html' },
    { id: 'batch', label: '배치 / 스케줄', href: '/om/admin/batch.html' },
    { id: 'health-check', label: 'Health Check', href: '/om/admin/health-check.html' },
    { id: 'system-config', label: '환경설정 조회', href: '/om/admin/system-config.html' },
    { id: 'file-management', label: '파일 관리', href: '/om/admin/file-management.html' }
  ];

  const NAV_TERTIARY = [
    { id: 'common-code', label: '공통코드 관리', href: '/om/admin/common-code.html' },
    { id: 'function-auth', label: '기능권한', href: '/om/admin/function-auth.html' },
    { id: 'data-auth', label: '데이터권한', href: '/om/admin/data-auth.html' },
    { id: 'auth-history', label: '권한이력', href: '/om/admin/auth-history.html' },
    { id: 'cache', label: 'Cache 관리', href: '/om/admin/cache.html' }
  ];

  const NAV = [...NAV_PRIMARY, ...NAV_SECONDARY, ...NAV_TERTIARY];

  const TX = {
    authLogin: { serviceId: 'OM.Auth.login', transactionCode: 'OM-AUT-0002' },
    authLogout: { serviceId: 'OM.Auth.logout', transactionCode: 'OM-AUT-0003' },
    authSession: { serviceId: 'OM.Auth.session', transactionCode: 'OM-AUT-0004' },
    dashboard: { serviceId: 'OM.Dashboard.inquiry', transactionCode: 'OM-DSH-0001' },
    transactionLog: { serviceId: 'OM.TransactionLog.inquiry', transactionCode: 'OM-TXL-0001' },
    transactionLogDeleteAll: { serviceId: 'OM.TransactionLog.deleteAll', transactionCode: 'OM-TXL-0002' },
    serviceCatalog: { serviceId: 'OM.ServiceCatalog.inquiry', transactionCode: 'OM-SVC-0001' },
    serviceCatalogDetail: { serviceId: 'OM.ServiceCatalog.detail', transactionCode: 'OM-SVC-0003' },
    serviceCatalogSave: { serviceId: 'OM.ServiceCatalog.save', transactionCode: 'OM-SVC-0002' },
    serviceCatalogUpdate: { serviceId: 'OM.ServiceCatalog.update', transactionCode: 'OM-SVC-0004' },
    serviceCatalogDelete: { serviceId: 'OM.ServiceCatalog.delete', transactionCode: 'OM-SVC-0005' },
    user: { serviceId: 'OM.User.inquiry', transactionCode: 'OM-USR-0001' },
    userDetail: { serviceId: 'OM.User.detail', transactionCode: 'OM-USR-0002' },
    userSave: { serviceId: 'OM.User.save', transactionCode: 'OM-USR-0003' },
    userUpdate: { serviceId: 'OM.User.update', transactionCode: 'OM-USR-0004' },
    userDelete: { serviceId: 'OM.User.delete', transactionCode: 'OM-USR-0005' },
    menu: { serviceId: 'OM.Menu.inquiry', transactionCode: 'OM-MNU-0001' },
    authGroup: { serviceId: 'OM.AuthGroup.inquiry', transactionCode: 'OM-AUT-0001' },
    auditLog: { serviceId: 'OM.AuditLog.inquiry', transactionCode: 'OM-AUD-0001' },
    errorCode: { serviceId: 'OM.ErrorCode.inquiry', transactionCode: 'OM-ERR-0001' },
    batch: { serviceId: 'OM.Batch.inquiry', transactionCode: 'OM-BAT-0001' },
    healthCheck: { serviceId: 'OM.HealthCheck.inquiry', transactionCode: 'OM-HLT-0001' },
    systemConfig: { serviceId: 'OM.SystemConfig.inquiry', transactionCode: 'OM-CFG-0001' },
    fileDownload: { serviceId: 'OM.FileDownload.inquiry', transactionCode: 'OM-FIL-0001' },
    commonCode: { serviceId: 'OM.CommonCode.inquiry', transactionCode: 'OM-CDC-0001' },
    commonCodeDetail: { serviceId: 'OM.CommonCode.detail', transactionCode: 'OM-CDC-0003' },
    commonCodeSave: { serviceId: 'OM.CommonCode.save', transactionCode: 'OM-CDC-0002' },
    commonCodeUpdate: { serviceId: 'OM.CommonCode.update', transactionCode: 'OM-CDC-0004' },
    commonCodeDelete: { serviceId: 'OM.CommonCode.delete', transactionCode: 'OM-CDC-0005' },
    errorCodeSave: { serviceId: 'OM.ErrorCode.save', transactionCode: 'OM-ERR-0002' },
    errorCodeDetail: { serviceId: 'OM.ErrorCode.detail', transactionCode: 'OM-ERR-0003' },
    errorCodeUpdate: { serviceId: 'OM.ErrorCode.update', transactionCode: 'OM-ERR-0004' },
    errorCodeDelete: { serviceId: 'OM.ErrorCode.delete', transactionCode: 'OM-ERR-0005' },
    batchExecute: { serviceId: 'OM.Batch.execute', transactionCode: 'OM-BAT-0002' },
    functionAuth: { serviceId: 'OM.FunctionAuth.inquiry', transactionCode: 'OM-FAU-0001' },
    dataAuth: { serviceId: 'OM.DataAuth.inquiry', transactionCode: 'OM-DAU-0001' },
    authHistory: { serviceId: 'OM.AuthHistory.inquiry', transactionCode: 'OM-AHT-0001' },
    cache: { serviceId: 'OM.Cache.inquiry', transactionCode: 'OM-CCH-0001' },
    cacheDelete: { serviceId: 'OM.Cache.delete', transactionCode: 'OM-CCH-0002' },
    session: { serviceId: 'OM.Session.inquiry', transactionCode: 'OM-SES-0001' },
    sessionDelete: { serviceId: 'OM.Session.delete', transactionCode: 'OM-SES-0002' }
  };

  let config = { deploymentMode: 'bootrun', bootrunHost: 'http://127.0.0.1', tomcatGatewayUrl: 'http://localhost:8080' };
  let targetUrl = '-';

  function todayIsoDate() {
    const now = new Date();
    const y = now.getFullYear();
    const m = String(now.getMonth() + 1).padStart(2, '0');
    const d = String(now.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
  }

  function todaySystemDate() {
    return todayIsoDate().replace(/-/g, '');
  }

  function newGuid() {
    if (window.crypto && crypto.randomUUID) return crypto.randomUUID();
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
      const r = Math.random() * 16 | 0;
      return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
  }

  function nowIsoKst() {
    const now = new Date();
    const offset = now.getTime() + (9 * 60 - now.getTimezoneOffset()) * 60000;
    return new Date(offset).toISOString().replace('Z', '+09:00');
  }

  function buildRelayQuery() {
    return new URLSearchParams({
      deploymentMode: config.deploymentMode,
      bootrunHost: config.bootrunHost,
      tomcatGatewayUrl: config.tomcatGatewayUrl
    }).toString();
  }

  function getSession() {
    try {
      const raw = sessionStorage.getItem(SESSION_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch (e) {
      return null;
    }
  }

  function setSession(session) {
    sessionStorage.setItem(SESSION_KEY, JSON.stringify(session));
  }

  function clearSession() {
    sessionStorage.removeItem(SESSION_KEY);
  }

  function syncSessionFromBody(body) {
    if (!body || !body.loggedIn) {
      return null;
    }
    const session = {
      userId: body.userId,
      userName: body.userName,
      branchId: body.branchId,
      authGroupId: body.authGroupId,
      authGroupName: body.authGroupName,
      sessionId: body.sessionId,
      lastLoginTime: body.lastLoginTime
    };
    setSession(session);
    return session;
  }

  async function requireAuth() {
    if (location.pathname.endsWith('login.html')) {
      return null;
    }
    try {
      await loadConfig();
      const { body } = await call('authSession', {}, 'INQUIRY');
      if (body.loggedIn) {
        return syncSessionFromBody(body);
      }
    } catch (e) {
      /* 서버 세션 없음 */
    }
    clearSession();
    location.href = '/om/admin/login.html';
    return null;
  }

  async function logout() {
    try {
      await mutate('authLogout', {}, 'EXECUTE');
    } catch (e) {
      /* ignore */
    } finally {
      clearSession();
      location.href = '/om/admin/login.html';
    }
  }

  function buildStandardHeader(options) {
    const session = getSession();
    const systemDate = todaySystemDate();
    const o = options || {};
    return {
      systemId: o.systemId || 'NSIGHT-MP',
      businessCode: (o.businessCode || BUSINESS_CODE).toUpperCase(),
      serviceId: o.serviceId || '',
      transactionCode: o.transactionCode || '',
      processingType: (o.processingType || 'INQUIRY').toUpperCase(),
      guid: o.guid || newGuid(),
      traceId: o.traceId != null ? o.traceId : '',
      channelId: o.channelId || 'WEBTOP',
      userId: o.userId != null ? o.userId : (session && session.userId ? session.userId : 'GUEST'),
      branchId: o.branchId != null ? o.branchId : (session && session.branchId ? session.branchId : ''),
      centerId: o.centerId || 'DC1',
      requestTime: o.requestTime || nowIsoKst(),
      transactionIntime: o.transactionIntime || nowIsoKst(),
      systemDate: o.systemDate || systemDate,
      bizDate: o.bizDate || systemDate,
      clientIp: o.clientIp || '127.0.0.1',
      idempotencyKey: o.idempotencyKey != null ? o.idempotencyKey : ''
    };
  }

  function buildHeader(tx, processingType) {
    return buildStandardHeader({
      businessCode: BUSINESS_CODE,
      serviceId: tx.serviceId,
      transactionCode: tx.transactionCode,
      processingType: processingType || 'INQUIRY'
    });
  }

  async function loadConfig() {
    const res = await fetch('/api/config');
    if (res.ok) {
      const data = await res.json();
      config.deploymentMode = data.deploymentMode || config.deploymentMode;
      config.bootrunHost = data.bootrunHost || config.bootrunHost;
      config.tomcatGatewayUrl = data.tomcatGatewayUrl || config.tomcatGatewayUrl;
    }
    const urlRes = await fetch(`/api/business-modules/${BUSINESS_CODE}/target-url?${buildRelayQuery()}`);
    if (urlRes.ok) {
      const data = await urlRes.json();
      targetUrl = data.targetUrl || targetUrl;
    }
    return config;
  }

  async function login(userId, password) {
    const tx = TX.authLogin;
    const request = {
      header: { ...buildHeader(tx, 'EXECUTE'), userId: userId || 'GUEST', branchId: '' },
      body: { userId, password }
    };
    const res = await fetch(`/api/relay/${BUSINESS_CODE}/online?${buildRelayQuery()}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request)
    });
    const relay = await res.json();
    if (!relay.responseBody) {
      throw new Error(relay.errorMessage || `HTTP ${relay.httpStatus}: 응답 없음`);
    }
    let payload;
    try {
      payload = JSON.parse(relay.responseBody);
    } catch (e) {
      throw new Error('응답 JSON 파싱 실패');
    }
    if (relay.httpStatus >= 400 || (payload.result && payload.result.status === 'ERROR')) {
      throw new Error(payload.result?.message || payload.result?.errorMessage || '로그인에 실패했습니다.');
    }
    const body = payload.body || {};
    if (!body.loggedIn) {
      throw new Error('로그인에 실패했습니다.');
    }
    syncSessionFromBody(body);
    return body;
  }

  async function relayMessage(businessCode, request) {
    const code = (businessCode || BUSINESS_CODE).toUpperCase();
    const res = await fetch(`/api/relay/${code}/online?${buildRelayQuery()}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request)
    });
    const relay = await res.json();
    let payload = null;
    if (relay.responseBody) {
      try {
        payload = JSON.parse(relay.responseBody);
      } catch (e) {
        payload = null;
      }
    }
    return { payload, relay };
  }

  async function call(txKey, body, processingType) {
    const tx = typeof txKey === 'string' ? TX[txKey] : txKey;
    const request = { header: buildHeader(tx, processingType), body: body || {} };
    const res = await fetch(`/api/relay/${BUSINESS_CODE}/online?${buildRelayQuery()}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request)
    });
    const relay = await res.json();
    if (!relay.responseBody) {
      throw new Error(relay.errorMessage || `HTTP ${relay.httpStatus}: 응답 없음`);
    }
    let payload;
    try {
      payload = JSON.parse(relay.responseBody);
    } catch (e) {
      throw new Error('응답 JSON 파싱 실패');
    }
    if (relay.httpStatus >= 400) {
      let msg = payload.result?.message || payload.error;
      if (!msg && relay.httpStatus === 502) {
        msg = 'tcf-om(8097)에 연결할 수 없습니다. tcf-om을 먼저 실행하세요.';
      }
      throw new Error(msg || `HTTP ${relay.httpStatus}`);
    }
    if (payload.result && payload.result.status === 'ERROR') {
      throw new Error(payload.result.message || payload.result.errorMessage || '거래 오류');
    }
    return { payload, relay, body: payload.body || {} };
  }

  async function inquiry(txKey, body) {
    return call(txKey, body, 'INQUIRY');
  }

  async function mutate(txKey, body, processingType) {
    return call(txKey, body, processingType);
  }

  function field(row, name, fallback) {
    if (!row) return fallback !== undefined ? fallback : '-';
    if (row[name] != null && row[name] !== '') return row[name];
    const upper = name.toUpperCase();
    if (row[upper] != null && row[upper] !== '') return row[upper];
    const key = Object.keys(row).find(k => k.toUpperCase() === upper);
    return key ? row[key] : (fallback !== undefined ? fallback : '-');
  }

  function chipForHealth(status) {
    const s = String(status || '').toUpperCase();
    if (['NORMAL', 'UP', 'SUCCESS', 'OK'].some(v => s.includes(v))) {
      return `<span class="om-chip ok">${status || 'OK'}</span>`;
    }
    if (['WARN', 'WARNING', '주의'].some(v => s.includes(v))) {
      return `<span class="om-chip warn">${status}</span>`;
    }
    if (['FAIL', 'ERROR', 'DOWN', '장애'].some(v => s.includes(v))) {
      return `<span class="om-chip fail">${status}</span>`;
    }
    return `<span class="om-chip muted">${status || '-'}</span>`;
  }

  function chipForResult(status) {
    const s = String(status || '').toUpperCase();
    if (s.includes('SUCCESS') || s === 'OK') return `<span class="om-chip ok">${status}</span>`;
    if (s.includes('FAIL') || s.includes('ERROR') || s.includes('위반')) {
      return `<span class="om-chip fail">${status}</span>`;
    }
    return `<span class="om-chip muted">${status || '-'}</span>`;
  }

  function renderNavSection(items, pageId) {
    return items.map(item =>
      `<a href="${item.href}" class="${item.id === pageId ? 'active' : ''}">${item.label}</a>`
    ).join('');
  }

  function renderShell(pageId, title) {
    const session = getSession();
    const userLabel = session
      ? `${session.userName || session.userId} (${session.userId})`
      : '';
    document.body.innerHTML = `
      <div class="om-admin">
        <aside class="om-sidebar">
          <div class="om-brand">
            <h1>NSIGHT OM</h1>
            <p>운영관리 포털 · Operation Management</p>
          </div>
          <nav class="om-nav">
            <div class="om-nav-section">
              <div class="om-nav-label">1차 운영관리</div>
              ${renderNavSection(NAV_PRIMARY, pageId)}
            </div>
            <div class="om-nav-section">
              <div class="om-nav-label">2차 운영관리</div>
              ${renderNavSection(NAV_SECONDARY, pageId)}
            </div>
            <div class="om-nav-section">
              <div class="om-nav-label">3차 운영관리</div>
              ${renderNavSection(NAV_TERTIARY, pageId)}
            </div>
          </nav>
          <div class="om-nav-footer">
            <a href="/om/index-multi.html">↗ API 거래 테스트</a>
            <a href="/index.html">← TCF UI 홈</a>
          </div>
        </aside>
        <main class="om-main">
          <header class="om-topbar">
            <h2>${title}</h2>
            <div class="om-topbar-meta">
              ${userLabel ? `<span class="om-user-badge">${userLabel}</span>` : ''}
              <button type="button" class="btn-secondary om-logout-btn" id="omLogoutBtn">로그아웃</button>
              <span id="omTargetUrl" title="tcf-om URL">${targetUrl}</span>
            </div>
          </header>
          <div class="om-content" id="omContent">
            <div class="om-empty">불러오는 중...</div>
          </div>
        </main>
      </div>`;
    const logoutBtn = document.getElementById('omLogoutBtn');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', () => logout());
    }
    return document.getElementById('omContent');
  }

  function renderPagination(container, pageNo, pageSize, totalCount, onPage, prevNextOnly) {
    const totalPages = Math.max(1, Math.ceil(totalCount / pageSize));
    if (totalCount === 0) {
      container.innerHTML = '';
      container.hidden = true;
      return;
    }
    container.hidden = false;
    let nums = '';
    if (!prevNextOnly) {
      for (let i = 1; i <= totalPages; i += 1) {
        nums += `<button type="button" class="om-page-btn ${i === pageNo ? 'active' : ''}" data-page="${i}">${i}</button>`;
      }
    }
    container.innerHTML = `
      <button type="button" class="om-page-btn" data-page="prev" ${pageNo <= 1 ? 'disabled' : ''}>PREV</button>
      ${nums}
      <button type="button" class="om-page-btn" data-page="next" ${pageNo >= totalPages ? 'disabled' : ''}>NEXT</button>
      <span style="color:var(--muted);font-size:0.85rem;margin-left:8px">${pageNo} / ${totalPages} · 총 ${totalCount}건</span>`;
    container.querySelectorAll('[data-page]').forEach(btn => {
      btn.addEventListener('click', () => {
        const v = btn.getAttribute('data-page');
        if (v === 'prev' && pageNo > 1) onPage(pageNo - 1);
        else if (v === 'next' && pageNo < totalPages) onPage(pageNo + 1);
        else if (v !== 'prev' && v !== 'next') onPage(Number(v));
      });
    });
  }

  function showError(container, message) {
    const hint = targetUrl && targetUrl !== '-'
      ? `릴레이 대상: <code>${targetUrl}</code>`
      : 'tcf-om(포트 8097)와 tcf-ui(8099)를 함께 기동했는지 확인하세요.';
    container.innerHTML = `<div class="om-alert error">${message}<br><small>${hint}</small></div>`;
  }

  function showErrorBanner(container, message) {
    const existing = container.querySelector('.om-load-error');
    if (existing) existing.remove();
    const hint = targetUrl && targetUrl !== '-'
      ? ` (${targetUrl})`
      : '';
    const banner = document.createElement('div');
    banner.className = 'om-alert error om-load-error';
    banner.innerHTML = `${message}<br><small>tcf-om을 재빌드 후 NsightTcfOmApplication을 재시작하세요.${hint}</small>`;
    container.prepend(banner);
  }

  async function pingBackend() {
    try {
      const res = await fetch(`/api/business-modules/${BUSINESS_CODE}/target-url?${buildRelayQuery()}`);
      if (!res.ok) return false;
      const data = await res.json();
      targetUrl = data.targetUrl || targetUrl;
      const el = document.getElementById('omTargetUrl');
      if (el) el.textContent = targetUrl;
      return true;
    } catch (e) {
      return false;
    }
  }

  function updownloadQuery(extra) {
    const params = new URLSearchParams({
      deploymentMode: config.deploymentMode,
      bootrunHost: config.bootrunHost,
      tomcatGatewayUrl: config.tomcatGatewayUrl
    });
    if (extra) {
      Object.entries(extra).forEach(([k, v]) => {
        if (v != null && v !== '') params.set(k, v);
      });
    }
    return params.toString();
  }

  async function updownloadBaseUrl() {
    const res = await fetch(`/api/updownload/base-url?${updownloadQuery()}`);
    if (!res.ok) throw new Error('UD 서비스 URL을 확인할 수 없습니다.');
    return res.json();
  }

  async function updownloadList(filters) {
    const res = await fetch(`/api/updownload/files?${updownloadQuery(filters)}`);
    const text = await res.text();
    let payload;
    try {
      payload = JSON.parse(text);
    } catch (e) {
      throw new Error('파일 목록 응답 파싱 실패');
    }
    if (payload.body && payload.body.error) {
      throw new Error(payload.body.hint || payload.body.error);
    }
    if (payload.result && (payload.result.resultCode === 'E0001' || payload.result.status === 'ERROR')) {
      throw new Error(payload.result.resultMessage || payload.result.errorMessage || '목록 조회 실패');
    }
    return payload.body || {};
  }

  async function updownloadUpload(file, description, businessCode) {
    const session = getSession();
    const formData = new FormData();
    formData.append('file', file);
    formData.append('userId', session && session.userId ? session.userId : 'GUEST');
    if (description) formData.append('description', description);
    if (businessCode) formData.append('businessCode', businessCode);
    const res = await fetch(`/api/updownload/upload?${updownloadQuery()}`, { method: 'POST', body: formData });
    const text = await res.text();
    let payload;
    try {
      payload = JSON.parse(text);
    } catch (e) {
      throw new Error(text || '업로드 실패');
    }
    if (payload.result && payload.result.resultCode && payload.result.resultCode.startsWith('E')) {
      throw new Error(payload.result.resultMessage || '업로드 실패');
    }
    if (!payload.body || !payload.body.file) {
      throw new Error('업로드 응답이 올바르지 않습니다.');
    }
    return payload.body.file;
  }

  async function updownloadDelete(fileId) {
    const res = await fetch(`/api/updownload/files/${encodeURIComponent(fileId)}?${updownloadQuery()}`, { method: 'DELETE' });
    const payload = await res.json();
    if (!payload.body || !payload.body.deleted) {
      throw new Error(payload.result?.resultMessage || '삭제 실패');
    }
    return payload.body;
  }

  function updownloadDownloadUrl(fileId) {
    const session = getSession();
    const userId = session && session.userId ? session.userId : 'GUEST';
    return `/api/updownload/files/${encodeURIComponent(fileId)}/download?${updownloadQuery({ userId })}`;
  }

  async function updownloadDetail(fileId) {
    const res = await fetch(`/api/updownload/files/${encodeURIComponent(fileId)}?${updownloadQuery()}`);
    const payload = await res.json();
    if (payload.body && payload.body.error) {
      throw new Error(payload.body.error);
    }
    if (payload.result && payload.result.status === 'ERROR') {
      throw new Error(payload.result.resultMessage || '상세 조회 실패');
    }
    if (!payload.body || !payload.body.file) {
      throw new Error('파일 정보를 찾을 수 없습니다.');
    }
    return payload.body.file;
  }

  async function updownloadUpdate(fileId, description) {
    const res = await fetch(
      `/api/updownload/files/${encodeURIComponent(fileId)}?${updownloadQuery({ description: description || '' })}`,
      { method: 'PUT' }
    );
    const payload = await res.json();
    if (payload.body && payload.body.error) {
      throw new Error(payload.body.error);
    }
    if (payload.result && payload.result.status === 'ERROR') {
      throw new Error(payload.result.resultMessage || '수정 실패');
    }
    if (!payload.body || !payload.body.file) {
      throw new Error('수정 응답이 올바르지 않습니다.');
    }
    return payload.body.file;
  }

  async function loadCommonCodes(codeGroup, options) {
    const opts = options || {};
    if (opts.forceRefresh) {
      invalidateCommonCodeCache(codeGroup);
    }
    const memKey = commonCodeCacheKey(codeGroup, opts.useYn);
    if (!opts.forceRefresh && commonCodeMemoryCache.has(memKey)) {
      return commonCodeMemoryCache.get(memKey);
    }
    const body = {
      codeGroup,
      pageNo: 1,
      pageSize: opts.pageSize || 500
    };
    if (opts.useYn != null && opts.useYn !== '') {
      body.useYn = opts.useYn;
    }
    const { body: data } = await inquiry('commonCode', body);
    const rows = sortCommonCodeRows(data.rows || []);
    if (data.fromCache !== false) {
      commonCodeMemoryCache.set(memKey, rows);
    }
    return rows;
  }

  async function loadCodeGroups(options) {
    const opts = options || {};
    if (opts.forceRefresh) {
      commonCodeMemoryCache.delete('__groups__');
    }
    if (!opts.forceRefresh && commonCodeMemoryCache.has('__groups__')) {
      return commonCodeMemoryCache.get('__groups__');
    }
    const body = { pageNo: 1, pageSize: 500 };
    if (opts.useYn != null && opts.useYn !== '') {
      body.useYn = opts.useYn;
    }
    const { body: data } = await inquiry('commonCode', body);
    const groups = [];
    (data.rows || []).forEach(row => {
      const code = field(row, 'codeGroup', '') || field(row, 'code', '');
      if (!code) return;
      groups.push({ code, codeName: field(row, 'codeName', code) });
    });
    const sorted = groups.sort((a, b) => a.code.localeCompare(b.code));
    if (data.fromCache) {
      commonCodeMemoryCache.set('__groups__', sorted);
    }
    return sorted;
  }

  const commonCodeMemoryCache = new Map();
  const DEFAULT_PREFETCH_CODE_GROUPS = ['BUSINESS_CODE', 'AUTH_CODE', 'CACHE_NAME'];

  function commonCodeCacheKey(codeGroup, useYn) {
    const yn = useYn != null && useYn !== '' ? useYn : 'ALL';
    return `${codeGroup}|${yn}`;
  }

  function sortCommonCodeRows(rows) {
    return rows.slice().sort((a, b) => {
      const sa = Number(field(a, 'sortOrder', 0));
      const sb = Number(field(b, 'sortOrder', 0));
      if (sa !== sb) return sa - sb;
      return String(field(a, 'code', '')).localeCompare(String(field(b, 'code', '')));
    });
  }

  function invalidateCommonCodeCache(codeGroup) {
    if (codeGroup) {
      [...commonCodeMemoryCache.keys()]
        .filter(k => k.startsWith(`${codeGroup}|`))
        .forEach(k => commonCodeMemoryCache.delete(k));
    } else {
      commonCodeMemoryCache.clear();
    }
    commonCodeMemoryCache.delete('__groups__');
  }

  async function prefetchCommonCodes(codeGroups, options) {
    const groups = codeGroups && codeGroups.length ? codeGroups : DEFAULT_PREFETCH_CODE_GROUPS;
    await Promise.all(groups.map(g => loadCommonCodes(g, options).catch(() => [])));
  }

  function fillCodeSelect(selectEl, codes, options) {
    if (!selectEl) return;
    const opts = options || {};
    const includeAll = !!opts.includeAll;
    const allLabel = opts.allLabel || '전체';
    const selected = opts.selected != null ? opts.selected : '';
    const parts = [];
    if (includeAll) {
      parts.push(`<option value="">${allLabel}</option>`);
    }
    (codes || []).forEach(row => {
      const code = field(row, 'code', '');
      const name = field(row, 'codeName', '');
      const label = name && name !== '-' ? `${code} · ${name}` : code;
      parts.push(`<option value="${code}">${label}</option>`);
    });
    selectEl.innerHTML = parts.join('');
    if (selected !== '') {
      selectEl.value = selected;
    }
    if (selected && selectEl.value !== selected && codes && codes.length) {
      selectEl.selectedIndex = includeAll ? 1 : 0;
    }
  }

  function formatCodeLabel(codes, code) {
    if (!code || code === '-') return '-';
    const row = (codes || []).find(r => field(r, 'code') === code);
    if (!row) return code;
    const name = field(row, 'codeName', '');
    return name && name !== '-' ? `${code} (${name})` : code;
  }

  async function initPage(pageId, title, renderFn) {
    const session = await requireAuth();
    if (!session) {
      return;
    }
    await loadConfig();
    const container = renderShell(pageId, title);
    const ok = await pingBackend();
    if (!ok) {
      showError(container, 'tcf-ui → tcf-om 릴레이 URL을 확인할 수 없습니다.');
      return;
    }
    try {
      prefetchCommonCodes(DEFAULT_PREFETCH_CODE_GROUPS, { useYn: 'Y' }).catch(() => {});
      await renderFn(container);
    } catch (err) {
      showError(container, err.message || String(err));
    }
  }

  return {
    NAV, TX, config, targetUrl, SESSION_KEY,
    todayIsoDate, todaySystemDate, newGuid, nowIsoKst, field,
    buildStandardHeader, relayMessage,
    chipForHealth, chipForResult,
    getSession, setSession, clearSession, requireAuth, logout, login,
    inquiry, mutate, call, initPage, renderPagination, showError, showErrorBanner, loadConfig,
    updownloadQuery, updownloadBaseUrl, updownloadList, updownloadUpload, updownloadDelete, updownloadDownloadUrl,
    updownloadDetail, updownloadUpdate,
    loadCommonCodes, loadCodeGroups, fillCodeSelect, formatCodeLabel,
    invalidateCommonCodeCache, prefetchCommonCodes
  };
})();
