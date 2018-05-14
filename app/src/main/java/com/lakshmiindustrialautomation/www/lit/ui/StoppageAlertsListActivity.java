package com.lakshmiindustrialautomation.www.lit.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lakshmiindustrialautomation.www.lit.Adapters.NormalAlert;
import com.lakshmiindustrialautomation.www.lit.Adapters.NormalAlertsArrayAdapter;
import com.lakshmiindustrialautomation.www.lit.Database.DatabaseHelper;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;

import java.util.ArrayList;

public class StoppageAlertsListActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    public Integer id;
    public String title, body;
    public static StoppageAlertsListActivity instance;
    TextView empty_notification_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoppage_alerts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        empty_notification_text = (TextView) findViewById(R.id.empty_notification_text);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_3, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        instance = this;

        ArrayList<NormalAlert> arrayOfItems = new ArrayList<NormalAlert>();

        // Create the adapter to convert the array to views
        final NormalAlertsArrayAdapter adapter = new NormalAlertsArrayAdapter(this, arrayOfItems);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!adapter.isEmpty()) {
                    final ProgressDialog progressdialog = new ProgressDialog(StoppageAlertsListActivity.this);
                    progressdialog.setMessage("Please wait..");
                    progressdialog.show();
                    adapter.clear();
                    databaseHelper.deleteAllNotifications(databaseHelper.SECOND_TABLE_NAME);
                    empty_notification_text.setVisibility(View.VISIBLE);
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Toast.makeText(StoppageAlertsListActivity.this, "Notifications deleted successfully", Toast.LENGTH_SHORT).show();
                                    progressdialog.dismiss();
                                }
                            }, 1500);
                }
                else {
                    Toast.makeText(StoppageAlertsListActivity.this, "No notifications found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.normal_alerts_list);
        listView.setAdapter(adapter);
        fetchData(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StoppageAlertsListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void fetchData(NormalAlertsArrayAdapter adapter){
        Cursor cursor;
        cursor = databaseHelper.getAllAlerts(DatabaseHelper.SECOND_TABLE_NAME);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                empty_notification_text.setVisibility(View.GONE);
                NormalAlert cartItem = new NormalAlert(title, body, id);
                cartItem.setTitle(cursor.getString(1));
                cartItem.setBody(cursor.getString(2));
                adapter.add(cartItem);
            } while (cursor.moveToNext());
        }

        else {
            Toast.makeText(StoppageAlertsListActivity.this, "No notifications found", Toast.LENGTH_SHORT).show();
        }
    }

}
