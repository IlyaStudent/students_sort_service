package org.university.feature.data.io;

import java.util.regex.Pattern;

public class FileManager {

    private static final Pattern JSON_FILE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_.\\-\\s]+?\\.json$", Pattern.CASE_INSENSITIVE);
    private static final String JSON_FILEPATH = "src/main/resources/data/";

    public static String getJSON_Filepath(String fileName) {
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Invalid filename: " + fileName);
        }
        if (!JSON_FILE_PATTERN.matcher(fileName).matches()) {
            throw new IllegalArgumentException(
                    "Wrong file name format" +
                    "Filename should only contain letters, numbers, spaces and following symbols: " +
                    "_, -, ., ");
        }
        return JSON_FILEPATH + fileName;
    }
}
