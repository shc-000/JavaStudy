package com.frog.study.threadpool.deliver;

import javax.annotation.Nullable;
import java.util.concurrent.Executor;

/**
 * 默认情况下，用于Java平台的交付。
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/18
 */
public final class JavaDeliver implements Executor {

    private static JavaDeliver instance = new JavaDeliver();

    /**
     * 使用单利模式获取对象
     * @return                      JavaDeliver对象
     */
    public static JavaDeliver getInstance() {
        return instance;
    }

    /**
     * 注意增加非空判断
     * @param runnable              runnable
     */
    @Override
    public void execute(@Nullable Runnable runnable) {
        if (runnable!=null){
            runnable.run();
        }
    }


}
