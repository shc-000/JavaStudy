package com.frog.study.design.state.event_bus.status.medicalleave.leader;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class MedicalLeaderDisAgreeStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--领导不同意休病假--leavePermit status:%s-%s",leavePermit.getUser(),leavePermit.getStatus().getStatus(),leavePermit.getStatus().getMemo()));
    }

}
