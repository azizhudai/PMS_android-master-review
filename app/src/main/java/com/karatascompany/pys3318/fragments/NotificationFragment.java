package com.karatascompany.pys3318.fragments;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karatascompany.pys3318.R;

/**
 * Created by azizmahmud on 3.3.2018.
 */

public class NotificationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.notification,container,false);

        return v;
    }
}
