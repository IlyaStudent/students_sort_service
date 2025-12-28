package org.university.feature.ui;

public final class InputValidator {

    private InputValidator() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static boolean isValidInt(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }

        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
