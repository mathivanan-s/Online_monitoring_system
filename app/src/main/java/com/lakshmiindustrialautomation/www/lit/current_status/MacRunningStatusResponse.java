package com.lakshmiindustrialautomation.www.lit.current_status;

import java.util.ArrayList;

/**
 * Created by MATHI on 5/24/2017.
 */

public class MacRunningStatusResponse {

    ArrayList r_machine_name;
    ArrayList r_eff;
    ArrayList r_peff;
    ArrayList r_picks;
    ArrayList r_meter;

    public ArrayList getR_peff() {
        return r_peff;
    }

    public void setR_peff(ArrayList r_peff) {
        this.r_peff = r_peff;
    }




    public ArrayList getR_machine_name() {
        return r_machine_name;
    }

    public void setR_machine_name(ArrayList r_machine_name) {
        this.r_machine_name = r_machine_name;
    }

    public ArrayList getR_eff() {
        return r_eff;
    }

    public void setR_eff(ArrayList r_eff) {
        this.r_eff = r_eff;
    }

    public ArrayList getR_picks() {
        return r_picks;
    }

    public void setR_picks(ArrayList r_picks) {
        this.r_picks = r_picks;
    }

    public ArrayList getR_meter() {
        return r_meter;
    }

    public void setR_meter(ArrayList r_meter) {
        this.r_meter = r_meter;
    }


}
