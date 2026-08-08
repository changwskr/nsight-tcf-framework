package nhnis.mk.ui.entry.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nhnis.mk.ui.application.service.TransactionCatalog;
import nhnis.mk.ui.client.TransactionRelayService;
import nhnis.mk.ui.config.PdmkUiProperties;
import nhnis.mk.ui.support.RelayResult;
import nhnis.mk.ui.support.TransactionInfo;

@RestController
@RequestMapping("/api")
public class PdmkUiApiController {

    private final TransactionCatalog catalog;
    private final TransactionRelayService relayService;
    private final PdmkUiProperties properties;

    public PdmkUiApiController(TransactionCatalog catalog, TransactionRelayService relayService,
            PdmkUiProperties properties) {
        this.catalog = catalog;
        this.relayService = relayService;
        this.properties = properties;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("targetBaseUrl", properties.getTargetBaseUrl());
        config.put("timeoutMs", properties.getTimeoutMs());
        return config;
    }

    @GetMapping("/transactions")
    public List<TransactionInfo> transactions() {
        return catalog.findAll();
    }

    @GetMapping("/transactions/{id}")
    public TransactionInfo transaction(@PathVariable("id") String id) {
        return catalog.findById(id);
    }

    @GetMapping("/transactions/{id}/target-url")
    public Map<String, String> targetUrl(@PathVariable("id") String id,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return Map.of("targetUrl", relayService.resolveTargetUrl(id, baseUrl));
    }

    @PostMapping("/relay/{id}")
    public RelayResult relay(@PathVariable("id") String id,
            @RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relay(id, requestBody, baseUrl);
    }

    @PostMapping("/imagelog/list")
    public RelayResult imageLogList(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa7777S0", requestBody, baseUrl);
    }

    @PostMapping("/imagelog/delete")
    public RelayResult imageLogDelete(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa7777D0", requestBody, baseUrl);
    }

    /** pdmk-service 이미지로그 (mkcoa8888) */
    @PostMapping("/imagelog-svc/list")
    public RelayResult imageLogSvcList(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa8888S0", requestBody, baseUrl);
    }

    @PostMapping("/imagelog-svc/delete")
    public RelayResult imageLogSvcDelete(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa8888D0", requestBody, baseUrl);
    }

    /** pdmk-om 거래통제 Service Catalog / 평가 (mkcoa6666) */
    @PostMapping("/txcontrol/list")
    public RelayResult txControlList(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666S0", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/insert")
    public RelayResult txControlInsert(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666I0", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/update")
    public RelayResult txControlUpdate(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666U0", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/delete")
    public RelayResult txControlDelete(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666D0", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/evaluate")
    public RelayResult txControlEvaluate(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666E0", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/summary")
    public RelayResult txControlSummary(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666S2", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/status")
    public RelayResult txControlStatus(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666U1", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/detail")
    public RelayResult txControlDetail(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666S1", requestBody, baseUrl);
    }

    @PostMapping("/txcontrol/results")
    public RelayResult txControlResults(@RequestBody(required = false) String requestBody,
            @RequestParam(value = "baseUrl", required = false) String baseUrl) {
        return relayService.relayPath("/mkcoa6666S3", requestBody, baseUrl);
    }
}
