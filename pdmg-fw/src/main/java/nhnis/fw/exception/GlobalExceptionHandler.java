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
        return error("E9999", e.getMessage(), TYPE.SERVICE);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<NH_NIS_ERR_DTO> handleBizException(BizException e) {
        log.warn("[GlobalExceptionHandler] biz exception: code={}", e.getCode());
        return error(e.getCode(), e.getMessage(), TYPE.BIZ);
    }

    private ResponseEntity<NH_NIS_ERR_DTO> error(String code, String message, TYPE type) {
        NH_NIS_ERR_DTO dto = new NH_NIS_ERR_DTO();
        dto.setStdErrCode(code);
        dto.setStdErrMsgCntn(message);
        dto.setErrType(type.name());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}
