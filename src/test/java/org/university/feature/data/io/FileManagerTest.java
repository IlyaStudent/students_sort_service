package org.university.feature.data.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    void getJsonFilepath_ShouldReturnCorrectPath_WhenFileNameIsValid() {
        String fileName = "students.json";
        String expectedPath = "src/main/resources/data/" + fileName;

        String actualPath = FileManager.getJsonFilepath(fileName);

        assertEquals(expectedPath, actualPath);
    }

    @Test
    void getJsonFilepath_ShouldThrowException_WhenFileNameIsEmpty() {
        String fileName = "";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileManager.getJsonFilepath(fileName)
        );

        assertEquals("Filename should not be null or empty", exception.getMessage());
    }

    @Test
    void getJsonFilepath_ShouldThrowException_WhenFileNameContainsInvalidCharacters() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileManager.getJsonFilepath("file@name.json")
        );

        assertTrue(exception.getMessage().contains("Wrong file name format")
                        || exception.getMessage().contains("should only contain"));
    }
}