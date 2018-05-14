package com.lakshmiindustrialautomation.www.lit;

import java.util.HashMap;
import java.util.Map;

class LiveStatusResponse {
    String success;
    String totalrunning;
    String totalstop;
    String longstop;
    String others;
    String warp;
    String weft;
    Float cur_shift_eff;
    String cur_shift_kpx;
    Float cur_shift_cmpx;
    String cur_shift_mtr;
    Float cur_shift_kmtr;
    Float previous_shift_eff;
    String previous_shift_kpx;
    Float previous_shift_cmpx;
    String previous_shift_mtr;
    Float previous_shift_kmtr;
    Float previous_day_eff;
    String previous_day_kpx;
    Float previous_day_cmpx;
    String previous_day_mtr;
    Float previous_day_kmtr;
    Float cur_month_eff;
    String cur_month_kpx;
    Float cur_month_cmpx;
    String cur_month_mtr;
    Float cur_month_kmtr;
    Float cur_shift_peff;
    String cur_shift_rpm;
    String previous_shift_rpm;
    Float previous_shift_peff;
    String previous_day_rpm;
    Float previous_day_peff;
    String cur_month_rpm;

    public Float getCur_shift_kmtr() {
        return cur_shift_kmtr;
    }

    public void setCur_shift_kmtr(Float cur_shift_kmtr) {
        this.cur_shift_kmtr = cur_shift_kmtr;
    }

    public Float getPrevious_shift_kmtr() {
        return previous_shift_kmtr;
    }

    public void setPrevious_shift_kmtr(Float previous_shift_kmtr) {
        this.previous_shift_kmtr = previous_shift_kmtr;
    }

    public Float getPrevious_day_kmtr() {
        return previous_day_kmtr;
    }

    public void setPrevious_day_kmtr(Float previous_day_kmtr) {
        this.previous_day_kmtr = previous_day_kmtr;
    }

    public Float getCur_month_kmtr() {
        return cur_month_kmtr;
    }

    public void setCur_month_kmtr(Float cur_month_kmtr) {
        this.cur_month_kmtr = cur_month_kmtr;
    }



    public String getSuccess() {
        return success;
    }

    public String getTotalrunning() {
        return totalrunning;
    }

    public void setTotalrunning(String totalrunning) {
        this.totalrunning = totalrunning;
    }

    public String getTotalstop() {
        return totalstop;
    }

    public void setTotalstop(String totalstop) {
        this.totalstop = totalstop;
    }

    public String getLongstop() {
        return longstop;
    }

    public void setLongstop(String longstop) {
        this.longstop = longstop;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getWarp() {
        return warp;
    }

    public void setWarp(String warp) {
        this.warp = warp;
    }

    public String getWeft() {
        return weft;
    }

    public void setWeft(String weft) {
        this.weft = weft;
    }

    public Float getCur_shift_eff() {
        return cur_shift_eff;
    }

    public void setCur_shift_eff(Float cur_shift_eff) {
        this.cur_shift_eff = cur_shift_eff;
    }

    public String getCur_shift_kpx() {
        return cur_shift_kpx;
    }

    public void setCur_shift_kpx(String cur_shift_kpx) {
        this.cur_shift_kpx = cur_shift_kpx;
    }

    public Float getCur_shift_cmpx() {
        return cur_shift_cmpx;
    }

    public void setCur_shift_cmpx(Float cur_shift_cmpx) {
        this.cur_shift_cmpx = cur_shift_cmpx;
    }

    public String getCur_shift_mtr() {
        return cur_shift_mtr;
    }

    public void setCur_shift_mtr(String cur_shift_mtr) {
        this.cur_shift_mtr = cur_shift_mtr;
    }

    public Float getPrevious_shift_eff() {
        return previous_shift_eff;
    }

    public void setPrevious_shift_eff(Float previous_shift_eff) {
        this.previous_shift_eff = previous_shift_eff;
    }

    public String getPrevious_shift_kpx() {
        return previous_shift_kpx;
    }

    public void setPrevious_shift_kpx(String previous_shift_kpx) {
        this.previous_shift_kpx = previous_shift_kpx;
    }

    public Float getPrevious_shift_cmpx() {
        return previous_shift_cmpx;
    }

    public void setPrevious_shift_cmpx(Float previous_shift_cmpx) {
        this.previous_shift_cmpx = previous_shift_cmpx;
    }

    public String getPrevious_shift_mtr() {
        return previous_shift_mtr;
    }

    public void setPrevious_shift_mtr(String previous_shift_mtr) {
        this.previous_shift_mtr = previous_shift_mtr;
    }

    public Float getPrevious_day_eff() {
        return previous_day_eff;
    }

    public void setPrevious_day_eff(Float previous_day_eff) {
        this.previous_day_eff = previous_day_eff;
    }

    public String getPrevious_day_kpx() {
        return previous_day_kpx;
    }

    public void setPrevious_day_kpx(String previous_day_kpx) {
        this.previous_day_kpx = previous_day_kpx;
    }

    public Float getPrevious_day_cmpx() {
        return previous_day_cmpx;
    }

    public void setPrevious_day_cmpx(Float previous_day_cmpx) {
        this.previous_day_cmpx = previous_day_cmpx;
    }

    public String getPrevious_day_mtr() {
        return previous_day_mtr;
    }

    public void setPrevious_day_mtr(String previous_day_mtr) {
        this.previous_day_mtr = previous_day_mtr;
    }

    public Float getCur_month_eff() {
        return cur_month_eff;
    }

    public void setCur_month_eff(Float cur_month_eff) {
        this.cur_month_eff = cur_month_eff;
    }

    public String getCur_month_kpx() {
        return cur_month_kpx;
    }

    public void setCur_month_kpx(String cur_month_kpx) {
        this.cur_month_kpx = cur_month_kpx;
    }

    public Float getCur_month_cmpx() {
        return cur_month_cmpx;
    }

    public void setCur_month_cmpx(Float cur_month_cmpx) {
        this.cur_month_cmpx = cur_month_cmpx;
    }

    public String getCur_month_mtr() {
        return cur_month_mtr;
    }

    public void setCur_month_mtr(String cur_month_mtr) {
        this.cur_month_mtr = cur_month_mtr;
    }

    public Float getCur_shift_peff() {
        return cur_shift_peff;
    }

    public void setCur_shift_peff(Float cur_shift_peff) {
        this.cur_shift_peff = cur_shift_peff;
    }

    public String getCur_shift_rpm() {
        return cur_shift_rpm;
    }

    public void setCur_shift_rpm(String cur_shift_rpm) {
        this.cur_shift_rpm = cur_shift_rpm;
    }

    public String getPrevious_shift_rpm() {
        return previous_shift_rpm;
    }

    public void setPrevious_shift_rpm(String previous_shift_rpm) {
        this.previous_shift_rpm = previous_shift_rpm;
    }

    public Float getPrevious_shift_peff() {
        return previous_shift_peff;
    }

    public void setPrevious_shift_peff(Float previous_shift_peff) {
        this.previous_shift_peff = previous_shift_peff;
    }

    public String getPrevious_day_rpm() {
        return previous_day_rpm;
    }

    public void setPrevious_day_rpm(String previous_day_rpm) {
        this.previous_day_rpm = previous_day_rpm;
    }

    public Float getPrevious_day_peff() {
        return previous_day_peff;
    }

    public void setPrevious_day_peff(Float previous_day_peff) {
        this.previous_day_peff = previous_day_peff;
    }

    public String getCur_month_rpm() {
        return cur_month_rpm;
    }

    public void setCur_month_rpm(String cur_month_rpm) {
        this.cur_month_rpm = cur_month_rpm;
    }

    public Float getCur_month_peff() {
        return cur_month_peff;
    }

    public void setCur_month_peff(Float cur_month_peff) {
        this.cur_month_peff = cur_month_peff;
    }

    Float cur_month_peff;


    public void setSuccess(String success) {
        this.success = success;
    }
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {this.additionalProperties = additionalProperties;}

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
