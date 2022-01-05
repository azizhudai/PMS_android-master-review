package com.karatascompany.pys3318.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by azizmahmud on 3.3.2018.
 */

public class ProjectModel {
    @SerializedName("ProjectId")
    private int ProjectId;
    @SerializedName("ProjectName")
    private String ProjectName;

    public ProjectModel(int projectId, String projectName) {
        ProjectId = projectId;
        ProjectName = projectName;
    }

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

}
