package org.university.feature.ui.io;

import org.university.common.exception.ValidationException;
import org.university.common.validator.GroupNumberValidator;
import org.university.common.validator.Validator;
import org.university.feature.ui.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements InputReader {

    private static ConsoleReader instance;

    private final BufferedReader in;
    private final InputValidator inputValidator;
    private final OutputWriter writer;
    private final Validator<String> groupNumberValidator;

    private ConsoleReader() {
        in = new BufferedReader(new InputStreamReader(System.in));
        inputValidator = new InputValidator();
        writer = ConsoleWriter.getInstance();
        groupNumberValidator = new GroupNumberValidator();
    }

    @Override
    public int readInt() {
        try {
            String input = in.readLine();
            while (!inputValidator.isValidInt(input)) {
                writer.println("\nНеверный ввод. Пожалуйста, введите допустимое целое число.");
                writer.print("Попробуйте еще раз: ");
                input = in.readLine();
            }

            return Integer.parseInt(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data", e);
        }
    }

    @Override
    public String readGroupNumber() {
        while (true) {
            try {
                writer.print("Введите номер группы (формат: XX-NNN, пример: CS-101): ");
                String input = in.readLine();
                groupNumberValidator.validate(input);
                return input;
            } catch (IOException e) {
                throw new RuntimeException("Failed to read data", e);
            } catch (ValidationException e) {
                writer.println("Некорректный формат ввода. Попробуйте еще раз.\n");
            }
        }
    }

    @Override
    public String readInput() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data", e);
        }
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed close resource", e);
        }
    }

    public static ConsoleReader getInstance() {
        if (instance == null) {
            instance = new ConsoleReader();
        }

        return instance;
    }
}
