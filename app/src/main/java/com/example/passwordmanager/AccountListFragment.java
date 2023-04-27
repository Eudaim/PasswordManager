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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends Fragment {

    private User u; // stores the current user
    FirebaseDataFragment fdf; // fragment to display account info
    List<String> companies; // list of websites
    ArrayAdapter<String> adapter; // adapter for the ListView
    private static AccountListFragment instance;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        instance = this;
        View v = inflater.inflate(R.layout.accountlistfragment, container, false);
        ListView bookmarks = (ListView) v.findViewById(R.id.accounts);

        // get the current user and add their websites to the list
        companies = new ArrayList<>();
        Bundle args = getArguments();
        u = args.getParcelable("user");
        List<String> listt = u.getWebsiteList();
        for(int i = 0; i < listt.size(); i++) {
            companies.add(listt.get(i));
        }

        // set up the adapter and ListView
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, companies);
        bookmarks.setAdapter(adapter);

        // when an item is clicked, display its account info in a new fragment
        bookmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                List<String> accountinfo = u.getUserNameAndPassword(s);
                FirebaseDataFragment firebaseDataFragment = FirebaseDataFragment.newInstance(accountinfo);
                FragmentTransaction trans = getParentFragmentManager().beginTransaction();
                trans.replace(R.id.firebasedatafragment, firebaseDataFragment, "AccountList");
                trans.commit();
            }
        });

        return v;
    }

    // create a new instance of the fragment with the given user
    public static AccountListFragment newInstance(User user) {
        AccountListFragment fragment = new AccountListFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    // adds a new website to the list and updates the adapter
    public void addCompany(String s) {
        adapter.add(s);
        adapter.notifyDataSetChanged();
    }
}
