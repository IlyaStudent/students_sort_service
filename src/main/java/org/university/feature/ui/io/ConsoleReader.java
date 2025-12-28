package org.university.feature.ui.io;

import org.university.common.Constants;
import org.university.common.exception.ValidationException;
import org.university.common.validator.GroupNumberValidator;
import org.university.common.validator.Validator;
import org.university.feature.ui.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements InputReader {

    private static final ConsoleReader INSTANCE = new ConsoleReader();

    private final BufferedReader in;
    private final OutputWriter writer;
    private final Validator<String> groupNumberValidator;

    private ConsoleReader() {
        in = new BufferedReader(new InputStreamReader(System.in));
        writer = ConsoleWriter.getInstance();
        groupNumberValidator = new GroupNumberValidator();
    }

    @Override
    public int readInt() {
        try {
            String input = in.readLine();
            while (!InputValidator.isValidInt(input)) {
                writer.println(Constants.MESSAGE_INVALID_INTEGER);
                writer.print(Constants.PROMPT_TRY_AGAIN);
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
                writer.print(Constants.PROMPT_GROUP_NUMBER);
                String input = in.readLine();
                groupNumberValidator.validate(input);
                return input;
            } catch (IOException e) {
                throw new RuntimeException("Failed to read data", e);
            } catch (ValidationException e) {
                writer.println(Constants.MESSAGE_INVALID_INPUT + "\n");
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

    public static ConsoleReader getInstance() {
        return INSTANCE;
    }
}
