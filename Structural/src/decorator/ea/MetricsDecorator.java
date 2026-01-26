package decorator.ea;

// 5. Concrete Decorator B: Metrics (Instrumentation)
public class MetricsDecorator<K, V> extends CacheDecorator<K, V> {
    private int hits = 0;
    private int misses = 0;

    public MetricsDecorator(Cache<K, V> delegate) {
        super(delegate);
    }

    @Override
    public V get(K key) {
        long start = System.nanoTime();
        V value = super.get(key);
        long end = System.nanoTime();

        if (value != null) hits++; else misses++;

        System.out.printf("[Metrics] Get: %s | Time: %dns | Hits: %d | Misses: %d%n",
                key, (end - start), hits, misses);
        return value;
    }
}
