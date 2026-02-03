package factory.eb;

public class EmailChannel implements NotificationChannel {
    private final String smtpHost;

    public EmailChannel(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("Sending Email to " + userId + " via SendGrid");
    }

    @Override
    public boolean isHealthy() { return true; }
}
