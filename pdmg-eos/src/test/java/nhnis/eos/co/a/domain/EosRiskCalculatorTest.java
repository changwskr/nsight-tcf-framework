package nhnis.eos.co.a.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;

import org.junit.jupiter.api.Test;

class EosRiskCalculatorTest {

    private final EosRiskCalculator calc = new EosRiskCalculator();

    @Test
    void total_sumsSevenItems() {
        assertThat(calc.total(4, 4, 3, 4, 4, 3, 5)).isEqualTo(27);
    }

    @Test
    void grade_usesThresholdBands() {
        assertThat(calc.grade(32, 32, 26, 20)).isEqualTo("CRITICAL");
        assertThat(calc.grade(26, 32, 26, 20)).isEqualTo("HIGH");
        assertThat(calc.grade(20, 32, 26, 20)).isEqualTo("MEDIUM");
        assertThat(calc.grade(19, 32, 26, 20)).isEqualTo("LOW");
    }

    @Test
    void validateScore_rejectsOutOfRange() {
        assertThatThrownBy(() -> calc.validateScore(0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> calc.validateScore(6))
                .isInstanceOf(IllegalArgumentException.class);
        calc.validateScore(1);
        calc.validateScore(5);
    }

    @Test
    void parseBand_fallsBackToDefault() {
        assertThat(calc.parseBand(null, "CRITICAL_MIN", 32)).isEqualTo(32);
        assertThat(calc.parseBand(Map.of("CRITICAL_MIN", "30"), "CRITICAL_MIN", 32)).isEqualTo(30);
        assertThat(calc.parseBand(Map.of("CRITICAL_MIN", "x"), "CRITICAL_MIN", 32)).isEqualTo(32);
    }
}
