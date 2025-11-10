package prototype.eb;

public class RegistryDemo {
    public static void main(String[] args) {
        System.out.println("--- Loading Registry (Costly) ---");
        VehicleRegistry registry = new VehicleRegistry();
        System.out.println("--- Registry Loaded ---\n");

        // Get a new car. This is a clone (fast).
        Vehicle car1 = registry.getVehicle("CAR");
        car1.drive();

        // Get another car. This is also a clone (fast).
        Vehicle car2 = registry.getVehicle("CAR");
        // We can modify this clone without affecting the prototype or car1
        ((Car) car2).setModel("Sporty Coupe");
        car2.drive();

        // The first car is unchanged
        car1.drive();

        // Get a bus.
        Vehicle bus1 = registry.getVehicle("BUS");
        bus1.drive();

        // Prove they are different objects
        System.out.println("\nCar 1 hash: " + car1.hashCode());
        System.out.println("Car 2 hash: " + car2.hashCode());
        System.out.println("Bus 1 hash: " + bus1.hashCode());
    }
}
