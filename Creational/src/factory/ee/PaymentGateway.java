package factory.ee;

import java.util.Currency;

// 1. The Interface
public interface PaymentGateway {
    boolean processPayment(double amount, Currency currency);
}
