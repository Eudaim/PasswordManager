package com.example.passwordmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FirebaseDataFragment extends Fragment {
    TextView info;
    private static FirebaseDataFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        instance = this;
        View v = inflater.inflate(R.layout.firebasedatafragment, container, false);
        info = v.findViewById(R.id.info);
        return v;
    }

    public static FirebaseDataFragment getInstance() {return instance;}

    public TextView getInfo()
    {
        return info;
    }
}
