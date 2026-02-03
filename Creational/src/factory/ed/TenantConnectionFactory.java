package factory.ed;

// 4. The Factory
public class TenantConnectionFactory {
    private final SecretManager secretManager;

    // Dependency Injection via constructor
    public TenantConnectionFactory(SecretManager secretManager) {
        this.secretManager = secretManager;
    }

    public DbConnectionPool createConnectionPool(TenantRoutingConfig config) {
        String password = secretManager.getPassword(config.tenantId);

        if ("POSTGRES".equalsIgnoreCase(config.dbType)) {
            // Logic specific to optimizing Postgres pools
            return new PostgresPool(config.host, "admin", password);
        } else if ("MYSQL".equalsIgnoreCase(config.dbType)) {
            // Logic specific to optimizing MySQL pools
            return new MySQLPool(config.host, "admin", password);
        } else {
            throw new IllegalArgumentException("Unsupported DB Type: " + config.dbType);
        }
    }
}
