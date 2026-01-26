package decorator.eb;

public class TracingDecorator extends ServiceClientDecorator {
    private final DistributedTracer tracer;

    public TracingDecorator(ServiceClient delegate, DistributedTracer tracer) {
        super(delegate);
        this.tracer = tracer;
    }

    @Override
    public ServiceResponse call(ServiceRequest request) throws ServiceException {
        Span span = tracer.startSpan("service.call");
        span.setTag("endpoint", request.getEndpoint());
        span.setTag("method", request.getMethod());

        try {
            // Inject trace context into request headers
            tracer.inject(span.getContext(), request.getHeaders());

            ServiceResponse response = delegate.call(request);

            span.setTag("status_code", response.getStatusCode());
            span.setTag("latency_ms", response.getLatencyMs());

            if (!response.isSuccess()) {
                span.setTag("error", true);
            }

            return response;

        } catch (ServiceException e) {
            span.setTag("error", true);
            span.setTag("error.message", e.getMessage());
            throw e;

        } finally {
            span.finish();
        }
    }
}
