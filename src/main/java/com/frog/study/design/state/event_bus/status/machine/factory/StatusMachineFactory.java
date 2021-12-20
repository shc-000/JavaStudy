package com.frog.study.design.state.event_bus.status.machine.factory;

import com.frog.study.design.state.event_bus.leavepermit.LeavePermitType;
import com.frog.study.design.state.event_bus.status.machine.AnnualLeaveStatusMachine;
import com.frog.study.design.state.event_bus.status.machine.MedicalLeaveStatusMachine;
import com.frog.study.design.state.event_bus.status.machine.StatusMachine;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class StatusMachineFactory {

    private StatusMachineFactory(){

    }

    /**
     * 根据状态获取状态机
     * @param leavePermitType
     * @return 对应请假类型的状态机
     */
    public static StatusMachine getStatusMachine(LeavePermitType leavePermitType){
        switch (leavePermitType){
            case MEDICAL_LEAVE:
                return new MedicalLeaveStatusMachine();
            case ANNUAL_LEAVE:
                return new AnnualLeaveStatusMachine();
            default:
                throw new RuntimeException("未知类型");
        }
    }

}
