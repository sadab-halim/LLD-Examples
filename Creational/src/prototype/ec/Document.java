package prototype.ec;

import java.util.ArrayList;
import java.util.List;

// 1. The Prototype: Document
// This time, it contains a List<Author>, which is a mutable reference type.
// We must implement Cloneable to use Object.clone().
public class Document implements Cloneable {
    private String title;
    private List<Author> authors; // This is a reference to an object

    public Document(String title) {
        this.title = title;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(String name) {
        this.authors.add(new Author(name));
    }

    public List<Author> getAuthors() { return this.authors; }

    @Override
    public String toString() {
        return "Document '" + title + "' by " + authors;
    }

    // --- Shallow Copy Implementation ---
    // This is the default behavior of Object.clone()
    public Document shallowClone() throws CloneNotSupportedException {
        System.out.println("Performing SHALLOW clone of Document...");
        // super.clone() copies primitives (title) and references (authors list)
        return (Document) super.clone();
    }

    // --- Deep Copy Implementation ---
    // This is a custom clone method to show the difference.
    // For a "true" deep clone, Author should also be Cloneable.
    // For this example, we will just create new Author objects manually.
    public Document deepClone() throws CloneNotSupportedException {
        System.out.println("Performing DEEP clone of Document...");

        // 1. Start with a shallow copy
        Document clonedDoc = (Document) super.clone();

        // 2. Fix the reference fields. Create a new List.
        clonedDoc.authors = new ArrayList<>();

        // 3. Copy the *contents* of the original list into the new list.
        // We create new Author objects to ensure they are distinct.
        for (Author author : this.authors) {
            clonedDoc.authors.add(new Author(author.name)); // Creating new Author objects
        }

        return clonedDoc;
    }
}
