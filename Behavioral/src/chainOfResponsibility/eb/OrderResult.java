package chainOfResponsibility.eb;

public class OrderResult {
    private final String orderId;
    private final boolean success;
    private final String message;
    private final RecoveryResult recovery;

    private OrderResult(String orderId, boolean success, String message,
                        RecoveryResult recovery) {
        this.orderId = orderId;
        this.success = success;
        this.message = message;
        this.recovery = recovery;
    }

    public static OrderResult success(String orderId) {
        return new OrderResult(orderId, true, "Order processed successfully", null);
    }

    public static OrderResult recovered(String orderId, RecoveryResult recovery) {
        return new OrderResult(orderId, true,
                "Order recovered: " + recovery.getMessage(), recovery);
    }

    public static OrderResult failed(String orderId, String message) {
        return new OrderResult(orderId, false, message, null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
