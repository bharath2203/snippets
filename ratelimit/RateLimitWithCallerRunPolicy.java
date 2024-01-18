package ratelimit;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitWithCallerRunPolicy {
    
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
        5, 5, 2, TimeUnit.MILLISECONDS, 
        new ArrayBlockingQueue<>(5), 
        new CallerRunsPolicy()
    );
    
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
        for (int i = 0; i < 100; i++) {
            executor.execute(task);
        }
    }
}
