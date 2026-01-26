package decorator.ea;

import java.time.Duration;
import java.util.Set;

public class CachingDecorator extends  RequestHandlerDecorator {
    private final Cache cache;
    private final Set<String> cacheableMethods = Set.of("GET", "HEAD");

    public CachingDecorator(RequestHandler handler, Cache cache) {
        super(handler);
        this.cache = cache;
    }

    @Override
    public Response handle(Request request) throws Exception {
        String method = request.getHeader("X-HTTP-Method");

        if (!cacheableMethods.contains(method)) {
            return wrappedHandler.handle(request);
        }

        String cacheKey = generateCacheKey(request);
        Response cachedResponse = cache.get(cacheKey);

        if (cachedResponse != null) {
            return cachedResponse.withHeader("X-Cache", "HIT");
        }

        Response response = wrappedHandler.handle(request);

        if (response.getStatusCode() == 200) {
            cache.put(cacheKey, response, Duration.ofMinutes(5));
        }

        return response.withHeader("X-Cache", "MISS");
    }

    private String generateCacheKey(Request request) {
        return request.getPath() + ":" +
                request.getContextAttribute("userId");
    }
}
