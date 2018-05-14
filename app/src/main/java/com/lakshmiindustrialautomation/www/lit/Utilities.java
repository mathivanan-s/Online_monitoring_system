package com.lakshmiindustrialautomation.www.lit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Switch;

import com.lakshmiindustrialautomation.www.lit.ui.IpSettingsActivity;
import com.lakshmiindustrialautomation.www.lit.ui.NormalAlertsListActivity;
import com.lakshmiindustrialautomation.www.lit.ui.StoppageAlertsListActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Android on 5/2/2017.
 */

public class Utilities {

    public static String getCurrentIp(Boolean is_cleaned, Activity activity){
        String ip_address = "no_ip";
        String network_mode = getNetworkMode(activity);
        if (network_mode.equals("online")){
            ip_address = getIpAddress(activity, "online");
        } else if (network_mode.equals("offline")){
            ip_address = getIpAddress(activity, "offline");
        }
        if(is_cleaned){
            return ip_address;
        }
        return "http://"+ip_address;
    }

    public static String getFirebaseTopicName (Activity activity){
        String static_ip = Utilities.getCurrentIp(true, activity);
        static_ip = static_ip.replaceAll("\\.", "").replaceAll("\\:", "");
        return static_ip;
    }

    public static void storeNetworkMode(String mode, Activity activity){
        String NETWORK_MODE_PREFS_NAME = "network_details";
        SharedPreferences settings= activity.getSharedPreferences(NETWORK_MODE_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("network_mode", mode);
        editor.apply();
    }

    public static String getNetworkMode(Activity activity) {
        String NETWORK_MODE_PREFS_NAME = "network_details";
        SharedPreferences settings = activity.getSharedPreferences(NETWORK_MODE_PREFS_NAME, MODE_PRIVATE);
        return settings.getString("network_mode", "offline");
    }

    public static String getIpAddress(Activity activity, String mode){
        String ip_address = null;
        if(mode.equals("online")) {
            ip_address = PreferenceManager.getDefaultSharedPreferences(activity).getString("static_ip", "no_ip");
        }
        else if (mode.equals("offline")){
            ip_address = PreferenceManager.getDefaultSharedPreferences(activity).getString("local_ip", "no_ip");
        }

        if(ip_address.equals("")){
            ip_address = "no_ip";
        }
        return ip_address;
    }

    public static Boolean isLocalIpAvailable(Activity activity){
        String ip_address;
        ip_address = PreferenceManager.getDefaultSharedPreferences(activity).getString("local_ip", "no_ip");
        if(ip_address.equals("no_ip") || ip_address.equals("")){
            return false;
        }
        return true;
    }

    public static Boolean isStaticIpAvailable(Activity activity){
        String ip_address;
        ip_address = PreferenceManager.getDefaultSharedPreferences(activity).getString("static_ip", "no_ip");
        if(ip_address.equals("no_ip") || ip_address.equals("")){
            return false;
        }
        return true;
    }

    public static void storeUsername(Activity activity, String username){
        String USER_DETAILS_PREFS_NAME = "user_details";
        SharedPreferences settings= activity.getSharedPreferences(USER_DETAILS_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_name", username);
        editor.apply();
    }
    public static String getUsername(Activity activity){
        String USER_DETAILS_PREFS_NAME = "user_details";
        SharedPreferences settings = activity.getSharedPreferences(USER_DETAILS_PREFS_NAME, MODE_PRIVATE);
        return settings.getString("user_name", "Guest");
    }
    public static void resetUsername(Activity activity){
        String USER_DETAILS_PREFS_NAME = "user_details";
        SharedPreferences settings= activity.getSharedPreferences(USER_DETAILS_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_name", "Guest");
        editor.apply();
    }

    public static String getFirebaseToken(Activity activity){
        String USER_DETAILS_PREFS_NAME = "firebase_details";
        SharedPreferences settings = activity.getSharedPreferences(USER_DETAILS_PREFS_NAME, MODE_PRIVATE);
        return settings.getString("firebase_token", "no_token");
    }

    public static boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    //single sheetview grid background color

    public static void storeSelectedColor(Activity activity, String selected_color,Integer id){
        String SELECTED_COLOR = "selected_color";
        SharedPreferences settings= activity.getSharedPreferences(SELECTED_COLOR, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("selected_color_"+id, selected_color);
        editor.apply();
    }
    public static String getSelectedColor(Activity activity,Integer id){
        String SELECTED_COLOR = "selected_color";
        SharedPreferences settings = activity.getSharedPreferences(SELECTED_COLOR, MODE_PRIVATE);
        return settings.getString("selected_color_"+id, String.valueOf(Color.parseColor("#FFFFFF")));
    }


}

