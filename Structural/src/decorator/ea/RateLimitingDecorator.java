package decorator.ea;

public class RateLimitingDecorator extends RequestHandlerDecorator {
    private final RateLimiter rateLimiter;

    public RateLimitingDecorator(RequestHandler handler, RateLimiter rateLimiter) {
        super(handler);
        this.rateLimiter = rateLimiter;
    }

    @Override
    public Response handle(Request request) throws Exception {
        String userId = (String) request.getContextAttribute("userId");
        if (userId == null) {
            userId = request.getHeader("X-Client-IP"); // Fallback to IP
        }

        if (!rateLimiter.allowRequest(userId)) {
            int remaining = rateLimiter.getRemainingQuota(userId);
            long resetTime = rateLimiter.getResetTime(userId);

            return new Response(429, "Rate limit exceeded")
                    .withHeader("X-RateLimit-Remaining", String.valueOf(remaining))
                    .withHeader("X-RateLimit-Reset", String.valueOf(resetTime));
        }

        Response response = wrappedHandler.handle(request);

        // Add rate limit info to response
        int remaining = rateLimiter.getRemainingQuota(userId);
        return response
                .withHeader("X-RateLimit-Remaining", String.valueOf(remaining));
    }
}
