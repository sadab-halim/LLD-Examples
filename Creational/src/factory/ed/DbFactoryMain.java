package factory.ed;

public class DbFactoryMain {
    public static void main(String[] args) {
        // 1. Setup Dependencies (Mocking the External Secret Manager)
        SecretManager secretManager = new SecretManager();

        // 2. Initialize Factory with dependencies
        TenantConnectionFactory factory = new TenantConnectionFactory(secretManager);

        // 3. Scenario A: Tenant 101 uses PostgreSQL
        System.out.println("--- Requesting DB for Tenant 101 ---");
        TenantRoutingConfig configA = new TenantRoutingConfig();
        configA.tenantId = "tenant_101";
        configA.dbType = "POSTGRES";
        configA.host = "db-primary-us-east.aws.com";

        DbConnectionPool poolA = factory.createConnectionPool(configA);
        poolA.executeQuery("SELECT * FROM users"); // Prints "PG Exec..."

        // 4. Scenario B: Tenant 202 uses MySQL (Legacy)
        System.out.println("\n--- Requesting DB for Tenant 202 ---");
        TenantRoutingConfig configB = new TenantRoutingConfig();
        configB.tenantId = "tenant_202";
        configB.dbType = "MYSQL";
        configB.host = "mysql-legacy-cluster";

        DbConnectionPool poolB = factory.createConnectionPool(configB);
        poolB.executeQuery("SELECT * FROM orders"); // Prints "MySQL Exec..."

        // 5. Scenario C: Unsupported Config (Safety check)
        try {
            System.out.println("\n--- Requesting DB for Tenant 999 (Oracle) ---");
            TenantRoutingConfig configC = new TenantRoutingConfig();
            configC.tenantId = "tenant_999";
            configC.dbType = "ORACLE"; // Not implemented in factory
            factory.createConnectionPool(configC);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

