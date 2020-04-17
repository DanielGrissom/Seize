package com.dgrissom.seize.client;

public enum ConnectResult {
    SUCCESS("Connection successful."),
    UNKNOWN_HOST("Unknown host!"),
    FAILED("Couldn't connect to the host!");

    private final String statusText;

    ConnectResult(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return this.statusText;
    }
}
