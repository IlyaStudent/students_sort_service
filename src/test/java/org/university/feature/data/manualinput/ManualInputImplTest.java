package org.university.feature.data.manualinput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.common.validator.AverageScoreValidator;
import java.util.InputMismatchException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManualInputImplTest {

    @Mock
    private Scanner mockScanner;

    private ManualInputImpl manualInput;

    @BeforeEach
    void setUp() {
        manualInput = new ManualInputImpl();

        try {
            var scannerField = ManualInputImpl.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(manualInput, mockScanner);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set mock scanner", e);
        }
    }

    @Test
    void inputData_ShouldReturnCorrectNumberOfStudents() {
        int count = 3;

        when(mockScanner.nextLine())
                .thenReturn("AB-123")
                .thenReturn("")
                .thenReturn("2023-12345")
                .thenReturn("CD-456")
                .thenReturn("")
                .thenReturn("2023-54321")
                .thenReturn("EF-789")
                .thenReturn("")
                .thenReturn("2023-98765");

        when(mockScanner.nextDouble())
                .thenReturn(4.5)
                .thenReturn(3.8)
                .thenReturn(4.2);

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(count, result.size());

        Student student1 = result.get(0);
        assertEquals("AB-123", student1.getGroupNumber());
        assertEquals(4.5, student1.getAverageScore(), 0.001);
        assertEquals("2023-12345", student1.getRecordBookNumber());

        Student student2 = result.get(1);
        assertEquals("CD-456", student2.getGroupNumber());
        assertEquals(3.8, student2.getAverageScore(), 0.001);
        assertEquals("2023-54321", student2.getRecordBookNumber());

        Student student3 = result.get(2);
        assertEquals("EF-789", student3.getGroupNumber());
        assertEquals(4.2, student3.getAverageScore(), 0.001);
        assertEquals("2023-98765", student3.getRecordBookNumber());

        verify(mockScanner, times(count * 3)).nextLine();
        verify(mockScanner, times(count)).nextDouble();
    }

    @Test
    void inputData_ShouldHandleSingleStudent() {
        int count = 1;

        when(mockScanner.nextLine())
                .thenReturn("AB-123")
                .thenReturn("2023-12345");

        when(mockScanner.nextDouble()).thenReturn(4.5);

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(1, result.size());

        Student student = result.get(0);
        assertEquals("AB-123", student.getGroupNumber());
        assertEquals(4.5, student.getAverageScore(), 0.001);
        assertEquals("2023-12345", student.getRecordBookNumber());
    }

    @Test
    void inputData_ShouldConvertGroupNumberToUpperCase() {
        int count = 1;

        when(mockScanner.nextLine())
                .thenReturn("ab-123")
                .thenReturn("2023-12345");

        when(mockScanner.nextDouble()).thenReturn(4.5);

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(1, result.size());

        Student student = result.get(0);
        assertEquals("AB-123", student.getGroupNumber());
    }

    @Test
    void inputData_ShouldTrimInputValues() {
        int count = 1;

        when(mockScanner.nextLine())
                .thenReturn("  AB-123  ")
                .thenReturn("  2023-12345  ");

        when(mockScanner.nextDouble()).thenReturn(4.5);

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(1, result.size());

        Student student = result.get(0);
        assertEquals("AB-123", student.getGroupNumber());
        assertEquals("2023-12345", student.getRecordBookNumber());
    }

    @Test
    void readAverageScore_ShouldHandleInputMismatchException() {
        try (MockedConstruction<AverageScoreValidator> mockedValidator =
                     mockConstruction(AverageScoreValidator.class)) {

            when(mockScanner.nextDouble())
                    .thenThrow(new InputMismatchException())
                    .thenReturn(4.5);

            when(mockScanner.nextLine()).thenReturn("");

            double result = invokePrivateMethod("readAverageScore", new Class<?>[0]);

            assertEquals(4.5, result, 0.001);

            verify(mockScanner, times(2)).nextDouble();
            verify(mockScanner, atLeastOnce()).nextLine();
        }
    }

    @Test
    void getInputType_ShouldReturnCorrectClassName() {
        String inputType = manualInput.getInputType();

        assertEquals(ManualInputImpl.class.getName(), inputType);
    }

    @Test
    void readStudentData_ShouldCreateStudentWithValidData() {
        int studentNumber = 1;

        when(mockScanner.nextLine())
                .thenReturn("AB-123")
                .thenReturn("")
                .thenReturn("2023-12345");

        when(mockScanner.nextDouble()).thenReturn(4.5);

        Student result = invokeReadStudentData(studentNumber);

        assertNotNull(result);
        assertEquals("AB-123", result.getGroupNumber());
        assertEquals(4.5, result.getAverageScore(), 0.001);
        assertEquals("2023-12345", result.getRecordBookNumber());
    }

    private Student invokeReadStudentData(int studentNumber) {
        try {
            var method = ManualInputImpl.class.getDeclaredMethod("readStudentData", int.class);
            method.setAccessible(true);
            return (Student) method.invoke(manualInput, studentNumber);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke readStudentData", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T invokePrivateMethod(String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            var method = ManualInputImpl.class.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return (T) method.invoke(manualInput, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke private method: " + methodName, e);
        }
    }
}