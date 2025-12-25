package org.university.feature.data.loader;

import org.university.feature.ui.option.DataLoadOption;

import java.util.Objects;

public final class DataLoaderCloseableFactory {

    private DataLoaderCloseableFactory() {}

    public static DataLoaderCloseable newInstance(DataLoadOption option) {
        if (Objects.requireNonNull(option) == DataLoadOption.CONSOLE) {
            return new ManualDataLoader();
        }

        throw new IllegalArgumentException();
    }
}
