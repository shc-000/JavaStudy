package com.frog.study.design.state.collection_state;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class OpenEvent implements Event {
    @Override
    public String execute() {
        return "opened";
    }
}
