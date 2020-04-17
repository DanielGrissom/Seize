package com.dgrissom.seize.server;

import com.dgrissom.seize.FileIO;

import java.io.File;
import java.io.IOException;
import java.util.Map;

class ServerSettings {
    private static File file = new File("settings.dat");

    private String name;
    private int port;

    private ServerSettings() {
        this.name = "Invalid Server";
        this.port = 0;
    }

    public String getName() {
        return this.name;
    }
    private void setName(String name) {
        this.name = name;
    }
    int getPort() {
        return this.port;
    }
    private void setPort(int port) {
        this.port = port;
    }


    private static void set(String setting, String value, ServerSettings settings) {
        if (setting.equals("name"))
            settings.setName(value);
        else if (setting.equals("port"))
            settings.setPort(Integer.parseInt(value));
        else
            System.err.println("Unknown setting!");
    }

    static ServerSettings load() {
        try {
            ServerSettings settings = new ServerSettings();
            Map<String, String> values = FileIO.loadBasicFile(file);
            for (String key : values.keySet())
                set(key, values.get(key), settings);
            return settings;
        } catch (IOException e) {
            System.err.println("Could not load settings file. Using default settings");
            return createDefault();
        }
    }

    private static ServerSettings createDefault() {
        ServerSettings settings = new ServerSettings();
        settings.setPort(SeizeServer.DEFAULT_PORT);
        return settings;
    }
}
