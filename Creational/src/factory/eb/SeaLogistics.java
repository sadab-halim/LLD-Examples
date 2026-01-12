package factory.eb;

// 4. Concrete Creators
public class SeaLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        // Sea logistics always create Ships.
        return new Ship();
    }
}
