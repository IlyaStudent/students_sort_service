package org.university.feature.data.loader;

import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.common.util.Constants;
import org.university.feature.data.io.FileManager;
import org.university.feature.data.io.JsonWriter;
import org.university.feature.data.manualinput.ManualInput;
import org.university.feature.data.manualinput.ManualInputImpl;

public class ManualDataLoader implements DataLoader {

    private final ManualInput manualInput;
    private final JsonWriter jsonWriter;

    public ManualDataLoader() {
        this.manualInput = new ManualInputImpl();
        this.jsonWriter = new JsonWriter();
    }

    @Override
    public CustomList<Student> loadData(int count) throws DataLoadException {
        CustomList<Student> students = manualInput.inputData(count);

        //TODO TBD
        jsonWriter.writeData(
                students,
                FileManager.getJsonFilepath(Constants.JSON_FILENAME)
        );
        return students;
    }

    @Override
    public String getLoaderType() {
        return this.getClass().getName();
    }
}
