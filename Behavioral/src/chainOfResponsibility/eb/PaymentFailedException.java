package chainOfResponsibility.eb;

public class PaymentFailedException extends Exception {
    public PaymentFailedException(String reason) {
        super("Payment failed: " + reason);
    }
}
