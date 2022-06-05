public class FooWithThreadSafe implements Runnable {
    public static volatile int count;

    public void run() {
        for(int i=0; i<10000; i++) {
            synchronized (this) {
                count++;
            }
        }
    }
}
