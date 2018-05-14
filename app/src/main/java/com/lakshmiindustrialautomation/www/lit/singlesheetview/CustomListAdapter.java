package com.lakshmiindustrialautomation.www.lit.singlesheetview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.Utilities;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] strArr;
    private final String[]  strArr1;
    private final String[]  strArr2;
    private final String[]  strArr3;
    private final String[]  strArr4;
    private final String[]  strArr5;
    private final String[]  strArr6;
    private final String[]  strArr7;
    private final String[]  strArr8;
    private final String[]  strArr9;
    private final String[]  strArr10;
    private final String[]  strArr11;
    ImageView status_image,stop_status_image;
    RelativeLayout single_sheet;
    TextView warp,weft,rpm,machine,aeff,peff,op_name,design;


    public CustomListAdapter(Activity context,String[] strArr, String[] strArr1,
                             String[] strArr2, String[] strArr3,String[] strArr4,String[] strArr5,String[] strArr6,String[] strArr7,
                             String[] strArr8,String[] strArr9,String[] strArr10,String[] strArr11)
    {

        super(context, R.layout.single_sheet_view_box_list, strArr);

        this.context=context;
        this.strArr=strArr;
        this.strArr1=strArr1;
        this.strArr2=strArr2;
        this.strArr3=strArr3;
        this.strArr4=strArr4;
        this.strArr5=strArr5;
        this.strArr6=strArr6;
        this.strArr7=strArr7;
        this.strArr8=strArr8;
        this.strArr9=strArr9;
        this.strArr10=strArr10;
        this.strArr11=strArr11;

    }
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.single_sheet_view_box_list, null,true);

        single_sheet=(RelativeLayout)rowView.findViewById(R.id.singlesheet_view);

         machine = (TextView) rowView.findViewById(R.id.machine_name);
         aeff = (TextView) rowView.findViewById(R.id.aeff);
         peff = (TextView) rowView.findViewById(R.id.peff);
        op_name = (TextView) rowView.findViewById(R.id.op);
        design = (TextView) rowView.findViewById(R.id.design);

        rpm = (TextView) rowView.findViewById(R.id.rpm);
        warp = (TextView) rowView.findViewById(R.id.warp);
        weft = (TextView) rowView.findViewById(R.id.weft);
        status_image=(ImageView)rowView.findViewById(R.id.stop);
        stop_status_image=(ImageView)rowView.findViewById(R.id.stop_status);

        machine.setText(strArr2[position]);
        aeff.setText(strArr3[position]+"%");
        peff.setText(strArr4[position]+"%");
        warp.setText("ST:"+strArr5[position]+"/"+strArr6[position]);
        weft.setText("LY:"+strArr7[position]+"/"+strArr8[position]);
        rpm.setText("RPM:"+strArr9[position]);
        op_name.setText(strArr11[position]);
        design.setText(strArr10[position]);

        setImagestopStateImage(strArr1[position],strArr[position]);
        setbackgroundcolor(strArr3[position]);
        setImageStateImage(strArr[position]);


        return rowView;

    };

    private void setImageStateImage(String state){
        if(state.equals("0")){
            status_image.setImageResource(R.drawable.stop);
        } else {
            status_image.setImageResource(R.drawable.play);
        }
    }

    private void setImagestopStateImage(String stop_state,String state){
        if((stop_state.equals("3"))&&(state.equals("0"))){
            warp.setVisibility(View.INVISIBLE);
            weft.setVisibility(View.INVISIBLE);
            rpm.setVisibility(View.INVISIBLE);
            stop_status_image.setImageResource(R.drawable.lycra);
        } else if((stop_state.equals("2"))&&(state.equals("0"))) {
            warp.setVisibility(View.INVISIBLE);
            weft.setVisibility(View.INVISIBLE);
            rpm.setVisibility(View.INVISIBLE);
            stop_status_image.setImageResource(R.drawable.storage);
        }
        else{
            warp.setVisibility(View.VISIBLE);
            weft.setVisibility(View.VISIBLE);
            rpm.setVisibility(View.VISIBLE);
        }
    }

    private void setbackgroundcolor(String eff){
        if((Float.valueOf(eff) >= 30.1 )&&(Float.valueOf(eff) <= 50.0)){
            single_sheet.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(context,1)));
        }
        else if((Float.valueOf(eff) >= 50.1 )&&(Float.valueOf(eff) <= 60.0)){
            single_sheet.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(context,2)));
        }
        else if((Float.valueOf(eff) >= 60.1 )&&(Float.valueOf(eff) <= 70.0)){
            single_sheet.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(context,3)));
        }
        else if((Float.valueOf(eff) >= 70.1 )&&(Float.valueOf(eff) <= 80.0)){
            single_sheet.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(context,4)));
        }
        else if((Float.valueOf(eff) >= 80.1 )&&(Float.valueOf(eff) <= 90.0)){
            single_sheet.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(context,5)));
        }
        else if((Float.valueOf(eff) >= 90.1 )&&(Float.valueOf(eff) <= 100.0)){
            single_sheet.setBackgroundColor(Integer.parseInt(Utilities.getSelectedColor(context,6)));
        }
    }

}
