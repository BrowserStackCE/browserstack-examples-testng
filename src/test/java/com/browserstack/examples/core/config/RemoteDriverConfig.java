package com.browserstack.examples.core.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteDriverConfig {

    private String hubUrl;

    private String user;

    private String accessKey;

    @JsonProperty("common_capabilities")
    private CommonCapabilities commonCapabilities;

    private LocalTunnelConfig localTunnel;

    private List<Platform> platforms;

    public String getHubUrl() {
        return hubUrl;
    }

    public void setHubUrl(String hubUrl) {
        this.hubUrl = hubUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public CommonCapabilities getCommonCapabilities() {
        return commonCapabilities;
    }

    public void setCommonCapabilities(CommonCapabilities commonCapabilities) {
        this.commonCapabilities = commonCapabilities;
    }

    public LocalTunnelConfig getLocalTunnel() {
        return localTunnel;
    }

    public void setLocalTunnel(LocalTunnelConfig localTunnel) {
        this.localTunnel = localTunnel;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }
}
