package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.EntityIterator;
import android.content.Intent;
import android.mtp.MtpObjectInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<User> usersDB = new ArrayList<>();

    Button createAccountButton;
    Button signInButton;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;

    User thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAccountButton = findViewById(R.id.createAccountButton);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
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
                                User user = new User(document.getData().get("username").toString(),
                                        document.getData().get("password").toString(),
                                        websites);
                                Log.d(TAG, thisUser + "=>" + document.getData());
                                usersDB.add(user);
                            }
                        }
                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.signInButton:
                    Intent signInIntent = new Intent(MainActivity.this,SigninActivity.class);
                    MainActivity.this.startActivity(signInIntent);
                    break;
                case R.id.createAccountButton:
                       String username = usernameEditText.getText().toString();
                       String password = passwordEditText.getText().toString();
                       String confirmPassword = confirmPasswordEditText.getText().toString();
                       if(!confirmPassword.equals(password)) {
                           //code error toast
                           break;
                       }
                    List<String> websites = new ArrayList<String>();
                    User user = new User(username, password, websites);
                    db.collection("users").document().set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    Toast.makeText(getApplicationContext(),
                                            "User Created. Welcome!",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                    break;
            }
        }

    };
}