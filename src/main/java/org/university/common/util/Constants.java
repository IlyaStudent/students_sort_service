package org.university.common.util;

import java.util.regex.Pattern;

public final class Constants {

    public static final String APP_NAME = "Student Sorting System";
    public static final String JSON_FILENAME = "students.json";
    public static final String[] GROUP_PREFIXES = {
            "CS", "IT", "SE", "AI", "CY", "MA", "PH", "BI", "EC", "EN",
            "CH", "PH", "ME", "EE", "CE", "BT", "MT", "AT", "NE", "RO"
    };
    public static final Pattern JSON_FILE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_.\\-\\s]+?\\.json$");
    public static final String JSON_FILEPATH = "src/main/resources/data/";

    public static final double MIN_AVERAGE_SCORE  = 0.0;
    public static final double MAX_AVERAGE_SCORE  = 5.0;

    private Constants() {
        throw new AssertionError("Cannot instantiate a Constants class");
    }
}
