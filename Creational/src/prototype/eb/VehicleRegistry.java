package prototype.eb;

import java.util.HashMap;
import java.util.Map;

// 4. The Prototype Registry
// This class manages and dispenses the prototype objects.
public class VehicleRegistry {
    private Map<String, Vehicle> prototypes = new HashMap<>();

    // Load the initial, costly-to-create prototypes
    public VehicleRegistry() {
        Car standardCar = new Car("Standard Sedan");
        Bus cityBus = new Bus(50);

        prototypes.put("CAR", standardCar);
        prototypes.put("BUS", cityBus);
    }

    // The key method: get an object by cloning a prototype
    public Vehicle getVehicle(String type) {
        try {
            // We clone the stored prototype, not return the prototype itself.
            return prototypes.get(type).clone();
        } catch (CloneNotSupportedException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
