#!/usr/bin/env python3
from __future__ import annotations

import json
import py_compile
import re
import sys
from pathlib import Path

root = Path(sys.argv[1]).resolve() if len(sys.argv) > 1 else Path(__file__).resolve().parents[1]
errors = []
refs = ['pdmg-ui','pdmg-fw','pdmg-service','pdmg-jwt']
required = [
    '00-REFERENCE-BASELINE/REFERENCE-PROJECTS.md',
    '04-STAGE-PROMPTS/REFERENCE','04-STAGE-PROMPTS/TARGET',
    '05-GATE/REFERENCE','05-GATE/TARGET',
    'docs/superpowers/specs/2026-08-17-pdmg-reference-baseline-orchestration-design.md',
    'docs/superpowers/plans/2026-08-17-pdmg-complete-execution-system.md',
    'QUICK-START.md','EXECUTION-GUIDE.md',
    'execution/pdmg_orchestrator.py','execution/engine/scanner.py','execution/engine/gates.py',
    'execution/config/reference-projects.json','execution/config/gates/reference-gates.json','execution/config/gates/target-gates.json',
    'execution/bin/pdmg-orchestrator','execution/bin/pdmg-orchestrator.bat','execution/bin/pdmg-orchestrator.ps1',
]
for r in required:
    if not (root/r).exists(): errors.append('missing '+r)

refdoc=(root/'00-REFERENCE-BASELINE/REFERENCE-PROJECTS.md').read_text(encoding='utf-8')
for x in refs:
    if x not in refdoc: errors.append('reference module missing '+x)

# Markdown integrity
md=list(root.rglob('*.md'))
empty=[]; repl=[]; fences=[]; broken=[]
for p in md:
    t=p.read_text(encoding='utf-8',errors='replace')
    if not t.strip(): empty.append(str(p.relative_to(root)))
    if '\ufffd' in t: repl.append(str(p.relative_to(root)))
    if len(re.findall(r'^```',t,flags=re.M))%2: fences.append(str(p.relative_to(root)))
    for m in re.finditer(r'\[[^\]]+\]\(([^)]+\.md(?:#[^)]+)?)\)',t):
        target=m.group(1).split('#')[0]
        if '://' in target or target.startswith('/') or target.startswith('sandbox:'): continue
        if not (p.parent/target).resolve().exists(): broken.append(f'{p.relative_to(root)} -> {target}')
if empty: errors.append('empty markdown '+str(empty))
if repl: errors.append('replacement chars '+str(repl))
if fences: errors.append('unbalanced fences '+str(fences))
if broken: errors.append('broken links '+str(broken))

# Human-readable gate/stage contracts
checks = {
    'reference_stages': len(list((root/'04-STAGE-PROMPTS/REFERENCE').glob('*.md'))),
    'target_stages': len(list((root/'04-STAGE-PROMPTS/TARGET').glob('*.md'))),
    'reference_gates': len(list((root/'05-GATE/REFERENCE').glob('*.md'))),
    'target_gates': len(list((root/'05-GATE/TARGET').glob('*.md'))),
}
for k,v in checks.items():
    if v != 10: errors.append(f'{k} count != 10: {v}')

# Executable gate contracts
for filename, expected in [('reference-gates.json', {'RG00','RG10','RG20','RG30','RG40','RG50','RG60','RG70','RG80','RHG90'}), ('target-gates.json', {'G00','G10','G20','G30','G40','G50','G60','G70','G80','HG90'})]:
    p=root/'execution/config/gates'/filename
    try:
        data=json.loads(p.read_text(encoding='utf-8'))
        actual=set(data.get('gates',{}))
        if actual != expected: errors.append(f'{filename} gate ids mismatch: {sorted(actual)}')
        for gid,g in data.get('gates',{}).items():
            for rule in g.get('rules',[]):
                if 'evaluator' not in rule: errors.append(f'{filename}:{gid}:{rule.get("ruleId")} missing evaluator')
                if 'hard' not in rule: errors.append(f'{filename}:{gid}:{rule.get("ruleId")} missing hard')
    except Exception as e:
        errors.append(f'invalid gate config {filename}: {e}')

# JSON syntax and schemas present
json_files=list((root/'execution').rglob('*.json'))
for p in json_files:
    try: json.loads(p.read_text(encoding='utf-8'))
    except Exception as e: errors.append(f'invalid json {p.relative_to(root)}: {e}')

# Python syntax
py_files=list((root/'execution').rglob('*.py')) + [root/'tools/validate_orchestration.py']
for p in py_files:
    try: py_compile.compile(str(p), doraise=True)
    except Exception as e: errors.append(f'python compile failed {p.relative_to(root)}: {e}')

# Final hard blocker requirements must exist in both final gates
for filename, gate_id in [('reference-gates.json','RHG90'),('target-gates.json','HG90')]:
    data=json.loads((root/'execution/config/gates'/filename).read_text())
    evals=[r['evaluator'] for r in data['gates'][gate_id]['rules'] if r.get('hard')]
    for required_eval in ['runtime_present','artifact_hash_present','critical_open_zero','approval_register_valid','json_fields_equal']:
        if required_eval not in evals: errors.append(f'{gate_id} missing hard evaluator {required_eval}')

print('markdown_files=',len(md))
for k,v in checks.items(): print(f'{k}=',v)
print('execution_python_files=',len(py_files))
print('execution_json_files=',len(json_files))
print('schemas=',len(list((root/'execution/schemas').glob('*.json'))))
if errors:
    print('RESULT=FAIL')
    for e in errors: print(e)
    sys.exit(1)
print('RESULT=PASS')
