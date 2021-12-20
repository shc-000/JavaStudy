package com.frog.study.design.state.collection_state;

import com.frog.study.design.state.Action;
import com.frog.study.design.state.EntranceMachineState;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * @author shaohaichao
 * @since 2021/12/16
 */
@Data
public class EntranceMachine {
    List<EntranceMachineTransaction> entranceMachineTransactionList = Arrays.asList(
            EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.LOCKED)
                    .action(Action.INSERT_COIN)
                    .nextState(EntranceMachineState.UNLOCKED)
                    .event(new OpenEvent())
                    .build(),
            EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.LOCKED)
                    .action(Action.PASS)
                    .nextState(EntranceMachineState.LOCKED)
                    .event(new AlarmEvent())
                    .build(),
            EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.UNLOCKED)
                    .action(Action.PASS)
                    .nextState(EntranceMachineState.LOCKED)
                    .event(new CloseEvent())
                    .build(),
            EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.UNLOCKED)
                    .action(Action.INSERT_COIN)
                    .nextState(EntranceMachineState.UNLOCKED)
                    .event(new RefundEvent())
                    .build()
    );

    private EntranceMachineState state;

    public EntranceMachine(EntranceMachineState state) {
        setState(state);
    }

    public String execute(Action action) {
        Optional<EntranceMachineTransaction> transactionOptional = entranceMachineTransactionList
                .stream()
                .filter(transaction ->
                        transaction.getAction().equals(action) && transaction.getCurrentState().equals(state))
                .findFirst();

        if (!transactionOptional.isPresent()) {
            return "没有实践";
        }

        EntranceMachineTransaction transaction = transactionOptional.get();
        setState(transaction.getNextState());
        return transaction.getEvent().execute();
    }

    public static void main(String[] args) {
        EntranceMachine entranceMachine = new EntranceMachine(EntranceMachineState.LOCKED);

        String result = entranceMachine.execute(Action.INSERT_COIN);
    }
}
