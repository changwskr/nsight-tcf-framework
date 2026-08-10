package nhnis.ontology.web;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping({"/", "/health"})
    public Map<String, Object> health() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "tcf-ontology-service");
        body.put("status", "UP");
        return body;
    }
}
