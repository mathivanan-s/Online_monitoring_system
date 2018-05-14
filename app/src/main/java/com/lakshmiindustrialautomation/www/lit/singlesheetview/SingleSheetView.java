package com.lakshmiindustrialautomation.www.lit.singlesheetview;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.lakshmiindustrialautomation.www.lit.CoreApis;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;
import com.lakshmiindustrialautomation.www.lit.month_chart.MachineWiseMonthChartActivity;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MATHI on 7/27/2017.
 */

public class SingleSheetView extends Activity {
    private final Handler handler = new Handler();
    String[] strArr,strArr1,strArr2,strArr3,strArr4,strArr5,strArr6, strArr7,strArr8,strArr9,strArr10, strArr11;
    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3,jsonArray4,jsonArray5,jsonArray6,jsonArray7,jsonArray8,jsonArray9,jsonArray10,jsonArray11;
    ArrayList input,input1,input2,input3,input4,input5,input6,input7,input8,input9,input10,input11;
    GridView grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_sheet_view);
        authenticate();
        doTheAutoRefresh();
    }
    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doTheAutoRefresh();
                authenticate();

            }
        }, 30000);
    }
    private void authenticate(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, SingleSheetView.this))
                .build();
        CoreApis api = adapter.create(CoreApis.class);
        api.getsinglesheet(new Callback<SingleSheetResponse>() {
            @Override
            public void success(SingleSheetResponse singleSheetResponse, Response response) {

                input = singleSheetResponse.getStatus();
                input1 = singleSheetResponse.getStop_code();
                input2 = singleSheetResponse.getMachine_name();
                input3 = singleSheetResponse.getEff();
                input4 = singleSheetResponse.getPeff();
                input5 = singleSheetResponse.getWarp();
                input6 = singleSheetResponse.getWarp_time();
                input7 = singleSheetResponse.getWeft();
                input8= singleSheetResponse.getWeft_time();
                input9=singleSheetResponse.getRpm();
                input10 = singleSheetResponse.getFab_design();
                input11 = singleSheetResponse.getOperator_name();


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
                }

                jsonArray9 = new JSONArray(input9);
                strArr9 = new String[jsonArray9.length()];
                for (int i = 0; i < jsonArray9.length(); i++) {
                    try {
                        strArr9[i] = jsonArray9.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray10 = new JSONArray(input10);
                strArr10 = new String[jsonArray10.length()];
                for (int i = 0; i < jsonArray10.length(); i++) {
                    try {
                        strArr10[i] = jsonArray10.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray11 = new JSONArray(input11);
                strArr11 = new String[jsonArray11.length()];
                for (int i = 0; i < jsonArray11.length(); i++) {
                    try {
                        strArr11[i] = jsonArray11.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    grid=(GridView) findViewById(R.id.gridView1);
                    CustomListAdapter adapter=new CustomListAdapter(SingleSheetView.this,strArr,strArr1,strArr2, strArr3,
                            strArr4,strArr5,strArr6, strArr7,strArr8,strArr9,strArr10, strArr11);
                    grid.setAdapter(adapter);


                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView machine = (TextView)view.findViewById(R.id.machine_name);
                            TextView aeff = (TextView)view.findViewById(R.id.aeff);
                            TextView peff = (TextView)view.findViewById(R.id.peff);
                            String smachine=machine.getText().toString();
                            String saeff=aeff.getText().toString();
                            String speff=peff.getText().toString();

                            Toast.makeText(SingleSheetView.this, smachine+"\n"+saeff+"\n"+speff+"\n"+"", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SingleSheetView.this);
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
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
