package chainOfResponsibility.eb;

public class RetryableErrorHandler extends AbstractErrorHandler {
    private static final int MAX_RETRIES = 3;
    private static final long BASE_DELAY_MS = 100;

    @Override
    protected RecoveryResult attemptRecovery(Exception exception, OrderContext context) throws Exception {
        if (context.getRetryCount() >= MAX_RETRIES) {
            throw new Exception("Max retries exceeded");
        }

        context.incrementRetryCount();
        context.addAttemptedRecovery("RETRY");

        // Exponential backoff
        long delay = BASE_DELAY_MS * (long) Math.pow(2, context.getRetryCount() - 1);
        Thread.sleep(delay);

        // Simulate retry logic (in real system, would re-invoke failed operation)
        boolean retrySuccess = simulateRetry(context);

        if (retrySuccess) {
            return RecoveryResult.success(
                    "Recovered after " + context.getRetryCount() + " retries",
                    "RetryableErrorHandler",
                    RecoveryResult.RecoveryAction.RETRY_SUCCEEDED
            );
        }

        throw new Exception("Retry failed");
    }

    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof TransientException;
    }

    private boolean simulateRetry(OrderContext context) {
        // In real system: re-invoke the failed service call
        return context.getRetryCount() >= 2; // Succeed on 2nd retry
    }
}
