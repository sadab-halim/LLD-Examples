package decorator.ea;

import java.util.Map;

public class Request {
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, Object> context;
    private final String body;

    public Request(String path, Map<String, String> headers, Map<String, Object> context, String body) {
        this.path = path;
        this.headers = headers;
        this.context = context;
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void setContextAttribute(String key, Object value) {
        context.put(key, value);
    }

    public Object getContextAttribute(String key) {
        return context.get(key);
    }
}
