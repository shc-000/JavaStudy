package com.frog.study.design.state.state_pattern;

public interface EntranceMachineState {
    String insertCoin(EntranceMachine entranceMachine);

    String pass(EntranceMachine entranceMachine);
}
