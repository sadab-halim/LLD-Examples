package factory.ee;

import java.util.Currency;

public class RazorpayGateway implements PaymentGateway {
    public RazorpayGateway(String keyId, String secret) { /* Init Razorpay */ }
    @Override
    public boolean processPayment(double amount, Currency currency) {
        System.out.println("Processing via Razorpay (India optimized)");
        return true;
    }
}
