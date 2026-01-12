package abstractFactory.ec;

// 2. Concrete Products (Family 1: Humans)
public class HumanWarrior implements Warrior {
    @Override
    public void attack() {
        System.out.println("Human Warrior swings sword!");
    }
}
