package com.karatascompany.pys3318.poco;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 15.3.2018.
 */

public class Project {
    @Expose
    @Nullable
    private int ProjectId;
    @Expose
    private String ProjectTitle;
    @Nullable
    @Expose
    private String ProjectDetail;
    @Nullable
    @Expose
    private String ProjectStartDate;
    @Nullable
    @Expose
    private String ProjectEndDate;
    @Expose
    private int ManagerUserId;

    public Project(int projectId, String projectTitle, String projectDetail, String projectStartDate, String projectEndDate, int managerUserId) {
        ProjectId = projectId;
        ProjectTitle = projectTitle;
        ProjectDetail = projectDetail;
        ProjectStartDate = projectStartDate;
        ProjectEndDate = projectEndDate;
        ManagerUserId = managerUserId;
    }

    public String getProjectTitle() {
        return ProjectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        ProjectTitle = projectTitle;
    }

    public String getProjectDetail() {
        return ProjectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        ProjectDetail = projectDetail;
    }

    public String getProjectStartDate() {
        return ProjectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        ProjectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return ProjectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        ProjectEndDate = projectEndDate;
    }

    public int getManagerUserId() {
        return ManagerUserId;
    }

    public void setManagerUserId(int managerUserId) {
        ManagerUserId = managerUserId;
    }
}
