#!/usr/bin/env python3
import argparse
import json
from pathlib import Path
from jsonschema import Draft202012Validator


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--model', required=True)
    ap.add_argument('--schema', required=True)
    ap.add_argument('--output', required=True)
    args = ap.parse_args()

    model = json.loads(Path(args.model).read_text(encoding='utf-8'))
    schema = json.loads(Path(args.schema).read_text(encoding='utf-8'))
    errors = sorted(Draft202012Validator(schema).iter_errors(model), key=lambda e: list(e.path))

    nodes = model.get('sourceExtracted', {}).get('nodes', [])
    edges = model.get('sourceExtracted', {}).get('edges', [])
    ids = [n.get('id') for n in nodes]
    id_set = set(ids)
    duplicate_ids = sorted({i for i in ids if i is not None and ids.count(i) > 1})
    dangling = [e for e in edges if e.get('from') not in id_set or e.get('to') not in id_set]
    declared_rel = set(model.get('relationTypes', []))
    undeclared_rel = sorted({e.get('relation') for e in edges if e.get('relation') not in declared_rel})
    declared_nodes = set(model.get('nodeTypes', []))
    undeclared_nodes = sorted({n.get('type') for n in nodes if n.get('type') not in declared_nodes})

    result = {
        'status': 'PASS' if not errors and not duplicate_ids and not dangling and not undeclared_rel and not undeclared_nodes else 'FAIL',
        'schemaErrors': [
            {'path': '/'.join(map(str, e.path)), 'message': e.message}
            for e in errors
        ],
        'semanticChecks': {
            'nodeCount': len(nodes),
            'edgeCount': len(edges),
            'duplicateNodeIds': duplicate_ids,
            'danglingEdgeCount': len(dangling),
            'danglingEdges': dangling[:50],
            'undeclaredRelationTypes': undeclared_rel,
            'undeclaredNodeTypes': undeclared_nodes
        }
    }
    Path(args.output).write_text(json.dumps(result, ensure_ascii=False, indent=2), encoding='utf-8')
    print(json.dumps(result, ensure_ascii=False, indent=2))
    raise SystemExit(0 if result['status'] == 'PASS' else 1)

if __name__ == '__main__':
    main()
