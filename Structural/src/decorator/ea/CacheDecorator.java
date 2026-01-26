package decorator.ea;

// 3. Abstract Decorator
abstract class CacheDecorator<K, V> implements Cache<K, V> {
    protected final Cache<K, V> delegate;

    public CacheDecorator(Cache<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void put(K key, V value) {
        delegate.put(key, value);
    }

    @Override
    public V get(K key) {
        return delegate.get(key);
    }
}
