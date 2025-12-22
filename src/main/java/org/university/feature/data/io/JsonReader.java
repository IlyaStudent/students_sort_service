package org.university.feature.data.io;

import com.google.gson.*;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader {

    public CustomList<Student> parseStudents(String fileName, int count) throws DataLoadException {
        if (count <= 0) {
            throw new DataLoadException("Number of students must be greater than 0.");
        }

        JsonArray jsonArray = readFromFile(fileName, count);
        CustomList<Student> students = new CustomArrayList<>();

        try {
            int loadedCount = 0;
            for (JsonElement element : jsonArray) {
                if (loadedCount >= count) {
                    break;
                }
                JsonObject jsonObject = element.getAsJsonObject();

                if (!jsonObject.has("groupNumber") ||
                        !jsonObject.has("averageScore") ||
                        !jsonObject.has("recordBookNumber")) {
                    continue;
                }

                String groupNumber = jsonObject.get("groupNumber").getAsString();
                double averageScore = jsonObject.get("averageScore").getAsDouble();
                String recordBookNumber = jsonObject.get("recordBookNumber").getAsString();

                Student student = new Student.Builder()
                        .groupNumber(groupNumber)
                        .averageScore(averageScore)
                        .recordBookNumber(recordBookNumber)
                        .build();

                students.add(student);
                loadedCount++;
            }

            System.out.printf("%d students loaded",
                    loadedCount);

        } catch (JsonSyntaxException e) {
            throw new DataLoadException("JSON syntax error", e);
        } catch (Exception e) {
            throw new DataLoadException("JSON data load error", e);
        }
        return students;
    }

    private JsonArray readFromFile(String fileName, int count) {
        String filePath = FileManager.getJsonFilepath(fileName);
        JsonArray jsonArray;
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() < count) {
                throw new DataLoadException(
                        String.format("There are only %d students in database",
                                jsonArray.size())
                );
            }
        } catch (IOException e) {
            throw new DataLoadException(
                    "Database read error", e);
        }
        return jsonArray;
    }
}
