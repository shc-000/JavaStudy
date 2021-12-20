package com.frog.study.design.state.state_pattern;

import com.frog.study.design.state.Action;

import java.util.Objects;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class EntranceMachine {
    private EntranceMachineState locked = new LockedEntranceMachineState();

    private EntranceMachineState unlocked = new UnlockedEntranceMachineState();

    private EntranceMachineState state;

    public EntranceMachine(EntranceMachineState state) {
        this.state = state;
    }

    public String execute(Action action) {
        if (Objects.isNull(action)) {
            return "没有对应的实践";
        }

        if (Action.PASS.equals(action)) {
            return state.pass(this);
        }

        return state.insertCoin(this);
    }

    public boolean isUnlocked() {
        return state == unlocked;
    }

    public boolean isLocked() {
        return state == locked;
    }

    public String open() {
        setState(unlocked);
        return "opened";
    }

    public String alarm() {
        setState(locked);
        return "alarm";
    }

    public String refund() {
        setState(unlocked);
        return "refund";
    }

    public String close() {
        setState(locked);
        return "closed";
    }

    private void setState(EntranceMachineState state) {
        this.state = state;
    }

    public static void main(String[] args) {
        EntranceMachine entranceMachine = new EntranceMachine(new LockedEntranceMachineState());

        String result = entranceMachine.execute(Action.INSERT_COIN);
    }
}
