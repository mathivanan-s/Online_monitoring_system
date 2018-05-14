package com.lakshmiindustrialautomation.www.lit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android on 5/2/2017.
 */

public class AuthenticationResponse {
    private String success;
    private String user;
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


}
