package abstractFactory.eb;

// 2. Concrete Products (Family 2: PostgreSQL)
public class PostgreSqlConnection implements Connection {
    @Override
    public void open() {
        System.out.println("Opening PostgreSQL Connection...");
    }
}
