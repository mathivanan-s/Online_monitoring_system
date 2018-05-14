package com.lakshmiindustrialautomation.www.lit;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.lakshmiindustrialautomation.www.lit.current_status.MacRunningStatusResponse;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.CustomListAdapter;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.SingleSheetView;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MATHI on 7/1/2017.
 */

public class ShedAndMachineActivity extends AppCompatActivity {
    String[] strArr,strArr1,strArr2,strArr3,strArr4,strArr5,strArr6,strArr7,strArr8;
    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3,jsonArray4,jsonArray5,jsonArray6,jsonArray7,jsonArray8;
    ArrayList input,input1,input2,input3,input4,input5,input6,input7,input8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shed_and_machine_wise_report);
        authenticate();
        final TabHost host = (TabHost) findViewById(R.id.main_tabHost);
        host.setup();
        //Tab 1 machine type
        TabHost.TabSpec spec = host.newTabSpec("Machine Type");
        spec.setContent(R.id.machine_wise);
        spec.setIndicator("Machine Type");
        host.addTab(spec);
        //tab 1 shed type
        spec = host.newTabSpec("Shed");
        spec.setContent(R.id.shed_wise);
        spec.setIndicator("Shed");
        host.addTab(spec);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                setTabColor(host);

            }
        });
    }
    public static void setTabColor(TabHost host) {
        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF")); //unselected
        }
        host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#3F51B5")); // selected
    }

    private void authenticate(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, ShedAndMachineActivity.this))
                .build();
        CoreApis api = adapter.create(CoreApis.class);
        api.currentstatusreport("stop",new Callback<MacRunningStatusResponse>() {
            @Override
            public void success(MacRunningStatusResponse macRunningStatusResponse, Response response) {

                input = macRunningStatusResponse.getR_machine_name();
                input1 = macRunningStatusResponse.getR_eff();
                input2 = macRunningStatusResponse.getR_peff();
                input3 = macRunningStatusResponse.getR_picks();
                input4 = macRunningStatusResponse.getR_meter();

                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                jsonArray1 = new JSONArray(input1);
                strArr1 = new String[jsonArray1.length()];
                for (int j = 0; j < jsonArray1.length(); j++) {
                    try {
                        strArr1[j] = jsonArray1.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                jsonArray2 = new JSONArray(input2);
                strArr2 = new String[jsonArray2.length()];
                for (int i = 0; i < jsonArray2.length(); i++) {
                    try {
                        strArr2[i] = jsonArray2.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray3 = new JSONArray(input3);
                strArr3 = new String[jsonArray3.length()];
                for (int i = 0; i < jsonArray3.length(); i++) {
                    try {
                        strArr3[i] = jsonArray3.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                jsonArray4 = new JSONArray(input4);
                strArr4 = new String[jsonArray4.length()];
                for (int j = 0; j < jsonArray4.length(); j++) {
                    try {
                        strArr4[j] = jsonArray4.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                jsonArray5 = new JSONArray(input5);
                strArr5 = new String[jsonArray5.length()];
                for (int i = 0; i < jsonArray5.length(); i++) {
                    try {
                        strArr5[i] = jsonArray5.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray6 = new JSONArray(input6);
                strArr6 = new String[jsonArray6.length()];
                for (int i = 0; i < jsonArray6.length(); i++) {
                    try {
                        strArr6[i] = jsonArray6.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray7 = new JSONArray(input7);
                strArr7 = new String[jsonArray7.length()];
                for (int i = 0; i < jsonArray7.length(); i++) {
                    try {
                        strArr7[i] = jsonArray7.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                jsonArray8 = new JSONArray(input8);
                strArr8 = new String[jsonArray8.length()];
                for (int j = 0; j < jsonArray8.length(); j++) {
                    try {
                        strArr8[j] = jsonArray8.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    GridView grid=(GridView) findViewById(R.id.gridView1);
                    //CustomListAdapter adapter=new CustomListAdapter(ShedAndMachineActivity.this,strArr,strArr1,strArr2,
                          //  strArr3,strArr4,strArr5,strArr6, strArr7,strArr8);
                    //grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView machine = (TextView)view.findViewById(R.id.machine_name);
                            TextView aeff = (TextView)view.findViewById(R.id.aeff);
                            TextView peff = (TextView)view.findViewById(R.id.peff);
                            String smachine=machine.getText().toString();
                            String saeff=aeff.getText().toString();
                            String speff=peff.getText().toString();

                            Toast.makeText(ShedAndMachineActivity.this, smachine+"\n"+saeff+"\n"+speff+"\n"+"",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }

            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    Toast.makeText(ShedAndMachineActivity.this,
                            "Server Request Timeout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }@Override
    public void onBackPressed() {

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
