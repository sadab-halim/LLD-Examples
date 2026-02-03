package factory.eb;

// 2. Concrete Implementations
public class SMSChannel implements NotificationChannel {
    private final String apiKey;

    public SMSChannel(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("Sending SMS to " + userId + " via Twilio");
    }

    @Override
    public boolean isHealthy() { return true; } // Mock health check
}
