package builder.ed;

// 1. Abstract Base Class
abstract class Transaction {
    private final double amount;
    private final String currency;

    protected Transaction(Builder<?> builder) {
        this.amount = builder.amount;
        this.currency = builder.currency;
    }

    public String toString() {
        return String.format("Base[Amt=%.2f %s]", amount, currency);
    }

    // Recursive Generic Builder to allow chaining in subclasses
    abstract static class Builder<T extends Builder<T>> {
        private double amount;
        private String currency;

        // The "self" trick ensures methods return the specific subclass type
        protected abstract T self();

        public T withAmount(double amount) {
            this.amount = amount;
            return self();
        }

        public T withCurrency(String currency) {
            this.currency = currency;
            return self();
        }

        abstract Transaction build();
    }
}