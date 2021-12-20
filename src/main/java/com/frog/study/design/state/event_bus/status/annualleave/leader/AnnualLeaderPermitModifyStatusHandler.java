package com.frog.study.design.state.event_bus.status.annualleave.leader;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class AnnualLeaderPermitModifyStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--领导退回假单补充材料--leavePermit status:%s",leavePermit.getUser(),leavePermit.getStatus().getStatus()));
    }

}
