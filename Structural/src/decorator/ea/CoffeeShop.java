package decorator.ea;

public class CoffeeShop {
    public static void main(String[] args) {
        // Start with a simple coffee
        Coffee myCoffee = new SimpleCoffee();
        System.out.println(myCoffee.getDescription() + " - $" + myCoffee.getCost()); // Simple Coffee - $2.0

        // Decorate it with Milk
        myCoffee = new MilkDecorator(myCoffee);
        System.out.println(myCoffee.getDescription() + " - $" + myCoffee.getCost()); // Simple Coffee, Milk - $2.5

        // Decorate it further with Sugar
        myCoffee = new SugarDecorator(myCoffee);
        System.out.println(myCoffee.getDescription() + " - $" + myCoffee.getCost()); // Simple Coffee, Milk, Sugar - $2.7

        // Order a new coffee: Simple Coffee with Caramel
        Coffee anotherCoffee = new CaramelDecorator(new SimpleCoffee());
        System.out.println(anotherCoffee.getDescription() + " - $" + anotherCoffee.getCost()); // Simple Coffee, Caramel - $2.7

        // Order a complex coffee: Simple Coffee with Milk, Sugar, and Caramel
        Coffee complexCoffee = new CaramelDecorator(new SugarDecorator(new MilkDecorator(new SimpleCoffee())));
        System.out.println(complexCoffee.getDescription() + " - $" + complexCoffee.getCost()); // Simple Coffee, Milk, Sugar, Caramel - $3.4
    }
}
