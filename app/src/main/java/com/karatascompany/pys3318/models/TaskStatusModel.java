package com.karatascompany.pys3318.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by azizmahmud on 13.3.2018.
 */

public class TaskStatusModel {
    @SerializedName("IsTaskStatu")
    private Boolean IsTaskStatu;

    public Boolean getTaskStatu() {
        return IsTaskStatu;
    }

    public void setTaskStatu(Boolean taskStatu) {
        IsTaskStatu = taskStatu;
    }
}
