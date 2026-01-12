package factory.eb;

// 4. Concrete Creators
public class RoadLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        // Road logistics always create Trucks
        return new Truck();
    }
}
