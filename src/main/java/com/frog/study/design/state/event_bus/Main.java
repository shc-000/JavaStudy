package com.frog.study.design.state.event_bus;

import com.frog.study.design.state.event_bus.engine.StatusMachineEngine;
import com.frog.study.design.state.event_bus.leavepermit.LeavePermitType;
import com.frog.study.design.state.event_bus.status.Status;
import com.frog.study.design.state.event_bus.status.annualleave.AnnualPermitFailStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.AnnualPermitSubmitStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.AnnualPermitSuccessStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.ceo.AnnualCEOAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.ceo.AnnualCEODisAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.ceo.AnnualCEOPermitModifyStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.ceo.AnnualCEOPermitingStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.event.Event;
import com.frog.study.design.state.event_bus.status.annualleave.leader.AnnualLeaderAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.leader.AnnualLeaderDisAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.leader.AnnualLeaderPermitModifyStatusHandler;
import com.frog.study.design.state.event_bus.status.annualleave.leader.AnnualLeaderPermitingStatusHandler;
import com.frog.study.design.state.event_bus.status.handle.LeavePermitHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.MedicalPermitFailStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.MedicalPermitSubmitStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.MedicalPermitSuccessStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.hr.MedicalHrAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.hr.MedicalHrDisAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.hr.MedicalHrPermitModifyStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.hr.MedicalHrPermitingStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.leader.MedicalLeaderAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.leader.MedicalLeaderDisAgreeStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.leader.MedicalLeaderPermitModifyStatusHandler;
import com.frog.study.design.state.event_bus.status.medicalleave.leader.MedicalLeaderPermitingStatusHandler;

/**
 * @author shaohaichao
 * @since 2021/12/16
 */
public class Main {

    public static void main(String[] args) {
        //注册年休假的状态和对应状态的处理类StatusHandler。
        registryAnnualPermitStatusHandler();
        //注册病假的状态和对应状态的处理类StatusHandler。
        registryMedicalPermitStatusHandler();

        LeavePermitHandler leavePermitHandler=new LeavePermitHandler();
        //状态机引擎接受事件处理类
        StatusMachineEngine.addListener(leavePermitHandler);
        //生成假单
        LeavePermit leavePermit=new LeavePermit();
        leavePermit.setLeavePermitType(LeavePermitType.ANNUAL_LEAVE);
        leavePermit.setStatus(Status.PERMIT_SUBMIT);
        leavePermit.setUser("jettyrun");
        //假单交给引擎去执行
        StatusMachineEngine.post(leavePermit);

        System.out.println("----- 分割线 代表假条需要领导审批了，领导给个通过意见,然后状态机接着走-------");
        leavePermit.setEvent(Event.AGREE);
        StatusMachineEngine.post(leavePermit);
        System.out.println("----- 分割线 代表假条需要ceo审批了，ceo给个通过意见,然后状态机接着走-------");
        leavePermit.setEvent(Event.AGREE);
        StatusMachineEngine.post(leavePermit);
        System.out.println("--->>>>>>>>>end<<<<<<<<-------");


        LeavePermit leavePermit2=new LeavePermit();
        leavePermit2.setLeavePermitType(LeavePermitType.MEDICAL_LEAVE);
        leavePermit2.setStatus(Status.PERMIT_SUBMIT);
        leavePermit2.setUser("jettyrun2");
        StatusMachineEngine.post(leavePermit2);

        System.out.println("----- 分割线 代表假条需要领导审批了，领导给个通过意见,然后状态机接着走-------");
        leavePermit2.setEvent(Event.AGREE);
        StatusMachineEngine.post(leavePermit2);


        System.out.println("----- 分割线 代表假条需要hr审批了，hr给个通过意见,然后状态机接着走-------");
        leavePermit2.setEvent(Event.AGREE);
        StatusMachineEngine.post(leavePermit2);
        System.out.println("--->>>>>>>>>end<<<<<<<<-------");



        LeavePermit leavePermit3=new LeavePermit();
        leavePermit3.setLeavePermitType(LeavePermitType.MEDICAL_LEAVE);
        leavePermit3.setStatus(Status.PERMIT_SUBMIT);
        leavePermit3.setUser("jettyrun3");
        StatusMachineEngine.post(leavePermit3);

        System.out.println("----- 分割线 代表假条需要领导审批了，领导给个通过意见,然后状态机接着走-------");
        leavePermit3.setEvent(Event.DISSAGREE);
        StatusMachineEngine.post(leavePermit3);
        System.out.println("--->>>>>>>>>end<<<<<<<<-------");

    }


    public static void registryAnnualPermitStatusHandler() {

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.PERMIT_SUBMIT, new AnnualPermitSubmitStatusHandler());

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.LEADER_PERMIT_AGREE, new AnnualLeaderAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.LEADER_PERMIT_DISAGREE, new AnnualLeaderDisAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.LEADER_PERMIT_MODIFY, new AnnualLeaderPermitModifyStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.LEADER_PERMITING, new AnnualLeaderPermitingStatusHandler());

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.CEO_PERMIT_AGREE, new AnnualCEOAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.CEO_PERMIT_DISAGREE, new AnnualCEODisAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.CEO_PERMIT_MODIFY, new AnnualCEOPermitModifyStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.CEO_PERMITING, new AnnualCEOPermitingStatusHandler());

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.PERMIT_SUCCESS, new AnnualPermitSuccessStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.ANNUAL_LEAVE, Status.PERMIT_FAIL, new AnnualPermitFailStatusHandler());
    }



    public static void registryMedicalPermitStatusHandler() {

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.PERMIT_SUBMIT, new MedicalPermitSubmitStatusHandler());

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.LEADER_PERMIT_AGREE, new MedicalLeaderAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.LEADER_PERMIT_DISAGREE, new MedicalLeaderDisAgreeStatusHandler
                ());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.LEADER_PERMIT_MODIFY, new MedicalLeaderPermitModifyStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.LEADER_PERMITING, new MedicalLeaderPermitingStatusHandler());

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.HR_PERMIT_AGREE, new MedicalHrAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.HR_PERMIT_DISAGREE, new MedicalHrDisAgreeStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.HR_PERMIT_MODIFY, new MedicalHrPermitModifyStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.HR_PERMITING, new MedicalHrPermitingStatusHandler());

        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.PERMIT_SUCCESS, new MedicalPermitSuccessStatusHandler());
        StatusHandlerRegistry.registryStatusHandler(LeavePermitType.MEDICAL_LEAVE, Status.PERMIT_FAIL, new MedicalPermitFailStatusHandler());
    }
}
