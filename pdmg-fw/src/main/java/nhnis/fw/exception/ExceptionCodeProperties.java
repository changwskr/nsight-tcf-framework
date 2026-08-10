package nhnis.fw.exception;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * exceptionCode.yml의 {@code nhnis.exception.*} / {@code nhnis.exception-action.*} 메시지 사전.
 *
 * <p>Spring의 완화된 바인딩은 맵 키를 소문자로 정규화할 수 있어 조회는 대소문자를 가리지 않는다.
 */
@ConfigurationProperties("nhnis")
public class ExceptionCodeProperties {

    /** 미분류 오류에 사용할 기본 코드. */
    public static final String DEFAULT_CODE = "FW9999";

    private static final String FALLBACK_MESSAGE = "시스템 오류가 발생하였습니다.";
    private static final String FALLBACK_ACTION = "입력값을 확인한 뒤 다시 시도하세요. 문제가 계속되면 시스템 관리자에게 문의하세요.";

    private Map<String, String> exception = new LinkedHashMap<>();
    private Map<String, String> exceptionAction = new LinkedHashMap<>();

    public Map<String, String> getException() {
        return exception;
    }

    public void setException(Map<String, String> exception) {
        this.exception = toCaseInsensitive(exception);
    }

    public Map<String, String> getExceptionAction() {
        return exceptionAction;
    }

    public void setExceptionAction(Map<String, String> exceptionAction) {
        this.exceptionAction = toCaseInsensitive(exceptionAction);
    }

    public String message(String code, Object... args) {
        String template = exception.get(code);
        if (template == null) {
            template = exception.getOrDefault(DEFAULT_CODE, FALLBACK_MESSAGE);
        }
        if (args == null || args.length == 0) {
            return template;
        }
        return MessageFormat.format(template, args);
    }

    /** 오류 코드별 조치(안내) 메시지. 없으면 기본 조치 문구. */
    public String actionMessage(String code) {
        if (code == null || code.isBlank()) {
            return FALLBACK_ACTION;
        }
        String action = exceptionAction.get(code);
        if (action == null || action.isBlank()) {
            action = exceptionAction.get(DEFAULT_CODE);
        }
        return (action == null || action.isBlank()) ? FALLBACK_ACTION : action;
    }

    private static Map<String, String> toCaseInsensitive(Map<String, String> source) {
        Map<String, String> caseInsensitive = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (source != null) {
            caseInsensitive.putAll(source);
        }
        return caseInsensitive;
    }
}
