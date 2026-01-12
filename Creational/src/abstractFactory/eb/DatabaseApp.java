package abstractFactory.eb;

// 5. Client Code
public class DatabaseApp {
    private final DatabaseFactory factory;

    public DatabaseApp(DatabaseFactory factory) {
        this.factory = factory;
    }

    public void runQuery(String query) {
        // The factory guarantees the connection and command match.
        Connection connection = factory.createConnection();
        Command command = factory.createCommand();

        connection.open();
        command.execute(query);
    }
}
