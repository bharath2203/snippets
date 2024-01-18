package ratelimit;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitWithBoundedExecutor {

    private static BoundedExecutor boundedExecutor;

    private static AtomicInteger counter = new AtomicInteger();

    private static Runnable task = () -> {
        System.out.println("Thread: " + Thread.currentThread().getName() + " executing task..." + counter.addAndGet(1)); 
        try {
            Thread.sleep(4000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    };

    public static void main(String[] args) {
        boundedExecutor = new BoundedExecutor(
            new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue()), 
            
            10
        );

        for (int i = 0; i < 100; i++) {
            boundedExecutor.execute(task);
        }
    }
}
