package com.frog.study.design.state.event_bus.status.medicalleave.hr;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class MedicalHrPermitingStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--hr审批病假中--leavePermit status:%s-%s",leavePermit.getUser(),leavePermit.getStatus().getStatus(),leavePermit.getStatus().getMemo()));
    }
    @Override
    protected void after(LeavePermit leavePermit){
        if(leavePermit.getEvent()==null){
            //还未审批，状态机结束，等待审批意见
            System.out.println(String.format("user:%s--等待hr审批--leavePermit status:%s",leavePermit.getUser(),leavePermit.getStatus().getStatus()));
            return;
        }
        goNextStatusHandler(leavePermit);
    }
}
