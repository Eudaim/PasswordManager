package com.example.passwordmanager;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int NOTIFICATION_REMINDER_NIGHT = 1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<User> usersDB = new ArrayList<>();

    Button createAccountButton;
    Button signInButton;

    private LowBatteryReceiver lowBatteryReceiver;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;

    User thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the intent for the low battery broadcast receiver
        lowBatteryReceiver = new LowBatteryReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(lowBatteryReceiver, filter);

        //create intent for notifications
        Intent notificationIntent = new Intent(getApplicationContext(), NotificationService.class);
        PendingIntent contentIntent = PendingIntent.getService(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_IMMUTABLE);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + 360, 360, contentIntent);


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
                // When the "Sign In" button is clicked
                case R.id.signInButton:
                    // Create an intent to navigate to the sign in activity
                    Intent signInIntent = new Intent(MainActivity.this,SigninActivity.class);
                    // Start the sign in activity
                    MainActivity.this.startActivity(signInIntent);
                    break;
                // When the "Create Account" button is clicked
                case R.id.createAccountButton:
                    // Get the entered username, password, and confirmed password
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String confirmPassword = confirmPasswordEditText.getText().toString();

                    if(username.equals("") || password.equals("") ){
                        Toast.makeText(getApplicationContext(),
                                "Enter a valid account",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    // If the passwords don't match, show an error message and stop execution
                    if(!confirmPassword.equals(password)) {
                        Toast.makeText(getApplicationContext(),
                                "Passwords Don't match!",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    // Check if the entered username already exists in the database, and show an error message if so
                    for(User u: usersDB) {
                        if(u.getUsername().equalsIgnoreCase(username)) {
                            Toast.makeText(getApplicationContext(),
                                    "Username already taken!",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    // Create a new User object with the entered username, password, and an empty list of websites
                    List<String> websites = new ArrayList<String>();
                    User user = new User(username, password, websites);
                    // Add the new user to the database
                    db.collection("users").document().set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    // Show a success message
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
                    // Create an intent to navigate to the account info activity and include the new user as an extra
                    signInIntent = new Intent(MainActivity.this,AccountInfoActivity.class);
                    signInIntent.putExtra("user", user );
                    // Start the account info activity
                    MainActivity.this.startActivity(signInIntent);
                    break;
            }
        }

    };

}