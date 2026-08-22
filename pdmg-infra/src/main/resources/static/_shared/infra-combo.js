/**
 * 코드성/마스터 FK 콤보 공통 로더.
 * HTML: <select data-combo="systems" data-empty="전체" data-selected="SYS-MKTG"></select>
 *       <select data-combo="code:HA_MODE" data-selected="ACTIVE_ACTIVE"></select>
 *       <select data-combo-type-src="targetTypeCd" data-empty="선택"></select>
 */
(function (global) {
  const esc = (v) => (global.InfraApi && InfraApi.escapeHtml)
    ? InfraApi.escapeHtml(v == null ? '' : String(v))
    : String(v == null ? '' : v).replace(/[&<>"']/g, (c) => ({
      '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    }[c]));

  const CODES = {
    HA_MODE: [
      ['ACTIVE_ACTIVE', 'Active-Active'],
      ['ACTIVE_STANDBY', 'Active-Standby'],
      ['N', 'None']
    ],
    DR_MODE: [
      ['HOT', 'Hot'], ['WARM', 'Warm'], ['COLD', 'Cold'], ['N', 'None']
    ],
    AUTH_METHOD: [
      ['JWT', 'JWT'], ['SSO', 'SSO'], ['CERT', 'Certificate'],
      ['BASIC', 'Basic'], ['NONE', 'None']
    ],
    NETWORK_ZONE: [
      ['INTERNET', 'Internet'], ['DMZ', 'DMZ'], ['WEB', 'WEB'], ['APP', 'APP'],
      ['DB', 'DB'], ['MGMT', 'MGMT'], ['BACKUP', 'BACKUP'], ['DR', 'DR']
    ],
    TARGET_TYPE: [
      ['GROUP', 'GROUP'], ['ASSET', 'ASSET'], ['SYSTEM', 'SYSTEM'],
      ['APP', 'APP'], ['DB', 'DB']
    ],
    ACTION: [
      ['CREATE', 'CREATE'], ['UPDATE', 'UPDATE'], ['DELETE', 'DELETE'],
      ['RETIRE', 'RETIRE'], ['VALIDATE', 'VALIDATE']
    ],
    CHECKLIST_CAT: [
      ['서버', '서버'], ['MW', 'MW'], ['App', 'App'], ['운영', '운영'], ['전환', '전환']
    ],
    TECH_ROLE: [
      ['WEB', 'WEB'], ['WAS', 'WAS'], ['DATABASE', 'Database'],
      ['BATCH', 'Batch'], ['CACHE', 'Cache']
    ],
    ASSET_KIND: [
      ['VM', '가상서버'], ['BARE_METAL', '물리서버'], ['SAAS', 'SaaS'], ['K8S', 'K8S']
    ],
    SERVICE_MODEL: [
      ['IAAS', 'IaaS'], ['PAAS', 'PaaS'], ['SAAS', 'SaaS'], ['BARE_METAL', 'Bare Metal']
    ],
    ENV: [['DEV', '개발'], ['STG', '스테이징'], ['PROD', '운영']],
    TIER: [['TIER0', 'Tier 0'], ['TIER1', 'Tier 1'], ['TIER2', 'Tier 2'], ['TIER3', 'Tier 3']],
    STATUS: [
      ['DISCOVERED', 'DISCOVERED'], ['VALIDATING', 'VALIDATING'],
      ['CONFIRMED', 'CONFIRMED'], ['RETIRED', 'RETIRED']
    ],
    APP_TYPE: [['ONLINE', 'ONLINE'], ['BATCH', 'BATCH'], ['UI', 'UI']],
    LANG: [['JAVA', 'JAVA'], ['NODE', 'NODE'], ['PYTHON', 'PYTHON'], ['SHELL', 'SHELL']],
    ENGINE: [['ORACLE', 'ORACLE'], ['POSTGRES', 'POSTGRES'], ['MYSQL', 'MYSQL'], ['MSSQL', 'MSSQL']],
    ENDPOINT_TYPE: [['IP', 'IP'], ['VIP', 'VIP'], ['DNS', 'DNS'], ['URL', 'URL']],
    PROTOCOL: [['TCP', 'TCP'], ['HTTP', 'HTTP'], ['HTTPS', 'HTTPS'], ['MQ', 'MQ'], ['FTP', 'FTP']],
    RELATION_TYPE: [
      ['CALLS', '호출'], ['USES_DB', 'DB 사용'], ['USES_CACHE', 'Cache 사용'],
      ['USES_MQ', 'MQ 사용'], ['FILE_XFER', '파일 전송'], ['REPLICATES', '복제']
    ],
    MAP_TYPE: [['GROUP', 'GROUP'], ['SERVER', 'SERVER'], ['DB', 'DB']],
    MAP_ROLE: [['PRIMARY', 'PRIMARY'], ['SECONDARY', 'SECONDARY'], ['ACTIVE', 'ACTIVE']]
  };

  const MASTERS = {
    systems: {
      service: 'ifina2100S0', scid: 'INF-210', listKey: 'ifina2100S0DTOSub0',
      valueKey: 'systemId',
      labelFn: (r) => `${r.systemId} - ${r.systemName || ''}`
    },
    apps: {
      service: 'ifina2200S0', scid: 'INF-220', listKey: 'ifina2200S0DTOSub0',
      valueKey: 'appId',
      labelFn: (r) => `${r.appId} - ${r.appName || ''}`
    },
    assets: {
      service: 'ifina3100S0', scid: 'INF-320', listKey: 'ifina3100S0DTOSub0',
      valueKey: 'assetId',
      labelFn: (r) => `${r.assetId} - ${r.assetName || ''}`
    },
    groups: {
      service: 'ifina3110S0', scid: 'INF-310', listKey: 'ifina3110S0DTOSub0',
      valueKey: 'groupId',
      labelFn: (r) => `${r.groupId} - ${r.groupName || ''}`
    },
    dbs: {
      service: 'ifina4200S0', scid: 'INF-420', listKey: 'ifina4200S0DTOSub0',
      valueKey: 'dbId',
      labelFn: (r) => `${r.dbId} - ${r.dbName || ''}`
    },
    orgs: {
      service: 'ifina1500S0', scid: 'INF-150', listKey: 'ifina1500S0DTOSub0',
      valueKey: 'orgId',
      labelFn: (r) => `${r.orgId} - ${r.orgName || ''}`,
      dto: { entityType: 'ORG', pageNo: 1, pageSize: 200, activeYn: 'Y' }
    },
    orgNames: {
      service: 'ifina1500S0', scid: 'INF-150', listKey: 'ifina1500S0DTOSub0',
      valueKey: 'orgName',
      labelFn: (r) => r.orgName || r.orgId || '',
      dto: { entityType: 'ORG', pageNo: 1, pageSize: 200, activeYn: 'Y' }
    },
    gates: {
      service: 'ifina1400S0', scid: 'INF-140', listKey: 'rows',
      valueKey: 'gateId',
      labelFn: (r) => `${r.gateId} - ${r.nameKo || ''}`,
      dto: { pageNo: 1, pageSize: 200, activeYn: 'Y' }
    }
  };

  const TYPE_MASTER = {
    GROUP: 'groups', ASSET: 'assets', SYSTEM: 'systems',
    APP: 'apps', DB: 'dbs', SERVER: 'assets'
  };

  const cache = Object.create(null);

  function optionsFromCodes(codeSetId) {
    return (CODES[codeSetId] || []).map(([value, label]) => ({ value, label }));
  }

  function fillOptions(sel, items, opts) {
    if (!sel) return;
    const empty = opts && opts.empty != null ? opts.empty : null;
    const selected = opts && opts.selected != null ? String(opts.selected) : (sel.getAttribute('data-selected') || sel.value || '');
    const allowEmpty = empty != null;
    const html = [];
    if (allowEmpty) {
      html.push(`<option value="">${esc(empty)}</option>`);
    }
    (items || []).forEach((it) => {
      const v = it.value == null ? '' : String(it.value);
      if (!v && allowEmpty) return;
      html.push(`<option value="${esc(v)}">${esc(it.label || v)}</option>`);
    });
    sel.innerHTML = html.join('');
    if (selected) ensureValue(sel, selected);
    else if (allowEmpty) sel.value = '';
  }

  function ensureValue(sel, value) {
    if (!sel || value == null || value === '') {
      if (sel) sel.value = '';
      return;
    }
    const v = String(value);
    const exists = Array.from(sel.options).some((o) => o.value === v);
    if (!exists) {
      const opt = document.createElement('option');
      opt.value = v;
      opt.textContent = v;
      sel.appendChild(opt);
    }
    sel.value = v;
  }

  async function loadMaster(kind) {
    const meta = MASTERS[kind];
    if (!meta) return [];
    if (cache[kind]) return cache[kind];
    if (!global.InfraApi || typeof InfraApi.postService !== 'function') return [];
    const dto = Object.assign({ pageNo: 1, pageSize: 200 }, meta.dto || {});
    const res = await InfraApi.postService(meta.service, dto, meta.scid);
    const body = (res && res.dto) || {};
    const rows = Array.isArray(body[meta.listKey]) ? body[meta.listKey] : [];
    let items = rows.map((r) => ({
      value: r[meta.valueKey],
      label: meta.labelFn(r)
    })).filter((it) => it.value);

    // 시스템 담당조직(ownerOrg) 자유값은 ORG 마스터와 다를 수 있어 병합
    if (kind === 'orgNames') {
      try {
        const sysRes = await InfraApi.postService('ifina2100S0', { pageNo: 1, pageSize: 200 }, 'INF-210');
        const sysRows = Array.isArray((sysRes.dto || {}).ifina2100S0DTOSub0)
          ? sysRes.dto.ifina2100S0DTOSub0 : [];
        const seen = new Set(items.map((i) => i.value));
        sysRows.forEach((r) => {
          const name = (r.ownerOrg || '').trim();
          if (name && !seen.has(name)) {
            seen.add(name);
            items.push({ value: name, label: name });
          }
        });
      } catch (_) { /* ignore */ }
    }

    cache[kind] = items;
    return items;
  }

  async function fillCode(sel, codeSetId, opts) {
    fillOptions(sel, optionsFromCodes(codeSetId), opts);
  }

  async function fillMaster(sel, kind, opts) {
    const items = await loadMaster(kind);
    fillOptions(sel, items, opts);
  }

  async function fillByType(sel, typeCd, opts) {
    const kind = TYPE_MASTER[(typeCd || '').toUpperCase()];
    if (!kind) {
      fillOptions(sel, [], Object.assign({ empty: (opts && opts.empty) || '선택' }, opts));
      return;
    }
    await fillMaster(sel, kind, opts);
  }

  function bindTypeLinked(sel) {
    const srcId = sel.getAttribute('data-combo-type-src');
    if (!srcId) return;
    const src = document.getElementById(srcId);
    if (!src) return;
    const empty = sel.getAttribute('data-empty') || '선택';
    const preferred = sel.getAttribute('data-selected') || '';
    const refresh = () => fillByType(sel, src.value, { empty, selected: preferred || sel.value });
    src.addEventListener('change', () => {
      sel.setAttribute('data-selected', '');
      fillByType(sel, src.value, { empty, selected: '' });
    });
    return refresh();
  }

  let bootPromise = null;

  async function boot(root) {
    if (bootPromise && !root) return bootPromise;
    const run = async () => {
      const scope = root || document;
      const tasks = [];
      scope.querySelectorAll('select[data-combo]').forEach((sel) => {
        if (sel.dataset.comboReady === '1' && !root) return;
        const kind = (sel.getAttribute('data-combo') || '').trim();
        const emptyAttr = sel.getAttribute('data-empty');
        const empty = emptyAttr == null ? null : emptyAttr;
        const selected = sel.getAttribute('data-selected') || '';
        const opts = { empty, selected };
        const done = (p) => Promise.resolve(p).then(() => { sel.dataset.comboReady = '1'; });
        if (kind.startsWith('code:')) {
          tasks.push(done(fillCode(sel, kind.slice(5), opts)));
        } else if (MASTERS[kind]) {
          tasks.push(done(fillMaster(sel, kind, opts)));
        }
      });
      scope.querySelectorAll('select[data-combo-type-src]').forEach((sel) => {
        if (sel.dataset.comboReady === '1' && !root) return;
        tasks.push(Promise.resolve(bindTypeLinked(sel)).then(() => { sel.dataset.comboReady = '1'; }));
      });
      await Promise.all(tasks);
    };
    if (!root) {
      bootPromise = run();
      return bootPromise;
    }
    return run();
  }

  global.InfraCombo = {
    CODES,
    MASTERS,
    boot,
    fillCode,
    fillMaster,
    fillByType,
    fillOptions,
    ensureValue,
    loadMaster,
    set: ensureValue
  };

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
      boot().catch(console.error);
    });
  } else {
    boot().catch(console.error);
  }
})(window);
