package factory.eb;

// 4. The Factory
public class NotificationFactory {
    public static NotificationChannel createChannel(ChannelType type, TenantConfig config) {
        switch (type) {
            case SMS:
                if (!config.smsEnabled) {
                    throw new IllegalStateException("SMS is disabled for this tenant");
                }
                return new SMSChannel(config.smsApiKey);

            case EMAIL:
                return new EmailChannel(config.emailHost);

            case PUSH:
                // Fallthrough or throw
                throw new UnsupportedOperationException("Push not configured");

            default:
                throw new IllegalArgumentException("Unknown channel type");
        }
    }
}
