package com.frog.study.design.state.switch_state;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */

import com.frog.study.design.state.Action;
import com.frog.study.design.state.EntranceMachineState;
import lombok.Data;

import java.util.Objects;

@Data
public class EntranceMachine {

    private EntranceMachineState state;

    public EntranceMachine(EntranceMachineState state) {
        this.state = state;
    }

    public String execute(Action action) {
        if (Objects.isNull(action)) {
            return "没有对应的实践";
        }

        if (EntranceMachineState.LOCKED.equals(state)) {
            switch (action) {
                case INSERT_COIN:
                    setState(EntranceMachineState.UNLOCKED);
                    return open();
                case PASS:
                    return alarm();
            }
        }

        if (EntranceMachineState.UNLOCKED.equals(state)) {
            switch (action) {
                case PASS:
                    setState(EntranceMachineState.LOCKED);
                    return close();
                case INSERT_COIN:
                    return refund();
            }
        }
        return null;
    }

    private String refund() {
        return "refund";
    }

    private String close() {
        return "closed";
    }

    private String alarm() {
        return "alarm";
    }

    private String open() {
        return "opened";
    }

    public static void main(String[] args) {
        EntranceMachine entranceMachine = new EntranceMachine(EntranceMachineState.LOCKED);
        System.out.println(entranceMachine.execute(Action.INSERT_COIN));
    }
}
