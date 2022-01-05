package com.karatascompany.pys3318.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by azizmahmud on 3.3.2018.
 */

public class UserModel {

    //@SerializedName("UserId")
    @Expose
    private int UserId;

   /*  @SerializedName("userEmail")
    private String userEmail;
     @SerializedName("userPassword")
    private String userPassword;*/

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
