package org.university.common.validator;

import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;

public class DataValidator {

    private final GroupNumberValidator groupNumberValidator;
    private final RecordBookValidator recordBookValidator;
    private final AverageScoreValidator averageScoreValidator;

    public DataValidator() {
        this.groupNumberValidator = new GroupNumberValidator();
        this.recordBookValidator = new RecordBookValidator();
        this.averageScoreValidator = new AverageScoreValidator();
    }

    public void validateStudentList(CustomList<Student> students) {
        if (students == null || students.isEmpty()) {
            throw new ValidationException(
                    "List cannot be empty"
            );
        }
        for (Student student : students) {
            validateStudent(student);
        }
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new ValidationException(
                    "Student cannot be null"
            );
        }
        groupNumberValidator.validate(student.getGroupNumber());
        recordBookValidator.validate(student.getRecordBookNumber());
        averageScoreValidator.validate(student.getAverageScore());
    }
}
