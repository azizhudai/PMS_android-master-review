package com.karatascompany.pys3318.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 19.3.2018.
 */

public class UserFriendModel {

    @Expose
    private int UserId;
    @Expose
    private String UserMail;
    @Expose
    @Nullable
    private Boolean IsAppoint;

    public int getUserId() {
        return UserId;
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
    public Boolean getAppoint() {
        return IsAppoint;
    }

    public void setAppoint(@Nullable Boolean appoint) {
        IsAppoint = appoint;
    }
}
