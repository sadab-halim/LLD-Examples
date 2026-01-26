package decorator.ea;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final int statusCode;
    private final Map<String, String> headers;
    private final String body;
    private final long timestamp;

    public Response(int statusCode, String body) {
        this(statusCode, new HashMap<>(), body);
    }

    public Response(int statusCode, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.timestamp = System.currentTimeMillis();
    }


    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Response withHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }
}
