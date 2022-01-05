package com.karatascompany.pys3318.models;

/**
 * Created by azizmahmud on 3.4.2018.
 */

public class GetUserTokenModel {

    private String UserId;
    private String TokenId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }
}
