import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String args[]) {
//        AtomicBoolean atomicBoolean = new AtomicBoolean();
//        AtomicInteger atomicInteger = new AtomicInteger();
//        atomicBoolean.set(false);

//        FooWithoutThreadSafe f = new FooWithoutThreadSafe();
//        FooWithThreadSafe f = new FooWithThreadSafe();
        ThreadWithAtomic f = new ThreadWithAtomic();
        Thread t1 = new Thread(f);
        Thread t2 = new Thread(f);
        Thread t3 = new Thread(f);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            System.out.println(f.count);
        } catch (Exception e) {

        }

    }
}
