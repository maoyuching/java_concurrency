package thread_pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class simpleRejctedHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        /*
        当发生拒绝添加任务时候的应对策略
        1. 抛异常
        2. 直接丢弃不管
        3. 丢弃队中最旧的任务
        4. 不放进线程池 用其他线程来执行
         */
        System.out.println(" 拒绝接受任务");
    }
}
