from __future__ import annotations

import re
import xml.etree.ElementTree as ET
from collections import Counter, defaultdict
from pathlib import Path
from typing import Any

from .common import UNKNOWN, git_facts, iter_files, now_iso, safe_rel, sha256_file, write_json, write_text

SERVICE_ID_RE = re.compile(r"\b[a-z][a-z0-9]{3,15}[SUQ][0-9]\b")
PACKAGE_RE = re.compile(r"^\s*package\s+([\w.]+)\s*;", re.M)
CLASS_RE = re.compile(r"\b(?:class|interface|enum|record)\s+([A-Za-z_][A-Za-z0-9_]*)")
IMPORT_RE = re.compile(r"^\s*import\s+([\w.*]+)\s*;", re.M)
ANNOT_RE = re.compile(r"@([A-Za-z_][A-Za-z0-9_]*)")
JAVA_VERSION_RE = re.compile(r"JavaLanguageVersion\.of\((\d+)\)")
SPRING_BOOT_RE = re.compile(r"org\.springframework\.boot['\"]\s+version\s+['\"]([^'\"]+)")
GRADLE_DIST_RE = re.compile(r"gradle-([0-9][0-9A-Za-z.\-]+)-(?:bin|all)\.zip")
TABLE_RE = re.compile(r"\b(?:from|join|update|into|delete\s+from)\s+([A-Za-z0-9_$#.]+)", re.I)

CONFIG_EXTS = {".yml", ".yaml", ".properties", ".xml", ".conf", ".ini", ".toml"}
DOC_EXTS = {".md", ".adoc", ".txt", ".pdf", ".doc", ".docx"}
SQL_EXTS = {".sql", ".ddl"}


def classify(path: Path) -> str:
    s = path.as_posix().lower()
    if "/src/test/" in s or path.name.lower().startswith("test"):
        return "TEST"
    if path.suffix.lower() == ".java":
        return "SOURCE"
    if path.suffix.lower() in SQL_EXTS:
        return "SQL_DDL"
    if path.suffix.lower() in CONFIG_EXTS or path.name in {"build.gradle", "settings.gradle", "gradle.properties", "pom.xml"}:
        return "CONFIGURATION"
    if path.suffix.lower() in DOC_EXTS:
        return "DOCUMENT"
    return "OTHER"


def _text(path: Path) -> str:
    try:
        return path.read_text(encoding="utf-8", errors="replace")
    except Exception:
        return ""


def java_fact(path: Path, root: Path) -> dict[str, Any]:
    text = _text(path)
    pkg = PACKAGE_RE.search(text)
    cls = CLASS_RE.search(text)
    annotations = sorted(set(ANNOT_RE.findall(text)))
    service_ids = sorted(set(SERVICE_ID_RE.findall(text)))
    return {
        "path": safe_rel(path, root),
        "package": pkg.group(1) if pkg else UNKNOWN,
        "class": cls.group(1) if cls else path.stem,
        "imports": sorted(set(IMPORT_RE.findall(text))),
        "annotations": annotations,
        "serviceIds": service_ids,
        "isHandler": (("entry" in [x.lower() for x in path.parts] and "handler" in [x.lower() for x in path.parts]) or "implements TransactionHandler" in text or "Collection<String> serviceIds()" in text),
        "isFacade": "Facade" in path.stem or "facade" in path.parts,
        "isService": path.stem.endswith("Service") or "service" in path.parts,
        "isDao": path.stem.endswith("DAO") or path.stem.endswith("Dao") or "dao" in [x.lower() for x in path.parts],
        "usesTransactional": "@Transactional" in text or "TransactionTemplate" in text,
        "usesTransactionTemplate": "TransactionTemplate" in text,
        "usesPrivateKey": any(x in text for x in ["PrivateKey", "PKCS8EncodedKeySpec", "private-key", "privateKey"]),
        "usesJwks": any(x in text.upper() for x in ["JWKS", "JWKSET"]),
        "sha256": sha256_file(path),
    }


def mapper_fact(path: Path, root: Path) -> dict[str, Any] | None:
    text = _text(path)
    if "<mapper" not in text:
        return None
    try:
        tree = ET.parse(path)
        mapper = tree.getroot()
        ns = mapper.attrib.get("namespace", UNKNOWN)
        stmts = []
        for el in mapper:
            tag = el.tag.split("}")[-1].lower()
            if tag not in {"select", "insert", "update", "delete"}:
                continue
            sql_text = " ".join("".join(el.itertext()).split())
            tables = sorted(set(TABLE_RE.findall(sql_text)))
            stmts.append({
                "id": el.attrib.get("id", UNKNOWN),
                "type": tag.upper(),
                "tables": tables,
                "sqlHash": __import__("hashlib").sha256(sql_text.encode("utf-8")).hexdigest(),
            })
        return {"path": safe_rel(path, root), "namespace": ns, "statements": stmts}
    except Exception:
        return {"path": safe_rel(path, root), "namespace": UNKNOWN, "statements": [], "parseError": True}


def gradle_facts(project: Path) -> dict[str, Any]:
    candidates = [project / "build.gradle", project / "build.gradle.kts"]
    texts = "\n".join(_text(p) for p in candidates if p.exists())
    java_versions = sorted(set(JAVA_VERSION_RE.findall(texts)))
    boot_versions = sorted(set(SPRING_BOOT_RE.findall(texts)))
    wrapper = project / "gradle/wrapper/gradle-wrapper.properties"
    wrapper_text = _text(wrapper)
    gradle_versions = sorted(set(GRADLE_DIST_RE.findall(wrapper_text)))
    return {
        "java": java_versions[0] if len(java_versions) == 1 else (java_versions or [UNKNOWN]),
        "springBoot": boot_versions[0] if len(boot_versions) == 1 else (boot_versions or [UNKNOWN]),
        "gradle": gradle_versions[0] if len(gradle_versions) == 1 else (gradle_versions or [UNKNOWN]),
        "buildFile": "build.gradle" if (project / "build.gradle").exists() else ("build.gradle.kts" if (project / "build.gradle.kts").exists() else UNKNOWN),
        "settingsPresent": (project / "settings.gradle").exists() or (project / "settings.gradle.kts").exists(),
        "gradlewPresent": (project / "gradlew").exists() or (project / "gradlew.bat").exists(),
    }


def scan_project(project: Path, repo_root: Path) -> dict[str, Any]:
    files = list(iter_files(project))
    inventory = Counter(classify(p) for p in files)
    java = [java_fact(p, repo_root) for p in files if p.suffix.lower() == ".java"]
    mappers = []
    for p in files:
        if p.suffix.lower() == ".xml":
            fact = mapper_fact(p, repo_root)
            if fact:
                mappers.append(fact)
    svc_owner: dict[str, list[str]] = defaultdict(list)
    for j in java:
        if j["isHandler"]:
            for sid in j["serviceIds"]:
                svc_owner[sid].append(j["class"])
    sql_ids = []
    table_names = []
    for m in mappers:
        for st in m["statements"]:
            sql_ids.append(f"{m['namespace']}::{st['id']}")
            table_names.extend(st.get("tables", []))

    # Build a lightweight dependency graph from Java imports. This is static evidence,
    # not a claim that every import is an actual runtime call.
    by_fqn = {}
    by_class = {}
    for j in java:
        fqn = j["class"] if j["package"] == UNKNOWN else f"{j['package']}.{j['class']}"
        by_fqn[fqn] = j
        by_class[j["class"]] = j
    relations = []
    outgoing = defaultdict(list)
    for j in java:
        src = j["class"]
        for imp in j.get("imports", []):
            target = by_fqn.get(imp)
            if not target:
                continue
            dst = target["class"]
            rel = {"from": src, "to": dst, "relation": "DEPENDS_ON", "sourcePath": j["path"]}
            relations.append(rel)
            outgoing[src].append(dst)

    def role(j):
        if not j: return "UNKNOWN"
        if j.get("isHandler"): return "Handler"
        if j.get("isFacade"): return "Facade"
        if j.get("isDao"): return "DAO"
        if j.get("isService"): return "Service"
        return "Component"

    service_traces = []
    for sid, owners in sorted(svc_owner.items()):
        for owner in sorted(set(owners)):
            visited = set()
            queue = [(owner, [owner])]
            best = [owner]
            while queue:
                cur, path = queue.pop(0)
                if cur in visited or len(path) > 8:
                    continue
                visited.add(cur)
                if len(path) > len(best): best = path
                for nxt in outgoing.get(cur, []):
                    if nxt not in path:
                        queue.append((nxt, path + [nxt]))
            service_traces.append({
                "serviceId": sid,
                "handler": owner,
                "components": [{"class": c, "role": role(by_class.get(c))} for c in best],
                "traceStatus": "STATIC_IMPORT_GRAPH",
            })
    return {
        "name": project.name,
        "path": safe_rel(project, repo_root),
        "technology": gradle_facts(project),
        "fileCounts": dict(inventory),
        "java": java,
        "mappers": mappers,
        "serviceIdOwners": dict(sorted(svc_owner.items())),
        "duplicateServiceIds": {k: v for k, v in svc_owner.items() if len(set(v)) > 1},
        "duplicateSqlIds": sorted([k for k, v in Counter(sql_ids).items() if v > 1]),
        "tables": sorted(set(table_names)),
        "relations": relations,
        "serviceTraces": service_traces,
    }


def scan_repository(repo_root: Path, project_names: list[str]) -> dict[str, Any]:
    repo_root = repo_root.resolve()
    project_results = []
    missing = []
    for name in project_names:
        p = repo_root / name
        if p.is_dir():
            project_results.append(scan_project(p, repo_root))
        else:
            missing.append(name)
    return {
        "scannedAt": now_iso(),
        "repositoryRoot": str(repo_root),
        "git": git_facts(repo_root),
        "requestedProjects": project_names,
        "missingProjects": missing,
        "projects": project_results,
    }


def write_scan_outputs(scan: dict[str, Any], out: Path) -> None:
    write_json(out / "source-baseline.json", scan)
    source_inventory = []
    config_inventory = []
    service_index = []
    mapper_index = []
    relation_index = []
    traceability_index = []
    for p in scan["projects"]:
        for j in p["java"]:
            source_inventory.append({"project": p["name"], **j})
        for sid, owners in p["serviceIdOwners"].items():
            service_index.append({"project": p["name"], "serviceId": sid, "handlers": owners})
        for m in p["mappers"]:
            mapper_index.append({"project": p["name"], **m})
        for r in p.get("relations", []):
            relation_index.append({"project": p["name"], **r})
        for t in p.get("serviceTraces", []):
            traceability_index.append({"project": p["name"], **t})
        project_path = Path(scan["repositoryRoot"]) / p["name"]
        for f in iter_files(project_path):
            if classify(f) == "CONFIGURATION":
                config_inventory.append({
                    "project": p["name"],
                    "path": safe_rel(f, Path(scan["repositoryRoot"])),
                    "sha256": sha256_file(f),
                })
    write_json(out / "source-inventory.json", source_inventory)
    write_json(out / "config-inventory.json", config_inventory)
    write_json(out / "serviceid-index.json", service_index)
    write_json(out / "mapper-sql-index.json", mapper_index)
    write_json(out / "component-relations.json", relation_index)
    write_json(out / "traceability.json", traceability_index)
    md = [
        "# Source Baseline Scan",
        "",
        f"- Repository: `{scan['repositoryRoot']}`",
        f"- Branch: `{scan['git']['branch']}`",
        f"- Commit: `{scan['git']['commit']}`",
        f"- Missing projects: `{', '.join(scan['missingProjects']) if scan['missingProjects'] else 'NONE'}`",
        "",
        "| Project | Java | Test | Java Version | Spring Boot | Gradle | ServiceId | Mapper |",
        "|---|---:|---:|---|---|---|---:|---:|",
    ]
    for p in scan["projects"]:
        t = p["technology"]
        md.append(
            f"| {p['name']} | {p['fileCounts'].get('SOURCE',0)} | {p['fileCounts'].get('TEST',0)} | "
            f"{t['java']} | {t['springBoot']} | {t['gradle']} | {len(p['serviceIdOwners'])} | {len(p['mappers'])} |"
        )
    write_text(out / "SOURCE-BASELINE.md", "\n".join(md))
