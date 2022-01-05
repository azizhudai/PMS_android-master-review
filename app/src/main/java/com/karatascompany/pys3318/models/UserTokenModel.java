package com.karatascompany.pys3318.models;

import com.google.gson.annotations.Expose;

/**
 * Created by azizmahmud on 3.4.2018.
 */

public class UserTokenModel {
    @Expose
    private String UserId;
    @Expose
    private String TokenId;

    public UserTokenModel(String userId, String tokenId) {
        UserId = userId;
        TokenId = tokenId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = UserId;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        this.TokenId = TokenId;
    }
}
