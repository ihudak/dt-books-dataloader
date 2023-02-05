package com.dynatrace.ingest.model;

public class Config {
    private String id;
    private String serviceId;
    private long loadCPU;
    private long loadRAM;
    private double probabilityFailure;
    private String propertyStr;

    private boolean turnedOn;

    public Config() {
    }

    public Config(String id, String serviceId, long loadCPU, long loadRAM, double probabilityFailure, String propertyStr, boolean turnedOn) {
        this.id = id;
        this.serviceId = serviceId;
        this.loadCPU = loadCPU;
        this.loadRAM = loadRAM;
        this.probabilityFailure = probabilityFailure;
        this.propertyStr = propertyStr;
        this.turnedOn = turnedOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public long getLoadCPU() {
        return loadCPU;
    }

    public void setLoadCPU(long loadCPU) {
        this.loadCPU = loadCPU;
    }

    public long getLoadRAM() {
        return loadRAM;
    }

    public void setLoadRAM(long loadRAM) {
        this.loadRAM = loadRAM;
    }
    public double getProbabilityFailure() {
        return probabilityFailure;
    }

    public void setProbabilityFailure(double propertyDouble) {
        this.probabilityFailure = propertyDouble;
    }

    public String getPropertyStr() {
        return propertyStr;
    }

    public void setPropertyStr(String propertyStr) {
        this.propertyStr = propertyStr;
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean propertyBool) {
        this.turnedOn = propertyBool;
    }
}
