package org.university.feature.data.io;

import org.university.common.util.Constants;

public class FileManager {

    public static String getJsonFilepath(String fileName) {
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Filename should not be empty");
        }
        if (!Constants.JSON_FILE_PATTERN.matcher(fileName).matches()) {
            throw new IllegalArgumentException(
                    "Wrong file name format" +
                    "Filename should only contain letters, numbers, spaces and following symbols: " +
                    "_, -, . ");
        }
        return Constants.JSON_FILEPATH + fileName;
    }
}
