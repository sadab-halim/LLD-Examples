package factory.ea;

// 2. Concrete Product
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle.");
    }
}
