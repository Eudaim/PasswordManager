package com.example.passwordmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AccountInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AccountListFragment fragment1 = new AccountListFragment();
        FirebaseDataFragment fragment2 = new FirebaseDataFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.accountlistfragment, fragment1);
        fragmentTransaction.add(R.id.firebasedatafragment, fragment2);

        setContentView(R.layout.accountinfoactivity);
    }
}
