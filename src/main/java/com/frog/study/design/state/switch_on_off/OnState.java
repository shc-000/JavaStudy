package com.frog.study.design.state.switch_on_off;

/**
 * @author shaohaichao
 * @since 2021/12/20
 */
class OnState extends State {
    @Override
    public void on(Switch s) {
        System.out.println("已经打开!");
    }
    @Override
    public void off(Switch s) {
        System.out.println("关闭!");
        s.setState(Switch.getState("off"));
    }
}

