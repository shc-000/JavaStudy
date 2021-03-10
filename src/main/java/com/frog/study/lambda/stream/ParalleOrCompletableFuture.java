package com.frog.study.lambda.stream;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * 使用并行流还是CompletableFuture
 * 对集合进行计算，可以使用并行ParallelStream和异步的CompletableFuture操作，都可以加快其处理，那么到底该使用并行还是异步呢
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/3/3
 */
public class ParalleOrCompletableFuture {

    private List<Shop> shops;

    /**
     * ParallelStream操作
     */
    public List<String> findPricesParallel() {
        return shops.parallelStream()
                .map(shop -> shop.getName() + " price is " + shop.getPrice())
                .collect(Collectors.toList());
    }

    /**
     * CompletableFuture操作
     */
    public List<String> findPricesFuture() {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is "
                                + shop.getPrice()))
                        .collect(Collectors.toList());

        List<String> prices = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return prices;
    }
//    以下两种写法的区别是什么呢？
//    它们内部采用的是同样的通用线程池，默认都使用固定数目的线程，具体线程数取决于Runtime.getRuntime().availableProcessors()的返回值。
//    然而，CompletableFuture具有一定的优势，因为它允许你对执行器(Executor)进行配置，尤其是线程池的大小，让它以更适合应用需求的方式进行配置，满足程序的要求，而这是并行流API无法提供的。
//    让我们看看你怎样利用这种配置上的灵活性带来实际应用程序性能上的提升。

//    使用定制的执行器
//    就这个主题而言，明智的选择似乎是创建一个配有线程池的执行器，线程池中线程的数目取决于你预计你的应用需要处理的负荷，但是你该如何选择合适的线程数目呢?
//    你的应用99%的时间都在等待商店的响应，所以估算出的W/C比率为100。这意味着如果你 期望的CPU利用率是100%，你需要创建一个拥有400个线程的线程池。
//    实际操作中，如果你创建 的线程数比商店的数目更多，反而是一种浪费，因为这样做之后，你线程池中的有些线程根本没 有机会被使用。
//    出于这种考虑，我们建议你将执行器使用的线程数，与你需要查询的商店数目设 定为同一个值，这样每个商店都应该对应一个服务线程。
//    不过，为了避免发生由于商店的数目过 多导致服务器超负荷而崩溃，你还是需要设置一个上限，比如100个线程。
//  代码清单如下所示。

    //创建一个线程池，线程池中线程的数目为100 和商店数目 二者中较小 的一个值
    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            //使用守护线程——这种方式不会阻止程序的关停
            t.setDaemon(true);
            return t;
        }
    });
    //注意:你现在正创建的是一个由守护线程构成的线程池。
    //Java程序无法终止或者退出一个正 在运行中的线程，所以最后剩下的那个线程会由于一直等待无法发生的事件而引发问题。
    //与此相反，如果将线程标记为守护进程，意味着程序退出时它也会被回收。这二者之间没有性能上的差异。
    //现在，你可以将执行器作为第二个参数传递给supplyAsync工厂方法了。
    //比如，你现在可以按照下面的方式创建一个可查询指定商品价格的CompletableFuture对象:

    // CompletableFuture.supplyAsync(() -> shop.getName() + " price is " +
    //                                        shop.getPrice(product), executor);
    //


    //结论
    //目前为止，你已经知道对集合进行并行计算有两种方式:要么将其转化为并行流，利用map这样的操作开展工作，要么枚举出集合中的每一个元素，创建新的线程，在Completable- Future内对其进行操作。后者提供了更多的灵活性，你可以调整线程池的大小，而这能帮助你确保整体的计算不会因为线程都在等待I/O而发生阻塞。
    //我们对使用这些API的建议如下。
    //
    //如果你进行的是计算密集型的操作，并且没有I/O，那么推荐使用Stream接口，因为实现简单，同时效率也可能是最高的(如果所有的线程都是计算密集型的，那就没有必要创建比处理器核数更多的线程)。
    //反之，如果你并行的工作单元还涉及等待I/O的操作(包括网络连接等待)，那么使用CompletableFuture灵活性更好，你可以像前文讨论的那样，依据等待/计算，或者 W/C的比率设定需要使用的线程数。这种情况不使用并行流的另一个原因是，处理流的 流水线中如果发生I/O等待，流的延迟特性会让我们很难判断到底什么时候触发了等待。

    @Data
    public static class Shop {
        private String name;
        private String Price;
    }
}
