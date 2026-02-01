package singleton.ea;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class MetricsRegistry {

    // 1. Volatile is CRITICAL here. Without it, other threads might see
    // a half-initialized 'instance' due to instruction reordering.
    private static volatile MetricsRegistry instance;

    // High-performance counter for heavy contention
    private final ConcurrentHashMap<String, LongAdder> counters;
    private final ScheduledExecutorService scheduler;

    private MetricsRegistry() {
        this.counters = new ConcurrentHashMap<>();

        // Background thread (Daemon) to flush metrics
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "metrics-flusher");
            t.setDaemon(true);
            return t;
        });

        // Schedule flush every 2 seconds for this demo
        this.scheduler.scheduleAtFixedRate(this::flush, 2, 2, TimeUnit.SECONDS);
    }

    // 2. Double-Checked Locking
    public static MetricsRegistry getInstance() {
        if (instance == null) {
            synchronized (MetricsRegistry.class) {
                if (instance == null) {
                    instance = new MetricsRegistry();
                }
            }
        }
        return instance;
    }

    public void increment(String metric) {
        counters.computeIfAbsent(metric, k -> new LongAdder()).increment();
    }

    private void flush() {
        if (counters.isEmpty()) return;
        System.out.println("[System] Flushing buffer to backend...");
        counters.forEach((k, v) ->
                System.out.println("   >> Metric: " + k + " = " + v.sumThenReset()));
    }

    // --- MAIN DRIVER ---
    public static void main(String[] args) throws InterruptedException {
        MetricsRegistry registry = MetricsRegistry.getInstance();
        ExecutorService pool = Executors.newFixedThreadPool(5);

        System.out.println("--- Starting High Concurrency Metrics Test ---");

        // Simulate 5 threads spamming metrics
        for (int i = 0; i < 5; i++) {
            pool.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    registry.increment("api.latency");
                    if (j % 10 == 0) registry.increment("db.error");
                    try { Thread.sleep(10); } catch (Exception e) {}
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        // Wait for the scheduled flush to happen
        Thread.sleep(3000);
        System.out.println("--- End of Test ---");
    }
}
