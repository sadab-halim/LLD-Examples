package singleton.ee;

// 1. Declare an 'enum' type with a single instance.
//    That's it. Really.
enum EnumSingleton {

    // 2. Define the one and only instance.
    INSTANCE;

    // 3. You can add methods and fields just like a regular class.
    private String connectionString = "jdbc:default:db";

    public void showMessage() {
        System.out.println("Hello from Enum Singleton! Hash: " + this.hashCode());
        System.out.println("Connection: " + connectionString);
    }

    // The constructor is implicitly private.
    // The JVM guarantees this is only called *once*.
    EnumSingleton() {
        System.out.println("EnumSingleton constructor called.");
        // (Initialization code here)
    }

    // Example getter/setter
    public String getConnectionString() {
        return connectionString;
    }
    public void setConnectionString(String cs) {
        this.connectionString = cs;
    }
}

// Main method to demonstrate
class EnumDemo {
    public static void main(String[] args) {
        // 4. You access the instance directly.
        EnumSingleton s1 = EnumSingleton.INSTANCE;
        EnumSingleton s2 = EnumSingleton.INSTANCE;

        s1.showMessage();
        s2.showMessage(); // Will be the same hashcode

        // Modify the instance via s1
        s1.setConnectionString("new_connection_string");

        // s2 will see the change because it's the *same* object.
        System.out.println("s2 connection: " + s2.getConnectionString());
    }
}

// Pros:
// 1. Simplicity: Extremely easy to write and read.
// 2. Thread-Safe: 100% guaranteed by the JVM.
// 3. Serialization-Safe: Handles serialization/deserialization correctly
//    without any extra code, preventing new instances from being created.
//
// Cons:
// 1. Not as "lazy": The instance is created when the enum is first
//    accessed, which might be slightly earlier than a true lazy load.
// 2. Less flexible if your singleton needs to inherit from another class.