package abstractFactory.ec;

public class HumanMage implements Mage {
    @Override
    public void castSpell() {
        System.out.println("Human Mage casts Fireball!");
    }
}
