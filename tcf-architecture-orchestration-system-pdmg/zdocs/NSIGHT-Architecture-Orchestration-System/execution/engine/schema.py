from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import read_json, now_iso, write_json, write_text


def _typename(value: Any) -> str:
    if isinstance(value, bool): return "boolean"
    if isinstance(value, dict): return "object"
    if isinstance(value, list): return "array"
    if isinstance(value, str): return "string"
    if isinstance(value, int) and not isinstance(value, bool): return "integer"
    if isinstance(value, (int, float)) and not isinstance(value, bool): return "number"
    if value is None: return "null"
    return type(value).__name__


def validate_data(data: Any, schema: dict[str, Any], path: str = "$") -> list[str]:
    errors = []
    stype = schema.get("type")
    if stype and _typename(data) != stype:
        errors.append(f"{path}: expected type {stype}, got {_typename(data)}")
        return errors
    if "const" in schema and data != schema["const"]:
        errors.append(f"{path}: expected const {schema['const']!r}, got {data!r}")
    if "enum" in schema and data not in schema["enum"]:
        errors.append(f"{path}: expected one of {schema['enum']!r}, got {data!r}")
    if isinstance(data, dict):
        for key in schema.get("required", []):
            if key not in data:
                errors.append(f"{path}: missing required key {key}")
        props = schema.get("properties", {})
        for key, subschema in props.items():
            if key in data:
                errors.extend(validate_data(data[key], subschema, f"{path}.{key}"))
    if isinstance(data, list) and "items" in schema:
        for i, item in enumerate(data):
            errors.extend(validate_data(item, schema["items"], f"{path}[{i}]"))
    return errors


def validate_file(data_path: Path, schema_path: Path, out: Path | None = None) -> dict[str, Any]:
    data = read_json(data_path, None)
    schema = read_json(schema_path, {})
    errors = ["data file missing or invalid JSON"] if data is None else validate_data(data, schema)
    result = {
        "validatedAt": now_iso(),
        "data": str(data_path),
        "schema": str(schema_path),
        "exitCode": 0 if not errors else 1,
        "result": "PASS" if not errors else "FAIL",
        "errors": errors,
    }
    if out:
        write_json(out, result)
        write_text(out.with_suffix('.md'), "\n".join([
            "# Schema Validation", "", f"- Result: **{result['result']}**", f"- Data: `{data_path}`", f"- Schema: `{schema_path}`", "", "## Errors", "", *([f"- {e}" for e in errors] or ["- NONE"])
        ]))
    return result
