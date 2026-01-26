package chainOfResponsibility.eb;

public class ServiceUnavailableException extends TransientException {
    public ServiceUnavailableException(String service) {
        super("Service unavailable: " + service);
    }
}
