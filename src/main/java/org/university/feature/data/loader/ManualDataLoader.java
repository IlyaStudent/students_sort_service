package org.university.feature.data.loader;

import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.feature.data.manualinput.ManualInput;
import org.university.feature.data.manualinput.ManualInputImpl;
import org.university.feature.ui.io.InputReader;
import org.university.feature.ui.io.OutputWriter;

public class ManualDataLoader implements DataLoader {

    private final ManualInput manualInput;

    public ManualDataLoader(InputReader reader, OutputWriter writer) {
        this.manualInput = new ManualInputImpl(reader, writer);
    }

    @Override
    public CustomList<Student> loadData(int count) throws DataLoadException {
        return manualInput.inputData(count);
    }

    @Override
    public String getLoaderType() {
        return this.getClass().getName();
    }
}
