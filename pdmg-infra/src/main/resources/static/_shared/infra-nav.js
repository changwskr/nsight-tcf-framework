/**
 * 인프라 아키텍처 관리 — 공통 좌측 메뉴
 * 요구사항 INF 화면번호 순으로 그룹화. 활성 항목은 URL path로 자동 표시.
 */
(function (global) {
  const GROUPS = [
    {
      label: '00. 통합 현황',
      items: [
        { id: 'HOME', href: '/index.html', text: '홈' },
        { id: 'INF-010', href: '/infra/dashboard/index.html', text: 'INF-010 통합 대시보드' },
        { id: 'INF-020', href: '/infra/risks/index.html', text: 'INF-020 리스크·Gate' },
        { id: 'INF-930', href: '/infra/survey-gaps/index.html', text: 'INF-930 조사 미완료' }
      ]
    },
    {
      label: '10. 기준정보',
      items: [
        { id: 'INF-110', href: '/infra/codes/index.html', text: 'INF-110 분류코드' },
        { id: 'INF-120', href: '/infra/surveys/index.html', text: 'INF-120 조사 템플릿' },
        { id: 'INF-130', href: '/infra/checklist-master/index.html', text: 'INF-130 Checklist 마스터' },
        { id: 'INF-140', href: '/infra/gate-defs/index.html', text: 'INF-140 Gate 정의' },
        { id: 'INF-150', href: '/infra/orgs/index.html', text: 'INF-150 조직·담당자' },
        { id: 'INF-160', href: '/infra/audit/index.html', text: 'INF-160 변경이력' }
      ]
    },
    {
      label: '20. 업무·Application',
      items: [
        { id: 'INF-210', href: '/infra/systems/index.html', text: 'INF-210 업무 시스템' },
        { id: 'INF-220', href: '/infra/apps/index.html', text: 'INF-220 Application' },
        { id: 'INF-230', href: '/infra/app-maps/index.html', text: 'INF-230 App 연결' },
        { id: 'INF-240', href: '/infra/mapping/index.html', text: 'INF-240 매핑 위저드' }
      ]
    },
    {
      label: '30. 서버군·자산',
      items: [
        { id: 'INF-310', href: '/infra/groups/index.html', text: 'INF-310 서버군' },
        { id: 'INF-320', href: '/infra/server-assets/index.html', text: 'INF-320 정규 자산' },
        { id: 'INF-320P', href: '/infra/assets/index.html', text: 'INF-320 파일럿 자산' },
        { id: 'INF-340', href: '/infra/bulk/index.html', text: 'INF-340 일괄등록' }
      ]
    },
    {
      label: '40. MW·DB·EOL',
      items: [
        { id: 'INF-410', href: '/infra/middleware/index.html', text: 'INF-410 Middleware' },
        { id: 'INF-420', href: '/infra/db/index.html', text: 'INF-420 DB Instance' },
        { id: 'INF-430', href: '/infra/eol/index.html', text: 'INF-430 EOL/EOS' }
      ]
    },
    {
      label: '50. 네트워크·연동',
      items: [
        { id: 'INF-510', href: '/infra/network/index.html', text: 'INF-510 Network' },
        { id: 'INF-520', href: '/infra/interfaces/index.html', text: 'INF-520 Interface' },
        { id: 'INF-530', href: '/infra/deps/index.html', text: 'INF-530 의존맵' }
      ]
    },
    {
      label: '60. 운영품질',
      items: [
        { id: 'INF-610', href: '/infra/ha/index.html', text: 'INF-610 HA/DR' },
        { id: 'INF-620', href: '/infra/capacity/index.html', text: 'INF-620 용량' },
        { id: 'INF-630', href: '/infra/security/index.html', text: 'INF-630 보안' },
        { id: 'INF-640', href: '/infra/capacity-compare/index.html', text: 'INF-640 용량 비교' }
      ]
    },
    {
      label: '70. 라이선스·TCO',
      items: [
        { id: 'INF-710', href: '/infra/licenses/index.html', text: 'INF-710 라이선스' },
        { id: 'INF-720', href: '/infra/license-alloc/index.html', text: 'INF-720 할당' },
        { id: 'INF-730', href: '/infra/tco/index.html', text: 'INF-730 TCO' }
      ]
    },
    {
      label: '80. 전환',
      items: [
        { id: 'INF-810', href: '/infra/migration/index.html', text: 'INF-810 7R 계획' },
        { id: 'INF-820', href: '/infra/waves/index.html', text: 'INF-820 Wave' },
        { id: 'INF-830', href: '/infra/asis-tobe/index.html', text: 'INF-830 AS-IS→TO-BE' }
      ]
    },
    {
      label: '90. Gate·Checklist·제안',
      items: [
        { id: 'INF-910', href: '/infra/checklist/index.html', text: 'INF-910 Checklist' },
        { id: 'INF-920', href: '/infra/gates/index.html', text: 'INF-920 Architecture Gate' },
        { id: 'INF-940', href: '/infra/proposal/index.html', text: 'INF-940 제안서' }
      ]
    }
  ];

  function normalizePath(p) {
    if (!p) return '/';
    let s = String(p).split('?')[0].split('#')[0];
    if (s.length > 1 && s.endsWith('/')) s = s.slice(0, -1);
    if (s.endsWith('/index.html')) s = s.slice(0, -11) || '/';
    if (s === '/index.html') s = '/';
    return s.toLowerCase();
  }

  function resolveActiveId() {
    const forced = document.body && document.body.getAttribute('data-nav-active');
    if (forced) return forced.toUpperCase();
    const path = normalizePath(location.pathname);
    if (path === '/' || path === '' || path === '/index') return 'HOME';

    let best = null;
    GROUPS.forEach((g) => {
      g.items.forEach((it) => {
        const hp = normalizePath(it.href);
        if (hp === '/' ) return;
        if (path === hp) {
          best = { id: it.id, hrefLen: hp.length };
        }
      });
    });
    return best ? best.id : null;
  }

  function render(navEl, activeId) {
    if (!navEl) return;
    const active = (activeId || resolveActiveId() || '').toUpperCase();
    navEl.innerHTML = GROUPS.map((g) => {
      const links = g.items.map((it) => {
        const isActive = it.id === active;
        const cls = 'infra-menu-link' + (isActive ? ' infra-menu-link--active' : '');
        return `<a class="${cls}" href="${it.href}" data-nav-id="${it.id}">${it.text}</a>`;
      }).join('');
      return `<div class="infra-menu-group"><div class="infra-menu-group__label">${g.label}</div>${links}</div>`;
    }).join('');

    const activeLink = navEl.querySelector('.infra-menu-link--active');
    if (activeLink && typeof activeLink.scrollIntoView === 'function') {
      try { activeLink.scrollIntoView({ block: 'nearest' }); } catch (_) { /* ignore */ }
    }
  }

  function boot() {
    const nav = document.getElementById('infraNav')
      || document.querySelector('.infra-sidebar nav');
    if (!nav) return;
    if (!nav.id) nav.id = 'infraNav';
    nav.classList.add('infra-sidebar__nav');
    render(nav, resolveActiveId());
  }

  global.InfraNav = { GROUPS, render, boot, resolveActiveId };

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', boot);
  } else {
    boot();
  }
})(window);
