package nhnis.fw.commons.log;

import org.slf4j.Logger;

/**
 * PDMK 운영 트랜잭션 로그 메시지 헬퍼 (운영 콘솔과 동일 포맷).
 *
 * <pre>
 * [ServicePreventionInterceptor] SystemPreProcessor Start!
 * [ServicePreventionInterceptor] GUID: ...
 * ▶▶▶▶▶▶ mpco 업무 공통 선처리 ▶▶▶▶▶▶
 * [bizPrePostAspect().before()] Argument: BRC : null
 * ▷▷▷▷▷▷▷▷ mkpca5530 Controller Start!
 * ▶▶▶▶▶▶▶▶ mkpca5530S0 Service Start!
 * ▶▶▶▶▶▶▶▶ mkpca5530S0 Service End! - Total: 3
 * ▷▷▷▷▷▷▷▷ mkpca5530 Controller End!mkpca5530S0DTOsub0 : [...]
 * ▶▶▶▶▶▶ mpco 업무 공통 후처리 ▶▶▶▶▶▶
 * [bizPrePostAspect().after()] Argument: BRC : null
 * [ServicePreventionInterceptor] SystemPostProcessor
 * </pre>
 */
public final class PdmkTxLog {

    /** Aspect 선/후처리 (6, 채운 오른쪽 삼각형 — 선·후 동일) */
    public static final String ASPECT = "▶▶▶▶▶▶";

    /** Controller Start/End (8, 속이 빈 삼각형 — 시작·종료 동일) */
    public static final String CONTROLLER = "▷▷▷▷▷▷▷▷";

    /** Service Start/End (8, 채운 삼각형 — 시작·종료 동일) */
    public static final String SERVICE = "▶▶▶▶▶▶▶▶";

    private static final String INTERCEPTOR = "ServicePreventionInterceptor";

    private PdmkTxLog() {
    }

    public static void systemPreProcessorStart(Logger log) {
        log.info("[{}] SystemPreProcessor Start!", INTERCEPTOR);
    }

    public static void systemGuid(Logger log, String guid) {
        log.info("[{}] GUID: {}", INTERCEPTOR, guid);
    }

    public static void systemPostProcessor(Logger log) {
        log.info("[{}] SystemPostProcessor", INTERCEPTOR);
    }

    public static void systemErrorProcessor(Logger log) {
        log.error("[{}] SystemErrorProcessor", INTERCEPTOR);
    }

    public static void systemContextNull(Logger log) {
        log.warn("[{}] Service Context is null...!! (continue)", INTERCEPTOR);
    }

    public static void bizPreProcess(Logger log) {
        log.info("{} mpco 업무 공통 선처리 {}", ASPECT, ASPECT);
    }

    public static void bizPostProcess(Logger log) {
        log.info("{} mpco 업무 공통 후처리 {}", ASPECT, ASPECT);
    }

    public static void bizArgumentBefore(Logger log, Object brc) {
        log.info("[bizPrePostAspect().before()] Argument: BRC : {}", brc);
    }

    public static void bizArgumentAfter(Logger log, Object brc) {
        log.info("[bizPrePostAspect().after()] Argument: BRC : {}", brc);
    }

    public static void controllerStart(Logger log, String programId) {
        log.info("{} {} Controller Start!", CONTROLLER, programId);
    }

    public static void controllerEnd(Logger log, String programId, String methodName, Object dto) {
        log.info("{} {} Controller End!{}DTOsub0 : {}", CONTROLLER, programId, methodName, dto);
    }

    public static void serviceStart(Logger log, String methodName) {
        log.info("{} {} Service Start!", SERVICE, methodName);
    }

    public static void serviceEnd(Logger log, String methodName, Object total) {
        log.info("{} {} Service End! - Total: {}", SERVICE, methodName, total);
    }
}
