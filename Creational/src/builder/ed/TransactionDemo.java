package builder.ed;

public class TransactionDemo {
    public static void main(String[] args) {
        // Creating a Crypto Transaction
        CryptoTransaction cryptoTx = new CryptoTransaction.Builder()
                .withAmount(100.00)
                .withCurrency("USD")
                .withWalletAddress("0xAbCdEf123456") // Specific to Crypto
                .build();

        System.out.println(cryptoTx);

        // Creating a Card Transaction
        CardTransaction cardTx = new CardTransaction.Builder()
                .withAmount(50.50)
                .withCurrency("EUR")
                .withCardMask("4111-xxxx-xxxx-1111") // Specific to Card
                .build();

        System.out.println(cardTx);
    }
}
