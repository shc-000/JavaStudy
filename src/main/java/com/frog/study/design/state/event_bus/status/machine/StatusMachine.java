package com.frog.study.design.state.event_bus.status.machine;

import com.frog.study.design.state.event_bus.status.Status;
import com.frog.study.design.state.event_bus.status.annualleave.event.Event;

public interface StatusMachine {

    public Status getNextStatus(Status status, Event event);

}
