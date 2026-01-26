package decorator.eb;

import java.util.Set;

public class RetryPolicy {
    private final int maxAttempts;
    private final long baseBackoffMs;
    private final Set<Integer> retryableStatusCodes = Set.of(429, 500, 502, 503, 504);

    public RetryPolicy(int maxAttempts, long baseBackoffMs) {
        this.maxAttempts = maxAttempts;
        this.baseBackoffMs = baseBackoffMs;
    }

    public int getMaxAttempts() { return maxAttempts; }

    public boolean shouldRetry(ServiceResponse response) {
        return retryableStatusCodes.contains(response.getStatusCode());
    }

    public boolean isRetryableException(Exception e) {
        return e instanceof ServiceException;
    }

    public long getBackoffMs(int attempt) {
        // Exponential backoff: base * 2^(attempt-1)
        return baseBackoffMs * (1L << (attempt - 1));
    }
}
