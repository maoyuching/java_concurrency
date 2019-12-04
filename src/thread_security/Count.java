package thread_security;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
count 是一个计数器 他将会被多个线程执有
 */
public class Count {
    public int num = 0;

    public  void add() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {

        }
        num += 1;
        /*线程不安全， jvm有堆内存和各个thread的工作内存，主内村多个thread共享
        而工作内存需要操作堆内存里的数据时， 想拿到工作内存里 更改后再送回堆内存
         */

        System.out.println(Thread.currentThread().getName() + " " + num);
    }

    /*
    thread security synchronized
     */
    public synchronized   void addWithSync() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        num += 1;
        /*
        thread 安全
        当使用synchronized 关键词  放在方法 public 修饰符后面， void 返回值 前面
         */

        System.out.println(Thread.currentThread().getName() + " " + num);
    }


    /*
    使用代码块的隐式锁
     */
    public  void addWithSyncCodeblock() {

        synchronized (this) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            num += 1;
        }
        /*
        thread 安全
        把synchronized 放在 方法体上 等同于 用this作为代码块的锁
        当 多个线程执有 本对象 时 他们要一个一个来
         */

        System.out.println(Thread.currentThread().getName() + " " + num);
    }


    /*
    开销更小的隐式锁
     */
    private byte aByte = 1;
    public  void addWithSyncCodeblockWithByte() {

        synchronized ( (Object) aByte) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {

            }
            num += 1;
        }
        /*
        thread 安全
        把一个 byte 用作 锁 每次 只有一个线程拿到锁 才能执行代码块
        这样加锁和释放锁 开销更小
         */

        System.out.println(Thread.currentThread().getName() + " " + num);
    }


    ReentrantLock lock = new ReentrantLock();
    //显式锁 手动地利用它来获取锁 释放锁

    /*
    get 和 put 方法 使用了这个锁， 则 所有的 get 和 put 方法执行都是互斥关系
    他们将按照代码顺序 执行 而不是并发执行
     */
    public void get() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " " + "get begin");

            Thread.sleep(500);

            System.out.println(Thread.currentThread().getName() + " " + "get end");
            lock.unlock();
        } catch (InterruptedException e) {
        }
    }

    public void put() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " " + "put begin");

            Thread.sleep(500);

            System.out.println(Thread.currentThread().getName() + " " + "put end");
            lock.unlock();
        } catch (InterruptedException e) {

        }
    }


    /*
    读写锁ReadWriteLock 是一个接口， 接口 两个函数 返回 读锁和 写锁
    .readLock() 就是 一个 Lock (Lock 是一个接口 定义了锁的方法 如lock unlock trylock。。。）

     只有 读锁的操作都是可以并发的 可以看到read的操作是乱来的
     而凡是涉及到写锁 必须互斥进行 就是一个write操作好了下一个来
     */
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public void read() {
        readWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + " read begin");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " read end");
        readWriteLock.readLock().unlock();
    }

    public void write() {
        readWriteLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + " write begin");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " write end");
        readWriteLock.writeLock().unlock();
    }


    /*
    测试一个死锁， 方法一 要拿一个o1 再拿一个o2， 而方法二正相反，导致两个方法互不相让
     */
    Object o1 = new Byte("1");
    Object o2 = new Byte("2");
    public void badMethod1() {
        synchronized (o1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {

            }
        }
    }

    public void badMethod2() {
        synchronized (o2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1) {

            }
        }
    }


    /*
    用 volatile修饰的变量 在读取这个变量时候要去内存地址中读 也就是内存暴露 内存可见
    volatile不意味着原子性，也就不意味着并发安全 而只是内存可见，
    适合很多人读而很少人写 的 地方
     */
    public volatile int a = 0;


    /*
    在 java util concurrent atomic 包中 有一些 Atomic 打头的类 实现了原子操作
     */
    public void someAtomicThing() {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(100);
        int i = atomicInteger.get();
        i = atomicInteger.getAndAdd(1);
        i = atomicInteger.getAndSet(99);

        int[] ar = {1, 2, 3};
        AtomicIntegerArray array = new AtomicIntegerArray(ar);
        array.get(0);
    }

}
