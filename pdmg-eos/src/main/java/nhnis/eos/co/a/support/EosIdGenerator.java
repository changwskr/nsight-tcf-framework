package nhnis.eos.co.a.support;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * ID 채번 — ADR-001 CONF-003 Default A: 접두3 + yyyyMMdd + 6digit seq
 */
@Component
public class EosIdGenerator {

    private static final DateTimeFormatter YMD = DateTimeFormatter.BASIC_ISO_DATE;
    private final AtomicInteger seq = new AtomicInteger((int) (System.currentTimeMillis() % 100000));

    public String next(String prefix3) {
        String p = prefix3 == null || prefix3.isBlank() ? "EOS" : prefix3.trim();
        if (p.length() > 3) {
            p = p.substring(0, 3);
        }
        while (p.length() < 3) {
            p = p + "X";
        }
        int n = seq.incrementAndGet() % 1_000_000;
        return p.toUpperCase() + LocalDate.now().format(YMD) + String.format("%06d", n);
    }

    public String resourceId() { return next("RSC"); }
    public String productId() { return next("PRD"); }
    public String versionId() { return next("VER"); }
    public String lifecycleId() { return next("LFC"); }
}
