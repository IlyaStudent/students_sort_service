package org.university.feature.data.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.common.util.Constants;
import org.university.common.validator.DataValidator;
import org.university.feature.data.io.JsonReader;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileDataLoaderReflectionTest {

    private FileDataLoader fileDataLoader;

    @Mock
    private JsonReader mockJsonReader;

    @Mock
    private DataValidator mockDataValidator;

    @Mock
    private CustomList<Student> mockStudentList;

    @BeforeEach
    void setUp() throws Exception {
        fileDataLoader = new FileDataLoader();

        Field jsonReaderField = FileDataLoader.class.getDeclaredField("jsonReader");
        jsonReaderField.setAccessible(true);
        jsonReaderField.set(fileDataLoader, mockJsonReader);

        Field dataValidatorField = FileDataLoader.class.getDeclaredField("dataValidator");
        dataValidatorField.setAccessible(true);
        dataValidatorField.set(fileDataLoader, mockDataValidator);
    }

    @Test
    void loadData_ShouldReturnValidatedStudents() throws Exception {
        int count = 10;

        when(mockJsonReader.parseStudents(Constants.JSON_FILENAME, count))
                .thenReturn(mockStudentList);
        doNothing().when(mockDataValidator).validateStudentList(mockStudentList);

        CustomList<Student> result = fileDataLoader.loadData(count);

        assertNotNull(result);
        verify(mockJsonReader, times(1))
                .parseStudents(Constants.JSON_FILENAME, count);
        verify(mockDataValidator, times(1))
                .validateStudentList(mockStudentList);
    }

    @Test
    void getLoaderType_ShouldReturnCorrectClassName() {
        assertEquals(FileDataLoader.class.getName(), fileDataLoader.getLoaderType());
    }
}