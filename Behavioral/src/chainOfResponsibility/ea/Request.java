package chainOfResponsibility.ea;

import java.util.Map;

public class Request {
    private final String requestId;
    private final String path;
    private final String method;
    private final Map<String, String> headers;
    private final String body;
    private UserContext userContext; // Populated by auth handler

    public Request(String requestId, String path, String method,
                   Map<String, String> headers, String body) {
        this.requestId = requestId;
        this.path = path;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getClientIp() {
        return headers.getOrDefault("X-Forwarded-For", "unknown");
    }

    // Getters
    public String getRequestId() { return requestId; }
    public String getPath() { return path; }
    public String getMethod() { return method; }
    public String getBody() { return body; }
    public UserContext getUserContext() { return userContext; }
    public void setUserContext(UserContext ctx) { this.userContext = ctx; }
}
