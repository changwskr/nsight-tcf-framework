package nhnis.mk.co.a.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nhnis.fw.commons.log.PdmkTxLog;
import nhnis.fw.commons.resolver.RequestBody;
import nhnis.mk.co.a.dto.mkpca9999DtoIn;
import nhnis.mk.co.a.dto.mkpca9999DtoOut;
import nhnis.mk.co.a.dto.mkpca9999ListResponseDto;
import nhnis.mk.co.a.service.mkpca9999Service;

/** ??? ?? ?? Controller (PDMK ???? ?? ??). */
@RestController
@RequestMapping("/api/mk/co/a/9999")
public class mkpca9999Controller {

    private static final String PROGRAM_ID = "mkpca9999";
    private static final Logger log = LoggerFactory.getLogger(mkpca9999Controller.class);

    private final mkpca9999Service service;

    public mkpca9999Controller(mkpca9999Service service) {
        this.service = service;
    }

    @PostMapping("/list")
    public mkpca9999ListResponseDto mkpca9999S0(@RequestBody mkpca9999DtoIn in) {
        PdmkTxLog.controllerStart(log, PROGRAM_ID);
        mkpca9999ListResponseDto out = service.mkpca9999S0(in);
        PdmkTxLog.controllerEnd(log, PROGRAM_ID, "mkpca9999S0", out);
        return out;
    }

    @PostMapping("/detail")
    public mkpca9999DtoOut mkpca9999S1(@RequestBody mkpca9999DtoIn in) {
        PdmkTxLog.controllerStart(log, PROGRAM_ID);
        mkpca9999DtoOut out = service.mkpca9999S1(in);
        PdmkTxLog.controllerEnd(log, PROGRAM_ID, "mkpca9999S1", out);
        return out;
    }
}
