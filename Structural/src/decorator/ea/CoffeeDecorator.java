package decorator.ea;

// 3. Decorator Abstract Class
// This abstract class implements the Component interface.
// It holds a reference to a Component object (either a concrete component or another decorator).
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee; // The object being decorated

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    // Delegates the calls to the decorated object.
    // Concrete decorators will override these to add behavior.
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}
