package prototype.ea;

// 1. The Prototype: An abstract class implementing Cloneable.
// Cloneable is a "marker interface" that tells the JVM it's okay for this object
// to be copied using the Object.clone() method.
public abstract class Shape implements Cloneable {
    private int x;
    private int y;
    private String color;

    // Constructor
    public Shape(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // Copy constructor (another way to implement the pattern, but clone() is more common)
    public Shape(Shape source) {
        this.x = source.x;
        this.y = source.y;
        this.color = source.color;
    }

    // Abstract method to be implemented by concrete classes
    public abstract void draw();

    // Abstract method for cloning
    // We will override this in concrete subclasses.
    public abstract Shape clone();

    // Getters and Setters (omitted for brevity)
    public void setColor(String color) { this.color = color; }
    public String getColor() { return this.color; }

    @Override
    public String toString() {
        return "Position: (" + x + "," + y + "), Color: " + color;
    }
}
