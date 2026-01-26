package decorator.ea;

public class AuthenticationDecorator extends RequestHandlerDecorator {
    private final AuthenticationService authService;

    AuthenticationDecorator(RequestHandler wrappedHandler, AuthenticationService authService) {
        super(wrappedHandler);
        this.authService = authService;
    }

    @Override
    public Response handle(Request request) throws Exception {
        String authToken = request.getHeader("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            return new Response(401, "Unauthorized: Missing token");
        }

        try {
            User user = authService.validateToken(authToken);
            request.setContextAttribute("user", user);
            request.setContextAttribute("userId", user.getId());

            Response response = wrappedHandler.handle(request);

            // Enrich response with user context
            return response.withHeader("X-User-Id", user.getId());

        } catch (InvalidTokenException e) {
            return new Response(401, "Unauthorized: Invalid token");
        }
    }
}
