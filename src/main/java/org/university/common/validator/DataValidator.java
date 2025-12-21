package org.university.common.validator;

import org.university.common.collection.CustomList;
import org.university.common.model.Student;

public class DataValidator implements Validator<CustomList<Student>> {
    private final Validator[] validators;

    public DataValidator() {
        this.validators = new Validator[] {
                new GroupNumberValidator(),
                new AverageScoreValidator(),
                new RecordBookValidator()
        };
    }

    public DataValidator(Validator... validators) {
        this.validators = validators;
    }

    @Override
    public void validate(CustomList<Student> arrayList) {
        for (Validator validator : validators) {
            validator.validate(arrayList);
        }
    }
}
