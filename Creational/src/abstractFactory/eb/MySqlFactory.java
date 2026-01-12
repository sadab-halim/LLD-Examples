package abstractFactory.eb;

// 4. Concrete Factories
public class MySqlFactory implements DatabaseFactory {
    @Override
    public Connection createConnection() { return new MySqlConnection(); }
    @Override
    public Command createCommand() { return new MySqlCommand(); }
}
