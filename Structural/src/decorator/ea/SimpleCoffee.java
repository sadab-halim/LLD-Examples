package decorator.ea;

// 2. Concrete Component
// This is the basic object that we want to add responsibilities to.
public class SimpleCoffee implements Coffee{
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 2.0;
    }
}
