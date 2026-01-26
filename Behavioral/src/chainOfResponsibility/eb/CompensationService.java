package chainOfResponsibility.eb;

public interface CompensationService {
    void compensate(Order order);
    void logForReview(String orderId, String reason);
}
