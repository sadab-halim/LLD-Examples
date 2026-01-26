package chainOfResponsibility.ea;

import java.util.Set;

public class UserContext {
    private final String userId;
    private final String username;
    private final Set<String> roles;

    public UserContext(String userId, String username, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public boolean hasRole(String role) { return roles.contains(role); }
}
