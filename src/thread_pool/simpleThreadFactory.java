package thread_pool;

import thread.ThreadA;

import java.util.concurrent.ThreadFactory;

public class simpleThreadFactory  implements ThreadFactory {


    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
