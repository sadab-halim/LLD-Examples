package decorator.ec;

// 3. Decorator Abstract Class
abstract class ComponentDecorator implements GUIComponent {
    protected GUIComponent decoratedComponent;

    public ComponentDecorator(GUIComponent component) {
        this.decoratedComponent = component;
    }

    @Override
    public void display() {
        decoratedComponent.display(); // Delegate to the wrapped component
    }
}
