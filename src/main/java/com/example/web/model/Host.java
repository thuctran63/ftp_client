package com.example.web.model;

public class Host {
    private String ipHost;
    private String port;

    public Host() {
    }

    public Host(String ipHost, String port) {
        this.ipHost = ipHost;
        this.port = port;
    }

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    

}
