package decorator.eb;

import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreaker {
    private enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final long openDurationMs;
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private final AtomicInteger successCount = new AtomicInteger(0);
    private volatile State state = State.CLOSED;
    private volatile long openedAt = 0;

    public CircuitBreaker(int failureThreshold, long openDurationMs) {
        this.failureThreshold = failureThreshold;
        this.openDurationMs = openDurationMs;
    }

    public boolean isOpen() {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - openedAt >= openDurationMs) {
                state = State.HALF_OPEN;
                return false;
            }
            return true;
        }
        return false;
    }

    public void recordSuccess() {
        failureCount.set(0);
        if (state == State.HALF_OPEN) {
            if (successCount.incrementAndGet() >= 3) {
                state = State.CLOSED;
                successCount.set(0);
            }
        }
    }

    public void recordFailure() {
        successCount.set(0);
        if (failureCount.incrementAndGet() >= failureThreshold) {
            state = State.OPEN;
            openedAt = System.currentTimeMillis();
        }
    }
}
