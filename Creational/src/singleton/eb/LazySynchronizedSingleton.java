package singleton.eb;

public class LazySynchronizedSingleton {

    // 1. The instance is declared 'null' initially.
    private static LazySynchronizedSingleton instance;

    // 2. Private constructor.
    private LazySynchronizedSingleton() {
        // (Initialization code)
    }

    // 3. The getter method is 'synchronized'.
    //    This keyword acts as a lock, ensuring that only one thread
    //    can enter this method at a time.
    public static synchronized LazySynchronizedSingleton getInstance() {

        // 4. Check if the instance is null. If it is, create it.
        //    Because the method is synchronized, we don't have to worry
        //    about two threads creating two different instances.
        if (instance == null) {
            instance = new LazySynchronizedSingleton();
        }

        // 5. Return the one and only instance.
        return instance;
    }

    // Example method
    public void showMessage() {
        System.out.println("Hello from Lazy Synchronized Singleton! Hash: " + this.hashCode());
    }

    // Main method to demonstrate
    public static void main(String[] args) {
        // Simulate two threads trying to get the instance at the same time
        Thread t1 = new Thread(() -> {
            LazySynchronizedSingleton s = LazySynchronizedSingleton.getInstance();
            s.showMessage();
        });

        Thread t2 = new Thread(() -> {
            LazySynchronizedSingleton s = LazySynchronizedSingleton.getInstance();
            s.showMessage();
        });

        t1.start();
        t2.start();
        // Both threads will get the same instance (same hashcode).
    }
}

// Pros: Instance is created only when needed (lazy). Thread-safe.
// Cons: Performance cost. The 'synchronized' lock is acquired *every time*
//       getInstance() is called, even after the instance is created,
//       which is unnecessary and can slow things down.