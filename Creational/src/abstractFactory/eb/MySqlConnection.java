package abstractFactory.eb;

// 2. Concrete Products (Family 1: MySQL)
public class MySqlConnection implements Connection {
    @Override
    public void open() {
        System.out.println("Opening MySQL Connection...");
    }
}
