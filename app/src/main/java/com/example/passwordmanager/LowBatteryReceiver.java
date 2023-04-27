package com.example.passwordmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Called when the BroadcastReceiver receives an intent that matches the ACTION_BATTERY_LOW intent filter.
 * Displays a warning message to the user that the battery is low and they should save their passwords.
 *
 * @param context The Context in which the receiver is running.
 * @param intent  The Intent being received.
 */
public class LowBatteryReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            Toast.makeText(context, "Battery is low! Save your passwords.", Toast.LENGTH_LONG).show();
        }
    }

}
