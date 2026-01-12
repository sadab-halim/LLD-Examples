package abstractFactory.ec;

// 5. Client Code (The Game Engine)
public class GameLevel {
    private final ArmyFactory armyFactory;

    public GameLevel(ArmyFactory factory) {
        this.armyFactory = factory;
    }

    public void spawnWave() {
        // Spawn a balanced wave of units for this specific race
        Warrior w1 = armyFactory.createWarrior();
        Warrior w2 = armyFactory.createWarrior();
        Mage m1 = armyFactory.createMage();

        System.out.println("Spawning Wave:");
        w1.attack();
        w2.attack();
        m1.castSpell();
    }
}
