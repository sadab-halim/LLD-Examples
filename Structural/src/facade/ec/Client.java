package facade.ec;

// 1. The Complex Subsystem (multiple microservices or modules)

class InventoryService {
    public boolean checkStock(String productId) {
        System.out.println("InventoryService: Checking stock for " + productId);
        return true; // Assume item is in stock
    }
    public void reserveStock(String productId) {
        System.out.println("InventoryService: Reserving stock for " + productId);
    }
}

class PaymentService {
    public boolean processPayment(double amount, String creditCard) {
        System.out.println("PaymentService: Processing payment of $" + amount + " on card " + creditCard);
        return true; // Assume payment is successful
    }
}

class ShippingService {
    public void createShipment(String productId, String address) {
        System.out.println("ShippingService: Creating shipment for " + productId + " to " + address);
    }
}

// 2. The Facade
// It provides a single point of contact for placing an order.
class OrderFacade {
    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;

    public OrderFacade() {
        this.inventory = new InventoryService();
        this.payment = new PaymentService();
        this.shipping = new ShippingService();
    }

    // The simplified method that handles the entire order workflow.
    public boolean placeOrder(String productId, double price, String creditCard, String address) {
        System.out.println("\nFacade: Processing order for " + productId + "...");

        // 1. Check inventory
        if (!inventory.checkStock(productId)) {
            System.out.println("Facade: Order failed, item out of stock.");
            return false;
        }

        // 2. Reserve stock (so it's not sold to someone else)
        inventory.reserveStock(productId);

        // 3. Process payment
        if (!payment.processPayment(price, creditCard)) {
            System.out.println("Facade: Order failed, payment declined.");
            // In a real app, you would un-reserve the stock here
            return false;
        }

        // 4. Ship the item
        shipping.createShipment(productId, address);

        System.out.println("Facade: Order placed successfully!");
        return true;
    }
}

// 3. The Client
class Client {
    public static void main(String[] args) {
        // The client (e.g., a web controller) just calls the facade.
        OrderFacade orderFacade = new OrderFacade();

        orderFacade.placeOrder(
                "product123",
                99.99,
                "1234-5678-9012-3456",
                "123 Main St, Anytown"
        );
    }
}