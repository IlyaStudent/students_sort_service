package org.university.feature.data.loader;

import org.university.feature.ui.io.ConsoleReader;
import org.university.feature.ui.io.ConsoleWriter;
import org.university.feature.ui.option.DataLoadOption;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class DataLoaderFactory {
    private static final Map<DataLoadOption, DataLoader> LOADERS = new HashMap<>();

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
        if (LOADERS.containsKey(loadOption)) {
            return LOADERS.get(loadOption);
        } else {
            DataLoader loader = createLoader(loadOption);
            LOADERS.put(loadOption, loader);
            return loader;
        }
    }
}
