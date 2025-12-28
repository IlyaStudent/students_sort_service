package org.university.common.validator;

import org.university.common.exception.ValidationException;
import org.university.common.Constants;

public class AverageScoreValidator implements Validator<Double> {

    @Override
    public void validate(Double score) {
        if (score < Constants.MIN_AVERAGE_SCORE
                || score > Constants.MAX_AVERAGE_SCORE) {
            throw new ValidationException(
                    Constants.ERROR_AVERAGE_SCORE_INVALID,
                    "averageScore",
                    Double.toString(score)
            );
        }
    }
}