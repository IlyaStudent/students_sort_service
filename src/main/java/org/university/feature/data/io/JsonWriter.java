package org.university.feature.data.io;

import com.google.gson.*;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonWriter {

    private final Gson gson;

    public JsonWriter() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void writeData(CustomList<Student> students, String filePath)
            throws DataLoadException {
        if (students == null) {
            throw new DataLoadException("List of students cannot be null");
        }

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new DataLoadException("Filepath cannot be empty");
        }
        try {
            JsonArray existingStudents = readOrCreateJsonArray(filePath);

            int addedCount = 0;
            for (Student student : students) {
                JsonObject studentJson = createStudentJson(student);

                if (isDuplicate(existingStudents, studentJson)) {
//                    System.out.printf("Пропущено повторное добавление записи: %s%n", student);
                    continue;
                }

                existingStudents.add(studentJson);
                addedCount++;
            }

            writeJsonArray(existingStudents, filePath);
            System.out.printf("Количество записей успешно записанных в файл: %d.\n", addedCount);

        } catch (IOException e) {
            throw new DataLoadException(
                    String.format("Error writing in file %s", filePath), e);
        } catch (Exception e) {
            throw new DataLoadException("JSON writing error", e);
        }
    }

    private JsonArray readOrCreateJsonArray(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return new JsonArray();
        }

        try (FileReader reader = new FileReader(filePath)) {
            return JsonParser.parseReader(reader).getAsJsonArray();
        } catch (Exception e) {
            System.out.println("Warning: Invalid JSON format. Creating new array.");
            return new JsonArray();
        }
    }

    private void writeJsonArray(JsonArray jsonArray, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(gson.toJson(jsonArray));
        } catch (IOException e) {
            throw new DataLoadException("JSON writing error", e);
        }
    }

    private JsonObject createStudentJson(Student student) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("groupNumber", student.getGroupNumber());
        jsonObject.addProperty("averageScore", student.getAverageScore());
        jsonObject.addProperty("recordBookNumber", student.getRecordBookNumber());
        return jsonObject;
    }

    private boolean isDuplicate(JsonArray array, JsonObject newStudent) {
        for (int i = 0; i < array.size(); i++) {
            JsonObject existing = array.get(i).getAsJsonObject();

            if (existing.get("groupNumber").getAsString().equals(newStudent.get("groupNumber").getAsString()) &&
                    existing.get("averageScore").getAsDouble() == newStudent.get("averageScore").getAsDouble() &&
                    existing.get("recordBookNumber").getAsString().equals(newStudent.get("recordBookNumber").getAsString())) {
                return true;
            }
        }
        return false;
    }
}
