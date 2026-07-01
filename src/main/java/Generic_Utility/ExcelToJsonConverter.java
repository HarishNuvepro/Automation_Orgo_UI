package Generic_Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reads each Excel sheet and writes a JSON file per sheet into testdata/.
 * Called once at suite startup — keeps JSON always in sync with Excel.
 */
public class ExcelToJsonConverter {

    private static final Logger log = LoggerFactory.getLogger(ExcelToJsonConverter.class);
    private static final Gson   GSON       = new GsonBuilder().setPrettyPrinting().create();
    static final         String OUTPUT_DIR = "src/test/resources/testdata";

    private final ExcelUtility excel;

    public ExcelToJsonConverter(ExcelUtility excel) {
        this.excel = excel;
    }

    public void convertAll() {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            convertLabSheet();
            convertUserSheet();
            convertCredentialsSheet();
            log.info("Excel → JSON complete. Output: {}", Paths.get(OUTPUT_DIR).toAbsolutePath());
        } catch (Throwable e) {
            log.error("Excel → JSON conversion failed", e);
            throw new RuntimeException("Failed to convert Excel to JSON", e);
        }
    }

    // ── Lab sheets — tabular: row 0 = optional header, remaining rows = test cases ──
    // Each platform sheet is loaded into the same map. TC_IDs must be unique across sheets
    // (AWS uses TC1–TC16; GCP uses GCP_TC1, GCP_TC2 …; CSP will use CSP_TC1 etc.)

    private void convertLabSheet() throws Throwable {
        String[] awsHeaders = {
            "TC_ID", "PlanType", "LabRequestType", "LabType", "TeamID",
            "PlanID", null, "PolicyName", "BatchName", "BatchDescription",
            "UserEmail", "UserName"
        };
        // GCP has one extra column: ForUserId (login ID of user to provision lab for)
        String[] gcpHeaders = {
            "TC_ID", "PlanType", "LabRequestType", "LabType", "TeamID",
            "PlanID", null, "PolicyName", "BatchName", "BatchDescription",
            "UserEmail", "UserName", "ForUserId"
        };

        Map<String, Map<String, String>> labData = new LinkedHashMap<>();
        loadLabSheetInto(labData, "Aws_Lab", awsHeaders);
        loadLabSheetInto(labData, "Gcp_Lab", gcpHeaders);

        writeJson("lab_data.json", labData);
        log.info("Lab sheets → lab_data.json ({} test cases)", labData.size());
    }

    private void loadLabSheetInto(Map<String, Map<String, String>> labData,
                                   String sheetName, String[] headers) throws Throwable {
        try {
            int rowCount = excel.getRowCount(sheetName);
            int loaded = 0;
            for (int i = 0; i <= rowCount; i++) {
                String tcId = excel.getDataFromExcel(sheetName, i, 0);
                if (tcId == null || tcId.isBlank() || tcId.equalsIgnoreCase("TC_ID")) continue;
                // AWS sheet: Excel still has TC1–TC16; prefix to AWS_TC1–AWS_TC16 to match feature tags
                if ("Aws_Lab".equals(sheetName) && !tcId.startsWith("AWS_")) {
                    tcId = "AWS_" + tcId;
                }
                Map<String, String> row = new LinkedHashMap<>();
                for (int col = 0; col < headers.length; col++) {
                    if (headers[col] != null) {
                        String value = excel.getDataFromExcel(sheetName, i, col);
                        row.put(headers[col], value != null ? value : "");
                    }
                }
                labData.put(tcId, row);
                loaded++;
            }
            log.info("{} sheet → {} test cases loaded", sheetName, loaded);
        } catch (Exception e) {
            log.warn("{} sheet not found or unreadable — skipping: {}", sheetName, e.getMessage());
        }
    }

    // ── User sheet — tabular TC rows + shared section below ──────────────────────
    //
    // Layout (0-indexed):
    //   Row 0      : title
    //   Row 1      : headers — col0=TC_ID, col1=Action, col3=firstName, col4=LastName,
    //                          col5=emailId, col6=employeeId, col7=LoginId,
    //                          col8=password, col9=confirmPassword
    //   Row 2      : TC01 — Create User
    //   Row 3      : TC02 — Edit User       (col3-6=update values, col7=target loginId, col8=new loginId)
    //   Row 4      : TC03 — Delete User     (col7 = loginId of user to delete)
    //   Row 5      : TC04 — Deactivate User (col7 = loginId)
    //   Row 6      : TC05 — Activate User   (col7 = loginId)
    //   Row 7      : TC06 — Change Password (col7 = loginId)
    //   Row 8      : TC07 — Add to Team     (col7 = loginId)
    //   Row 9      : TC08 — View Details    (col7 = loginId)
    //   Row 10     : TC09 — New Login       (col7 = loginId)
    //   Row 31     : TeamName (col1=value)
    //   Row 32     : UserRole (col1=value)

    private void convertUserSheet() throws Throwable {
        Map<String, String> userData = new LinkedHashMap<>();

        // ── TC01 Create User fields (row 2, tabular columns) ────────────────
        userData.put("FirstName",       excel.getDataFromExcel("User", 2, 3));
        userData.put("LastName",        excel.getDataFromExcel("User", 2, 4));
        userData.put("EmailId",         excel.getDataFromExcel("User", 2, 5));
        userData.put("EmployeeId",      excel.getDataFromExcel("User", 2, 6));
        userData.put("LoginId",         excel.getDataFromExcel("User", 2, 7));
        userData.put("Password",        excel.getDataFromExcel("User", 2, 8));
        userData.put("ConfirmPassword", excel.getDataFromExcel("User", 2, 9));

        // ── Action-specific LoginIds (col 7 of each TC row) ─────────────────
        userData.put("EditUserLoginId",       excel.getDataFromExcel("User", 4, 7));  // TC03
        userData.put("RemoveUserLoginId",     excel.getDataFromExcel("User", 5, 7));  // TC04
        userData.put("DeactivateUserLoginId", excel.getDataFromExcel("User", 5, 7));  // TC04
        userData.put("ChangePasswordLoginId", excel.getDataFromExcel("User", 7, 7));  // TC06
        userData.put("AddToTeamLoginId",      excel.getDataFromExcel("User", 9, 7));  // TC08
        userData.put("ViewUserLoginId",       excel.getDataFromExcel("User", 10, 7)); // TC09

        // ── TC10 Change Login ID row (row 11) — new loginId in col 7 ────────
        userData.put("Tc10NewLoginId",        excel.getDataFromExcel("User", 11, 7)); // TC10

        // ── TC13 Change User Role row (row 14) — new role in col 3 ──────────
        userData.put("Tc13NewRole",           excel.getDataFromExcel("User", 14, 2)); // TC13

        // ── TC14 Change Email ID row (row 15) — new email base in col 5 ─────
        userData.put("Tc14NewEmailId",        excel.getDataFromExcel("User", 15, 5)); // TC14

        // ── TC03 Edit User row (row 4) — update values read from same row ───
        // col3=UpdateFirstName, col4=UpdateLastName, col5=UpdateEmail, col6=UpdateEmployeeId
        userData.put("UpdateFirstName",  excel.getDataFromExcel("User", 4, 3));
        userData.put("UpdateLastName",   excel.getDataFromExcel("User", 4, 4));
        userData.put("UpdateEmail",      excel.getDataFromExcel("User", 4, 5));
        userData.put("UpdateEmployeeId", excel.getDataFromExcel("User", 4, 6));
        userData.put("UpdateLoginId",    excel.getDataFromExcel("User", 4, 8));
        userData.put("TeamName",         excel.getDataFromExcel("User", 9, 2));  // TC08 row, col 2
        userData.put("UserRole",         excel.getDataFromExcel("User", 32, 1));

        // ── TC30 old-password negative test (row 31) — new password in col O(14) ──
        userData.put("Tc30NewPassword", excel.getDataFromExcel("User", 31, 14));

        // ── Fields not yet in Excel — empty until added ──────────────────────
        userData.put("BillingAdminRole", "");
        userData.put("NewRole",          "");
        userData.put("NewLoginId",       "");
        userData.put("NewEmailId",       "");

        writeJson("user_data.json", userData);
        log.info("User sheet → user_data.json ({} fields)", userData.size());
    }

    // ── Credentials sheet — non-sensitive values only; secrets go to env vars ───

    private void convertCredentialsSheet() throws Throwable {
        Map<String, String> credData = new LinkedHashMap<>();
        try {
            credData.put("browser",         excel.getDataFromExcel("Credentials", 3, 3));
            credData.put("invalidPassword", excel.getDataFromExcel("Credentials", 12, 1));
            credData.put("invalidUsername", excel.getDataFromExcel("Credentials", 13, 1));
            log.info("Credentials sheet → credentials.json (browser + negative-test values)");
        } catch (Throwable e) {
            log.warn("Credentials sheet not found or unreadable — writing empty values. " +
                     "All credentials are loaded from the env file. Cause: {}", e.getMessage());
            credData.put("browser",         "");
            credData.put("invalidPassword", "");
            credData.put("invalidUsername", "");
        }
        writeJson("credentials.json", credData);
    }

    // ── internal ──────────────────────────────────────────────────────────────────

    private void writeJson(String filename, Object data) throws IOException {
        Path filePath = Paths.get(OUTPUT_DIR, filename);
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            GSON.toJson(data, writer);
        }
    }
}
