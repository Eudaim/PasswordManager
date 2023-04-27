package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SigninActivity extends AppCompatActivity {
    Button signInButton;
    TextView usernameTextView;
    TextView passwordTextView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<User> usersDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        // Initialize sign in button and set an OnClickListener
        signInButton = (Button) findViewById(R.id.signInButtonSignIn);
        signInButton.setOnClickListener(onClickListener);

        // Initialize username and password TextViews
        usernameTextView = findViewById(R.id.usernameSignIn);
        passwordTextView = findViewById(R.id.passwordSignIn);

        // Retrieve user data from Firestore database
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    // Iterate over each document in the collection
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        // Print document ID and data to log
                        Log.d(TAG, document.getId() + "=>" + document.getData());

                        // Retrieve list of websites associated with this user
                        List<String> websites = (List<String>) document.get("websites");

                        // Create a new User object using the data retrieved from Firestore
                        User thisUser = new User(document.getData().getOrDefault("username",null).toString(),
                                document.getData().getOrDefault("password",null).toString(),
                                websites);

                        // Print thisUser object and associated data to log
                        Log.d(TAG, thisUser + "=>" + document.getData());

                        // Add thisUser object to usersDB ArrayList
                        usersDB.add(thisUser);
                    }
                }
                else {
                    // Print error message to log if task is not successful
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.signInButtonSignIn:
                    // Get the username and password entered by the user
                    String username = usernameTextView.getText().toString();
                    String password = (String) passwordTextView.getText().toString();

                    // Loop through all the users in the database and check if the entered credentials match with any user
                    for(User user : usersDB) {
                        if(user.getPassword().equals(password) && user.getUsername().equals(username)) {
                            // If a match is found, start a new activity and pass the user object as an extra
                            Intent startFragmentIntent = new Intent(SigninActivity.this,AccountInfoActivity.class);
                            startFragmentIntent.putExtra("user", user );
                            SigninActivity.this.startActivity(startFragmentIntent);
                            break;
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Incorrect Username or Password. Please Try Again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            }
        }
    };

}
