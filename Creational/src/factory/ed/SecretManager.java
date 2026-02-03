package factory.ed;

// 3. Supporting Services
public class SecretManager {
    // Simulating AWS Secrets Manager
    public String getPassword(String tenantId) { return "secret_123"; }
}
