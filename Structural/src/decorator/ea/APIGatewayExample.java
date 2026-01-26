package decorator.ea;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class APIGatewayExample {
    public static void main(String[] args) {
        // Setup services
        ServiceClient serviceClient = new ServiceClient();
        AuthenticationService authService = new AuthenticationService();
        RateLimiter rateLimiter = new RateLimiter(100, Duration.ofMinutes(1));
        Cache cache = new Cache();
/*        Logger logger = LoggerFactory.getLogger(APIGatewayExample.class);

        // Build decorator chain
        RequestHandler handler = new LoggingDecorator(
                new CachingDecorator(
                        new RateLimitingDecorator(
                                new AuthenticationDecorator(
                                        new BaseRequestHandler(serviceClient),
                                        authService
                                ),
                                rateLimiter
                        ),
                        cache
                ),
                logger
        );

        // Test authenticated request
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer valid-token-123");
            headers.put("X-HTTP-Method", "GET");

            Request request = new Request("/api/users/profile", headers, "");
            Response response = handler.handle(request);

            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test unauthenticated request
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("X-HTTP-Method", "GET");

            Request request = new Request("/api/users/profile", headers, "");
            Response response = handler.handle(request);

            System.out.println("\nUnauthenticated request:");
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
