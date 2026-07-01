package Generic_Utility;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Buffers all log events per scenario thread and flushes them to
 * passed.log or failed.log at the end of each scenario (@After hook).
 *
 * This appender is registered in logback.xml and needs no per-step changes.
 * Hook.java calls startScenario() in @Before and flushScenario() in @After.
 */
public class ScenarioBufferAppender extends AppenderBase<ILoggingEvent> {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    // thread → buffered log lines for the currently running scenario
    private static final ConcurrentHashMap<Thread, List<String>> BUFFERS = new ConcurrentHashMap<>();

    // Set by ExecutionContext once the run folder is created
    private static volatile String logsDir = "test-output/_latest/logs";

    public static void setLogsDir(String dir) {
        logsDir = dir;
    }

    /** Called in @Before — opens a fresh buffer for this thread. */
    public static void startScenario() {
        BUFFERS.put(Thread.currentThread(), new ArrayList<>());
    }

    /**
     * Called in @After — appends the buffered lines as a single block to
     * either passed.log or failed.log, then clears the buffer.
     */
    public static void flushScenario(String scenarioName, boolean passed) {
        List<String> lines = BUFFERS.remove(Thread.currentThread());
        if (lines == null || lines.isEmpty()) return;

        String fileName = passed ? "passed.log" : "failed.log";
        Path outPath = Paths.get(logsDir, fileName);

        try {
            Files.createDirectories(Paths.get(logsDir));
            try (BufferedWriter w = Files.newBufferedWriter(outPath,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                String sep = "=".repeat(100);
                w.write(sep);          w.newLine();
                w.write("SCENARIO : " + scenarioName); w.newLine();
                w.write("STATUS   : " + (passed ? "PASSED" : "FAILED")); w.newLine();
                w.write(sep);          w.newLine();
                for (String line : lines) w.write(line);
                w.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ScenarioBufferAppender] Failed to write "
                    + outPath + " : " + e.getMessage());
        }
    }

    // ── Logback AppenderBase ────────────────────────────────────────────────────

    @Override
    protected void append(ILoggingEvent event) {
        List<String> buf = BUFFERS.get(Thread.currentThread());
        if (buf == null) return; // no active scenario on this thread

        String loggerShort = event.getLoggerName();
        int dot = loggerShort.lastIndexOf('.');
        if (dot >= 0) loggerShort = loggerShort.substring(dot + 1);

        buf.add(String.format("%s [%s] %-5s %-30s - %s%n",
                FMT.format(Instant.ofEpochMilli(event.getTimeStamp())),
                event.getThreadName(),
                event.getLevel(),
                loggerShort,
                event.getFormattedMessage()));
    }
}
