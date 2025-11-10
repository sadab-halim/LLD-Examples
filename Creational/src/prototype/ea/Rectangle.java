package prototype.ea;

// 2. Concrete Prototype: Rectangle
public class Rectangle extends Shape{
    private int width;
    private int height;

    public Rectangle(int x, int y, String color, int width, int height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle with width " + width + " and height " + height);
    }

    // 3. The clone implementation
    @Override
    public Shape clone() {
        // Using the copy constructor
        return new Rectangle(this);
    }

    // Copy constructor for Rectangle
    public Rectangle(Rectangle source) {
        super(source);
        this.width = source.width;
        this.height = source.height;
    }

    @Override
    public String toString() {
        return "Rectangle. " + super.toString() + ", Width: " + width + ", Height: " + height;
    }
}
