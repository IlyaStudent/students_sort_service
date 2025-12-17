package org.university.common.exception;

public class ValidationException extends RuntimeException   {
    private final String field;
    private final String invalidValue;

    public ValidationException(String message, String field, String invalidValue) {
        super(message);
        this.field = field;
        this.invalidValue = invalidValue;
    }

    public ValidationException(String message) {
        this(message, null, null);
    }

    public String getField() {
        return field;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String toString() {
        if (field == null && invalidValue == null) {
            return String.format("Validation exception{ %s }", getMessage());
        }
        return "ValidationException{" +
                "field='" + field + '\'' +
                ", invalidValue='" + invalidValue + '\'' +
                '}';
    }
}
