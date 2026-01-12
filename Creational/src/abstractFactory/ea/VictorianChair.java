package abstractFactory.ea;

// 2. Concrete Products (Family 2: Victorian)
public class VictorianChair implements Chair {
    @Override
    public void sitOn() {
        System.out.println("Sitting on an elegant Victorian Chair.");
    }
}
