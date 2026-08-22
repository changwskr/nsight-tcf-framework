package nhnis.eos.co.a.support;

import java.util.LinkedHashMap;
import java.util.Map;

public final class EosResults {
    private EosResults() {}

    public static Map<String, Object> ok(Map<String, Object> data) {
        Map<String, Object> m = data == null ? new LinkedHashMap<>() : new LinkedHashMap<>(data);
        m.put("RSLT_CD", "0000");
        m.put("RSLT_MSG", "OK");
        return m;
    }

    public static Map<String, Object> fail(String code, String msg) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("RSLT_CD", code);
        m.put("RSLT_MSG", msg);
        return m;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> asMap(Object dtoBody) {
        if (dtoBody == null) return new LinkedHashMap<>();
        if (dtoBody instanceof Map<?, ?> map) {
            return new LinkedHashMap<>((Map<String, Object>) map);
        }
        return new LinkedHashMap<>();
    }

    public static String str(Map<String, Object> m, String key) {
        if (m == null) return null;
        Object v = m.get(key);
        if (v == null) {
            for (Map.Entry<String, Object> e : m.entrySet()) {
                if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) {
                    v = e.getValue();
                    break;
                }
            }
        }
        if (v == null) return null;
        String s = String.valueOf(v).trim();
        return s.isEmpty() ? null : s;
    }

    public static int intVal(Map<String, Object> m, String key, int def) {
        String s = str(m, key);
        if (s == null) return def;
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
