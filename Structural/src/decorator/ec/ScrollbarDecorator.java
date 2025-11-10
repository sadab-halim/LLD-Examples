package decorator.ec;

public class ScrollbarDecorator extends ComponentDecorator {
    public ScrollbarDecorator(GUIComponent component) {
        super(component);
    }

    @Override
    public void display() {
        System.out.println(">>> Adding Scrollbar <<<"); // Add behavior before
        super.display(); // Call the wrapped component's display
        System.out.println(">>> Scrollbar Added <<<"); // Add behavior after
    }
}
