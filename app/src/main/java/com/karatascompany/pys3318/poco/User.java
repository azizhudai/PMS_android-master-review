package com.karatascompany.pys3318.poco;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private String  UserMail;
    @Expose
    private String  UserName;
    @Expose
    private String  UserSurname;
    @Expose
    private String  Password;

    public User(String userMail, String userName, String userSurname, String password) {
        UserMail = userMail;
        UserName = userName;
        UserSurname = userSurname;
        Password = password;
    }

    public String getUserMail() {
        return UserMail;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserSurname() {
        return UserSurname;
    }

    public void setUserSurname(String userSurname) {
        UserSurname = userSurname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
