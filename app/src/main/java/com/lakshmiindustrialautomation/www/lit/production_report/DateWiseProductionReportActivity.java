package com.lakshmiindustrialautomation.www.lit.production_report;

/**
 * Created by MATHI on 6/28/2017.
 */

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.lakshmiindustrialautomation.www.lit.CSVWriter;
import com.lakshmiindustrialautomation.www.lit.CoreApis;
import com.lakshmiindustrialautomation.www.lit.LoginActivity;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;
import com.lakshmiindustrialautomation.www.lit.current_status.MacRunningStatusResponse;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.SingleSheetResponse;
import com.lakshmiindustrialautomation.www.lit.ui.NormalAlertsListActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DateWiseProductionReportActivity extends AppCompatActivity implements View.OnClickListener {
    TableLayout tableLayout;
    Button date;
    EditText edit_csv_name;
    TableRow trow,trow1;
    Spinner spinShift;
    DatePickerDialog datePickerDialog;
    View mainView = null;
    String current_date,select_date,select_shift;
    int select_saved_date;
    String[] SHIFT={"-","1","2","3"};
    ArrayList<String> shiftlist;
    String[] strArr, strArr1, strArr2, strArr3, strArr4, strArr5, strArr6, strArr7, strArr8, strArr9,
            strArr10, strArr11, strArr12, strArr13, strArr14, strArr15, strArr16;
    JSONArray jsonArray, jsonArray1, jsonArray2, jsonArray3, jsonArray4, jsonArray5, jsonArray6,
            jsonArray7, jsonArray8, jsonArray9, jsonArray10, jsonArray11, jsonArray12, jsonArray13,
            jsonArray14, jsonArray15, jsonArray16;
    ArrayList  input, input1, input2, input3, input4, input5, input6, input7, input8, input9,
            input10, input11, input12, input13, input14, input15, input16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_wise_production_report);
        date = (Button) findViewById(R.id.date);
        mainView = (TableLayout) findViewById(R.id.table1);
        ImageButton csv = (ImageButton) findViewById(R.id.print_fab);

        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(select_saved_date==0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Select The Date ",
                            Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toastView.setBackgroundResource(R.drawable.toast_background);
                    toast.show();
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DateWiseProductionReportActivity.this);
                    alertDialog.setTitle("Export to CSV");
                    alertDialog.setMessage("Enter File Name");

                    edit_csv_name = new EditText(DateWiseProductionReportActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    edit_csv_name.setLayoutParams(lp);
                    alertDialog.setView(edit_csv_name);
                    edit_csv_name.setText("Production Report For "+date.getText().toString()+"Shift "+select_shift+".csv");
                    alertDialog.setIcon(R.drawable.csvfileimage);
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (edit_csv_name.length() == 0) {
                                Toast.makeText(DateWiseProductionReportActivity.this,
                                        "Please Enter File Name..", Toast.LENGTH_SHORT).show();
                            } else {
                                exportcsv();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

        Button buttonZoomOut = (Button) findViewById(R.id.buttonZoomOut);
        Button buttonNormal = (Button) findViewById(R.id.buttonNormal);

        buttonZoomOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zoom(0.5f, 0.5f, new PointF(0, 0));
            }
        });
        buttonNormal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final  int mYear = c.get(Calendar.YEAR);
                final  int mMonth = c.get(Calendar.MONTH);
                final  int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(DateWiseProductionReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                        int mnth=monthOfYear+1;
                        String mnth1;
                        if (mnth < 10) {
                            mnth1="0"+mnth;
                        }
                        else{
                            mnth1=""+mnth;
                        }
                        int idate=dayOfMonth;
                        String day1;
                        if (idate< 10) {
                            day1="0"+idate;
                        }
                        else{
                            day1=""+idate;
                        }
                        select_date=(day1 + "-" + mnth1 + "-" + year);
                        current_date=String.valueOf(mDay+"-"+(mMonth+1)+"-"+mYear);
                        select_saved_date= (dayOfMonth + (monthOfYear + 1)  + year);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date1 = null;
                        try {
                            date1 = formatter.parse(current_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date date2 = null;
                        try {
                            date2 = formatter.parse(select_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date1.compareTo(date2)<0)
                            {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Please Select Valid Date or Month or Year", Toast.LENGTH_SHORT);
                                View toastView = toast.getView();
                                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                                toastView.setBackgroundResource(R.drawable.toast_background);
                                toast.show();
                            }
                        else
                        {
                            date.setText(select_date);
                        }
                    }
                }, mYear, mMonth, mDay);
            datePickerDialog.show();
            }
        });
        CoreApis adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, DateWiseProductionReportActivity.this))
                .build().create(CoreApis.class);

        adapter.getsinglesheet(new Callback<SingleSheetResponse>() {
            @Override
            public void success(SingleSheetResponse singleSheetResponse, Response response) {

                int shift_total = Integer.parseInt(singleSheetResponse.getShift_count());
                shiftlist = new ArrayList<String>();
                for (int i = 0; i <= shift_total; i++) {
                    shiftlist.add(SHIFT[i]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DateWiseProductionReportActivity.this,
                        R.layout.date_wise_shift_spin_list, shiftlist);
                spinShift = (Spinner) findViewById(R.id.shift_select_spinner);
                spinShift.setAdapter(adapter);
                spinShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        TextView shift = (TextView) view.findViewById(R.id.select_shift_wise);
                        select_shift = shift.getText().toString();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });
            }
            @Override
            public void failure(RetrofitError cause) {
                if (cause.getResponse() == null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DateWiseProductionReportActivity.this);
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
        Button bt=(Button)findViewById(R.id.date_wise_view);
        bt.setOnClickListener(DateWiseProductionReportActivity.this);
    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        mainView.setPivotX(pivot.x);
        mainView.setPivotY(pivot.y);
        mainView.setScaleX(scaleX);
        mainView.setScaleY(scaleY);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.date_wise_view){
            if(select_saved_date==0){
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select The Date ",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.toast_background);
                toast.show();
            }
            else{
                TextView tv=(TextView)findViewById(R.id.select_date);
                tv.setText(select_date);
                TextView tv1=(TextView)findViewById(R.id.select_shift);
                tv1.setText(select_shift);
                addmachine();
            }
        }
    }
    private void addmachine(){
        final ProgressDialog progressdialog = new ProgressDialog(DateWiseProductionReportActivity.this);
        progressdialog.setMessage("Loading...");
        progressdialog.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Utilities.getCurrentIp(false, DateWiseProductionReportActivity.this))
                .build();

        CoreApis api = adapter.create(CoreApis.class);
        api.datewiseproductionreport(date.getText().toString(),select_shift,new Callback<ProductionReportResponse>() {
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

                tableLayout=(TableLayout) findViewById(R.id.table1);
                if(tableLayout.getChildCount()>0){
                    tableLayout.removeAllViewsInLayout();
                }

                trow1=new TableRow(DateWiseProductionReportActivity.this);
                trow1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView a1=new TextView(DateWiseProductionReportActivity.this);
                a1.setTextSize(18);
                a1.setTextColor(Color.WHITE);
                a1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a1.setText("Machine");
                a1.setWidth(380);
                trow1.addView(a1);

                TextView a2=new TextView(DateWiseProductionReportActivity.this);
                a2.setTextSize(18);
                a2.setTextColor(Color.WHITE);
                a2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a2.setText("Eff");
                a2.setWidth(150);
                trow1.addView(a2);

                TextView a3=new TextView(DateWiseProductionReportActivity.this);
                a3.setTextSize(18);
                a3.setTextColor(Color.WHITE);
                a3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a3.setText("P.Eff");
                a3.setWidth(150);
                trow1.addView(a3);

                TextView a4=new TextView(DateWiseProductionReportActivity.this);
                a4.setTextSize(18);
                a4.setTextColor(Color.WHITE);
                a4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a4.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a4.setText("A.Revol");
                a4.setWidth(250);
                trow1.addView(a4);

                TextView a5=new TextView(DateWiseProductionReportActivity.this);
                a5.setTextSize(18);
                a5.setTextColor(Color.WHITE);
                a5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a5.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a5.setText("Kg");
                a5.setWidth(250);
                trow1.addView(a5);

                TextView a6=new TextView(DateWiseProductionReportActivity.this);
                a6.setTextSize(18);
                a6.setTextColor(Color.WHITE);
                a6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a6.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a6.setText("Rpm");
                a6.setWidth(150);
                trow1.addView(a6);

                TextView a7=new TextView(DateWiseProductionReportActivity.this);
                a7.setTextSize(18);
                a7.setTextColor(Color.WHITE);
                a7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a7.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a7.setText("Storage");
                a7.setWidth(250);
                trow1.addView(a7);

                TextView a8=new TextView(DateWiseProductionReportActivity.this);
                a8.setTextSize(18);
                a8.setTextColor(Color.WHITE);
                a8.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a8.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a8.setText("ST.Time");
                a8.setWidth(250);
                trow1.addView(a8);

                TextView a9=new TextView(DateWiseProductionReportActivity.this);
                a9.setTextSize(18);
                a9.setTextColor(Color.WHITE);
                a9.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a9.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a9.setText("Lycra");
                a9.setWidth(170);
                trow1.addView(a9);

                TextView a10=new TextView(DateWiseProductionReportActivity.this);
                a10.setTextSize(18);
                a10.setTextColor(Color.WHITE);
                a10.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a10.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a10.setText("Ly.Time");
                a10.setWidth(250);
                trow1.addView(a10);

                TextView a11=new TextView(DateWiseProductionReportActivity.this);
                a11.setTextSize(18);
                a11.setTextColor(Color.WHITE);
                a11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a11.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a11.setText("S.Stop");
                a11.setWidth(200);
                trow1.addView(a11);

                TextView a12=new TextView(DateWiseProductionReportActivity.this);
                a12.setTextSize(18);
                a12.setTextColor(Color.WHITE);
                a12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a12.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a12.setText("S.S.Time");
                a12.setWidth(250);
                trow1.addView(a12);

                TextView a13=new TextView(DateWiseProductionReportActivity.this);
                a13.setTextSize(18);
                a13.setTextColor(Color.WHITE);
                a13.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a13.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a13.setText("L.Stop");
                a13.setWidth(200);
                trow1.addView(a13);

                TextView a14=new TextView(DateWiseProductionReportActivity.this);
                a14.setTextSize(18);
                a14.setTextColor(Color.WHITE);
                a14.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a14.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a14.setText("L.S.Time");
                a14.setWidth(250);
                trow1.addView(a14);

                TextView a15=new TextView(DateWiseProductionReportActivity.this);
                a15.setTextSize(18);
                a15.setTextColor(Color.WHITE);
                a15.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a15.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a15.setText("O.Stop");
                a15.setWidth(200);
                trow1.addView(a15);

                TextView a16=new TextView(DateWiseProductionReportActivity.this);
                a16.setTextSize(18);
                a16.setTextColor(Color.WHITE);
                a16.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a16.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a16.setText("O.S.Time");
                a16.setWidth(250);
                trow1.addView(a16);

                TextView a17=new TextView(DateWiseProductionReportActivity.this);
                a17.setTextSize(18);
                a17.setTextColor(Color.WHITE);
                a17.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                a17.setBackgroundDrawable(getResources().getDrawable(R.drawable.border3));
                a17.setText("Run.Time");
                a17.setWidth(250);
                trow1.addView(a17);

                trow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape111));
                tableLayout.addView(trow1);

                final View vline11 = new View(DateWiseProductionReportActivity.this);
                vline11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                vline11.setBackgroundColor(Color.BLUE);
                tableLayout.addView(vline11);


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

                    trow=new TableRow(DateWiseProductionReportActivity.this);
                    trow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    trow.setId(i);



                     TextView b1=new TextView(DateWiseProductionReportActivity.this);
                    b1.setTextSize(18);
                    b1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b1.setText(Html.fromHtml(str1));
                    b1.setWidth(135);
                    trow.addView(b1);

                     TextView b2=new TextView(DateWiseProductionReportActivity.this);
                    b2.setTextSize(18);
                    b2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b2.setText(Html.fromHtml(str2));
                    b2.setWidth(100);
                    trow.addView(b2);

                     TextView b3=new TextView(DateWiseProductionReportActivity.this);
                    b3.setTextSize(18);
                    b3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b3.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b3.setText(Html.fromHtml(str3));
                    b3.setWidth(100);
                    trow.addView(b3);


                     TextView b4=new TextView(DateWiseProductionReportActivity.this);
                    b4.setTextSize(18);
                    b4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b4.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b4.setText(Html.fromHtml(str4));
                    b4.setWidth(100);
                    trow.addView(b4);

                     TextView b5=new TextView(DateWiseProductionReportActivity.this);
                    b5.setTextSize(18);
                    b5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b5.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b5.setText(Html.fromHtml(str5));
                    b5.setWidth(100);
                    trow.addView(b5);

                    final TextView b6=new TextView(DateWiseProductionReportActivity.this);
                    b6.setTextSize(18);
                    b6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b6.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b6.setText(Html.fromHtml(str6));
                    b6.setWidth(100);
                    trow.addView(b6);

                     TextView b7=new TextView(DateWiseProductionReportActivity.this);
                    b7.setTextSize(18);
                    b7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b7.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b7.setText(Html.fromHtml(str7));
                    b7.setWidth(100);
                    trow.addView(b7);


                     TextView b8=new TextView(DateWiseProductionReportActivity.this);
                    b8.setTextSize(18);
                    b8.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b8.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b8.setText(Html.fromHtml(str8));
                    b8.setWidth(120);
                    trow.addView(b8);

                    final TextView b9=new TextView(DateWiseProductionReportActivity.this);
                    b9.setTextSize(18);
                    b9.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b9.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b9.setText(Html.fromHtml(str9));
                    b9.setWidth(100);
                    trow.addView(b9);

                     TextView b10=new TextView(DateWiseProductionReportActivity.this);
                    b10.setTextSize(18);
                    b10.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b10.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b10.setText(Html.fromHtml(str10));
                    b10.setWidth(120);
                    trow.addView(b10);

                     TextView b11=new TextView(DateWiseProductionReportActivity.this);
                    b11.setTextSize(18);
                    b11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b11.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b11.setText(Html.fromHtml(str11));
                    b11.setWidth(100);
                    trow.addView(b11);

                     TextView b12=new TextView(DateWiseProductionReportActivity.this);
                    b12.setTextSize(18);
                    b12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b12.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b12.setText(Html.fromHtml(str12));
                    b12.setWidth(120);
                    trow.addView(b12);

                     TextView b13=new TextView(DateWiseProductionReportActivity.this);
                    b13.setTextSize(18);
                    b13.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b13.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b13.setText(Html.fromHtml(str13));
                    b13.setWidth(100);
                    trow.addView(b13);

                     TextView b14=new TextView(DateWiseProductionReportActivity.this);
                    b14.setTextSize(18);
                    b14.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b14.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b14.setText(Html.fromHtml(str14));
                    b14.setWidth(120);
                    trow.addView(b14);

                     TextView b15=new TextView(DateWiseProductionReportActivity.this);
                    b15.setTextSize(18);
                    b15.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b15.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b15.setText(Html.fromHtml(str15));
                    b15.setWidth(100);
                    trow.addView(b15);

                     TextView b16=new TextView(DateWiseProductionReportActivity.this);
                    b16.setTextSize(18);
                    b16.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b16.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b16.setText(Html.fromHtml(str16));
                    b16.setWidth(120);
                    trow.addView(b16);

                     TextView b17=new TextView(DateWiseProductionReportActivity.this);
                    b17.setTextSize(18);
                    b17.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b17.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    b17.setText(Html.fromHtml(str17));
                    b17.setWidth(120);
                    trow.addView(b17);


                    trow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape111));
                    tableLayout.addView(trow);

                    final View vline1 = new View(DateWiseProductionReportActivity.this);
                    vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                    vline1.setBackgroundColor(Color.BLUE);
                    tableLayout.addView(vline1);

                    trow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String e=String.valueOf(machine_name);
                            Toast.makeText(getApplicationContext(), "Machine Name:"+e, Toast.LENGTH_SHORT).show();
                        }
                    });
                    i=i+1;


                }
                tableLayout.invalidate();
                tableLayout.refreshDrawableState();
                progressdialog.dismiss();

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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DateWiseProductionReportActivity.this);
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
                progressdialog.dismiss();
            }
        });
    }
    public  void exportcsv(){
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir,edit_csv_name.getText().toString() );
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            int Machine_length=strArr.length;
            int i=0;
            while(i<Machine_length) {
                jsonArray = new JSONArray(input);
                strArr = new String[jsonArray.length()+1];
                for ( i = 0; i < jsonArray.length()+1; i++) {
                    try {
                        strArr[i+1] = jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr[0]="Machine";
                }
                jsonArray1 = new JSONArray(input1);
                strArr1 = new String[jsonArray1.length()+1];
                for ( i = 0; i < jsonArray1.length()+1; i++) {
                    try {
                        strArr1[i+1] = jsonArray1.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr1[0]="A.EFF %";
                }
                jsonArray2 = new JSONArray(input2);
                strArr2 = new String[jsonArray2.length()+1];
                for ( i = 0; i < jsonArray2.length()+1; i++) {
                    try {
                        strArr2[i+1] = jsonArray2.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr2[0]="P.EFF %";
                }
                jsonArray3 = new JSONArray(input3);
                strArr3 = new String[jsonArray3.length()+1];
                for ( i = 0; i < jsonArray3.length()+1; i++) {
                    try {
                        strArr3[i+1] = jsonArray3.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr3[0]="K.Picks";
                }
                jsonArray4 = new JSONArray(input4);
                strArr4 = new String[jsonArray4.length()+1];
                for ( i = 0; i < jsonArray4.length()+1; i++) {
                    try {
                        strArr4[i+1] = jsonArray4.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr4[0]="Meter";
                }
                jsonArray5 = new JSONArray(input5);
                strArr5 = new String[jsonArray5.length()+1];
                for ( i = 0; i < jsonArray5.length()+1; i++) {
                    try {
                        strArr5[i+1] = jsonArray5.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr5[0]="Rpm";
                }
                jsonArray6 = new JSONArray(input6);
                strArr6 = new String[jsonArray6.length()+1];
                for ( i = 0; i < jsonArray6.length()+1; i++) {
                    try {
                        strArr6[i+1] = jsonArray6.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr6[0]="Warp";
                }
                jsonArray7 = new JSONArray(input7);
                strArr7 = new String[jsonArray7.length()+1];
                for ( i = 0; i < jsonArray7.length()+1; i++) {
                    try {
                        strArr7[i+1] = jsonArray7.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr7[0]="wp.Time";
                }
                jsonArray8 = new JSONArray(input8);
                strArr8 = new String[jsonArray8.length()+1];
                for ( i = 0; i < jsonArray8.length()+1; i++) {
                    try {
                        strArr8[i+1] = jsonArray8.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr8[0]="Weft";
                }
                jsonArray9 = new JSONArray(input9);
                strArr9 = new String[jsonArray9.length()+1];
                for ( i = 0; i < jsonArray9.length()+1; i++) {
                    try {
                        strArr9[i+1] = jsonArray9.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr9[0]="wt.Time";
                }
                jsonArray10 = new JSONArray(input10);
                strArr10 = new String[jsonArray10.length()+1];
                for ( i = 0; i < jsonArray10.length()+1; i++) {
                    try {
                        strArr10[i+1] = jsonArray10.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr10[0]="S.Stop";
                }
                jsonArray11 = new JSONArray(input11);
                strArr11 = new String[jsonArray11.length()+1];
                for ( i = 0; i < jsonArray11.length()+1; i++) {
                    try {
                        strArr11[i+1] = jsonArray11.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr11[0]="S.S.Time";
                }
                jsonArray12 = new JSONArray(input12);
                strArr12 = new String[jsonArray12.length()+1];
                for ( i = 0; i < jsonArray12.length()+1; i++) {
                    try {
                        strArr12[i+1] = jsonArray12.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr12[0]="L.Stop";
                }
                jsonArray13 = new JSONArray(input13);
                strArr13 = new String[jsonArray13.length()+1];
                for ( i = 0; i < jsonArray13.length()+1; i++) {
                    try {
                        strArr13[i+1] = jsonArray13.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr13[0]="L.S.Time";
                }
                jsonArray14 = new JSONArray(input14);
                strArr14 = new String[jsonArray14.length()+1];
                for ( i = 0; i < jsonArray14.length()+1; i++) {
                    try {
                        strArr14[i+1] = jsonArray14.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr14[0]="O.Stop";
                }
                jsonArray15 = new JSONArray(input15);
                strArr15 = new String[jsonArray15.length()+1];
                for ( i = 0; i < jsonArray15.length()+1; i++) {
                    try {
                        strArr15[i+1] = jsonArray15.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr15[0]="O.S.Time";
                }
                jsonArray16 = new JSONArray(input16);
                strArr16 = new String[jsonArray16.length()+1];
                for ( i = 0; i < jsonArray16.length()+1; i++) {
                    try {
                        strArr16[i+1] = jsonArray16.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    strArr16[0]="Run.Time";
                    String arrStr[] = {strArr[i],strArr1[i],strArr2[i],strArr3[i],strArr4[i],strArr5[i],strArr6[i],
                            strArr7[i],strArr8[i],strArr9[i],strArr10[i],strArr11[i],strArr12[i],strArr13[i],
                            strArr14[i],strArr15[i],strArr16[i]};

                    csvWrite.writeNext(arrStr);
                }

            }
            Toast.makeText(this, "Successfully saved to csv file....", Toast.LENGTH_SHORT).show();
            csvWrite.close();
        }
        catch (Exception sqlEx) {
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}