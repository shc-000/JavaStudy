package com.frog.study.future;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/28 9:15 下午
 */
public class CallableServiceTest {

    private static final int FUTURE_TIMEOUT = 15000;

    private final static BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<>();

    /**
     * 执行callable的线程池
     */
    private static final ThreadFactory SPRING_THREAD_FACTORY = new CustomizableThreadFactory("shc-thread-pool-");
    private final static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 15, 1, TimeUnit.MINUTES, QUEUE, SPRING_THREAD_FACTORY);

    public List<String> getCallableData(List<String> requestUrlList) {

        if (CollectionUtils.isEmpty(requestUrlList)) {
            return new ArrayList<>();
        }

        List<FutureTask<String>> futureTaskList = new ArrayList<>();
        for (String url : requestUrlList) {
            RequestCallable callable = new RequestCallable(url);
            FutureTask<String> futureTask = new FutureTask<>(callable);
            futureTaskList.add(futureTask);
            // 线程池开始获取数据
            THREAD_POOL_EXECUTOR.submit(futureTask);
        }

        // 获取数据
        List<String> resultDataList = new ArrayList<>();
        for (FutureTask<String> futureTask : futureTaskList) {
            try {
                // 获取数据，注意有超时时间，如果超出，即获取不到数据
                String resultData = futureTask.get(FUTURE_TIMEOUT, TimeUnit.MILLISECONDS);
                resultDataList.add(resultData);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("超时");
                e.printStackTrace();
            } finally {
                // 最后一定要调用cancel方法，里面的参数 mayInterruptIfRunning 是是否在运行的时候也关闭，如果设置为true，那么在
                // 运行的时候也能关闭，之后的代码不会再执行。
                // 如果正在运行，暂停成功，会返回true，如果运行完了，那么不管 mayInterruptIfRunning 是什么值，都会返回false。
                futureTask.cancel(true);
            }
        }

        return resultDataList;
    }

    public static void main(String[] args) {
        CallableServiceTest test = new CallableServiceTest();
        test.getCallableData(Arrays.asList("url", "url")).forEach(System.out::println);
        //关闭线程池
        THREAD_POOL_EXECUTOR.shutdownNow();
    }
}
