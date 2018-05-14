package com.lakshmiindustrialautomation.www.lit.stop_chart;

import java.util.ArrayList;

/**
 * Created by MATHI on 5/30/2017.
 */

public class StopChartResponse {
    ArrayList stop_name;
    ArrayList noofstops;
    ArrayList stop_duration;

    public ArrayList getStop_duration() {
        return stop_duration;
    }

    public void setStop_duration(ArrayList stop_duration) {
        this.stop_duration = stop_duration;
    }



    public ArrayList getStop_name() {
        return stop_name;
    }

    public void setStop_name(ArrayList stop_name) {
        this.stop_name = stop_name;
    }

    public ArrayList getNoofstops() {
        return noofstops;
    }

    public void setNoofstops(ArrayList noofstops) {
        this.noofstops = noofstops;
    }


}
