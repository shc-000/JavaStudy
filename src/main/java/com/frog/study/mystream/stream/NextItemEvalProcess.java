package com.frog.study.mystream.stream;

import com.frog.study.mystream.function.EvalFunction;

/**
 * 下一个元素求值过程
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
public class NextItemEvalProcess {

    /**
     * 求值方法
     * */
    private EvalFunction evalFunction;

    public NextItemEvalProcess(EvalFunction evalFunction) {
        this.evalFunction = evalFunction;
    }

    MyStream eval(){
        return evalFunction.apply();
    }
}
