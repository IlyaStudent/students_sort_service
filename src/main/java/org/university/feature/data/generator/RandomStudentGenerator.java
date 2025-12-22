package org.university.feature.data.generator;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.common.util.Constants;
import java.util.Random;

public class RandomStudentGenerator implements DataGenerator{

    private final Random random;

    public RandomStudentGenerator() {
        this.random = new Random();
    }

    @Override
    public CustomList<Student> generateStudents(int count) {
        CustomList<Student> students = new CustomArrayList<>();

        for (int i = 0; i < count; i++) {
            Student student = generateStudent();
            students.add(student);
        }

        System.out.printf("Generated %d random students%n", count);
        for (Student student : students) {
            System.out.println(student.toString());
        }
        return students;
    }

    private Student generateStudent() {
        String groupNumber = generateGroupNumber();
        double averageScore = generateAverageScore();
        String recordBookNumber = generateRecordBookNumber();

        return new Student.Builder()
                .groupNumber(groupNumber)
                .averageScore(averageScore)
                .recordBookNumber(recordBookNumber)
                .build();
    }

    private String generateGroupNumber() {
        String prefix = Constants.GROUP_PREFIXES[random.nextInt(Constants.GROUP_PREFIXES.length)];
        int number = 100 + random.nextInt(900);
        return String.format("%s-%03d", prefix, number);
    }

    private double generateAverageScore() {
        double score = 0.1 + random.nextDouble() * 4.9;
        return Math.round(score * 10.0) / 10.0;
    }

    private String generateRecordBookNumber() {
        int year = 2000 + random.nextInt(26);
        int baseNumber = 10000 + 17;
        int randomOffset = random.nextInt(1000);
        int recordNumber = (baseNumber + randomOffset) % 90000 + 10000;

        return String.format("%d-%05d", year, recordNumber);
    }
}
