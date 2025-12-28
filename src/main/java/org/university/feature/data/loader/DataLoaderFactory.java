package org.university.feature.data.loader;

import org.university.feature.ui.io.ConsoleReader;
import org.university.feature.ui.io.ConsoleWriter;
import org.university.feature.ui.option.DataLoadOption;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class DataLoaderFactory {
    private static final Map<DataLoadOption, DataLoader> LOADERS = new ConcurrentHashMap<>();

    private DataLoaderFactory() {
        throw new AssertionError("Cannot instantiate factory class");
    }

    private static DataLoader createLoader(DataLoadOption option) {
        return switch (option) {
            case FILE -> new FileDataLoader();
            case CONSOLE -> new ManualDataLoader(ConsoleReader.getInstance(), ConsoleWriter.getInstance());
            case GENERATION -> new RandomDataLoader();
            default ->
                    throw new IllegalArgumentException(
                            String.format("Unsupported data load option: %s", option));
        };
    }

    public static DataLoader getInstanceFromOption(DataLoadOption loadOption) {
        Objects.requireNonNull(loadOption, "Load option cannot be null");
        return LOADERS.computeIfAbsent(loadOption, DataLoaderFactory::createLoader);
    }
}
