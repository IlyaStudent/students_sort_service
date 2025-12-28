package org.university.feature.ui.io;

import org.university.common.Constants;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.exception.ValidationException;
import org.university.feature.data.loader.DataLoader;
import org.university.feature.ui.option.*;
import org.university.common.model.Student;

import java.util.Objects;
import java.util.Optional;

public class ConsoleUI {

    private static final int MIN_MENU_OPTION = 1;

    private final InputReader reader;
    private final OutputWriter writer;

    public ConsoleUI(InputReader reader, OutputWriter writer) {
        this.reader = Objects.requireNonNull(reader, "InputReader cannot be null");
        this.writer = Objects.requireNonNull(writer, "OutputWriter cannot be null");
    }

    public void displayMessage(String message) {
        Objects.requireNonNull(message, "Message cannot be null");
        writer.println(message);
    }

    public DataLoadOption promptLoadOption() {
        return promptMenuOption(
                DataLoadOption.values(),
                Constants.TITLE_LOAD_OPTION
        );
    }

    public DataProcessOption promptDataProcessOption() {
        return promptMenuOption(
                DataProcessOption.values(),
                Constants.TITLE_DATA_PROCESS_OPTION
        );
    }

    public GeneralSortAlgorithmOption promptSortAlgorithmOption() {
        return promptMenuOption(
                GeneralSortAlgorithmOption.values(),
                Constants.TITLE_SORT_ALGORITHM_OPTION
        );
    }

    public SpecificSortOption promptSpecificSortOption() {
        return promptMenuOption(
                SpecificSortOption.values(),
                Constants.TITLE_SPECIFIC_SORT_OPTION
        );
    }

    private <T extends Enum<T> & CodeOption> T promptMenuOption(T[] options, String title) {
        while (true) {
            displayMessage(title);
            displayAllLoadOptions(options);
            writer.printf("\n" + Constants.TEMPLATE_INPUT_NUMBER_OPTION, MIN_MENU_OPTION, options.length);

            int code = reader.readInt();
            Optional<T> option = CodeOption.fromCode(code, options);

            if (option.isPresent()) {
                return option.get();
            }

            writer.println(Constants.MESSAGE_INVALID_OPTION);
        }
    }

    private <T extends Enum<T> & CodeOption> void displayAllLoadOptions(T[] options) {
        for (T option : options) {
            writer.printf(Constants.TEMPLATE_OUTPUT_OPTION, option.getCode(), option.getDescription());
        }
    }

    public CustomList<Student> promptDataStudents(DataLoader loader, int count) {
        Objects.requireNonNull(loader, "DataLoader cannot be null");
        while (true) {
            try {
                CustomList<Student> students = loader.loadData(count);
                writer.println(Constants.MESSAGE_DATA_LOADED);
                return students;
            } catch (ValidationException e) {
                writer.println(Constants.MESSAGE_INVALID_FORMAT_INPUT);

                if (!isContinueInputStudents()) {
                    return new CustomArrayList<>();
                }
            } catch (DataLoadException e) {
                writer.println(Constants.MESSAGE_DATA_EXCEEDS_FILE_SIZE);
                writer.println(Constants.MESSAGE_TRY_AGAIN);
                count = promptNumberOfRecords();
            }
        }
    }

    private boolean isContinueInputStudents() {
        String input;
        while (true) {
            writer.print(Constants.PROMPT_CONTINUE_INPUT);
            input = reader.readInput().toLowerCase();

            if (input.equals(Constants.INPUT_YES)) {
                return true;
            } else if (input.equals(Constants.INPUT_NO)) {
                return false;
            } else {
                writer.println(Constants.MESSAGE_INVALID_CONTINUE_INPUT);
            }
        }
    }

    public int promptNumberOfRecords() {
        while (true) {
            writer.print(Constants.PROMPT_NUMBER_OF_RECORDS);
            int input = reader.readInt();

            if (input > 0) {
                return input;
            }

            writer.println('\n' + Constants.MESSAGE_INVALID_COUNT);
            writer.println(Constants.MESSAGE_TRY_AGAIN_NEWLINE);
        }
    }

    public String promptGroupNumberStudent() {
        return reader.readGroupNumber();
    }

    public void showDataOnDisplay(CustomList<? extends Student> collection) {
        Objects.requireNonNull(collection, "Collection cannot be null");
        writer.print("\n");
        collection.forEach(student -> writer.println(student.toString()));
    }
}
