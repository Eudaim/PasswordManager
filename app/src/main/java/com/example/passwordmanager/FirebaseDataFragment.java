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

    // Singleton instance of the fragment
    private static FirebaseDataFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        // Set the singleton instance to this fragment
        instance = this;

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.firebasedatafragment, container, false);

        // Get references to the UI elements in the layout
        usernameTextView = v.findViewById(R.id.username);
        passwordTextview = v.findViewById(R.id.password);

        // Set the username and password values in the UI elements
        Bundle args = getArguments();
        String[] extra = args.getStringArray("info");
        usernameTextView.setText(extra[0]);
        passwordTextview.setText(extra[1]);

        return v;
    }

    // Create a new instance of the fragment with the given username and password
    public static FirebaseDataFragment newInstance(List<String> usernameAndPassword) {
        FirebaseDataFragment fragment = new FirebaseDataFragment();
        Bundle args = new Bundle();

        // Convert the list of username and password to a String array and put it in the arguments bundle
        String[] array = usernameAndPassword.toArray(new String[0]);
        args.putStringArray("info", array);

        // Set the arguments bundle to the fragment
        fragment.setArguments(args);

        return fragment;
    }
}
