#!/usr/bin/env python3
"""Validate the NSIGHT Auto Harness design package without network access."""
from __future__ import annotations

import json
import re
import sys
import warnings
from collections import Counter
from pathlib import Path
from typing import Any, Iterable

warnings.filterwarnings("ignore", category=DeprecationWarning)

import yaml
from jsonschema import Draft202012Validator, FormatChecker, RefResolver

ROOT = Path(__file__).resolve().parents[1]
SCHEMA_DIR = ROOT / "schemas"
WORKFLOW_DIR = ROOT / "workflows"
GATE_DIR = ROOT / "gate-rules"
API_FILE = ROOT / "api" / "openapi.yaml"
DDL_DIR = ROOT / "database" / "oracle"

ERRORS: list[str] = []
WARNINGS: list[str] = []
STATS: dict[str, int] = {}


def err(message: str) -> None:
    ERRORS.append(message)


def warn(message: str) -> None:
    WARNINGS.append(message)


def load_json(path: Path) -> dict[str, Any]:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:  # noqa: BLE001
        err(f"JSON parse failed: {path.relative_to(ROOT)}: {exc}")
        return {}


def load_yaml(path: Path) -> dict[str, Any]:
    try:
        data = yaml.safe_load(path.read_text(encoding="utf-8"))
        if not isinstance(data, dict):
            raise ValueError("top-level YAML must be an object")
        return data
    except Exception as exc:  # noqa: BLE001
        err(f"YAML parse failed: {path.relative_to(ROOT)}: {exc}")
        return {}


def validate_instance(instance: Any, schema: dict[str, Any], source: Path, *, resolver: RefResolver | None = None) -> None:
    validator = Draft202012Validator(schema, resolver=resolver, format_checker=FormatChecker())
    for violation in sorted(validator.iter_errors(instance), key=lambda e: list(e.absolute_path)):
        location = "/".join(str(v) for v in violation.absolute_path) or "$"
        err(f"Schema validation failed: {source.relative_to(ROOT)}:{location}: {violation.message}")


def validate_json_schemas() -> dict[str, dict[str, Any]]:
    schemas: dict[str, dict[str, Any]] = {}
    ids: Counter[str] = Counter()
    for path in sorted(SCHEMA_DIR.glob("*.json")):
        schema = load_json(path)
        if not schema:
            continue
        try:
            Draft202012Validator.check_schema(schema)
        except Exception as exc:  # noqa: BLE001
            err(f"Invalid JSON Schema: {path.relative_to(ROOT)}: {exc}")
        schema_id = schema.get("$id")
        if not schema_id:
            err(f"Schema has no $id: {path.relative_to(ROOT)}")
        else:
            ids[schema_id] += 1
        schemas[path.name] = schema
    for schema_id, count in ids.items():
        if count > 1:
            err(f"Duplicate schema $id: {schema_id}")
    STATS["json_schemas"] = len(schemas)
    return schemas


def validate_template_manifest(schemas: dict[str, dict[str, Any]]) -> None:
    path = ROOT / "workspace" / "templates" / "input-manifest.json"
    instance = load_json(path)
    schema = schemas.get("artifact-manifest.schema.json")
    common = schemas.get("common-definitions.schema.json")
    if not schema or not common or not instance:
        return
    schema_uri = (SCHEMA_DIR / "artifact-manifest.schema.json").as_uri()
    common_uri = (SCHEMA_DIR / "common-definitions.schema.json").as_uri()
    store = {
        schema.get("$id"): schema,
        common.get("$id"): common,
        common_uri: common,
        schema_uri: schema,
    }
    resolver = RefResolver(base_uri=schema_uri, referrer=schema, store=store)
    validate_instance(instance, schema, path, resolver=resolver)


def detect_cycle(stage_dependencies: dict[str, list[str]], source: Path) -> None:
    visiting: set[str] = set()
    visited: set[str] = set()

    def visit(node: str) -> None:
        if node in visiting:
            err(f"Workflow cycle detected in {source.relative_to(ROOT)} at {node}")
            return
        if node in visited:
            return
        visiting.add(node)
        for dep in stage_dependencies.get(node, []):
            visit(dep)
        visiting.remove(node)
        visited.add(node)

    for node in stage_dependencies:
        visit(node)


def validate_workflows(schemas: dict[str, dict[str, Any]], gate_ids: set[str]) -> set[str]:
    schema = schemas.get("workflow-definition.schema.json")
    if not schema:
        err("workflow-definition.schema.json is missing")
        return set()
    known_contract_schemas = {p.name for p in SCHEMA_DIR.glob("*.json")}
    workflow_ids: set[str] = set()
    all_step_ids: set[str] = set()
    for path in sorted(WORKFLOW_DIR.glob("*.yaml")):
        data = load_yaml(path)
        if not data:
            continue
        validate_instance(data, schema, path)
        workflow_id = data.get("metadata", {}).get("id")
        if workflow_id in workflow_ids:
            err(f"Duplicate workflow id: {workflow_id}")
        workflow_ids.add(workflow_id)
        stages = data.get("spec", {}).get("stages", [])
        stage_ids = [s.get("id") for s in stages]
        if len(stage_ids) != len(set(stage_ids)):
            err(f"Duplicate stage id in {path.relative_to(ROOT)}")
        deps: dict[str, list[str]] = {}
        orders: list[int] = []
        for stage in stages:
            stage_id = stage.get("id")
            orders.append(stage.get("order"))
            deps[stage_id] = stage.get("dependsOn", [])
            for dep in stage.get("dependsOn", []):
                if dep not in stage_ids:
                    err(f"Unknown stage dependency {dep} in {path.relative_to(ROOT)}:{stage_id}")
            gate = stage.get("gate")
            if gate and gate not in gate_ids:
                err(f"Unknown gate reference {gate} in {path.relative_to(ROOT)}:{stage_id}")
            for step in stage.get("steps", []):
                step_id = f"{workflow_id}:{step.get('id')}"
                if step_id in all_step_ids:
                    err(f"Duplicate workflow step id: {step_id}")
                all_step_ids.add(step_id)
                contract = step.get("contract", {})
                for key in ("inputSchema", "outputSchema"):
                    ref = contract.get(key)
                    if not ref:
                        continue
                    name = Path(ref).name
                    if name not in known_contract_schemas:
                        err(f"Unknown contract schema {ref} in {path.relative_to(ROOT)}:{step.get('id')}")
        if orders != sorted(orders) or len(orders) != len(set(orders)):
            err(f"Stage orders must be unique and ascending: {path.relative_to(ROOT)}")
        detect_cycle(deps, path)
    STATS["workflows"] = len(workflow_ids)
    STATS["workflow_steps"] = len(all_step_ids)
    return workflow_ids


def validate_gate_rules(schemas: dict[str, dict[str, Any]]) -> set[str]:
    schema = schemas.get("gate-rule-set.schema.json")
    if not schema:
        err("gate-rule-set.schema.json is missing")
        return set()
    gate_ids: set[str] = set()
    rule_ids: set[str] = set()
    for path in sorted(GATE_DIR.glob("*.yaml")):
        data = load_yaml(path)
        if not data:
            continue
        validate_instance(data, schema, path)
        gate_id = data.get("metadata", {}).get("gateId")
        if gate_id in gate_ids:
            err(f"Duplicate gate id: {gate_id}")
        gate_ids.add(gate_id)
        for rule in data.get("spec", {}).get("rules", []):
            rule_id = rule.get("id")
            if rule_id in rule_ids:
                err(f"Duplicate gate rule id: {rule_id}")
            rule_ids.add(rule_id)
            if rule.get("type") == "SCORED" and "weight" not in rule:
                err(f"SCORED rule has no weight: {path.relative_to(ROOT)}:{rule_id}")
            if rule.get("type") == "HUMAN" and not rule.get("requiredApproverRole"):
                err(f"HUMAN rule has no requiredApproverRole: {path.relative_to(ROOT)}:{rule_id}")
    expected = {f"HG-{value:02d}" for value in range(0, 100, 10)}
    missing = expected - gate_ids
    extra = gate_ids - expected
    if missing:
        err(f"Missing gate definitions: {sorted(missing)}")
    if extra:
        warn(f"Additional gate definitions: {sorted(extra)}")
    STATS["gate_rule_sets"] = len(gate_ids)
    STATS["gate_rules"] = len(rule_ids)
    return gate_ids


def walk_refs(value: Any) -> Iterable[str]:
    if isinstance(value, dict):
        for key, child in value.items():
            if key == "$ref" and isinstance(child, str):
                yield child
            yield from walk_refs(child)
    elif isinstance(value, list):
        for child in value:
            yield from walk_refs(child)


def validate_openapi() -> None:
    data = load_yaml(API_FILE)
    if not data:
        return
    if data.get("openapi") != "3.1.0":
        err("OpenAPI version must be 3.1.0")
    operation_ids: list[str] = []
    path_parameters: set[tuple[str, str]] = set()
    for path_name, path_item in data.get("paths", {}).items():
        placeholders = set(re.findall(r"\{([^}]+)\}", path_name))
        for method, operation in path_item.items():
            if method.lower() not in {"get", "post", "put", "patch", "delete", "head", "options", "trace"}:
                continue
            operation_id = operation.get("operationId")
            if not operation_id:
                err(f"OpenAPI operation has no operationId: {method.upper()} {path_name}")
            else:
                operation_ids.append(operation_id)
            declared: set[str] = set()
            for param in operation.get("parameters", []):
                if "$ref" in param:
                    ref_name = param["$ref"].split("/")[-1]
                    definition = data.get("components", {}).get("parameters", {}).get(ref_name, {})
                    if definition.get("in") == "path":
                        declared.add(definition.get("name"))
                elif param.get("in") == "path":
                    declared.add(param.get("name"))
            if placeholders != declared:
                err(f"OpenAPI path parameter mismatch {method.upper()} {path_name}: placeholders={sorted(placeholders)}, declared={sorted(declared)}")
            for name in declared:
                path_parameters.add((path_name, name))
    duplicates = [item for item, count in Counter(operation_ids).items() if count > 1]
    if duplicates:
        err(f"Duplicate OpenAPI operationId: {duplicates}")
    for ref in walk_refs(data):
        if ref.startswith("../"):
            target = (API_FILE.parent / ref.split("#", 1)[0]).resolve()
            if not target.exists():
                err(f"OpenAPI external $ref does not exist: {ref}")
    STATS["api_operations"] = len(operation_ids)


def validate_ddl() -> None:
    table_pattern = re.compile(r"\bCREATE\s+TABLE\s+([A-Z0-9_]+)", re.IGNORECASE)
    ref_pattern = re.compile(r"\bREFERENCES\s+([A-Z0-9_]+)", re.IGNORECASE)
    index_pattern = re.compile(r"\bCREATE\s+(?:UNIQUE\s+)?INDEX\s+([A-Z0-9_]+)", re.IGNORECASE)
    constraint_pattern = re.compile(r"\bCONSTRAINT\s+([A-Z0-9_]+)", re.IGNORECASE)
    tables: list[str] = []
    references: list[tuple[Path, str]] = []
    indexes: list[str] = []
    constraints: list[str] = []
    sql_files = sorted(DDL_DIR.glob("*.sql"))
    for path in sql_files:
        text = path.read_text(encoding="utf-8")
        tables.extend(name.upper() for name in table_pattern.findall(text))
        references.extend((path, name.upper()) for name in ref_pattern.findall(text))
        indexes.extend(name.upper() for name in index_pattern.findall(text))
        constraints.extend(name.upper() for name in constraint_pattern.findall(text))
    for label, values in (("table", tables), ("index", indexes), ("constraint", constraints)):
        duplicates = [name for name, count in Counter(values).items() if count > 1]
        if duplicates:
            err(f"Duplicate Oracle {label} names: {duplicates}")
    table_set = set(tables)
    for path, target in references:
        if target not in table_set:
            err(f"Foreign key references unknown table {target}: {path.relative_to(ROOT)}")
    STATS["ddl_files"] = len(sql_files)
    STATS["db_tables"] = len(table_set)
    STATS["db_indexes"] = len(set(indexes))


def validate_required_files() -> None:
    required = [
        ROOT / "README.md",
        ROOT / "architecture" / "module-package-structure.md",
        ROOT / "database" / "db-table-definition.md",
        ROOT / "api" / "rest-api-spec.md",
        ROOT / "api" / "openapi.yaml",
        ROOT / "workspace" / "workspace-layout.md",
        ROOT / "workspace" / "templates" / "run.yaml",
        ROOT / "workspace" / "templates" / "input-manifest.json",
    ]
    for path in required:
        if not path.exists() or path.stat().st_size == 0:
            err(f"Required file missing or empty: {path.relative_to(ROOT)}")


def main() -> int:
    validate_required_files()
    schemas = validate_json_schemas()
    validate_template_manifest(schemas)
    gate_ids = validate_gate_rules(schemas)
    validate_workflows(schemas, gate_ids)
    validate_openapi()
    validate_ddl()

    print("NSIGHT Auto Harness Design Package Validation")
    print("=" * 48)
    for key in sorted(STATS):
        print(f"{key}: {STATS[key]}")
    if WARNINGS:
        print("\nWarnings:")
        for item in WARNINGS:
            print(f"- {item}")
    if ERRORS:
        print("\nErrors:")
        for item in ERRORS:
            print(f"- {item}")
        print(f"\nRESULT: FAIL ({len(ERRORS)} errors)")
        return 1
    print("\nRESULT: PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
