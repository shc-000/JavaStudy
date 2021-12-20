package com.frog.study.design.state.event_bus.status.medicalleave.leader;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class MedicalLeaderAgreeStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        leavePermit.setEvent(null);
        System.out.println(String.format("user:%s--领导同意休病假--leavePermit status:%s-%s",leavePermit.getUser(),leavePermit.getStatus().getStatus(),leavePermit.getStatus().getMemo()));
    }

}
