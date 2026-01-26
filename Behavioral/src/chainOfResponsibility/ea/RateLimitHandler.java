package chainOfResponsibility.ea;

public class RateLimitHandler extends AbstractRequestHandler {
    private final RateLimiter rateLimiter;

    public RateLimitHandler(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected Response doHandle(Request request) {
        String clientIp = request.getClientIp();
        String apiKey = request.getHeader("X-API-Key");

        String rateLimitKey = apiKey != null ? "api_key:" + apiKey : "ip:" + clientIp;

        if (!rateLimiter.allowRequest(rateLimitKey)) {
            return Response.error(429, "Rate limit exceeded", "RateLimitHandler");
        }

        return Response.success();
    }
}
