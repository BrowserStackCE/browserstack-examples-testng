package com.browserstack.examples.core.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalTunnelConfig {

    @JsonProperty("local_options")
    private final Map<String, String> localOptions = new LinkedHashMap<>();
    private Boolean enabled;

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enable) {
        this.enabled = enable;
    }

    public Map<String, String> getLocalOptions() {
        return localOptions;
    }

    @JsonAnySetter
    public void setLocalOption(String key, String value) {
        this.localOptions.put(key, value);
    }

}
