package singleton.ed;

public class DoubleCheckedSingleton {

    // 1. The instance must be marked 'volatile'.
    //    'volatile' ensures that multiple threads correctly handle
    //    the 'instance' variable when it's being initialized.
    //    It guarantees that any write to 'instance' is visible
    //    to all other threads immediately.
    private static volatile DoubleCheckedSingleton instance;

    // 2. Private constructor.
    private DoubleCheckedSingleton() {
        // (Initialization code)
    }

    // 3. The getter method (not synchronized).
    public static DoubleCheckedSingleton getInstance() {
        // 4. First check (the "outer check").
        //    This check is non-synchronized. If the instance already
        //    exists, we return it immediately without any locking.
        //    This is the performance gain.
        if (instance == null) {

            // 5. If instance is null, we enter the synchronized block.
            //    Only one thread can be in this block at a time.
            synchronized (DoubleCheckedSingleton.class) {

                // 6. Second check (the "inner" or "double-check").
                //    We must check *again* if instance is null.
                //    Why? By the time a thread gets the lock, another
                //    thread might have *already* created the instance.
                //    This second check prevents creating it twice.
                if (instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }

    // Example method
    public void showMessage() {
        System.out.println("Hello from Double-Checked Locking! Hash: " + this.hashCode());
    }

    // Main method to demonstrate
    public static void main(String[] args) {
        // This will be thread-safe and performant.
        Thread t1 = new Thread(() -> DoubleCheckedSingleton.getInstance().showMessage());
        Thread t2 = new Thread(() -> DoubleCheckedSingleton.getInstance().showMessage());
        t1.start();
        t2.start();
    }
}

// Pros: Lazy loading and high performance (no sync cost after init).
// Cons: Complex. It's easy to get wrong (forgetting 'volatile' breaks it).
//       The Bill Pugh method (Example 3) is cleaner and solves the same problem.