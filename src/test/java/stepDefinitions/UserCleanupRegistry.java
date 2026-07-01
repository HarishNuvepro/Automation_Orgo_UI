package stepDefinitions;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Accumulates user login IDs across scenarios so TC_CLEANUP can remove them all in one pass.
 * Static (not ThreadLocal) — intentionally shared across all scenarios in a run.
 */
public class UserCleanupRegistry {

    private static final List<String> loginIds = new CopyOnWriteArrayList<>();

    private UserCleanupRegistry() {}

    public static void register(String loginId) {
        if (loginId != null && !loginId.trim().isEmpty()) {
            loginIds.add(loginId.trim());
        }
    }

    public static List<String> getAll() {
        return Collections.unmodifiableList(loginIds);
    }

    public static void clear() {
        loginIds.clear();
    }
}
