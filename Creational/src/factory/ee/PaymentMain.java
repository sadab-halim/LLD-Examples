package factory.ee;

import java.util.Currency;

public class PaymentMain {
    public static void main(String[] args) {
        PaymentGatewayFactory factory = new PaymentGatewayFactory();

        // Scenario 1: Standard US Transaction
        // Expected: Stripe (Default)
        System.out.println("--- Scenario 1: US Micro-transaction ---");
        PaymentContext ctx1 = new PaymentContext();
        ctx1.amount = 50.00;
        ctx1.currency = Currency.getInstance("USD");
        ctx1.countryCode = "US";

        PaymentGateway gateway1 = factory.selectGateway(ctx1);
        gateway1.processPayment(ctx1.amount, ctx1.currency);


        // Scenario 2: Indian Rupee Transaction
        // Expected: Razorpay (Specific Rule for INR)
        System.out.println("\n--- Scenario 2: Indian Market Transaction ---");
        PaymentContext ctx2 = new PaymentContext();
        ctx2.amount = 5000.00;
        ctx2.currency = Currency.getInstance("INR");
        ctx2.countryCode = "IN";

        PaymentGateway gateway2 = factory.selectGateway(ctx2);
        gateway2.processPayment(ctx2.amount, ctx2.currency);


        // Scenario 3: High Value Transaction
        // Expected: Stripe (Specific Rule for > 10,000)
        System.out.println("\n--- Scenario 3: High Value Enterprise Transaction ---");
        PaymentContext ctx3 = new PaymentContext();
        ctx3.amount = 150000.00; // $150k
        ctx3.currency = Currency.getInstance("USD");
        ctx3.countryCode = "US";

        PaymentGateway gateway3 = factory.selectGateway(ctx3);
        gateway3.processPayment(ctx3.amount, ctx3.currency);
    }
}
