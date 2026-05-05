package Generic_Utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HealingReportLogger {

    private static final Logger log = LoggerFactory.getLogger(HealingReportLogger.class);
    
    private String customReportFolder;
    
    public void setReportFolder(String folder) {
        this.customReportFolder = folder;
    }
    
    public void saveHealingReport(List<SelfHealingLocator.HealingRecord> records) {
        if (records == null || records.isEmpty()) {
            return;
        }
        
        try {
            String reportFolder = customReportFolder;
            if (reportFolder == null || reportFolder.isEmpty()) {
                reportFolder = "./test-output/default/reports";
            }
            
            Files.createDirectories(Paths.get(reportFolder));
            
            String reportPath = reportFolder + "/healing_report.json";
            
            List<SelfHealingLocator.HealingRecord> existingRecords = loadExistingRecords(reportPath);
            existingRecords.addAll(records);
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(existingRecords);
            
            try (FileWriter writer = new FileWriter(reportPath)) {
                writer.write(json);
            }
            
            log.info("Saved {} healing records to {}", records.size(), reportPath);
            
        } catch (IOException e) {
            log.error("Failed to save healing report", e);
        }
    }
    
    private List<SelfHealingLocator.HealingRecord> loadExistingRecords(String reportPath) {
        if (!Files.exists(Paths.get(reportPath))) {
            return new ArrayList<>();
        }
        
        try (FileReader reader = new FileReader(reportPath)) {
            Gson gson = new Gson();
            java.lang.reflect.Type listType = new TypeToken<ArrayList<SelfHealingLocator.HealingRecord>>(){}.getType();
            List<SelfHealingLocator.HealingRecord> records = gson.fromJson(reader, listType);
            return records != null ? records : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}