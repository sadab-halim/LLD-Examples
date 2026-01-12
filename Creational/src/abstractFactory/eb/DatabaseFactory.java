package abstractFactory.eb;

// 3. Abstract Factory
public interface DatabaseFactory {
    Connection createConnection();
    Command createCommand();
}
