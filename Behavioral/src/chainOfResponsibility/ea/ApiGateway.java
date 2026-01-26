package chainOfResponsibility.ea;

public class ApiGateway {
    private final RequestHandler handlerChain;

    public ApiGateway(RateLimiter rateLimiter,
                      TokenValidator tokenValidator,
                      PermissionService permissionService,
                      RequestValidator validator,
                      Logger logger) {

        // Build the chain
        RequestHandler logging = new LoggingHandler(logger);
        RequestHandler rateLimit = new RateLimitHandler(rateLimiter);
        RequestHandler auth = new AuthenticationHandler(tokenValidator);
        RequestHandler authz = new AuthorizationHandler(permissionService);
        RequestHandler validation = new ValidationHandler(validator);

        // Chain: Logging → RateLimit → Auth → Authz → Validation
        logging.setNext(rateLimit)
                .setNext(auth)
                .setNext(authz)
                .setNext(validation);

        this.handlerChain = logging;
    }

    public Response processRequest(Request request) {
        Response response = handlerChain.handle(request);

        if (response.shouldContinue()) {
            // All checks passed, forward to actual API endpoint
            return invokeApiEndpoint(request);
        }

        return response;
    }

    private Response invokeApiEndpoint(Request request) {
        // Actual API logic here
        return Response.ok("API response data");
    }
}
