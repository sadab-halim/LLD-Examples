package factory.ec;

public class AbstractFactoryDemo {
    public static void main(String[] args) {
        // Configuration: Assume we read the OS type from a config file
        String osName = "mac";

        GUIFactory factory;
        if (osName.equals("mac")) {
            factory = new MacFactory();
        } else {
            factory = new WindowsFactory();
        }

        // The application is assembled with the correct family of objects.
        Application app = new Application(factory);
        app.paint();
    }
}
