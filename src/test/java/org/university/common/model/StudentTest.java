package org.university.common.model;

import org.junit.jupiter.api.Test;
import org.university.common.Constants;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void builder_ShouldCreateStudentWithDefaultValues() {
        Student student = new Student.Builder().build();

        assertEquals("AA-000", student.getGroupNumber());
        assertEquals(5.0, student.getAverageScore());
        assertEquals("2000-00000", student.getRecordBookNumber());
    }

    @Test
    void builder_ShouldCreateStudentWithCustomValues() {
        Student student = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertEquals("CS-101", student.getGroupNumber());
        assertEquals(4.5, student.getAverageScore());
        assertEquals("2023-12345", student.getRecordBookNumber());
    }

    @Test
    void builder_ShouldThrowNPE_WhenGroupNumberIsNull() {
        assertThrows(NullPointerException.class, () -> new Student.Builder().groupNumber(null));
    }

    @Test
    void builder_ShouldThrowNPE_WhenRecordBookNumberIsNull() {
        assertThrows(NullPointerException.class, () -> new Student.Builder().recordBookNumber(null));
    }

    @Test
    void builder_ShouldSupportMethodChaining() {
        Student student = new Student.Builder()
                .groupNumber("IT-202")
                .averageScore(3.8)
                .recordBookNumber("2022-54321")
                .build();

        assertNotNull(student);
        assertEquals("IT-202", student.getGroupNumber());
        assertEquals(3.8, student.getAverageScore());
        assertEquals("2022-54321", student.getRecordBookNumber());
    }

    @Test
    void compareTo_ShouldReturnZero_WhenStudentsAreEqual() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertEquals(0, student1.compareTo(student2));
    }

    @Test
    void compareTo_ShouldCompareByGroupNumber_First() {
        Student student1 = new Student.Builder()
                .groupNumber("AA-100")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("ZZ-999")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertTrue(student1.compareTo(student2) < 0);
        assertTrue(student2.compareTo(student1) > 0);
    }

    @Test
    void compareTo_ShouldCompareByAverageScore_WhenGroupNumbersEqual() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(3.0)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(5.0)
                .recordBookNumber("2023-12345")
                .build();

        assertTrue(student1.compareTo(student2) < 0);
        assertTrue(student2.compareTo(student1) > 0);
    }

    @Test
    void compareTo_ShouldCompareByRecordBookNumber_WhenGroupAndScoreEqual() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2020-11111")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-99999")
                .build();

        assertTrue(student1.compareTo(student2) < 0);
        assertTrue(student2.compareTo(student1) > 0);
    }

    @Test
    void compareTo_ShouldThrowNPE_WhenOtherIsNull() {
        Student student = new Student.Builder().build();

        assertThrows(NullPointerException.class, () -> student.compareTo(null));
    }

    @Test
    void equals_ShouldReturnTrue_WhenStudentsHaveSameData() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertEquals(student1, student2);
        assertEquals(student2, student1);
    }

    @Test
    void equals_ShouldReturnFalse_WhenGroupNumberDiffers() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("IT-202")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertNotEquals(student1, student2);
    }

    @Test
    void equals_ShouldReturnFalse_WhenAverageScoreDiffers() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(3.5)
                .recordBookNumber("2023-12345")
                .build();

        assertNotEquals(student1, student2);
    }

    @Test
    void equals_ShouldReturnFalse_WhenRecordBookNumberDiffers() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-99999")
                .build();

        assertNotEquals(student1, student2);
    }

    @Test
    void equals_ShouldReturnTrue_WhenComparingWithItself() {
        Student student = new Student.Builder().build();

        assertEquals(student, student);
    }

    @Test
    void equals_ShouldReturnFalse_WhenComparingWithNull() {
        Student student = new Student.Builder().build();

        assertNotEquals(null, student);
    }

    @Test
    void equals_ShouldReturnFalse_WhenComparingWithDifferentClass() {
        Student student = new Student.Builder().build();
        String notAStudent = "Not a student";

        assertNotEquals(notAStudent, student);
    }

    @Test
    void hashCode_ShouldBeEqual_WhenStudentsAreEqual() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void hashCode_ShouldBeDifferent_WhenStudentsAreDifferent() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("IT-202")
                .averageScore(3.0)
                .recordBookNumber("2020-99999")
                .build();

        assertEquals(student1.hashCode(), student1.hashCode());
        assertEquals(student2.hashCode(), student2.hashCode());
    }

    @Test
    void toString_ShouldReturnFormattedString() {
        Student student = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        String expectedFormat = String.format(
                Constants.FORMAT_STUDENT_DISPLAY,
                "CS-101", 4.5, "2023-12345"
        );

        assertEquals(expectedFormat, student.toString());
    }

    @Test
    void toString_ShouldContainAllFields() {
        Student student = new Student.Builder()
                .groupNumber("IT-202")
                .averageScore(3.75)
                .recordBookNumber("2022-54321")
                .build();

        String result = student.toString();

        assertTrue(result.contains("IT-202"));
        assertTrue(result.contains("3.75") || result.contains("3,75")); // locale-dependent
        assertTrue(result.contains("2022-54321"));
    }

    @Test
    void getters_ShouldReturnCorrectValues() {
        Student student = new Student.Builder()
                .groupNumber("SE-303")
                .averageScore(4.2)
                .recordBookNumber("2021-11111")
                .build();

        assertEquals("SE-303", student.getGroupNumber());
        assertEquals(4.2, student.getAverageScore(), 0.001);
        assertEquals("2021-11111", student.getRecordBookNumber());
    }

    @Test
    void student_ShouldBeImmutable() {
        Student student = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertEquals("CS-101", student.getGroupNumber());
        assertEquals(4.5, student.getAverageScore());
        assertEquals("2023-12345", student.getRecordBookNumber());

        assertEquals("CS-101", student.getGroupNumber());
        assertEquals(4.5, student.getAverageScore());
        assertEquals("2023-12345", student.getRecordBookNumber());
    }

    @Test
    void compareTo_ShouldBeConsistentWithEquals() {
        Student student1 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(4.5)
                .recordBookNumber("2023-12345")
                .build();

        assertEquals(student1, student2);
        assertEquals(0, student1.compareTo(student2));
    }

    @Test
    void compareTo_ShouldBeTransitive() {
        Student student1 = new Student.Builder()
                .groupNumber("AA-100")
                .averageScore(3.0)
                .recordBookNumber("2020-11111")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("BB-200")
                .averageScore(4.0)
                .recordBookNumber("2021-22222")
                .build();

        Student student3 = new Student.Builder()
                .groupNumber("CC-300")
                .averageScore(5.0)
                .recordBookNumber("2022-33333")
                .build();

        assertTrue(student1.compareTo(student2) < 0);
        assertTrue(student2.compareTo(student3) < 0);
        assertTrue(student1.compareTo(student3) < 0);
    }
}
