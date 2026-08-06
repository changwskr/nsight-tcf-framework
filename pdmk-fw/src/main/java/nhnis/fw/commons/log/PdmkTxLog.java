package nhnis.fw.commons.log;

/**
 * PDMK 운영 트랜잭션 로그 메시지 포맷.
 *
 * <p>실제 {@code log.info} 호출은 호출부(Interceptor/Aspect/Controller/Service)에서 해야
 * Log4j {@code %C.%M} 이 운영과 같이 {@code ServicePreventionInterceptor.postHandle} 등으로 남는다.
 * 이 클래스는 메시지 문자열만 만든다.
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

    /** Aspect 선/후처리 (6) */
    public static final String ASPECT = "▶▶▶▶▶▶";

    /** Controller Start/End (8) */
    public static final String CONTROLLER = "▷▷▷▷▷▷▷▷";

    /** Service Start/End (8) */
    public static final String SERVICE = "▶▶▶▶▶▶▶▶";

    private static final String INTERCEPTOR = "ServicePreventionInterceptor";

    private PdmkTxLog() {
    }

    public static String systemPreProcessorStart() {
        return "[" + INTERCEPTOR + "] SystemPreProcessor Start!";
    }

    public static String systemGuid(String guid) {
        return "[" + INTERCEPTOR + "] GUID: " + guid;
    }

    public static String systemPostProcessor() {
        return "[" + INTERCEPTOR + "] SystemPostProcessor";
    }

    public static String systemErrorProcessor() {
        return "[" + INTERCEPTOR + "] SystemErrorProcessor";
    }

    public static String systemContextNull() {
        return "[" + INTERCEPTOR + "] Service Context is null...!! (continue)";
    }

    public static String bizPreProcess() {
        return ASPECT + " mpco 업무 공통 선처리 " + ASPECT;
    }

    public static String bizPostProcess() {
        return ASPECT + " mpco 업무 공통 후처리 " + ASPECT;
    }

    public static String bizArgumentBefore(Object brc) {
        return "[bizPrePostAspect().before()] Argument: BRC : " + brc;
    }

    public static String bizArgumentAfter(Object brc) {
        return "[bizPrePostAspect().after()] Argument: BRC : " + brc;
    }

    public static String controllerStart(String programId) {
        return CONTROLLER + " " + programId + " Controller Start!";
    }

    public static String controllerEnd(String programId, String methodName, Object dto) {
        return CONTROLLER + " " + programId + " Controller End!" + methodName + "DTOsub0 : " + dto;
    }

    public static String serviceStart(String methodName) {
        return SERVICE + " " + methodName + " Service Start!";
    }

    public static String serviceEnd(String methodName, Object total) {
        return SERVICE + " " + methodName + " Service End! - Total: " + total;
    }
}
