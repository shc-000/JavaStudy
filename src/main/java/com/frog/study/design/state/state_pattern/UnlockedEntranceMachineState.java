package com.frog.study.design.state.state_pattern;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class UnlockedEntranceMachineState implements EntranceMachineState{
    @Override
    public String insertCoin(EntranceMachine entranceMachine) {
        return entranceMachine.refund();
    }

    @Override
    public String pass(EntranceMachine entranceMachine) {
        return entranceMachine.close();
    }
}
