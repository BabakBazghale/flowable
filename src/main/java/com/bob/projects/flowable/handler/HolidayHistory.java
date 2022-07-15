package com.bob.projects.flowable.handler;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class HolidayHistory {
    private Date processStartTime;
    private Date processEndTime;
    private Long durationInMillis;

    @Builder
    public HolidayHistory(Date processStartTime, Date processEndTime, Long durationInMillis) {
        this.processStartTime = processStartTime;
        this.processEndTime = processEndTime;
        this.durationInMillis = durationInMillis;
    }
}
