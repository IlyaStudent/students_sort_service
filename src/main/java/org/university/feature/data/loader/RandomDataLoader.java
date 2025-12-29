package org.university.feature.data.loader;

import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.common.validator.DataValidator;
import org.university.feature.data.generator.DataGenerator;
import org.university.feature.data.generator.RandomStudentGenerator;

public class RandomDataLoader implements DataLoader {

    private final DataGenerator generator;
    private final DataValidator dataValidator;

    public RandomDataLoader() {
        this.generator = new RandomStudentGenerator();
        this.dataValidator = new DataValidator();
    }

    @Override
    public CustomList<Student> loadData(int count) throws DataLoadException {
        CustomList<Student> students = generator.generateStudents(count);
        dataValidator.validateAndCleanDuplicates(students);
        return students;
    }

    @Override
    public String getLoaderType() {
        return this.getClass().getName();
    }
}
