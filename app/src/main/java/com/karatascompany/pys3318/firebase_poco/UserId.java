package com.karatascompany.pys3318.firebase_poco;

import androidx.annotation.NonNull;

/**
 * Created by azizmahmud on 2.4.2018.
 */

public class UserId{

    public String userId;

    public <T extends UserId> T withId(@NonNull final String id) {
        this.userId = id;
        return (T) this;
    }
}
