package org.university.common.validator;

import org.university.common.Constants;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;

import java.util.HashSet;
import java.util.Set;

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
                    Constants.ERROR_LIST_EMPTY
            );
        }
        for (Student student : students) {
            validateStudent(student);
        }
    }

    public void validateAndCleanDuplicates(CustomList<Student> students) {
        validateStudentList(students);
        removeDuplicatesInPlace(students);
    }

    private void removeDuplicatesInPlace(CustomList<Student> students) {
        Set<String> seenRecordBooks = new HashSet<>();
        CustomList<Student> uniqueStudents = new CustomArrayList<>();

        for (Student student : students) {
            String recordBookNumber = student.getRecordBookNumber();

            if (!seenRecordBooks.contains(recordBookNumber)) {
                seenRecordBooks.add(recordBookNumber);
                uniqueStudents.add(student);
            }
        }

        students.clear();

        for (Student student : uniqueStudents) {
            students.add(student);
        }
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new ValidationException(
                    Constants.ERROR_STUDENT_NULL
            );
        }
        groupNumberValidator.validate(student.getGroupNumber());
        recordBookValidator.validate(student.getRecordBookNumber());
        averageScoreValidator.validate(student.getAverageScore());
    }
}
