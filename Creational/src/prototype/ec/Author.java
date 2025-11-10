package prototype.ec;

// A helper class. A Document has a list of Authors.
public class Author {
    public String name;
    public Author(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}
