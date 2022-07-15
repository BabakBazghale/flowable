package com.bob.projects.flowable.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TaskResponse {
    String taskId;
    String taskName;
    Map<String, Object> taskData;

    @Builder
    public TaskResponse(String taskId, String taskName, Map<String, Object> taskData) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskData = taskData;
    }
}
