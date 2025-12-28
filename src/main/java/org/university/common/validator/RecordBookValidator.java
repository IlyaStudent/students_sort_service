package org.university.common.validator;

import org.university.common.Constants;
import org.university.common.exception.ValidationException;

public class RecordBookValidator implements Validator<String> {

    @Override
    public void validate(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.isEmpty()) {
            throw new ValidationException(
                    Constants.ERROR_RECORD_BOOK_EMPTY
            );
        }
        if (!recordBookNumber.matches(Constants.PATTERN_RECORD_BOOK)) {
            throw new ValidationException(
                    Constants.ERROR_RECORD_BOOK_INVALID,
                    "recordBookNumber",
                    recordBookNumber
            );
        }
    }
}
