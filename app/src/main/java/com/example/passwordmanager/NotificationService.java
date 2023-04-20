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

public class NotificationService extends Service {

    private final static String TAG = "ShowNotification";

    @Override
    public void onCreate() {
        super.onCreate();

        Intent mainIntent = new Intent(this, MainActivity.class);
        NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_stat_access_alarms)
                .setContentTitle("PasswordManager")
                .setContentText("Don't forget to add your passwords!");
        Notification notification = builder.build();


        notificationManager.notify(0, notification);

        Log.i(TAG, "Notification created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}