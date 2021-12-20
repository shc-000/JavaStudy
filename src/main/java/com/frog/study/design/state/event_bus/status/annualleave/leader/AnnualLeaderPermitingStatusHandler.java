package com.frog.study.design.state.event_bus.status.annualleave.leader;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class AnnualLeaderPermitingStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--领导审批年休假中--leavePermit status:%s",leavePermit.getUser(),leavePermit.getStatus().getStatus()));
    }


    @Override
    protected void after(LeavePermit leavePermit){
        if(leavePermit.getEvent()==null){
            //还未审批，状态机结束，等待审批意见
            System.out.println(String.format("user:%s--等待领导审批--leavePermit status:%s",leavePermit.getUser(),leavePermit.getStatus().getStatus()));
            return;
        }
        super.goNextStatusHandler(leavePermit);
    }
}
