package builder.ec;

public class HttpClientDemo {
    public static void main(String[] args) {
        // Usage: User only overrides what they specifically need
        ResilientHttpClient client = new ResilientHttpClient.Builder()
                .withBaseUrl("https://api.payment-service.internal")
                .withRetries(5) // Override default of 3
                // Timeout not specified, will default to 1000ms safely
                .build();

        System.out.println(client.getConfigSummary());
    }
}
