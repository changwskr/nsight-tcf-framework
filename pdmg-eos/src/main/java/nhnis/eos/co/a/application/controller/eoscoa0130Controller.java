package nhnis.eos.co.a.application.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import nhnis.eos.co.a.application.service.eoscoa0130Service;
import nhnis.eos.co.a.dto.eoscoa0130C0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130C0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130D0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130D0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130S0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130U0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130U0DTOout;
import nhnis.fw.commons.resolver.RequestBody;

@RestController
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "false", matchIfMissing = true)
public class eoscoa0130Controller {

    private final eoscoa0130Service service;

    public eoscoa0130Controller(eoscoa0130Service service) {
        this.service = service;
    }

    @PostMapping("/eoscoa0130S0")
    public eoscoa0130S0DTOout s0(@RequestBody eoscoa0130S0DTOin input) { return service.eoscoa0130S0(input); }

    @PostMapping("/eoscoa0130C0")
    public eoscoa0130C0DTOout c0(@RequestBody eoscoa0130C0DTOin input) { return service.eoscoa0130C0(input); }

    @PostMapping("/eoscoa0130U0")
    public eoscoa0130U0DTOout u0(@RequestBody eoscoa0130U0DTOin input) { return service.eoscoa0130U0(input); }

    @PostMapping("/eoscoa0130D0")
    public eoscoa0130D0DTOout d0(@RequestBody eoscoa0130D0DTOin input) { return service.eoscoa0130D0(input); }
}
