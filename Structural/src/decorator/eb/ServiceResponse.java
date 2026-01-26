package decorator.eb;

public class ServiceResponse {
    private final int statusCode;
    private final String body;
    private final long latencyMs;

    public ServiceResponse(int statusCode, String body, long latencyMs) {
        this.statusCode = statusCode;
        this.body = body;
        this.latencyMs = latencyMs;
    }

    public int getStatusCode() { return statusCode; }
    public String getBody() { return body; }
    public long getLatencyMs() { return latencyMs; }
    public boolean isSuccess() { return statusCode >= 200 && statusCode < 300; }
}
