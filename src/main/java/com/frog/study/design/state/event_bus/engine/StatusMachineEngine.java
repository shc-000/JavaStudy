package com.frog.study.design.state.event_bus.engine;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.status.handle.LeavePermitHandler;
import com.google.common.eventbus.EventBus;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class StatusMachineEngine {

    private static EventBus eventBus;
    static{
        eventBus = new EventBus();
    }

    /**
     * 发布一条假单
     * @param leavePermit
     */
    public static void post(LeavePermit leavePermit) {
        eventBus.post(leavePermit);
    }

    /**
     * 假单处理类
     * @param statusLeavePermitHandler
     */
    public static void addListener(LeavePermitHandler statusLeavePermitHandler) {
        eventBus.register(statusLeavePermitHandler);
    }
}
