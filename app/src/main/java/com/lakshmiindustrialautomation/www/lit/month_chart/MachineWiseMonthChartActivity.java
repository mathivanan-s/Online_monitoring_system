package com.lakshmiindustrialautomation.www.lit.month_chart;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
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
import com.lakshmiindustrialautomation.www.lit.current_status.MacRunningStatusResponse;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.SingleSheetResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.lakshmiindustrialautomation.www.lit.ShedAndMachineActivity.setTabColor;

/**
 * Created by MATHI on 22/6/2017.
 */

public class MachineWiseMonthChartActivity extends AppCompatActivity implements View.OnClickListener {
    JSONArray jsonArray, jsonArray1, jsonArray2, jsonArray3, jsonArray4;
    ArrayList input, input1, input2, input3, input4;
    String[] strArr, strArr1, strArr2, strArr3, strArr4;
    BarChart chart, chart1, chart2, chart3;
    ArrayList<BarEntry> BARENTRY, BARENTRY1, BARENTRY2, BARENTRY3;
    ArrayList<String> BarEntryLabels, BarEntryLabels1, BarEntryLabels2, BarEntryLabels3;
    BarDataSet Bardataset, Bardataset1, Bardataset2, Bardataset3;
    BarData BARDATA, BARDATA1, BARDATA2, BARDATA3;
    ArrayList<String> imonths;
    int thisMonth;
    ArrayAdapter<String> adapter, spinadapter;
    Spinner spinMonth, spinMac;
    String machine,months,state;
    String[] MONTHS = {"January", "February", "March","April","May","June","July",
            "August","September","October","November", "December"};
    TabHost host;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_wise_month_chart);


        host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("EFF");
        spec.setContent(R.id.machine_wise_month_chart_eff);
        spec.setIndicator("EFF");
        host.addTab(spec);
        //tab 2
        spec = host.newTabSpec("P.EFF");
        spec.setContent(R.id.machine_wise_month_chart_p_eff);
        spec.setIndicator("P.EFF");
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("REVOL");
        spec.setContent(R.id.machine_wise_month_chart_picks);
        spec.setIndicator("A.REVOL");
        host.addTab(spec);
        //Tab 4
        spec = host.newTabSpec("Kg");
        spec.setContent(R.id.machine_wise_month_chart_meter);
        spec.setIndicator("Kg");
        host.addTab(spec);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                setTabColor(host);

            }
        });

        imonths = new ArrayList<String>();
        thisMonth = Calendar.getInstance().get(Calendar.MONTH);

        for (int i = 0; i <= thisMonth; i++) {
            imonths.add(MONTHS[i]);
        }
        //spinner get month
        adapter = new ArrayAdapter<String>(this, R.layout.month_wise_chart_spin_list, imonths);
        spinMonth = (Spinner) findViewById(R.id.monthspin);
        spinMonth.setAdapter(adapter);
        spinMonth.setSelection(thisMonth);
        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                months = (String)spinMonth.getSelectedItem();
                switch(months){
                    case "January":
                        state="january";
                        break;
                    case "February":
                        state="february";
                        break;
                    case "March":
                        state="march";
                        break;
                    case "April":
                        state="april";
                        break;
                    case "May":
                        state="may";
                        break;
                    case "June":
                        state="june";
                        break;
                    case "July":
                        state="july";
                        break;
                    case "August":
                        state="august";
                        break;
                    case "September":
                        state="september";
                        break;
                    case "October":
                        state="october";
                        break;
                    case "November":
                        state="november";
                        break;
                    case "December":
                        state="december";
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        CoreApis adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, MachineWiseMonthChartActivity.this))
                .build().create(CoreApis.class);

        adapter. getsinglesheet(new Callback<SingleSheetResponse>() {
            @Override
            public void success(SingleSheetResponse singleSheetResponse, Response response) {

                input = singleSheetResponse.getMachine_name();

                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // spinner 2 get machine name

                    spinadapter = new ArrayAdapter<String>(MachineWiseMonthChartActivity.this, R.layout.machine_wise_spin_list, strArr);
                    spinMac = (Spinner) findViewById(R.id.month_mac_spin);
                    spinMac.setAdapter(spinadapter);
                    spinMac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            TextView select_machine=(TextView)view.findViewById(R.id.select_machine);
                            machine =select_machine.getText().toString();

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }@Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MachineWiseMonthChartActivity.this);
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
        Button bt=(Button)findViewById(R.id.spinview);
        bt.setOnClickListener(MachineWiseMonthChartActivity.this);
    }
    public static void setTabColor(TabHost host) {
        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            host.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.border); //unselected
        }
        host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFCC0000")); // selected
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.spinview){
            addmachine();
        }

    }
    private void addmachine() {
        CoreApis adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, MachineWiseMonthChartActivity.this))
                .build().create(CoreApis.class);

        adapter.machinewisemonthchartDetails(state,machine, new Callback<MonthWiseChartResponse>() {
            @Override
            public void success(MonthWiseChartResponse monthWiseChartResponse, Response response) {
                input = monthWiseChartResponse.getXaxis();
                input1 = monthWiseChartResponse.getEff();
                input2 = monthWiseChartResponse.getPicks();
                input3 = monthWiseChartResponse.getMeter();
                input4 = monthWiseChartResponse.getP_eff();

                //eff month_chart
                chart = (BarChart) findViewById(R.id.machine_wise_month_eff_chart);
                BARENTRY = new ArrayList<>();
                BarEntryLabels = new ArrayList<String>();
                AddValuesTocharteff();
                AddValuesToBarcharteff();
                Bardataset = new BarDataSet(BARENTRY, "Actual Efficiency");
                BARDATA = new BarData(BarEntryLabels, Bardataset);
                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                chart.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                chart.setData(BARDATA);
                if(jsonArray.length()<=7){
                    chart.setScaleMinima(0f,0f);
                }
                else if(jsonArray.length()<=15){
                    chart.setScaleMinima(1f,0f);
                }
                else if(jsonArray.length()>20){
                    chart.setScaleMinima(4f,0f);
                }
                chart.animateY(3000);
                chart.setPinchZoom(true);
                chart.setDescription( state+"\n \n"+machine);

                //picks month_chart
                chart1 = (BarChart) findViewById(R.id.machine_wise_month_picks_chart);
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
                if(jsonArray.length()<=7){
                    chart1.setScaleMinima(0f,0f);
                }
                else if(jsonArray.length()<=15){
                    chart1.setScaleMinima(1f,0f);
                }
                else if(jsonArray.length()>20){
                    chart1.setScaleMinima(4f,0f);
                }
                chart1.animateY(3000);
                chart1.setDescription(state+"\n \n"+machine);

                //meter month_chart
                chart2 = (BarChart) findViewById(R.id.machine_wise_month_meter_chart);
                BARENTRY2 = new ArrayList<>();
                BarEntryLabels2 = new ArrayList<String>();
                AddValuesTochartmeter();
                AddValuesToBarchartmeter();
                Bardataset2 = new BarDataSet(BARENTRY2, "Kg");
                BARDATA2 = new BarData(BarEntryLabels2, Bardataset2);
                Bardataset2.setColors(ColorTemplate.COLORFUL_COLORS);
                chart2.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                if(jsonArray.length()<=7){
                    chart2.setScaleMinima(0f,0f);
                }
                else if(jsonArray.length()<=15){
                    chart2.setScaleMinima(1f,0f);
                }
                else if(jsonArray.length()>20){
                    chart2.setScaleMinima(4f,0f);
                }
                chart2.setData(BARDATA2);
                chart2.setPinchZoom(true);
                chart2.animateY(3000);
                chart2.setDescription(state+"\n \n"+machine);

                //month chart p.eff
                chart3 = (BarChart) findViewById(R.id.machine_wise_month_p_eff_chart);
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
                if(jsonArray.length()<=7){
                    chart3.setScaleMinima(0f,0f);
                }
                else if(jsonArray.length()<=15){
                    chart3.setScaleMinima(1f,0f);
                }
                else if(jsonArray.length()>20){
                    chart3.setScaleMinima(4f,0f);
                }
                chart3.animateY(3000);
                chart3.setDescription(state+"\n \n"+machine);
            }

            //eff month_chart
            public void AddValuesTocharteff() {
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
            public void AddValuesToBarcharteff() {
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels.add(strArr[i] + "");

                }
            }
            //picks month_chart
            public void AddValuesTochartpicks() {
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
            public void AddValuesToBarchartpicks() {
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels1.add(strArr[i] + "");
                }
            }
            //meter month_chart
            public void AddValuesTochartmeter() {
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
            public void AddValuesToBarchartmeter() {
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels2.add(strArr[i] + "");
                }
            }
            //p.eff month_chart
            public void AddValuesTochartpeff() {
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
            public void AddValuesToBarchartpeff() {
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        strArr[i] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BarEntryLabels3.add(strArr[i] + "");
                }
            }
            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MachineWiseMonthChartActivity.this);
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


}
