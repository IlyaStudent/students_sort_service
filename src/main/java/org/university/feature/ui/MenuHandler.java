package org.university.feature.ui;

import org.university.common.collection.CustomList;
import org.university.common.util.Constants;
import org.university.feature.data.io.FileManager;
import org.university.feature.data.io.JsonWriter;
import org.university.feature.data.loader.*;
import org.university.feature.search.StudentSearcher;
import org.university.feature.sorting.strategy.SortContext;
import org.university.feature.sorting.strategy.impl.SortFactory;
import org.university.feature.ui.io.ConsoleUI;
import org.university.feature.ui.option.DataLoadOption;
import org.university.feature.ui.option.DataProcessOption;
import org.university.common.model.Student;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;
import org.university.feature.ui.option.SpecificSortOption;

import java.util.Comparator;

public class MenuHandler {

    private static final String MESSAGE_FINISH_PROGRAM = "Завершение программы...";

    private final ConsoleUI consoleUI;
    private final StudentSearcher searcher;
    private final SortContext sortContext;
    private final JsonWriter jsonWriter;

    private boolean isRunning = true;

    public MenuHandler(ConsoleUI consoleUI, StudentSearcher searcher,
                       JsonWriter jsonWriter, SortContext sortContext) {
        this.consoleUI = consoleUI;
        this.searcher = searcher;
        this.jsonWriter = jsonWriter;
        this.sortContext = sortContext;
    }

    public void process() {
        try {
            consoleUI.displayMessage("\n=== Система сортировки студентов ===");
            processLoadData();
        } catch (Exception e) {
            consoleUI.displayMessage("Unexpected error: " + e.getMessage());
        } finally {
            closeAllBeforeExit();
        }
    }

    private void processLoadData() throws Exception {
        while (isRunning) {
            DataLoadOption loadOption = consoleUI.promptLoadOption();
            if (loadOption == DataLoadOption.EXIT) {
                return;
            }

            int count = consoleUI.promptNumberOfRecords();
            CustomList<Student> students;

            if (loadOption == DataLoadOption.CONSOLE) {
                try (DataLoaderCloseable loader =
                        DataLoaderCloseableFactory.newInstance(loadOption)) {
                    students = consoleUI.promptDataStudents(loader, count);
                }
            } else {
                DataLoader loader = DataLoaderFactory.getInstanceFromOption(loadOption);
                students = consoleUI.promptDataStudents(loader, count);
            }

            if (!students.isEmpty()) {
                processData(students);
            } else {
                consoleUI.displayMessage("Коллекция не должна быть пустой!");
                throw new IllegalStateException("Collection cannot be empty");
            }
        }
    }

    private void processData(CustomList<Student> students) {
        while (isRunning) {
            DataProcessOption processOption = consoleUI.promptDataProcessOption();

            if (processOption == DataProcessOption.BACK) {
                consoleUI.displayMessage("Возврат к выбору вариантов получения данных...");
                return;
            }

            if (processOption == DataProcessOption.EXIT) {
                isRunning = false;
                return;
            }

            switch (processOption) {
                case FIND_STUDENT -> {
                    String groupNumber = consoleUI.promptGroupNumberStudent();
                    int count = searcher.countOccurrences(students, groupNumber);
                    consoleUI.displayMessage("Количество совпадений: " + count + ".");
                }

                case SAVE_DATA_TO_FILE ->
                        jsonWriter.writeData(
                                students, FileManager.getJsonFilepath(Constants.JSON_FILENAME));

                case SORT_STUDENTS -> processSelectAlgorithmSort(students);
                case SHOW_DATA_TO_USER -> consoleUI.showDataOnDisplay(students);

                default ->
                        throw new IllegalArgumentException(
                                "The specified option does not exist: " + processOption);
            }
        }
    }

    private void processSelectAlgorithmSort(CustomList<Student> students) {
        while (isRunning) {
            GeneralSortAlgorithmOption sortOption = consoleUI.promptSortAlgorithmOption();

            if (sortOption == GeneralSortAlgorithmOption.BACK) {
                return;
            }

            if (sortOption == GeneralSortAlgorithmOption.EXIT) {
                isRunning = false;
                return;
            }

            if (processDataSort(sortOption, students)) {
                return;
            }
        }
    }

    private boolean processDataSort(
            GeneralSortAlgorithmOption sortOption, CustomList<Student> students) {
        SpecificSortOption specificSortOption = consoleUI.promptSpecificSortOption();
        if (specificSortOption == SpecificSortOption.BACK) {
            return false;
        }

        if (specificSortOption == SpecificSortOption.EXIT) {
            isRunning = false;
            return false;
        }

        sortContext.setStrategy(
                SortFactory.getSortStrategyFromOptions(sortOption, specificSortOption));
        if (specificSortOption == SpecificSortOption.SORT_EVEN_SCORE) {
            sortContext.executeSort(students, Comparator.comparingDouble(Student::getAverageScore));
        } else {
            sortContext.executeSort(students);
        }

        consoleUI.displayMessage("Данные отсортированы...");
        return true;
    }

    private void closeAllBeforeExit() {
        consoleUI.displayMessage(MESSAGE_FINISH_PROGRAM);
        consoleUI.closeAllResources();
    }
}
