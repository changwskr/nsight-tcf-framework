package nhnis.ontology.recommend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import nhnis.ontology.graph.OntologyGraphService;
import nhnis.ontology.ontology.OntologyRegistry;
import nhnis.ontology.prompt.PromptContextExporter;

@Service
public class RecommendService {

    private final OntologyRegistry registry;
    private final OntologyGraphService graphService;
    private final PromptContextExporter promptExporter;

    public RecommendService(
            OntologyRegistry registry,
            OntologyGraphService graphService,
            PromptContextExporter promptExporter) {
        this.registry = registry;
        this.graphService = graphService;
        this.promptExporter = promptExporter;
    }

    /**
     * 신규 시스템/프로그램 구축 시 기존 PDMG 패턴 추천.
     */
    public Map<String, Object> recommend(Map<String, String> request) {
        String system = defaultVal(request.get("system"), "MG");
        String business = defaultVal(request.get("business"), "CO");
        String function = defaultVal(request.get("function"), "A");
        String intent = defaultVal(request.get("intent"), "crud"); // crud | query | delete
        String like = request.get("like"); // optional programId or serviceId

        List<Map<String, Object>> candidates = new ArrayList<>();
        for (Map<String, Object> program : registry.listPrograms()) {
            int score = 0;
            List<String> reasons = new ArrayList<>();
            if (equalsIgnore(system, program.get("majorGroup"))) {
                score += 20;
                reasons.add("same system " + system);
            }
            if (equalsIgnore(business, program.get("businessCode"))) {
                score += 30;
                reasons.add("same business " + business);
            }
            if (equalsIgnore(function, program.get("functionCode"))) {
                score += 20;
                reasons.add("same function " + function);
            }
            score += intentScore(intent, program, reasons);
            if (like != null && !like.isBlank()) {
                String programId = String.valueOf(program.get("programId"));
                if (programId.equalsIgnoreCase(like) || containsService(program, like)) {
                    score += 50;
                    reasons.add("explicit like=" + like);
                }
            }
            if (score <= 0) {
                continue;
            }
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("programId", program.get("programId"));
            row.put("title", program.get("title"));
            row.put("score", score);
            row.put("reasons", reasons);
            row.put("reuse", Map.of(
                    "packageRoot", program.get("packageRoot"),
                    "development", program.get("development"),
                    "data", program.get("data"),
                    "services", program.get("services"),
                    "path", graphService.buildProgramGraph(program).get("pathLabel")));
            candidates.add(row);
        }
        candidates.sort((a, b) -> Integer.compare((int) b.get("score"), (int) a.get("score")));
        if (candidates.size() > 5) {
            candidates = new ArrayList<>(candidates.subList(0, 5));
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("status", candidates.isEmpty() ? "NO_MATCH" : "OK");
        Map<String, Object> reqOut = new LinkedHashMap<>();
        reqOut.put("system", system);
        reqOut.put("business", business);
        reqOut.put("function", function);
        reqOut.put("intent", intent);
        if (like != null && !like.isBlank()) {
            reqOut.put("like", like);
        }
        out.put("request", reqOut);
        out.put("recommendations", candidates);
        if (!candidates.isEmpty()) {
            String top = String.valueOf(candidates.get(0).get("programId"));
            out.put("topProgramId", top);
            out.put("promptMarkdown", promptExporter.asMarkdown(top));
            out.put("nextSteps", List.of(
                    "Reuse package/component pattern from topProgramId",
                    "Allocate new identifier (4 digits) under same MG/CO/A axis",
                    "Run seedPdmg after implementation, then validatePdmg",
                    "Attach promptMarkdown to 32.범용CRUD프롬프트"));
        }
        out.put("metaModel", "ontology/core/meta-model.yml");
        out.put("relations", "ontology/core/relations.yml");
        return out;
    }

    private static int intentScore(String intent, Map<String, Object> program, List<String> reasons) {
        if (!(program.get("services") instanceof List<?> services)) {
            return 0;
        }
        boolean hasS = false;
        boolean hasC = false;
        boolean hasU = false;
        boolean hasD = false;
        for (Object item : services) {
            if (item instanceof Map<?, ?> svc && svc.get("op") != null) {
                switch (String.valueOf(svc.get("op"))) {
                    case "S" -> hasS = true;
                    case "C" -> hasC = true;
                    case "U" -> hasU = true;
                    case "D" -> hasD = true;
                    default -> {
                    }
                }
            }
        }
        String i = intent.toLowerCase(Locale.ROOT);
        if ("crud".equals(i) && hasS && hasC && hasU && hasD) {
            reasons.add("full CRUD services");
            return 25;
        }
        if (("query".equals(i) || "read".equals(i)) && hasS && !hasC && !hasU) {
            reasons.add("query-oriented");
            return 25;
        }
        if ("delete".equals(i) && hasD) {
            reasons.add("has delete service");
            return 15;
        }
        if (hasS) {
            reasons.add("has query service");
            return 5;
        }
        return 0;
    }

    private static boolean containsService(Map<String, Object> program, String serviceId) {
        if (!(program.get("services") instanceof List<?> services)) {
            return false;
        }
        for (Object item : services) {
            if (item instanceof Map<?, ?> svc
                    && serviceId.equalsIgnoreCase(String.valueOf(svc.get("serviceId")))) {
                return true;
            }
        }
        return false;
    }

    private static boolean equalsIgnore(String expected, Object actual) {
        return expected != null && actual != null
                && expected.equalsIgnoreCase(String.valueOf(actual));
    }

    private static String defaultVal(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }
}
