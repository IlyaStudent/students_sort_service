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

import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class ManualInputImpl implements ManualInput {

    private final InputReader reader;
    private final OutputWriter writer;

    public ManualInputImpl(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public CustomList<Student> inputData(int count) throws InputMismatchException {
        CustomList<Student> students = new CustomArrayList<>();

        IntStream.range(0, count)
                .forEach(i -> students.add(readStudentData(i + 1, students)));

        return students;
    }

    @Override
    public String getInputType() {
        return this.getClass().getName();
    }

    private Student readStudentData(int studentNumber, CustomList<Student> existingStudents) {
        writer.printf("\nПожалуйста введите данные для студента под номером #%d:%n", studentNumber);

        String groupNumber = readGroupNumber();
        double averageScore = readAverageScore();
        String recordBookNumber = readRecordBookNumber(existingStudents);

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

    private String readRecordBookNumber(CustomList<Student> existingStudents) {
        while (true) {
            writer.print("Введите номер зачетной книжки (формат: YYYY-NNNNN, пример: 2023-12345): ");
            String input = reader.readInput();

            RecordBookValidator validator = new RecordBookValidator();
            validator.validate(input);

            boolean isDuplicate = false;
            for (Student student : existingStudents) {
                if (student.getRecordBookNumber().equals(input)) {
                    writer.printf("\nСтудент с номером зачетки %s уже существует, повторите ввод\n", input);
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                return input;
            }
        }
    }
}
