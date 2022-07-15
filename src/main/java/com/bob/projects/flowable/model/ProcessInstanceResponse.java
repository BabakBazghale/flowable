package com.bob.projects.flowable.model;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcessInstanceResponse {
    String processId;
    boolean isEnded;
    @Builder
    public ProcessInstanceResponse(String processId, boolean isEnded) {
        this.processId = processId;
        this.isEnded = isEnded;
    }
}
