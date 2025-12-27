package org.university.feature.data.loader;

import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;

public interface DataLoader {
    CustomList<Student> loadData(int count) throws DataLoadException;
    String getLoaderType();
}
