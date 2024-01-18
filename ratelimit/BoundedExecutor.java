package ratelimit;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class BoundedExecutor {
    private ThreadPoolExecutor executor;
    private Semaphore semaphore;

    public BoundedExecutor(ThreadPoolExecutor executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
    }

    public void execute(Runnable runnable) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return;
        }
        
        try {
            executor.execute(() -> {
                try {
                    runnable.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch(Exception e) {
            semaphore.release();
        }
    }
}
