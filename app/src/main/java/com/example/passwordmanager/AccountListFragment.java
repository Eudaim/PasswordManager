package com.example.passwordmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends Fragment {
    List<String> companies; //a list of companies that the user has an account with
    ArrayAdapter<String> adapter;
    private static AccountListFragment instance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        instance = this;
        View v = inflater.inflate(R.layout.accountlistfragment, container, false);
        ListView bookmarks = (ListView) v.findViewById(R.id.accounts);

        companies = new ArrayList<>();
        companies.add("Hulu");
        companies.add("Google");
        companies.add("Crunchyroll");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, companies);
        bookmarks.setAdapter(adapter);

        //Set adapter for the ListView
        bookmarks.setAdapter(adapter);
        bookmarks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if statements telling code to display information based on the positioned item clicked
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
