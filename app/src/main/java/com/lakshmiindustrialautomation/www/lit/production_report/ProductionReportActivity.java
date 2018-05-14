package com.lakshmiindustrialautomation.www.lit.production_report;

/**
 * Created by MATHI on 5/24/2017.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;
import com.lakshmiindustrialautomation.www.lit.CoreApis;
import com.lakshmiindustrialautomation.www.lit.LoginActivity;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductionReportActivity extends Activity {
    View mainView = null;
    TableRow trow,trow1;
    int clickcount=0;
    AVLoadingIndicatorView avi;
    String[] strArr, strArr1, strArr2, strArr3, strArr4, strArr5, strArr6, strArr7, strArr8,
            strArr9, strArr10, strArr11, strArr12, strArr13, strArr14, strArr15, strArr16;

    JSONArray jsonArray, jsonArray1, jsonArray2, jsonArray3, jsonArray4, jsonArray5, jsonArray6, jsonArray7, jsonArray8,
            jsonArray9, jsonArray10, jsonArray11, jsonArray12, jsonArray13, jsonArray14, jsonArray15, jsonArray16;

    ArrayList input, input1, input2, input3, input4, input5, input6, input7, input8,
            input9, input10, input11, input12, input13, input14, input15, input16;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.production_report_activity);
        mainView = (TableLayout) findViewById(R.id.table1);
        authenticate();

        String indicator=getIntent().getStringExtra("indicator");
        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);
        avi.show();

        Button buttonZoomOut = (Button) findViewById(R.id.buttonZoomOut);
        Button buttonNormal = (Button) findViewById(R.id.buttonNormal);

        buttonZoomOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    zoom(0.7f, 0.7f, new PointF(0, 0));
                }
                else if(clickcount==2)
                {
                    zoom(0.6f, 0.6f, new PointF(0, 0));
                }
                else if(clickcount==3)
                {
                    zoom(0.5f, 0.5f, new PointF(0, 0));
                }
                else if(clickcount==4)
                {
                    zoom(0.4f, 0.4f, new PointF(0, 0));
                }
            }
        });

        buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
                clickcount=0;
            }
        });
    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        mainView.setPivotX(pivot.x);
        mainView.setPivotY(pivot.y);
        mainView.setScaleX(scaleX);
        mainView.setScaleY(scaleY);
    }
    private void authenticate(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, ProductionReportActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);
        api.productionreport(new Callback<ProductionReportResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void success(ProductionReportResponse productionReportResponse, Response response) {

                input = productionReportResponse.getMachine();
                input1 = productionReportResponse.getAct_eff();
                input2 = productionReportResponse.getProd_eff();
                input3 = productionReportResponse.getKpicks();
                input4 = productionReportResponse.getMtr();
                input5 = productionReportResponse.getRpm();
                input6 = productionReportResponse.getWarp();
                input7 = productionReportResponse.getWarp_time();
                input8 = productionReportResponse.getWeft();
                input9 = productionReportResponse.getWeft_time();
                input10 = productionReportResponse.getShort_stops();
                input11 = productionReportResponse.getShort_stops_time();
                input12= productionReportResponse.getLong_stops();
                input13 = productionReportResponse.getLong_stops_time();
                input14 = productionReportResponse.getOthers();
                input15 = productionReportResponse.getOthers_stop_time();
                input16 = productionReportResponse.getRuntime();

                machine();
                eff();
                peff();
                picks();
                meter();
                rpm();
                warp();
                warp_time();
                weft();
                weft_time();
                short_stop();
                short_stop_time();
                longstop();
                longstop_time();
                others_stop();
                others_stop_time();
                runtime();

                TableLayout tv=(TableLayout) findViewById(R.id.table1);

                //this is header row for report table

                trow1=new TableRow(ProductionReportActivity.this);
                trow1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                TextView a1=new TextView(ProductionReportActivity.this);
                a1.setTextSize(18);
                a1.setTextColor(Color.WHITE);
                a1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a1.setText("Machine");
                a1.setWidth(380);
                trow1.addView(a1);

                TextView a2=new TextView(ProductionReportActivity.this);
                a2.setTextSize(18);
                a2.setTextColor(Color.WHITE);
                a2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a2.setText("Eff");
                a2.setWidth(150);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("a_eff", false)){
                    trow1.addView(a2);
                }else{
                    trow1.removeView(a2);
                }
                TextView a3=new TextView(ProductionReportActivity.this);
                a3.setTextSize(18);
                a3.setTextColor(Color.WHITE);
                a3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a3.setText("P.Eff");
                a3.setWidth(150);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("p_eff", false)){
                    trow1.addView(a3);
                }else{
                    trow1.removeView(a3);
                }

                TextView a4=new TextView(ProductionReportActivity.this);
                a4.setTextSize(18);
                a4.setTextColor(Color.WHITE);
                a4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a4.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a4.setText("A.Revol");
                a4.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("picks", false)){
                    trow1.addView(a4);
                }else{
                    trow1.removeView(a4);
                }

                TextView a5=new TextView(ProductionReportActivity.this);
                a5.setTextSize(18);
                a5.setTextColor(Color.WHITE);
                a5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a5.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a5.setText("Kg");
                a5.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("meter", false)){
                    trow1.addView(a5);
                }else{
                    trow1.removeView(a5);
                }

                TextView a6=new TextView(ProductionReportActivity.this);
                a6.setTextSize(18);
                a6.setTextColor(Color.WHITE);
                a6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a6.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a6.setText("Rpm");
                a6.setWidth(150);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("rpm", false)){
                    trow1.addView(a6);
                }else{
                    trow1.removeView(a6);
                }

                TextView a7=new TextView(ProductionReportActivity.this);
                a7.setTextSize(18);
                a7.setTextColor(Color.WHITE);
                a7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a7.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a7.setText("Storage");
                a7.setWidth(200);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("warp", false)){
                    trow1.addView(a7);
                }else{
                    trow1.removeView(a7);
                }

                TextView a8=new TextView(ProductionReportActivity.this);
                a8.setTextSize(18);
                a8.setTextColor(Color.WHITE);
                a8.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a8.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a8.setText("ST.Time");
                a8.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("warp_time", false)){
                    trow1.addView(a8);
                }else{
                    trow1.removeView(a8);
                }

                TextView a9=new TextView(ProductionReportActivity.this);
                a9.setTextSize(18);
                a9.setTextColor(Color.WHITE);
                a9.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a9.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a9.setText("Lycra");
                a9.setWidth(150);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("weft", false)){
                    trow1.addView(a9);
                }else{
                    trow1.removeView(a9);
                }

                TextView a10=new TextView(ProductionReportActivity.this);
                a10.setTextSize(18);
                a10.setTextColor(Color.WHITE);
                a10.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a10.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a10.setText("Ly.Time");
                a10.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("weft_time", false)){
                    trow1.addView(a10);
                }else{
                    trow1.removeView(a10);
                }

                TextView a11=new TextView(ProductionReportActivity.this);
                a11.setTextSize(18);
                a11.setTextColor(Color.WHITE);
                a11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a11.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a11.setText("S.Stop");
                a11.setWidth(200);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("short_stop", false)){
                    trow1.addView(a11);
                }else{
                    trow1.removeView(a11);
                }

                TextView a12=new TextView(ProductionReportActivity.this);
                a12.setTextSize(18);
                a12.setTextColor(Color.WHITE);
                a12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a12.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a12.setText("S.S.Time");
                a12.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("short_stop_time", false)){
                    trow1.addView(a12);
                }else{
                    trow1.removeView(a12);
                }

                TextView a13=new TextView(ProductionReportActivity.this);
                a13.setTextSize(18);
                a13.setTextColor(Color.WHITE);
                a13.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a13.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a13.setText("L.Stop");
                a13.setWidth(200);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("long_stop", false)){
                    trow1.addView(a13);
                }else{
                    trow1.removeView(a13);
                }

                TextView a14=new TextView(ProductionReportActivity.this);
                a14.setTextSize(18);
                a14.setTextColor(Color.WHITE);
                a14.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a14.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a14.setText("L.S.Time");
                a14.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("long_stop_time", false)){
                    trow1.addView(a14);
                }else{
                    trow1.removeView(a14);
                }

                TextView a15=new TextView(ProductionReportActivity.this);
                a15.setTextSize(18);
                a15.setTextColor(Color.WHITE);
                a15.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a15.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a15.setText("O.Stop");
                a15.setWidth(200);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("other_stop", false)){
                    trow1.addView(a15);
                }else{
                    trow1.removeView(a15);
                }

                TextView a16=new TextView(ProductionReportActivity.this);
                a16.setTextSize(18);
                a16.setTextColor(Color.WHITE);
                a16.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a16.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a16.setText("O.S.Time");
                a16.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("other_stop_time", false)){
                    trow1.addView(a16);
                }else{
                    trow1.removeView(a16);
                }

                TextView a17=new TextView(ProductionReportActivity.this);
                a17.setTextSize(18);
                a17.setTextColor(Color.WHITE);
                a17.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a17.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a17.setText("Run.Time");
                a17.setWidth(250);
                if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("run_time", false)){
                    trow1.addView(a17);
                }else{
                    trow1.removeView(a17);
                }

                trow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape111));
                tv.addView(trow1);

                final View vline11 = new View(ProductionReportActivity.this);
                vline11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                vline11.setBackgroundColor(Color.BLUE);
                tv.addView(vline11);

                //second row for report data

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
                    String str6="<<font color=#000000> "+strArr5[i]+" </font>";
                    String str7="<<font color=#000000> "+strArr6[i]+" </font>";
                    String str8="<<font color=#000000> "+strArr7[i]+" </font>";
                    String str9="<<font color=#000000> "+strArr8[i]+" </font>";
                    String str10="<<font color=#000000> "+strArr9[i]+" </font>";
                    String str11="<<font color=#000000> "+strArr10[i]+" </font>";
                    String str12="<<font color=#000000> "+strArr11[i]+" </font>";
                    String str13="<<font color=#000000> "+strArr12[i]+" </font>";
                    String str14="<<font color=#000000> "+strArr13[i]+" </font>";
                    String str15="<<font color=#000000> "+strArr14[i]+" </font>";
                    String str16="<<font color=#000000> "+strArr15[i]+" </font>";
                    String str17="<<font color=#000000> "+strArr16[i]+" </font>";

                    trow=new TableRow(ProductionReportActivity.this);
                    trow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    trow.setId(i);

                    TextView b1=new TextView(ProductionReportActivity.this);
                    b1.setTextSize(18);
                    b1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b1.setText(Html.fromHtml(str1));
                    b1.setWidth(145);
                    trow.addView(b1);

                    TextView b2=new TextView(ProductionReportActivity.this);
                    b2.setTextSize(18);
                    b2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b2.setText(Html.fromHtml(str2));
                    b2.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("a_eff", false)){
                        trow.addView(b2);
                    }else{
                        trow.removeView(b2);
                    }

                    TextView b3=new TextView(ProductionReportActivity.this);
                    b3.setTextSize(18);
                    b3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b3.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b3.setText(Html.fromHtml(str3));
                    b3.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("p_eff", false)){
                        trow.addView(b3);
                    }else{
                        trow.removeView(b3);
                    }

                    TextView b4=new TextView(ProductionReportActivity.this);
                    b4.setTextSize(18);
                    b4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b4.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b4.setText(Html.fromHtml(str4));
                    b4.setWidth(120);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("picks", false)){
                        trow.addView(b4);
                    }else{
                        trow.removeView(b4);
                    }

                    TextView b5=new TextView(ProductionReportActivity.this);
                    b5.setTextSize(18);
                    b5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b5.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b5.setText(Html.fromHtml(str5));
                    b5.setWidth(120);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("meter", false)){
                        trow.addView(b5);
                    }else{
                        trow.removeView(b5);
                    }

                    TextView b6=new TextView(ProductionReportActivity.this);
                    b6.setTextSize(18);
                    b6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b6.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b6.setText(Html.fromHtml(str6));
                    b6.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("rpm", false)){
                        trow.addView(b6);
                    }else{
                        trow.removeView(b6);
                    }

                    TextView b7=new TextView(ProductionReportActivity.this);
                    b7.setTextSize(18);
                    b7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b7.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b7.setText(Html.fromHtml(str7));
                    b7.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("warp", false)){
                        trow.addView(b7);
                    }else{
                        trow.removeView(b7);
                    }

                    TextView b8=new TextView(ProductionReportActivity.this);
                    b8.setTextSize(18);
                    b8.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b8.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b8.setText(Html.fromHtml(str8));
                    b8.setWidth(140);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("warp_time", false)){
                        trow.addView(b8);
                    }else{
                        trow.removeView(b8);
                    }

                    TextView b9=new TextView(ProductionReportActivity.this);
                    b9.setTextSize(18);
                    b9.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b9.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b9.setText(Html.fromHtml(str9));
                    b9.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("weft", false)){
                        trow.addView(b9);
                    }else{
                        trow.removeView(b9);
                    }

                    TextView b10=new TextView(ProductionReportActivity.this);
                    b10.setTextSize(18);
                    b10.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b10.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b10.setText(Html.fromHtml(str10));
                    b10.setWidth(140);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("weft_time", false)){
                        trow.addView(b10);
                    }else{
                        trow.removeView(b10);
                    }

                    TextView b11=new TextView(ProductionReportActivity.this);
                    b11.setTextSize(18);
                    b11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b11.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b11.setText(Html.fromHtml(str11));
                    b11.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("short_stop", false)){
                        trow.addView(b11);
                    }else{
                        trow.removeView(b11);
                    }

                    TextView b12=new TextView(ProductionReportActivity.this);
                    b12.setTextSize(18);
                    b12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b12.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b12.setText(Html.fromHtml(str12));
                    b12.setWidth(140);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("short_stop_time", false)){
                        trow.addView(b12);
                    }else{
                        trow.removeView(b12);
                    }

                    TextView b13=new TextView(ProductionReportActivity.this);
                    b13.setTextSize(18);
                    b13.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b13.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b13.setText(Html.fromHtml(str13));
                    b13.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("long_stop", false)){
                        trow.addView(b13);
                    }else{
                        trow.removeView(b13);
                    }

                    TextView b14=new TextView(ProductionReportActivity.this);
                    b14.setTextSize(18);
                    b14.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b14.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b14.setText(Html.fromHtml(str14));
                    b14.setWidth(140);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("long_stop_time", false)){
                        trow.addView(b14);
                    }else{
                        trow.removeView(b14);
                    }

                    TextView b15=new TextView(ProductionReportActivity.this);
                    b15.setTextSize(18);
                    b15.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b15.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b15.setText(Html.fromHtml(str15));
                    b15.setWidth(100);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("other_stop", false)){
                        trow.addView(b15);
                    }else{
                        trow.removeView(b15);
                    }

                     TextView b16=new TextView(ProductionReportActivity.this);
                    b16.setTextSize(18);
                    b16.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b16.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b16.setText(Html.fromHtml(str16));
                    b16.setWidth(140);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("other_stop_time", false)){
                        trow.addView(b16);
                    }else{
                        trow.removeView(b16);
                    }

                    TextView b17=new TextView(ProductionReportActivity.this);
                    b17.setTextSize(18);
                    b17.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b17.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b17.setText(Html.fromHtml(str17));
                    b17.setWidth(140);
                    if(PreferenceManager.getDefaultSharedPreferences(ProductionReportActivity.this).getBoolean("run_time", false)){
                        trow.addView(b17);
                    }else{
                        trow.removeView(b17);
                    }


                    trow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape111));
                    tv.addView(trow);

                    final View vline1 = new View(ProductionReportActivity.this);
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
                avi.hide();

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
            public void rpm(){
                jsonArray5 = new JSONArray(input5);
                strArr5 = new String[jsonArray5.length()];
                for (int i = 0; i < jsonArray5.length(); i++) {
                    try {
                        strArr5[i] = jsonArray5.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void warp(){
                jsonArray6 = new JSONArray(input6);
                strArr6 = new String[jsonArray6.length()];

                for (int i = 0; i < jsonArray6.length(); i++) {
                    try {
                        strArr6[i] = jsonArray6.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void warp_time(){
                jsonArray7 = new JSONArray(input7);
                strArr7= new String[jsonArray7.length()];

                for (int i = 0; i < jsonArray7.length(); i++) {
                    try {
                        strArr7[i] = jsonArray7.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
            public void weft(){
                jsonArray8 = new JSONArray(input8);
                strArr8 = new String[jsonArray8.length()];
                for (int i = 0; i < jsonArray8.length(); i++) {
                    try {
                        strArr8[i] = jsonArray8.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void weft_time(){
                jsonArray9 = new JSONArray(input9);
                strArr9 = new String[jsonArray9.length()];
                for (int i = 0; i < jsonArray9.length(); i++) {
                    try {
                        strArr9[i] = jsonArray9.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void short_stop(){
                jsonArray10 = new JSONArray(input10);
                strArr10 = new String[jsonArray10.length()];
                for (int i = 0; i < jsonArray10.length(); i++) {
                    try {
                        strArr10[i] = jsonArray10.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void short_stop_time(){
                jsonArray11 = new JSONArray(input11);
                strArr11 = new String[jsonArray11.length()];
                for (int i = 0; i < jsonArray11.length(); i++) {
                    try {
                        strArr11[i] = jsonArray11.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void longstop(){
                jsonArray12 = new JSONArray(input12);
                strArr12 = new String[jsonArray12.length()];
                for (int i = 0; i < jsonArray12.length(); i++) {
                    try {
                        strArr12[i] = jsonArray12.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void longstop_time(){
                jsonArray13 = new JSONArray(input13);
                strArr13 = new String[jsonArray13.length()];
                for (int i = 0; i < jsonArray13.length(); i++) {
                    try {
                        strArr13[i] = jsonArray13.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void others_stop(){
                jsonArray14 = new JSONArray(input14);
                strArr14 = new String[jsonArray14.length()];
                for (int i = 0; i < jsonArray14.length(); i++) {
                    try {
                        strArr14[i] = jsonArray14.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void others_stop_time(){
                jsonArray15 = new JSONArray(input15);
                strArr15 = new String[jsonArray15.length()];
                for (int i = 0; i < jsonArray15.length(); i++) {
                    try {
                        strArr15[i] = jsonArray15.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void runtime(){
                jsonArray16 = new JSONArray(input16);
                strArr16 = new String[jsonArray16.length()];
                for (int i = 0; i < jsonArray16.length(); i++) {
                    try {
                        strArr16[i] = jsonArray16.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductionReportActivity.this);
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