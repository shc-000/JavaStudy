package com.frog.study.design.state.enum_state;

import lombok.Data;

import java.util.Objects;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
@Data
public class EntranceMachine {
    private EntranceMachineState state;

    public EntranceMachine(EntranceMachineState state) {
        setState(state);
    }

    public String execute(Action action) {
        if (Objects.isNull(action)) {
            return "没实践";
        }

        return action.execute(this, state);
    }

    public String open() {
        return "opened";
    }

    public String alarm() {
        return "alarm";
    }

    public String refund() {
        return "refund";
    }

    public String close() {
        return "closed";
    }
}
