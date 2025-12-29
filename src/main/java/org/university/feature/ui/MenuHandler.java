package org.university.feature.ui;

import org.university.common.collection.CustomList;
import org.university.common.Constants;
import org.university.feature.data.io.FileManager;
import org.university.feature.data.io.JsonWriter;
import org.university.feature.data.loader.DataLoader;
import org.university.feature.data.loader.DataLoaderFactory;
import org.university.feature.search.StudentSearcher;
import org.university.feature.sorting.SortContext;
import org.university.feature.sorting.strategy.impl.SortFactory;
import org.university.feature.ui.io.ConsoleUI;
import org.university.feature.ui.option.DataLoadOption;
import org.university.feature.ui.option.DataProcessOption;
import org.university.common.model.Student;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;
import org.university.feature.ui.option.SpecificSortOption;

import java.util.Comparator;
import java.util.Objects;

public class MenuHandler {

    private final ConsoleUI consoleUI;
    private final StudentSearcher searcher;
    private final SortContext sortContext;
    private final JsonWriter jsonWriter;

    private boolean isRunning = true;

    public MenuHandler(ConsoleUI consoleUI, StudentSearcher searcher, JsonWriter jsonWriter, SortContext sortContext) {
        this.consoleUI = Objects.requireNonNull(consoleUI, "ConsoleUI cannot be null");
        this.searcher = Objects.requireNonNull(searcher, "StudentSearcher cannot be null");
        this.jsonWriter = Objects.requireNonNull(jsonWriter, "JsonWriter cannot be null");
        this.sortContext = Objects.requireNonNull(sortContext, "SortContext cannot be null");
    }

    public void process() {
        consoleUI.displayMessage(Constants.MESSAGE_SYSTEM_TITLE);

        try {
            processLoadData();
        } catch (Exception e) {
            consoleUI.displayMessage(Constants.MESSAGE_UNEXPECTED_ERROR + e.getMessage());
            throw e;

        } finally {
            closeAllBeforeExit();
        }
    }

    private void processLoadData() {
        while (isRunning) {
            DataLoadOption loadOption = consoleUI.promptLoadOption();

            if (loadOption == DataLoadOption.EXIT) {
                return;
            }

            int count = consoleUI.promptNumberOfRecords();
            DataLoader loader = DataLoaderFactory.getInstanceFromOption(loadOption);
            CustomList<Student> students = consoleUI.promptDataStudents(loader, count);

            if (!students.isEmpty()) {
                processData(students);
            }
        }
    }

    private void processData(CustomList<Student> students) {
        while (isRunning) {
            DataProcessOption processOption = consoleUI.promptDataProcessOption();

            if (processOption == DataProcessOption.BACK) {
                consoleUI.displayMessage(Constants.MESSAGE_RETURN_TO_LOAD_OPTIONS);
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
                    consoleUI.displayMessage(Constants.MESSAGE_MATCHES_COUNT + count + ".");
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

    private boolean processDataSort(GeneralSortAlgorithmOption sortOption, CustomList<Student> students) {
        SpecificSortOption specificSortOption = consoleUI.promptSpecificSortOption();
        if (specificSortOption == SpecificSortOption.BACK) {
            return false;
        }

        if (specificSortOption == SpecificSortOption.EXIT) {
            isRunning = false;
            return false;
        }

        sortContext.setStrategy(SortFactory.getSortStrategyFromOptions(sortOption, specificSortOption));
        if (specificSortOption == SpecificSortOption.SORT_EVEN_SCORE) {
            sortContext.executeSort(students, Comparator.comparingDouble(Student::getAverageScore));
        } else {
            sortContext.executeSort(students);
        }

        consoleUI.displayMessage(Constants.MESSAGE_DATA_SORTED);
        return true;
    }

    private void closeAllBeforeExit() {
        consoleUI.displayMessage(Constants.MESSAGE_FINISH_PROGRAM);
    }
}
