package org.university.feature.data.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    private JsonWriter jsonWriter;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        jsonWriter = new JsonWriter();
    }

    @Test
    void writeData_ShouldThrowException_WhenStudentsIsNull() {
        String filePath = tempDir.resolve("test.json").toString();

        DataLoadException exception = assertThrows(DataLoadException.class,
                () -> jsonWriter.writeData(null, filePath)
        );

        assertEquals("List of students cannot be null", exception.getMessage());
    }

    @Test
    void writeData_ShouldThrowException_WhenFilePathIsNull() {
        CustomList<Student> students = new CustomArrayList<>();

        DataLoadException exception = assertThrows(DataLoadException.class,
                () -> jsonWriter.writeData(students, null)
        );

        assertEquals("Filepath cannot be empty", exception.getMessage());
    }

    @Test
    void writeData_ShouldCreateNewFile_WhenFileDoesNotExist() throws Exception {
        Path filePath = tempDir.resolve("newfile.json");
        CustomList<Student> students = createSampleStudents(3);

        jsonWriter.writeData(students, filePath.toString());

        assertTrue(Files.exists(filePath), "Файл должен быть создан");

        String content = Files.readString(filePath);
        JsonArray jsonArray = JsonParser.parseString(content).getAsJsonArray();
        assertEquals(3, jsonArray.size(), "В файле должно быть 3 студента");
    }

    @Test
    void writeData_ShouldAppendToExistingFile() throws Exception {
        Path filePath = tempDir.resolve("existing.json");

        String initialJson = """
            [
                {
                    "groupNumber": "AB-100",
                    "averageScore": 4.0,
                    "recordBookNumber": "2020-10000"
                },
                {
                    "groupNumber": "CD-200",
                    "averageScore": 3.5,
                    "recordBookNumber": "2020-10001"
                }
            ]
            """;
        Files.writeString(filePath, initialJson);

        CustomList<Student> newStudents = createSampleStudents(2);

        jsonWriter.writeData(newStudents, filePath.toString());

        String content = Files.readString(filePath);
        JsonArray jsonArray = JsonParser.parseString(content).getAsJsonArray();
        assertEquals(4, jsonArray.size(), "В файле должно быть 4 студента (2 старых + 2 новых)");
    }

    @Test
    void writeData_ShouldSkipDuplicateStudents() throws Exception {
        Path filePath = tempDir.resolve("duplicates.json");

        String initialJson = """
            [
                {
                    "groupNumber": "AB-100",
                    "averageScore": 4.0,
                    "recordBookNumber": "2020-10000"
                }
            ]
            """;
        Files.writeString(filePath, initialJson);

        CustomList<Student> students = new CustomArrayList<>();

        students.add(new Student.Builder()
                .groupNumber("AB-100")
                .averageScore(4.0)
                .recordBookNumber("2020-10000")
                .build());

        students.add(new Student.Builder()
                .groupNumber("CD-200")
                .averageScore(3.5)
                .recordBookNumber("2020-10001")
                .build());

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        try {
            jsonWriter.writeData(students, filePath.toString());

            String content = Files.readString(filePath);
            JsonArray jsonArray = JsonParser.parseString(content).getAsJsonArray();
            assertEquals(2, jsonArray.size(), "Должен быть добавлен только 1 новый студент");

            String consoleOutput = outContent.toString();
            assertTrue(consoleOutput.contains("Duplicate student skipped"),
                    "Должно быть сообщение о пропуске дубликата");
            assertTrue(consoleOutput.contains("1 students were added"),
                    "Должно быть сообщение о добавлении 1 студента");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void writeData_ShouldThrowException_WhenFileWritingFails() throws Exception {
        CustomList<Student> students = createSampleStudents(2);
        Path testFilePath = tempDir.resolve("test.json");

        JsonWriter writer = new JsonWriter();

        try {
            Files.createFile(testFilePath);
            testFilePath.toFile().setWritable(false);

            DataLoadException exception = assertThrows(DataLoadException.class,
                    () -> writer.writeData(students, testFilePath.toString())
            );

            assertTrue(exception.getMessage().contains("Error writing in file") ||
                    exception.getMessage().contains("JSON writing error"));
            assertNotNull(exception.getCause());
        } finally {
            testFilePath.toFile().setWritable(true);
        }
    }

    @Test
    void readOrCreateJsonArray_ShouldReadExistingFile() throws Exception {
        Path filePath = tempDir.resolve("existing.json");
        String jsonContent = """
            [
                {"groupNumber": "AB-100", "averageScore": 4.0, "recordBookNumber": "2020-10000"}
            ]
            """;
        Files.writeString(filePath, jsonContent);

        JsonArray result = invokePrivateMethod("readOrCreateJsonArray", filePath.toString());
        assertNotNull(result);
        assertEquals(1, result.size());

        JsonObject student = result.get(0).getAsJsonObject();
        assertEquals("AB-100", student.get("groupNumber").getAsString());
        assertEquals(4.0, student.get("averageScore").getAsDouble(), 0.001);
    }

    private CustomList<Student> createSampleStudents(int count) {
        CustomList<Student> students = new CustomArrayList<>();

        for (int i = 0; i < count; i++) {
            Student student = new Student.Builder()
                    .groupNumber(String.format("AB-%03d", 100 + i))
                    .averageScore(3.5 + (i * 0.1))
                    .recordBookNumber(String.format("2020-1%04d", i))
                    .build();
            students.add(student);
        }

        return students;
    }

    @SuppressWarnings("unchecked")
    private <T> T invokePrivateMethod(String methodName, Object... args) {
        try {
            Class<?>[] paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i].getClass();
            }

            var method = JsonWriter.class.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return (T) method.invoke(jsonWriter, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke private method: " + methodName, e);
        }
    }
}