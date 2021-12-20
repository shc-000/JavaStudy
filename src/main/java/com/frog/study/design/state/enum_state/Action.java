package com.frog.study.design.state.enum_state;

public enum Action {
    PASS {
        @Override
        public String execute(EntranceMachine entranceMachine, EntranceMachineState state) {
            return state.pass(entranceMachine);
        }
    },
    INSERT_COIN {
        @Override
        public String execute(EntranceMachine entranceMachine, EntranceMachineState state) {
            return state.insertCoin(entranceMachine);
        }
    };

    public abstract String execute(EntranceMachine entranceMachine, EntranceMachineState state);
}
