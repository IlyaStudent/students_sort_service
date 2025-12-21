package org.university.common.util;

public final class Constants {

    public static final String APP_NAME = "Student Sorting System";
    public static final String JSON_FILENAME = "students.json";
    public static final String[] GROUP_PREFIXES = {
            "CS", "IT", "SE", "AI", "CY", "MA", "PH", "BI", "EC", "EN",
            "CH", "PH", "ME", "EE", "CE", "BT", "MT", "AT", "NE", "RO"
    };

    private Constants() {
        throw new AssertionError("Cannot instantiate a Constants class");
    }
}
