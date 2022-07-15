package com.bob.projects.flowable.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class HolidayRequest {
    @NotNull
    private String employerName;
    @NotNull
    @Min(1)
    @Max(5)
    private Long holidaysCount;
    @NotNull
    private String comment;
}
