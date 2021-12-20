package com.frog.study.design.state.event_bus;

import com.frog.study.design.state.event_bus.leavepermit.LeavePermitType;
import com.frog.study.design.state.event_bus.status.Status;
import com.frog.study.design.state.event_bus.status.handle.StatusHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class StatusHandlerRegistry {

    private static Map<String,StatusHandler> statusHandlerMap;

    static {
        statusHandlerMap=new ConcurrentHashMap<String, StatusHandler>();
    }

    private StatusHandlerRegistry(){

    }

    private static String getKey(LeavePermitType leavePermitType,Status status){
        return String.format("%s@-@%s",leavePermitType.getType(),status.name());
    }

    /**
     * 注册状态处理类
     * @param leavePermitType  请假类型
     * @param status           请假状态
     * @param statusHandler    状态处理对象
     */
    public static void registryStatusHandler(LeavePermitType leavePermitType, Status status, StatusHandler statusHandler){
        statusHandlerMap.put(getKey(leavePermitType,status),statusHandler);
    }

    /**
     * 获取状态处理类
     * @param leavePermitType  请假类型
     * @param status            请假状态
     * @return StatusHandler
     */
    public static StatusHandler acquireStatusHandler(LeavePermitType leavePermitType, Status status){
        return statusHandlerMap.get(getKey(leavePermitType,status));
    }

}
