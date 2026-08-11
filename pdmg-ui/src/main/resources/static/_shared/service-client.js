/**
 * 브라우저에서 pdmg-service 온라인 거래를 직접 호출한다.
 * 반환 형태는 구 RelayResult 와 동일해 기존 화면 로직을 재사용한다.
 *
 * Access Token 이 없거나 만료되면 로그인 화면(#/jwt)으로 유도한다.
 */
(function (global) {
  function trimTrailingSlash(baseUrl) {
    const value = (baseUrl || '').trim();
    return value.endsWith('/') ? value.slice(0, -1) : value;
  }

  function joinUrl(baseUrl, path) {
    const base = trimTrailingSlash(baseUrl || 'http://localhost:8080');
    let p = path == null ? '/' : String(path).trim();
    if (!p.startsWith('/')) {
      p = '/' + p;
    }
    return base + p;
  }

  var JWT_SESSION_KEY = 'pdmg.jwt.session';
  var JWT_RETURN_HASH_KEY = 'pdmg.jwt.returnHash';
  /** 만료 직전 조기 판단(초). 서버 401 직전에 UI에서 로그인으로 보낸다. */
  var AUTH_SKEW_MS = 30000;
  var redirectingToLogin = false;

  function getJwtSession() {
    try {
      const raw = sessionStorage.getItem(JWT_SESSION_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch (e) {
      return null;
    }
  }

  function decodeJwtExpMs(token) {
    if (!token) {
      return null;
    }
    try {
      const parts = String(token).split('.');
      if (parts.length < 2) {
        return null;
      }
      let base64 = parts[1].replace(/-/g, '+').replace(/_/g, '/');
      const pad = base64.length % 4;
      if (pad) {
        base64 += '='.repeat(4 - pad);
      }
      const json = JSON.parse(atob(base64));
      if (json && json.exp != null && !Number.isNaN(Number(json.exp))) {
        return Number(json.exp) * 1000;
      }
    } catch (e) {
      /* ignore */
    }
    return null;
  }

  /** Access Token 만료 시각(ms). JWT exp 우선, 없으면 loggedInAt+expiresIn. */
  function accessTokenExpiresAt(session) {
    const s = session || getJwtSession();
    if (!s || !s.accessToken) {
      return null;
    }
    const fromJwt = decodeJwtExpMs(s.accessToken);
    if (fromJwt != null) {
      return fromJwt;
    }
    if (s.loggedInAt != null && s.expiresIn != null) {
      const loggedInAt = Number(s.loggedInAt);
      const expiresIn = Number(s.expiresIn);
      if (!Number.isNaN(loggedInAt) && !Number.isNaN(expiresIn)) {
        return loggedInAt + expiresIn * 1000;
      }
    }
    return null;
  }

  /**
   * Access Token 존재 + 만료 전 여부.
   * exp 정보가 없으면 토큰 문자열만 있으면 유효로 보고, 서버 401에 맡긴다.
   */
  function isAccessTokenValid(session) {
    const s = session === undefined ? getJwtSession() : session;
    if (!s || !s.accessToken) {
      return false;
    }
    const expAt = accessTokenExpiresAt(s);
    if (expAt == null) {
      return true;
    }
    return Date.now() < (expAt - AUTH_SKEW_MS);
  }

  /** 로그인 세션(accessToken)이 있으면 Authorization Bearer 를 붙인다. */
  function authorizationHeaders() {
    const headers = {
      'Content-Type': 'application/json;charset=UTF-8',
      Accept: 'application/json'
    };
    const session = getJwtSession();
    if (session && session.accessToken && isAccessTokenValid(session)) {
      const type = session.tokenType || 'Bearer';
      headers.Authorization = type + ' ' + session.accessToken;
    }
    return headers;
  }

  function clearJwtSession() {
    try {
      sessionStorage.removeItem(JWT_SESSION_KEY);
    } catch (e) {
      /* ignore */
    }
  }

  function uiPath(path) {
    return typeof global.nsightUiUrl === 'function' ? global.nsightUiUrl(path) : path;
  }

  function rememberReturnHash() {
    try {
      var hash = '';
      try {
        if (global.self !== global.top) {
          hash = global.top.location.hash || '';
        } else {
          hash = location.hash || '';
        }
      } catch (e) {
        hash = location.hash || '';
      }
      if (hash && hash !== '#/jwt' && hash.indexOf('#/jwt') !== 0) {
        sessionStorage.setItem(JWT_RETURN_HASH_KEY, hash);
      }
    } catch (e) {
      /* ignore */
    }
  }

  /**
   * JWT 세션을 지우고 로그인 화면으로 이동한다.
   * 셸 iframe 안이면 부모 hash 를 #/jwt 로 바꾼다.
   */
  function redirectToLogin() {
    if (redirectingToLogin) {
      return;
    }
    redirectingToLogin = true;
    rememberReturnHash();
    clearJwtSession();

    try {
      if (global.self !== global.top) {
        global.top.location.hash = '#/jwt';
        return;
      }
    } catch (e) {
      /* cross-origin — fall through */
    }

    var path = location.pathname || '';
    if (path.endsWith('/index.html') || path === '/' || /\/ui\/?$/.test(path)) {
      location.hash = '#/jwt';
      return;
    }
    location.href = uiPath('/jwt/admin/login.html');
  }

  /** 유효한 Access Token 이 없으면 로그인으로 보내고 false. */
  function ensureAuthenticated() {
    if (isAccessTokenValid()) {
      return true;
    }
    redirectToLogin();
    return false;
  }

  /** HTTP 401 또는 인증 실패 코드면 로그인으로 보낸다. */
  function maybeRedirectOnUnauthorized(httpStatus, responseBody) {
    if (httpStatus === 401) {
      redirectToLogin();
      return true;
    }
    if (!responseBody || typeof responseBody !== 'string') {
      return false;
    }
    try {
      var parsed = JSON.parse(responseBody);
      var code = (parsed && parsed.result && parsed.result.stdErrCode)
          || (parsed && parsed.stdErrCode)
          || '';
      if (String(code).toUpperCase() === 'FW0401') {
        redirectToLogin();
        return true;
      }
    } catch (e) {
      /* ignore parse errors */
    }
    return false;
  }

  async function post(targetUrl, body, timeoutMs, transactionId) {
    if (!ensureAuthenticated()) {
      return {
        transactionId: transactionId || '',
        targetUrl: targetUrl || '',
        httpStatus: 401,
        elapsedMs: 0,
        responseBody: JSON.stringify({
          stdErrCode: 'FW0401',
          error: '로그인이 필요하거나 Access Token이 만료되었습니다.'
        })
      };
    }

    const started = performance.now();
    const controller = new AbortController();
    const ms = timeoutMs == null ? 0 : Number(timeoutMs);
    const timer = ms > 0 ? setTimeout(() => controller.abort(), ms) : null;

    let responseBody = '';
    let httpStatus = 0;
    try {
      const response = await fetch(targetUrl, {
        method: 'POST',
        headers: authorizationHeaders(),
        body: typeof body === 'string' ? (body || '{}') : JSON.stringify(body == null ? {} : body),
        signal: controller.signal
      });
      httpStatus = response.status;
      responseBody = await response.text();
    } catch (error) {
      const aborted = !!(error && error.name === 'AbortError');
      const message = aborted
          ? `요청 시간 초과 (${ms} ms)`
          : (error && error.message) || String(error);
      httpStatus = aborted ? 504 : 502;
      responseBody = JSON.stringify({
        stdErrCode: aborted ? 'UI_TIMEOUT' : 'UI_NETWORK',
        error: message,
        targetUrl,
        hint: 'pdmg-service가 기동 중인지, CORS·대상 URL이 맞는지 확인하세요.'
      });
    } finally {
      if (timer) {
        clearTimeout(timer);
      }
    }

    maybeRedirectOnUnauthorized(httpStatus, responseBody);

    return {
      transactionId: transactionId || '',
      targetUrl,
      httpStatus,
      elapsedMs: Math.round(performance.now() - started),
      responseBody
    };
  }

  async function postPath(baseUrl, path, body, timeoutMs, transactionId) {
    return post(joinUrl(baseUrl, path), body, timeoutMs, transactionId);
  }

  global.PdmgServiceClient = {
    joinUrl,
    post,
    postPath,
    getJwtSession,
    isAccessTokenValid,
    accessTokenExpiresAt,
    ensureAuthenticated,
    redirectToLogin,
    clearJwtSession
  };
})(window);
