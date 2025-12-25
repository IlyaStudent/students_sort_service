package org.university.feature.data.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.common.validator.DataValidator;
import org.university.feature.data.generator.RandomStudentGenerator;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomDataLoaderTest {

    private RandomDataLoader randomDataLoader;

    @Mock
    private RandomStudentGenerator mockGenerator;

    @Mock
    private DataValidator mockValidator;

    @Mock
    private CustomList<Student> mockStudentList;

    @BeforeEach
    void setUp() throws Exception {
        randomDataLoader = new RandomDataLoader();

        Field generatorField = RandomDataLoader.class.getDeclaredField("generator");
        generatorField.setAccessible(true);
        generatorField.set(randomDataLoader, mockGenerator);

        Field validatorField = RandomDataLoader.class.getDeclaredField("dataValidator");
        validatorField.setAccessible(true);
        validatorField.set(randomDataLoader, mockValidator);
    }

    @Test
    void loadData_ShouldReturnStudents_WhenCountIsValid() throws DataLoadException {

        int count = 5;
        when(mockGenerator.generateStudents(count)).thenReturn(mockStudentList);
        doNothing().when(mockValidator).validateStudentList(mockStudentList);

        CustomList<Student> result = randomDataLoader.loadData(count);

        assertNotNull(result);
        assertEquals(mockStudentList, result);
        verify(mockGenerator, times(1)).generateStudents(count);
        verify(mockValidator, times(1)).validateStudentList(mockStudentList);
    }

    @Test
    void loadData_WithDifferentCounts_ShouldCallGeneratorWithCorrectCount() throws DataLoadException {

        int[] counts = {0, 1, 10, 100};

        for (int count : counts) {
            reset(mockGenerator, mockValidator);
            when(mockGenerator.generateStudents(count)).thenReturn(mockStudentList);
            doNothing().when(mockValidator).validateStudentList(mockStudentList);

            randomDataLoader.loadData(count);

            verify(mockGenerator, times(1)).generateStudents(count);
            verify(mockValidator, times(1)).validateStudentList(mockStudentList);
        }
    }

    @Test
    void getLoaderType_ShouldReturnCorrectClassName() {
        String loaderType = randomDataLoader.getLoaderType();
        assertEquals(RandomDataLoader.class.getName(), loaderType);
    }

    @Test
    void constructor_ShouldInitializeDependencies() {
        RandomDataLoader freshLoader = new RandomDataLoader();
        assertNotNull(freshLoader);
        assertEquals(RandomDataLoader.class.getName(), freshLoader.getLoaderType());
    }
}