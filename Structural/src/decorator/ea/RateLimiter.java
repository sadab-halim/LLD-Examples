package decorator.ea;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> resetTimes = new ConcurrentHashMap<>();
    private final int maxRequests;
    private final Duration window;

    public RateLimiter(int maxRequests, Duration window) {
        this.maxRequests = maxRequests;
        this.window = window;
    }

    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();
        long resetTime = resetTimes.getOrDefault(userId, now);

        if (now >= resetTime) {
            requestCounts.put(userId, new AtomicInteger(0));
            resetTimes.put(userId, now + window.toMillis());
        }

        AtomicInteger count = requestCounts.computeIfAbsent(
                userId, k -> new AtomicInteger(0)
        );

        return count.incrementAndGet() <= maxRequests;
    }

    public int getRemainingQuota(String userId) {
        int current = requestCounts.getOrDefault(userId, new AtomicInteger(0)).get();
        return Math.max(0, maxRequests - current);
    }

    public long getResetTime(String userId) {
        return resetTimes.getOrDefault(userId, System.currentTimeMillis());
    }
}
