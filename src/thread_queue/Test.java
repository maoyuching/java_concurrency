package thread_queue;

import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        BlockingQueue<String> bq = new ArrayBlockingQueue(16);

        bq.offer("hi");
        //插入元素， 返回true or false
        bq.add("a");
        //add 是借住offer实现的 true 或 抛异常
        bq.put("a");
        //put 会一直等待在这儿 无返回值 声明可能会抛异常


        bq.poll();
        //dequeue 队首元素出队, 和 offer类似
        bq.poll(1L, TimeUnit.SECONDS);
        //等待 1秒， 还是取不到则返回 null, 防止一直等待着
        bq.take();
        //类似put 会一直等待 出队, put()　and take() 是bq的最特别的方法

        bq.peek();
        // return the head of the queue or null

        /*
        生产者 把元素放入队列 满则等待
         */
        for (int i = 0; i < 16; i++) {
            bq.put(i+" ->");
        }

        /*
        消费者 把元素去除队列 空则等待
         */
        for (int i = 0; i < 4; i++) {
            new Thread(){
                public void run() {
                    while (true) {
                        try {
                            String s = bq.take();
                            System.out.println(s+ System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }


        /*
        性能更高， 因为采用了独立的锁  生产者和消费者可以并行操作队列
        一开始要指定大小 因为默认类似无限大
         */
        LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>(4);

        /*
        采用优先级 需要在构造函数传入比较器来实现优先级比较
        不会阻塞生产者
         */
        PriorityBlockingQueue<String> pbq = new PriorityBlockingQueue<>(4);

        /*
        学生写试卷是生产者， 老师改试卷是消费者
        不能学生一入队就交卷， 必须延迟等待一个小时才能交卷
        元素必须实现 Delayd接口 和 Comparable
         */
        DelayQueue delayq = new DelayQueue();

        /*
        生产者生产一个 消费者就消费一个  只能是一个
         */
        SynchronousQueue sq = new SynchronousQueue();


        /*
        倒计数器 指定要倒计数几个 然后 持有倒计数器的线程可以倒计数一个
        执行await() 的线程 将等待计数器归零 也就是等待它们
         */
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(){
                public void run() {
                    latch.countDown();
                }
            }.start();
        }
        latch.await();


        /*
        Semaphore 同步计数器， 指定每次只有n个线程并发， 其他的都要阻塞
        线程持有它 执行acquire方法 来试图摆脱阻塞
        一旦成功运行 运行完毕后， 执行 release方法 释放锁
         */
        Semaphore sp = new Semaphore(3);
        sp.acquire();
        sp.release();


        /*
        参数一指定几个线程持有 参数二指定 这几个线程都完成某个任务后要执行的动作
        然后若干个线程在run方法里 完成某特定程度的任务后 调用 await()方法 等大家都完成
         */
        CyclicBarrier cb = new CyclicBarrier(1,new Thread());
        cb.await();
    }
}
