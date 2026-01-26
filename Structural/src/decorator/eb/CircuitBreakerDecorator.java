package decorator.eb;

public class CircuitBreakerDecorator extends ServiceClientDecorator {
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakerDecorator(ServiceClient delegate,
                                   CircuitBreaker circuitBreaker) {
        super(delegate);
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public ServiceResponse call(ServiceRequest request) throws ServiceException {
        if (circuitBreaker.isOpen()) {
            throw new ServiceException("Circuit breaker is OPEN");
        }

        try {
            ServiceResponse response = delegate.call(request);

            if (response.isSuccess()) {
                circuitBreaker.recordSuccess();
            } else {
                circuitBreaker.recordFailure();
            }

            return response;

        } catch (ServiceException e) {
            circuitBreaker.recordFailure();
            throw e;
        }
    }
}
