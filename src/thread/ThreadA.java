package thread;

/*

book : java 并发编程从入门到精通 张振华
 */
public class ThreadA extends Thread{

    /*
    类 继承Thread 类实现 run 方法 ，run 方法里就是线程要做的事情
     */
    public void run() {
        //run 方法 不允许 抛出异常 异常 需要自行解决

        this.iSay("我登场了");

        boolean stop = false;
        while (!stop) {
            this.iSay("hi 看我看我");

            long temptime = System.currentTimeMillis();
            while (System.currentTimeMillis() - temptime < 1000) {
                //啥也不做, 就等到时间到了就跳出while 循环了
            }

            if (currentThread().isInterrupted()) {
                //本线程通过isInterrupted方法得知是否被别人interrupted
                this.iSay("啊 我被人中断了吗， 这样的话，再见吧");
                break;
            }
        }
        this.iSay("byebye");
        // 大while 循环结束了也就退出了线程
    }

    private void iSay(String s) {
        System.out.println(currentThread().getName() + " : " + s);
    }






    /*
    main
     */
    public static void main(String[] args) {

        Thread t1 = new ThreadA(); //线程 t1 new 新建状态
        t1.setName("t1");

        t1.start(); // t1 被启动 t1 就绪状态

        //当t1 获得cpu 时 进入运行状态

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //当线程sleep 时 主线程让出cpu 进入 block state 阻塞状态

        t1.interrupt();
        //在主线程中调用线程t1的中断方法来interrupt t1， 但是具体怎么中断有那个线程自己决定

        //当线程t1 处理了中断请求并结束 run 方法 则t1 线程 dead

        Thread t2 = new Thread(new ThreadImplementsRunnable());
        //实现 runnable 接口的类 通过实现 run 方法也可做线程用

        t2.setDaemon(true);
        //将线程设置为守护线程， 守护线程 生命周期 伴随主线程

        ThreadGroup threadGroup = currentThread().getThreadGroup();
        //线程组 方便管理线程级别

    }
}

