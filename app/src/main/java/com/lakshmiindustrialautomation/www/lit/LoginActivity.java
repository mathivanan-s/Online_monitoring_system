package com.lakshmiindustrialautomation.www.lit;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.lakshmiindustrialautomation.www.lit.Database.DatabaseHelper;
import com.lakshmiindustrialautomation.www.lit.ui.IpSettingsActivity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    EditText username;
    EditText password;
    ImageButton imagebutton;
    public static LoginActivity instance = null;
    DatabaseHelper databaseHelper;
    boolean doubleBackToExitPressedOnce = false;
    RadioButton online_button, offline_button;
    String is_silent = "false";


    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.VIBRATE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.GET_ACCOUNTS,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //permission
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);


        if(ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[5]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[6]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[7]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[8]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[5])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[6])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[7])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,permissionsRequired[8])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }


        if(getIntent().getStringExtra("silent") == null){
            is_silent = "false";
        } else {
            is_silent = getIntent().getStringExtra("silent");
        }

        databaseHelper = new DatabaseHelper(this);

        instance = this;
        username = (EditText) findViewById(R.id.user_id_textbox);
        password = (EditText) findViewById(R.id.password_textbox);
        imagebutton = (ImageButton) findViewById(R.id.ip_settings);
        Button login_button = (Button) findViewById(R.id.login_button);
        imagebutton.setOnClickListener(this);
        login_button.setOnClickListener(this);

        online_button = (RadioButton) findViewById(R.id.online_radio_button);
        offline_button = (RadioButton) findViewById(R.id.offline_radio_button);
        online_button.setOnCheckedChangeListener(this);
        offline_button.setOnCheckedChangeListener(this);

        setInitialRadioButtonState();
    }

    private void setInitialRadioButtonState() {
            String network_mode = Utilities.getNetworkMode(LoginActivity.this);
            if (network_mode.equals("online")) {
                online_button.setChecked(true);
            } else if (network_mode.equals("offline")) {
                offline_button.setChecked(true);
            } else {
                offline_button.setChecked(true);
            }
    }

    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                ActivityCompat.finishAffinity(LoginActivity.this);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[5])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[6])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[7])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[8])){
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_button){
            if( Utilities.getCurrentIp(true, LoginActivity.this) != "no_ip"){
                if (Utilities.isStaticIpAvailable(LoginActivity.this) && !Utilities.isInternetAvailable(LoginActivity.this)){
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().length() == 0 && password.getText().toString().length() == 0) {
                    Toast.makeText(this, "Please Fill username and password", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().length()==0){
                    Toast.makeText(this, "Please Fill username", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length()==0){
                    Toast.makeText(this, "Please Fill password", Toast.LENGTH_SHORT).show();
                } else {
                    authenticate(username.getText().toString(), password.getText().toString());
                }
            } else {
                Toast.makeText(LoginActivity.this, "No IP address found", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ip_settings){
            startActivity(new Intent(LoginActivity.this, IpSettingsActivity.class));
        }
    }

    private void authenticate(String t_username, final String t_password){
        final ProgressDialog progressdialog = new ProgressDialog(LoginActivity.this);
        progressdialog.setMessage("Authenticating...");
        progressdialog.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, LoginActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);

        api.getUserDetails(t_username, t_password, new Callback<AuthenticationResponse>() {
            @Override
            public void success(AuthenticationResponse authenticationResponse, Response response) {
                String state = authenticationResponse.getSuccess();
                if (state.equals("true")){
                    databaseHelper.updateUserDetails(DatabaseHelper.USER_TABLE_COL_2,true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Utilities.storeUsername(LoginActivity.this, authenticationResponse.getUser());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Wrong username or Password", Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    Toast.makeText(LoginActivity.this, "Server Request Timeout", Toast.LENGTH_SHORT).show();
                }

                progressdialog.dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Integer id = buttonView.getId();
        if(id == R.id.online_radio_button && isChecked && Utilities.isStaticIpAvailable(LoginActivity.this)){
            offline_button.setChecked(false);
            Utilities.storeNetworkMode("online", LoginActivity.this);
        } else if (id == R.id.offline_radio_button && isChecked && Utilities.isLocalIpAvailable(LoginActivity.this)){
            online_button.setChecked(false);
            Utilities.storeNetworkMode("offline", LoginActivity.this);
        } else if (!Utilities.isStaticIpAvailable(LoginActivity.this)){
            online_button.setChecked(false);
            showMessage("No static IP found");
        } else if (!Utilities.isLocalIpAvailable(LoginActivity.this)){
            offline_button.setChecked(false);
            showMessage("No local IP found");
        }
    }

    private void showMessage(String message) {
        if (!is_silent.equals("true")) {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        } else {
            is_silent = "false";
        }
    }
}
