package org.university.common.validator;

import org.university.common.exception.ValidationException;

public class GroupNumberValidator implements Validator<String> {

    @Override
    public void validate(String groupNumber) {
        if (groupNumber == null || groupNumber.isEmpty()) {
            throw new ValidationException(
                    "Group number cannot be empty"
            );
        }
        if (!groupNumber.matches("[A-Z]{2}-\\d{3}")) {
            throw new ValidationException(
                    "Group number field value is invalid ",
                    "groupNumber",
                    groupNumber
            );
        }
    }
}
