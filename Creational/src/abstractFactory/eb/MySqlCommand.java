package abstractFactory.eb;

public class MySqlCommand implements Command {
    @Override
    public void execute(String query) {
        System.out.println("Executing MySQL Query: " + query);
    }
}
