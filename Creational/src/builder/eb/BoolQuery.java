package builder.eb;

import java.util.List;
import java.util.stream.Collectors;

public class BoolQuery implements Query {
    private final List<Query> mustClauses;
    private final List<Query> shouldClauses;

    public BoolQuery(List<Query> must, List<Query> should) {
        this.mustClauses = must;
        this.shouldClauses = should;
    }

    @Override
    public String toJson() {
        String mustJson = mustClauses.stream().map(Query::toJson).collect(Collectors.joining(","));
        String shouldJson = shouldClauses.stream().map(Query::toJson).collect(Collectors.joining(","));
        return String.format("{ \"bool\": { \"must\": [%s], \"should\": [%s] } }", mustJson, shouldJson);
    }
}
