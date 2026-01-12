package factory.ec;

// 4. Concrete Factories
// This factory ensures that if you want Mac components, you get ALL Mac components.
public class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
