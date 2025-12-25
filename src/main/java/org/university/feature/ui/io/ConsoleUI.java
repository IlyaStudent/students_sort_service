package org.university.feature.ui.io;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.exception.DataLoadException;
import org.university.common.exception.ValidationException;
import org.university.feature.data.loader.DataLoader;
import org.university.feature.ui.option.*;
import org.university.common.model.Student;

import java.util.Optional;

public class ConsoleUI {

    private static final int MIN_MENU_OPTION = 1;

    private static final String TITLE_LOAD_OPTION = "\n=== Варианты получения данных ===\n";
    private static final String TITLE_DATA_PROCESS_OPTION = "\n=== Варианты обработки данных ===\n";
    private static final String TITLE_SORT_ALGORITHM_OPTION = "\n=== Варианты алгоритмов сортировки ===\n";
    private static final String TITLE_SPECIFIC_SORT_OPTION = "\n=== Конкретные сортировки ===\n";


    private static final String TEMPLATE_OUTPUT_OPTION = "%d. %s%n";
    private static final String INVALID_INPUT_MESSAGE =
            "Вы указали вариант, который не существует. Попробуйте еще раз.";
    private static final String INVALID_COUNT_MESSAGE =
            "Количество записей не может быть отрицательным или равное 0.";
    private static final String TEMPLATE_INPUT_NUMBER_OPTION =
            "Введите номер варианта (от %d до %d): ";

    private final InputReader reader;
    private final OutputWriter writer;

    public ConsoleUI(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void displayMessage(String message) {
        writer.println(message);
    }

    public DataLoadOption promptLoadOption() {
        return promptMenuOption(
                DataLoadOption.values(),
                TITLE_LOAD_OPTION
        );
    }

    public DataProcessOption promptDataProcessOption() {
        return promptMenuOption(
                DataProcessOption.values(),
                TITLE_DATA_PROCESS_OPTION
        );
    }

    public GeneralSortAlgorithmOption promptSortAlgorithmOption() {
        return promptMenuOption(
                GeneralSortAlgorithmOption.values(),
                TITLE_SORT_ALGORITHM_OPTION
        );
    }

    public SpecificSortOption promptSpecificSortOption() {
        return promptMenuOption(
                SpecificSortOption.values(),
                TITLE_SPECIFIC_SORT_OPTION
        );
    }

    private <T extends Enum<T> & CodeOption> T promptMenuOption(T[] options, String title) {
        while (true) {
            displayMessage(title);
            displayAllLoadOptions(options);
            writer.printf("\n" + TEMPLATE_INPUT_NUMBER_OPTION, MIN_MENU_OPTION, options.length);

            int code = reader.readInt();
            Optional<T> option = CodeOption.fromCode(code, options);

            if (option.isPresent()) {
                return option.get();
            }

            writer.println(INVALID_INPUT_MESSAGE);
        }
    }

    private <T extends Enum<T> & CodeOption> void displayAllLoadOptions(T[] options) {
        for (T option : options) {
            writer.printf(TEMPLATE_OUTPUT_OPTION, option.getCode(), option.getDescription());
        }
    }

    public CustomList<Student> promptDataStudents(DataLoader loader, int count) {
        while (true) {
            try {
                CustomList<Student> students = loader.loadData(count);
                writer.println("Данные получены....");
                return students;
            } catch (ValidationException e) {
                writer.println("Некорректный формат ввода.\n");

                if (!isContinueInputStudents()) {
                    return new CustomArrayList<>();
                }
            } catch (DataLoadException e) {
                writer.println("\nКоличество запрашиваемых данных превышает целевой размер файла.");
                writer.println("Попробуйте снова.\n");
                count = promptNumberOfRecords();
            }
        }
    }

    private boolean isContinueInputStudents() {
        final String continueExecution = "да";
        final String notContinueExecution = "нет";

        String input;
        while (true) {
            writer.print("Возобновить ввод? (Да|Нет): ");
            input = reader.readInput().toLowerCase();

            if (input.equals(continueExecution)) {
                return true;
            } else if (input.equals(notContinueExecution)) {
                return false;
            } else {
                writer.println("Некорректный ввод. Попробуйте еще раз.");
            }
        }
    }

    public int promptNumberOfRecords() {
        while (true) {
            writer.print("Введите количество записей: ");
            int input = reader.readInt();

            if (input > 0) {
                return input;
            }

            writer.println('\n' + INVALID_COUNT_MESSAGE);
            writer.println("Попробуйте еще раз.\n");
        }
    }

    public String promptGroupNumberStudent() {
        return reader.readGroupNumber();
    }

    public void showDataOnDisplay(CustomList<? extends Student> collection) {
        writer.print("\n");
        collection.forEach(student -> writer.println(student.toString()));
    }

    public void closeAllResources() {
        writer.close();
        reader.close();
    }
}
