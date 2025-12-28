package org.university.common.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class DataValidatorTest {

    private DataValidator dataValidator;

    @BeforeEach
    void setUp() {
        dataValidator = new DataValidator();
    }

    @Test
    void validateStudentList_ShouldThrowException_WhenListIsNull() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> dataValidator.validateStudentList(null)
        );
        assertEquals("List cannot be empty", exception.getMessage());
    }

    @Test
    void validateStudentList_ShouldThrowException_WhenListIsEmpty() {
        CustomList<Student> emptyList = new CustomArrayList<>();
        ValidationException exception = assertThrows(ValidationException.class,
                () -> dataValidator.validateStudentList(emptyList)
        );
        assertEquals("List cannot be empty", exception.getMessage());
    }

    @Test
    void validateStudentList_ShouldNotThrowException_WhenAllStudentsAreValid() {
        CustomList<Student> students = new CustomArrayList<>();

        students.add(new Student.Builder()
                .groupNumber("AB-123")
                .averageScore(4.5)
                .recordBookNumber("1234-56789")
                .build())
        ;
        students.add(new Student.Builder()
                .groupNumber("CD-456")
                .averageScore(3.8)
                .recordBookNumber("9876-54321")
                .build())
        ;

        assertDoesNotThrow(() -> dataValidator.validateStudentList(students));
    }

    @Test
    void validateStudentList_ShouldThrowException_WhenStudentIsNull() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder()
                .groupNumber("AB-123")
                .averageScore(4.5)
                .recordBookNumber("1234-56789")
                .build())
        ;
        students.add(null);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> dataValidator.validateStudentList(students)
        );
        assertEquals("Student cannot be null", exception.getMessage());
    }

    @Test
    void validateAndCleanDuplicates_ShouldRemoveDuplicateRecordBookNumbers() {
        CustomList<Student> students = new CustomArrayList<>();

        Student student1 = new Student.Builder()
                .groupNumber("AB-123")
                .averageScore(4.5)
                .recordBookNumber("1234-56789")
                .build();

        Student student2 = new Student.Builder()
                .groupNumber("CD-456")
                .averageScore(3.8)
                .recordBookNumber("1234-56789")
                .build();

        Student student3 = new Student.Builder()
                .groupNumber("EF-789")
                .averageScore(4.2)
                .recordBookNumber("9876-54321")
                .build();

        Student student4 = new Student.Builder()
                .groupNumber("GH-012")
                .averageScore(3.5)
                .recordBookNumber("1234-56789")
                .build();

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        dataValidator.validateAndCleanDuplicates(students);

        assertEquals(2, students.size());

        boolean hasStudent1 = false;
        boolean hasStudent3 = false;

        for (Student student : students) {
            if (student.getRecordBookNumber().equals("1234-56789")) {
                hasStudent1 = true;
                assertEquals("AB-123", student.getGroupNumber());
                assertEquals(4.5, student.getAverageScore(), 0.001);
            }
            if (student.getRecordBookNumber().equals("9876-54321")) {
                hasStudent3 = true;
                assertEquals("EF-789", student.getGroupNumber());
                assertEquals(4.2, student.getAverageScore(), 0.001);
            }
        }

        assertTrue(hasStudent1);
        assertTrue(hasStudent3);
    }
}


class AverageScoreValidatorTest {

    private AverageScoreValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AverageScoreValidator();
    }

    @Test
    void validate_ShouldNotThrowException_WhenScoreIsMinimumValid() {
        assertDoesNotThrow(() -> validator.validate(1.0));
    }

    @Test
    void validate_ShouldNotThrowException_WhenScoreIsMaximumValid() {
        assertDoesNotThrow(() -> validator.validate(5.0));
    }

    @Test
    void validate_ShouldNotThrowException_WhenScoreIsMiddleValue() {
        assertDoesNotThrow(() -> validator.validate(3.5));
        assertDoesNotThrow(() -> validator.validate(4.0));
    }

    @Test
    void validate_ShouldThrowException_WhenScoreIsNegativeSix() {
        Double score = -6.0;
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(score)
        );
        assertTrue(exception.getMessage().contains("Average score field value is invalid"));
        assertEquals("averageScore", exception.getField());
        assertEquals(score.toString(), exception.getInvalidValue());
    }
}

class RecordBookValidatorTest {

    private RecordBookValidator validator;

    @BeforeEach
    void setUp() {
        validator = new RecordBookValidator();
    }

    @Test
    void validate_ShouldThrowException_WhenRecordBookNumberIsNull() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(null)
        );
        assertEquals("Record book cannot be empty", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenRecordBookNumberIsEmpty() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate("")
        );
        assertEquals("Record book cannot be empty", exception.getMessage());
    }

    @Test
    void validate_ShouldNotThrowException_WhenRecordBookNumberIsValid() {
        assertDoesNotThrow(() -> validator.validate("1234-56789"));
        assertDoesNotThrow(() -> validator.validate("0000-00000"));
        assertDoesNotThrow(() -> validator.validate("9999-99999"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456789", "123-45678", "1234-5678", "12-3456789", "abcd-efghi"})
    void validate_ShouldThrowException_WhenRecordBookNumberFormatIsInvalid(String recordBookNumber) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(recordBookNumber)
        );
        assertTrue(exception.getMessage().contains("Record book number field value is invalid"));
        assertEquals("recordBookNumber", exception.getField());
        assertEquals(recordBookNumber, exception.getInvalidValue());
    }
}
class GroupNumberValidatorTest {

    private GroupNumberValidator validator;

    @BeforeEach
    void setUp() {
        validator = new GroupNumberValidator();
    }

    @Test
    void validate_ShouldThrowException_WhenGroupNumberIsNull() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(null)
        );
        assertEquals("Group number cannot be empty", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenGroupNumberIsEmpty() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate("")
        );
        assertEquals("Group number cannot be empty", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenGroupNumberIsBlank() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate("   ")
        );
        assertNotNull(exception);
    }

    @Test
    void validate_ShouldNotThrowException_WhenGroupNumberIsValid() {
        assertDoesNotThrow(() -> validator.validate("AB-123"));
        assertDoesNotThrow(() -> validator.validate("XY-999"));
        assertDoesNotThrow(() -> validator.validate("ZZ-001"));
        assertDoesNotThrow(() -> validator.validate("AA-100"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab-123", "Ab-123", "aB-123"})
    void validate_ShouldThrowException_WhenGroupNumberHasLowercaseLetters(String groupNumber) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(groupNumber)
        );
        assertTrue(exception.getMessage().contains("Group number field value is invalid"));
        assertEquals("groupNumber", exception.getField());
        assertEquals(groupNumber, exception.getInvalidValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AB123", "AB-12", "AB-1234", "AB-12A"})
    void validate_ShouldThrowException_WhenGroupNumberHasWrongFormat(String groupNumber) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(groupNumber)
        );
        assertTrue(exception.getMessage().contains("Group number field value is invalid"));
        assertEquals("groupNumber", exception.getField());
        assertEquals(groupNumber, exception.getInvalidValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABC-123", "A-123", "ABCD-1234"})
    void validate_ShouldThrowException_WhenGroupNumberHasWrongLetterCount(String groupNumber) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(groupNumber)
        );
        assertTrue(exception.getMessage().contains("Group number field value is invalid"));
        assertEquals("groupNumber", exception.getField());
        assertEquals(groupNumber, exception.getInvalidValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AB-", "-123", "AB-", "-"})
    void validate_ShouldThrowException_WhenGroupNumberHasMissingParts(String groupNumber) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(groupNumber)
        );
        assertTrue(exception.getMessage().contains("Group number field value is invalid"));
        assertEquals("groupNumber", exception.getField());
        assertEquals(groupNumber, exception.getInvalidValue());
    }

    @Test
    void validate_ShouldThrowException_WhenGroupNumberHasSpecialCharacters() {
        String groupNumber = "A*-123";
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(groupNumber)
        );
        assertTrue(exception.getMessage().contains("Group number field value is invalid"));
        assertEquals("groupNumber", exception.getField());
        assertEquals(groupNumber, exception.getInvalidValue());
    }

    @Test
    void validate_ShouldThrowException_WhenGroupNumberHasSpaces() {
        String[] invalidGroupNumbers = {"AB -123", "AB- 123", "AB - 123", " AB-123", "AB-123 "};

        for (String groupNumber : invalidGroupNumbers) {
            ValidationException exception = assertThrows(ValidationException.class,
                    () -> validator.validate(groupNumber)
            );
            assertTrue(exception.getMessage().contains("Group number field value is invalid"));
            assertEquals("groupNumber", exception.getField());
            assertEquals(groupNumber, exception.getInvalidValue());
        }
    }
}