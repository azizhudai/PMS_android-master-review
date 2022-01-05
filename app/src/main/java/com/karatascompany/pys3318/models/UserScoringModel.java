package com.karatascompany.pys3318.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 5.6.2018.
 */

public class UserScoringModel {

    @Expose
    public int AppointedId;
    @NonNull
    @Expose
    public int UserById;
    @Expose
    public int UserId;
    @Expose
    public double Score;
  //  public int? TaskId { get; set; }
    //public int? ProjectId


    public UserScoringModel(int appointedId, @NonNull int userById, int userId, double score) {
        AppointedId = appointedId;
        UserById = userById;
        UserId = userId;
        Score = score;
    }
}
