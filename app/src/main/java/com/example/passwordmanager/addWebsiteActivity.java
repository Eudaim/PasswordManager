package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addWebsiteActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button addWebsiteButton;
    Button cancelButton;
    EditText websiteNameEditText;
    EditText passwordEditText;
    EditText usernameEditText;
    User extra;
    String documentId;
    List<String> websites = new ArrayList<>();
    CollectionReference usersRef = db.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addwebsiteactivity);
        cancelButton = findViewById(R.id.cancel);
        addWebsiteButton = findViewById(R.id.addwebsite);
        websiteNameEditText = findViewById(R.id.websitename);
        passwordEditText = findViewById(R.id.password);
        usernameEditText = findViewById(R.id.username);
        addWebsiteButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
        extra = getIntent().getParcelableExtra("user");
        Query query = usersRef.whereEqualTo("username",extra.getUsername());
        db.collection("users").whereEqualTo("username",extra.getUsername()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                documentId = document.getId();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addwebsite:
                    for(String s : extra.websites) {
                        websites.add(s);
                    }
                    websites.add(websiteNameEditText.getText().toString());
                    websites.add(usernameEditText.getText().toString());
                    websites.add(passwordEditText.getText().toString());
                    extra.websites.add(websiteNameEditText.getText().toString());
                    extra.websites.add(usernameEditText.getText().toString());
                    extra.websites.add(passwordEditText.getText().toString());
                    db.collection("users").document(documentId)
                            .update("websites", websites);
                    Intent startFragmentIntent = new Intent(addWebsiteActivity.this,AccountInfoActivity.class);
                    startFragmentIntent.putExtra("user", extra );
                    addWebsiteActivity.this.startActivity(startFragmentIntent);
                    break;
                case R.id.cancel:
                    startFragmentIntent = new Intent(addWebsiteActivity.this,AccountInfoActivity.class);
                    startFragmentIntent.putExtra("user", extra );
                    addWebsiteActivity.this.startActivity(startFragmentIntent);
                    break;
            }

        }
    };
}
