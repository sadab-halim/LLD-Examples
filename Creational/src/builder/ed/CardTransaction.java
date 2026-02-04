package builder.ed;

// 3. Concrete Implementation: Card
class CardTransaction extends Transaction {
    private final String cardMask;

    private CardTransaction(Builder builder) {
        super(builder);
        this.cardMask = builder.cardMask;
    }

    @Override
    public String toString() {
        return super.toString() + " Card[Mask=" + cardMask + "]";
    }

    public static class Builder extends Transaction.Builder<Builder> {
        private String cardMask;

        public Builder withCardMask(String mask) {
            this.cardMask = mask;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CardTransaction build() {
            return new CardTransaction(this);
        }
    }
}