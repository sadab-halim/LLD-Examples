package abstractFactory.ea;

public class FurnitureDemo {
    public static void main(String[] args) {
        // Customer wants a Modern setup
        FurnitureFactory modernFactory = new ModernFurnitureFactory();
        Client client1 = new Client(modernFactory);
        client1.useFurniture();
        // Output: Sleek Modern Chair... Minimalist Modern Sofa...

        System.out.println("---");

        // Customer wants a Victorian setup
        FurnitureFactory victorianFactory = new VictorianFurnitureFactory();
        Client client2 = new Client(victorianFactory);
        client2.useFurniture();
        // Output: Elegant Victorian Chair... Tufted Victorian Sofa...
    }
}
