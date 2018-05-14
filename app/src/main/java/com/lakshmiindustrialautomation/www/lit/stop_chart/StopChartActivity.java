package com.lakshmiindustrialautomation.www.lit.stop_chart;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.lakshmiindustrialautomation.www.lit.CoreApis;

import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;
import com.lakshmiindustrialautomation.www.lit.month_chart.MachineWiseMonthChartActivity;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
/**
 * Created by MATHI on 5/21/2017.
 */
public class StopChartActivity extends AppCompatActivity {
    String[] strArr,strArr1,strArr2;
    JSONArray jsonArray,jsonArray1,jsonArray2;
    ArrayList input,input1,input2;
    HorizontalBarChart chart;
    BarDataSet Bardataset;
    BarData BARDATA;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    int i,j,k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_chart);
        monthchart();

    }
    private void monthchart() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, StopChartActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);

        api.stopchartDetails(new Callback<StopChartResponse>() {
            @Override
            public void success(StopChartResponse stopChartResponse, Response response) {
                input = stopChartResponse.getStop_name();
                input1 = stopChartResponse.getNoofstops();
                input2 = stopChartResponse.getStop_duration();

                chart = (HorizontalBarChart) findViewById(R.id.stop_bar_chart);
                BARENTRY = new ArrayList<>();
                BarEntryLabels = new ArrayList<String>();
                stopname();
                duration();
                noofstops();
                Bardataset = new BarDataSet(BARENTRY, "Stops");
                BARDATA = new BarData(BarEntryLabels, Bardataset);
                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                chart.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                chart.setData(BARDATA);
                Bardataset.setValueTextSize(10);
                chart.setScaleMinima(0f,0f);
                chart.animateY(3000);
                chart.setPinchZoom(true);
                chart.setDragEnabled(true);
                chart.setDescription("Stop Chart");
                chart.invalidate();

            }
            public void stopname(){
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (k = 0; k< jsonArray.length(); k++) {
                    try {
                        strArr[k] = jsonArray.getString(k);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels.add(strArr[k]+"");
                }
            }
            public void noofstops(){
                jsonArray1 = new JSONArray(input1);
                strArr1 = new String[jsonArray1.length()];
                for (j = 0; j < jsonArray1.length(); j++) {
                    try {
                        strArr1[j] = jsonArray1.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BARENTRY.add(new BarEntry(Float.parseFloat(strArr1[j]), j));
                }
            }
            public void duration(){
                jsonArray2 = new JSONArray(input2);
                strArr2 = new String[jsonArray2.length()];
                for ( i = 0; i < jsonArray2.length(); i++) {
                    try {
                        strArr2[i] = jsonArray2.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(StopChartActivity.this);
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