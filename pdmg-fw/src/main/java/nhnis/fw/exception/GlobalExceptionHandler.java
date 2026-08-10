package nhnis.fw.exception;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import nhnis.fw.commons.dto.NH_NIS_ERR_DTO;
import nhnis.fw.commons.exception.NhBaseException.TYPE;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.timeout.OnlineOverloadException;
import nhnis.fw.tcf.timeout.OnlineTimeoutException;

/**
 * TCF 단일 Controller 경로 예외 처리.
 *
 * <p>오류 본문은 commons {@code NH_NIS_ERR_DTO} 이며,
 * {@code ResponseBodyArgumentResolver}가 응답 전문의 {@code result} 키로 조립한다.
 * 업무 데이터용 {@code dto}와 분리한다. STF/ETF 표준 전문은 사용하지 않는다.
 */
@RestControllerAdvice
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ExceptionCodeProperties exceptionCodes;

    public GlobalExceptionHandler(ExceptionCodeProperties exceptionCodes) {
        this.exceptionCodes = exceptionCodes;
    }

    @ExceptionHandler(ServiceHandlerNotFound.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleServiceHandlerNotFound(ServiceHandlerNotFound e) {
        log.warn("[GlobalExceptionHandler] handler not found: {}", e.getMessage());
        return error("E9999", e.getMessage(), TYPE.SERVICE, HttpStatus.INTERNAL_SERVER_ERROR,
                exceptionCodes.actionMessage("E9999"), e);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleBizException(BizException e) {
        log.warn("[GlobalExceptionHandler] biz exception: code={}", e.getCode());
        String message = exceptionCodes.message(e.getCode(), e.getArgs());
        String action = exceptionCodes.actionMessage(e.getCode());
        return error(e.getCode(), message, TYPE.BIZ, HttpStatus.INTERNAL_SERVER_ERROR, action, e);
    }

    @ExceptionHandler(OnlineTimeoutException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleOnlineTimeout(OnlineTimeoutException e) {
        log.warn("[GlobalExceptionHandler] online timeout serviceId={} guid={} timeoutMs={} elapsedMs={}",
                e.getServiceId(), e.getGuid(), e.getTimeoutMs(), e.getElapsedMs());
        String detail = "serviceId=" + nullToEmpty(e.getServiceId())
                + ",guid=" + nullToEmpty(e.getGuid())
                + ",timeoutMs=" + e.getTimeoutMs()
                + ",elapsedMs=" + e.getElapsedMs()
                + ",조치메시지=" + exceptionCodes.actionMessage("FW_TIMEOUT");
        return error("FW_TIMEOUT", e.getMessage(), TYPE.COMMON, HttpStatus.GATEWAY_TIMEOUT, detail, e);
    }

    @ExceptionHandler(OnlineOverloadException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleOnlineOverload(OnlineOverloadException e) {
        log.warn("[GlobalExceptionHandler] online overload serviceId={} guid={} active={}/{} queue={}",
                e.getServiceId(), e.getGuid(), e.getActive(), e.getPoolSize(), e.getQueueSize());
        String detail = "serviceId=" + nullToEmpty(e.getServiceId())
                + ",guid=" + nullToEmpty(e.getGuid())
                + ",active=" + e.getActive()
                + ",poolSize=" + e.getPoolSize()
                + ",queueSize=" + e.getQueueSize()
                + ",조치메시지=" + exceptionCodes.actionMessage("FW_OVERLOADED");
        return error("FW_OVERLOADED", e.getMessage(), TYPE.COMMON, HttpStatus.SERVICE_UNAVAILABLE, detail, e);
    }

    private ResponseEntity<NH_NIS_ERR_DTO> error(String code, String message, TYPE type,
            HttpStatus status, String addMsg, Throwable source) {
        NH_NIS_ERR_DTO dto = new NH_NIS_ERR_DTO();
        dto.setStdErrCode(code);
        dto.setStdErrMsgCntn(message);
        dto.setErrType(type.name());
        if (addMsg != null && !addMsg.isBlank()) {
            dto.setAddMsgContents(addMsg);
        }
        fillSourceLocation(dto, source);
        return ResponseEntity.status(status).body(dto);
    }

    /**
     * 스택에서 업무 패키지({@code nhnis.*}) 프레임을 골라 클래스/메서드/파일/라인을 채운다.
     * (핸들러 자신은 제외)
     */
    private static void fillSourceLocation(NH_NIS_ERR_DTO dto, Throwable source) {
        if (source == null) {
            return;
        }
        StackTraceElement[] stack = source.getStackTrace();
        if (stack == null || stack.length == 0) {
            return;
        }
        String handlerName = GlobalExceptionHandler.class.getName();
        StackTraceElement target = Arrays.stream(stack)
                .filter(el -> el.getClassName() != null && el.getClassName().startsWith("nhnis"))
                .filter(el -> !handlerName.equals(el.getClassName()))
                .findFirst()
                .orElse(stack[0]);
        dto.setErrClassName(target.getClassName());
        dto.setErrMethodName(target.getMethodName());
        dto.setErrFileName(target.getFileName());
        dto.setErrLineNo(target.getLineNumber());
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
