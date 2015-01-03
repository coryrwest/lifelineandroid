package com.lifeline.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.lifeline.helpers.LifeLine;
import com.lifeline.helpers.LocationHandler;
import com.lifeline.helpers.NotificationHandler;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ConnectivityChangeReceiver extends WakefulBroadcastReceiver {
    private LifeLine lifeline = new LifeLine();

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d("ConnectivityChangeReceiver", "Connectivity Change");

        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo =
                    intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (
                    (networkInfo.getState().name().equals("CONNECTED") && networkInfo.isConnected() && networkInfo.isAvailable()) ||
                    (!networkInfo.isConnected() && networkInfo.isAvailable() && !networkInfo.getState().name().equals("CONNECTING"))
                ) {
                // Wifi state changed
                Log.d("ConnectivityChangeReceiver", "Wifi state change: " + String.valueOf(networkInfo));

                final ConnectivityManager connMgr = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                final android.net.NetworkInfo wifi = connMgr
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                boolean isConnectedAndHomeNetwork = true;

                LocationHandler location = new LocationHandler(context);

                if (wifi.isConnected()) {
                    String networkName = LocationHandler.getCurrentSsid(context).replace("\"", "");

                    // Compare with current network
                    if (!networkName.contains(location.homeNetwork) && location.lastConnectionTimeSpan > 2) {
                        isConnectedAndHomeNetwork = false;
                    }

                    // Set the last network connected
                    location.SetLastNetworkNameAndTime(networkName);

                    Log.d("ConnectivityChangeReceiver", "Network Available");
                } else {
                    isConnectedAndHomeNetwork = false;
                    Log.d("ConnectivityChangeReceiver", "No Network Available");
                }

                if (!isConnectedAndHomeNetwork && location.lastConnectedNetwork.contains(location.homeNetwork)) {
                    // Show notification
                    NotificationHandler handler = new NotificationHandler();
                    //handler.showNotification(context, "Did you leave the house?");
                }
            }
        }
    }
}
