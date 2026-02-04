package builder.ec;

public class ResilientHttpClient {
    private final String baseUrl;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;
    private final int maxRetries;
    private final boolean circuitBreakerEnabled;

    private ResilientHttpClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.connectTimeoutMs = builder.connectTimeoutMs;
        this.readTimeoutMs = builder.readTimeoutMs;
        this.maxRetries = builder.maxRetries;
        this.circuitBreakerEnabled = builder.circuitBreakerEnabled;
    }

    public String getConfigSummary() {
        return String.format("Client[URL=%s, ConnectTimeout=%dms, Retries=%d]",
                baseUrl, connectTimeoutMs, maxRetries);
    }

    public static class Builder {
        // 1. Mandatory Fields (No default)
        private String baseUrl;

        // 2. Safe Defaults (Critical for resilience)
        private int connectTimeoutMs = 1000;
        private int readTimeoutMs = 3000;
        private int maxRetries = 3;
        private boolean circuitBreakerEnabled = true;

        public Builder withBaseUrl(String url) {
            this.baseUrl = url;
            return this;
        }

        public Builder withConnectTimeout(int ms) {
            if (ms <= 0) throw new IllegalArgumentException("Timeout must be positive");
            this.connectTimeoutMs = ms;
            return this;
        }

        public Builder withRetries(int retries) {
            this.maxRetries = retries;
            return this;
        }

        public Builder disableCircuitBreaker() {
            this.circuitBreakerEnabled = false;
            return this;
        }

        public ResilientHttpClient build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL is required");
            }
            // In a real implementation, we would initialize the connection pool here
            return new ResilientHttpClient(this);
        }
    }
}
