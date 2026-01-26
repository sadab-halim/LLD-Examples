package chainOfResponsibility.eb;

public class RecoveryResult {
    private final boolean recovered;
    private final String message;
    private final String handlerName;
    private final RecoveryAction actionTaken;

    public enum RecoveryAction {
        RETRY_SUCCEEDED, FALLBACK_USED, COMPENSATED, ESCALATED, FAILED
    }

    private RecoveryResult(boolean recovered, String message,
                           String handlerName, RecoveryAction action) {
        this.recovered = recovered;
        this.message = message;
        this.handlerName = handlerName;
        this.actionTaken = action;
    }

    public static RecoveryResult success(String message, String handler,
                                         RecoveryAction action) {
        return new RecoveryResult(true, message, handler, action);
    }

    public static RecoveryResult failure(String message) {
        return new RecoveryResult(false, message, null, RecoveryAction.FAILED);
    }

    public boolean isRecovered() { return recovered; }
    public String getMessage() { return message; }
    public RecoveryAction getActionTaken() { return actionTaken; }
}
