/**
 * tcf-ui Tomcat (/ui) 배포 시 절대 경로 API·리소스에 context 접두사를 붙입니다.
 * bootRun(루트 context)에서는 no-op 입니다.
 */
(function () {
  if (window.__NSIGHT_UI_CONTEXT_INIT__) {
    return;
  }
  window.__NSIGHT_UI_CONTEXT_INIT__ = true;

  const uiContext = location.pathname.startsWith('/ui/') || location.pathname === '/ui' ? '/ui' : '';
  window.__NSIGHT_UI_CTX__ = uiContext;

  window.nsightUiUrl = function nsightUiUrl(path) {
    if (!path) {
      return uiContext;
    }
    const normalized = path.startsWith('/') ? path : '/' + path;
    if (uiContext && (normalized === uiContext || normalized.startsWith(uiContext + '/'))) {
      return normalized;
    }
    return uiContext + normalized;
  };

  if (!uiContext) {
    return;
  }

  const originalFetch = window.fetch.bind(window);
  window.fetch = function patchedFetch(input, init) {
    if (typeof input === 'string' && input.startsWith('/') && !input.startsWith('//')) {
      if (input !== uiContext && !input.startsWith(uiContext + '/')) {
        input = uiContext + input;
      }
    }
    return originalFetch(input, init);
  };
})();
