package com.karatascompany.pys3318.models;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 5.6.2018.
 */

public class UserAppointedTaskModel {


    @Expose
    public int AppointedId;
    @Expose
    public int UserId;
    @Expose
    public String UserMail;

    @Expose
    @Nullable
    public int ProjectId;
    @Expose
    public int TaskId;
    @Expose
    public String TaskName;
    @Expose
    public String ProjectName;
    @Expose
    @Nullable
    public Double Score;
    @Expose
    @Nullable
    public Boolean IsDone;
    @Expose
    @Nullable
    public Double ResidulaPercentageValue;
    @Expose
    @Nullable
    private String ResidualTotalDays;

    public int getUserId() {
        return UserId;
    }

    public int getAppointedId() {
        return AppointedId;
    }

    public void setAppointedId(int appointedId) {
        AppointedId = appointedId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserMail() {
        return UserMail;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    @Nullable
    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(@Nullable int projectId) {
        ProjectId = projectId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    @Nullable
    public Double getScore() {
        return Score;
    }

    public void setScore(@Nullable Double score) {
        Score = score;
    }

    public Boolean getDone() {
        return IsDone;
    }

    public void setDone(Boolean done) {
        IsDone = done;
    }

    public Double getResidulaPercentageValue() {
        return ResidulaPercentageValue;
    }

    public void setResidulaPercentageValue(Double residulaPercentageValue) {
        ResidulaPercentageValue = residulaPercentageValue;
    }

    public String getResidualTotalDays() {
        return ResidualTotalDays;
    }

    public void setResidualTotalDays(String residualTotalDays) {
        ResidualTotalDays = residualTotalDays;
    }
}
