/**
 * pdmg-ui context path helper (tcf-ui ui-context.js 정렬).
 * bootRun(루트)에서는 no-op, /ui 컨텍스트 배포 시 접두사를 붙인다.
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
      return uiContext || '/';
    }
    const normalized = path.startsWith('/') ? path : '/' + path;
    if (uiContext && (normalized === uiContext || normalized.startsWith(uiContext + '/'))) {
      return normalized;
    }
    return uiContext + normalized;
  };
})();
