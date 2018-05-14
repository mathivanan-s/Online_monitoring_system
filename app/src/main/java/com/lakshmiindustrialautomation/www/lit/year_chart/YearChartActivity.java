package com.lakshmiindustrialautomation.www.lit.year_chart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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
 * Created by MATHI on 5/14/2017.
 */
public class YearChartActivity extends AppCompatActivity {

    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3,jsonArray4;
    ArrayList input,input1,input2,input3,input4;
    String[] strArr,strArr1,strArr2,strArr3,strArr4;
    BarChart chart,chart1,chart2,chart3 ;
    ArrayList<BarEntry> BARENTRY,BARENTRY1,BARENTRY2,BARENTRY3 ;
    ArrayList<String> BarEntryLabels,BarEntryLabels1,BarEntryLabels2,BarEntryLabels3 ;
    BarDataSet Bardataset,Bardataset1,Bardataset2,Bardataset3 ;
    BarData BARDATA,BARDATA1,BARDATA2,BARDATA3 ;
    TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.year_chart);
        yearchart();

        host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("EFF");
        spec.setContent(R.id.year_chart_eff);
        spec.setIndicator("EFF");
        host.addTab(spec);
        //tab 2

        spec = host.newTabSpec("P.EFF");
        spec.setContent(R.id.year_chart_p_eff);
        spec.setIndicator("P.EFF");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("REVOL");
        spec.setContent(R.id.year_chart_picks);
        spec.setIndicator("A.REVOL");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("KG");
        spec.setContent(R.id.year_chart_meter);
        spec.setIndicator("KG");
        host.addTab(spec);


    }

    private void yearchart() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, YearChartActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);

        api.yearchartDetails(new Callback<YearChartDetailResponse>() {
            @Override
            public void success(YearChartDetailResponse yearChartDetailResponse, Response response) {
                input = yearChartDetailResponse.getXaxis();
                input1 = yearChartDetailResponse.getEff();
                input2 = yearChartDetailResponse.getPicks();
                input3 = yearChartDetailResponse.getMeter();
                input4 = yearChartDetailResponse.getP_eff();


                //eff year_chart

                chart = (BarChart) findViewById(R.id.year_eff_chart);
                BARENTRY = new ArrayList<>();
                BarEntryLabels = new ArrayList<String>();
                AddValuesTocharteff();
                AddValuesToBarcharteff();
                Bardataset = new BarDataSet(BARENTRY, "Actual Efficiency");
                BARDATA = new BarData(BarEntryLabels, Bardataset);
                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                chart.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                chart.setData(BARDATA);
                chart.setScaleMinima(0f,0f);
                chart.animateY(3000);
                chart.setPinchZoom(true);
                chart.setDescription("Month Chart");

                //picks year_chart

                chart1 = (BarChart) findViewById(R.id.year_picks_chart);
                BARENTRY1 = new ArrayList<>();
                BarEntryLabels1 = new ArrayList<String>();
                AddValuesTochartpicks();
                AddValuesToBarchartpicks();
                Bardataset1 = new BarDataSet(BARENTRY1, "A.Revolution");
                BARDATA1 = new BarData(BarEntryLabels1, Bardataset1);
                chart1.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                Bardataset1.setColors(ColorTemplate.COLORFUL_COLORS);
                chart1.setData(BARDATA1);
                chart1.setPinchZoom(true);
                chart1.setScaleMinima(0f,0f);
                chart1.animateY(3000);
                chart1.setDescription("Month Chart");

                //meter year_chart
                chart2 = (BarChart) findViewById(R.id.year_meter_chart);
                BARENTRY2 = new ArrayList<>();
                BarEntryLabels2 = new ArrayList<String>();
                AddValuesTochartmeter();
                AddValuesToBarchartmeter();
                Bardataset2 = new BarDataSet(BARENTRY2, "Kg");
                BARDATA2 = new BarData(BarEntryLabels2, Bardataset2);
                Bardataset2.setColors(ColorTemplate.COLORFUL_COLORS);
                chart2.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                chart2.setData(BARDATA2);
                chart2.setPinchZoom(true);
                chart2.setScaleMinima(0f,0f);
                chart2.animateY(3000);
                chart2.setDescription("Month Chart");

                //year chart p.eff

                chart3 = (BarChart) findViewById(R.id.year_p_eff_chart);
                BARENTRY3 = new ArrayList<>();
                BarEntryLabels3 = new ArrayList<String>();
                AddValuesTochartpeff();
                AddValuesToBarchartpeff();
                Bardataset3 = new BarDataSet(BARENTRY3, "Production Efficiency");
                BARDATA3 = new BarData(BarEntryLabels3, Bardataset3);
                Bardataset3.setColors(ColorTemplate.COLORFUL_COLORS);
                chart3.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                chart3.setData(BARDATA3);
                chart3.setPinchZoom(true);
                chart1.setScaleMinima(0f,0f);
                chart3.animateY(3000);
                chart3.setDescription("Month Chart");
            }

            //eff year_chart
            public void AddValuesTocharteff(){
                jsonArray1 = new JSONArray(input1);
                strArr1 = new String[jsonArray1.length()];

                for (int j = 0; j < jsonArray1.length(); j++) {
                    try {
                        strArr1[j] = jsonArray1.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BARENTRY.add(new BarEntry(Float.parseFloat(strArr1[j]), j));
                }
            }
            public void AddValuesToBarcharteff(){
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels.add(strArr[i]+"");
                }
            }
            //picks year_chart
            public void AddValuesTochartpicks(){
                jsonArray2 = new JSONArray(input2);
                strArr2 = new String[jsonArray2.length()];

                for (int j = 0; j < jsonArray2.length(); j++) {
                    try {
                        strArr2[j] = jsonArray2.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BARENTRY1.add(new BarEntry(Float.parseFloat(strArr2[j]), j));
                }
            }
            public void AddValuesToBarchartpicks(){
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels1.add(strArr[i]+"");
                }
            }
            //meter year_chart
            public void AddValuesTochartmeter(){
                jsonArray3 = new JSONArray(input3);
                strArr3 = new String[jsonArray3.length()];

                for (int j = 0; j < jsonArray3.length(); j++) {
                    try {
                        strArr3[j] = jsonArray3.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BARENTRY2.add(new BarEntry(Float.parseFloat(strArr3[j]), j));
                }
            }
            public void AddValuesToBarchartmeter(){
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels2.add(strArr[i]+"");
                }
            } //p.eff year_chart
            public void AddValuesTochartpeff(){
                jsonArray4 = new JSONArray(input4);
                strArr4 = new String[jsonArray3.length()];

                for (int j = 0; j < jsonArray4.length(); j++) {
                    try {
                        strArr4[j] = jsonArray4.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BARENTRY3.add(new BarEntry(Float.parseFloat(strArr4[j]), j));
                }
            }
            public void AddValuesToBarchartpeff(){
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels3.add(strArr[i]+"");
                }
            }
            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(YearChartActivity.this);
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
    }  @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
