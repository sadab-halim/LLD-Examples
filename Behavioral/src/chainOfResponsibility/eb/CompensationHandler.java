package chainOfResponsibility.eb;

public class CompensationHandler extends AbstractErrorHandler {
    private final CompensationService compensationService;

    public CompensationHandler(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    @Override
    public boolean canHandle(Exception exception) {
        // Handle cases where partial work was done
        return exception instanceof PaymentFailedException ||
                exception instanceof InsufficientInventoryException;
    }

    @Override
    protected RecoveryResult attemptRecovery(Exception exception,
                                             OrderContext context) throws Exception {
        context.addAttemptedRecovery("COMPENSATION");
        context.getOrder().setStatus(Order.OrderStatus.COMPENSATING);

        // Rollback completed steps
        compensationService.compensate(context.getOrder());

        // Log for manual review
        compensationService.logForReview(context.getOrderId(), exception.getMessage());

        return RecoveryResult.success(
                "Order compensated and logged for manual review",
                "CompensationHandler",
                RecoveryResult.RecoveryAction.COMPENSATED
        );
    }
}
