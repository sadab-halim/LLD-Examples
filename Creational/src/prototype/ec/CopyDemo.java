package prototype.ec;

public class CopyDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        // Create the original document
        Document original = new Document("Prototype Pattern");
        original.addAuthor("Erich Gamma");
        original.addAuthor("Richard Helm");

        System.out.println("Original: " + original);
        System.out.println("Original Authors hash: " + original.getAuthors().hashCode());
        System.out.println("---------------------------------");

        // --- Test Shallow Copy ---
        Document shallowCopy = original.shallowClone();
        System.out.println("Shallow:  " + shallowCopy);
        System.out.println("Shallow Authors hash:  " + shallowCopy.getAuthors().hashCode());

        // Modify the author list in the shallow copy
        shallowCopy.addAuthor("Ralph Johnson");

        System.out.println("\n...After modifying shallow copy's author list...");
        System.out.println("Original: " + original); // !!! PROBLEM: Original is also modified!
        System.out.println("Shallow:  " + shallowCopy);
        System.out.println("REASON: Both 'original' and 'shallowCopy' point to the *same* List object.\n");
        System.out.println("---------------------------------");

        // --- Test Deep Copy ---
        // Let's reset the original for a fair test
        Document original2 = new Document("Design Patterns");
        original2.addAuthor("GoF");

        System.out.println("Original 2: " + original2);
        System.out.println("Original 2 Authors hash: " + original2.getAuthors().hashCode());

        Document deepCopy = original2.deepClone();
        System.out.println("Deep:     " + deepCopy);
        System.out.println("Deep Authors hash:     " + deepCopy.getAuthors().hashCode()); // A different hash!

        // Modify the author list in the deep copy
        deepCopy.addAuthor("New Author");

        System.out.println("\n...After modifying deep copy's author list...");
        System.out.println("Original 2: " + original2); // Unchanged, as expected!
        System.out.println("Deep:     " + deepCopy);
        System.out.println("REASON: 'deepCopy' has its own, separate List object.");
    }
}
