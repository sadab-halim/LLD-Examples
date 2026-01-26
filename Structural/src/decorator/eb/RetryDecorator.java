package decorator.eb;

public class RetryDecorator extends ServiceClientDecorator {
    private final RetryPolicy retryPolicy;

    public RetryDecorator(ServiceClient delegate, RetryPolicy retryPolicy) {
        super(delegate);
        this.retryPolicy = retryPolicy;
    }

    @Override
    public ServiceResponse call(ServiceRequest request) throws ServiceException {
        int attempt = 0;
        ServiceException lastException = null;

        while (attempt < retryPolicy.getMaxAttempts()) {
            try {
                ServiceResponse response = delegate.call(request);

                if (response.isSuccess() || !retryPolicy.shouldRetry(response)) {
                    return response;
                }

                attempt++;
                if (attempt < retryPolicy.getMaxAttempts()) {
                    long backoffMs = retryPolicy.getBackoffMs(attempt);
                    Thread.sleep(backoffMs);
                }

            } catch (ServiceException e) {
                lastException = e;
                attempt++;

                if (!retryPolicy.isRetryableException(e)) {
                    throw e;
                }

                if (attempt < retryPolicy.getMaxAttempts()) {
                    try {
                        long backoffMs = retryPolicy.getBackoffMs(attempt);
                        Thread.sleep(backoffMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new ServiceException("Retry interrupted", ie);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ServiceException("Retry sleep interrupted", e);
            }
        }

        throw new ServiceException("Max retries exceeded", lastException);
    }
}
