package chainOfResponsibility.eb;

public class TimeoutException extends RuntimeException {
    public TimeoutException(String operation) {
        super("Operation timed out: " + operation);
    }
}
