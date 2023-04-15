package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    TextView userNameTextView;
    TextView passwordTextView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<User> usersDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signInButton = (Button) findViewById(R.id.signInButtonSignIn);
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.signInButton:
                    String username = (String) userNameTextView.getText();
                    String password = (String) passwordTextView.getText();
                    for(User u : usersDB) {
                        if(u.getPassword() == username && u.getUsername() == password) {
                            Intent signInIntent = new Intent(SigninActivity.this,AccountInfoActivity.class);
                            SigninActivity.this.startActivity(signInIntent);
                        }
                    }
                    break;
            }
        }

    };
}
