package factory.eb;

// 1. The Interface
public interface NotificationChannel {
    void send(String userId, String message);
    boolean isHealthy();
}
