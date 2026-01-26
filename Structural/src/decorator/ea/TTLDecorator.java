package decorator.ea;

import java.util.HashMap;
import java.util.Map;

// 4. Concrete Decorator A: Time-to-Live (Expiration)
public class TTLDecorator<K, V> extends CacheDecorator<K, V> {
    private final long ttlMillis;
    private final Map<K, Long> timestamps = new HashMap<>();

    public TTLDecorator(Cache<K, V> delegate, long ttlMillis) {
        super(delegate);
        this.ttlMillis = ttlMillis;
    }

    @Override
    public void put(K key, V value) {
        timestamps.put(key, System.currentTimeMillis());
        super.put(key, value);
    }

    @Override
    public V get(K key) {
        if (!timestamps.containsKey(key)) return null;

        long outputTime = System.currentTimeMillis();
        if (outputTime - timestamps.get(key) > ttlMillis) {
            System.out.println("[TTL] Key expired: " + key);
            timestamps.remove(key);
            return null; // Logic to actually remove from delegate omitted for brevity
        }
        return super.get(key);
    }
}
