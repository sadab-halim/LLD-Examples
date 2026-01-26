package chainOfResponsibility.ea;

public interface PermissionService {
    boolean hasPermission(UserContext user, String resource, String action);
}
