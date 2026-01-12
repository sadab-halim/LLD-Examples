package abstractFactory.ea;

// 3. Abstract Factory Interface
// This declares methods to create EACH type of product in the family.
public interface FurnitureFactory {
    Chair createChair();
    Sofa createSofa();
}
