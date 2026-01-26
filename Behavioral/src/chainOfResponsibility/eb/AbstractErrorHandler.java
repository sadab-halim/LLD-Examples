package chainOfResponsibility.eb;

abstract class AbstractErrorHandler implements ErrorHandler {
    protected ErrorHandler next;

    @Override
    public ErrorHandler setNext(ErrorHandler next) {
        this.next = next;
        return next;
    }

    @Override
    public RecoveryResult handle(Exception exception, OrderContext context) {
        if (canHandle(exception)) {
            try {
                return attemptRecovery(exception, context);
            } catch (Exception e) {
                // Recovery failed, try next handler
                if (next != null) {
                    return next.handle(exception, context);
                }
                return RecoveryResult.failure("All recovery attempts exhausted");
            }
        }

        if (next != null) {
            return next.handle(exception, context);
        }

        return RecoveryResult.failure("No handler available for: " +
                exception.getClass().getSimpleName());
    }

    protected abstract RecoveryResult attemptRecovery(Exception exception,
                                                      OrderContext context)
            throws Exception;
}
