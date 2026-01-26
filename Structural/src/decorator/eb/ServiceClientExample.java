package decorator.eb;

public class ServiceClientExample {
    public static void main(String[] args) {
        // Setup dependencies
        HttpClient httpClient = new HttpClient();
        DistributedTracer tracer = new DistributedTracer();
        RetryPolicy retryPolicy = new RetryPolicy(3, 100);
        CircuitBreaker circuitBreaker = new CircuitBreaker(5, 10000);
        MetricsCollector metrics = new MetricsCollector();

        // Build decorator chain
        ServiceClient client = new MetricsDecorator(
                new CircuitBreakerDecorator(
                        new RetryDecorator(
                                new TracingDecorator(
                                        new HttpServiceClient(httpClient),
                                        tracer
                                ),
                                retryPolicy
                        ),
                        circuitBreaker
                ),
                metrics,
                "user-service"
        );

        // Make service call
        try {
            ServiceRequest request = new ServiceRequest(
                    "https://api.example.com/users/123",
                    "GET",
                    ""
            );

            ServiceResponse response = client.call(request);

            System.out.println("\nResponse:");
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Latency: " + response.getLatencyMs() + "ms");

        } catch (ServiceException e) {
            System.err.println("Service call failed: " + e.getMessage());
        }
    }
}
