package com.frog.study.design.state.switch_on_off;

/**
 * @author shaohaichao
 * @since 2021/12/20
 */
class OffState extends State {
    @Override
    public void on(Switch s) {
        System.out.println("打开!");
        s.setState(Switch.getState("on"));
    }
    @Override
    public void off(Switch s) {
        System.out.println("已经关闭!");
    }
}
