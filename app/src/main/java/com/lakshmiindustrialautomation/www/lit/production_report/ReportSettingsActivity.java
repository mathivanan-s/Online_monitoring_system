package com.lakshmiindustrialautomation.www.lit.production_report;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;

/**
 * Created by MATHI on 7/1/2017.
 */

public class ReportSettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.report_settings_preference_layout);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReportSettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
