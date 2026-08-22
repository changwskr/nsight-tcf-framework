package nhnis.eos.co.a.application.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import nhnis.eos.co.a.application.service.eoscoa0120Service;
import nhnis.eos.co.a.dto.eoscoa0120S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0120S0DTOout;
import nhnis.fw.commons.resolver.RequestBody;

@RestController
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "false", matchIfMissing = true)
public class eoscoa0120Controller {

    private final eoscoa0120Service service;

    public eoscoa0120Controller(eoscoa0120Service service) {
        this.service = service;
    }

    @PostMapping("/eoscoa0120S0")
    public eoscoa0120S0DTOout eoscoa0120S0(@RequestBody eoscoa0120S0DTOin input) {
        return service.eoscoa0120S0(input);
    }
}
