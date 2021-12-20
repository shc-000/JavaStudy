package com.frog.study.design.state.event_bus.status.annualleave;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class AnnualPermitSubmitStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--提交年休假假单--leavePermit status:%s",leavePermit.getUser(),leavePermit.getStatus().getStatus()));
    }

}

