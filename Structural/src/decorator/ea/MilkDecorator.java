package decorator.ea;

// 4. Concrete Decorators
// These classes add specific responsibilities (e.g., milk, sugar)
// to the component.
public class MilkDecorator extends CoffeeDecorator{
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.5; // Add cost for milk
    }
}
