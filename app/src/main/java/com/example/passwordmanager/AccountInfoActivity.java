package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AccountInfoActivity extends AppCompatActivity {

    // UI elements
    Button addWebsiteButton;

    // User object to hold account information
    User extra;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfoactivity);

        // Initialize UI elements
        addWebsiteButton = findViewById(R.id.addwebsite);
        addWebsiteButton.setOnClickListener(onClickListener);

        // Retrieve User object passed from MainActivity
        extra = getIntent().getParcelableExtra("user");

        // Initialize and display AccountListFragment
        AccountListFragment accountListFragment = AccountListFragment.newInstance(extra);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.accountlistfragment, accountListFragment, "AccountList");
        trans.commit();
    }

    // OnClickListener for Add Website button
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addwebsite:
                    // Launch AddWebsiteActivity and pass User object
                    Intent addWebsiteIntent = new Intent(AccountInfoActivity.this,addWebsiteActivity.class);
                    addWebsiteIntent.putExtra("user", extra );
                    AccountInfoActivity.this.startActivity(addWebsiteIntent);
                    break;
            }

        }
    };
}
