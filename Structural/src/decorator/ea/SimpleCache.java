package decorator.ea;

import java.util.HashMap;
import java.util.Map;

// 2. Concrete Component (The actual storage)
public class SimpleCache<K, V> implements Cache<K, V> {
    private final Map<K, V> store = new HashMap<>();

    @Override
    public void put(K key, V value) {
        store.put(key, value);
    }

    @Override
    public V get(K key) {
        return store.get(key);
    }
}
