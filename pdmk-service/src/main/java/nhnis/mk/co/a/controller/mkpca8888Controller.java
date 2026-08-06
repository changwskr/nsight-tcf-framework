package nhnis.mk.co.a.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nhnis.fw.commons.log.PdmkTxLog;
import nhnis.fw.commons.resolver.RequestBody;
import nhnis.mk.co.a.dto.mkpca8888DtoIn;
import nhnis.mk.co.a.dto.mkpca8888DtoOut;
import nhnis.mk.co.a.dto.mkpca8888ListResponseDto;
import nhnis.mk.co.a.service.mkpca8888Service;

/**
 * ??? CRUD Controller.
 *
 * <p>????? Controller ??? ?? ? ????, Service? ?? TX? ????.
 * ??? readOnly, ??/??/??? ?? TX(timeout 4?).
 */
@RestController
@RequestMapping("/api/mk/co/a/8888")
public class mkpca8888Controller {

    private static final String PROGRAM_ID = "mkpca8888";
    private static final Logger log = LoggerFactory.getLogger(mkpca8888Controller.class);

    private final mkpca8888Service service;

    public mkpca8888Controller(mkpca8888Service service) {
        this.service = service;
    }

    @PostMapping("/list")
    @Transactional(readOnly = true)
    public mkpca8888ListResponseDto mkpca8888S0(@RequestBody mkpca8888DtoIn in) {
        log.info(PdmkTxLog.controllerStart(PROGRAM_ID));
        mkpca8888ListResponseDto out = service.mkpca8888S0(in);
        log.info(PdmkTxLog.controllerEnd(PROGRAM_ID, "mkpca8888S0", out));
        return out;
    }

    @PostMapping("/detail")
    @Transactional(readOnly = true)
    public mkpca8888DtoOut mkpca8888S1(@RequestBody mkpca8888DtoIn in) {
        log.info(PdmkTxLog.controllerStart(PROGRAM_ID));
        mkpca8888DtoOut out = service.mkpca8888S1(in);
        log.info(PdmkTxLog.controllerEnd(PROGRAM_ID, "mkpca8888S1", out));
        return out;
    }

    @PostMapping("/create")
    @Transactional(timeout = 4)
    public void mkpca8888I0(@RequestBody mkpca8888DtoIn in) {
        log.info(PdmkTxLog.controllerStart(PROGRAM_ID));
        service.mkpca8888I0(in);
        log.info(PdmkTxLog.controllerEnd(PROGRAM_ID, "mkpca8888I0", null));
    }

    @PostMapping("/update")
    @Transactional(timeout = 4)
    public void mkpca8888U0(@RequestBody mkpca8888DtoIn in) {
        log.info(PdmkTxLog.controllerStart(PROGRAM_ID));
        service.mkpca8888U0(in);
        log.info(PdmkTxLog.controllerEnd(PROGRAM_ID, "mkpca8888U0", null));
    }

    @PostMapping("/delete")
    @Transactional(timeout = 4)
    public void mkpca8888D0(@RequestBody mkpca8888DtoIn in) {
        log.info(PdmkTxLog.controllerStart(PROGRAM_ID));
        service.mkpca8888D0(in);
        log.info(PdmkTxLog.controllerEnd(PROGRAM_ID, "mkpca8888D0", null));
    }
}
