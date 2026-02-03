package factory.eb;

public class NotificationMain {
    public static void main(String[] args) {
        // 1. Setup Tenant Configuration (Simulating fetching from DB)
        TenantConfig tenantAConfig = new TenantConfig();
        tenantAConfig.smsEnabled = true;
        tenantAConfig.smsApiKey = "twilio_live_key_123";
        tenantAConfig.emailHost = "smtp.sendgrid.net";

        TenantConfig tenantBConfig = new TenantConfig();
        tenantBConfig.smsEnabled = false; // Tenant B hasn't paid for SMS
        tenantBConfig.emailHost = "smtp.mailgun.org";

        // 2. Scenario A: Tenant A wants to send an SMS (Allowed)
        try {
            System.out.println("--- Scenario A: Tenant A sending SMS ---");
            NotificationChannel channel = NotificationFactory.createChannel(ChannelType.SMS, tenantAConfig);

            // Client uses interface methods only
            if (channel.isHealthy()) {
                channel.send("user_101", "Your order #999 is shipped!");
            }
        } catch (Exception e) {
            System.err.println("Failed to create channel: " + e.getMessage());
        }

        // 3. Scenario B: Tenant B tries to send an SMS (Restricted)
        try {
            System.out.println("\n--- Scenario B: Tenant B sending SMS ---");
            // This should trigger the validation logic inside the factory
            NotificationChannel channel = NotificationFactory.createChannel(ChannelType.SMS, tenantBConfig);
            channel.send("user_202", "This won't send");
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
            // Expected output: "Error: SMS is disabled for this tenant"
        }

        // 4. Scenario C: Using Email (Allowed for Tenant B)
        try {
            System.out.println("\n--- Scenario C: Tenant B sending Email ---");
            NotificationChannel channel = NotificationFactory.createChannel(ChannelType.EMAIL, tenantBConfig);
            channel.send("user_202", "Welcome to our service");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
