package thread_pool;

import java.util.concurrent.*;

public class ThreadPool {
    /*
    线程池的好处：1. 降低线程创建和销毁的资源消耗
                 2. 便于管理 可以统一分配调度
                 3， 防止服务器过载

     线程池的组成：1. 线程等待池 BlockingQueue 一个线程队列
                  2. 任务处理池 HashSet<线程>

     线程池参数: 1. corePoolSize 固定大小 达到这个数量后就不减少线程
                2. maximumPoolSize 即最大接受任务数量 超过就不再接受了
     */

    public static void main(String[] args) throws ExecutionException {


        /*
        ThreadPoolExecutor 最基本的线程池类
        构造函数传入的参数也是跟线程池工作策略和组成部分息息相关
         */
        BlockingQueue q = new ArrayBlockingQueue(2);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2,
                10,
                100l, TimeUnit.SECONDS,
                q
        );
        pool.execute(new Thread());
        pool.shutdown();


        /*
        Executor 是一个定义了线程池的 接口
        ExecutorService  是其更加完善的子接口
        Executors 是工厂类 可以直接返回需要的线程池

         */
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 4; i++) {
            Runnable worker = new Runnable(){
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            };
            /*
            execute 接受一个runnable, 而且只接受一种runnable
            这是一个单线程线程池， 当线程中断结束时，会弄一个新的一样的线程
             */
            singlePool.execute(worker);
        }
        singlePool.shutdown();


        /*
        缓存线程池 终止的线程 线程池会缓存一定时间
         */
        ExecutorService ctp = Executors.newCachedThreadPool();
        ctp.submit(new Thread());
        ctp.execute(new Thread(){
            public void run() {System.out.println(currentThread().getName());}
        });
        ctp.execute(new Thread(){
            public void run() {System.out.println(currentThread().getName());}
        });
        ctp.shutdown();


        /*
        固定大小线程池 同一时间只有固定数量的线程活跃 其他的线程 等待
         */
        ExecutorService ftp = Executors.newFixedThreadPool(4);
        ftp.execute(new Thread());

    }
}
