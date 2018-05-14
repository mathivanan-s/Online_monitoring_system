package com.lakshmiindustrialautomation.www.lit.current_status;

/**
 * Created by MATHI on 5/24/2017.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import com.lakshmiindustrialautomation.www.lit.CoreApis;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;
import com.lakshmiindustrialautomation.www.lit.production_report.ProductionReportActivity;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MacLongStopActivity extends Activity {
    String[] strArr,strArr1,strArr2,strArr3,strArr4;
    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3,jsonArray4;
    ArrayList input,input1,input2,input3,input4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_status_activity);
        authenticate();
    }
    private void authenticate(){
        final ProgressDialog progressdialog = new ProgressDialog(MacLongStopActivity.this);
        progressdialog.setMessage("Loading...");
        progressdialog.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, MacLongStopActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);
        api.currentstatusreport("lstop",new Callback<MacRunningStatusResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void success(MacRunningStatusResponse macRunningStatusResponse, Response response) {

                input = macRunningStatusResponse.getR_machine_name();
                input1 = macRunningStatusResponse.getR_eff();
                input2 = macRunningStatusResponse.getR_peff();
                input3 = macRunningStatusResponse.getR_picks();
                input4 = macRunningStatusResponse.getR_meter();


                machine();
                eff();
                peff();
                picks();
                meter();

                TableLayout tv=(TableLayout) findViewById(R.id.table1);

                TableRow trow1=new TableRow(MacLongStopActivity.this);
                trow1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                TextView a1=new TextView(MacLongStopActivity.this);
                a1.setTextSize(18);
                a1.setTextColor(Color.WHITE);
                a1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a1.setText("Machine");
                a1.setWidth(380);
                trow1.addView(a1);

                TextView a2=new TextView(MacLongStopActivity.this);
                a2.setTextSize(18);
                a2.setTextColor(Color.WHITE);
                a2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a2.setText("Eff");
                a2.setWidth(150);
                trow1.addView(a2);

                TextView a3=new TextView(MacLongStopActivity.this);
                a3.setTextSize(18);
                a3.setTextColor(Color.WHITE);
                a3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a3.setText("P.Eff");
                a3.setWidth(150);
                trow1.addView(a3);

                TextView a4=new TextView(MacLongStopActivity.this);
                a4.setTextSize(18);
                a4.setTextColor(Color.WHITE);
                a4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a4.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a4.setText("A.Revol");
                a4.setWidth(200);
                trow1.addView(a4);

                TextView a5=new TextView(MacLongStopActivity.this);
                a5.setTextSize(18);
                a5.setTextColor(Color.WHITE);
                a5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a5.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a5.setText("Kg");
                a5.setWidth(250);
                trow1.addView(a5);

                trow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape111));
                tv.addView(trow1);

                final View vline11 = new View(MacLongStopActivity.this);
                vline11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                vline11.setBackgroundColor(Color.BLUE);
                tv.addView(vline11);



                int Machine_length=strArr.length;
                int i=0;

                while(i<Machine_length)
                {
                    final String machine_name=strArr[i];
                    String str1="<font color=#000000> "+strArr[i]+" </font>";
                    String str2="<font color=#000000> "+strArr1[i]+" </font>";
                    String str3="<font color=#000000> "+strArr2[i]+" </font>";
                    String str4="<<font color=#000000> "+strArr3[i]+" </font>";
                    String str5="<<font color=#000000> "+strArr4[i]+" </font>";

                    final TableRow trow=new TableRow(MacLongStopActivity.this);
                    trow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    trow.setId(i);

                    final TextView b1=new TextView(MacLongStopActivity.this);
                    b1.setTextSize(18);
                    b1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b1.setText(Html.fromHtml(str1));
                    b1.setWidth(250);
                    trow.addView(b1);

                    final TextView b2=new TextView(MacLongStopActivity.this);
                    b2.setTextSize(18);
                    b2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b2.setText(Html.fromHtml(str2));
                    b2.setWidth(150);
                    trow.addView(b2);

                    final TextView b3=new TextView(MacLongStopActivity.this);
                    b3.setTextSize(18);
                    b3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b3.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b3.setText(Html.fromHtml(str3));
                    b3.setWidth(150);
                    trow.addView(b3);


                    final TextView b4=new TextView(MacLongStopActivity.this);
                    b4.setTextSize(18);
                    b4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b4.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b4.setText(Html.fromHtml(str4));
                    b4.setWidth(200);
                    trow.addView(b4);

                    final TextView b5=new TextView(MacLongStopActivity.this);
                    b5.setTextSize(18);
                    b5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b5.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b5.setText(Html.fromHtml(str5));
                    b5.setWidth(200);
                    trow.addView(b5);

                    trow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape111));
                    tv.addView(trow);

                    final View vline1 = new View(MacLongStopActivity.this);
                    vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                    vline1.setBackgroundColor(Color.BLUE);
                    tv.addView(vline1);

                    trow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String e=String.valueOf(machine_name);
                            Toast.makeText(getApplicationContext(), "Machine Name:"+e, Toast.LENGTH_SHORT).show();
                        }
                    });
                    i=i+1;
                }
                progressdialog.dismiss();
            }
            public void eff(){
                jsonArray1 = new JSONArray(input1);
                strArr1 = new String[jsonArray1.length()];

                for (int i = 0; i < jsonArray1.length(); i++) {
                    try {
                        strArr1[i] = jsonArray1.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void machine(){
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void meter(){
                jsonArray4 = new JSONArray(input4);
                strArr4= new String[jsonArray4.length()];
                for (int i = 0; i < jsonArray4.length(); i++) {
                    try {
                        strArr4[i] = jsonArray4.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void peff(){
                jsonArray2 = new JSONArray(input2);
                strArr2 = new String[jsonArray2.length()];
                for (int i = 0; i < jsonArray2.length(); i++) {
                    try {
                        strArr2[i] = jsonArray2.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void picks(){
                jsonArray3 = new JSONArray(input3);
                strArr3 = new String[jsonArray3.length()];
                for (int i = 0; i < jsonArray3.length(); i++) {
                    try {
                        strArr3[i] = jsonArray3.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    Toast.makeText(MacLongStopActivity.this, "Server Request Timeout", Toast.LENGTH_SHORT).show();
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