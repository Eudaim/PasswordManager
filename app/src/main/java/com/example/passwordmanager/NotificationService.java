package com.example.passwordmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**

 A service that creates and displays a notification reminding the user to add passwords.
 */

public class NotificationService extends Service {

    private final static String TAG = "ShowNotification";

    @Override
    public void onCreate() {
        super.onCreate();

        // Create an intent to open the main activity when the notification is clicked
        Intent mainIntent = new Intent(this, MainActivity.class);
        // Create a notification channel
        NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        // Register the notification channel
        notificationManager.createNotificationChannel(channel);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_stat_access_alarms)
                .setContentTitle("PasswordManager")
                .setContentText("Don't forget to add your passwords!");
        Notification notification = builder.build();

        // Display the notification
        notificationManager.notify(0, notification);

        Log.i(TAG, "Notification created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This service doesn't need to be bound, so return null
        return null;
    }
}