package chainOfResponsibility.eb;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private final String orderId;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final BigDecimal totalAmount;

    public Order(String orderId, List<OrderItem> items, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
    }

    public enum OrderStatus {
        PENDING, PROCESSING, COMPLETED, FAILED, COMPENSATING
    }

    public String getOrderId() { return orderId; }
    public List<OrderItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
