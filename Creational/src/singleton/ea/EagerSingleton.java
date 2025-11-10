package singleton.ea;

public class EagerSingleton {

    // 1. Create the one and only instance of the class *immediately*
    //    when the class is loaded.
    //    This is 'final' so it can never be changed.
    private static final EagerSingleton instance = new EagerSingleton();

    // 2. Make the constructor 'private' so that no one can
    //    create a new instance using 'new EagerSingleton()'.
    private EagerSingleton() {
        // (Initialization code, if any, goes here)
    }

    // 3. Provide a 'public static' method to get the single instance.
    //    This is the only way to access the object.
    public static EagerSingleton getInstance() {
        return instance;
    }

    // Example method to show it's working
    public void showMessage() {
        System.out.println("Hello from Eager Singleton! My hashcode is: " + this.hashCode());
    }

    // Main method to demonstrate
    public static void main(String[] args) {
        // You cannot do this: new EagerSingleton(); // <-- Compile error

        // Get the instance twice
        EagerSingleton s1 = EagerSingleton.getInstance();
        EagerSingleton s2 = EagerSingleton.getInstance();

        // Check if they are the same object
        if (s1 == s2) {
            System.out.println("s1 and s2 are the same instance.");
            s1.showMessage();
            s2.showMessage(); // Will print the same hashcode
        }
    }
}

// Pros: Simple and inherently thread-safe (instance is created before any thread can access it).
// Cons: The instance is created even if the application never uses it.
