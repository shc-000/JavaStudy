package com.frog.study.design.obj;

import java.util.Objects;

/**
 * 空对象模式
 *
 * @author shaohaichao
 * @since 2021/10/15
 */
public class MyParser implements Parser {
    private static final Action DO_NOTHING = new Action() {
        //匿名内部类
        @Override
        public void doSomething() {
            System.out.println("do some...");
        }
    };

    @Override
    public Action findAction(String userInput) {
        // ...
        Action action = null;

        /* we can't find any actions */
        if (Objects.isNull(action)) {
            return DO_NOTHING;
        }
        return action;
    }

    public static void main(String[] args) {
        MyParser myParser = new MyParser();
        myParser.findAction("someInput").doSomething();
    }
}
