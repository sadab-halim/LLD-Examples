package prototype.eb;

// 1. The Prototype interface (can also be an abstract class)
// We'll use the built-in Object.clone() method this time.
public interface Vehicle extends Cloneable{
    Vehicle clone() throws CloneNotSupportedException; // Make clone() public
    void drive();
}
