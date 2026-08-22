from __future__ import annotations

RUN_IDS = [
    'RUN-S1800','RUN-HIKARI','RUN-SLOWSQL','RUN-N1','RUN-SESSION',
    'RUN-CF','RUN-TRACE','RUN-ROLLING','RUN-JWT-ROTATE'
]


def expected_run_ids():
    return list(RUN_IDS)


def _contains(values, needle):
    return any(needle.lower() in str(v).lower() for v in values)


def validate_catalog(catalog):
    issues = []
    runs = catalog.get('runs', {}) if isinstance(catalog, dict) else {}
    for rid in RUN_IDS:
        if rid not in runs:
            issues.append(f'missing run: {rid}')
            continue
        r = runs[rid]
        if r.get('production_status') == 'PASS':
            issues.append(f'{rid}: production result must not be pre-approved')
        if not r.get('required_evidence'):
            issues.append(f'{rid}: required_evidence must not be empty')
        if not r.get('prerequisites'):
            issues.append(f'{rid}: prerequisites must not be empty')
        if not r.get('human_gates'):
            issues.append(f'{rid}: human_gates must not be empty')

    if 'RUN-SESSION' in runs and not _contains(runs['RUN-SESSION'].get('human_gates', []), 'Session ADR'):
        issues.append('RUN-SESSION: Session ADR human gate required')
    if 'RUN-CF' in runs and not _contains(runs['RUN-CF'].get('human_gates', []), 'RTO/RPO'):
        issues.append('RUN-CF: RTO/RPO human gate required')
    if 'RUN-JWT-ROTATE' in runs and not _contains(runs['RUN-JWT-ROTATE'].get('prerequisites', []), 'Key Provider'):
        issues.append('RUN-JWT-ROTATE: Key Provider prerequisite required')
    if 'RUN-TRACE' in runs:
        ev = runs['RUN-TRACE'].get('required_evidence', [])
        if not (_contains(ev, 'GUID') and _contains(ev, 'ServiceId')):
            issues.append('RUN-TRACE: GUID+ServiceId evidence required')
    return issues
