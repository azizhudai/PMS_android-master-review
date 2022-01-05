package com.karatascompany.pys3318.models.Graphs;

/**
 * Created by azizmahmud on 26.3.2018.
 */

public class ProjectTaskStatusModel {

    public String  TaskStatus;
    public float TaskStatusInPercent;
    public int TaskStatusCount;

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public float getTaskStatusInPercent() {
        return TaskStatusInPercent;
    }

    public void setTaskStatusInPercent(float taskStatusInPercent) {
        TaskStatusInPercent = taskStatusInPercent;
    }

    public int getTaskStatusCount() {
        return TaskStatusCount;
    }

    public void setTaskStatusCount(int taskStatusCount) {
        TaskStatusCount = taskStatusCount;
    }
}
