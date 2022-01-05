package com.karatascompany.pys3318.models.Graphs;

/**
 * Created by azizmahmud on 25.3.2018.
 */

public class UserAssignedTaskStatusModel {

    private String UserTaskStatus;
    private float UserTaskStatusInPercent;
    private int UserTaskCount;

    public String getUserTaskStatus() {
        return UserTaskStatus;
    }

    public void setUserTaskStatus(String userTaskStatus) {
        UserTaskStatus = userTaskStatus;
    }

    public float getUserTaskStatusInPercent() {
        return UserTaskStatusInPercent;
    }

    public void setUserTaskStatusInPercent(float userTaskStatusInPercent) {
        UserTaskStatusInPercent = userTaskStatusInPercent;
    }

    public int getUserTaskCount() {
        return UserTaskCount;
    }

    public void setUserTaskCount(int userTaskCount) {
        UserTaskCount = userTaskCount;
    }
}
