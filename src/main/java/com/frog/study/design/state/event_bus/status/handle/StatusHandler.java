package com.frog.study.design.state.event_bus.status.handle;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.engine.StatusMachineEngine;
import com.frog.study.design.state.event_bus.status.machine.factory.StatusMachineFactory;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public abstract class StatusHandler implements AbstractStatusHandler{

    protected void before(LeavePermit leavePermit){

    }


    @Override
    public void handle(LeavePermit leavePermit){
        before(leavePermit);
        doHandler(leavePermit);
        after(leavePermit);
    }

    protected abstract void doHandler(LeavePermit leavePermit);


    protected void after(LeavePermit leavePermit){
        goNextStatusHandler(leavePermit);
    }

    protected void goNextStatusHandler(LeavePermit leavePermit){
        //获取下一个状态
        leavePermit.setStatus(StatusMachineFactory
                .getStatusMachine(leavePermit.getLeavePermitType()).getNextStatus(leavePermit.getStatus(),leavePermit.getEvent()));
        //状态机引擎去处理该假单
        StatusMachineEngine.post(leavePermit);
    }
}
