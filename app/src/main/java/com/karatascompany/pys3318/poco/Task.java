package com.karatascompany.pys3318.poco;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 22.3.2018.
 */

public class Task {

    @Expose
    private int UserId;
    @Expose
    private int ProjectId;
    @Expose
    private String TaskName;
    @Expose
    private String TaskDetail;
    @Expose
    private String TaskStartDate;
    @Expose
    private String TaskEndDate;

    public Task(int userId, int projectId, String taskName, String taskDetail, String taskStartDate, String taskEndDate) {
        UserId = userId;
        ProjectId = projectId;
        TaskName = taskName;
        TaskDetail = taskDetail;
        TaskStartDate = taskStartDate;
        TaskEndDate = taskEndDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskDetail() {
        return TaskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        TaskDetail = taskDetail;
    }

    public String getTaskStartDate() {
        return TaskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        TaskStartDate = taskStartDate;
    }

    public String getTaskEndDate() {
        return TaskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        TaskEndDate = taskEndDate;
    }
}
