package singleton.ec;

public class BillPughSingleton {

    // 1. Private constructor, same as always.
    private BillPughSingleton() {
        // (Initialization code)
    }

    // 2. A private static *inner class* (the "Holder").
    //    This inner class is *not* loaded into memory by the JVM
    //    until the getInstance() method is called for the first time.
    private static class SingletonHolder {

        // 3. The single instance is created *inside* the holder class.
        //    When the SingletonHolder class is loaded, this static
        //    field is initialized. The JVM *guarantees* that this
        //    class-loading process is thread-safe.
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    // 4. The public getter.
    //    The first time this is called, it triggers the loading of
    //    SingletonHolder, which creates the instance.
    //    Subsequent calls just return the already-created instance.
    public static BillPughSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // Example method
    public void showMessage() {
        System.out.println("Hello from Bill Pugh Singleton! Hash: " + this.hashCode());
    }

    // Main method to demonstrate
    public static void main(String[] args) {
        // Even with multiple threads, this is safe and fast.
        Thread t1 = new Thread(() -> {
            BillPughSingleton s = BillPughSingleton.getInstance();
            s.showMessage();
        });

        Thread t2 = new Thread(() -> {
            BillPughSingleton s = BillPughSingleton.getInstance();
            s.showMessage();
        });

        t1.start();
        t2.start();
    }
}

// Pros: All of them!
// 1. Lazy Loading: Instance is only created when getInstance() is called.
// 2. Thread-Safe: Guaranteed by the
//    JVM's class-loading mechanism.
// 3. High Performance: No 'synchronized' locks to slow down subsequent calls.
// Cons: None for most use cases. (Slightly more complex than Eager init).
