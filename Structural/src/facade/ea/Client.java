package facade.ea;

// 1. The Complex Subsystem
// These are the low-level classes the client doesn't want to interact with directly.

class CPU {
    public void freeze() { System.out.println("CPU: Freezing..."); }
    public void jump(long position) { System.out.println("CPU: Jumping to " + position); }
    public void execute() { System.out.println("CPU: Executing..."); }
}

class Memory {
    public void load(long position, byte[] data) {
        System.out.println("Memory: Loading data to position " + position);
    }
}

class HardDrive {
    public byte[] read(long lba, int size) {
        System.out.println("HardDrive: Reading " + size + " bytes from LBA " + lba);
        return new byte[]{ 'b', 'o', 'o', 't' }; // Dummy boot data
    }
}

// 2. The Facade
// This class provides the simplified interface (e.g., a single 'start' button).
// It holds references to all the subsystem components.
class ComputerFacade {
    private final CPU cpu;
    private final Memory memory;
    private final HardDrive hardDrive;

    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
    }

    // The simple method that hides all the complexity.
    public void start() {
        System.out.println("Facade: Starting computer...");

        // The facade's job is to coordinate the subsystem.
        // The client doesn't need to know this sequence.
        final long BOOT_ADDRESS = 0x7C00;
        final long BOOT_SECTOR = 0x0001;

        cpu.freeze();
        byte[] bootData = hardDrive.read(BOOT_SECTOR, 512);
        memory.load(BOOT_ADDRESS, bootData);
        cpu.jump(BOOT_ADDRESS);
        cpu.execute();

        System.out.println("Facade: Computer started successfully.");
    }
}

// 3. The Client
// The client's code is now extremely simple.
public class Client {
    public static void main(String[] args) {
        // The client only interacts with the Facade.
        ComputerFacade computer = new ComputerFacade();

        // They just call the simple method.
        computer.start();
    }
}