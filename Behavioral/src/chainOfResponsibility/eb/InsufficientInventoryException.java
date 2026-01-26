package chainOfResponsibility.eb;

public class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String productId) {
        super("Insufficient inventory for product: " + productId);
    }
}
