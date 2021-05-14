package com.frog.study.callBack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Notifier 收到提问，执行计算（耗时操作），最后做出响应（回调接口，告诉 Caller 结果）。
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/05/14
 */
public class Notifier {
    private final static Logger LOGGER = LoggerFactory.getLogger(Notifier.class);

    public void execute(Caller caller, String msg) throws InterruptedException {
        LOGGER.info("收到消息=【{}】", msg);

        LOGGER.info("等待响应中。。。。。");
        TimeUnit.SECONDS.sleep(2);


        caller.getCallBackListener().callBackNotify("我在北京！");

    }
}
