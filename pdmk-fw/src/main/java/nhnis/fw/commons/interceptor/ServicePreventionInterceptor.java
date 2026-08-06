/***************************************************************************
 * Copyright 2026 by Nonghyup. All rights reserved. Nonghyup 의 사전 승인 없이
 * 본 내용의 전부 또는 일부에 대한 복사, 배포, 사용을 금합니다. Nonghyup의 사전 승인
 * 없이 소스코드를 변경하여 사용하는 경우 소스코드에 대한 품질과 성능을 보장하지 않습니다.
 *
 * If you modify this source without Nonghyup’s approval. Nonghyup does
 * not guarantee the quality and performance of source.
 ***************************************************************************/
package nhnis.fw.commons.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhnis.fw.commons.context.ServiceContextHolder;
import nhnis.fw.commons.dto.header.hdr_nhnis;
import nhnis.fw.commons.log.PdmkTxLog;

/**
 * 시스템 선/후처리 Interceptor.
 *
 * <p>PDMK 운영 로그: {@link PdmkTxLog#systemPreProcessorStart} /
 * {@link PdmkTxLog#systemGuid} / {@link PdmkTxLog#systemPostProcessor}
 */
@Component
public class ServicePreventionInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicePreventionInterceptor.class);
    private static final String MULTI_PART = "multipart";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (LOGGER.isInfoEnabled()) {
            PdmkTxLog.systemPreProcessorStart(LOGGER);
        }

        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(MULTI_PART)) {
            return true;
        }

        if (ServiceContextHolder.getInstance() == null) {
            PdmkTxLog.systemContextNull(LOGGER);
            return true;
        }

        hdr_nhnis header = ServiceContextHolder.getInstance().getHeader();
        if (header != null && header.getSys_comm() != null) {
            String guid = header.getSys_comm().getStd_gbl_id();
            if (guid != null) {
                PdmkTxLog.systemGuid(LOGGER, guid);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            PdmkTxLog.systemPostProcessor(LOGGER);
        }

        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(MULTI_PART)) {
            // 파일 업로드
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        if (ex != null) {
            PdmkTxLog.systemErrorProcessor(LOGGER);
            ServiceContextHolder.removeInstance();
            throw ex;
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public <T> T convertMapToDto(Object input, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.convertValue(input, type);
    }
}
