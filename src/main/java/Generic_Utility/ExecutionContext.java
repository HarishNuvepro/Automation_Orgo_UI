package Generic_Utility;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class ExecutionContext {

    private static final Logger log = LoggerFactory.getLogger(ExecutionContext.class);

    private static final String TEST_OUTPUT_BASE = "./test-output";
    private static final int MAX_FOLDERS_TO_KEEP = 7;
    private static volatile String runFolder;
    private static volatile String screenshotFolder;
    private static volatile String reportFolder;
    private static volatile String logsFolder;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final Object lock = new Object();

    private ExecutionContext() {
    }

    public static void initialize() {
        if (initialized.get()) {
            return;
        }
        synchronized (lock) {
            if (initialized.get()) {
                return;
            }
            cleanOldFolders();
            createExecutionFolder();
            initialized.set(true);
        }
    }

    private static void cleanOldFolders() {
        try {
            Path baseDir = Paths.get(TEST_OUTPUT_BASE);
            if (!Files.exists(baseDir)) {
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            List<Path> folders = new ArrayList<>();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(baseDir)) {
                for (Path dir : stream) {
                    if (!Files.isDirectory(dir)) {
                        continue;
                    }
                    String folderName = dir.getFileName().toString();
                    if (!folderName.matches("\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}")) {
                        continue;
                    }
                    try {
                        sdf.parse(folderName);
                        folders.add(dir);
                    } catch (Exception e) {
                        log.warn("Could not parse folder date: {}", folderName);
                    }
                }
            }

            if (folders.size() <= MAX_FOLDERS_TO_KEEP) {
                return;
            }

            Collections.sort(folders, (p1, p2) -> {
                try {
                    return p1.getFileName().toString().compareTo(p2.getFileName().toString());
                } catch (Exception e) {
                    return 0;
                }
            });

            int toDelete = folders.size() - MAX_FOLDERS_TO_KEEP;
            for (int i = 0; i < toDelete; i++) {
                deleteDirectory(folders.get(i));
                log.info("Deleted old run folder: {}", folders.get(i).getFileName());
            }
        } catch (Exception e) {
            log.error("Error cleaning old folders", e);
        }
    }

    private static void deleteDirectory(Path dir) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    deleteDirectory(file);
                } else {
                    Files.delete(file);
                }
            }
        }
        Files.delete(dir);
    }

    private static void createExecutionFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = sdf.format(new Date());
        runFolder = TEST_OUTPUT_BASE + "/" + timestamp;
        screenshotFolder = runFolder + "/screenshots";
        reportFolder = runFolder + "/reports";
        logsFolder = runFolder + "/logs";

        try {
            Files.createDirectories(Paths.get(screenshotFolder));
            Files.createDirectories(Paths.get(reportFolder));
            Files.createDirectories(Paths.get(logsFolder));
            reconfigureLogFile(logsFolder + "/test.log");
            log.info("Created execution folder: {}", runFolder);
            updateLatestLink(runFolder);
        } catch (IOException e) {
            log.error("Failed to create execution folder", e);
        }
    }

    // Creates/updates a _latest junction in test-output/ pointing to the current run.
    // _latest sorts before timestamp folders (underscore < digits), so it appears at the top.
    private static void updateLatestLink(String targetFolder) {
        try {
            Path latestLink  = Paths.get(TEST_OUTPUT_BASE, "_latest").toAbsolutePath();
            Path targetPath  = Paths.get(targetFolder).toAbsolutePath();

            // Remove existing junction with "rd" (safe — does NOT delete junction target contents)
            if (Files.exists(latestLink) || Files.isSymbolicLink(latestLink)) {
                new ProcessBuilder("cmd", "/c", "rd \"" + latestLink + "\"")
                        .start().waitFor();
            }

            // Create a directory junction: mklink /J "_latest" "<runFolder>"
            new ProcessBuilder("cmd", "/c",
                    "mklink /J \"" + latestLink + "\" \"" + targetPath + "\"")
                    .start().waitFor();

            log.info("_latest → {}", targetPath.getFileName());
        } catch (Exception e) {
            log.debug("Could not update _latest junction: {}", e.getMessage());
        }
    }

    private static void reconfigureLogFile(String newLogPath) {
        try {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            ch.qos.logback.classic.Logger root =
                    lc.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
            FileAppender<ILoggingEvent> fa =
                    (FileAppender<ILoggingEvent>) root.getAppender("FILE");
            if (fa != null) {
                fa.stop();
                fa.setFile(newLogPath);
                fa.start();
            }
        } catch (Exception e) {
            log.warn("Could not reconfigure log file: {}", e.getMessage());
        }
    }

    public static String getRunFolder() {
        initialize();
        return runFolder;
    }

    public static String getScreenshotFolder() {
        initialize();
        return screenshotFolder;
    }

    public static String getReportFolder() {
        initialize();
        return reportFolder;
    }

    public static String getLogsFolder() {
        initialize();
        return logsFolder;
    }

    public static boolean isInitialized() {
        return initialized.get();
    }

    public static void reset() {
        synchronized (lock) {
            initialized.set(false);
            runFolder = null;
            screenshotFolder = null;
            reportFolder = null;
            logsFolder = null;
        }
    }
}