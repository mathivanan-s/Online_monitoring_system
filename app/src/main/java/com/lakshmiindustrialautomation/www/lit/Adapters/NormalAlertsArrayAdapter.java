package com.lakshmiindustrialautomation.www.lit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lakshmiindustrialautomation.www.lit.R;

import java.util.ArrayList;

/**
 * Created by Steephan Selvaraj on 5/3/2017.
 */

public class NormalAlertsArrayAdapter extends ArrayAdapter<NormalAlert> {

    public NormalAlertsArrayAdapter(Context context, ArrayList<NormalAlert> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NormalAlert normalAlert = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alert_row_layout, parent, false);
        }

        // Lookup view for data population
        TextView t_title = (TextView) convertView.findViewById(R.id.alert_title);
        TextView t_body = (TextView) convertView.findViewById(R.id.alert_body);

        // Populate the data into the template view using the data object
        t_title.setText(normalAlert.title);
        t_body.setText(normalAlert.body);

        // Return the completed view to render on screen
        return convertView;
    }
}
