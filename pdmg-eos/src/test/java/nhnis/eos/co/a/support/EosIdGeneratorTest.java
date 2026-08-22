package nhnis.eos.co.a.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class EosIdGeneratorTest {

    private final EosIdGenerator ids = new EosIdGenerator();

    @Test
    void next_prefixDateAndSixDigits() {
        String id = ids.next("rsc");
        String ymd = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        assertThat(id).startsWith("RSC" + ymd);
        assertThat(id).hasSize(3 + 8 + 6);
        assertThat(id.substring(11)).matches("\\d{6}");
    }

    @Test
    void next_padsAndTrimsPrefix() {
        assertThat(ids.next("AB").substring(0, 3)).isEqualTo("ABX");
        assertThat(ids.next("PRODUCT").substring(0, 3)).isEqualTo("PRO");
    }

    @Test
    void helpers_useExpectedPrefixes() {
        assertThat(ids.resourceId()).startsWith("RSC");
        assertThat(ids.productId()).startsWith("PRD");
        assertThat(ids.versionId()).startsWith("VER");
        assertThat(ids.lifecycleId()).startsWith("LFC");
    }
}
