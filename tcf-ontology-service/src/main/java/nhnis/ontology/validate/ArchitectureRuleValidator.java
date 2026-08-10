package nhnis.ontology.validate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import nhnis.ontology.domain.concept.ConceptType;
import nhnis.ontology.domain.concept.OntologyConcept;
import nhnis.ontology.domain.relation.RelationType;
import nhnis.ontology.store.OntologyStore;
import nhnis.ontology.support.ServiceIdParser;

/**
 * Architecture rule validation (Ontology 1.0 RULE-001 ~ RULE-006).
 */
@Service
public class ArchitectureRuleValidator {

    private final OntologyStore store;

    public ArchitectureRuleValidator(OntologyStore store) {
        this.store = store;
    }

    public Map<String, Object> validateAll() {
        List<Map<String, Object>> findings = new ArrayList<>();
        findings.addAll(rule001());
        findings.addAll(rule002());
        findings.addAll(rule003());
        findings.addAll(rule004());
        findings.addAll(rule005());
        findings.addAll(rule006());

        long fails = findings.stream().filter(f -> "FAIL".equals(f.get("severity"))).count();
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("status", fails == 0 ? "PASS" : "FAIL");
        out.put("failCount", fails);
        out.put("findings", findings);
        return out;
    }

    /** RULE-001 ServiceId는 11자리 형식이어야 한다. */
    public List<Map<String, Object>> rule001() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (OntologyConcept c : store.findConceptsByType(ConceptType.SERVICE_ID)) {
            String sid = c.getName();
            boolean ok = ServiceIdParser.isValid(sid);
            out.add(finding("RULE-001", ok ? "PASS" : "FAIL", sid,
                    ok ? "ServiceId format OK" : "ServiceId must be 11-char PDMG format",
                    Map.of("serviceId", sid),
                    c.getProvenance() == null ? null : c.getProvenance().toMap()));
        }
        return out;
    }

    /** RULE-002 모든 ServiceId는 최소 하나의 Handler와 연결되어야 한다. */
    public List<Map<String, Object>> rule002() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (OntologyConcept c : store.findConceptsByType(ConceptType.SERVICE_ID)) {
            boolean ok = !store.findRelations(c.getId(), RelationType.HANDLED_BY).isEmpty();
            out.add(finding("RULE-002", ok ? "PASS" : "FAIL", c.getName(),
                    ok ? "HANDLED_BY present" : "ServiceId has no HANDLED_BY Handler",
                    Map.of("serviceConceptId", c.getId()),
                    c.getProvenance() == null ? null : c.getProvenance().toMap()));
        }
        return out;
    }

    /**
     * RULE-003 Handler가 등록한 ServiceId와 Ontology 관계가 일치해야 한다.
     * 1.0: Ontology HANDLED_BY reverse set must be non-empty and service names match Handler program.
     */
    public List<Map<String, Object>> rule003() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (OntologyConcept handler : store.findConceptsByType(ConceptType.COMPONENT)) {
            if (!"HANDLER".equals(handler.attr("role"))) {
                continue;
            }
            List<String> linked = store.findRelationsTo(handler.getId()).stream()
                    .filter(r -> r.getPredicate() == RelationType.HANDLED_BY)
                    .map(r -> store.findConcept(r.getFromId()).map(OntologyConcept::getName).orElse(""))
                    .filter(s -> !s.isBlank())
                    .toList();
            Object programId = handler.attr("programId");
            boolean ok = !linked.isEmpty()
                    && linked.stream().allMatch(sid -> programId == null || sid.startsWith(String.valueOf(programId)));
            out.add(finding("RULE-003", ok ? "PASS" : "FAIL", handler.getName(),
                    ok ? "Handler-ServiceId relations consistent with programId"
                            : "Handler-ServiceId ontology mismatch",
                    Map.of("linkedServiceIds", linked, "programId", programId == null ? "" : programId),
                    handler.getProvenance() == null ? null : handler.getProvenance().toMap()));
        }
        return out;
    }

    /** RULE-004 Program은 최소 하나 이상의 ServiceId를 가져야 한다. */
    public List<Map<String, Object>> rule004() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (OntologyConcept program : store.findConceptsByType(ConceptType.PROGRAM)) {
            boolean ok = !store.findRelations(program.getId(), RelationType.PROVIDES_SERVICE).isEmpty();
            out.add(finding("RULE-004", ok ? "PASS" : "FAIL", program.getName(),
                    ok ? "Program provides ServiceId" : "Program has no PROVIDES_SERVICE",
                    Map.of("programConceptId", program.getId()),
                    program.getProvenance() == null ? null : program.getProvenance().toMap()));
        }
        return out;
    }

    /** RULE-005 Service는 DAO 또는 Client 중 최소 하나의 하위 의존성을 가져야 한다. */
    public List<Map<String, Object>> rule005() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (OntologyConcept service : store.findConceptsByType(ConceptType.COMPONENT)) {
            if (!"SERVICE".equals(service.attr("role"))) {
                continue;
            }
            boolean ok = store.findRelationsFrom(service.getId()).stream()
                    .anyMatch(r -> r.getPredicate() == RelationType.USES || r.getPredicate() == RelationType.CALLS);
            out.add(finding("RULE-005", ok ? "PASS" : "FAIL", service.getName(),
                    ok ? "Service has USES/CALLS dependency" : "Service has no DAO/Client dependency",
                    Map.of("serviceConceptId", service.getId()),
                    service.getProvenance() == null ? null : service.getProvenance().toMap()));
        }
        return out;
    }

    /** RULE-006 DAO를 통해 DB 접근 시 Mapper 또는 SqlId 관계가 존재해야 한다. */
    public List<Map<String, Object>> rule006() {
        List<Map<String, Object>> out = new ArrayList<>();
        for (OntologyConcept dao : store.findConceptsByType(ConceptType.COMPONENT)) {
            if (!"DAO".equals(dao.attr("role"))) {
                continue;
            }
            boolean ok = store.findRelations(dao.getId(), RelationType.EXECUTES).stream()
                    .anyMatch(r -> store.findConcept(r.getToId())
                            .map(c -> c.getType() == ConceptType.MAPPER || c.getType() == ConceptType.SQL_ID)
                            .orElse(false));
            out.add(finding("RULE-006", ok ? "PASS" : "FAIL", dao.getName(),
                    ok ? "DAO EXECUTES Mapper/SqlId" : "DAO missing Mapper/SqlId EXECUTES relation",
                    Map.of("daoConceptId", dao.getId()),
                    dao.getProvenance() == null ? null : dao.getProvenance().toMap()));
        }
        return out;
    }

    private static Map<String, Object> finding(
            String ruleId,
            String verdict,
            String target,
            String message,
            Map<String, Object> evidence,
            Map<String, Object> source) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("ruleId", ruleId);
        m.put("verdict", verdict);
        m.put("target", target);
        m.put("message", message);
        m.put("evidence", evidence);
        if (source != null) {
            m.put("source", source);
        }
        return m;
    }
}
