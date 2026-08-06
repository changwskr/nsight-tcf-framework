package nhnis.mk.co.a.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nhnis.fw.commons.log.PdmkTxLog;
import nhnis.fw.commons.resolver.RequestBody;
import nhnis.mk.co.a.dto.mkpca5530DtoIn;
import nhnis.mk.co.a.dto.mkpca5530ListResponseDto;
import nhnis.mk.co.a.service.mkpca5530Service;

/**
 * mkpca5530 ???? ?? Controller.
 *
 * <p>PDMK ??: DefaultFilter ? ServicePreventionInterceptor ? BizPrePostAspect ? Controller ? Service.
 * ?? Body? {@code {"dto":{...}}} ??({@link RequestBody}).
 */
@RestController
@RequestMapping("/api/mk/co/a/5530")
public class mkpca5530Controller {

    private static final String PROGRAM_ID = "mkpca5530";
    private static final Logger log = LoggerFactory.getLogger(mkpca5530Controller.class);

    private final mkpca5530Service service;

    public mkpca5530Controller(mkpca5530Service service) {
        this.service = service;
    }

    @PostMapping("/list")
    public mkpca5530ListResponseDto mkpca5530S0(@RequestBody mkpca5530DtoIn in) {
        PdmkTxLog.controllerStart(log, PROGRAM_ID);
        mkpca5530ListResponseDto out = service.mkpca5530S0(in);
        PdmkTxLog.controllerEnd(log, PROGRAM_ID, "mkpca5530S0", out);
        return out;
    }
}
