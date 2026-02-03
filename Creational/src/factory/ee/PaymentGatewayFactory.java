package factory.ee;

// 4. The Factory
public class PaymentGatewayFactory {
    // Ideally injected from config
    private static final String STRIPE_KEY = "sk_test_123";
    private static final String RAZORPAY_KEY = "rzp_live_123";
    private static final String RAZORPAY_SECRET = "secret_abc";

    public PaymentGateway selectGateway(PaymentContext context) {
        // Business Logic: Routing Rules inside the factory (or delegated to a helper)

        // Rule 1: High value transactions go to Stripe for stability
        if (context.amount > 10000) {
            return new StripeGateway(STRIPE_KEY);
        }

        // Rule 2: INR currency must use Razorpay
        if ("INR".equals(context.currency.getCurrencyCode())) {
            return new RazorpayGateway(RAZORPAY_KEY, RAZORPAY_SECRET);
        }

        // Rule 3: Default fallback
        return new StripeGateway(STRIPE_KEY);
    }
}
