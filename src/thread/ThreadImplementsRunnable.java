package thread;

public class ThreadImplementsRunnable implements Runnable {

    public void  run() {
        System.out.println("this is thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) {

    }

}
