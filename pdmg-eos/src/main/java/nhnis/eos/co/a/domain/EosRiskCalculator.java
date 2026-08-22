package nhnis.eos.co.a.domain;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 위험 총점·등급 서버 계산 (RULE-031/032). 임계값 POLICY 또는 샘플 기본값.
 */
@Component
public class EosRiskCalculator {

    public int total(int biz, int env, int exp, int sec, int imp, int alt, int eos) {
        return biz + env + exp + sec + imp + alt + eos;
    }

    public void validateScore(int s) {
        if (s < 1 || s > 5) {
            throw new IllegalArgumentException("score must be 1..5");
        }
    }

    public String grade(int total, int criticalMin, int highMin, int mediumMin) {
        if (total >= criticalMin) return "CRITICAL";
        if (total >= highMin) return "HIGH";
        if (total >= mediumMin) return "MEDIUM";
        return "LOW";
    }

    public int parseBand(Map<String, String> bands, String key, int def) {
        if (bands == null || !bands.containsKey(key)) return def;
        try {
            return Integer.parseInt(bands.get(key).trim());
        } catch (Exception e) {
            return def;
        }
    }
}
