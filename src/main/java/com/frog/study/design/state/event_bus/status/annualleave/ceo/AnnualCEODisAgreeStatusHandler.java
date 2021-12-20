package com.frog.study.design.state.event_bus.status.annualleave.ceo;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class AnnualCEODisAgreeStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--CEO不同意休年休假--leavePermit status:%s",leavePermit.getUser()));
    }

}
