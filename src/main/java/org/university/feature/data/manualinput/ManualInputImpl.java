package org.university.feature.data.manualinput;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.ValidationException;
import org.university.common.model.Student;
import org.university.common.validator.AverageScoreValidator;
import org.university.common.validator.GroupNumberValidator;
import org.university.common.validator.RecordBookValidator;
import org.university.feature.ui.io.InputReader;
import org.university.feature.ui.io.OutputWriter;

import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ManualInputImpl implements ManualInput {

    private final InputReader reader;
    private final OutputWriter writer;

    public ManualInputImpl(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public CustomList<Student> inputData(int count)  {
        CustomList<Student> students = new CustomArrayList<>();

        for (int i = 0; i < count; i++) {
            Student student = readStudentData(i + 1);
            students.add(student);
        }

        return students;
    }

    @Override
    public String getInputType() {
        return this.getClass().getName();
    }

    private Student readStudentData(int studentNumber) {
        writer.printf("\nПожалуйста введите данные для студента под номером #%d:%n", studentNumber);

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
            writer.print("\nВведите номер группы (формат: XX-NNN, пример: CS-101): ");
            String input = reader.readInput().toUpperCase();

            GroupNumberValidator validator = new GroupNumberValidator();
            validator.validate(input);
            return input;
        }
    }

    private double readAverageScore() {
        while (true) {
            try {
                writer.print("Введите средний балл (0.0 - 5.0): ");
                double score = Double.parseDouble(reader.readInput());
                AverageScoreValidator validator = new AverageScoreValidator();
                validator.validate(score);
                return score;

            } catch (NumberFormatException e) {
                throw new ValidationException(e.getMessage());
            }
        }
    }

    private String readRecordBookNumber() {
        while (true) {
            writer.print("Введите номер зачетной книжки (формат: YYYY-NNNNN, пример: 2023-12345): ");
            String input = reader.readInput();

            RecordBookValidator validator = new RecordBookValidator();
            validator.validate(input);
            return input;
        }
    }
}
