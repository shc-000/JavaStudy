package com.frog.study.design.state.event_bus.status.medicalleave;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class MedicalPermitSuccessStatusHandler extends StatusHandler {

    @Override
    protected void doHandler(LeavePermit leavePermit){
        System.out.println(String.format("user:%s--成功病假审批--leavePermit status:%s-%s",leavePermit.getUser(),leavePermit.getStatus().getStatus(),leavePermit.getStatus().getMemo()));
    }

    @Override
    protected void after(LeavePermit leavePermit){

    }
}
