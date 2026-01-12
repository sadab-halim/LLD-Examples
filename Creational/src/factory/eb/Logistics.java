package factory.eb;

// 3. The Creator (Abstract Factory Class)
// It declares the factory method but doesn't implement it.
abstract class Logistics {
    // This is the core business logic.
    // It uses the product, but doesn't know which specific product it is yet.
    public void planDelivery() {
        // 1. Create the transport (we don't know if it's a truck or ship yet)
        Transport transport = createTransport();
        // 2. Use it
        transport.deliver();
    }

    // THE FACTORY METHOD
    // Subclasses must implement this to create the specific object.
    protected abstract Transport createTransport();
}
