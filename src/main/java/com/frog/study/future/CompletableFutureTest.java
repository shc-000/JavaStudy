package com.frog.study.future;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * CompletableFuture实现了Future接口，FutureTask也实现了Future接口
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/2/25
 */
public class CompletableFutureTest {
    //为什么要使用阻塞队列？
    /**
     * 我们知道队列是先进先出的。当放入一个元素的时候，会放在队列的末尾，取出元素的时候，会从队头取。那么，当队列为空或者队列满的时候怎么办呢。
     * <p>
     * 这时，阻塞队列，会自动帮我们处理这种情况。
     * <p>
     * 当阻塞队列为空的时候，从队列中取元素的操作就会被阻塞。当阻塞队列满的时候，往队列中放入元素的操作就会被阻塞。
     * <p>
     * 而后，一旦空队列有数据了，或者满队列有空余位置时，被阻塞的线程就会被自动唤醒。
     * <p>
     * 这就是阻塞队列的好处，你不需要关心线程何时被阻塞，也不需要关心线程何时被唤醒，一切都由阻塞队列自动帮我们完成。我们只需要关注具体的业务逻辑就可以了。
     */
    private final static BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<>();

    private static final ThreadFactory SPRING_THREAD_FACTORY = new CustomizableThreadFactory("shc-thread-pool-");
    private final static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 15, 1, TimeUnit.MINUTES, QUEUE, SPRING_THREAD_FACTORY);

    public static void main(String[] args) {
        singleTaskAndAsync();
    }

    /**
     * 单任务异步
     */
    public static void singleTaskAndAsync() {
        //1.无返回值的异步任务 runAsync()
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("线程号为***" + Thread.currentThread().getId());
            int i = 5;
            System.out.println("---------" + i);
        }, THREAD_POOL_EXECUTOR);

        //2.有返回值异步任务 supplyAsync()
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("线程号为***" + Thread.currentThread().getId());
            int i = 5;
            System.out.println("---------" + i);
            return i;
        }, THREAD_POOL_EXECUTOR).whenComplete((r, e) -> {
//            whenComplete第一个参数是结果，第二个参数是异常，他可以感知异常，无法返回默认数据
            System.out.println("执行完毕，结果是---" + r + "异常是----" + e);
        }).exceptionally(u -> {
//            exceptionally只有一个参数是异常类型，他可以感知异常，同时返回默认数据10
            return 10;
//            handler既可以感知异常，也可以返回默认数据，是whenComplete和exceptionally的结合
        }).handle((r, e) -> {
            if (r != null) {
                return r;
            }
            if (e != null) {
                return 0;
            }
            return 0;
        });
    }

    /**
     * 线程串行化（把后边的线程和前边的串起来）
     */
    public void threadSerial() {
        //thenRunAsync()无返回值，不可以接受传来的值
        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("线程号为***" + Thread.currentThread().getId());
            int i = 5;
            System.out.println("---------" + i);
            return i;
        }, THREAD_POOL_EXECUTOR).thenRunAsync(() -> {
            System.out.println("thenRunAsync，不可接受传来的值，自己无返回值的串行化---");
        }, THREAD_POOL_EXECUTOR);

        //thenAccept(x)无返回值，可以接受传来的值
        CompletableFuture<Void> voidCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("线程号为***" + Thread.currentThread().getId());
            int i = 5;
            System.out.println("---------" + i);
            return i;
        }, THREAD_POOL_EXECUTOR).thenAccept((r) -> {
            System.out.println("thenAccept可接受传来的值，自己无返回值的串行化---");
        });

        //thenApply(x)有返回值，可以接受传来的值
        CompletableFuture<Integer> voidCompletableFuture3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("线程号为***" + Thread.currentThread().getId());
            int i = 5;
            System.out.println("---------" + i);
            return i;
        }, THREAD_POOL_EXECUTOR).thenApply((r) -> {
            System.out.println("thenApply可接受传来的值，自己有返回值的串行化---");
            return 10;
        });
    }


    /**
     * 异步，两任务组合 ：两个任务都完成，第三个任务才开始执行
     */
    public void taskCombine1() {
        //任务一
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> {
            int i = 5;
            System.out.println("任务一开始执行" + i);
            return i;
        }, THREAD_POOL_EXECUTOR);

        //任务二
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            int i = 10;
            System.out.println("任务二开始执行" + i);
            return i;
        }, THREAD_POOL_EXECUTOR);

        //要求：任务一、二都完成后才执行任务三
//      runAfterBothAsync：无传入值、无返回值
        task1.runAfterBothAsync(task2, () -> {
            System.out.println("任务三开始执行-runAfterBothAsync：无传入值、无返回值  ");
        }, THREAD_POOL_EXECUTOR);

//      thenAcceptBothAsync：有传入值、无返回值
        task1.thenAcceptBothAsync(task2, (x, y) -> {
            System.out.println("任务三开始执行-runAfterBothAsync：无传入值、无返回值  task1的结果是x ,task2的结果是y");
        }, THREAD_POOL_EXECUTOR);

//      thenCombineAsync：有传入值、有返回值
        task1.thenCombineAsync(task2, (x, y) -> {
            System.out.println("任务三开始执行-runAfterBothAsync：无传入值、无返回值  task1的结果是x ,task2的结果是y,task3返回hello");
            return "hello";
        }, THREAD_POOL_EXECUTOR);
    }

    /**
     * 要求：任务一、二[任何一个完成]都可以执行任务三
     */
    public void taskCombine2() {
        //任务一
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> {
            int i = 5;
            System.out.println("任务一开始执行" + i);
            return i;
        }, THREAD_POOL_EXECUTOR);

        //任务二
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            int i = 10;
            System.out.println("任务二开始执行" + i);
            return i;
        }, THREAD_POOL_EXECUTOR);

        //runAfterEither：无传入值、无返回值
        task1.runAfterEither(task2, () -> {
            System.out.println("任务三开始执行-runAfterEither：无传入值、无返回值  ");
        });

        //acceptEither：有传入值、无返回值
        task1.acceptEither(task2, (x) -> {
            System.out.println("任务三开始执行-runAfterBothAsync：无传入值、无返回值  task1的结果是x ,task2的结果是y");
        });

        //applyToEither：有传入值、有返回值
        task1.applyToEither(task2, (x) -> {
            System.out.println("任务三开始执行-runAfterBothAsync：无传入值、无返回值  task1的结果是x ,task2的结果是y,task3返回hello");
            return "hello";
        });
    }

    /**
     * 异步：多任务组合
     * allof,anyof方法
     * allof（等待所有任务完成）顾名思义,就是所有的任务执行完成后返回future,
     * anyif（只要有一个任务完成即可）就是只要有一个任务执行完成后就返回future并将第一个完成的参数带着一起返回,
     * 注意：最后使用get（）方法：阻塞式等待所有任务都做完，再进行下一步
     */
    public void moreTaskAllOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "f1";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//      throw new RuntimeException("aa");
            return "f2";
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2).thenApply(x -> {
            System.out.println("all");
            return null;
        });

        //阻塞，直到所有任务结束。任务complete就会执行,handler里面不一定会执行..
        System.out.println(System.currentTimeMillis() + ":阻塞");
        //join 或者get
        Void aVoid = all.get();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");

        //一个需要耗时2秒，一个需要耗时3秒，只有当最长的耗时3秒的完成后，才会结束。
        System.out.println("任务均已完成。");
    }

    /**
     * 异步多任务组合（如果是f1先返回那参数就是f1,如果是异常,那么异常就带过来了）
     */
    public void moreTaskAnyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "f1";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//      throw new RuntimeException("aa");
            return "f2";
        });
        CompletableFuture<Object> anyof = CompletableFuture.anyOf(f1, f2).handle((x, y) -> {
            System.out.println(x);
            return x;
        });
        //阻塞，直到所有任务结束。任务complete就会执行,handler里面不一定会执行..
        anyof.get();
        //一个需要耗时2秒，一个需要耗时3秒，只有当最长的耗时3秒的完成后，才会结束。
        System.out.println("任务均已完成。");
    }

}
