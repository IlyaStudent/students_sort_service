package org.university.feature.data.generator;

import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.common.Constants;
import org.university.common.util.StreamHelper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

public class RandomStudentGenerator implements DataGenerator{

    private final Random random;
    private final Set<String> usedRecordBookNumbers;

    public RandomStudentGenerator() {
        this.random = new Random();
        this.usedRecordBookNumbers = new HashSet<>();
    }

    @Override
    public CustomList<Student> generateStudents(int count) {
        usedRecordBookNumbers.clear();
        return IntStream.range(0, count)
                .mapToObj(i -> generateStudent())
                .collect(StreamHelper.toCustomList());
    }

    private Student generateStudent() {
        String groupNumber = generateGroupNumber();
        double averageScore = generateAverageScore();
        String recordBookNumber = generateUniqueRecordBookNumber();

        return new Student.Builder()
                .groupNumber(groupNumber)
                .averageScore(averageScore)
                .recordBookNumber(recordBookNumber)
                .build();
    }

    private String generateGroupNumber() {
        String prefix = Constants.GROUP_PREFIXES[random.nextInt(Constants.GROUP_PREFIXES.length)];
        int number = 100 + random.nextInt(900);
        return String.format(Constants.FORMAT_GROUP_NUMBER, prefix, number);
    }

    private double generateAverageScore() {
        double score = 0.1 + random.nextDouble() * 4.9;
        return Math.round(score * 10.0) / 10.0;
    }

    private String generateUniqueRecordBookNumber() {
        String recordBookNumber;
        int attempts = 0;
        final int maxAttempts = 1000;

        do {
            recordBookNumber = generateRecordBookNumber();
            attempts++;

            if (attempts >= maxAttempts) {
                throw new RuntimeException(
                        "Unable to generate unique record book number after " + maxAttempts + " attempts");
            }
        } while (usedRecordBookNumbers.contains(recordBookNumber));

        usedRecordBookNumbers.add(recordBookNumber);
        return recordBookNumber;
    }

    private String generateRecordBookNumber() {
        int year = 2000 + random.nextInt(26);
        int baseNumber = 10000 + 17;
        int randomOffset = random.nextInt(1000);
        int recordNumber = (baseNumber + randomOffset) % 90000 + 10000;

        return String.format(Constants.FORMAT_RECORD_BOOK, year, recordNumber);
    }
}
