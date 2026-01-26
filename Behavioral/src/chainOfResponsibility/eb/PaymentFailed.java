package chainOfResponsibility.eb;

public class PaymentFailed extends Exception {
    public PaymentFailed(String reason) {
        super("Payment failed: " + reason);
    }
}
