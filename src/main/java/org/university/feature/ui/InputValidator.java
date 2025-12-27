package org.university.feature.ui;

public final class InputValidator {

    public InputValidator() {}

    public boolean isValidInt(String value) {
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

    public boolean isValidDouble(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
