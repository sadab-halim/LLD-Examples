package prototype.ea;

// 2. Concrete Prototype: Circle
public class Circle extends Shape{
    private int radius;

    public Circle(int x, int y, String color, int radius) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Circle with radius " + radius);
    }

    // 3. The clone implementation
    // This creates a new Circle object by copying the current one.
    @Override
    public Shape clone() {
        // For simple objects (primitives, Strings), a super.clone() (shallow copy)
        // is often sufficient, but we'll use the copy constructor for clarity here.
        // A more "pure" prototype pattern might rely on super.clone().
        return new Circle(this);
    }

    // Copy constructor for Circle
    public Circle(Circle source) {
        super(source); // Calls the Shape copy constructor
        this.radius = source.radius;
    }

    @Override
    public String toString() {
        return "Circle. " + super.toString() + ", Radius: " + radius;
    }
}
