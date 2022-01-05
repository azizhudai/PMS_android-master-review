package com.karatascompany.pys3318.models.Graphs;

/**
 * Created by azizmahmud on 27.3.2018.
 */

public class ManagerProjectDaysModel {

    private String ProjectName;
    private float ProjectDaysCount;

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public float getProjectDaysCount() {
        return ProjectDaysCount;
    }

    public void setProjectDaysCount(float projectDaysCount) {
        ProjectDaysCount = projectDaysCount;
    }
}
