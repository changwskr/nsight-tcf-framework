package nhnis.fw.exception;

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
 * <p>응답 포맷은 PDMG commons({@code NH_NIS_ERR_DTO})를 유지한다.
 * STF/ETF 표준 전문은 사용하지 않는다.
 */
@RestControllerAdvice
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceHandlerNotFound.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleServiceHandlerNotFound(ServiceHandlerNotFound e) {
        log.warn("[GlobalExceptionHandler] handler not found: {}", e.getMessage());
        return error("E9999", e.getMessage(), TYPE.SERVICE, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleBizException(BizException e) {
        log.warn("[GlobalExceptionHandler] biz exception: code={}", e.getCode());
        return error(e.getCode(), e.getMessage(), TYPE.BIZ, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(OnlineTimeoutException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleOnlineTimeout(OnlineTimeoutException e) {
        log.warn("[GlobalExceptionHandler] online timeout serviceId={} guid={} timeoutMs={} elapsedMs={}",
                e.getServiceId(), e.getGuid(), e.getTimeoutMs(), e.getElapsedMs());
        String detail = "serviceId=" + nullToEmpty(e.getServiceId())
                + ",guid=" + nullToEmpty(e.getGuid())
                + ",timeoutMs=" + e.getTimeoutMs()
                + ",elapsedMs=" + e.getElapsedMs();
        return error("FW_TIMEOUT", e.getMessage(), TYPE.COMMON, HttpStatus.GATEWAY_TIMEOUT, detail);
    }

    @ExceptionHandler(OnlineOverloadException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleOnlineOverload(OnlineOverloadException e) {
        log.warn("[GlobalExceptionHandler] online overload serviceId={} guid={} active={}/{} queue={}",
                e.getServiceId(), e.getGuid(), e.getActive(), e.getPoolSize(), e.getQueueSize());
        String detail = "serviceId=" + nullToEmpty(e.getServiceId())
                + ",guid=" + nullToEmpty(e.getGuid())
                + ",active=" + e.getActive()
                + ",poolSize=" + e.getPoolSize()
                + ",queueSize=" + e.getQueueSize();
        return error("FW_OVERLOADED", e.getMessage(), TYPE.COMMON, HttpStatus.SERVICE_UNAVAILABLE, detail);
    }

    private ResponseEntity<NH_NIS_ERR_DTO> error(String code, String message, TYPE type,
            HttpStatus status, String addMsg) {
        NH_NIS_ERR_DTO dto = new NH_NIS_ERR_DTO();
        dto.setStdErrCode(code);
        dto.setStdErrMsgCntn(message);
        dto.setErrType(type.name());
        if (addMsg != null && !addMsg.isBlank()) {
            dto.setAddMsgContents(addMsg);
        }
        return ResponseEntity.status(status).body(dto);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
