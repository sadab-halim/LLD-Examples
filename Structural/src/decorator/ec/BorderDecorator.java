package decorator.ec;

// 4. Concrete Decorators
public class BorderDecorator extends ComponentDecorator {
    private String borderStyle;

    public BorderDecorator(GUIComponent component, String borderStyle) {
        super(component);
        this.borderStyle = borderStyle;
    }

    @Override
    public void display() {
        System.out.println("--- " + borderStyle + " Border ---"); // Add behavior before
        super.display(); // Call the wrapped component's display
        System.out.println("--- " + borderStyle + " Border ---"); // Add behavior after
    }
}
