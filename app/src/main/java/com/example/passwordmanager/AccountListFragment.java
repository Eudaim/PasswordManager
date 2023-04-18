package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends Fragment {
    User u;
    FirebaseDataFragment fdf;
    List<String> companies; //a list of companies that the user has an account with
    ArrayAdapter<String> adapter;
    private static AccountListFragment instance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        instance = this;
        View v = inflater.inflate(R.layout.accountlistfragment, container, false);
        u = (User) getArguments().getParcelable("user");
        ListView bookmarks = (ListView) v.findViewById(R.id.accounts);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        //make fragment object to use from this class
        fdf = (FirebaseDataFragment) fragmentManager.findFragmentById(R.id.firebasedatafragment);

        companies = new ArrayList<>();
        //go into database and add the company names to companies list

        List<String> listt = u.getWebsiteList();
        for(int i = 0; i < listt.size(); i++)
        {
            companies.add(listt.get(i));
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, companies);
        bookmarks.setAdapter(adapter);

        //Set adapter for the ListView
        bookmarks.setAdapter(adapter);
        bookmarks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);     //s is the company name
                //need to find username and password from firebase based on s
                List<String> accountinfo = u.getUserNameAndPassword(s);
                fdf.getInfo().setText("Username: " + accountinfo.get(0) + "\nPassword: " + accountinfo.get(1));
            }
        });
        return v;
    }

    public static AccountListFragment getInstance() {return instance;}

    public void addCompany(String s)
    {
        adapter.add(s);
        adapter.notifyDataSetChanged();
    }
}
