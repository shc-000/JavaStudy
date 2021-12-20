package com.frog.study.design.state.event_bus.status.handle;

import com.frog.study.design.state.event_bus.LeavePermit;
import com.frog.study.design.state.event_bus.StatusHandlerRegistry;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class LeavePermitHandler {
    //处理假单 注解代表可以接受到StatusMachineEngine发布的假单
    @Subscribe
    @AllowConcurrentEvents
    public void handle(LeavePermit leavePermit){
        //获取到状态处理类，然后去处理 handler为StatusHandler的入口
        getStatusHandler(leavePermit).handle(leavePermit);
    }

    /**
     * 根据假单获取StatusHandler 状态处理对象
     * @param leavePermit
     * @return
     */
    public static StatusHandler getStatusHandler(LeavePermit leavePermit){
        return StatusHandlerRegistry.acquireStatusHandler(leavePermit.getLeavePermitType(),leavePermit.getStatus());
    }
}
