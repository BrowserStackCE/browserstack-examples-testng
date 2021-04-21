package com.browserstack.examples.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Data
@ToString
public class RemoteDriverConfig {

    private String hubUrl;

    private String user;

    private String accessKey;

    @JsonProperty("common_capabilities")
    private CommonCapabilities commonCapabilities;

    private LocalTunnelConfig localTunnel;

    private List<Platform> platforms;
}
