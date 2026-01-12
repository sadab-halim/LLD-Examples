package abstractFactory.ec;

public class OrcArmyFactory implements ArmyFactory {
    @Override
    public Warrior createWarrior() { return new OrcWarrior(); }
    @Override
    public Mage createMage() { return new OrcMage(); }
}
