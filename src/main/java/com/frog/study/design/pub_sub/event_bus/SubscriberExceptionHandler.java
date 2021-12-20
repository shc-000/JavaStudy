package com.frog.study.design.pub_sub.event_bus;

import com.google.common.eventbus.SubscriberExceptionContext;

/**
 * 如果处理时发生异常应该如何处理? 在看源码中，无论是 EventBus 还是 AsyncEventBus 都可传入自定义的 SubscriberExceptionHandler
 * 该 handler 当出现异常时会被调用，我可可以从参数 exception 获取异常信息，从 context 中获取消息信息进行特定的处理
 */
public interface SubscriberExceptionHandler {
    /**
     * Handles exceptions thrown by subscribers.
     */
    void handleException(Throwable exception, SubscriberExceptionContext context);

}
