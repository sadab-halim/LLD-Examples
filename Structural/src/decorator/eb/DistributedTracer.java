package decorator.eb;

import java.util.Map;
import java.util.UUID;

public class DistributedTracer {
    private final ThreadLocal<Span> currentSpan = new ThreadLocal<>();

    public Span startSpan(String operationName) {
        Span span = new Span(operationName, generateTraceId());
        currentSpan.set(span);
        return span;
    }

    public void inject(SpanContext context, Map<String, String> headers) {
        headers.put("X-Trace-Id", context.getTraceId());
        headers.put("X-Span-Id", context.getSpanId());
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
