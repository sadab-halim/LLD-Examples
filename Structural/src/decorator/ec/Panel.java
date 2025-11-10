package decorator.ec;

public class Panel implements GUIComponent {
    private String name;

    public Panel(String name) {
        this.name = name;
    }

    @Override
    public void display() {
        System.out.println("Displaying Panel: " + name);
    }
}
