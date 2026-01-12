package factory.ea;

// 2. Concrete Product
public class ShapeFactory {
    // The "factory method" (often static in simple factories)
    public static Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }

        throw new IllegalArgumentException("Unknown shape type: " + shapeType);
    }
}
