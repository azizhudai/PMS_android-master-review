package com.karatascompany.pys3318.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by azizmahmud on 13.3.2018.
 */

public class TaskDetailModel {

    @SerializedName("TaskDetail")
    public String TaskDetail;
    @SerializedName("SystemStartDate")
    public String SystemStartDate;
    @SerializedName("ByUserName")
    public String ByUserName;

    public String getTaskDetail() {
        return TaskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        TaskDetail = taskDetail;
    }

    public String getSystemStartDate() {
        return SystemStartDate;
    }

    public void setSystemStartDate(String systemStartDate) {
        SystemStartDate = systemStartDate;
    }

    public String getByUserName() {
        return ByUserName;
    }

    public void setByUserName(String byUserName) {
        ByUserName = byUserName;
    }
}
