package org.university.feature.ui;

public final class InputValidator {

    public InputValidator() {}

    public boolean isValidInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
