package prototype.eb;

// 2. Concrete Prototype: Car
public class Car implements Vehicle{
    private String model;

    public Car(String model) {
        this.model = model;
        // Simulate a costly creation process
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println(model + " car created (costly operation).");
    }

    public void setModel(String model) { this.model = model; }

    @Override
    public void drive() {
        System.out.println("Driving a " + model + " car.");
    }

    // 3. The clone implementation using Object.clone()
    @Override
    public Vehicle clone() throws CloneNotSupportedException {
        System.out.println("Cloning a " + model + " car (cheap operation).");
        // super.clone() performs a shallow copy of the object.
        return (Car) super.clone();
    }
}
