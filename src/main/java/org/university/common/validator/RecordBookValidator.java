package org.university.common.validator;

import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;

public class RecordBookValidator implements Validator<CustomList<Student>> {

    @Override
    public void validate(CustomList<Student> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            throw new ValidationException(
                    "List cannot be empty"
            );
        }

        for (Student student : arrayList) {
            isValidRecordBookNumber(student.getRecordBookNumber());
        }
    }

    public void isValidRecordBookNumber(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.isEmpty()) {
            throw new ValidationException(
                    "Record book cannot be empty"
            );
        }
        if (!recordBookNumber.matches("\\d{4}-\\d{5}")) {
            throw new ValidationException(
                    "Record book number field value is invalid ", "recordBookNumber", recordBookNumber
            );
        }
    }
}
