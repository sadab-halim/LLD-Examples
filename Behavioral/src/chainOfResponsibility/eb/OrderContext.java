package chainOfResponsibility.eb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderContext {
    private final String orderId;
    private final Order order;
    private int retryCount;
    private final List<String> attemptedRecoveries;
    private final Map<String, Object> metadata;

    public OrderContext(String orderId, Order order) {
        this.orderId = orderId;
        this.order = order;
        this.retryCount = 0;
        this.attemptedRecoveries = new ArrayList<>();
        this.metadata = new HashMap<>();
    }

    public void incrementRetryCount() { retryCount++; }
    public int getRetryCount() { return retryCount; }
    public void addAttemptedRecovery(String recovery) {
        attemptedRecoveries.add(recovery);
    }
    public Order getOrder() { return order; }
    public String getOrderId() { return orderId; }
    public void putMetadata(String key, Object value) { metadata.put(key, value); }
    public Object getMetadata(String key) { return metadata.get(key); }
}
