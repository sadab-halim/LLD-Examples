package decorator.ec;

// 2. Concrete Component
public class Button implements GUIComponent {
    private String label;

    public Button(String label) {
        this.label = label;
    }

    @Override
    public void display() {
        System.out.println("Displaying Button: " + label);
    }
}
