package com.frog.study.design.state.event_bus.status.handle;

import com.frog.study.design.state.event_bus.LeavePermit;

/**
 * @author shaohaichao
 */
public interface AbstractStatusHandler {

    public void handle(LeavePermit leavePermit);

}
