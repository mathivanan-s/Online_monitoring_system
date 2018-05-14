package com.lakshmiindustrialautomation.www.lit.Adapters;

import android.content.Intent;

/**
 * Created by Steephan Selvaraj on 5/3/2017.
 */

public class NormalAlert {
    String title, body;
    Integer id;

    public NormalAlert(String title, String body, Integer id) {
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
