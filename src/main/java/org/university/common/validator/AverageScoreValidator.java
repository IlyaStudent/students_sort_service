package org.university.common.validator;

import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;

public class AverageScoreValidator implements Validator<CustomList<Student>> {

    @Override
    public void validate(CustomList<Student> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            throw new ValidationException(
                    "List cannot be empty"
            );
        }

        for (Student student : arrayList) {
            double score = student.getAverageScore();
            isValidScore(score);
        }
    }

    public void isValidScore(double score) {
        if (score < 0.0 || score > 5.0) {
            throw new ValidationException(
                    "Average score field value is invalid ",
                    "averageScore",
                    Double.toString(score)
            );
        }
    }
}