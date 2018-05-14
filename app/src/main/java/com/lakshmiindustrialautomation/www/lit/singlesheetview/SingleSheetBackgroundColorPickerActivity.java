package com.lakshmiindustrialautomation.www.lit.singlesheetview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

/**
 * Created by MATHI on 7/28/2017.
 */

public class SingleSheetBackgroundColorPickerActivity extends Activity implements View.OnClickListener {
    TextView select_color1,select_color2,select_color3,select_color4,select_color5,select_color6;
    Button color_picker1,color_picker2,color_picker3,color_picker4,color_picker5,color_picker6;

    ColorPickerDialog colorPickerDialog;
    int color= Color.parseColor("#FFFFFF");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_activity);

        //color set txt

        select_color1=(TextView)findViewById(R.id.select_color1);
        select_color2=(TextView)findViewById(R.id.select_color2);
        select_color3=(TextView)findViewById(R.id.select_color3);
        select_color4=(TextView)findViewById(R.id.select_color4);
        select_color5=(TextView)findViewById(R.id.select_color5);
        select_color6=(TextView)findViewById(R.id.select_color6);

        select_color1.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(this,1)));
        select_color2.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(this,2)));
        select_color3.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(this,3)));
        select_color4.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(this,4)));
        select_color5.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(this,5)));
        select_color6.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(this,6)));

        //color picker button

        color_picker1=(Button)findViewById(R.id.color_button1);
        color_picker2=(Button)findViewById(R.id.color_button2);
        color_picker3=(Button)findViewById(R.id.color_button3);
        color_picker4=(Button)findViewById(R.id.color_button4);
        color_picker5=(Button)findViewById(R.id.color_button5);
        color_picker6=(Button)findViewById(R.id.color_button6);

        color_picker1.setOnClickListener(this);
        color_picker2.setOnClickListener(this);
        color_picker3.setOnClickListener(this);
        color_picker4.setOnClickListener(this);
        color_picker5.setOnClickListener(this);
        color_picker6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.color_button1) {
            showcolorPickerDialog(1);
        }
        if (id == R.id.color_button2) {
            showcolorPickerDialog(2);
        }
        if (id == R.id.color_button3) {
            showcolorPickerDialog(3);
        }
        if (id == R.id.color_button4) {
            showcolorPickerDialog(4);
        }
        if (id == R.id.color_button5) {
            showcolorPickerDialog(5);
        }
        if (id == R.id.color_button6) {
            showcolorPickerDialog(6);
        }
    }
    private  void showcolorPickerDialog(final int item){

        colorPickerDialog = new ColorPickerDialog(this, color);
        colorPickerDialog.setAlphaSliderVisible(true);
        colorPickerDialog.setHexValueEnabled(true);
        colorPickerDialog.setTitle("Color Picker");
        colorPickerDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int i) {
                color = i;

                Utilities.storeSelectedColor(SingleSheetBackgroundColorPickerActivity.this, String.valueOf(color),item);

                select_color1.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(SingleSheetBackgroundColorPickerActivity.this,1)));
                select_color2.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(SingleSheetBackgroundColorPickerActivity.this,2)));
                select_color3.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(SingleSheetBackgroundColorPickerActivity.this,3)));
                select_color4.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(SingleSheetBackgroundColorPickerActivity.this,4)));
                select_color5.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(SingleSheetBackgroundColorPickerActivity.this,5)));
                select_color6.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(SingleSheetBackgroundColorPickerActivity.this,6)));

            }
        });
        colorPickerDialog.show();

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
