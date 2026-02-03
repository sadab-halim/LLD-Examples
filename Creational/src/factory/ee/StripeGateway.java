package factory.ee;

import java.util.Currency;

// 2. Concrete Implementations
public class StripeGateway implements PaymentGateway {
    public StripeGateway(String apiKey) { /* Init Stripe SDK */ }
    @Override
    public boolean processPayment(double amount, Currency currency) {
        System.out.println("Processing via Stripe");
        return true;
    }
}
