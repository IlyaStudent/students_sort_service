package org.university.feature.ui.option;

import org.university.common.Constants;

import java.util.Optional;

public enum DataLoadOption implements CodeOption {
    CONSOLE(1, "Ввести вручную"),
    FILE(2, "Загрузить из файла"),
    GENERATION(3, "Сгенерировать случайным образом"),
    EXIT(4, Constants.DESCRIPTION_OPTION_EXIT);

    private final int code;
    private final String description;

    DataLoadOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return code;
    }

    public static Optional<DataLoadOption> fromCode(int code) {
        return CodeOption.fromCode(code, values());
    }
}
