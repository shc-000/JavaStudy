package com.frog.study.design.state.switch_on_off;

/**
 * @author shaohaichao
 * @since 2021/12/20
 */
abstract class State {
    public abstract void on(Switch s);
    public abstract void off(Switch s);
}
