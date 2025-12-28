package org.university.common.validator;

import org.university.common.Constants;
import org.university.common.exception.ValidationException;

public class GroupNumberValidator implements Validator<String> {

    @Override
    public void validate(String groupNumber) {
        if (groupNumber == null || groupNumber.isEmpty()) {
            throw new ValidationException(
                    Constants.ERROR_GROUP_NUMBER_EMPTY
            );
        }
        if (!groupNumber.matches(Constants.PATTERN_GROUP_NUMBER)) {
            throw new ValidationException(
                    Constants.ERROR_GROUP_NUMBER_INVALID,
                    "groupNumber",
                    groupNumber
            );
        }
    }
}
