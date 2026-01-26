package chainOfResponsibility.eb;

public class EscalationHandler extends AbstractErrorHandler {
    private final AlertingService alertingService;

    public EscalationHandler(AlertingService alertingService) {
        this.alertingService = alertingService;
    }

    @Override
    public boolean canHandle(Exception exception) {
        return true; // Final handler catches everything
    }

    @Override
    protected RecoveryResult attemptRecovery(Exception exception,
                                             OrderContext context) {
        context.addAttemptedRecovery("ESCALATION");

        // Alert on-call
        alertingService.alert(
                "Order processing failed",
                "OrderId: " + context.getOrderId() +
                        ", Exception: " + exception.getMessage()
        );

        // Log detailed context
        System.err.println("ESCALATED - Order: " + context.getOrderId() +
                ", Attempts: " + context.getRetryCount());

        return RecoveryResult.success(
                "Escalated to on-call team",
                "EscalationHandler",
                RecoveryResult.RecoveryAction.ESCALATED
        );
    }
}
