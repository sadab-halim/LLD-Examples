package abstractFactory.ec;

// 2. Concrete Products (Family 2: Orcs)
public class OrcWarrior implements Warrior {
    @Override
    public void attack() {
        System.out.println("Orc Warrior swings huge axe!");
    }
}
