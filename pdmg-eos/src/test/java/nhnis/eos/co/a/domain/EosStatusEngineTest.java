package nhnis.eos.co.a.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class EosStatusEngineTest {

    private static final DateTimeFormatter YMD = DateTimeFormatter.BASIC_ISO_DATE;
    private final EosStatusEngine engine = new EosStatusEngine();

    @Test
    void remainDays_nullWhenBlank() {
        assertThat(engine.remainDays(null)).isNull();
        assertThat(engine.remainDays("")).isNull();
        assertThat(engine.remainDays("2026")).isNull();
    }

    @Test
    void resolveStatus_normalWhenFar() {
        String eos = LocalDate.now().plusDays(400).format(YMD);
        assertThat(engine.resolveStatus(eos, null, 365, 90)).isEqualTo("NORMAL");
    }

    @Test
    void resolveStatus_approachingAndDue() {
        String approaching = LocalDate.now().plusDays(200).format(YMD);
        assertThat(engine.resolveStatus(approaching, null, 365, 90)).isEqualTo("APPROACHING");

        String due = LocalDate.now().plusDays(30).format(YMD);
        assertThat(engine.resolveStatus(due, null, 365, 90)).isEqualTo("DUE");
    }

    @Test
    void resolveStatus_overdueAndEol() {
        String past = LocalDate.now().minusDays(5).format(YMD);
        assertThat(engine.resolveStatus(past, null, 365, 90)).isEqualTo("OVERDUE");

        String eos = LocalDate.now().plusDays(10).format(YMD);
        String eol = LocalDate.now().minusDays(1).format(YMD);
        assertThat(engine.resolveStatus(eos, eol, 365, 90)).isEqualTo("EOL");
    }
}
