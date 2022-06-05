import java.util.concurrent.atomic.AtomicInteger;

public class ThreadWithAtomic implements Runnable {
    public static AtomicInteger count = new AtomicInteger(0);

    public void run() {
        for(int i=0; i<10000; i++) {
            count.getAndIncrement();
        }
    }
}
