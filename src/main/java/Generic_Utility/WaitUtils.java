package Generic_Utility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BooleanSupplier;

/**
 * Centralized wait strategy for the test framework.
 *
 * Why this exists:
 *   - Named constants replace magic numbers (500, 1000, 3000 …) scattered across
 *     step definitions, making tuning a one-place change.
 *   - pause() handles InterruptedException correctly (restores interrupt flag)
 *     instead of the unchecked swallow common with raw Thread.sleep.
 *   - Playwright-aware helpers (waitForNetworkIdle, waitForVisible, waitForCondition)
 *     replace hand-rolled polling loops with a single tested implementation.
 */
public final class WaitUtils {

    private static final Logger log = LoggerFactory.getLogger(WaitUtils.class);

    // ── Named duration constants (ms) ────────────────────────────────────────

    /** Post-click / tab-switch / animation settle */
    public static final int SHORT        =   500;
    /** Page transition / fast AJAX */
    public static final int MEDIUM       =  1000;
    /** Slower AJAX / modal open */
    public static final int LONG         =  2000;
    /** Multi-step server operation */
    public static final int EXTRA_LONG   =  3000;
    /** Heavy operations / file upload confirmation */
    public static final int FIVE_SECONDS =  5000;
    /** Sync / batch-refresh polling interval */
    public static final int TEN_SECONDS  = 10000;
    /** Lab launch / sequential login redirect */
    public static final int THIRTY_SEC   = 30000;

    // ── Element / page timeout budgets (ms) ──────────────────────────────────

    /** Default timeout for locator.waitFor() in healing logic */
    public static final int ELEMENT_TIMEOUT  = 10000;
    /** Fallback locator probe timeout */
    public static final int FALLBACK_TIMEOUT =  5000;
    /** Network-idle probe budget */
    public static final int NETWORK_IDLE_TIMEOUT = 15000;

    private WaitUtils() {}

    // ── Core pause ───────────────────────────────────────────────────────────

    /**
     * Named, interrupt-safe replacement for Thread.sleep.
     * Pass a WaitUtils constant (e.g. {@code WaitUtils.MEDIUM}) instead of a
     * raw millisecond literal.
     */
    public static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("pause({} ms) interrupted", ms);
        }
    }

    // ── Playwright-aware waits ────────────────────────────────────────────────

    /**
     * Waits for the page to reach LOAD state (DOM + resources).
     * Direct replacement for {@code page.waitForLoadState()}.
     */
    public static void waitForPageReady(Page page) {
        page.waitForLoadState();
    }

    /**
     * Waits for all in-flight network requests to finish.
     * Use after triggering background AJAX / API calls.
     * Times out silently after {@value #NETWORK_IDLE_TIMEOUT} ms.
     */
    public static void waitForNetworkIdle(Page page) {
        try {
            page.waitForLoadState(LoadState.NETWORKIDLE,
                    new Page.WaitForLoadStateOptions().setTimeout(NETWORK_IDLE_TIMEOUT));
        } catch (Exception e) {
            log.warn("waitForNetworkIdle timed out after {} ms — continuing", NETWORK_IDLE_TIMEOUT);
        }
    }

    /**
     * Waits for a locator to become visible within the given timeout.
     */
    public static void waitForVisible(Locator locator, int timeoutMs) {
        locator.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs));
    }

    /**
     * Waits for a locator to be hidden / detached within the given timeout.
     */
    public static void waitForHidden(Locator locator, int timeoutMs) {
        locator.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMs));
    }

    /**
     * Polls {@code condition} every {@code intervalMs} until it returns true
     * or {@code timeoutMs} elapses.
     *
     * Replaces hand-rolled for/retry loops in step definitions:
     * <pre>
     *   boolean done = WaitUtils.waitForCondition(
     *       () -> status.contains("Complete"),
     *       300_000, 5_000, "lab deployment complete");
     * </pre>
     *
     * @return true if condition was satisfied, false if timed out
     */
    public static boolean waitForCondition(BooleanSupplier condition,
                                           int timeoutMs, int intervalMs,
                                           String description) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            try {
                if (condition.getAsBoolean()) {
                    log.debug("Condition '{}' satisfied", description);
                    return true;
                }
            } catch (Exception e) {
                log.debug("Condition '{}' check threw: {}", description, e.getMessage());
            }
            pause(intervalMs);
        }
        log.warn("Condition '{}' not met within {} ms", description, timeoutMs);
        return false;
    }
}
