package decorator.ea;

public class ServiceClient {
    public Response call(Request request) {
        // Simulate microservice call
        return new Response(200, "{\"message\": \"Success\"}");
    }
}
