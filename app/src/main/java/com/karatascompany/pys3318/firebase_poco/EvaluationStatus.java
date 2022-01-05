package com.karatascompany.pys3318.firebase_poco;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 4.4.2018.
 */

public class EvaluationStatus {
    @Expose
    private String UserId;
    @Expose
    private String NominatorUserId;
    @Expose
    private String ProjectId;
    @Expose
    private String TaskId;
    @Expose
    private Boolean ToBeAppoint;

    public EvaluationStatus(String userId, String nominatorUserId, String projectId, String taskId, Boolean toBeAppoint) {
        UserId = userId;
        NominatorUserId = nominatorUserId;
        ProjectId = projectId;
        TaskId = taskId;
        ToBeAppoint = toBeAppoint;
    }
    public EvaluationStatus(){

    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNominatorUserId() {
        return NominatorUserId;
    }

    public void setNominatorUserId(String nominatorUserId) {
        NominatorUserId = nominatorUserId;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public Boolean getToBeAppoint() {
        return ToBeAppoint;
    }

    public void setToBeAppoint(Boolean toBeAppoint) {
        ToBeAppoint = toBeAppoint;
    }
}
