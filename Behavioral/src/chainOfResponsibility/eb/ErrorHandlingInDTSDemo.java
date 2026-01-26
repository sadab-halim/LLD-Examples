package chainOfResponsibility.eb;

import java.math.BigDecimal;
import java.util.List;

public class ErrorHandlingInDTSDemo {
    public static void main(String[] args) {
        // Mock services
        FallbackService fallbackService = order -> true;
        CompensationService compensationService = new CompensationService() {
            public void compensate(Order order) {
                System.out.println("Compensating order: " + order.getOrderId());
            }
            public void logForReview(String orderId, String reason) {
                System.out.println("Logged for review: " + orderId + " - " + reason);
            }
        };
        AlertingService alertingService = (title, msg) ->
                System.out.println("ALERT: " + title + " - " + msg);

        OrderService orderService = new OrderService(fallbackService,
                compensationService,
                alertingService);

        // Test cases
        Order order1 = new Order("order-timeout-123",
                List.of(new OrderItem("prod1", 2, new BigDecimal("29.99"))),
                new BigDecimal("59.98"));

        Order order2 = new Order("order-payment-fail-456",
                List.of(new OrderItem("prod2", 1, new BigDecimal("99.99"))),
                new BigDecimal("99.99"));

        System.out.println("=== Processing Order 1 (Transient Failure) ===");
        OrderResult result1 = orderService.processOrder(order1);
        System.out.println("Result: " + result1.getMessage());

        System.out.println("\n=== Processing Order 2 (Payment Failure) ===");
        OrderResult result2 = orderService.processOrder(order2);
        System.out.println("Result: " + result2.getMessage());
    }
}
