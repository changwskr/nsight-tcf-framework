package nhnis.eos.co.a.application.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import nhnis.eos.co.a.application.service.eoscoa0100Service;
import nhnis.eos.co.a.dto.eoscoa0100S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0100S0DTOout;
import nhnis.fw.commons.resolver.RequestBody;

@RestController
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "false", matchIfMissing = true)
public class eoscoa0100Controller {

    private final eoscoa0100Service service;

    public eoscoa0100Controller(eoscoa0100Service service) {
        this.service = service;
    }

    @PostMapping("/eoscoa0100S0")
    public eoscoa0100S0DTOout eoscoa0100S0(@RequestBody eoscoa0100S0DTOin input) {
        return service.eoscoa0100S0(input);
    }
}
