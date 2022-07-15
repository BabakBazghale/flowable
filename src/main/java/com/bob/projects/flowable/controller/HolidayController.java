package com.bob.projects.flowable.controller;

import com.bob.projects.flowable.handler.HolidayHistory;
import com.bob.projects.flowable.model.HolidayRequest;
import com.bob.projects.flowable.model.ProcessInstanceResponse;
import com.bob.projects.flowable.model.TaskResponse;
import com.bob.projects.flowable.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/process/deployment")
    public Boolean processDeployment() {
        return holidayService.processDeployment();
    }

    @PostMapping("/user/request")
    public ProcessInstanceResponse holidayRequest(@Valid @RequestBody HolidayRequest holidayRequest) {
        return holidayService.holidayRequest(holidayRequest);
    }

    @GetMapping("/user/fetch-tasks")
    public List<TaskResponse> fetchUserTasks() {
        return holidayService.fetchUserTasks();
    }

    @GetMapping("/manager/fetch-tasks")
    public List<TaskResponse> fetchTasks() {
        return holidayService.fetchTasks();
    }

    @PostMapping("/manager/set/task-status")
    public Boolean approveTask(@RequestParam("taskId") String taskId, @RequestParam boolean status) {
       return holidayService.approveHoliday(taskId, status);
    }

    @GetMapping("/fetch-history")
    public List<HolidayHistory> validateProcessHistory(@RequestParam("processId") String processId) {
        return holidayService.validateProcessHistory(processId);
    }
}