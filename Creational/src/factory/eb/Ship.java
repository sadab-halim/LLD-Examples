package factory.eb;

// 2. Concrete Product
public class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivery by ship in a container.");
    }
}
