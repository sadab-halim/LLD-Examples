package chainOfResponsibility.eb;

public interface FallbackService {
    boolean processWithFallback(Order order);
}
