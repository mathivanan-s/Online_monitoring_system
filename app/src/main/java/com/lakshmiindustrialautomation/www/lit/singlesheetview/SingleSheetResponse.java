package com.lakshmiindustrialautomation.www.lit.singlesheetview;

import java.util.ArrayList;

/**
 * Created by MATHI on 8/22/2017.
 */

public class SingleSheetResponse {
    public String getShift_count() {
        return shift_count;
    }

    public void setShift_count(String shift_count) {
        this.shift_count = shift_count;
    }

    String shift_count;
    ArrayList shift;
    ArrayList status;
    ArrayList stop_code;
    ArrayList machine_name;
    ArrayList eff;
    ArrayList peff;
    ArrayList warp;
    ArrayList warp_time;
    ArrayList weft;
    ArrayList weft_time;
    ArrayList rpm;
    ArrayList fab_design;
    public ArrayList getShift() {
        return shift;
    }

    public void setShift(ArrayList shift) {
        this.shift = shift;
    }
    public ArrayList getStatus() {
        return status;
    }

    public void setStatus(ArrayList status) {
        this.status = status;
    }

    public ArrayList getStop_code() {
        return stop_code;
    }

    public void setStop_code(ArrayList stop_code) {
        this.stop_code = stop_code;
    }

    public ArrayList getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(ArrayList machine_name) {
        this.machine_name = machine_name;
    }

    public ArrayList getEff() {
        return eff;
    }

    public void setEff(ArrayList eff) {
        this.eff = eff;
    }

    public ArrayList getPeff() {
        return peff;
    }

    public void setPeff(ArrayList peff) {
        this.peff = peff;
    }

    public ArrayList getWarp() {
        return warp;
    }

    public void setWarp(ArrayList warp) {
        this.warp = warp;
    }

    public ArrayList getWarp_time() {
        return warp_time;
    }

    public void setWarp_time(ArrayList warp_time) {
        this.warp_time = warp_time;
    }

    public ArrayList getWeft() {
        return weft;
    }

    public void setWeft(ArrayList weft) {
        this.weft = weft;
    }

    public ArrayList getWeft_time() {
        return weft_time;
    }

    public void setWeft_time(ArrayList weft_time) {
        this.weft_time = weft_time;
    }

    public ArrayList getRpm() {
        return rpm;
    }

    public void setRpm(ArrayList rpm) {
        this.rpm = rpm;
    }

    public ArrayList getFab_design() {
        return fab_design;
    }

    public void setFab_design(ArrayList fab_design) {
        this.fab_design = fab_design;
    }

    public ArrayList getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(ArrayList operator_name) {
        this.operator_name = operator_name;
    }

    ArrayList operator_name;

}
