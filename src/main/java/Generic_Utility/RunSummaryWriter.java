package Generic_Utility;

import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Writes a human-readable run_summary.txt after each scenario completes.
 * Call startScenario() in @Before and recordScenario() in @After.
 *
 * Output: test-output/{timestamp}/run_summary.txt
 *
 *   Run folder : test-output/2026-05-23_10-30-00
 *   Started    : 2026-05-23 10:30:00
 *   ----------------------------------------------------------
 *   PASSED  [  3m 21s]  AWS_TC1 - Normal Plan - Single Lab request - Account
 *   FAILED  [  5m 12s]  GCP_TC4 - Plan with configured duration...
 */
public class RunSummaryWriter {

    private static final Logger log = LoggerFactory.getLogger(RunSummaryWriter.class);

    private static final ThreadLocal<Long> TL_START = new ThreadLocal<>();

    private static final AtomicBoolean headerWritten = new AtomicBoolean(false);
    private static final AtomicInteger totalCount    = new AtomicInteger(0);
    private static final AtomicInteger passedCount   = new AtomicInteger(0);
    private static final AtomicInteger failedCount   = new AtomicInteger(0);

    private RunSummaryWriter() {}

    /** Called in @Before — records the start time for this scenario thread. */
    public static void startScenario() {
        TL_START.set(System.currentTimeMillis());
    }

    /**
     * Called in @After — appends one line to run_summary.txt.
     * Thread-safe: synchronized on file write block.
     */
    public static void recordScenario(Scenario scenario) {
        Long start = TL_START.get();
        long durationMs = (start != null) ? System.currentTimeMillis() - start : 0;
        TL_START.remove();

        String status = scenario.isFailed() ? "FAILED" : "PASSED";
        totalCount.incrementAndGet();
        if (scenario.isFailed()) failedCount.incrementAndGet();
        else                     passedCount.incrementAndGet();

        String durationStr = formatDuration(durationMs);
        String line = String.format("%-6s  [%8s]  %s%n", status, durationStr, scenario.getName());

        String folder = ExecutionContext.getRunFolder();
        if (folder == null) return;

        Path summaryPath = Paths.get(folder, "run_summary.txt");

        synchronized (RunSummaryWriter.class) {
            try {
                Files.createDirectories(summaryPath.getParent());

                if (headerWritten.compareAndSet(false, true)) {
                    String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    String header = String.format(
                            "Run folder : %s%nStarted    : %s%n%s%n",
                            folder,
                            ts,
                            "-".repeat(66));
                    try (BufferedWriter w = Files.newBufferedWriter(summaryPath,
                            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                        w.write(header);
                    }
                }

                try (BufferedWriter w = Files.newBufferedWriter(summaryPath,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    w.write(line);
                }
            } catch (Exception e) {
                log.warn("run_summary.txt write failed: {}", e.getMessage());
            }
        }
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private static String formatDuration(long ms) {
        long totalSec = ms / 1000;
        long min      = totalSec / 60;
        long sec      = totalSec % 60;
        return min > 0
                ? String.format("%dm %02ds", min, sec)
                : String.format("%ds", sec);
    }
}
