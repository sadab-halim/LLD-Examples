package factory.ec;

// 2. Concrete Products (Variant 1: MacOS)
public class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a Mac Button");
    }
}
