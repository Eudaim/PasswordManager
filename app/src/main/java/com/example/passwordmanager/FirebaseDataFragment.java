package com.example.passwordmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class FirebaseDataFragment extends Fragment {
    TextView usernameTextView;
    TextView passwordTextview;

    private static FirebaseDataFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        instance = this;
        View v = inflater.inflate(R.layout.firebasedatafragment, container, false);
        usernameTextView = v.findViewById(R.id.username);
        passwordTextview = v.findViewById(R.id.password);
        Bundle args = getArguments();

        String[] extra = args.getStringArray("info");
        usernameTextView.setText(extra[0]);
        passwordTextview.setText(extra[1]);
        //info = v.findViewById(R.id.username);
        return v;
    }

    public static FirebaseDataFragment newInstance(List<String> usernameAndPassword) {
        FirebaseDataFragment fragment = new FirebaseDataFragment();
        Bundle args = new Bundle();
        String[] array = usernameAndPassword.toArray(new String[0]);
        args.putStringArray("info", array);
        fragment.setArguments(args);
        return fragment;
    }

//    public TextView getInfo()
//    {
//        return info;
//    }
}
