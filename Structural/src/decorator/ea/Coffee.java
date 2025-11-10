package decorator.ea;

// 1. Component Interface
// This defines the common interface for both concrete components and decorators.
public interface Coffee {
    String getDescription(); // Describes the coffee
    double getCost();        // Returns the cost of the coffee
}
