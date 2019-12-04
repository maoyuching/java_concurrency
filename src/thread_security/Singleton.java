package thread_security;

import java.util.concurrent.locks.ReentrantLock;

public class Singleton {
    /*
    单例类：
        私有 类变量
        私有构造方法
        公开 静态方法返回类变量
     */
    private static Singleton instance;
    private Singleton(){

    }
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }


    /*
    对静态工厂方法 加上隐式锁，使得获取变量成为线程安全的操作
     */
    public static synchronized Singleton getInstanceWithSync() {
        if ( instance == null) instance = new Singleton();
        return instance;
    }


    /*
    上面的方法 不管三七二十一先加锁，性能不好， 所以先判断一下是否为null再决定
    拿到锁后， 在判断一下在拿锁过程中是否已经有instance了
     */
    static Object lock = new Byte("a");
    public static Singleton getInstanceWithSyncByte() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }


    /*
    使用了显式锁的静态工厂方法， 同样要两次验证是否为null
     */
    private static ReentrantLock reentrantLock = new ReentrantLock();
    public static Singleton getInstanceWithReentrantLock() {
        if (instance == null) {
            reentrantLock.lock();
            if (instance == null) instance = new Singleton();
            reentrantLock.unlock();
        }
        return instance;
    }
}



