package com.dgrissom.seize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIO {
    private FileIO() {}

    private static void parse(String line, int lineNumber, Map<String, String> values) {
        line = line.trim();
        if (line.startsWith("//") || line.isEmpty())
            return;

        String[] split = line.split(":");
        if (split.length < 2) {
            System.err.println("Invalid setting at line #" + lineNumber);
            return;
        }

        String key = split[0].trim();
        String value = line.substring(line.indexOf(":") + 1).trim();
        values.put(key, value);
    }
    // file with lines like key: value
    // all keys must be unique and cannot contain colons ':'
    // // means comment
    public static Map<String, String> loadBasicFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        Map<String, String> values = new HashMap<>();
        int lineNumber = 1;
        for (String line : lines)
            parse(line, lineNumber++, values);
        return values;
    }

    public static void saveBasicFile(Map<String, String> values, File file) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String key : values.keySet())
            lines.add(key + ": " + values.get(key));
        // this overwrites the existing file
        //todo we could save existing comments if we really tried
        if (!file.exists())
            if (!file.createNewFile())
                throw new IOException("couldn't create file: " + file.getName());
        Files.write(file.toPath(), lines, StandardOpenOption.WRITE);
    }
}
