package abstractFactory.eb;

public class DatabaseDemo {
    public static void main(String[] args) {
        // Switch between databases easily by changing the factory.

        // Scenario 1: Configured for MySQL
        DatabaseFactory mySqlFactory = new MySqlFactory();
        DatabaseApp app = new DatabaseApp(mySqlFactory);
        app.runQuery("SELECT * FROM users");

        System.out.println("--- Switching Config ---");

        // Scenario 2: Configured for PostgreSQL
        DatabaseFactory pgFactory = new PostgreSqlFactory();
        DatabaseApp app2 = new DatabaseApp(pgFactory);
        app2.runQuery("SELECT * FROM employees");
    }
}
