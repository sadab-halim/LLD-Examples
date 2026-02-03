package factory.ed;

// 2. Concrete Implementations
public class PostgresPool implements DbConnectionPool {
    private final String connectionString;

    public PostgresPool(String host, String user, String pass) {
        this.connectionString = String.format("jdbc:postgresql://%s?user=%s", host, user);
        System.out.println("HikariCP Pool created for Postgres: " + host);
    }

    @Override
    public void executeQuery(String sql) { System.out.println("PG Exec: " + sql); }
    @Override
    public void close() { System.out.println("PG Pool closed"); }
}
