package com.dynatrace.ingest.model;

public class Version {
    private String serviceId;
    private String ver;
    private String date;
    private String status;
    private String message;

    public Version() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Version(String serviceId, String ver, String date, String status, String message) {
        this.serviceId = serviceId;
        this.ver = ver;
        this.date = date;
        this.status = status;
        this.message = message;
    }

    public Version(String serviceId, String message) {
        this.serviceId = serviceId;
        this.message   = message;
        this.ver = "N/A";
        this.date = "N/A";
        this.status = "N/A";
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMessageEmpty() {
        return this.message == null || this.message.isEmpty();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
