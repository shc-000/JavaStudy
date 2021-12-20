package com.frog.study.design.state.collection_state;

import com.frog.study.design.state.Action;
import com.frog.study.design.state.EntranceMachineState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntranceMachineTransaction {

    private EntranceMachineState currentState;

    private Action action;

    private EntranceMachineState nextState;

    private Event event;
}
