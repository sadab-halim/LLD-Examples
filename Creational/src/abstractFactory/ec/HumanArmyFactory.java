package abstractFactory.ec;

// 4. Concrete Factories
public class HumanArmyFactory implements ArmyFactory {
    @Override
    public Warrior createWarrior() {
        return new HumanWarrior();
    }

    @Override
    public Mage createMage() {
        return new HumanMage();
    }
}
