package org.university.feature.data.loader;

import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.feature.data.manualinput.ManualInput;
import org.university.feature.data.manualinput.ManualInputImpl;

public class ManualDataLoader implements DataLoaderCloseable {

    private final ManualInput manualInput;

    public ManualDataLoader() {
        this.manualInput = new ManualInputImpl();
    }

    @Override
    public CustomList<Student> loadData(int count) throws DataLoadException {
        return manualInput.inputData(count);
    }

    @Override
    public String getLoaderType() {
        return this.getClass().getName();
    }

    @Override
    public void close() {
        manualInput.close();
    }
}
