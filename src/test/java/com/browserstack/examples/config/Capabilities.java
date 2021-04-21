package com.browserstack.examples.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Data
@ToString
public class Capabilities {

    private Map<String, Object> capabilityMap = new LinkedHashMap<>();

    @JsonAnySetter
    public void setCapabilities(String key, Object value) {
        this.capabilityMap.put(key, value);
    }

    public Object capability(String key) {
        return this.capabilityMap.get(key);
    }
}

