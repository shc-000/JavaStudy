package com.frog.study.callBack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件回调其实是一种常见的设计模式
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/05/14
 */
public class Test {
//    1.Caller 向 Notifier 提问。
//    2.提问方式是异步，接着做其他事情。
//    3.Notifier 收到问题执行计算然后回调 Caller 告知结果。
    private final static Logger LOGGER = LoggerFactory.getLogger(Notifier.class);

    public static void main(String[] args) {
        Notifier notifier = new Notifier() ;

        Caller caller = new Caller() ;
        caller.setNotifier(notifier) ;
        caller.setQuestion("你在哪儿！");
        caller.setCallBackListener(new CallBackListener() {
            @Override
            public void callBackNotify(String msg) {
                LOGGER.info("回复=【{}】" ,msg);
            }
        });

        caller.call();
    }
}
