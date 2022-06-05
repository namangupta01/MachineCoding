public class FooWithoutThreadSafe implements Runnable {
    public static int count;

    public void run() {
        for(int i=0; i<10000; i++) {
            count++;
        }
    }
}
