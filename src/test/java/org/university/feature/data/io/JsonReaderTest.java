package org.university.feature.data.io;

import com.google.gson.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import java.io.FileReader;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonReaderTest {

    private JsonReader jsonReader;

    @BeforeEach
    void setUp() {
        jsonReader = new JsonReader();
    }

    @Test
    void parseStudents_ShouldThrowException_WhenCountIsNegative() {
        DataLoadException exception = assertThrows(DataLoadException.class,
                () -> jsonReader.parseStudents("students.json", -1)
        );

        assertEquals("Number of students must be greater than 0.", exception.getMessage());
    }

    @Test
    void parseStudents_ShouldReturnCorrectNumberOfStudents() {
        String fileName = "students.json";
        int count = 3;

        JsonArray jsonArray = createSampleJsonArray();

        try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
            JsonElement jsonElement = mock(JsonElement.class);
            when(jsonElement.getAsJsonArray()).thenReturn(jsonArray);

            mockedJsonParser.when(() -> JsonParser.parseReader(any(FileReader.class)))
                    .thenReturn(jsonElement);

            CustomList<Student> result = jsonReader.parseStudents(fileName, count);

            assertNotNull(result);
            assertEquals(count, result.size());

            Student student1 = result.get(0);
            assertEquals("AB-100", student1.getGroupNumber());
            assertEquals(4.0, student1.getAverageScore(), 0.001);
            assertEquals("2020-10000", student1.getRecordBookNumber());

            Student student2 = result.get(1);
            assertEquals("CD-200", student2.getGroupNumber());
            assertEquals(3.5, student2.getAverageScore(), 0.001);
            assertEquals("2020-10001", student2.getRecordBookNumber());

            Student student3 = result.get(2);
            assertEquals("EF-300", student3.getGroupNumber());
            assertEquals(4.5, student3.getAverageScore(), 0.001);
            assertEquals("2020-10002", student3.getRecordBookNumber());
        }
    }

    @Test
    void parseStudents_ShouldThrowException_WhenJsonSyntaxInvalid() {
        String fileName = "invalid.json";
        int count = 5;

        try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
            mockedJsonParser.when(() -> JsonParser.parseReader(any(FileReader.class)))
                    .thenThrow(new JsonSyntaxException("Invalid JSON"));

            DataLoadException exception = assertThrows(DataLoadException.class,
                    () -> jsonReader.parseStudents(fileName, count)
            );

            assertEquals("Database read error", exception.getMessage());
            assertNotNull(exception.getCause());
        }
    }

    @Test
    void parseStudents_ShouldThrowException_WhenCountIsZero() {
        DataLoadException exception = assertThrows(DataLoadException.class,
                () -> jsonReader.parseStudents("students.json", 0)
        );

        assertEquals("Number of students must be greater than 0.", exception.getMessage());
    }

    @Test
    void parseStudents_ShouldSkipInvalidObjects() {
        String fileName = "students.json";
        int count = 4;

        JsonArray jsonArray = createMixedJsonArray();

        try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
            JsonElement jsonElement = mock(JsonElement.class);
            when(jsonElement.getAsJsonArray()).thenReturn(jsonArray);

            mockedJsonParser.when(() -> JsonParser.parseReader(any(FileReader.class)))
                    .thenReturn(jsonElement);

            CustomList<Student> result = jsonReader.parseStudents(fileName, count);

            assertNotNull(result);
            assertEquals(2, result.size());
        }
    }

    private JsonArray createSampleJsonArray() {
        JsonArray jsonArray = new JsonArray();

        String[] groups = {"AB-100", "CD-200", "EF-300", "GH-400", "IJ-500"};
        double[] scores = {4.0, 3.5, 4.5, 3.8, 4.2};
        String[] recordBooks = {"2020-10000", "2020-10001", "2020-10002", "2020-10003", "2020-10004"};

        for (int i = 0; i < 5; i++) {
            JsonObject student = new JsonObject();
            student.addProperty("groupNumber", groups[i]);
            student.addProperty("averageScore", scores[i]);
            student.addProperty("recordBookNumber", recordBooks[i]);
            jsonArray.add(student);
        }

        return jsonArray;
    }

    private JsonArray createMixedJsonArray() {
        JsonArray jsonArray = new JsonArray();

        JsonObject valid1 = new JsonObject();
        valid1.addProperty("groupNumber", "AB-100");
        valid1.addProperty("averageScore", 4.0);
        valid1.addProperty("recordBookNumber", "2020-10000");
        jsonArray.add(valid1);

        JsonObject invalid1 = new JsonObject();
        invalid1.addProperty("averageScore", 3.5);
        invalid1.addProperty("recordBookNumber", "2020-10001");
        jsonArray.add(invalid1);

        JsonObject invalid2 = new JsonObject();
        invalid2.addProperty("groupNumber", "CD-200");
        invalid2.addProperty("recordBookNumber", "2020-10002");
        jsonArray.add(invalid2);

        JsonObject valid2 = new JsonObject();
        valid2.addProperty("groupNumber", "EF-300");
        valid2.addProperty("averageScore", 4.5);
        valid2.addProperty("recordBookNumber", "2020-10003");
        jsonArray.add(valid2);

        return jsonArray;
    }
}