package chainOfResponsibility.eb;

public class FallbackHandler extends AbstractErrorHandler {
    private final FallbackService fallbackService;

    public FallbackHandler(FallbackService fallbackService) {
        this.fallbackService = fallbackService;
    }

    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof ServiceUnavailableException;
    }

    @Override
    protected RecoveryResult attemptRecovery(Exception exception,
                                             OrderContext context) throws Exception {
        context.addAttemptedRecovery("FALLBACK");

        // Try cached data or alternative service
        boolean fallbackSuccess = fallbackService.processWithFallback(context.getOrder());

        if (fallbackSuccess) {
            return RecoveryResult.success(
                    "Used fallback service/cached data",
                    "FallbackHandler",
                    RecoveryResult.RecoveryAction.FALLBACK_USED
            );
        }

        throw new Exception("Fallback failed");
    }
}
