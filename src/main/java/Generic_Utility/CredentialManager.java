package Generic_Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Single source of truth for all credentials.
 *
 * Priority for every key:
 *   1. OS / CI environment variable   → System.getenv("KEY")
 *   2. .env file in project root      → DotEnvLoader.get("KEY")
 *
 * All credentials live in the active .env.<environment> file (see DotEnvLoader).
 * There is no Excel fallback — the Credentials sheet has been removed.
 *
 * Environment variables / .env keys:
 *   BASE_URL, BROWSER_TYPE,
 *   MSP_ADMIN_USERNAME, MSP_ADMIN_PASSWORD,
 *   SYS_ADMIN_USERNAME, SYS_ADMIN_PASSWORD,
 *   TENANT_ADMIN_USERNAME, TENANT_ADMIN_PASSWORD,
 *   GCP_TENANT_ADMIN_USERNAME, GCP_TENANT_ADMIN_PASSWORD,
 *   USER_USERNAME, USER_PASSWORD,
 *   INVALID_USERNAME, INVALID_PASSWORD,
 *   GEMINI_API_KEY
 */
public final class CredentialManager {

    private static final Logger log = LoggerFactory.getLogger(CredentialManager.class);

    private CredentialManager() {}

    // ── URL / Browser ─────────────────────────────────────────────────────────

    public static String getBaseUrl() {
        return resolve("BASE_URL");
    }

    public static String getBrowser() {
        String value = fromEnvOrDotEnv("BROWSER_TYPE");
        return (value != null) ? value : "Chrome";
    }

    // ── MSP Admin ────────────────────────────────────────────────────────────

    public static String getMspAdminUsername() {
        return resolve("MSP_ADMIN_USERNAME");
    }

    public static String getMspAdminPassword() {
        return resolve("MSP_ADMIN_PASSWORD");
    }

    // ── System Admin ─────────────────────────────────────────────────────────

    public static String getSysAdminUsername() {
        return resolve("SYS_ADMIN_USERNAME");
    }

    public static String getSysAdminPassword() {
        return resolve("SYS_ADMIN_PASSWORD");
    }

    // ── Tenant Admin ─────────────────────────────────────────────────────────

    public static String getTenantAdminUsername() {
        return resolve("TENANT_ADMIN_USERNAME");
    }

    public static String getTenantAdminPassword() {
        return resolve("TENANT_ADMIN_PASSWORD");
    }

    // ── GCP Tenant Admin ─────────────────────────────────────────────────────

    public static String getGcpTenantAdminUsername() {
        return resolve("GCP_TENANT_ADMIN_USERNAME");
    }

    public static String getGcpTenantAdminPassword() {
        return resolve("GCP_TENANT_ADMIN_PASSWORD");
    }

    // ── Regular User ─────────────────────────────────────────────────────────

    public static String getUserUsername() {
        return resolve("USER_USERNAME");
    }

    public static String getUserPassword() {
        return resolve("USER_PASSWORD");
    }

    // ── Negative-test credentials ───────────────────────────────────────────

    public static String getInvalidPassword() {
        return resolve("INVALID_PASSWORD");
    }

    public static String getInvalidUsername() {
        return resolve("INVALID_USERNAME");
    }

    // ── Gemini API Key ────────────────────────────────────────────────────────

    public static String getGeminiApiKey() {
        String value = fromEnvOrDotEnv("GEMINI_API_KEY");
        if (value != null) return value;
        log.warn("GEMINI_API_KEY not found in env or .env — using fallback from ConstantFilePath");
        return ConstantFilePath.geminiApiKey;
    }

    // ── Public helpers ────────────────────────────────────────────────────────

    /**
     * Optional lookup used to resolve ${KEY} placeholders in Excel data cells.
     * Resolution order: OS env var → active .env file. Returns null if the key is
     * defined in neither (caller decides how to handle a missing key).
     */
    public static String lookup(String key) {
        return fromEnvOrDotEnv(key);
    }

    // ── Internal helpers ──────────────────────────────────────────────────────

    /**
     * Resolution order: OS env var → .env file. Throws if neither has the key.
     */
    private static String resolve(String envKey) {
        String value = fromEnvOrDotEnv(envKey);
        if (value != null) return value;

        log.error("Credential '{}' not found in OS env or .env file", envKey);
        throw new RuntimeException("Credential '" + envKey + "' not configured — "
                + "add it to the active .env.<environment> file");
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
