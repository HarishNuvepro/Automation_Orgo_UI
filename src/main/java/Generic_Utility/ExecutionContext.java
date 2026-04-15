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

public class ExecutionContext {

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
                        System.out.println("[ExecutionContext] Could not parse folder date: " + folderName);
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
                System.out.println("[ExecutionContext] Deleted old folder: " + folders.get(i).getFileName());
            }
        } catch (Exception e) {
            System.err.println("[ExecutionContext] Error cleaning old folders: " + e.getMessage());
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
            System.out.println("[ExecutionContext] Created execution folder: " + runFolder);
        } catch (IOException e) {
            System.err.println("[ExecutionContext] Failed to create execution folder: " + e.getMessage());
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