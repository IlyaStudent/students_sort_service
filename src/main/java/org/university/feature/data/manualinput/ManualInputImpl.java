package org.university.feature.data.manualinput;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.common.util.StreamHelper;
import org.university.common.validator.AverageScoreValidator;
import org.university.common.validator.GroupNumberValidator;
import org.university.common.validator.RecordBookValidator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ManualInputImpl implements ManualInput {

    private final Scanner scanner;

    public ManualInputImpl() {
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    @Override
    public CustomList<Student> inputData(int count) throws InputMismatchException {
        return IntStream.range(0, count)
                .mapToObj(i -> readStudentData(i + 1))
                .collect(StreamHelper.toCustomList());
    }

    @Override
    public String getInputType() {
        return this.getClass().getName();
    }

    private Student readStudentData(int studentNumber) {
        System.out.printf("\nПожалуйста введите данные для студента под номером #%d:%n", studentNumber);

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
            System.out.print("\nВведите номер группы (формат: XX-NNN, пример: CS-101): ");
            String input = scanner.nextLine().trim().toUpperCase();

            GroupNumberValidator validator = new GroupNumberValidator();
            validator.validate(input);
            return input;
        }
    }

    private double readAverageScore() {
        while (true) {
            try {
                System.out.print("Введите средний балл (0.0 - 5.0): ");
                double score = scanner.nextDouble();
                scanner.nextLine();

                AverageScoreValidator validator = new AverageScoreValidator();
                validator.validate(score);
                return score;

            } catch (InputMismatchException e) {
                System.out.println("Введите номер (пример: 4.5 или 3.7)");
                scanner.nextLine();
            }
        }
    }

    private String readRecordBookNumber() {
        while (true) {
            System.out.print("Введите номер зачетной книжки (формат: YYYY-NNNNN, пример: 2023-12345): ");
            String input = scanner.nextLine().trim();

            RecordBookValidator validator = new RecordBookValidator();
            validator.validate(input);
            return input;
        }
    }
}
