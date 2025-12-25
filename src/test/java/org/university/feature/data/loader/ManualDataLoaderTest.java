package org.university.feature.data.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.common.util.Constants;
import org.university.feature.data.io.FileManager;
import org.university.feature.data.io.JsonWriter;
import org.university.feature.data.manualinput.ManualInput;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManualDataLoaderTest {

    private ManualDataLoader manualDataLoader;

    @Mock
    private ManualInput mockManualInput;

    @Mock
    private JsonWriter mockJsonWriter;

    @Mock
    private CustomList<Student> mockStudentList;

    @BeforeEach
    void setUp() throws Exception {
        manualDataLoader = new ManualDataLoader();

        Field manualInputField = ManualDataLoader.class.getDeclaredField("manualInput");
        manualInputField.setAccessible(true);
        manualInputField.set(manualDataLoader, mockManualInput);

        Field jsonWriterField = ManualDataLoader.class.getDeclaredField("jsonWriter");
        jsonWriterField.setAccessible(true);
        jsonWriterField.set(manualDataLoader, mockJsonWriter);
    }

    @Test
    void loadData_ShouldReturnStudentsAndWriteToFile() throws Exception {
        int count = 5;

        when(mockManualInput.inputData(count)).thenReturn(mockStudentList);
        doNothing().when(mockJsonWriter).writeData(any(CustomList.class), anyString());

        String expectedFilePath = "data/students.json";
        try (MockedStatic<FileManager> mockedFileManager = mockStatic(FileManager.class)) {
            mockedFileManager.when(() -> FileManager.getJsonFilepath(Constants.JSON_FILENAME))
                    .thenReturn(expectedFilePath);

            CustomList<Student> result = manualDataLoader.loadData(count);

            assertNotNull(result);
            assertEquals(mockStudentList, result);

            verify(mockManualInput, times(1)).inputData(count);
            verify(mockJsonWriter, times(1)).writeData(mockStudentList, expectedFilePath);
        }
    }

    @Test
    void loadData_ShouldPropagateException_WhenManualInputFails() throws Exception {
        int count = 3;
        DataLoadException expectedException = new DataLoadException("Input error");

        when(mockManualInput.inputData(count)).thenThrow(expectedException);

        DataLoadException actualException = assertThrows(DataLoadException.class,
                () -> manualDataLoader.loadData(count)
        );

        assertEquals(expectedException, actualException);
        verify(mockManualInput, times(1)).inputData(count);
        verify(mockJsonWriter, never()).writeData(any(), anyString());
    }

    @Test
    void loadData_ShouldPropagateException_WhenJsonWriterFails() throws Exception {
        int count = 3;
        DataLoadException jsonWriterException = new DataLoadException("Write error");

        when(mockManualInput.inputData(count)).thenReturn(mockStudentList);
        doThrow(jsonWriterException).when(mockJsonWriter).writeData(any(CustomList.class), anyString());

        try (MockedStatic<FileManager> mockedFileManager = mockStatic(FileManager.class)) {
            mockedFileManager.when(() -> FileManager.getJsonFilepath(anyString()))
                    .thenReturn("data/test.json");

            DataLoadException actualException = assertThrows(DataLoadException.class,
                    () -> manualDataLoader.loadData(count)
            );

            assertEquals(jsonWriterException, actualException);
            verify(mockManualInput, times(1)).inputData(count);
            verify(mockJsonWriter, times(1)).writeData(any(), anyString());
        }
    }

    @Test
    void loadData_ShouldUseCorrectFileNameAndPath() throws Exception {
        int count = 5;
        String expectedFileName = Constants.JSON_FILENAME;
        String expectedFilePath = "data/" + expectedFileName;

        when(mockManualInput.inputData(count)).thenReturn(mockStudentList);
        doNothing().when(mockJsonWriter).writeData(any(CustomList.class), anyString());

        try (MockedStatic<FileManager> mockedFileManager = mockStatic(FileManager.class)) {
            mockedFileManager.when(() -> FileManager.getJsonFilepath(expectedFileName))
                    .thenReturn(expectedFilePath);

            manualDataLoader.loadData(count);

            mockedFileManager.verify(() -> FileManager.getJsonFilepath(expectedFileName), times(1));
            verify(mockJsonWriter, times(1)).writeData(mockStudentList, expectedFilePath);
        }
    }

    @Test
    void getLoaderType_ShouldReturnCorrectClassName() {
        String loaderType = manualDataLoader.getLoaderType();

        assertEquals(ManualDataLoader.class.getName(), loaderType);
    }

    @Test
    void loadData_ShouldHandleZeroCount() throws Exception {
        int count = 0;

        when(mockManualInput.inputData(count)).thenReturn(mockStudentList);
        doNothing().when(mockJsonWriter).writeData(any(CustomList.class), anyString());

        try (MockedStatic<FileManager> mockedFileManager = mockStatic(FileManager.class)) {
            mockedFileManager.when(() -> FileManager.getJsonFilepath(Constants.JSON_FILENAME))
                    .thenReturn("data/students.json");

            CustomList<Student> result = manualDataLoader.loadData(count);

            assertNotNull(result);
            verify(mockManualInput, times(1)).inputData(count);
            verify(mockJsonWriter, times(1)).writeData(mockStudentList, "data/students.json");
        }
    }

    @Test
    void loadData_ShouldPassCorrectStudentListToJsonWriter() throws Exception {
        int count = 2;

        CustomList<Student> testStudents = new org.university.common.collection.CustomArrayList<>();
        testStudents.add(new Student.Builder()
                .groupNumber("AB-123")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build());
        testStudents.add(new Student.Builder()
                .groupNumber("CD-456")
                .averageScore(3.8)
                .recordBookNumber("2023-54321")
                .build());

        when(mockManualInput.inputData(count)).thenReturn(testStudents);
        doNothing().when(mockJsonWriter).writeData(any(CustomList.class), anyString());

        try (MockedStatic<FileManager> mockedFileManager = mockStatic(FileManager.class)) {
            mockedFileManager.when(() -> FileManager.getJsonFilepath(anyString()))
                    .thenReturn("data/students.json");

            CustomList<Student> result = manualDataLoader.loadData(count);

            assertSame(testStudents, result); // Проверяем, что возвращается тот же список

            verify(mockJsonWriter, times(1)).writeData(eq(testStudents), anyString());
        }
    }
}