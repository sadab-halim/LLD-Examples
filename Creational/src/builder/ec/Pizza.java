package builder.ec;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private final String dough;
    private final String sauce;
    private final String cheese;
    private final List<String> toppings;

    // Private constructor.
    private Pizza(PizzaBuilder builder) {
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.cheese = builder.cheese;
        this.toppings = builder.toppings;
    }

    @Override
    public String toString() {
        return "Pizza Specs:\n" +
                "  Dough: " + dough + "\n" +
                "  Sauce: " + sauce + "\n" +
                "  Cheese: " + cheese + "\n" +
                "  Toppings: " + toppings;
    }

    // Static nested Builder class.
    public static class PizzaBuilder {
        // Optional fields with default values.
        private String dough = "Regular Crust";
        private String sauce = "Tomato";
        private String cheese = "Mozzarella";
        private List<String> toppings = new ArrayList<>();

        // The builder has a public constructor with no arguments.
        public PizzaBuilder() {}

        // Methods to set each part of the pizza.
        // Each returns the builder to allow chaining.
        public PizzaBuilder dough(String dough) {
            this.dough = dough;
            return this;
        }

        public PizzaBuilder sauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public PizzaBuilder cheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public PizzaBuilder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        // The final build() method to create the Pizza.
        public Pizza build() {
            return new Pizza(this);
        }
    }

    // Main method to demonstrate usage.
    public static void main(String[] args) {
        // Build a classic pepperoni pizza.
        Pizza pepperoniPizza = new Pizza.PizzaBuilder()
                .addTopping("Pepperoni")
                .build(); // Uses default dough, sauce, and cheese
        System.out.println(pepperoniPizza);
        System.out.println("----------");

        // Build a custom veggie pizza.
        Pizza veggiePizza = new Pizza.PizzaBuilder()
                .dough("Thin Crust")
                .sauce("Pesto")
                .cheese("Feta")
                .addTopping("Spinach")
                .addTopping("Olives")
                .addTopping("Red Onion")
                .build();
        System.out.println(veggiePizza);
    }
}