package stepDefinitions;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Accumulates lab IDs across scenarios so AWS_TC50 / GCP_TC50 can delete them all in one shot.
 * Static (not ThreadLocal) — intentionally shared across all scenarios in a run.
 */
public class LabCleanupRegistry {

    private static final List<String> labIds = new CopyOnWriteArrayList<>();

    private LabCleanupRegistry() {}

    public static void register(String labId) {
        if (labId != null && !labId.trim().isEmpty()) {
            labIds.add(labId.trim());
        }
    }

    public static List<String> getAll() {
        return Collections.unmodifiableList(labIds);
    }

    public static void clear() {
        labIds.clear();
    }
}
