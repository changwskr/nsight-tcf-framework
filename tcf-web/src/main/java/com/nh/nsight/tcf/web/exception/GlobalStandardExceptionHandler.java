package com.nh.nsight.tcf.web.exception;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.core.message.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalStandardExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse<Object> handleBusiness(BusinessException e) {
        return StandardResponse.fail(null, e.getErrorCode(), e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse<Object> handleValidation(MethodArgumentNotValidException e) {
        return StandardResponse.fail(null, ErrorCode.INVALID_HEADER, "??? ??? ??????.", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse<Object> handleSystem(Exception e) {
        return StandardResponse.fail(null, ErrorCode.SYSTEM_ERROR, "??? ??? ??????.", e.getClass().getSimpleName());
    }
}
