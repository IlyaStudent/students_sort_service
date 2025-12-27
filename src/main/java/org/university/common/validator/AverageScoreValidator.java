package org.university.common.validator;

import org.university.common.exception.ValidationException;
import org.university.common.util.Constants;

public class AverageScoreValidator implements Validator<Double> {

    @Override
    public void validate(Double score) {
        if (score < Constants.MIN_AVERAGE_SCORE
                || score > Constants.MAX_AVERAGE_SCORE) {
            throw new ValidationException(
                    "Average score field value is invalid ",
                    "averageScore",
                    Double.toString(score)
            );
        }
    }
}