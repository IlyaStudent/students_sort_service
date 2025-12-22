package org.university.feature.data.manualinput;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.common.validator.AverageScoreValidator;
import org.university.common.validator.GroupNumberValidator;
import org.university.common.validator.RecordBookValidator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ManualInputImpl implements ManualInput{

    private final Scanner scanner;

    public ManualInputImpl() {
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    @Override
    public CustomList<Student> inputData(int count) throws InputMismatchException {
        CustomList<Student> students = new CustomArrayList<>();

        for (int i = 0; i < count; i++) {
            Student student = readStudentData(i + 1);
            students.add(student);
        }
        for (Student student : students) {
            System.out.println(student.toString());
        }
        return students;
    }

    @Override
    public String getInputType() {
        return this.getClass().getName();
    }

    private Student readStudentData(int studentNumber) {
        System.out.printf("Please enter data for student number #%d:%n", studentNumber);

        String groupNumber = readGroupNumber();
        double averageScore = readAverageScore();
        String recordBookNumber = readRecordBookNumber();

        return new Student.Builder()
                .groupNumber(groupNumber)
                .averageScore(averageScore)
                .recordBookNumber(recordBookNumber)
                .build();
    }

    private String readGroupNumber() {
        while (true) {
            System.out.print("Enter group number (format: XX-NNN, example CS-101): ");
            String input = scanner.nextLine().trim().toUpperCase();

            GroupNumberValidator validator = new GroupNumberValidator();
            validator.validate(input);
            return input;
        }
    }

    private double readAverageScore() {
        while (true) {
            try {
                System.out.print("Enter average score (0.0 - 5.0): ");
                double score = scanner.nextDouble();
                scanner.nextLine();

                AverageScoreValidator validator = new AverageScoreValidator();
                validator.validate(score);
                return score;

            } catch (InputMismatchException e) {
                System.out.println("Enter number (ex.: 4.5 or 3.7)");
                scanner.nextLine();
            }
        }
    }

    private String readRecordBookNumber() {
        while (true) {
            System.out.print("Enter record book number (format: YYYY-NNNNN, ex. 2023-12345): ");
            String input = scanner.nextLine().trim();

            RecordBookValidator validator = new RecordBookValidator();
            validator.validate(input);
            return input;
        }
    }
}
