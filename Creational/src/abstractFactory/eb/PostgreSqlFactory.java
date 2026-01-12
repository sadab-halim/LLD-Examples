package abstractFactory.eb;

public class PostgreSqlFactory implements DatabaseFactory {
    @Override
    public Connection createConnection() { return new PostgreSqlConnection(); }
    @Override
    public Command createCommand() { return new PostgreSqlCommand(); }
}
