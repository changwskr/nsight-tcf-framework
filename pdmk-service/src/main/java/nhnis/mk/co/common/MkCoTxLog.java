package nhnis.mk.co.common;

import org.slf4j.Logger;

/**
 * PDMK Controller/Service 트랜잭션 로그 헬퍼 (운영 로그 형식).
 *
 * <pre>
 * ▶▶▶▶▶▶▶ {programId} Controller Start!
 * ▶▶▶▶▶▶▶ {methodName} Service Start!
 * ◀◀◀◀◀◀◀ {methodName} Service End! - Total: {n}
 * ◀◀◀◀◀◀◀ {programId} Controller End!{methodName}DTOsub0 : {dto}
 * </pre>
 */
public final class MkCoTxLog {

    private MkCoTxLog() {
    }

    public static void controllerStart(Logger log, String programId) {
        log.info("▶▶▶▶▶▶▶ {} Controller Start!", programId);
    }

    public static void controllerEnd(Logger log, String programId, String methodName, Object dto) {
        log.info("◀◀◀◀◀◀◀ {} Controller End!{}DTOsub0 : {}", programId, methodName, dto);
    }

    public static void serviceStart(Logger log, String methodName) {
        log.info("▶▶▶▶▶▶▶ {} Service Start!", methodName);
    }

    public static void serviceEnd(Logger log, String methodName, Object total) {
        log.info("◀◀◀◀◀◀◀ {} Service End! - Total: {}", methodName, total);
    }
}
