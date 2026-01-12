package factory.ea;

// 2. Concrete Product
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Square.");
    }
}
