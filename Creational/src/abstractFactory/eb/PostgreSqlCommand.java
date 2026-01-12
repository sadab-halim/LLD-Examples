package abstractFactory.eb;

public class PostgreSqlCommand implements Command {
    @Override
    public void execute(String query) {
        System.out.println("Executing PostgreSQL Query: " + query);
    }
}
