package decorator.eb;

public class HttpServiceClient implements ServiceClient {
    private final HttpClient httpClient;

    public HttpServiceClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ServiceResponse call(ServiceRequest request) throws ServiceException {
        long startTime = System.currentTimeMillis();

        try {
            // Simulate HTTP call
            Thread.sleep(50); // Simulate network latency

            int statusCode = 200;
            String body = "{\"result\": \"success\"}";
            long latency = System.currentTimeMillis() - startTime;

            return new ServiceResponse(statusCode, body, latency);

        } catch (InterruptedException e) {
            throw new ServiceException("Request interrupted", e);
        }
    }
}
