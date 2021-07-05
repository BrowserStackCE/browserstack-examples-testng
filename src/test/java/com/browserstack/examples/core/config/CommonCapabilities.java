package com.browserstack.examples.core.config;

public class CommonCapabilities {

    private String project;

    private String buildPrefix;

    private Capabilities capabilities;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBuildPrefix() {
        return buildPrefix;
    }

    public void setBuildPrefix(String buildPrefix) {
        this.buildPrefix = buildPrefix;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }
}
