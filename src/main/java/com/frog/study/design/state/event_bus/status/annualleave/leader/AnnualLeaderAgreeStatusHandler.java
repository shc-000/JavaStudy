package com.frog.study.design.state.event_bus.status.annualleave.leader;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class AnnualLeaderAgreeStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        leavePermit.setEvent(null);
        System.out.println(String.format("user:%s--直线领导同意请年休假--leavePermit status:%s",leavePermit.getUser(),leavePermit.getStatus().getStatus()));
    }

}
