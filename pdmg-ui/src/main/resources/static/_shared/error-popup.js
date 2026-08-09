/**
 * pdmg-ui 공통 에러 팝업.
 * 서비스 오류 DTO · 중계 오류 · HTTP 오류를 모달로 보여주며,
 * stackTrace 및 응답 전문(전체 로그)을 그대로 표시한다.
 */
(function (global) {
  const MODAL_ID = 'pdmgErrorModal';

  function escapeHtml(value) {
    return String(value == null ? '' : value)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#39;');
  }

  function ensureModal() {
    let modal = document.getElementById(MODAL_ID);
    if (modal) {
      return modal;
    }

    modal = document.createElement('div');
    modal.id = MODAL_ID;
    modal.className = 'modal error-modal';
    modal.hidden = true;
    modal.innerHTML = `
      <div class="modal-backdrop" data-close="true"></div>
      <div class="modal-card error-modal-card" role="alertdialog" aria-modal="true" aria-labelledby="pdmgErrorTitle">
        <div class="panel-head">
          <h2 id="pdmgErrorTitle">오류</h2>
          <button class="btn-secondary" type="button" data-close="true">닫기</button>
        </div>
        <div class="modal-body">
          <p class="error-message" id="pdmgErrorMessage"></p>
          <dl class="detail-grid" id="pdmgErrorDetails" hidden></dl>
          <div class="error-log-block" id="pdmgErrorLogBlock" hidden>
            <div class="error-log-head">
              <span id="pdmgErrorLogLabel">전체 로그</span>
              <button class="btn-secondary btn-tiny" type="button" id="pdmgErrorLogCopy">복사</button>
            </div>
            <pre class="error-log mono" id="pdmgErrorLog"></pre>
          </div>
        </div>
        <div class="modal-actions">
          <button class="btn-primary" type="button" data-close="true">확인</button>
        </div>
      </div>
    `;
    document.body.appendChild(modal);

    modal.addEventListener('click', (event) => {
      if (event.target && event.target.getAttribute('data-close') === 'true') {
        hide();
      }
    });
    document.addEventListener('keydown', (event) => {
      if (event.key === 'Escape' && !modal.hidden) {
        hide();
      }
    });

    const copyBtn = modal.querySelector('#pdmgErrorLogCopy');
    copyBtn.addEventListener('click', async () => {
      const text = modal.querySelector('#pdmgErrorLog').textContent || '';
      try {
        await navigator.clipboard.writeText(text);
        copyBtn.textContent = '복사됨';
        setTimeout(() => { copyBtn.textContent = '복사'; }, 1200);
      } catch (_e) {
        copyBtn.textContent = '실패';
        setTimeout(() => { copyBtn.textContent = '복사'; }, 1200);
      }
    });
    return modal;
  }

  function hide() {
    const modal = document.getElementById(MODAL_ID);
    if (modal) {
      modal.hidden = true;
    }
  }

  function formatStackTrace(stackTrace) {
    if (stackTrace == null) {
      return '';
    }
    if (Array.isArray(stackTrace)) {
      return stackTrace.filter((line) => line != null && String(line).trim() !== '').join('\n');
    }
    return String(stackTrace);
  }

  function prettyJson(value) {
    if (value == null) {
      return '';
    }
    if (typeof value === 'string') {
      try {
        return JSON.stringify(JSON.parse(value), null, 2);
      } catch (_e) {
        return value;
      }
    }
    try {
      return JSON.stringify(value, null, 2);
    } catch (_e) {
      return String(value);
    }
  }

  /**
   * 팝업에 표시할 전체 로그 텍스트를 만든다.
   * 메시지 · 상세 · stackTrace · 원본 응답을 빠짐없이 이어 붙인다.
   */
  function buildFullLog(opts) {
    const lines = [];
    const title = opts.title || '오류';
    const message = opts.message || '';
    lines.push(`[${title}]`);
    if (message) {
      lines.push(message);
    }

    const details = (opts.details || []).filter(([, value]) => value != null && String(value).trim() !== '');
    if (details.length) {
      lines.push('');
      lines.push('---- 상세 ----');
      details.forEach(([label, value]) => {
        lines.push(`${label}: ${value}`);
      });
    }

    const stack = formatStackTrace(opts.stackTrace);
    if (stack) {
      lines.push('');
      lines.push('---- StackTrace ----');
      lines.push(stack);
    }

    if (opts.rawLog) {
      lines.push('');
      lines.push('---- 전체 응답 ----');
      lines.push(typeof opts.rawLog === 'string' ? opts.rawLog : prettyJson(opts.rawLog));
    }

    return lines.join('\n').trim();
  }

  /**
   * @param {{
   *   title?: string,
   *   message?: string,
   *   details?: Array<[string, string|number|null|undefined]>,
   *   stackTrace?: string[]|string|null,
   *   rawLog?: string|object|null,
   *   logLabel?: string
   * }} options
   */
  function show(options) {
    const opts = options || {};
    const modal = ensureModal();
    const titleEl = modal.querySelector('#pdmgErrorTitle');
    const messageEl = modal.querySelector('#pdmgErrorMessage');
    const detailsEl = modal.querySelector('#pdmgErrorDetails');
    const logBlock = modal.querySelector('#pdmgErrorLogBlock');
    const logEl = modal.querySelector('#pdmgErrorLog');
    const logLabel = modal.querySelector('#pdmgErrorLogLabel');

    titleEl.textContent = opts.title || '오류';
    messageEl.textContent = opts.message || '알 수 없는 오류가 발생했습니다.';

    const details = (opts.details || []).filter(([, value]) => value != null && String(value).trim() !== '');
    if (!details.length) {
      detailsEl.hidden = true;
      detailsEl.innerHTML = '';
    } else {
      detailsEl.hidden = false;
      detailsEl.innerHTML = details.map(([label, value]) => `
        <div>
          <dt>${escapeHtml(label)}</dt>
          <dd class="mono wrap">${escapeHtml(value)}</dd>
        </div>
      `).join('');
    }

    const fullLog = buildFullLog(opts);
    if (fullLog) {
      logBlock.hidden = false;
      logLabel.textContent = opts.logLabel || '전체 로그';
      logEl.textContent = fullLog;
    } else {
      logBlock.hidden = true;
      logEl.textContent = '';
    }

    modal.hidden = false;
  }

  function showSimple(message, title) {
    const text = String(message == null ? '' : message);
    show({
      title: title || '오류',
      message: text,
      rawLog: text
    });
  }

  /** 응답 JSON에서 업무/시스템 오류 본문을 꺼낸다. */
  function errorPayload(parsed) {
    if (!parsed || typeof parsed !== 'object') {
      return null;
    }
    if (parsed.dto && typeof parsed.dto === 'object'
        && (parsed.dto.stdErrMsgCntn || parsed.dto.stdErrCode || parsed.dto.errType
            || parsed.dto.stackTrace)) {
      return parsed.dto;
    }
    if (parsed.stdErrMsgCntn || parsed.stdErrCode || parsed.errType || parsed.stackTrace) {
      return parsed;
    }
    return null;
  }

  /**
   * 중계/서비스 응답을 해석해 오류면 팝업을 띄운다.
   * @param {object|null} parsed 파싱된 응답
   * @param {number|null} httpStatus HTTP 상태
   * @param {string} [fallbackMessage]
   * @param {string|object|null} [rawBody] 파싱 실패 시에도 보여줄 원문
   * @returns {boolean} 오류 팝업을 띄웠으면 true
   */
  function showFromResponse(parsed, httpStatus, fallbackMessage, rawBody) {
    const err = errorPayload(parsed);
    const relayError = parsed && (parsed.error || parsed.message);
    const failed = (httpStatus != null && (httpStatus < 200 || httpStatus >= 300))
        || !!err
        || !!relayError;

    if (!failed) {
      return false;
    }

    const fullRaw = parsed != null ? parsed : (rawBody != null ? rawBody : null);

    if (err) {
      const message = err.stdErrMsgCntn
          || err.addMsgContents
          || err.message
          || fallbackMessage
          || '서비스 오류가 발생했습니다.';
      show({
        title: err.stdErrCode ? `오류 (${err.stdErrCode})` : (err.errType ? `오류 (${err.errType})` : '서비스 오류'),
        message,
        details: [
          ['오류코드', err.stdErrCode],
          ['오류유형', err.errType],
          ['추가메시지', err.addMsgContents],
          ['클래스', err.errClassName],
          ['메서드', err.errMethodName],
          ['파일', err.errFileName],
          ['라인', err.errLineNo],
          ['HTTP', httpStatus]
        ],
        stackTrace: err.stackTrace,
        rawLog: fullRaw,
        logLabel: '전체 로그 (응답 전문 + StackTrace)'
      });
      return true;
    }

    const message = relayError
        || fallbackMessage
        || (httpStatus != null ? `HTTP ${httpStatus} 응답` : '요청 처리 중 오류가 발생했습니다.');
    show({
      title: '요청 오류',
      message: parsed && parsed.hint ? `${message}\n${parsed.hint}` : message,
      details: [
        ['HTTP', httpStatus],
        ['대상 URL', parsed && parsed.targetUrl]
      ],
      rawLog: fullRaw != null ? fullRaw : message,
      logLabel: '전체 로그'
    });
    return true;
  }

  global.PdmgErrorPopup = {
    show,
    showSimple,
    showFromResponse,
    hide,
    errorPayload
  };
})(window);
