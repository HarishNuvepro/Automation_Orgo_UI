package SmartFlow.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Writes a Gemini-generated Cucumber feature file into
 * src/test/java/FeatureFiles/generated/
 *
 * File name pattern: <module>_<yyyyMMdd_HHmmss>.feature
 */
public class FeatureFileWriter {

    private static final Logger log = LoggerFactory.getLogger(FeatureFileWriter.class);

    private static final String OUTPUT_DIR =
            "src/test/java/FeatureFiles/generated";

    public Path write(String featureContent, String moduleName) throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_DIR));

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename  = moduleName.toLowerCase().replace(" ", "_")
                           + "_" + timestamp + ".feature";

        Path filePath = Paths.get(OUTPUT_DIR, filename);
        Files.writeString(filePath, featureContent);

        log.info("[SmartFlow] Feature file written → {}", filePath.toAbsolutePath());
        return filePath;
    }
}
