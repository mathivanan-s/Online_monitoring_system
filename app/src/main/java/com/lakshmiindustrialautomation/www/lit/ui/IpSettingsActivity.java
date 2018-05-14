package com.lakshmiindustrialautomation.www.lit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.lakshmiindustrialautomation.www.lit.LoginActivity;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;

public class IpSettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
    public static IpSettingsActivity instance = null;
    Preference static_ip, local_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ip_controlpanel_layout);

        instance = this;
        static_ip= (Preference) findPreference("static_ip");
        local_ip = (Preference) findPreference("local_ip");
        setSummary();
        static_ip.setOnPreferenceChangeListener(this);
        local_ip.setOnPreferenceChangeListener(this);
    }

    private void setSummary() {
        if(Utilities.isStaticIpAvailable(IpSettingsActivity.this)
                && !Utilities.getIpAddress(IpSettingsActivity.this, "online").equals("no_ip")) {
            static_ip.setSummary(Utilities.getIpAddress(IpSettingsActivity.this, "online"));
        } else {
            static_ip.setSummary("Example: 117.212.218.255:90");
        }
        if (Utilities.isLocalIpAvailable(IpSettingsActivity.this)
                && !Utilities.getIpAddress(IpSettingsActivity.this, "offline").equals("no_ip")) {
            local_ip.setSummary(Utilities.getIpAddress(IpSettingsActivity.this, "offline"));
        } else {
            local_ip.setSummary("Example: 192.168.1.233");
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Intent intent = new Intent(IpSettingsActivity.this, IpSettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(IpSettingsActivity.this, LoginActivity.class);
        intent.putExtra("silent", "true");
        startActivity(intent);
    }
}
