package abstractFactory.ea;

// 2. Concrete Products (Family 1: Modern)
public class ModernChair implements Chair {
    @Override
    public void sitOn() {
        System.out.println("Sitting on a sleek Modern Chair.");
    }
}
