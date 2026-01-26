package decorator.eb;

import java.util.HashMap;
import java.util.Map;

public class ServiceRequest {
    private final String endpoint;
    private final String method;
    private final Map<String, String> headers;
    private final String payload;

    public ServiceRequest(String endpoint, String method, String payload) {
        this.endpoint = endpoint;
        this.method = method;
        this.headers = new HashMap<>();
        this.payload = payload;
    }

    public String getEndpoint() { return endpoint; }
    public String getMethod() { return method; }
    public Map<String, String> getHeaders() { return headers; }
    public String getPayload() { return payload; }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }
}
