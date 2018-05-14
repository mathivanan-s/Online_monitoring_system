package com.lakshmiindustrialautomation.www.lit.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lakshmiindustrialautomation.www.lit.Database.DatabaseHelper;
import com.lakshmiindustrialautomation.www.lit.LoginActivity;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;

public class ControlPanelActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private final static String REG_TOKEN = "REG_TOKEN";
    Preference ring_tone, is_stoppage_notification_vibrate_enabled,  is_normal_notification_vibrate_enabled;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.controlpanel_preference_layout);

        databaseHelper = new DatabaseHelper(this);
        ring_tone = (Preference) findPreference("alert_tone");
        is_normal_notification_vibrate_enabled = (Preference) findPreference("is_normal_notification_vibrate_enabled");
        is_stoppage_notification_vibrate_enabled = (Preference) findPreference("is_stoppage_notification_vibrate_enabled");
        ring_tone.setOnPreferenceChangeListener(this);
        is_stoppage_notification_vibrate_enabled.setOnPreferenceChangeListener(this);
        is_normal_notification_vibrate_enabled.setOnPreferenceChangeListener(this);

        Boolean stoppage_vibrated_enabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("is_stoppage_notification_vibrate_enabled", false);
        Boolean normal_vibrated_enabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("is_normal_notification_vibrate_enabled", false);
        String notification_tone = PreferenceManager.getDefaultSharedPreferences(this).getString("alert_tone", "");
        databaseHelper.updateUserPreferenceDetails(notification_tone, stoppage_vibrated_enabled, normal_vibrated_enabled);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ProgressDialog progressdialog = new ProgressDialog(ControlPanelActivity.this);
        progressdialog.setMessage("Please wait..");
        progressdialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Intent intent = new Intent(ControlPanelActivity.this, ControlPanelActivity.class);
                        startActivity(intent);
                        progressdialog.dismiss();
                    }
                }, 1500);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ControlPanelActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
