package fork_join;

import java.util.concurrent.*;

public class ForkandJoin {

    public static void main(String[] args) throws Exception{

        ForkJoinPool pool = new ForkJoinPool();
        //ForkJoinPool 是特殊的线程池 他利用双端queue存储任务,帮助你把大任务分解成小任务

        Future future = pool.submit(new Task(1,100));
        //像线程池中递交任务 立即返回future对象不block在这里, 在以后获取返回值

        System.out.println(future.get());
        //利用future对象可以得到返回值

        System.out.println(pool.invoke(new Task(1, 100)));
        //或者不实用future 直接用invoke 方法 提交任务 等待返回结果


        /*
        递交给fork join 线程池的 任务比较特殊 有下面两种
        一种 有返回值 可以 用invoke（）同步等待 结果
                    或者用 submit（） 并接受一个future对象 来异步获取结果
        第二种， 没有返回值 可以用 execute() 异步执行 反正不需要结果
         */
        ForkJoinTask<String> task1 = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return null;
            }
        };

        ForkJoinTask task2 = new RecursiveAction() {
            @Override
            protected void compute() {
            }
        };

        task1.isCompletedAbnormally();
        //判断 任务是否非正常结束

    }

}



class Task extends RecursiveTask<Integer> {

    private int begin;
    private int end;

    public Task(int b, int end) {
        this.begin = b;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        Integer ans = 0;

        boolean canCompute = (end - begin) <= 5;
        if (canCompute) {
            for (int i = begin; i <= end; i++) {
                ans += i;
            }
        } else {
            Task t1 = new Task(begin, (begin + end) / 2);
            Task t2 = new Task( (begin + end) / 2 + 1, end);
            t1.fork();
            t2.fork();
            ans = t1.join() + t2.join();
        }
        return  ans;
    }
}
