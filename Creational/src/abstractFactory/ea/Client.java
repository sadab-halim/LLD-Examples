package abstractFactory.ea;

// 5. Client Code
public class Client {
    private final Chair chair;
    private final Sofa sofa;

    // The client accepts the Factory interface.
    // It doesn't know if it's creating Modern or Victorian furniture.
    public Client(FurnitureFactory factory) {
        this.chair = factory.createChair();
        this.sofa = factory.createSofa();
    }

    public void useFurniture() {
        chair.sitOn();
        sofa.lieOn();
    }
}
