package org.university.feature.data.loader;

import org.university.common.exception.ValidationException;
import org.university.common.util.Constants;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.common.validator.DataValidator;
import org.university.feature.data.io.JsonReader;

public class FileDataLoader implements DataLoader {

    private final JsonReader jsonReader;
    private final DataValidator dataValidator;

    public FileDataLoader() {
        this.jsonReader = new JsonReader();
        this.dataValidator = new DataValidator();
    }

    @Override
    public CustomList<Student> loadData(int count) throws DataLoadException, ValidationException {
        CustomList<Student> students = jsonReader.parseStudents(Constants.JSON_FILENAME, count);
        dataValidator.validateStudentList(students);
        return students;
    }

    @Override
    public String getLoaderType() {
        return this.getClass().getName();
    }
}
