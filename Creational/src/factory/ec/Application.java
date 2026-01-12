package factory.ec;

// 5. Client Code
public class Application {
    private Button button;
    private Checkbox checkbox;

    // The client doesn't know if it's Mac or Windows.
    // It just uses the factory interface.
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}
