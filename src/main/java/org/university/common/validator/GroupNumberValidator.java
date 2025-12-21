package org.university.common.validator;

import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;

public class GroupNumberValidator implements Validator<CustomList<Student>> {

    @Override
    public void validate(CustomList<Student> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            throw new ValidationException(
                    "List cannot be empty"
            );
        }

        for (Student student : arrayList) {
            isValidGroupNumber(student.getGroupNumber());
        }
    }

    public void isValidGroupNumber(String groupNumber) {
        if (groupNumber == null || groupNumber.isEmpty()) {
            throw new ValidationException(
                    "Group number cannot be empty"
            );
        }
        if (!groupNumber.matches("[A-Z]{2}-\\d{3}")) {
            throw new ValidationException(
                    "Group number field value is invalid ", "groupNumber", groupNumber
            );
        }
    }
}
