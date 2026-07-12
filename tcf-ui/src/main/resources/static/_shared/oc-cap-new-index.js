(function () {
    const listEl = document.getElementById('scenarioList');
    const msgEl = document.getElementById('capNewMsg');
    const createBtn = document.getElementById('btnCreateScenario');

    function showMsg(text, ok) {
        if (!msgEl) return;
        msgEl.textContent = text;
        msgEl.className = 'oc-capnew-msg ' + (ok ? 'oc-capnew-msg--ok' : 'oc-capnew-msg--error');
        msgEl.hidden = !text;
    }

    function statusClass(status) {
        const key = (status || 'DRAFT').toLowerCase();
        return 'oc-capnew-status oc-capnew-status--' + key;
    }

    function renderList(items) {
        if (!listEl) return;
        if (!items || items.length === 0) {
            listEl.innerHTML = '<p>저장된 시나리오가 없습니다. 아래에서 신규 산정을 시작하세요.</p>';
            return;
        }
        listEl.innerHTML = items.map(item => `
            <article class="oc-capnew-card">
                <div style="display:flex;justify-content:space-between;gap:0.5rem;align-items:center;">
                    <h3>${escapeHtml(item.scenarioName || item.scenarioId)}</h3>
                    <span class="${statusClass(item.status)}">${escapeHtml(item.status || 'DRAFT')}</span>
                </div>
                <p>${escapeHtml(item.projectName || item.projectId || '')} · ${escapeHtml(item.targetEnv || '')}</p>
                <p>현재 STEP ${item.currentStep || 1} · 갱신 ${escapeHtml(item.updatedAt || '-')}</p>
                <p>
                  <a href="/oc/cap-new/wizard.html?id=${encodeURIComponent(item.scenarioId)}">Wizard 열기 →</a>
                  ${item.status === 'COMPLETED' || item.status === 'APPROVED'
                    ? ` · <a href="/oc/cap-new/compare.html?id=${encodeURIComponent(item.scenarioId)}">비교</a>`
                    : ''}
                  ${item.status === 'COMPLETED'
                    ? ` · <a href="/oc/cap-new/approved.html?id=${encodeURIComponent(item.scenarioId)}">확정</a>`
                    : ''}
                </p>
            </article>
        `).join('');
    }

    function escapeHtml(value) {
        return String(value ?? '')
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;');
    }

    async function loadList() {
        try {
            const items = await window.ocCapNewApi.listScenarios();
            renderList(items);
            showMsg('', true);
        } catch (e) {
            showMsg(e.message, false);
        }
    }

    async function createScenario() {
        try {
            const defaults = await window.ocCapNewApi.defaults();
            const step1 = defaults.step1 || {};
            const created = await window.ocCapNewApi.createScenario({
                projectId: step1.projectId,
                projectName: step1.projectName,
                scenarioName: step1.scenarioName,
                targetEnv: step1.targetEnv,
                baseDate: step1.baseDate,
                versionNo: step1.versionNo,
                author: step1.author,
                description: step1.description,
                purpose: step1.purpose
            });
            location.href = '/oc/cap-new/wizard.html?id=' + encodeURIComponent(created.scenarioId);
        } catch (e) {
            showMsg(e.message, false);
        }
    }

    if (createBtn) {
        createBtn.addEventListener('click', createScenario);
    }

    loadList();
})();
