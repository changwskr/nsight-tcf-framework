package nhnis.eos.co.a.domain;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class EosActionStateMachine {

    private static final Map<String, Set<String>> ALLOWED = Map.of(
            "NOT_STARTED", Set.of("PLANNED", "HOLD", "EXC_MGMT"),
            "PLANNED", Set.of("IN_PROGRESS", "HOLD", "EXC_MGMT"),
            "IN_PROGRESS", Set.of("TESTING", "HOLD", "EXC_MGMT"),
            "TESTING", Set.of("DONE", "IN_PROGRESS", "HOLD"),
            "HOLD", Set.of("PLANNED", "IN_PROGRESS", "EXC_MGMT"),
            "EXC_MGMT", Set.of("PLANNED", "HOLD")
    );

    /** DONE only via 0165 verify */
    public boolean canTransit(String from, String to) {
        if (from == null || to == null) return false;
        if (from.equals(to)) return true;
        if ("DONE".equals(to) && !"TESTING".equals(from)) return false;
        if ("DONE".equals(to)) return false; // must use 0160U2 + 0165
        Set<String> next = ALLOWED.get(from);
        return next != null && next.contains(to);
    }

    public boolean canRequestComplete(String from) {
        return "TESTING".equals(from);
    }
}
