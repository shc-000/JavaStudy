package com.frog.study.design.state.event_bus.status.annualleave.ceo;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class AnnualCEOPermitModifyStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--ceo退回年休假单补充材料--leavePermit status:%s",leavePermit.getUser()));
    }

}