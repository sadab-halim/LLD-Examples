package builder.eb;

// 3. Leaf Implementation
public class TermQuery implements Query {
    private final String field;
    private final String value;

    public TermQuery(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String toJson() {
        return String.format("{ \"term\": { \"%s\": \"%s\" } }", field, value);
    }
}
