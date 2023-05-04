package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addwebsiteactivity);

        // Initialize UI elements
        cancelButton = findViewById(R.id.cancel);
        addWebsiteButton = findViewById(R.id.addwebsite);
        websiteNameEditText = findViewById(R.id.websitename);
        passwordEditText = findViewById(R.id.password);
        usernameEditText = findViewById(R.id.username);

        // Set click listeners for buttons
        addWebsiteButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);

        // Get user object from intent extras
        extra = getIntent().getParcelableExtra("user");

        // Query Firestore for document with user's username
        Query query = usersRef.whereEqualTo("username",extra.getUsername());
        db.collection("users").whereEqualTo("username",extra.getUsername()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Iterate through result documents and get the ID of the matching document
                            for (QueryDocumentSnapshot document : task.getResult()) {
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
                // if add website button is clicked
                case R.id.addwebsite:
                    // add new website information to lists
                    if(websiteNameEditText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),
                                "Enter a valid website",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    for(String s : extra.websites) {
                        websites.add(s);
                    }
                    websites.add(websiteNameEditText.getText().toString());
                    websites.add(usernameEditText.getText().toString());
                    websites.add(passwordEditText.getText().toString());
                    extra.websites.add(websiteNameEditText.getText().toString());
                    extra.websites.add(usernameEditText.getText().toString());
                    extra.websites.add(passwordEditText.getText().toString());
                    // update user document in the database with new website information
                    db.collection("users").document(documentId)
                            .update("websites", websites);
                    // navigate back to AccountInfoActivity
                    Intent startFragmentIntent = new Intent(addWebsiteActivity.this,AccountInfoActivity.class);
                    startFragmentIntent.putExtra("user", extra );
                    addWebsiteActivity.this.startActivity(startFragmentIntent);
                    break;
                // if cancel button is clicked
                case R.id.cancel:
                    // navigate back to AccountInfoActivity without updating database
                    startFragmentIntent = new Intent(addWebsiteActivity.this,AccountInfoActivity.class);
                    startFragmentIntent.putExtra("user", extra );
                    addWebsiteActivity.this.startActivity(startFragmentIntent);
                    break;
            }
        }
    };
}

