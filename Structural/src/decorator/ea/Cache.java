package decorator.ea;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<String, CacheEntry> store = new ConcurrentHashMap<>();

    public Response get(String key) {
        CacheEntry entry = store.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.response;
        }
        store.remove(key);
        return null;
    }

    public void put(String key, Response response, Duration ttl) {
        store.put(key, new CacheEntry(response,
                System.currentTimeMillis() + ttl.toMillis()));
    }

    private static class CacheEntry {
        final Response response;
        final long expiryTime;

        CacheEntry(Response response, long expiryTime) {
            this.response = response;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
