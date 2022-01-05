package com.karatascompany.pys3318.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by azizmahmud on 7.3.2018.
 */

public class TaskModel {

    @SerializedName("TaskId")

    private int TaskId;
    @SerializedName("ProjectName")

    private String ProjectName;
    @SerializedName("TaskName")
    private String TaskName;
    @Nullable
    @SerializedName("IsNow")

    public Boolean IsNow; //şuansa true gelecek ise false geçmiş ise null
    @SerializedName("StartDateStr")

    private String StartDateStr;
    @SerializedName("EndDateStr")

    private String EndDateStr;

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    @Nullable
    public Boolean getNow() {
        return IsNow;
    }

    public void setNow(@Nullable Boolean now) {
        IsNow = now;
    }

    public String getStartDateStr() {
        return StartDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        StartDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return EndDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        EndDateStr = endDateStr;
    }
}
