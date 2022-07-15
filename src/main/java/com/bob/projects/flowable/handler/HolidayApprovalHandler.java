package com.bob.projects.flowable.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class HolidayApprovalHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.printf("Approved.");
    }
}
