package decorator.ea;

// 1. Component Interface
public interface Cache<K, V> {
    void put(K key, V value);
    V get(K key);
}
