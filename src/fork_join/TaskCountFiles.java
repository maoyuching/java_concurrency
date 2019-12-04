package fork_join;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class TaskCountFiles extends RecursiveTask<Integer> {


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int num = pool.invoke(new TaskCountFiles(Paths.get("D:/code")));
        System.out.println(num);
    }


    private Path dir;

    public TaskCountFiles(Path dir) {
        this.dir = dir;
    }

    @Override
    protected Integer compute() {
        int count = 0;
        List<TaskCountFiles> subtasks = new ArrayList<>();

        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
            for (Path subPath : ds) {
                if (Files.isDirectory(subPath)) {
                    subtasks.add(new TaskCountFiles(subPath));
                } else {
                    count+= 1;
                }
            }

            if (!subtasks.isEmpty()) {
                for (TaskCountFiles task : invokeAll(subtasks)) {
                    count += task.join();
                }
            }

        } catch (Exception e) {

        }

        return count;
    }
}
