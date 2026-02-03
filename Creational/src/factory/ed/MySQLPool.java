package factory.ed;

public class MySQLPool implements DbConnectionPool {
    public MySQLPool(String host, String user, String pass) {
        System.out.println("HikariCP Pool created for MySQL: " + host);
    }
    @Override
    public void executeQuery(String sql) { System.out.println("MySQL Exec: " + sql); }
    @Override
    public void close() { System.out.println("MySQL Pool closed"); }
}
