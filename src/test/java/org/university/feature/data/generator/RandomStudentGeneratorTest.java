package org.university.feature.data.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.university.common.Constants;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RandomStudentGeneratorTest {

    private RandomStudentGenerator generator;
    private Random mockRandom;

    @BeforeEach
    void setUp() throws Exception {
        mockRandom = mock(Random.class);
        generator = new RandomStudentGenerator();

        Field randomField = RandomStudentGenerator.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(generator, mockRandom);

        mockRandomBehavior();
    }

    @Test
    void generateStudents_ShouldReturnCorrectNumberOfStudents() {
        int count = 5;

        CustomList<Student> students = generator.generateStudents(count);

        assertNotNull(students);
        assertEquals(count, students.size());

        for (Student student : students) {
            assertNotNull(student);
            assertNotNull(student.getGroupNumber());
            assertNotNull(student.getRecordBookNumber());
            assertNotNull(student.getAverageScore());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 100})
    void generateStudents_WithDifferentCounts_ShouldReturnCorrectSize(int count) {
        CustomList<Student> students = generator.generateStudents(count);

        assertNotNull(students);
        assertEquals(count, students.size());
    }

    @Test
    void generateStudents_ShouldGenerateValidGroupNumbers() {
        reset(mockRandom);

        int count = 10;

        when(mockRandom.nextInt(Constants.GROUP_PREFIXES.length))
                .thenReturn(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        when(mockRandom.nextInt(900))
                .thenReturn(123, 456, 789, 111, 222, 333, 444, 555, 666, 777);

        when(mockRandom.nextInt(26))
                .thenReturn(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        when(mockRandom.nextInt(1000))
                .thenReturn(100, 200, 300, 400, 500, 600, 700, 800, 900, 999);

        when(mockRandom.nextDouble()).thenReturn(0.5);

        CustomList<Student> students = generator.generateStudents(count);

        assertEquals(count, students.size());

        for (Student student : students) {
            String groupNumber = student.getGroupNumber();
            assertNotNull(groupNumber);
            assertTrue(groupNumber.matches("^[A-Z]{2}-\\d{3}$"));

            String prefix = groupNumber.substring(0, 2);
            boolean validPrefix = false;
            for (String validPrefixConst : Constants.GROUP_PREFIXES) {
                if (validPrefixConst.equals(prefix)) {
                    validPrefix = true;
                    break;
                }
            }
            assertTrue(validPrefix, "Префикс группы должен быть из Constants.GROUP_PREFIXES");
        }
    }

    @Test
    void generateStudents_ShouldGenerateValidAverageScores() {
        reset(mockRandom);

        int count = 10;

        when(mockRandom.nextDouble()).thenReturn(
                0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9
        );

        when(mockRandom.nextInt(Constants.GROUP_PREFIXES.length))
                .thenReturn(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        when(mockRandom.nextInt(900))
                .thenReturn(100, 200, 300, 400, 500, 600, 700, 800, 900, 999);
        when(mockRandom.nextInt(26))
                .thenReturn(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        when(mockRandom.nextInt(1000))
                .thenReturn(100, 200, 300, 400, 500, 600, 700, 800, 900, 999);

        CustomList<Student> students = generator.generateStudents(count);

        assertEquals(count, students.size());

        for (Student student : students) {
            Double averageScore = student.getAverageScore();
            assertNotNull(averageScore);

            assertTrue(averageScore >= 0.0 && averageScore <= 5.0);

            String scoreStr = String.valueOf(averageScore);
            int decimalIndex = scoreStr.indexOf('.');
            if (decimalIndex != -1) {
                int decimalPlaces = scoreStr.length() - decimalIndex - 1;
                assertTrue(decimalPlaces <= 1);
            }
        }
    }

    @Test
    void generateStudents_ShouldGenerateValidRecordBookNumbers() {
        reset(mockRandom);

        int count = 10;

        when(mockRandom.nextInt(26))
                .thenReturn(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        when(mockRandom.nextInt(1000))
                .thenReturn(100, 200, 300, 400, 500, 600, 700, 800, 900, 999);

        when(mockRandom.nextInt(Constants.GROUP_PREFIXES.length))
                .thenReturn(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        when(mockRandom.nextInt(900))
                .thenReturn(100, 200, 300, 400, 500, 600, 700, 800, 900, 999);
        when(mockRandom.nextDouble()).thenReturn(0.5);

        CustomList<Student> students = generator.generateStudents(count);

        assertEquals(count, students.size());

        for (Student student : students) {
            String recordBookNumber = student.getRecordBookNumber();
            assertNotNull(recordBookNumber);
            assertTrue(recordBookNumber.matches("^\\d{4}-\\d{5}$"));

            String yearStr = recordBookNumber.substring(0, 4);
            int year = Integer.parseInt(yearStr);
            assertTrue(year >= 2000 && year <= 2025);
        }
    }

    @Test
    void generateStudent_ShouldCreateValidStudent() {
        CustomList<Student> students = generator.generateStudents(1);
        Student student = students.get(0);

        assertNotNull(student);
        assertNotNull(student.getGroupNumber());
        assertNotNull(student.getAverageScore());
        assertNotNull(student.getRecordBookNumber());
        assertTrue(student.getGroupNumber().matches("^[A-Z]{2}-\\d{3}$"));
        assertTrue(student.getRecordBookNumber().matches("^\\d{4}-\\d{5}$"));
        assertTrue(student.getAverageScore() >= 0.0 && student.getAverageScore() <= 5.0);
    }

    @Test
    void generateAverageScore_ShouldBeRoundedToOneDecimal() {
        reset(mockRandom);

        when(mockRandom.nextDouble()).thenReturn(0.123456);
        when(mockRandom.nextInt(Constants.GROUP_PREFIXES.length)).thenReturn(0);
        when(mockRandom.nextInt(900)).thenReturn(100);
        when(mockRandom.nextInt(26)).thenReturn(10);
        when(mockRandom.nextInt(1000)).thenReturn(500);

        CustomList<Student> students = generator.generateStudents(1);
        Double score = students.get(0).getAverageScore();

        String scoreStr = String.valueOf(score);
        int decimalIndex = scoreStr.indexOf('.');
        if (decimalIndex != -1) {
            int decimalPlaces = scoreStr.length() - decimalIndex - 1;
            assertTrue(decimalPlaces <= 1);
        }
    }

    @Test
    void generateRecordBookNumber_ShouldHaveCorrectFormat() {
        reset(mockRandom);

        when(mockRandom.nextInt(26)).thenReturn(5);
        when(mockRandom.nextInt(1000)).thenReturn(123);
        when(mockRandom.nextInt(Constants.GROUP_PREFIXES.length)).thenReturn(0);
        when(mockRandom.nextInt(900)).thenReturn(100);
        when(mockRandom.nextDouble()).thenReturn(0.5);

        CustomList<Student> students = generator.generateStudents(1);
        String recordBookNumber = students.get(0).getRecordBookNumber();

        assertTrue(recordBookNumber.matches("^2005-\\d{5}$"));
    }

    private void mockRandomBehavior() {
        AtomicInteger yearCounter = new AtomicInteger(0);
        AtomicInteger offsetCounter = new AtomicInteger(0);
        AtomicInteger groupNumberCounter = new AtomicInteger(0);
        AtomicInteger prefixCounter = new AtomicInteger(0);

        when(mockRandom.nextInt(anyInt())).thenAnswer(invocation -> {
            int bound = invocation.getArgument(0);
            if (bound == 900) {
                return groupNumberCounter.getAndIncrement() % 900;
            } else if (bound == 26) {
                return yearCounter.getAndIncrement() % 26;
            } else if (bound == 1000) {
                return offsetCounter.getAndIncrement() % 1000;
            } else if (bound == Constants.GROUP_PREFIXES.length) {
                return prefixCounter.getAndIncrement() % Constants.GROUP_PREFIXES.length;
            }
            return 0;
        });
        when(mockRandom.nextDouble()).thenReturn(0.5);
    }
}