package facade.eb;

// 1. The Complex Subsystem (multiple devices)

class Amplifier {
    public void on() { System.out.println("Amplifier: On"); }
    public void setVolume(int level) { System.out.println("Amplifier: Setting volume to " + level); }
    public void setSurroundSound() { System.out.println("Amplifier: Setting surround sound"); }
    public void off() { System.out.println("Amplifier: Off"); }
}

class Projector {
    public void on() { System.out.println("Projector: On"); }
    public void wideScreenMode() { System.out.println("Projector: Setting wide screen mode"); }
    public void off() { System.out.println("Projector: Off"); }
}

class DvdPlayer {
    public void on() { System.out.println("DVD Player: On"); }
    public void play(String movie) { System.out.println("DVD Player: Playing \"" + movie + "\""); }
    public void stop() { System.out.println("DVD Player: Stopped"); }
    public void off() { System.out.println("DVD Player: Off"); }
}

class TheaterLights {
    public void dim(int level) { System.out.println("Theater Lights: Dimming to " + level + "%"); }
    public void on() { System.out.println("Theater Lights: On"); }
}

// 2. The Facade
// It simplifies the process of watching and ending a movie.
class HomeTheaterFacade {
    // The Facade holds references to all subsystem components.
    private final Amplifier amp;
    private final Projector projector;
    private final DvdPlayer dvd;
    private final TheaterLights lights;

    public HomeTheaterFacade(Amplifier amp, Projector projector, DvdPlayer dvd, TheaterLights lights) {
        this.amp = amp;
        this.projector = projector;
        this.dvd = dvd;
        this.lights = lights;
    }

    // The simplified 'watch' method.
    public void watchMovie(String movie) {
        System.out.println("\nFacade: Get ready to watch a movie...");
        // It coordinates all the components in the correct order.
        lights.dim(10);
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setSurroundSound();
        amp.setVolume(5);
        dvd.on();
        dvd.play(movie);
    }

    // The simplified 'end' method.
    public void endMovie() {
        System.out.println("\nFacade: Shutting movie theater down...");
        dvd.stop();
        dvd.off();
        amp.off();
        projector.off();
        lights.on();
    }
}

// 3. The Client
public class Client {
    public static void main(String[] args) {
        // Create the components (this could also be hidden by the facade)
        Amplifier amp = new Amplifier();
        Projector projector = new Projector();
        DvdPlayer dvd = new DvdPlayer();
        TheaterLights lights = new TheaterLights();

        // Create the facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(amp, projector, dvd, lights);

        // Client just makes simple calls
        homeTheater.watchMovie("Inception");
        homeTheater.endMovie();
    }
}