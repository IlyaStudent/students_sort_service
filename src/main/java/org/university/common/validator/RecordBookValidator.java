package org.university.common.validator;

import org.university.common.exception.ValidationException;

public class RecordBookValidator implements Validator<String> {

    @Override
    public void validate(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.isEmpty()) {
            throw new ValidationException(
                    "Record book cannot be empty"
            );
        }
        if (!recordBookNumber.matches("\\d{4}-\\d{5}")) {
            throw new ValidationException(
                    "Record book number field value is invalid ",
                    "recordBookNumber",
                    recordBookNumber
            );
        }
    }
}
