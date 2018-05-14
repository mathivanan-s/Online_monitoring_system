package com.lakshmiindustrialautomation.www.lit.year_chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MATHI on 5/19/2017.
 */

public class YearChartDetailResponse {
    ArrayList eff;
    ArrayList Xaxis;
    ArrayList picks;
    ArrayList meter;
    ArrayList p_eff;

    public ArrayList getP_eff() {
        return p_eff;
    }

    public void setP_eff(ArrayList p_eff) {
        this.p_eff = p_eff;
    }



    public ArrayList getPicks() {
        return picks;
    }

    public void setPicks(ArrayList picks) {
        this.picks = picks;
    }

    public ArrayList getMeter() {
        return meter;
    }

    public void setMeter(ArrayList meter) {
        this.meter = meter;
    }



    public ArrayList getXaxis() {
        return Xaxis;
    }

    public void setXaxis(ArrayList xaxis) {
        Xaxis = xaxis;
    }


    public ArrayList getEff() {
        return eff;
    }

    public void setEff(ArrayList eff) {
        this.eff = eff;
    }





    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {this.additionalProperties = additionalProperties;}

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
