package com.karatascompany.pys3318.models;

import androidx.annotation.Nullable;

/**
 * Created by azizmahmud on 16.3.2018.
 */

public class StatusModel {
    @Nullable
    private boolean IsSuccess;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
