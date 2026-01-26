package decorator.eb;

public class MetricsDecorator extends ServiceClientDecorator {
    private final MetricsCollector metricsCollector;
    private final String serviceName;

    public MetricsDecorator(ServiceClient delegate,
                            MetricsCollector metricsCollector,
                            String serviceName) {
        super(delegate);
        this.metricsCollector = metricsCollector;
        this.serviceName = serviceName;
    }

    @Override
    public ServiceResponse call(ServiceRequest request) throws ServiceException {
        long startTime = System.currentTimeMillis();

        try {
            ServiceResponse response = delegate.call(request);

            long duration = System.currentTimeMillis() - startTime;
            metricsCollector.recordLatency(serviceName, request.getEndpoint(), duration);
            metricsCollector.recordStatusCode(serviceName, response.getStatusCode());

            return response;

        } catch (ServiceException e) {
            long duration = System.currentTimeMillis() - startTime;
            metricsCollector.recordLatency(serviceName, request.getEndpoint(), duration);
            metricsCollector.recordError(serviceName, e.getClass().getSimpleName());
            throw e;
        }
    }
}
