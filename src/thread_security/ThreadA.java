package thread_security;

public class ThreadA extends Thread{
    private Count count;
    //count 是定义的 计数器

    public ThreadA(Count count) {
        this.count = count;
    }

    public void run() {
        //count.add();
        //线程执行时 对计数器加一

//        count.addWithSync();

//        count.addWithSyncCodeblock();

        count.addWithSyncCodeblockWithByte();
    }




    /*
    main
     */
    public static void main(String[] args) {
        Count count = new Count();

        /*
        测试 5个 线程 concurrent 访问 count计数器
         */
//        for (int i = 0; i < 5; i++) {
//            ThreadA t1 = new ThreadA(count);
//            t1.start();
//        }


        /*
        测试 显式锁 Lock
         */
//        for (int i = 0; i < 2; i++) {
//            new Thread(){
//                public void run() {
//                    count.read();
//                }
//            }.start();
//        }
//        for (int i = 0; i < 2; i++) {
//            new Thread(){
//                public void run() {
//                    count.write();
//                }
//            }.start();
//        }

        new Thread(){
            public void run() {
                System.out.println(currentThread().getName() + "begin");
                count.badMethod1();
                System.out.println(currentThread().getName() + "end");
            }
        }.start();

        new Thread(){
            public void run() {
                System.out.println(currentThread().getName() + "begin");
                count.badMethod2();
                System.out.println(currentThread().getName() + "end");
            }
        }.start();



        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }

        System.out.println("num is " + count.num);
    }
}
