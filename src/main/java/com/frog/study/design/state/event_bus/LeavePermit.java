package com.frog.study.design.state.event_bus;

import com.frog.study.design.state.event_bus.leavepermit.LeavePermitType;
import com.frog.study.design.state.event_bus.status.Status;
import com.frog.study.design.state.event_bus.status.annualleave.event.Event;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class LeavePermit {
    private Status status;
    private LeavePermitType leavePermitType;
    private Event event;
    private String user;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LeavePermitType getLeavePermitType() {
        return leavePermitType;
    }

    public void setLeavePermitType(LeavePermitType leavePermitType) {
        this.leavePermitType = leavePermitType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
