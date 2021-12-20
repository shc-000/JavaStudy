package com.frog.study.design.state.state_pattern;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class LockedEntranceMachineState implements EntranceMachineState {
    @Override
    public String insertCoin(EntranceMachine entranceMachine) {
        return entranceMachine.open();
    }

    @Override
    public String pass(EntranceMachine entranceMachine) {
        return entranceMachine.alarm();
    }
}
