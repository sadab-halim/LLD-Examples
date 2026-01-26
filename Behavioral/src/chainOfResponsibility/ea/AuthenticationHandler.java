package chainOfResponsibility.ea;

public class AuthenticationHandler extends AbstractRequestHandler {
    private final TokenValidator tokenValidator;

    public AuthenticationHandler(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected Response doHandle(Request request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.error(401, "Missing or invalid authorization header",
                    "AuthenticationHandler");
        }

        String token = authHeader.substring(7);

        try {
            UserContext userContext = tokenValidator.validate(token);
            request.setUserContext(userContext);
            return Response.success();
        } catch (InvalidTokenException e) {
            return Response.error(401, "Invalid token: " + e.getMessage(),
                    "AuthenticationHandler");
        }
    }
}
