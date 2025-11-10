package builder.eb;

import java.util.Map;
import java.util.HashMap;

public class HttpRequest {
    private final String url;        // required
    private final String method;     // optional, defaults to "GET"
    private final Map<String, String> headers; // optional
    private final String body;       // optional

    // Private constructor that takes the builder.
    private HttpRequest(RequestBuilder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    @Override
    public String toString() {
        return "HttpRequest Details:\n" +
                "  URL: " + url + "\n" +
                "  Method: " + method + "\n" +
                "  Headers: " + headers + "\n" +
                "  Body: '" + body + "'";
    }

    public static class RequestBuilder {
        private final String url;
        private String method = "GET";
        private Map<String, String> headers = new HashMap<>();
        private String body;

        // Builder constructor for required fields.
        public RequestBuilder(String url) {
            this.url = url;
        }

        // Setter-like methods that return the builder for chaining.
        public RequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        // Can have more complex logic, like adding one header at a time.
        public RequestBuilder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public RequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            if(url == null || url.trim().isEmpty()) throw new IllegalStateException("URL cannot be null or empty.");
            return new HttpRequest(this);
        }
    }

    public static void main(String[] args) {
        HttpRequest getRequest = new RequestBuilder("https://api.google.com/search").build();
        System.out.println(getRequest);
        System.out.println("-------------");

        HttpRequest postRequest = new RequestBuilder("https://api.google.com/users")
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer your-token-here")
                .body("{\"name\": \"test user\"}")
                .build();
        System.out.println(postRequest);
    }
}
