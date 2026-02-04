package builder.eb;

public class TermQueryBuilder implements QueryBuilder {
    private final String field;
    private final String value;

    public TermQueryBuilder(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public Query build() {
        return new TermQuery(field, value);
    }
}
