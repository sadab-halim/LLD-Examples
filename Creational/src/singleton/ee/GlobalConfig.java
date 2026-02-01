package singleton.ee;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GlobalConfig {
    private static final GlobalConfig INSTANCE = new GlobalConfig();

    // Holds the immutable snapshot
    private final AtomicReference<Map<String, String>> currentConfig;

    private GlobalConfig() {
        Map<String, String> initial = new HashMap<>();
        initial.put("mode", "production");
        initial.put("timeout", "5000");
        this.currentConfig = new AtomicReference<>(Collections.unmodifiableMap(initial));
    }

    public static GlobalConfig getInstance() { return INSTANCE; }

    // Lock-Free Read
    public String get(String key) {
        return currentConfig.get().get(key);
    }

    // Atomic Write
    public void update(Map<String, String> newConfig) {
        // Create completely new immutable map
        Map<String, String> nextSnapshot = Collections.unmodifiableMap(new HashMap<>(newConfig));
        // Atomic swap
        currentConfig.set(nextSnapshot);
        System.out.println("[Config] >>> Configuration Swapped! <<<");
    }

    // --- MAIN DRIVER ---
    public static void main(String[] args) throws InterruptedException {
        GlobalConfig config = GlobalConfig.getInstance();

        // 1. Reader Thread
        Thread reader = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Reader sees timeout: " + config.get("timeout"));
                try { Thread.sleep(300); } catch (Exception e) { break; }
            }
        });
        reader.start();

        // 2. Wait a bit, then Hot Reload
        Thread.sleep(1000);

        Map<String, String> newSettings = new HashMap<>();
        newSettings.put("mode", "maintenance");
        newSettings.put("timeout", "20"); // Drastic change

        config.update(newSettings); // Atomic update

        Thread.sleep(1000);
        reader.interrupt();
    }
}
