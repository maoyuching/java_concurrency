package fork_join;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class TheFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*
        要实现callable接口来实现线程， 则要实现接口的call方法
        callable 做完了任务是会返回东西的
         */
        Callable work = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };
        /*
          Future 是 接口
          FutureTask 类实现了该接口
          构造函数可以传入一个 callable
         */
        FutureTask<String> future = new FutureTask(work);

        new Thread(future).start();
        //future 必须要放到thread 里运行 否则是拿不到返回数据的

        String s = future.get();
        // 运行后 就可以 尝试那到返回值了

        future.cancel(true);
    }
}
