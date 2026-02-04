package builder.eb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoolQueryBuilder implements QueryBuilder {
    private List<QueryBuilder> mustBuilders = new ArrayList<>();
    private List<QueryBuilder> shouldBuilders = new ArrayList<>();

    // Recursive API: Accepts Builders, not just Queries
    public BoolQueryBuilder must(QueryBuilder qb) {
        mustBuilders.add(qb);
        return this;
    }

    public BoolQueryBuilder should(QueryBuilder qb) {
        shouldBuilders.add(qb);
        return this;
    }

    @Override
    public Query build() {
        // Build children recursively
        List<Query> must = mustBuilders.stream().map(QueryBuilder::build).collect(Collectors.toList());
        List<Query> should = shouldBuilders.stream().map(QueryBuilder::build).collect(Collectors.toList());
        return new BoolQuery(must, should);
    }
}
