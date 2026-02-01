package singleton.eb;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// The Singleton is an Enum
enum ConnectionPool {
    INSTANCE;

    private static final int MAX_CONN = 3; // Small limit for demo
    private final BlockingQueue<String> pool;

    ConnectionPool() {
        pool = new ArrayBlockingQueue<>(MAX_CONN);
        for (int i = 1; i <= MAX_CONN; i++) pool.offer("Connection-" + i);
    }

    public String borrow() throws InterruptedException {
        // Blocks up to 2 seconds waiting for a resource
        String conn = pool.poll(2, TimeUnit.SECONDS);
        if (conn == null) throw new RuntimeException("Pool Exhausted!");
        return conn;
    }

    public void release(String conn) {
        pool.offer(conn); // Returns to queue
    }
}

// Wrapper class to run the demo
public class ConnectionPoolDemo {
    public static void main(String[] args) {
        ExecutorService server = Executors.newCachedThreadPool();

        // We launch 6 requests, but pool size is only 3.
        // 3 will succeed immediately, 3 will block.
        for (int i = 1; i <= 6; i++) {
            final int id = i;
            server.submit(() -> {
                try {
                    System.out.println("Req " + id + " waiting...");
                    String conn = ConnectionPool.INSTANCE.borrow();

                    System.out.println("Req " + id + " ACQUIRED " + conn);
                    Thread.sleep(1000); // Hold connection

                    ConnectionPool.INSTANCE.release(conn);
                    System.out.println("Req " + id + " RELEASED " + conn);
                } catch (Exception e) {
                    System.err.println("Req " + id + " FAILED: " + e.getMessage());
                }
            });
        }
        server.shutdown();
    }
}
