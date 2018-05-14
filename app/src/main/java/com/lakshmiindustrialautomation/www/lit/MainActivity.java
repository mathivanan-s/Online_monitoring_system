package com.lakshmiindustrialautomation.www.lit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lakshmiindustrialautomation.www.lit.Database.DatabaseHelper;
import com.lakshmiindustrialautomation.www.lit.current_status.MacLongStopActivity;
import com.lakshmiindustrialautomation.www.lit.current_status.MacOthersActivity;
import com.lakshmiindustrialautomation.www.lit.current_status.MacRunningActivity;
import com.lakshmiindustrialautomation.www.lit.current_status.MacRunningStatusResponse;
import com.lakshmiindustrialautomation.www.lit.current_status.MacStopActivity;
import com.lakshmiindustrialautomation.www.lit.current_status.MacWarpActivity;
import com.lakshmiindustrialautomation.www.lit.current_status.MacWeftActivity;
import com.lakshmiindustrialautomation.www.lit.month_chart.MachineWiseMonthChartActivity;
import com.lakshmiindustrialautomation.www.lit.month_chart.MonthWiseChartActivity;
import com.lakshmiindustrialautomation.www.lit.production_report.DateWiseProductionReportActivity;
import com.lakshmiindustrialautomation.www.lit.production_report.ProductionReportActivity;
import com.lakshmiindustrialautomation.www.lit.production_report.ReportSettingsActivity;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.SingleSheetBackgroundColorPickerActivity;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.SingleSheetView;
import com.lakshmiindustrialautomation.www.lit.stop_chart.StopChartActivity;
import com.lakshmiindustrialautomation.www.lit.ui.ControlPanelActivity;
import com.lakshmiindustrialautomation.www.lit.ui.NormalAlertsListActivity;
import com.lakshmiindustrialautomation.www.lit.ui.StoppageAlertsListActivity;
import com.lakshmiindustrialautomation.www.lit.year_chart.YearChartActivity;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    private final Handler handler = new Handler();
    boolean doubleBackToExitPressedOnce = false;
    public static MainActivity instance;
    DatabaseHelper databaseHelper;
    NavigationView navigationView;
    String device_id, email, token;
    RelativeLayout status_layout,run_layout,pre_layout,pre_day,this_month,relativeLayout;
    ArrayList input1;
    TextView runmacscroll;
    TextView total_stop,total_running,long_stop,warp,weft,others;
    String t_run,t_stop,t_warp,t_weft,t_others,l_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        networkMode();
        authenticate();
        //animation
        status_layout=(RelativeLayout)findViewById(R.id.runningstatus);
        pre_layout=(RelativeLayout)findViewById(R.id.previous_Shift);
        run_layout=(RelativeLayout)findViewById(R.id.running_Shift);
        pre_day=(RelativeLayout)findViewById(R.id.previous_day);
        this_month=(RelativeLayout)findViewById(R.id.this_month);
        runmacscroll = (TextView) findViewById(R.id.runmacscroll);

        Animation blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        runmacscroll.startAnimation(blink);
        Animation slide_down_rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_rotate);
        run_layout.startAnimation(slide_down_rotate);

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        status_layout.startAnimation(slide_down);
        pre_layout.startAnimation(slide_down);
        pre_day.startAnimation(slide_down);
        this_month.startAnimation(slide_down);

        instance = this;
        databaseHelper = new DatabaseHelper(this);
        FirebaseMessaging.getInstance().subscribeToTopic(Utilities.getFirebaseTopicName(MainActivity.this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        //menu title color settings

        MenuItem tools= menu.findItem(R.id.tit_report);
        MenuItem tools1= menu.findItem(R.id.tit_chart);
        MenuItem tools2= menu.findItem(R.id.tit_monitoring);
        MenuItem tools3= menu.findItem(R.id.tit_settings);

        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);

        SpannableString s1 = new SpannableString(tools1.getTitle());
        s1.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s1.length(), 0);
        tools1.setTitle(s1);

        SpannableString s2 = new SpannableString(tools2.getTitle());
        s2.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s2.length(), 0);
        tools2.setTitle(s2);

        SpannableString s3 = new SpannableString(tools3.getTitle());
        s3.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s3.length(), 0);
        tools3.setTitle(s3);

        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.username);
        name.setText(Utilities.getUsername(MainActivity.this));
        fetchDrawerMenuItems();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            email = getEmailID(MainActivity.this);
        } catch (Exception e) {
            email = "no_email_address";
        }
        token = Utilities.getFirebaseToken(MainActivity.this);
        RegisterDeviceTask task = new RegisterDeviceTask();
        task.execute();

        //show and hide cmpx,kmeter
        TextView run_picks = (TextView) findViewById(R.id.t_rpicks);
        TextView pre_s_picks = (TextView) findViewById(R.id.t_ppicks);
        TextView pre_d_picks = (TextView) findViewById(R.id.t_pdpicks);
        TextView this_m_picks = (TextView) findViewById(R.id.t_mpicks);

        TextView t_rmeter = (TextView) findViewById(R.id.t_rmeter);
        TextView t_pmeter = (TextView) findViewById(R.id.t_pmeter);
        TextView t_dmeter = (TextView) findViewById(R.id.t_dmeter);
        TextView t_mmeter = (TextView) findViewById(R.id.t_mmeter);

        if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("cmpx", false)) {
            //cmpx
            run_picks.setText("K.Revol");
            pre_s_picks.setText("K.Revol");
            this_m_picks.setText("K.Revol");
            pre_d_picks.setText("K.Revol");
            //k.meter
            t_rmeter.setText("K.Kg");
            t_pmeter.setText("K.Kg");
            t_dmeter.setText("K.Kg");
            t_mmeter.setText("K.Kg");
        }
        else
        {
            //k.picks
            run_picks.setText("A.Revol");
            pre_s_picks.setText("A.Revol");
            this_m_picks.setText("A.Revol");
            pre_d_picks.setText("A.Revol");
            //meter
            t_rmeter.setText("Kg");
            t_pmeter.setText("Kg");
            t_dmeter.setText("Kg");
            t_mmeter.setText("Kg");
        }

       //current status
        total_running = (TextView) findViewById(R.id.total_running);
        total_stop = (TextView) findViewById(R.id.total_stop);
        long_stop = (TextView) findViewById(R.id.long_stop);
        warp = (TextView) findViewById(R.id.total_warp);
        weft = (TextView) findViewById(R.id.total_weft);
        others = (TextView) findViewById(R.id.others);
        TextView t_total_running = (TextView) findViewById(R.id.t_total_run);
        TextView t_total_stop = (TextView) findViewById(R.id.t_total_stop);
        TextView t_long_stop = (TextView) findViewById(R.id.t_long_stop);
        TextView t_warp = (TextView) findViewById(R.id.t_total_warp);
        TextView t_weft = (TextView) findViewById(R.id.t_total_weft);
        TextView t_others = (TextView) findViewById(R.id.t_total_others);

        total_running.setOnClickListener(this);
        total_stop.setOnClickListener(this);
        long_stop.setOnClickListener(this);
        warp.setOnClickListener(this);
        weft.setOnClickListener(this);
        others.setOnClickListener(this);
        t_total_running.setOnClickListener(this);
        t_total_stop.setOnClickListener(this);
        t_long_stop.setOnClickListener(this);
        t_warp.setOnClickListener(this);
        t_weft.setOnClickListener(this);
        t_others.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if ((id == R.id.total_running)||(id == R.id.t_total_run)){
            t_run=total_running.getText().toString();
            if(t_run.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "NO MACHINE", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
            else {
                Intent intent = new Intent(this, MacRunningActivity.class);
                startActivity(intent);
            }
        }
        if ((id == R.id.total_stop)||(id == R.id.t_total_stop)) {
            t_stop=total_stop.getText().toString();
            if(t_stop.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "NO MACHINE", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
            else{
                Intent intent = new Intent(this, MacStopActivity.class);
                startActivity(intent);
            }
        }
        if ((id == R.id.long_stop)||(id == R.id.t_long_stop)) {
            l_stop=long_stop.getText().toString();
            if(l_stop.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "NO MACHINE", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
            else {
                Intent intent = new Intent(this, MacLongStopActivity.class);
                startActivity(intent);
            }
        }
        if ((id == R.id.total_warp)||(id==R.id.t_total_warp)) {
            t_warp=warp.getText().toString();
            if(t_warp.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "NO MACHINE", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
            else{
                Intent intent = new Intent(this, MacWarpActivity.class);
                startActivity(intent);
            }
        }
        if ((id == R.id.total_weft)||(id == R.id.t_total_weft)) {
            t_weft=weft.getText().toString();
            if(t_weft.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "NO MACHINE", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();

            }
            else {
                Intent intent = new Intent(this, MacWeftActivity.class);
                startActivity(intent);
            }
        }
        if ((id == R.id.others)||(id == R.id.t_total_others)){
            t_others=others.getText().toString();
            if(t_others.equals("0")){
                Toast toast = Toast.makeText(getApplicationContext(), "NO MACHINE", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
            else {
                Intent intent = new Intent(this, MacOthersActivity.class);
                startActivity(intent);
            }
        }
    }
    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doTheAutoRefresh();
                authenticate();
                runningmac();

            }
        }, 60000);
    }

    private void networkMode(){

        ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cn.getActiveNetworkInfo();
        if(nf != null && nf.isConnected()==true )
        {
            doTheAutoRefresh();
            authenticate();
            runningmac();
        }
        else
        {
            relativeLayout=(RelativeLayout)findViewById(R.id.main_content_activity);
            relativeLayout.setVisibility(View.INVISIBLE);

            Toast toast = Toast.makeText(getApplicationContext(), "Network Not Available \n " +
                    "Please Check Your Internet Connection", Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
            toastView.setBackgroundResource(R.drawable.toast_background);
            toast.show();
        }

    }
    private void runningmac() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, MainActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);
        api.currentstatusreport("running", new Callback<MacRunningStatusResponse>() {
            @Override
            public void success(MacRunningStatusResponse macRunningStatusResponse, Response response) {

                input1 = macRunningStatusResponse.getR_machine_name();
                TextView runscroll = (TextView) findViewById(R.id.sliding_text_marquee);
                String mac = input1.toString().replace("[", "").replace("]", "");
                runscroll.setText(mac);
                runscroll.setSelected(true);

            }

            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                }
            }
        });
    }
    private void authenticate() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, MainActivity.this)).build();

        CoreApis api = adapter.create(CoreApis.class);
        api.liveupdateDetails(new Callback<LiveStatusResponse>() {
            @Override
            public void success(LiveStatusResponse liveStatusResponse, Response response) {
                String state = liveStatusResponse.getSuccess();
                if (state.equals("true")) {

                    //running status
                    TextView total_running = (TextView) findViewById(R.id.total_running);
                    TextView total_stop = (TextView) findViewById(R.id.total_stop);
                    TextView long_stop = (TextView) findViewById(R.id.long_stop);
                    TextView warp = (TextView) findViewById(R.id.total_warp);
                    TextView weft = (TextView) findViewById(R.id.total_weft);
                    TextView others = (TextView) findViewById(R.id.others);


                    total_running.setText(liveStatusResponse.getTotalrunning());
                    total_stop.setText(liveStatusResponse.getTotalstop());
                    long_stop.setText(liveStatusResponse.getLongstop());
                    warp.setText(liveStatusResponse.getWarp());
                    weft.setText(liveStatusResponse.getWeft());
                    others.setText(liveStatusResponse.getOthers());
                    //running shift
                    TextView r_a_eff = (TextView) findViewById(R.id.running_a_eff);
                    TextView r_p_eff = (TextView) findViewById(R.id.running_p_eff);
                    TextView r_rpm = (TextView) findViewById(R.id.running_rpm);
                    TextView r_meter = (TextView) findViewById(R.id.running_meter);
                    TextView r_picks = (TextView) findViewById(R.id.running_picks);

                    r_a_eff.setText(String.valueOf(liveStatusResponse.getCur_shift_eff()));
                    r_p_eff.setText(String.valueOf(liveStatusResponse.getCur_shift_peff()));
                    r_rpm.setText(String.valueOf(liveStatusResponse.getCur_shift_rpm()));

                    //previous shift
                    TextView p_a_eff = (TextView) findViewById(R.id.shift_a_eff);
                    TextView p_p_eff = (TextView) findViewById(R.id.shift_p_eff);
                    TextView p_rpm = (TextView) findViewById(R.id.shift_rpm);
                    TextView p_meter = (TextView) findViewById(R.id.shift_meter);
                    TextView p_picks = (TextView) findViewById(R.id.shift_picks);

                    p_a_eff.setText(String.valueOf(liveStatusResponse.getPrevious_shift_eff()));
                    p_p_eff.setText(String.valueOf(liveStatusResponse.getPrevious_shift_peff()));
                    p_rpm.setText(String.valueOf(liveStatusResponse.getPrevious_shift_rpm()));

                    //previous day
                    TextView day_a_eff = (TextView) findViewById(R.id.day_aeff);
                    TextView day_p_eff = (TextView) findViewById(R.id.day_peff);
                    TextView day_rpm = (TextView) findViewById(R.id.day_rpm);
                    TextView day_meter = (TextView) findViewById(R.id.day_meter);
                    TextView day_picks = (TextView) findViewById(R.id.day_picks);

                    day_a_eff.setText(String.valueOf(liveStatusResponse.getPrevious_day_eff()));
                    day_p_eff.setText(String.valueOf(liveStatusResponse.getPrevious_day_peff()));
                    day_rpm.setText(String.valueOf(liveStatusResponse.getPrevious_day_rpm()));


                    //this month
                    TextView month_a_eff = (TextView) findViewById(R.id.month_aeff);
                    TextView month_p_eff = (TextView) findViewById(R.id.month_peff);
                    TextView month_rpm = (TextView) findViewById(R.id.month_rpm);
                    TextView month_meter = (TextView) findViewById(R.id.month_meter);
                    TextView month_picks = (TextView) findViewById(R.id.month_picks );

                    month_a_eff.setText(String.valueOf(liveStatusResponse.getCur_month_eff()));
                    month_p_eff.setText(String.valueOf(liveStatusResponse.getCur_month_peff()));
                    month_rpm.setText(String.valueOf(liveStatusResponse.getCur_month_rpm()));

                    if(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("cmpx", false))
                    {
                        //cmpx
                        month_picks.setText(String.valueOf(liveStatusResponse.getCur_month_cmpx()));
                        day_picks.setText(String.valueOf(liveStatusResponse.getPrevious_day_cmpx()));
                        p_picks.setText(String.valueOf(liveStatusResponse.getPrevious_shift_cmpx()));
                        r_picks.setText(String.valueOf(liveStatusResponse.getCur_shift_cmpx()));
                        //kmeter
                        r_meter.setText(String.valueOf(liveStatusResponse.getCur_shift_kmtr()));
                        p_meter.setText(String.valueOf(liveStatusResponse.getPrevious_shift_kmtr()));
                        day_meter.setText(String.valueOf(liveStatusResponse.getPrevious_day_kmtr()));
                        month_meter.setText(String.valueOf(liveStatusResponse.getCur_month_kmtr()));

                    }
                    else
                    {
                        //meter
                        r_meter.setText(String.valueOf(liveStatusResponse.getCur_shift_mtr()));
                        p_meter.setText(String.valueOf(liveStatusResponse.getPrevious_shift_mtr()));
                        day_meter.setText(String.valueOf(liveStatusResponse.getPrevious_day_mtr()));
                        month_meter.setText(String.valueOf(liveStatusResponse.getCur_month_mtr()));
                        //kpicks
                        month_picks.setText(String.valueOf(liveStatusResponse.getCur_month_kpx()));
                        day_picks.setText(String.valueOf(liveStatusResponse.getPrevious_day_kpx()));
                        p_picks.setText(String.valueOf(liveStatusResponse.getPrevious_shift_kpx()));
                        r_picks.setText(String.valueOf(liveStatusResponse.getCur_shift_kpx()));
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    relativeLayout=(RelativeLayout)findViewById(R.id.main_content_activity);
                    relativeLayout.setVisibility(View.INVISIBLE);

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Server Request Timeout");
                    alertDialog.setMessage("Please check your server internet connection\nor\ncheck your ip address.... ");

                    alertDialog.setIcon(R.drawable.interneterror);
                    alertDialog.setNegativeButton("Dismiss",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    //finish();
                                }
                            });
                    alertDialog.show();

                }

            }
        });
    }

    private class RegisterDeviceTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... parms) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(Utilities.getCurrentIp(false, MainActivity.this))
                    .build();

            CoreApis api = adapter.create(CoreApis.class);

            api.updateDeviceDetails(device_id, new Callback<DeviceRegisterResponse>() {

                @Override
                public void success(DeviceRegisterResponse deviceRegisterResponse, Response response) {
                }

                @Override
                public void failure(RetrofitError cause) {
                }

            });
            return "success";
        }
    }

    private String getEmailID(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);
        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

        private void fetchDrawerMenuItems() {
        Menu menu = navigationView.getMenu();

        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("single_sheet", true)) {
            menu.findItem(R.id.nav_single_sheet).setVisible(false);
        } else {
            menu.findItem(R.id.nav_single_sheet).setVisible(true);
        }
            //month_chart

        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("month_chart", false)) {
            menu.findItem(R.id.nav_month_chart).setVisible(false);
        } else {
            menu.findItem(R.id.nav_month_chart).setVisible(true);
        }

        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("year_chart", false)) {
            menu.findItem(R.id.nav_year_chart).setVisible(false);
        } else {
            menu.findItem(R.id.nav_year_chart).setVisible(true);
        }
        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("stop_chart", false)) {
            menu.findItem(R.id.nav_stoppage_chart).setVisible(false);
        } else {
            menu.findItem(R.id.nav_stoppage_chart).setVisible(true);
        }
        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("machine_wise_month_chart", false)) {
            menu.findItem(R.id.nav_machine_wise_month_chart).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.nav_machine_wise_month_chart).setVisible(true);
        }
            //report
        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("running_shift_report", false)) {
                menu.findItem(R.id.nav_running_shift_report).setVisible(false);
        }
        else {
                menu.findItem(R.id.nav_running_shift_report).setVisible(true);
        }
        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("date_wise_production_report", false)) {
                menu.findItem(R.id.nav_date_wise_production_report).setVisible(false);
            }
        else {
                menu.findItem(R.id.nav_date_wise_production_report).setVisible(true);
            }
        //chat
        if (!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("efficiency_color_settings", false)) {
                menu.findItem(R.id.nav_single_sheet_color_settings).setVisible(false);
        }
        else {
                menu.findItem(R.id.nav_single_sheet_color_settings).setVisible(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);

        }
        else
            {
            if (doubleBackToExitPressedOnce) {
                ActivityCompat.finishAffinity(MainActivity.this);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (databaseHelper.isStoppageNotificationAvailable()) {
            MenuItem settingsItem = menu.findItem(R.id.action_stoppage_alert);
            settingsItem.setIcon(getResources().getDrawable(R.drawable.stop_unread));
        }
        if (databaseHelper.isNormalNotificationAvailable()){
            MenuItem settingsItem = menu.findItem(R.id.action_normal_alert);
            settingsItem.setIcon(getResources().getDrawable(R.drawable.normal_unread));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_normal_alert) {
            if(databaseHelper.isNormalNotificationAvailable()){
                databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_4, false);
            }
            startActivity(new Intent(MainActivity.this, NormalAlertsListActivity.class));
        } else if (id == R.id.action_stoppage_alert){
            if(databaseHelper.isStoppageNotificationAvailable()){
                databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_3, false);
            }
            startActivity(new Intent(MainActivity.this, StoppageAlertsListActivity.class));
        } else if(id == R.id.settings){
            startActivity(new Intent(MainActivity.this, ControlPanelActivity.class));
        } else if(id == R.id.action_logout){
            final ProgressDialog progressdialog = new ProgressDialog(MainActivity.this);
            progressdialog.setMessage("Logging out..");
            progressdialog.show();
            logout();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "You are logged out successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.putExtra("silent", "true");
                            startActivity(intent);
                            progressdialog.dismiss();
                            finish();
                        }
                    }, 1500);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        Utilities.resetUsername(MainActivity.this);
        databaseHelper.deleteAllNotifications(databaseHelper.SECOND_TABLE_NAME);
        databaseHelper.deleteAllNotifications(databaseHelper.FIRST_TABLE_NAME);
        databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_4, false);
        databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_3, false);
        databaseHelper.updateUserDetails(DatabaseHelper.USER_TABLE_COL_2,false);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String url;
        Intent intent;

        if (id == R.id.nav_single_sheet) {
            intent = new Intent(this, SingleSheetView.class);
            startActivity(intent);
        }
        //month_chart

        else if (id == R.id.shed_and_machine) {
            intent = new Intent(this, ShedAndMachineActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_month_chart) {
                intent = new Intent(this, MonthWiseChartActivity.class);
                startActivity(intent);
        }
        //stop chart
        else if (id == R.id.nav_stoppage_chart) {
            intent = new Intent(this, StopChartActivity.class);
            startActivity(intent);
        }
        //machine wise month chart
        else if (id == R.id.nav_machine_wise_month_chart) {
            intent = new Intent(this, MachineWiseMonthChartActivity.class);
            startActivity(intent);
        }
        //year chart
        else if (id == R.id.nav_year_chart) {
            intent = new Intent(this, YearChartActivity.class);
            startActivity(intent);
        }
        //production report
        else if (id == R.id.nav_running_shift_report) {
            intent = new Intent(this, ProductionReportActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_date_wise_production_report) {
            intent = new Intent(this, DateWiseProductionReportActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_production_report_settings) {
            intent = new Intent(this, ReportSettingsActivity.class);
            startActivity(intent);
        }
        //chat
        else if (id == R.id.nav_single_sheet_color_settings) {
            intent = new Intent(this, SingleSheetBackgroundColorPickerActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
