package nhnis.eos.co.a.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class EosDtm {
    private static final DateTimeFormatter DTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private EosDtm() {}

    public static String now() {
        return LocalDateTime.now().format(DTM);
    }
}
