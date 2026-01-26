package decorator.eb;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsCollector {
    private final Map<String, List<Long>> latencies = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> statusCodes = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> errors = new ConcurrentHashMap<>();

    public void recordLatency(String service, String endpoint, long latencyMs) {
        String key = service + ":" + endpoint;
        latencies.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(latencyMs);
        System.out.println("Metric: latency " + key + "=" + latencyMs + "ms");
    }

    public void recordStatusCode(String service, int statusCode) {
        String key = service + ":status_" + statusCode;
        statusCodes.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public void recordError(String service, String errorType) {
        String key = service + ":error_" + errorType;
        errors.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();
        System.out.println("Metric: error " + key);
    }
}
