package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<User> usersDB = new ArrayList<>();

    Button createAccountButton;
    Button signInButton;

    private LowBatteryReceiver lowBatteryReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the intent for the low battery broadcast receiver
        lowBatteryReceiver = new LowBatteryReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(lowBatteryReceiver, filter);


        createAccountButton = findViewById(R.id.createAccountButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        createAccountButton.setOnClickListener(onClickListener);
        signInButton.setOnClickListener(onClickListener);
        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                List<String> websites = (List<String>) document.get("websites");
                                User thisUser = new User(document.getData().getOrDefault("users",null),
                                        document.getData().getOrDefault("users",null),
                                        websites);
                                Log.d(TAG, thisUser + "=>" + document.getData());
                                usersDB.add(thisUser);
                            }
                        }
                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    //Unregister the battery receiver to prevent memory problems
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(lowBatteryReceiver);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.signInButton:
                    Intent signInIntent = new Intent(MainActivity.this,SigninActivity.class);
                    MainActivity.this.startActivity(signInIntent);
                    break;
            }
        }

    };
}