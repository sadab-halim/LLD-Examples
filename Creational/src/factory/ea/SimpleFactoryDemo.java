package factory.ea;

public class SimpleFactoryDemo {
    public static void main(String[] args) {
        // The client doesn't call 'new Circle()' directly.
        // It asks the factory to do it.
        Shape shape1 = ShapeFactory.getShape("CIRCLE");
        shape1.draw();

        Shape shape2 = ShapeFactory.getShape("RECTANGLE");
        shape2.draw();
    }
}
