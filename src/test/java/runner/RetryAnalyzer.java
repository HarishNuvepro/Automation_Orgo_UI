package runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries a failed test scenario up to MAX_RETRY_COUNT times.
 *
 * Applied automatically to every @Test method via RetryListener —
 * no per-runner annotation change is needed.
 *
 * ThreadLocal counter ensures parallel scenarios don't share retry state.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);

    public static final int MAX_RETRY_COUNT = 1;

    private final ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);

    @Override
    public boolean retry(ITestResult result) {
        int count = retryCount.get();
        if (count < MAX_RETRY_COUNT) {
            retryCount.set(count + 1);
            log.warn("RETRY {}/{} — scenario: {}", count + 1, MAX_RETRY_COUNT,
                    result.getName());
            return true;
        }
        retryCount.remove();
        return false;
    }
}
