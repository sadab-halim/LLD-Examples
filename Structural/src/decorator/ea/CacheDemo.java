package decorator.ea;

public class CacheDemo {
    public static void main(String[] args) throws InterruptedException {
        // Stack: Metrics -> TTL -> Storage
        Cache<String, String> cache = new MetricsDecorator<>(
                new TTLDecorator<>(
                        new SimpleCache<>(),
                        100 // 100ms TTL
                )
        );

        cache.put("user_1", "John Doe");
        cache.get("user_1"); // Hit

        Thread.sleep(150); // Wait for expiration
        cache.get("user_1"); // Miss (Expired)
    }
}
