package factory.eb;

// 5. Client Code
public class FactoryMethodDemo {
    public static void main(String[] args) {
        // If the business logic is Road...
        Logistics logistics = new RoadLogistics();
        logistics.planDelivery(); // Output: Deliver by land...

        // If the business logic changes to Sea...
        logistics = new SeaLogistics();
        logistics.planDelivery(); // Output: Deliver by sea...
    }
}
