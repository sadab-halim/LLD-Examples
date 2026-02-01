package singleton.ec;

import java.util.concurrent.*;

public class HSMController {

    private static final HSMController INSTANCE = new HSMController();

    // Internal queue to serialize hardware access
    private final ExecutorService hardwareBus;

    private HSMController() {
        this.hardwareBus = Executors.newSingleThreadExecutor(r -> new Thread(r, "hsm-driver"));
    }

    public static HSMController getInstance() { return INSTANCE; }

    // API: Returns immediately, does not block the caller
    public CompletableFuture<String> sign(String payload) {
        return CompletableFuture.supplyAsync(() -> unsafeHardwareCall(payload), hardwareBus);
    }

    // The dangerous, single-threaded hardware interaction
    private String unsafeHardwareCall(String payload) {
        try {
            // Simulate hardware slowness (100ms)
            Thread.sleep(100);
            return "SIGNATURE_" + payload.toUpperCase();
        } catch (InterruptedException e) {
            return "ERROR";
        }
    }

    // --- MAIN DRIVER ---
    public static void main(String[] args) {
        HSMController hsm = HSMController.getInstance();

        System.out.println("--- Submitting 5 Async Requests ---");
        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            final int id = i;
            hsm.sign("data-" + id).thenAccept(sig -> {
                long duration = System.currentTimeMillis() - start;
                System.out.println("Finished Req " + id + ": " + sig + " (at " + duration + "ms)");
            });
        }

        System.out.println("--- Main Thread Unblocked Immediately ---");
    }
}
