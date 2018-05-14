package com.lakshmiindustrialautomation.www.lit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steephan Selvaraj on 5/7/2017.
 */

public class DeviceRegisterResponse {
    private String deviceId;
    private String email;
    private String token;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
