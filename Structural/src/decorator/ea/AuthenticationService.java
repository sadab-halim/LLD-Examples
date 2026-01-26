package decorator.ea;

public class AuthenticationService {
    public User validateToken(String token) throws InvalidTokenException {
        // Simulate token validation
        if (token.startsWith("Bearer ")) {
            String tokenValue = token.substring(7);
            if (tokenValue.equals("valid-token-123")) {
                return new User("user-123", "john.doe@example.com");
            }
        }
        throw new InvalidTokenException("Invalid token");
    }
}
