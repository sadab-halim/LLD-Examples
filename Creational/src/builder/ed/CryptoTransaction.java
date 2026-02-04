package builder.ed;

// 2. Concrete Implementation: Crypto
class CryptoTransaction extends Transaction {
    private final String walletAddress;

    private CryptoTransaction(Builder builder) {
        super(builder);
        this.walletAddress = builder.walletAddress;
    }

    @Override
    public String toString() {
        return super.toString() + " Crypto[Wallet=" + walletAddress + "]";
    }

    public static class Builder extends Transaction.Builder<Builder> {
        private String walletAddress;

        public Builder withWalletAddress(String address) {
            this.walletAddress = address;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CryptoTransaction build() {
            return new CryptoTransaction(this);
        }
    }
}
