package chainOfResponsibility.ea;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class APIGatewayRequestProcessingPipelineDemo {
    public static void main(String[] args) {
        // Setup dependencies (mock implementations)
        RateLimiter rateLimiter = key -> true; // Allow all for demo
        TokenValidator tokenValidator = token -> {
            if ("valid_token".equals(token)) {
                return new UserContext("user123", "john_doe",
                        Set.of("USER", "ADMIN"));
            }
            throw new InvalidTokenException("Invalid token");
        };
        PermissionService permissionService = (user, resource, action) -> true;
        RequestValidator validator = req -> List.of();
        Logger logger = (msg, arguments) -> System.out.println(
                String.format(msg.replace("{}", "%s"), arguments)
        );

        ApiGateway gateway = new ApiGateway(rateLimiter, tokenValidator,
                permissionService, validator, logger);

        // Test request
        Request request = new Request(
                "req-123",
                "/api/users",
                "GET",
                Map.of("Authorization", "Bearer valid_token",
                        "X-Forwarded-For", "192.168.1.1"),
                ""
        );

        Response response = gateway.processRequest(request);
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Message: " + response.getMessage());
    }
}
