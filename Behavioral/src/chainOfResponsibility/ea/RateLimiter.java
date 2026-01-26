package chainOfResponsibility.ea;

public interface RateLimiter {
    boolean allowRequest(String key);
}
