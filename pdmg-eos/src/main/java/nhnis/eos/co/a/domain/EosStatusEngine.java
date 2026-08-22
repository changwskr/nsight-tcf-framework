package nhnis.eos.co.a.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

/**
 * EOS 잔여일·상태 재산정 (서버 전용). ADR-001 / POLICY thresholds.
 */
@Component
public class EosStatusEngine {

    private static final DateTimeFormatter YMD = DateTimeFormatter.BASIC_ISO_DATE;

    public Integer remainDays(String eosYmd) {
        if (eosYmd == null || eosYmd.isBlank() || eosYmd.length() < 8) {
            return null;
        }
        LocalDate eos = LocalDate.parse(eosYmd.substring(0, 8), YMD);
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), eos);
    }

    /**
     * Default thresholds: APPROACHING 365, DUE 90 (seed POL-THR-*).
     */
    public String resolveStatus(String eosYmd, String eolYmd, int approachingDays, int dueDays) {
        Integer remain = remainDays(eosYmd);
        if (remain == null) {
            return "NORMAL";
        }
        if (eolYmd != null && eolYmd.length() >= 8) {
            LocalDate eol = LocalDate.parse(eolYmd.substring(0, 8), YMD);
            if (!LocalDate.now().isBefore(eol)) {
                return "EOL";
            }
        }
        if (remain < 0) {
            return "OVERDUE";
        }
        if (remain <= dueDays) {
            return "DUE";
        }
        if (remain <= approachingDays) {
            return "APPROACHING";
        }
        return "NORMAL";
    }
}
