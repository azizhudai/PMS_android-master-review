package com.karatascompany.pys3318.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.session.Session;

/**
 * Created by azizmahmud on 1.4.2018.
 */

public class Tab1SettingUserProfile extends Fragment{

    Session session;
    TextView textViewUserMail;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_setting_user_profile,container,false);

        session = new Session(getActivity());
        textViewUserMail = view.findViewById(R.id.textViewUserMail);
        textViewUserMail.setText(session.getUserMail());

        return view;
    }
}
