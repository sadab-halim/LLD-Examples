package abstractFactory.ec;

public class GameDemo {
    public static void main(String[] args) {
        // Level 1: Fighting Humans
        System.out.println("--- Level 1 Starts ---");
        GameLevel level1 = new GameLevel(new HumanArmyFactory());
        level1.spawnWave();

        // Level 2: Fighting Orcs
        System.out.println("\n--- Level 2 Starts ---");
        GameLevel level2 = new GameLevel(new OrcArmyFactory());
        level2.spawnWave();
    }
}
