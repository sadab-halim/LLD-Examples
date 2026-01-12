package factory.ec;

// 2. Concrete Products (Variant 2: Windows)
public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a Windows Button");
    }
}
