package org.university;

import org.university.feature.data.io.JsonWriter;
import org.university.feature.search.MultiThreadSearcher;
import org.university.feature.search.StudentSearcher;
import org.university.feature.sorting.strategy.SortContext;
import org.university.feature.ui.InputValidator;
import org.university.feature.ui.MenuHandler;
import org.university.feature.ui.io.ConsoleReader;
import org.university.feature.ui.io.ConsoleUI;
import org.university.feature.ui.io.ConsoleWriter;

public class Application {
    public static void main(String[] args) {
        InputValidator validator = new InputValidator();

        ConsoleUI consoleUI = new ConsoleUI(ConsoleReader.getInstance(), ConsoleWriter.getInstance());

        StudentSearcher searcher = new MultiThreadSearcher();
        SortContext sortContext = new SortContext();
        JsonWriter jsonWriter = new JsonWriter();

        MenuHandler menuHandler = new MenuHandler(consoleUI, searcher, jsonWriter, sortContext);
        menuHandler.process();
    }
}