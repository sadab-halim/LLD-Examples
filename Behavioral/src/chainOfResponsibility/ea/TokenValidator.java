package chainOfResponsibility.ea;

public interface TokenValidator {
    UserContext validate(String token) throws InvalidTokenException;
}
