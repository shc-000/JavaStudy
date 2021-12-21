package com.frog.study.thread.stream;

import com.frog.study.base.Student;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 自定义线程池结合CompletableFuture
 *
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/28 9:15 下午
 */
public class Test {
    public final static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private static List<Student> buildStudentList() {
        return Arrays.asList(new Student("张三", 18, "男", "清华"),
                new Student("李斯", 19, "男", "清华"),
                new Student("李四", 18, "男", "清华"),
                new Student("王五", 17, "男", "清华"),
                new Student("昭君", 19, "女", "清华"),
                new Student("貂蝉", 18, "女", "北大"),
                new Student("西施", 16, "女", "南开"));
    }

    public static void main(String[] args) {
        List<Student> list = buildStudentList();
        //线程设置名字
        ThreadFactory springThreadFactory = new CustomizableThreadFactory("shc-thread-pool-");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(list.size(), 100, 1, TimeUnit.MINUTES, queue, springThreadFactory);
        list.stream().map(a -> CompletableFuture.supplyAsync(() -> {
            // 操作代码.....
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return a;
        }, threadPoolExecutor)).collect(Collectors.toList()).stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
