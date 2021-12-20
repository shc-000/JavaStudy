package com.frog.study.design.pub_sub.event_bus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executors;

/**
 * @author shaohaichao
 * @since 2021/12/17
 */
public class EventListener {
    /**
     * 监听 Integer 类型的消息
     */
    @Subscribe
    public void listenInteger(Integer param) {

        System.out.println("EventListener#listenInteger ->" + param);

    }

    /**
     * 监听 String 类型的消息
     */

    @Subscribe
    public void listenString(String param) {
        System.out.println("EventListener#listenString ->" + param);
    }

    public static void main(String[] args) {
        syc();
//        asyc();
    }

    /**
     * 同步方式（默认构造器创建new EventBus()）
     * Guava Event 实际上是使用线程池来处理订阅消息的，通过源码可以看出，当我们使用默认的构造方法创建 EventBus 的时候，
     * 其中 executor 为 MoreExecutors.directExecutor()，其具体实现中直接调用的 Runnable#run 方法，使其仍然在同一个线程中执行，所以默认操作仍然是同步的.
     * 这种处理方法也有适用的地方，这样既可以解耦也可以让方法在同一个线程中执行获取同线程中的便利，比如事务的处理
     */
    public static void syc(){
        //post只支持一个参数
        EventBus eventBus = new EventBus();
        // TODO-SHC: 2021/12/20 Map中维护类型及其lister方法的映射关系
        eventBus.register(new EventListener());
//        eventBus.register(new EventListener());注册多个
        // TODO-SHC: 2021/12/20 根据event(1,2,'3')的数据类型，找到对应的操作
        eventBus.post(1);
        eventBus.post(2);
        eventBus.post("3");
    }

    /**
     * 异步方式
     * 通过上面的源码，可以看出只要将构造方法中的 executor 换成一个线程池实现即可, 同时 Guava EventBus 为了简化操作，提供了一个简化的方案即 AsyncEventBus
     */
    public static void asyc(){
        EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        eventBus.register(new EventListener());
        eventBus.post(1);
        eventBus.post(2);
        eventBus.post("3");
    }

    //在上面的基础上，我们可以定义一些消息类型来实现不同消息的监听和处理，
    // 通过实现 SubscriberExceptionHandler 来处理异常的情况，无论时同步还是异步都能游刃有余
}
