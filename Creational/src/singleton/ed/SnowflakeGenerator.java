package singleton.ed;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SnowflakeGenerator {
    private static SnowflakeGenerator instance;

    // Bit configuration
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private final long machineId;

    private SnowflakeGenerator(long machineId) { this.machineId = machineId; }

    public static synchronized void init(long machineId) {
        if (instance == null) instance = new SnowflakeGenerator(machineId);
    }

    public static SnowflakeGenerator getInstance() {
        if (instance == null) throw new RuntimeException("Not initialized");
        return instance;
    }

    // Critical Section: Only one thread can generate an ID at a time
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp == lastTimestamp) {
            // Collision handling: increment sequence
            sequence = (sequence + 1) & 4095; // 12 bits mask
            if (sequence == 0) {
                // Sequence overflow: wait for next millisecond
                while (currentTimestamp <= lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        // Bit shifting to create 64-bit ID
        return ((currentTimestamp - 1609459200000L) << 22) | (machineId << 12) | sequence;
    }

    // --- MAIN DRIVER ---
    public static void main(String[] args) throws InterruptedException {
        SnowflakeGenerator.init(101);
        SnowflakeGenerator gen = SnowflakeGenerator.getInstance();

        // Thread-safe set to detect duplicates
        Set<Long> uniqueIds = Collections.synchronizedSet(new HashSet<>());
        ExecutorService pool = Executors.newFixedThreadPool(10);

        System.out.println("--- Generating 10,000 IDs in parallel ---");

        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    uniqueIds.add(gen.nextId());
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Total Unique IDs: " + uniqueIds.size());
        System.out.println("Collisions: " + (10000 - uniqueIds.size()));
    }
}
