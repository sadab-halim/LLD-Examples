package prototype.ea;

public class ShapeDemo {
    public static void main(String[] args) {
        // Create an original "prototype" object
        System.out.println("--- SHALLOW COPY ---");
        Circle originalCircle = new Circle(10, 20, "Red", 50);
        System.out.println("Original: " + originalCircle);

        // Clone the original object
        Circle clonedCircle = (Circle) originalCircle.clone();
        System.out.println("Cloned:   " + clonedCircle);

        // Modify the clone. This will not affect the original.
        clonedCircle.setColor("Blue");

        System.out.println("\nAfter modifying clone:");
        System.out.println("Original: " + originalCircle); // Still Red
        System.out.println("Cloned:   " + clonedCircle);   // Now Blue

        // Prove they are different objects in memory
        System.out.println("Original hash: " + originalCircle.hashCode());
        System.out.println("Cloned hash:   " + clonedCircle.hashCode());
    }
}
