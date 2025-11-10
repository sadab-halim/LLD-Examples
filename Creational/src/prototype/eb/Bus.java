package prototype.eb;

// 2. Concrete Prototype: Bus
public class Bus implements Vehicle{
    private int capacity;

    public Bus(int capacity) {
        this.capacity = capacity;
        // Simulate a costly creation process
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("Bus with capacity " + capacity + " created (costly operation).");
    }

    @Override
    public void drive() {
        System.out.println("Driving a bus with capacity " + capacity + ".");
    }

    // 3. The clone implementation
    @Override
    public Vehicle clone() throws CloneNotSupportedException {
        System.out.println("Cloning a bus (cheap operation).");
        return (Bus) super.clone();
    }
}
