package com.bob.projects.flowable.service;

import com.bob.projects.flowable.handler.HolidayHistory;
import com.bob.projects.flowable.model.HolidayRequest;
import com.bob.projects.flowable.model.ProcessInstanceResponse;
import com.bob.projects.flowable.model.TaskResponse;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class HolidayService {
    public static final String TASK_CANDIDATE_GROUP = "managers";
    public static final String PROCESS_KEY = "holidayRequest";
    public static final String HOLIDAY_REQUEST_PROCESS_DIR = "process/holiday-request.bpmn20.xml";
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;

    public Boolean processDeployment() {
        repositoryService.createDeployment()
                .addClasspathResource(HOLIDAY_REQUEST_PROCESS_DIR)
                .deploy();
        return true;
    }

    public ProcessInstanceResponse holidayRequest(HolidayRequest holidayRequest) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", holidayRequest.getEmployerName());
        variables.put("noOfHolidays", holidayRequest.getHolidaysCount());
        variables.put("description", holidayRequest.getComment());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                PROCESS_KEY, variables);
        return ProcessInstanceResponse.builder()
                .processId(processInstance.getId())
                .isEnded(processInstance.isEnded()).build();
    }


    public List<TaskResponse> fetchTasks() {
        List<Task> tasks = taskService.createTaskQuery().
                taskCandidateGroup(TASK_CANDIDATE_GROUP).list();
        List<TaskResponse> taskResponses = fetchTaskDetails(tasks);
        return taskResponses;
    }

    public List<TaskResponse> fetchUserTasks() {
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(PROCESS_KEY).list();
        List<TaskResponse> taskResponses = fetchTaskDetails(tasks);
        return taskResponses;
    }

    private List<TaskResponse> fetchTaskDetails(List<Task> tasks) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        tasks.forEach(x -> {
            Map<String, Object> processVariables = taskService
                    .getVariables(x.getId());
            taskResponses.add(TaskResponse.builder()
                    .taskId(x.getId())
                    .taskName(x.getName())
                    .taskData(processVariables).build());
        });
        return taskResponses;
    }


    public Boolean approveHoliday(String taskId, Boolean approved) {
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("approved", approved.booleanValue());
        taskService.complete(taskId, processVariables);
        return true;
    }

    public List<HolidayHistory> validateProcessHistory(String processId) {
        List<HolidayHistory> holidayHistories = new ArrayList<>();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processId)
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        activities.forEach(x -> {
            HolidayHistory holidayHistory = HolidayHistory.builder().processEndTime(x.getEndTime())
                    .processStartTime(x.getStartTime())
                    .durationInMillis(x.getDurationInMillis()).build();
            holidayHistories.add(holidayHistory);
        });
        return holidayHistories;
    }

}
