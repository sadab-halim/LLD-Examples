package chainOfResponsibility.eb;

public class OrderService {
    private final ErrorHandler errorHandlerChain;

    public OrderService(FallbackService fallbackService,
                        CompensationService compensationService,
                        AlertingService alertingService) {

        // Build error handler chain
        ErrorHandler retry = new RetryableErrorHandler();
        ErrorHandler fallback = new FallbackHandler(fallbackService);
        ErrorHandler compensation = new CompensationHandler(compensationService);
        ErrorHandler escalation = new EscalationHandler(alertingService);

        retry.setNext(fallback)
                .setNext(compensation)
                .setNext(escalation);

        this.errorHandlerChain = retry;
    }

    public OrderResult processOrder(Order order) {
        OrderContext context = new OrderContext(order.getOrderId(), order);

        try {
            // Simulate order processing
            executeOrderWorkflow(order);
            return OrderResult.success(order.getOrderId());

        } catch (Exception e) {
            // Handle error through chain
            RecoveryResult recovery = errorHandlerChain.handle(e, context);

            if (recovery.isRecovered()) {
                return OrderResult.recovered(order.getOrderId(), recovery);
            } else {
                return OrderResult.failed(order.getOrderId(), recovery.getMessage());
            }
        }
    }

    private void executeOrderWorkflow(Order order) throws Exception {
        // Simulate failures
        if (order.getOrderId().contains("timeout")) {
            throw new TimeoutException("inventory-service");
        }
        if (order.getOrderId().contains("unavailable")) {
            throw new ServiceUnavailableException("payment-service");
        }
        if (order.getOrderId().contains("payment-fail")) {
            throw new PaymentFailedException("Insufficient funds");
        }

        // Success case
        order.setStatus(Order.OrderStatus.COMPLETED);
    }
}
