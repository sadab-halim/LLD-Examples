package chainOfResponsibility.eb;

public interface ErrorHandler {
    RecoveryResult handle(Exception exception, OrderContext context);
    ErrorHandler setNext(ErrorHandler next);
    boolean canHandle(Exception exception);
}
