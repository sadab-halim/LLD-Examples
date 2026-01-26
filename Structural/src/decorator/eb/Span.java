package decorator.eb;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Span {
    private final String operationName;
    private final SpanContext context;
    private final Map<String, Object> tags = new HashMap<>();
    private final long startTime;
    private long endTime;

    public Span(String operationName, String traceId) {
        this.operationName = operationName;
        this.context = new SpanContext(traceId, UUID.randomUUID().toString());
        this.startTime = System.currentTimeMillis();
    }

    public SpanContext getContext() { return context; }

    public void setTag(String key, Object value) {
        tags.put(key, value);
    }

    public void finish() {
        this.endTime = System.currentTimeMillis();
        System.out.println("Span finished: " + operationName +
                " duration=" + (endTime - startTime) + "ms " +
                "tags=" + tags);
    }
}
