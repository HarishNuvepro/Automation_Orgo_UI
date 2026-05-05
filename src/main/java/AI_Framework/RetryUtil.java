package AI_Framework;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryUtil {

    private static final Logger log = LoggerFactory.getLogger(RetryUtil.class);

    private static final int DEFAULT_MAX_RETRIES = 2;
    private static final long DEFAULT_DELAY_MS = 1000;

    private int maxRetries;
    private long delayMs;

    public RetryUtil() {
        this(DEFAULT_MAX_RETRIES, DEFAULT_DELAY_MS);
    }

    public RetryUtil(int maxRetries, long delayMs) {
        this.maxRetries = maxRetries;
        this.delayMs = delayMs;
    }

    public <T> T execute(Supplier<T> action, String operationName) {
        int attempts = 0;
        Exception lastException = null;

        while (attempts <= maxRetries) {
            try {
                return action.get();
            } catch (Exception e) {
                attempts++;
                lastException = e;
                log.warn("Attempt {} failed for '{}': {}", attempts, operationName, e.getMessage());

                if (attempts <= maxRetries) {
                    sleep(delayMs);
                }
            }
        }

        throw new RuntimeException("Retry failed after " + maxRetries + " attempts for '" + operationName + "'",
            lastException);
    }

    public void executeVoid(Runnable action, String operationName) {
        execute(() -> {
            action.run();
            return null;
        }, operationName);
    }

    public <T> T executeWithFixedRetry(Supplier<T> action, String operationName, int fixedRetries) {
        int attempts = 0;
        Exception lastException = null;

        while (attempts < fixedRetries) {
            try {
                return action.get();
            } catch (Exception e) {
                attempts++;
                lastException = e;
                log.warn("Attempt {}/{} failed for '{}'", attempts, fixedRetries, operationName);

                if (attempts < fixedRetries) {
                    sleep(delayMs);
                }
            }
        }

        throw new RuntimeException("Retry failed after " + fixedRetries + " attempts for '" + operationName + "'",
            lastException);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(long delayMs) {
        this.delayMs = delayMs;
    }
}