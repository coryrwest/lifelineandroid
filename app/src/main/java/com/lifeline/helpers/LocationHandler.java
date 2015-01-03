package com.lifeline.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

public class LocationHandler {
    public String homeNetwork;
    public String workNetwork;
    public String lastConnectedNetwork;
    public DateTime lastConnectedTime;
    public int lastConnectionTimeSpan;

    SharedPreferences sharedPref;

    public LocationHandler(Context context) {
        // Get preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        // Get setting for network name from preferences
        homeNetwork = sharedPref.getString("pref_home_network", "");
        workNetwork = sharedPref.getString("pref_work_network", "");
        // Get last network. If it wasn't home network, do nothing.
        lastConnectedNetwork = sharedPref.getString("pref_last_network", "");

        // Get last connected time
        String lastConnectionTime = sharedPref.getString("pref_last_connection_time", "");
        if (lastConnectionTime.equals("")) {
            lastConnectedTime = new DateTime();
        } else {
            lastConnectedTime = new DateTime(lastConnectionTime);
        }

        // Get time span
        DateTime today = new DateTime();
        lastConnectionTimeSpan = Minutes.minutesBetween(lastConnectedTime, today).getMinutes();
    }

    public void SetLastNetworkNameAndTime(String networkName) {
        SharedPreferences.Editor prefEditor = sharedPref.edit();

        // Set last connected network.
        prefEditor.putString("pref_last_network", networkName);

        // Set last connection time
        prefEditor.putString("pref_last_connection_time", new DateTime().toString());
        prefEditor.apply();
    }

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
        }
        return ssid;
    }
}
