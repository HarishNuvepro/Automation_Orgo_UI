package Generic_Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Single source of truth for all credentials.
 *
 * Priority for every key:
 *   1. OS / CI environment variable   → System.getenv("KEY")
 *   2. .env file in project root      → DotEnvLoader.get("KEY")
 *   3. Excel fallback                 → ExcelUtility.getDataFromExcel(...)
 *
 * Environment variables / .env keys:
 *   BASE_URL, BROWSER_TYPE,
 *   MSP_ADMIN_USERNAME, MSP_ADMIN_PASSWORD,
 *   TENANT_ADMIN_USERNAME, TENANT_ADMIN_PASSWORD,
 *   USER_USERNAME, USER_PASSWORD,
 *   GEMINI_API_KEY
 */
public final class CredentialManager {

    private static final Logger log = LoggerFactory.getLogger(CredentialManager.class);

    private static ExcelUtility excel;

    private CredentialManager() {}

    /** Called once by TestDataManager.initialize() before any credential is read. */
    public static void setExcelFallback(ExcelUtility eLib) {
        excel = eLib;
    }

    // ── URL / Browser ─────────────────────────────────────────────────────────

    public static String getBaseUrl() {
        return resolve("BASE_URL", "Credentials", 3, 1);
    }

    public static String getBrowser() {
        String value = fromEnvOrDotEnv("BROWSER_TYPE");
        return (value != null) ? value : TestDataManager.getCredential("browser");
    }

    // ── MSP Admin ────────────────────────────────────────────────────────────

    public static String getMspAdminUsername() {
        return resolve("MSP_ADMIN_USERNAME", "Credentials", 4, 1);
    }

    public static String getMspAdminPassword() {
        return resolve("MSP_ADMIN_PASSWORD", "Credentials", 5, 1);
    }

    // ── System Admin ─────────────────────────────────────────────────────────

    public static String getSysAdminUsername() {
        return resolve("SYS_ADMIN_USERNAME", "Credentials", 6, 1);
    }

    public static String getSysAdminPassword() {
        return resolve("SYS_ADMIN_PASSWORD", "Credentials", 7, 1);
    }

    // ── Tenant Admin ─────────────────────────────────────────────────────────

    public static String getTenantAdminUsername() {
        return resolve("TENANT_ADMIN_USERNAME", "Credentials", 10, 1);
    }

    public static String getTenantAdminPassword() {
        return resolve("TENANT_ADMIN_PASSWORD", "Credentials", 11, 1);
    }

    // ── Regular User ─────────────────────────────────────────────────────────

    public static String getUserUsername() {
        return resolve("USER_USERNAME", "Credentials", 8, 1);
    }

    public static String getUserPassword() {
        return resolve("USER_PASSWORD", "Credentials", 9, 1);
    }

    // ── Negative-test credentials (non-sensitive — stored in credentials.json) ─

    public static String getInvalidPassword() {
        return TestDataManager.getCredential("invalidPassword");
    }

    public static String getInvalidUsername() {
        return TestDataManager.getCredential("invalidUsername");
    }

    // ── Gemini API Key ────────────────────────────────────────────────────────

    public static String getGeminiApiKey() {
        String value = fromEnvOrDotEnv("GEMINI_API_KEY");
        if (value != null) return value;
        log.warn("GEMINI_API_KEY not found in env or .env — using fallback from ConstantFilePath");
        return ConstantFilePath.geminiApiKey;
    }

    // ── Internal helpers ──────────────────────────────────────────────────────

    /**
     * Resolution order: OS env var → .env file → Excel.
     */
    private static String resolve(String envKey, String sheet, int row, int col) {
        String value = fromEnvOrDotEnv(envKey);
        if (value != null) return value;

        log.debug("'{}' not in env or .env — reading from Excel", envKey);
        try {
            return excel.getDataFromExcel(sheet, row, col);
        } catch (Throwable t) {
            log.error("Failed to read credential '{}' from Excel", envKey, t);
            throw new RuntimeException("Credential '" + envKey + "' not configured", t);
        }
    }

    /**
     * Checks OS env var first, then .env file. Returns null if not found in either.
     */
    private static String fromEnvOrDotEnv(String key) {
        String env = System.getenv(key);
        if (env != null && !env.isBlank()) {
            log.debug("'{}' loaded from OS environment variable", key);
            return env;
        }
        String dot = DotEnvLoader.get(key);
        if (dot != null && !dot.isBlank()) {
            log.debug("'{}' loaded from .env file", key);
            return dot;
        }
        return null;
    }
}
