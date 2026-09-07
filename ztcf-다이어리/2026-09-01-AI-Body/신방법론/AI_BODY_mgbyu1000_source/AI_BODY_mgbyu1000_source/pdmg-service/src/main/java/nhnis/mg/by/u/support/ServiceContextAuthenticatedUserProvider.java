package nhnis.mg.by.u.support;

import nhnis.fw.commons.context.ServiceContext;
import nhnis.fw.commons.context.ServiceContextHolder;
import nhnis.fw.exception.BizException;
import org.springframework.stereotype.Component;

/**
 * PDMG ServiceContext에서 검증된 인증 사용자를 읽는다.
 *
 * <p>AS-IS 호환 우선순위:
 * 1) Framework가 향후 설치할 userContext.authenticatedUserId
 * 2) DefaultFilter가 JWT 검증 후 request attribute에 설치한 ssoId
 * 3) local profile 전용 LOCAL 사용자
 *
 * <p>Client 전문의 작업자 번호나 업무 DTO 사용자 ID는 신뢰하지 않는다.</p>
 */
@Component
public class ServiceContextAuthenticatedUserProvider implements AuthenticatedUserProvider {

    @Override
    public String requireUserId() {
        ServiceContext context = ServiceContextHolder.getInstance();

        if (context == null) {
            throw new BizException("FW0401");
        }

        if (context.getUserContext() != null) {
            Object trusted = context.getUserContext().get("authenticatedUserId");
            String userId = text(trusted);
            if (userId != null) {
                return userId;
            }
        }

        if (context.getHttpServletRequest() != null) {
            Object ssoId = context.getHttpServletRequest().getAttribute("ssoId");
            String userId = text(ssoId);
            if (userId != null) {
                return userId;
            }
        }

        if ("local".equalsIgnoreCase(String.valueOf(context.getActive()))) {
            return "LOCAL";
        }

        throw new BizException("FW0401");
    }

    private String text(Object value) {
        if (value == null) {
            return null;
        }
        String text = String.valueOf(value).trim();
        return text.isEmpty() ? null : text;
    }
}
