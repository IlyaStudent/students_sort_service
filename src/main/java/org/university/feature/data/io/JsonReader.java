package org.university.feature.data.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.model.Student;
import org.university.common.util.StreamHelper;

import java.io.FileReader;
import java.io.IOException;
import java.util.stream.StreamSupport;

public class JsonReader {

    public CustomList<Student> parseStudents(String fileName, int count) throws DataLoadException {
        if (count <= 0) {
            throw new DataLoadException("Number of students must be greater than 0.");
        }

        JsonArray jsonArray = readFromFile(fileName, count);

        try {
            return StreamSupport.stream(jsonArray.spliterator(), false)
                    .limit(count)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("groupNumber") &&
                            jsonObject.has("averageScore") &&
                            jsonObject.has("recordBookNumber"))
                    .map(jsonObject -> new Student.Builder()
                            .groupNumber(jsonObject.get("groupNumber").getAsString())
                            .averageScore(jsonObject.get("averageScore").getAsDouble())
                            .recordBookNumber(jsonObject.get("recordBookNumber").getAsString())
                            .build())
                    .collect(StreamHelper.toCustomList());

        } catch (JsonSyntaxException e) {
            throw new DataLoadException("JSON syntax error", e);
        } catch (Exception e) {
            throw new DataLoadException("JSON data load error", e);
        }
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
